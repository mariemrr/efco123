package com.efco.formation.controllers;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.efco.formation.config.Middleware;
import com.efco.formation.config.SmtpMailSender;
import com.efco.formation.model.User;
import com.efco.formation.service.ClientService;
import com.efco.formation.service.ClientServiceCrud;
import com.efco.formation.service.RoleService;
import com.efco.formation.service.SessionFormationService;
import com.efco.formation.service.StagiareService;
import com.efco.formation.service.TypeFormationService;
import com.efco.formation.service.UserService;
import com.efco.formation.service.UserServiceCrud;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.efco.formation.model.Client;
import com.efco.formation.model.Event;
import com.efco.formation.model.SessionFormation;
import com.efco.formation.model.SessionFormationTime;
import com.efco.formation.model.Stagiaire;
import com.efco.formation.model.TypeFormation;


@Controller
@Scope("request")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	RoleService roleService;

	@Autowired
	ClientService clientService;

	@Autowired
	UserServiceCrud userServiceCrud;

	@Autowired
	TypeFormationService typeFormationService;

	Middleware middleWare = new Middleware();


	@Autowired
	StagiareService stagiaireService;
	@Autowired
	private SmtpMailSender smtpMailSender;

	@Autowired
	ClientServiceCrud clientServiceCrud;

	@Autowired
	SessionFormationService sessionFormationService;


	@RequestMapping("/user/login")
	public Object login(HttpSession session) {
		if(session.getAttribute("user")!=null)
		{

			return "redirect:/";
		}
		System.out.println("Here_connexion");
		session.setAttribute("type_formation_headers", typeFormationService.findAll());
		return new ModelAndView("../pages/all/Login");
	}


	@RequestMapping("/formateur/profile")
	public Object showProfileFormateur(HttpSession session) {
		System.out.println("Here profile");
		if(session.getAttribute("user")!=null)
		{
			if(middleWare.isFormateur(session))
			{
				updateUser(session);
				ModelAndView ProfilePage=  new ModelAndView("../pages/formateur/profile");
				User user =(User)session.getAttribute("user");
				System.out.println("user name : "+user.getNom());
				ProfilePage.addObject("user",user);
				List<SessionFormation> listSessionFormation= sessionFormationService.findAll();
				List<SessionFormation> session_formation_current_formateur = new ArrayList<SessionFormation>();
				List<Stagiaire> stagiaire_list=new ArrayList<Stagiaire>();




				for(SessionFormation x : listSessionFormation)
				{
					if(x.getFormateur().getId()==user.getId())
					{
						session_formation_current_formateur.add(x);
						for(Stagiaire y : x.getStagiaires())
						{
							if(!stagiaire_list.contains(y))
							{
								stagiaire_list.add(y);
							}
						}

					}
				}

				List<SessionFormation> formationList=sessionFormationService.findAll();
				System.out.println("Size from controller :"+formationList.size() );
				List<Event> events = new ArrayList<Event>();
				Map<String,String> eventsList = new HashMap<String,String>();
				for(SessionFormation sf : session_formation_current_formateur)
				{
					for(SessionFormationTime sft:sf.getSession_formation_time())
					{
						Event event = new Event();
						event.setId(sf.getId());
						event.setTitle(sf.getTitre());
						event.setStart(sft.getDate_deb().toString());
						event.setEnd(sft.getDate_fin().toString());
						event.setDuree(Integer.toString(sf.getDuree()));
						event.setLink(sf.getLink());
						event.setLocalisation(sf.getSallef().getLocalisation());
						event.setDescription(sf.getFormation().getDescription());
						event.setSalle(sf.getSallef().getNom());
						event.setAdresse(sf.getSallef().getAdresse());
						
						if(sf.getDate_Deb().before(new Date()))
						{
							event.setExpired(1);
						}
						Color color=new Color((int)(Math.random() * 0x1000000));
						String eventColor=String.format("#%02x%02x%02x", color.getRed(),color.getGreen() , color.getBlue());

						while(eventsList.containsValue(eventColor))
						{
							color=new Color((int)(Math.random() * 0x1000000));
							eventColor=String.format("#%02x%02x%02x", color.getRed(),color.getGreen() , color.getBlue());

							System.out.println("Color code : "+ eventColor);	
						}
						event.setColor(eventColor);


						System.out.println("Place restantes : "+(sf.getNbre_stagiaires()-sf.getStagiaires().size()));
						event.setFree_stagiare_number(sf.getNbre_stagiaires()-sf.getStagiaires().size());
						if(eventsList.get(event.getTitle())!=null)
						{
							event.setColor(eventsList.get((event.getTitle())));
						}
						events.add(event);
						eventsList.put(event.getTitle(),event.getColor());
						System.out.println("Event : "+event.toString());
					}
				}

				ObjectMapper mapper = new ObjectMapper();
				String jsonMsg = null;
				try {
					jsonMsg =  mapper.writerWithDefaultPrettyPrinter().writeValueAsString(events);
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				ProfilePage.addObject("list_formation",session_formation_current_formateur);
				ProfilePage.addObject("list_stag",stagiaire_list);
				ProfilePage.addObject("events",jsonMsg);
				session.setAttribute("type_formation_headers", typeFormationService.findAll());

				return ProfilePage;
			}
		}
		else
		{
			return "redirect:/user/login";
		}
		return "redirect:/index";

	}

	@RequestMapping(value="/formateur/profile/edit",method=RequestMethod.POST)
	public Object editFormateur(@RequestParam("password") String password,@RequestParam("adresse") String adresse,@RequestParam("nom") String nom,@RequestParam("prenom") String prenom,@RequestParam("email") String email,@RequestParam("num_tel") String num_tel,HttpSession session)
	{
		if(Middleware.isFormateur(session))
		{
			User user = (User)session.getAttribute("user");
			Optional<User> searched = userServiceCrud.findById(user.getId());
			if(searched.isPresent())
			{
				if(searched.get().getRole().getId()==2)
				{
					searched.get().setAdresse(adresse);
					searched.get().setEmail(email);
					searched.get().setNom(nom);
					searched.get().setPassword(password);
					searched.get().setEmail(email);
					searched.get().setNum_tel(num_tel);
					searched.get().setPrenom(prenom);
					if(password.length()!=0)
					{
						searched.get().setPassword(password);
					}

					userServiceCrud.saveAndFlush(searched.get());
					System.out.println("Done ");
				}

				return "redirect:/formateur/profile"; 
			}

			return "redirect:/formateur/profile"; 
		}
		return "redirect:/index";
	}




	@RequestMapping("/user/profile")
	public Object showProfile(HttpSession session) {
		System.out.println("Here profile");
		if(session.getAttribute("user")!=null)
		{
			if(middleWare.isUser(session))
			{
				updateUser(session);
				ModelAndView ProfilePage=  new ModelAndView("../pages/Client/ProfilClient");
				User user =(User)session.getAttribute("user");
				System.out.println("user name : "+user.getNom());
				ProfilePage.addObject("user",user);
				ProfilePage.addObject("list_stag",user.getClient().getList_stagiaires());
				//System.out.println("client id : "+clientService.getClientByUserId((int)user.getId()).getId());
				//		System.out.println("Size stagiaires count : "+user.getClient().getList_stagiaires().get(0).getNom());
				session.setAttribute("type_formation_headers", typeFormationService.findAll());

				return ProfilePage;
			}
		}
		else
		{
			return "redirect:/user/login";
		}
		return "redirect:/index";

	}

	public  void updateUser(HttpSession session)
	{
		User currentUser= (User)session.getAttribute("user");
		Optional<User> updateUser=userServiceCrud.findById(currentUser.getId());
		if(updateUser.isPresent())
		{
			session.setAttribute("user", updateUser.get());

		}
	}


	@RequestMapping(value="/user/login",method=RequestMethod.POST)
	public Object login(@RequestParam("email") String email,@RequestParam("password") String password,HttpSession session)
	{
		if(Middleware.isConnected(session))
		{
			updateUser(session);
			return "redirect:/";
		}
		System.out.println("Email : "+email+" Password : "+password);
		ModelAndView loginPage= new ModelAndView("../pages/all/Login");

		User user =null;
		try
		{
			user=userService.getUserByEmailAndPassword(email, password);
			if(user!=null)
			{
				System.out.println("Status : "+user.getStatus());

				if(user.getStatus()!=1)
				{

					loginPage.addObject("errors","Compte non valide");
					return loginPage;
				}


				System.out.println("Role name : "+user.getRole().getNom());

				switch(user.getRole().getId())
				{
				case 2 : 
					session.setAttribute("user", userServiceCrud.findById(user.getId()).get());
					return "redirect:/formateur/profile";

				case 3 :
					System.out.println("ok");

					session.setAttribute("user", userServiceCrud.findById(user.getId()).get());
					return "redirect:/user/profile";
				case 4 :

					System.out.println("ok");
					session.setAttribute("user", userServiceCrud.findById(user.getId()).get());
					return "redirect:/admin/dashboard";

				case 5 : 
					System.out.println("Stagiaire");
					session.setAttribute("user", userServiceCrud.findById(user.getId()).get());

					return "redirect:/stagiaire/profile";
				}

				System.out.println("Nom : "+user.getNom()+" Prénom : "+user.getPrenom());

			}
			else
			{
				loginPage.addObject("errors","Veuillez vérifier vos données");
				return loginPage;
			}
		}catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Excetpion : "+ex.getMessage());
			loginPage.addObject("errors","Veuillez vérifier vos données");
			return loginPage;
		}



		return null;
	}



	@RequestMapping("/user/register")
	public Object register(HttpSession session) {
		if(middleWare.isConnected(session))
		{

			return "redirect:/";
		}
		System.out.println("here_inscription");
		session.setAttribute("type_formation_headers", typeFormationService.findAll());

		return new ModelAndView("../pages/all/inscription");
	}

	public String generatePassword() {

		int leftLimit = 97; // letter 'a'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 10;
		Random random = new Random();
		StringBuilder buffer = new StringBuilder(targetStringLength);
		for (int i = 0; i < targetStringLength; i++) {
			int randomLimitedInt = leftLimit + (int) 
					(random.nextFloat() * (rightLimit - leftLimit + 1));
			buffer.append((char) randomLimitedInt);
		}
		String generatedString = buffer.toString();

		return generatedString;
	}

	@ResponseBody
	@RequestMapping(value="/user/register",method=RequestMethod.POST)
	public Object register(@RequestParam("nom") String nom,@RequestParam("prenom") String prenom,@RequestParam("email") String email,@RequestParam("adresse") String adresse,@RequestParam("tel") String tel,@RequestParam("role") int role,HttpSession session)
	{
		if(middleWare.isConnected(session))
		{
			return "redirect:/index";
		}
		session.setAttribute("type_formation_headers", typeFormationService.findAll());

		ModelAndView signUpPage= new ModelAndView("../pages/all/inscription");
		System.out.println("Nom : "+nom+" Email : "+email+" Prénom : "+prenom+" tel : "+tel+" Adresse : "+adresse+" Role : "+role);
		User user =null;
		try
		{
			user=userService.getUserByEmail(email);
			//Role myRole =roleService.getRoleById(role);
			//System.out.println("Role name : "+myRole.getName());
			if(user!=null)
			{
				System.out.println("errors");
				System.out.println("User adresse : "+user.getAdresse());
				signUpPage.addObject("errors","Adresse e-mail déjà utilisée");
				return signUpPage;
			}
			else
			{
				if(((role==3)||(role==2))||(role==5))
				{
					System.out.println("Register in progress");
					User newUser=new User();
					newUser.setNom(nom);
					newUser.setPrenom(prenom);
					newUser.setEmail(email);
					newUser.setAdresse(adresse);
					newUser.setNum_tel(tel);
					//newUser.setStatus(0);
					newUser.setStatus(1);
					String password=generatePassword();
					newUser.setPassword(password);
					/*Set<Role> roleList = new HashSet<Role>();
					roleList.add(roleService.getRoleById(role));
					newUser.setRoles(roleList);*/

					System.out.println("Role namee :"+roleService.getRoleById(role).getNom());
					newUser.setRole(roleService.getRoleById(role));
					User userSaved=userServiceCrud.saveAndFlush(newUser);

					System.out.println("User : "+userSaved.getId());

					if(role==3)
					{
						Client client = new Client();
						client.setUser(userSaved);
						client.setStatus(0);
						clientService.insertClient(client);
					}
					if(role==5)
					{
						Stagiaire newStagiaire = new Stagiaire();
						newStagiaire.setUser(userSaved);
						stagiaireService.save(newStagiaire);
					}

					System.out.println("Done");
					try {
						smtpMailSender.send(email, "Inscription EFCO-FORMATION", "Bonjour,<br/> Vous avez reussi à avoir un compte,Bienvenue sur notre plateforme Votre identifiant est : "+email+" votre mot de passe est  : "+password);
					} catch (MessagingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						//result=e.getMessage();
					}
					signUpPage.addObject("success","Inscription validée, veuillez consulter votre boîte email");
					return signUpPage;

				}
				else
				{

					signUpPage.addObject("errors","Veuillez vérifier vos données d'inscription");
					return signUpPage;
				}
			}

		}catch(Exception ex)
		{
			System.out.println("Exception : \n "+ex.getMessage());
			ex.printStackTrace();
			signUpPage.addObject("errors","Veuillez vérifier vos données");

			return signUpPage;
		}



	}



	//add stag to session formation
	@RequestMapping(value="/user/add_stag_to_session",method=RequestMethod.POST)
	public Object addStagToSession(HttpSession session,
			@RequestParam(value="stag_list_to_session",required=false) String stag_list_to_session,
			@RequestParam(value="new_stag_name",required=false) String new_stag_name,
			@RequestParam(value="new_stag_surname",required=false) String new_stag_surname,
			@RequestParam(value="new_stag_email",required=false) String new_stag_email,
			@RequestParam(value="new_stag_tel",required=false) String new_stag_tel,
			@RequestParam(value="genre",required=false) String genre,
			@RequestParam(value="new_stag_password",required=false) String new_stag_password,
			@RequestParam("session_formation_signup_id") long session_formation_signup_id)
	{
		if(!Middleware.isUser(session))
		{

			return "redirect:/index";
		}

		updateUser(session);

		User currentUser=(User)session.getAttribute("user");
		session.setAttribute("type_formation_headers", typeFormationService.findAll());

		ModelAndView planningPage=new ModelAndView("../pages/all/PlanningFormations");
		Optional<SessionFormation> checkSessionFormation=sessionFormationService.findById(session_formation_signup_id);

		if(!checkSessionFormation.isPresent())
		{
			session.setAttribute("errors","Veuillez vérifier la session séléctionnée");
			return "redirect:/planning";
		}

		if(checkSessionFormation.get().getDate_Deb().before(new Date()))
		{
			session.setAttribute("info","Session de formation expirée");
			return "redirect:/planning";
		}

		String name_list_added="";
		boolean done=false;
		List<Stagiaire> currentStagInSF=checkSessionFormation.get().getStagiaires();
		String[] stags_id=null;

		int cpt=0;
		int countNewStag=0;
		int countOldStag=0;

		if(stag_list_to_session!=null)
		{
			stags_id = stag_list_to_session.split(",");
		}

		if(stags_id!=null)
		{
			countOldStag+=stags_id.length;
		}


		if(new_stag_name!=null)
		{

			String[] list_new_stag_name = new_stag_name.split(",");
			String[] list_new_stag_surname = new_stag_surname.split(",");
			String[] list_new_stag_email = new_stag_email.split(",");
			String[] list_new_stag_tel = new_stag_tel.split(",");
			String[] list_new_stag_password = new_stag_password.split(",");
			String[] list_genre=genre.split(",");
			countNewStag=list_new_stag_email.length;
			System.out.println("New stage count : "+countNewStag);
			for(String new_name :list_new_stag_name)
			{
				User user=userService.getUserByEmail(list_new_stag_email[cpt]);
				//Role myRole =roleService.getRoleById(role);
				//System.out.println("Role name : "+myRole.getName());
				if(user!=null)
				{
					System.out.println("errors");
					System.out.println("User adresse : "+user.getAdresse());

					session.setAttribute("errors", "Email "+list_new_stag_email[cpt]+" Adresse e-mail déjà utilisée");
					return "redirect:/planning";
				}
				User newUser=new User();
				newUser.setNom(new_name);
				newUser.setPrenom(list_new_stag_surname[cpt]);
				newUser.setEmail(list_new_stag_email[cpt]);
				newUser.setAdresse("");
				newUser.setNum_tel(list_new_stag_tel[cpt]);
				newUser.setStatus(1);
				newUser.setGender(list_genre[cpt]);
				newUser.setRole(roleService.getRoleById(5));
				newUser.setPassword(list_new_stag_password[cpt]);
				User userSaved=userServiceCrud.saveAndFlush(newUser);
				Stagiaire newStagiaire = new Stagiaire();
				newStagiaire.setUser(userSaved);
				newStagiaire.setClient(currentUser.getClient());
				stagiaireService.saveAndFlush(newStagiaire);
				
				name_list_added+=userSaved.getNom()+" "+userSaved.getPrenom()+"<br/>";
				if((checkSessionFormation.get().getNbre_stagiaires())-(checkSessionFormation.get().getStagiaires().size())-(countOldStag)-(countNewStag)>0)
				{
					try {
						smtpMailSender.send(newStagiaire.getUser().getEmail(), "Pré-inscription EFCO-Formation", "Bonjour "+newStagiaire.getUser().getGender()+" "+newStagiaire.getUser().getNom()+",<br/>Nous avons le plaisir de vous confirmer votre pré-inscription à notre session de formation "+checkSessionFormation.get().getTitre()+", du "+checkSessionFormation.get().getDate_Deb()+" à "+checkSessionFormation.get().getDate_fin()+",à "+checkSessionFormation.get().getSallef().getAdresse()+", "+checkSessionFormation.get().getSallef().getLocalisation()+", "+checkSessionFormation.get().getSallef().getNom()+"<br/>Un de nos conseillers formation vous contactera très prochainement afin de finaliser votre inscription.<br/> En attendant nous restons à votre disposition pour de plus amples informations.<br/>Cordialement");
						
						currentStagInSF.add(newStagiaire);

					} catch (MessagingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			done=true;
		}
		

		int x=countOldStag+countNewStag;
		if((checkSessionFormation.get().getNbre_stagiaires())-(checkSessionFormation.get().getStagiaires().size())-x<0)
		{
			
			session.setAttribute("errors","Il n'y a plus de places libres pour vos "+x+" stagiaires !");
			return "redirect:/planning";
		}
		

		boolean stagExist=false;

		String names_list = "";
		if(stags_id!=null)
		{
			for(String id:stags_id)
			{
				Optional<Stagiaire> checkStagiaire = stagiaireService.findById(Long.valueOf(id));
				if(checkStagiaire.isPresent())
				{
					if(!currentUser.getClient().getList_stagiaires().contains(checkStagiaire.get()))
					{
						session.setAttribute("errors","Veuillez vérifier vos stagiaires séléctionné(e)s");
						return "redirect:/planning";
					}
					if(!currentStagInSF.contains(checkStagiaire.get()))
					{
						name_list_added+=checkStagiaire.get().getUser().getNom()+" "+checkStagiaire.get().getUser().getPrenom()+" / ";
						currentStagInSF.add(checkStagiaire.get());
						try {
							
							smtpMailSender.send(checkStagiaire.get().getUser().getEmail(), "Pré-inscription EFCO-Formation", "Bonjour "+checkStagiaire.get().getUser().getGender()+" "+checkStagiaire.get().getUser().getNom()+",<br/>Nous avons le plaisir de vous confirmer votre pré-inscription à notre session de formation "+checkSessionFormation.get().getTitre()+", du "+checkSessionFormation.get().getDate_Deb()+" à "+checkSessionFormation.get().getDate_fin()+",à "+checkSessionFormation.get().getSallef().getAdresse()+", "+checkSessionFormation.get().getSallef().getLocalisation()+", "+checkSessionFormation.get().getSallef().getNom()+"<br/>Un de nos conseillers formation vous contactera très prochainement afin de finaliser votre inscription.<br/>En attendant nous restons à votre disposition pour de plus amples informations.<br/>Cordialement");
							} catch (MessagingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						done=true;
					}
					else
					{
						stagExist=true;
						names_list+=checkStagiaire.get().getUser().getNom()+" "+checkStagiaire.get().getUser().getPrenom()+"<br/>";
					}


				}
				else
				{
					session.setAttribute("errors","Veuillez vérifier vos stagiaires séléctionné(e)s");
					return "redirect:/planning";

				}
			}
		}
		if(stagExist)
		{

			session.setAttribute("info", "Le(s) stagiaire(s)  : "+names_list+ " existe(ent) déja dans cette session ");

		}
		
		if(done)
		{
			try {
				smtpMailSender.send(currentUser.getEmail(), "EFCO-FORMATION", "Bonjour,<br/> Nous avons bien reçu votre demande de préinscription pour le(s) stagiaire(s) :"+name_list_added+"à la session de formation "+checkSessionFormation.get().getTitre()+" formation qui se déroulera du "+checkSessionFormation.get().getDate_Deb()+"à "+checkSessionFormation.get().getDate_fin()+" à  "+checkSessionFormation.get().getSallef().getNom()+","+checkSessionFormation.get().getSallef().getAdresse()+".\n<br/>" + 
						"Nous vous remercions de votre confiance et revenons vers vous pour valider la session de formation dans les plus brefs délais.\n<br/>" + 
						"Cordialement.");
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//result=e.getMessage();
			}
		}

		checkSessionFormation.get().setStagiaires(currentStagInSF);
		sessionFormationService.save(checkSessionFormation.get());
		session.setAttribute("success","Vos stagiaires ont été inscrits pour cette session de formation");
		return "redirect:/planning";

	}

	//CRUD stagiaire client	
	@RequestMapping("/user/stagiaire/restore/{id}")
	public Object restoreStagiaire( @PathVariable("id") long stagiaire_id,HttpSession session)
	{
		if(!Middleware.isConnected(session))
		{
			return "redirect:/user/login";

		}
		updateUser(session);
		User currentUser=(User)session.getAttribute("user");
		Optional<Stagiaire> stagiaire=stagiaireService.findById(stagiaire_id);
		if(stagiaire.isPresent())
		{
			if(currentUser.getClient().getList_stagiaires().contains(stagiaire.get()))
			{
				stagiaire.get().getUser().setStatus(1);
				stagiaireService.save(stagiaire.get());
				return "redirect:/user/profile";
			}
		}
		return "redirect:/user/profile";
	}

	@RequestMapping("/user/stagiaire/delete/{id}")
	public Object deleteStagiaire( @PathVariable("id") long stagiaire_id,HttpSession session)
	{
		if(!Middleware.isConnected(session))
		{
			return "redirect:/user/login";

		}
		updateUser(session);
		session.setAttribute("type_formation_headers", typeFormationService.findAll());

		ModelAndView ProfilePage=  new ModelAndView("../../../pages/Client/ProfilClient");
		User currentUser=(User)session.getAttribute("user");
		Optional<Stagiaire> stagiaire=stagiaireService.findById(stagiaire_id);
		if(stagiaire.isPresent())
		{
			if(currentUser.getClient().getList_stagiaires().contains(stagiaire.get()))
			{
				System.out.println("Here ya degla");
				System.out.println("lol :"+stagiaire.get().getUser().getEmail());
				//stagiaire.get().setStatus(0);
				//		stagiaireService.save(stagiaire.get());
				userServiceCrud.delete(stagiaire.get().getUser());
				//System.out.println("User id : "+currentUser.getId());
				userService.deleteStag(stagiaire_id);

				session.setAttribute("success", "Stagiaire supprimé avec succès");
				return "redirect:/user/profile"; 

			}
		}
		return "redirect:/user/profile";
	}


	@RequestMapping(value="user/stagiaire/add",method=RequestMethod.POST)
	public Object addStagUser(@RequestParam("adresse") String adresse,
			@RequestParam("num_tel") String num_tel,
			@RequestParam("nom") String nom,
			@RequestParam("prenom") String prenom,
			@RequestParam("email") String email,

			@RequestParam("password") String password,
			HttpSession session) throws MessagingException
	{

		if(Middleware.isUser(session))
		{
			updateUser(session);
			User currentUser=(User)session.getAttribute("user");
			System.out.println("Here");

			if(userService.getUserByEmail(email)!=null)
			{
				session.setAttribute("errors", "Adresse e-mail déjà utilisée");
				return "redirect:/user/profile";
			}

			User user = new User();
			user.setNom(nom);
			user.setPrenom(prenom);
			user.setPassword(password);
			user.setEmail(email);
			user.setAdresse(adresse);
			user.setNum_tel(num_tel);
			user.setStatus(1);



			user.setRole(roleService.getRoleById(5));

			userServiceCrud.save(user);

			Stagiaire x = new Stagiaire();
			x.setUser(user);
			x.setClient(currentUser.getClient());

			stagiaireService.save(x);

			System.out.println("Done");
			smtpMailSender.send(user.getEmail(), "Inscription EFCO-Formation", "Bonjour,<br/>Vous avez reussi à avoir un compte,Bienvenue sur notre plateforme Votre identifiant est : "+user.getEmail()+" Password : "+password);

			session.setAttribute("success", "Stagiaire ajouté avec succès");
			return "redirect:/user/profile";


		}

		return "redirect:/index";
	}

	@RequestMapping(value="user/stagiaire/edit",method=RequestMethod.POST)
	public Object editTypeFormation(@RequestParam("adresse") String adresse,
			@RequestParam("num_tel") String num_tel,
			@RequestParam("nom") String nom,
			@RequestParam("prenom") String prenom,
			@RequestParam("password") String password,
			@RequestParam("email") String email,

			@RequestParam("stagiaire_id_to_edit") long id,
			HttpSession session)
	{

		if(Middleware.isUser(session))
		{
			updateUser(session);
			User currentUser=(User)session.getAttribute("user");
			System.out.println("Here");
			Optional<Stagiaire> stagiaire=stagiaireService.findById(id);

			if(stagiaire.isPresent())
			{
				if(currentUser.getClient().getList_stagiaires().contains(stagiaire.get()))
				{
					stagiaire.get().getUser().setAdresse(adresse);
					stagiaire.get().getUser().setEmail(email);
					stagiaire.get().getUser().setNom(nom);
					stagiaire.get().getUser().setPrenom(prenom);

					stagiaire.get().getUser().setNum_tel(num_tel);
					if(password.length()!=0)
					{
						stagiaire.get().getUser().setPassword(password);
					}
					stagiaireService.save(stagiaire.get());

					System.out.println("Done");
					session.setAttribute("success", "Stagiaire modifié avec succès");
					return "redirect:/user/profile";
				}
				System.out.println("Done");
				return "redirect:/user/profile";
			}

			System.out.println("Done");
			return "redirect:/user/profile";
		}

		return "redirect:/index";
	}

	//Edit profile
	@RequestMapping(value="/user/profile/edit",method=RequestMethod.POST)
	public Object editProfile(

			@RequestParam("nom") String nom,
			@RequestParam("password") String password,
			@RequestParam("adresse") String adresse,
			@RequestParam("prenom") String prenom,
			@RequestParam("email") String email,
			@RequestParam("num_tel") String num_tel,
			@RequestParam("siren") Long siren,
			@RequestParam("siret") Long siret,
			@RequestParam("num_carte_bancaire") Long num_carte_bancaire,
			@RequestParam("assujetti_tva") int assujetti_tva,
			@RequestParam("rcs_rm") Long rcs_rm,
			@RequestParam("code_postal") Long code_postal,
			@RequestParam("pays") String pays,
			@RequestParam("ville") String ville,
			@RequestParam("maison_mere") String maison_mere,
			@RequestParam("type_tier") String type_tier,
			@RequestParam("forme_juridique") String forme_juridique,
			HttpSession session)	
	{
		if(Middleware.isUser(session))
		{
			updateUser(session);
			User currentUser = (User)session.getAttribute("user");
			Optional<User> searchedClient = userServiceCrud.findById(currentUser.getId());
			if(searchedClient.isPresent())
			{

				searchedClient.get().setAdresse(adresse);
				searchedClient.get().setEmail(email);
				searchedClient.get().setNum_tel(num_tel);
				searchedClient.get().setNom(nom);
				searchedClient.get().setPrenom(prenom);
				searchedClient.get().setPassword(password);

				userServiceCrud.saveAndFlush(searchedClient.get());

				Client client = searchedClient.get().getClient();

				if(assujetti_tva==1)
				{
					client.setAssujetti_TVA(true);
				}
				else
				{
					client.setAssujetti_TVA(false);
				}

				client.setCodePostal(code_postal);
				client.setFormejuridique(forme_juridique);
				client.setMaisonMere(maison_mere);
				client.setSiren(siren);
				client.setSiret(siret);
				client.setNumeroCompteBanc(num_carte_bancaire);
				client.setPays(pays);
				client.setVille(ville);
				client.setType_tier(type_tier);
				client.setRcs_rm(rcs_rm);
				clientServiceCrud.saveAndFlush(client);
				System.out.println("Done");
				session.setAttribute("success", "Profil modifié avec succès");
				return "redirect:/user/profile";
			}
		}
		return "redirect:/index";
	}

	//Stagiaire Profile 

	@RequestMapping("/user/session_stag_delete/{user_id_stag}/{session_formation_id}")
	public Object deleteStagFromSession(@PathVariable("user_id_stag") long stagiaire_id,
			@PathVariable("session_formation_id") long session_formation_id,
			HttpSession session)
	{
		if(Middleware.isUser(session))
		{
			Optional<User> user = userServiceCrud.findById(stagiaire_id);
			if(user.isPresent())
			{
				Optional<SessionFormation> sf=sessionFormationService.findById(session_formation_id);
				if(sf.isPresent())
				{
					sf.get().getStagiaires().remove(user.get().getStagiaire());
					sessionFormationService.saveAndFlush(sf.get());
					session.setAttribute("success", user.get().getNom()+" "+ user.get().getPrenom()+" a été bien supprimé(e) de la formation :"+sf.get().getTitre());
				}
				return "redirect:/planning";
			}
			return "redirect:/planning";
		}
		return "redirect:/planning";
	}


	@RequestMapping(value="/stagiaire/insign_to_client",method=RequestMethod.POST)
	public Object insignToClient(@RequestParam("nom_client") String nom_client,HttpSession session)
	{
		if(Middleware.isStagiare(session))
		{
			List<User> listUsers = userServiceCrud.findAll();
			User searchedUser=new User();
			boolean founded=false;
			for(User x : listUsers)
			{
				if((x.getNom().equals(nom_client))&&(x.getRole().getId()==3))
				{
					searchedUser=x;
					founded=true;
					break;
				}
			}

			if(founded)
			{
				User currentStag=(User)session.getAttribute("user");
				currentStag.getStagiaire().setClient(searchedUser.getClient());
				System.out.println("FOunded");
				userServiceCrud.save(currentStag);
				session.setAttribute("success", searchedUser.getNom()+" a été bien associé pour vous");
			}
			return "redirect:/stagiaire/profile";
		}
		return "redirect:/index";
	}
	@RequestMapping("/stagiaire/profile")
	public Object showProfileGtagiaire(HttpSession session) {
		System.out.println("Here profile");
		session.setAttribute("type_formation_headers", typeFormationService.findAll());

		if(session.getAttribute("user")!=null)
		{
			if(Middleware.isStagiare(session))
			{
				updateUser(session);
				ModelAndView ProfilePage=  new ModelAndView("../pages/stagiaire/profile");
				User user =(User)session.getAttribute("user");

				System.out.println("user name : "+user.getNom());
				ProfilePage.addObject("user",user);
				List<SessionFormation> listSessionFormation= sessionFormationService.findAll();
				List<SessionFormation> session_formation_current_stagiaire = new ArrayList<SessionFormation>();


				for(SessionFormation x : listSessionFormation)
				{	

					if(x.getStagiaires().contains(user.getStagiaire()))
					{
						session_formation_current_stagiaire.add(x);

					}
				}



				List<SessionFormation> formationList=sessionFormationService.findAll();
				System.out.println("Size from controller :"+formationList.size() );
				List<Event> events = new ArrayList<Event>();
				Map<String,String> eventsList = new HashMap<String,String>();
				for(SessionFormation sf : session_formation_current_stagiaire)
				{
					for(SessionFormationTime sft:sf.getSession_formation_time())
					{
						Event event = new Event();
						event.setId(sf.getId());
						event.setTitle(sf.getTitre());
						event.setStart(sft.getDate_deb().toString());
						event.setEnd(sft.getDate_fin().toString());
						event.setDuree(Integer.toString(sf.getDuree()));
						event.setLink(sf.getLink());
						event.setLocalisation(sf.getSallef().getLocalisation());
						event.setDescription(sf.getFormation().getDescription());
						event.setSalle(sf.getSallef().getNom());
						event.setAdresse(sf.getSallef().getAdresse());
						
						if(sf.getDate_Deb().before(new Date()))
						{
							event.setExpired(1);
						}
						Color color=new Color((int)(Math.random() * 0x1000000));
						String eventColor=String.format("#%02x%02x%02x", color.getRed(),color.getGreen() , color.getBlue());

						while(eventsList.containsValue(eventColor))
						{
							color=new Color((int)(Math.random() * 0x1000000));
							eventColor=String.format("#%02x%02x%02x", color.getRed(),color.getGreen() , color.getBlue());

							System.out.println("Color code : "+ eventColor);	
						}
						event.setColor(eventColor);


						System.out.println("Place restantes : "+(sf.getNbre_stagiaires()-sf.getStagiaires().size()));
						event.setFree_stagiare_number(sf.getNbre_stagiaires()-sf.getStagiaires().size());
						if(eventsList.get(event.getTitle())!=null)
						{
							event.setColor(eventsList.get((event.getTitle())));
						}
						events.add(event);
						eventsList.put(event.getTitle(),event.getColor());
						System.out.println("Event : "+event.toString());
					}
				}

				ObjectMapper mapper = new ObjectMapper();
				String jsonMsg = null;
				try {
					jsonMsg =  mapper.writerWithDefaultPrettyPrinter().writeValueAsString(events);
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


				ProfilePage.addObject("list_formation",session_formation_current_stagiaire);

				ProfilePage.addObject("events",jsonMsg);
				return ProfilePage;
			}
		}
		else
		{
			return "redirect:/user/login";
		}
		return "redirect:/index";

	}

	@RequestMapping(value="/stagiaire/profile/edit",method=RequestMethod.POST)
	public Object editStagiaire(@RequestParam("password") String password,@RequestParam("adresse") String adresse,@RequestParam("nom") String nom,@RequestParam("prenom") String prenom,@RequestParam("email") String email,@RequestParam("num_tel") String num_tel,HttpSession session)
	{
		if(Middleware.isStagiare(session))
		{
			User user = (User)session.getAttribute("user");
			Optional<User> searched = userServiceCrud.findById(user.getId());
			if(searched.isPresent())
			{
				if(searched.get().getRole().getId()==5)
				{
					searched.get().setAdresse(adresse);
					searched.get().setEmail(email);
					searched.get().setNom(nom);
					searched.get().setPassword(password);
					searched.get().setEmail(email);
					searched.get().setNum_tel(num_tel);
					searched.get().setPrenom(prenom);
					if(password.length()!=0)
					{
						searched.get().setPassword(password);
					}

					userServiceCrud.saveAndFlush(searched.get());
					System.out.println("Done ");
					session.setAttribute("success", "Profil modifié avec succès");
					return "redirect:/stagiaire/profile"; 

				}

				return "redirect:/stagiaire/profile"; 
			}

			return "redirect:/stagiaire/profile"; 
		}
		return "redirect:/index";
	}


	@RequestMapping("/llogout")
	public Object logout(HttpSession session)
	{
		session.removeAttribute("user");

		System.out.println("OK");
		return "redirect:/index";
	}
}
