package com.efco.formation.controllers;


import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.efco.formation.config.Middleware;
import com.efco.formation.config.SmtpMailSender;
import com.efco.formation.model.Actualite;
import com.efco.formation.model.Client;
import com.efco.formation.model.Devis;
import com.efco.formation.model.Event;
import com.efco.formation.model.Facture;
import com.efco.formation.model.Formation;
import com.efco.formation.model.Role;
import com.efco.formation.model.Salle;
import com.efco.formation.model.SessionFormation;
import com.efco.formation.model.SessionFormationTime;
import com.efco.formation.model.SousTypeFormation;
import com.efco.formation.model.Stagiaire;
import com.efco.formation.model.TypeFormation;
import com.efco.formation.model.User;
import com.efco.formation.service.ActualiteService;
import com.efco.formation.service.ClientService;
import com.efco.formation.service.ClientServiceCrud;
import com.efco.formation.service.DevisService;
import com.efco.formation.service.FactureService;
import com.efco.formation.service.FormationService;
import com.efco.formation.service.RoleService;
import com.efco.formation.service.SalleService;
import com.efco.formation.service.SessionFormationService;
import com.efco.formation.service.SousTypeFormationServiceCrud;
import com.efco.formation.service.StagiareService;
import com.efco.formation.service.TypeFormationService;
import com.efco.formation.service.UserService;
import com.efco.formation.service.UserServiceCrud;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class AdminController {
	@Autowired
	UserService userService;

	@Autowired
	RoleService roleService;

	@Autowired
	private SmtpMailSender smtpMailSender;

	//  public JavaMailSender emailSender;

	@Autowired
	TypeFormationService typeFormationService;

	@Autowired
	FormationService formationService;

	@Autowired
	SessionFormationService sessionFormationService;

	@Autowired
	SousTypeFormationServiceCrud sousTypeFormationService;

	@Autowired
	private HttpServletRequest request;


	private static String UPLOADED_FOLDER = "F://temp//";

	@Autowired
	UserServiceCrud userServiceCrud;

	@Autowired
	ActualiteService actualiteService;

	@Autowired
	ClientService clientService;

	@Autowired
	ClientServiceCrud clientServiceCrud;

	@Autowired
	DevisService devisService;

	@Autowired
	FactureService factureService;


	@Autowired
	StagiareService stagiaireService;

	@Autowired
	SalleService salleService;

	@RequestMapping("/admin/login")
	public ModelAndView login() {

		System.out.println("Here_connexion");
		return new ModelAndView("../pages/admin/Login");
	}




	@RequestMapping("/admin/dashboard")
	public Object showAdminDashboard(HttpSession session) {

		if((Middleware.isAdmin(session))||(Middleware.isSalari(session)))
		{
			ModelAndView adminPage=new ModelAndView("../pages/admin/indexAdmin");
			System.out.println("Lol");
			int client_count=0,former_count=0,stagiaires_count=0;
			int j = 0,f= 0,mar= 0,avr= 0,mai= 0,jun= 0,ju= 0,aou= 0,sep= 0,oct= 0,nov= 0,dec= 0;
			for(User user : userServiceCrud.findAll())
			{
				System.out.println("User month : "+user.getCreated_at().getMonth());
				switch(user.getCreated_at().getMonth()+1) 
				{
				case 1 : j++;break;
				case 2 : f++;break;
				case 3 : mar++;break;
				case 4 : avr++;break;
				case 5 : mai++;break;
				case 6 : jun++;break;
				case 7 : ju++;break;
				case 8 : aou++;break;
				case 9 : sep++;break;
				case 10 : oct++;break;
				case 11 : nov++;break;
				case 12 : dec++;break;
				}
				switch (user.getRole().getId())
				{
				case 2 : former_count++;break;
				case 3 : client_count++;break;
				case 5 : stagiaires_count++;break;
				}

			}
			int session_formation_count=sessionFormationService.findAll().size();
			adminPage.addObject("user",(User)session.getAttribute("user"));
			adminPage.addObject("client_count",client_count);
			adminPage.addObject("former_count",former_count);
			adminPage.addObject("stagiaires_count", stagiaires_count);
			adminPage.addObject("session_formation_count",session_formation_count);
			adminPage.addObject("salle_count", salleService.count());
			adminPage.addObject("actualite_count",actualiteService.count());
			String users_per_month=j+","+f+","+mar+","+avr+","+mai+","+jun+","+ju+","+aou+","+sep+","+oct+","+nov+","+dec+",";
			adminPage.addObject("users_per_month",users_per_month);
			adminPage.addObject("devis_count",devisService.count());
			return adminPage;
		}

		return "redirect:/index";


	}



	@RequestMapping(value="/admin/login",method=RequestMethod.POST)
	public Object login(@RequestParam("email") String email,@RequestParam("password") String password,HttpSession session)
	{
		if(Middleware.isAdmin(session))
		{
			return "redirect:/admin/dashboard";
		}

		System.out.println("Email : "+email+" Password : "+password);

		ModelAndView loginPage= new ModelAndView("../pages/admin/Login");

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

				if((user.getRole().getId()==1)||(user.getRole().getId()==4))
				{
					session.setAttribute("user", user);
					session.setAttribute("admin", "1");
					return "redirect:/admin/dashboard";
				}
				else
				{
					loginPage.addObject("errors","Veuillez vérifier vos données");
					return loginPage;
				}

				//	System.out.println("Nom : "+user.getNom()+" Prénom : "+user.getPrenom());

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
	}

	//      Gestion utilisateurs 

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


	@RequestMapping("/admin/user/cancel/{id}")
	public Object cancelUser( @PathVariable("id") long user_id,HttpSession session)
	{
		if((!Middleware.isAdmin(session))&&(!Middleware.isSalari(session)))
		{
			return "redirect:/";
		}

		String password =generatePassword();
		Optional<User> searchUser = userServiceCrud.findById(user_id);
		System.out.println("activate the user by sending him and email with a new password");
		if(searchUser.isPresent())
		{

			searchUser.get().setPassword(password);
			searchUser.get().setStatus(-2);

			try {
				smtpMailSender.send(searchUser.get().getEmail(), "Inscription EFCO-FORMATION", "Bonjour,<br/> Vous n'avez pas reussi à avoir un compte");
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//result=e.getMessage();

			}

			userServiceCrud.saveAndFlush(searchUser.get());
			System.out.println("Done");
			if(searchUser.get().getRole().getId()==2)
			{
				return "redirect:/admin/formateurs";
			}
			else if(searchUser.get().getRole().getId()==3)
			{
				return "redirect:/admin/clients";
			}
			else
			{
				return "redirect:/admin/dashboard";
			}

		}

		return "redirect:/admin/dashboard";

	}


	@RequestMapping("/admin/user/verify/{id}")
	public Object verifyUser( @PathVariable("id") long user_id,HttpSession session)
	{
		if((!Middleware.isAdmin(session))&&(!Middleware.isSalari(session)))
		{
			return "redirect:/";
		}

		String password =generatePassword();
		Optional<User> searchUser = userServiceCrud.findById(user_id);
		System.out.println("activate the user by sending him and email with a new password");
		if(searchUser.isPresent())
		{
			if(searchUser.get().getStatus()!=1)
			{
				searchUser.get().setPassword(password);
				searchUser.get().setStatus(1);

				try {
					smtpMailSender.send(searchUser.get().getEmail(), "Inscription EFCO-FORMATION", "Bonjour,<br/> Vous avez reussi à avoir un compte,Bienvenue sur notre plateforme Votre identifiant est : "+searchUser.get().getEmail()+" votre mot de passe est  : "+password);
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					//result=e.getMessage();

				}

				userServiceCrud.saveAndFlush(searchUser.get());
				session.setAttribute("success", "Utilisateur vérifié avec succès");
				System.out.println("OK");
				System.out.println("Done");

			}
			if(searchUser.get().getRole().getId()==2)
			{
				return "redirect:/admin/formateurs";
			}
			else if(searchUser.get().getRole().getId()==3)
			{
				return "redirect:/admin/clients";
			}
			else if(searchUser.get().getRole().getId()==5)
			{
				return "redirect:/admin/stagiaires";
			}
			else
			{
				return "redirect:/admin/dashboard";
			}

		}

		return "redirect:/admin/dashboard";

	}

	@RequestMapping("/admin/user/delete/{id}")
	public Object deleteUser( @PathVariable("id") long user_id,HttpSession session)
	{

		if((!Middleware.isAdmin(session))&&(!Middleware.isSalari(session)))
		{
			return "redirect:/";
		}

		Optional<User> searchUser = userServiceCrud.findById(user_id);
		if(searchUser.isPresent())
		{

			searchUser.get().setStatus(0);

			//	userServiceCrud.saveAndFlush(searchUser.get());
			userServiceCrud.delete(searchUser.get());
			if(searchUser.get().getRole().getId()==2)
			{
				return "redirect:/admin/formateurs";
			}
			else if(searchUser.get().getRole().getId()==3)
			{
				return "redirect:/admin/clients";
			}	
			if(searchUser.get().getRole().getId()==4)
			{
				return "redirect:/admin/salaries";
			}
			if(searchUser.get().getRole().getId()==5)
			{
				return "redirect:/admin/stagiaires";
			}
			session.setAttribute("success", "Utilisateur supprimé avec succès");
		}

		return "redirect:/admin/dashboard";

	}

	@RequestMapping("/admin/user/restore/{id}")
	public Object restoreUser( @PathVariable("id") long user_id,HttpSession session)
	{

		if((!Middleware.isAdmin(session))&&(!Middleware.isSalari(session)))
		{
			return "redirect:/";
		}

		Optional<User> searchUser = userServiceCrud.findById(user_id);
		if(searchUser.isPresent())
		{

			searchUser.get().setStatus(1);

			userServiceCrud.saveAndFlush(searchUser.get());
			if(searchUser.get().getRole().getId()==2)
			{
				return "redirect:/admin/formateurs";
			}
			if(searchUser.get().getRole().getId()==3)
			{
				return "redirect:/admin/clients";
			}	
			if(searchUser.get().getRole().getId()==4)
			{
				return "redirect:/admin/salaries";
			}

		}

		return "redirect:/admin/dashboard";

	}

	//Crud type formation

	@RequestMapping("/admin/type_formation/restore/{id}")
	public Object restoreTypeFormation( @PathVariable("id") long type_formation_id,HttpSession session)
	{
		if((!Middleware.isAdmin(session))&(!Middleware.isSalari(session)))
		{
			return "redirect:/";
		}
		Optional<TypeFormation> searched=typeFormationService.findById(type_formation_id);
		if(searched.isPresent())
		{
			searched.get().setStatus(1);
			typeFormationService.save(searched.get());
			//typeFormation.delete(searched.get());
		}



		return "redirect:/admin/type_formation";
	}

	@RequestMapping("/admin/type_formation/delete/{id}")
	public Object deleteTypeFormation( @PathVariable("id") long type_formation_id,HttpSession session)
	{
		if((!Middleware.isAdmin(session))&(!Middleware.isSalari(session)))
		{
			return "redirect:/";
		}
		Optional<TypeFormation> searched=typeFormationService.findById(type_formation_id);
		if(searched.isPresent())
		{
			searched.get().setStatus(0);
			//	typeFormationService.save(searched.get());
			typeFormationService.delete(searched.get());
		}



		return "redirect:/admin/type_formation";
	}

	@RequestMapping("/admin/formations/add")
	public Object showAddFormation(HttpSession session)
	{

		if((Middleware.isAdmin(session))||(Middleware.isSalari(session)))
		{
			ModelAndView addFormation=new ModelAndView("../pages/admin/AddFormation");

			return addFormation;
		}
		return "redirect:/index";
	}


	@RequestMapping("/admin/type_formation")
	public Object showTypeFormation(HttpSession session)
	{

		if((Middleware.isAdmin(session))||(Middleware.isSalari(session)))
		{
			ModelAndView showTypeFormations=new ModelAndView("../pages/admin/AfficherTypeFormation");
			showTypeFormations.addObject("type_formation_list",typeFormationService.findAll());
			return showTypeFormations;
		}

		return "redirect:/index";
	}

	@RequestMapping(value="admin/type_formation/add",method=RequestMethod.POST)
	public Object addTypeFormation(@RequestParam("titre") String titre,@RequestParam("description") String description,HttpSession session)
	{
		System.out.println("passed from here");

		if((Middleware.isAdmin(session))||(Middleware.isSalari(session)))
		{
			System.out.println("Here");
			TypeFormation newTypeFormation=new TypeFormation();
			newTypeFormation.setStatus(1);
			newTypeFormation.setTitre(titre);
			newTypeFormation.setDescription(description);
			typeFormationService.saveAndFlush(newTypeFormation);
			System.out.println("Done");
			session.setAttribute("success", "Type de formation ajouté avec succès");
			return "redirect:/admin/type_formation";
		}

		return "redirect:/index";
	}

	@RequestMapping(value="admin/type_formation/edit",method=RequestMethod.POST)
	public Object editTypeFormation(@RequestParam("titre") String titre,@RequestParam("description") String description,@RequestParam("type_formation_id") long id,HttpSession session)
	{
		System.out.println("passed from here");

		if((Middleware.isAdmin(session))||(Middleware.isSalari(session)))
		{
			System.out.println("Here");
			Optional<TypeFormation> typeFormationToEdit=typeFormationService.findById(id);
			if(typeFormationToEdit.isPresent())
			{
				typeFormationToEdit.get().setTitre(titre);
				typeFormationToEdit.get().setDescription(description);

				typeFormationService.saveAndFlush(typeFormationToEdit.get());
				session.setAttribute("success", "Type de formation modifié avec succès");
			}

			System.out.println("Done");
			return "redirect:/admin/type_formation";
		}

		return "redirect:/index";
	}

	//Sous type de formation CRUD

	@RequestMapping("/admin/sous_types_formation")
	public Object showSousTypesDeFormation(HttpSession session)
	{
		if((!Middleware.isAdmin(session))&(!Middleware.isSalari(session)))
		{
			return "redirect:/";
		}

		List<SousTypeFormation> listSousTypesFormation=sousTypeFormationService.findAll();
		ModelAndView sousTypeFormationPage= new ModelAndView("../pages/admin/sousTypeFormationAll");
		sousTypeFormationPage.addObject("list_sous_type_formation",listSousTypesFormation);
		sousTypeFormationPage.addObject("type_formation_list",typeFormationService.findAll());
		return sousTypeFormationPage;

	}

	@RequestMapping("/admin/sous_type_formation/delete/{id}")
	public Object deleteSousTypeFormation(@PathVariable("id") long sous_type_formation,HttpSession session)
	{
		if((!Middleware.isAdmin(session))&(!Middleware.isSalari(session)))
		{
			return "redirect:/";
		}
		Optional<SousTypeFormation> search=sousTypeFormationService.findById(sous_type_formation);
		if(search.isPresent())
		{
			for(Formation x :formationService.findAll())
			{
				if(x.getSous_type().getId()==search.get().getId())
				{
					formationService.delete(x);
				}
			}
			sousTypeFormationService.delete(search.get());
			session.setAttribute("success", "Sous type de formation supprimé avec succès");
		}
		return "redirect:/admin/sous_types_formation";
	}

	@RequestMapping("/admin/type_formation/under_type_formation/{id}")
	public Object showSousTypeFormation( @PathVariable("id") long type_formation_id,HttpSession session)
	{
		if((!Middleware.isAdmin(session))&(!Middleware.isSalari(session)))
		{
			return "redirect:/";
		}
		Optional<TypeFormation> searched=typeFormationService.findById(type_formation_id);
		ModelAndView sousTypeFormationPage= new ModelAndView("../../../pages/admin/sousTypeFormation");

		if(searched.isPresent())
		{
			sousTypeFormationPage.addObject("type_formation",searched.get());
			sousTypeFormationPage.addObject("type_formation_list",typeFormationService.findAll());
			sousTypeFormationPage.addObject("sous_type_formation",searched.get().getSous_type_formations());
			return sousTypeFormationPage;

			//typeFormation.delete(searched.get());
		}

		return "redirect:/admin/type_formation";
	}

	@RequestMapping(value="admin/sous_type_formation/edit",method=RequestMethod.POST)
	public Object editSousTypeFormation(@RequestParam("type_formation") long type_formation,@RequestParam("titre") String titre,@RequestParam("sous_type_formation_id") long id,@RequestParam("image") MultipartFile file,@RequestParam("description") String description,HttpSession session)
	{
		if((!Middleware.isAdmin(session))&(!Middleware.isSalari(session)))
		{
			return "redirect:/";
		}
		Optional<SousTypeFormation> search=sousTypeFormationService.findById(id);

		Optional<TypeFormation> searchTF=typeFormationService.findById(type_formation);

		if(!searchTF.isPresent())
		{
			session.setAttribute("errors", "Veuillez vérifier le type de formation");
			return "redirect:/admin/sous_types_formation";
		}

		if(search.isPresent())
		{
			if (!file.isEmpty()) {

				try {
					String uploadsDir = "/uploads/";
					String realPathtoUploads =  request.getServletContext().getRealPath(uploadsDir);

					if(! new File(realPathtoUploads).exists())
					{
						new File(realPathtoUploads).mkdir();
					}

					System.out.println("realPathtoUploads = "+ realPathtoUploads);
					String[] fileFrags = file.getOriginalFilename().split("\\.");
					String extension = fileFrags[fileFrags.length-1];
					String newFileName=generatePassword()+"."+extension;
					String filePath = realPathtoUploads + newFileName;
					search.get().setImage(uploadsDir+newFileName);
					File dest = new File(filePath);
					file.transferTo(dest);

					byte[] bytes = file.getBytes();
					Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
					Files.write(path, bytes);

					//Save the uploaded file to this folder


				}catch(Exception ex)
				{
					System.out.println("Exception : "+ex.getMessage());
				}
			}
			search.get().setTitre(titre);
			search.get().setDescription(description);
			search.get().setTypeFormaton(searchTF.get());
			sousTypeFormationService.saveAndFlush(search.get());
			session.setAttribute("success", "Sous type de formation modifié avec succès");
		}
		return "redirect:/admin/sous_types_formation";
	}


	@RequestMapping(value="admin/sous_type_formation/add",method=RequestMethod.POST)
	public Object addSousTypeFormation(@RequestParam("type_formation") long type_formation,@RequestParam("titre") String titre,@RequestParam("image") MultipartFile file,@RequestParam("description") String description,HttpSession session)
	{
		if((!Middleware.isAdmin(session))&(!Middleware.isSalari(session)))
		{
			return "redirect:/";
		}
		Optional<TypeFormation> search=typeFormationService.findById(type_formation);
		SousTypeFormation sousTypeFormation = new SousTypeFormation();
		if(search.isPresent())
		{
			sousTypeFormation.setTypeFormaton(search.get());
		}
		else
		{
			session.setAttribute("errors", "Veuillez vérifier le type de formation");
			return "redirect:/admin/sous_types_formation";
		}


		if (!file.isEmpty()) {

			try {
				String uploadsDir = "/uploads/";
				String realPathtoUploads =  request.getServletContext().getRealPath(uploadsDir);

				if(! new File(realPathtoUploads).exists())
				{
					new File(realPathtoUploads).mkdir();
				}

				System.out.println("realPathtoUploads = "+ realPathtoUploads);
				String[] fileFrags = file.getOriginalFilename().split("\\.");
				String extension = fileFrags[fileFrags.length-1];
				String newFileName=generatePassword()+"."+extension;
				String filePath = realPathtoUploads + newFileName;
				sousTypeFormation.setImage(uploadsDir+newFileName);
				File dest = new File(filePath);
				file.transferTo(dest);

				byte[] bytes = file.getBytes();
				Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
				Files.write(path, bytes);

				//Save the uploaded file to this folder


			}catch(Exception ex)
			{
				System.out.println("Exception : "+ex.getMessage());
			}
		}
		else
		{
			sousTypeFormation.setImage(null);
		}

		sousTypeFormation.setTitre(titre);
		sousTypeFormation.setDescription(description);
		sousTypeFormation.setStatus(1);
		sousTypeFormationService.saveAndFlush(sousTypeFormation);
		session.setAttribute("success", "Sous type de formation ajouté avec succès");

		return "redirect:/admin/sous_types_formation";
	}


	//Formation CRUD

	@RequestMapping(value="/admin/add_formation",method=RequestMethod.POST)
	public Object addFormation(@RequestParam("sous_type_formation") Long sous_type_formation,@RequestParam("image") MultipartFile file,@RequestParam("titre") String titre,@RequestParam("description") String description,HttpSession session)
	{

		if((Middleware.isAdmin(session))||(Middleware.isSalari(session)))
		{
			Formation newFormation=new Formation();
			newFormation.setStatus(1);
			newFormation.setTitre(titre);
			newFormation.setDescription(description);
			if (file.isEmpty()) {
				newFormation.setImage("");
				//return "redirect:uploadStatus";
			}
			else
			{
				try {
					String uploadsDir = "/uploads/";
					String realPathtoUploads =  request.getServletContext().getRealPath(uploadsDir);

					if(! new File(realPathtoUploads).exists())
					{
						new File(realPathtoUploads).mkdir();
					}

					System.out.println("realPathtoUploads = "+ realPathtoUploads);
					String[] fileFrags = file.getOriginalFilename().split("\\.");
					String extension = fileFrags[fileFrags.length-1];
					String newFileName=generatePassword()+"."+extension;
					String filePath = realPathtoUploads + newFileName;
					newFormation.setImage(uploadsDir+newFileName);
					File dest = new File(filePath);
					file.transferTo(dest);

					byte[] bytes = file.getBytes();
					Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
					Files.write(path, bytes);

					//Save the uploaded file to this folder


				}catch(Exception ex)
				{
					System.out.println("Exception : "+ex.getMessage());
				}
			}
			Optional<SousTypeFormation> searchSousTypeFormation = sousTypeFormationService.findById(sous_type_formation);
			if(searchSousTypeFormation.isPresent())
			{
				newFormation.setSous_type(searchSousTypeFormation.get());
			}
			else
			{
				session.setAttribute("errors", "Veuillez vérifier le sous type de formation selectionnée");
				return "redirect:/admin/formations";
			}

			formationService.saveAndFlush(newFormation);
			System.out.println("Done");
			session.setAttribute("success", "Formation ajoutée avec succès");
			return "redirect:/admin/formations";
		}
		return "redirect:/index";
	}

	@RequestMapping("/admin/formations")
	public Object showFormations(HttpSession session)
	{
		System.out.println("Passed from here");
		if((Middleware.isAdmin(session))||(Middleware.isSalari(session)))
		{
			ModelAndView showFormations=new ModelAndView("../pages/admin/AfficherFormation");
			showFormations.addObject("formation_list",formationService.findAll());
			showFormations.addObject("sous_formation_type_list",sousTypeFormationService.findAll());
			showFormations.addObject("formation_type_list",typeFormationService.findAll());
			return showFormations;
		}
		return "redirect:/admin/formations";
	}

	@RequestMapping("/admin/formation/delete/{id}")
	public Object deleteFormation( @PathVariable("id") long type_formation_id,HttpSession session)
	{
		if((Middleware.isAdmin(session))||(Middleware.isSalari(session)))
		{
			Optional<Formation> searched=formationService.findById(type_formation_id);
			if(searched.isPresent())
			{
				searched.get().setStatus(0);
				formationService.delete(searched.get());
				//	formationService.save(searched.get());
			}

			return "redirect:/admin/formations";
		}
		return "redirect:/index";
	}


	@RequestMapping("/admin/formation/restore/{id}")
	public Object restoreFormation( @PathVariable("id") long type_formation_id,HttpSession session)
	{
		if((Middleware.isAdmin(session))||(Middleware.isSalari(session)))
		{
			Optional<Formation> searched=formationService.findById(type_formation_id);
			if(searched.isPresent())
			{
				searched.get().setStatus(1);
				formationService.save(searched.get());
			}

			return "redirect:/admin/formations";
		}
		return "redirect:/index";
	}

	@RequestMapping(value="admin/formation/edit",method=RequestMethod.POST)
	public Object editFormation(@RequestParam("sous_type_formation") Long sous_type_formation,@RequestParam("image") MultipartFile file,@RequestParam("titre") String titre,@RequestParam("desc") String desc,@RequestParam("type_formation_id") long id,HttpSession session)
	{
		System.out.println("passed from here");

		if((Middleware.isAdmin(session))||(Middleware.isSalari(session)))
		{
			System.out.println("Here");
			Optional<Formation> formationToEdit=formationService.findById(id);
			if(formationToEdit.isPresent())
			{
				formationToEdit.get().setTitre(titre);
				formationToEdit.get().setDescription(desc);
				if (!file.isEmpty()) {
					try {
						String uploadsDir = "/uploads/";
						String realPathtoUploads =  request.getServletContext().getRealPath(uploadsDir);

						if(! new File(realPathtoUploads).exists())
						{
							new File(realPathtoUploads).mkdir();
						}

						System.out.println("realPathtoUploads = "+ realPathtoUploads);
						String[] fileFrags = file.getOriginalFilename().split("\\.");
						String extension = fileFrags[fileFrags.length-1];
						String newFileName=generatePassword()+"."+extension;
						String filePath = realPathtoUploads + newFileName;
						formationToEdit.get().setImage(uploadsDir+newFileName);
						File dest = new File(filePath);
						file.transferTo(dest);

					}catch(Exception ex)
					{
						System.out.println("Exception : "+ex.getMessage());
					}

				}
				Optional<SousTypeFormation> searchSousTypeFormation = sousTypeFormationService.findById(sous_type_formation);
				if(searchSousTypeFormation.isPresent())
				{
					formationToEdit.get().setSous_type(searchSousTypeFormation.get());
				}
				else
				{
					session.setAttribute("errors", "Veuillez vérifier le sous type de formation selectionnée");
					return "redirect:/admin/formations";
				}
				formationService.saveAndFlush(formationToEdit.get());
				System.out.println("Done edit formation");
				return "redirect:/admin/formations";
			}

			return "redirect:/admin/formations";

		}

		return "redirect:/index";
	}

	//Session formation CRUD

	@RequestMapping(value="/admin/add_session_formation",method=RequestMethod.POST)
	public  Object addSessionFormation(
			@RequestParam("titre") String titre,
			@RequestParam("date_debut") String date_deb,
			@RequestParam("date_fin") String date_fin,
			@RequestParam("heures_deb") String heures_deb,
			@RequestParam("nbr_stag") int nbr_stag,
			@RequestParam(value="link",required=false) String link,
			@RequestParam("salle_id") long salle_id,
			@RequestParam("date_to_inset_data") String date_to_inset_data,
			@RequestParam("formateur_id") Long formateur_id,
			@RequestParam("formation_id") Long formation_id,
			@RequestParam("heures_fin") String heures_fin,
			@RequestParam("duree") int duree,
			HttpSession session) throws ParseException
	{

		System.out.println("Heures fin list : "+heures_fin);
		session.removeAttribute("errors_session_formation");
		if((Middleware.isAdmin(session)||(Middleware.isSalari(session))))
		{

			SessionFormation newSessionFormation=new SessionFormation();
			newSessionFormation.setStatus(1);
			newSessionFormation.setTitre(titre);

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			newSessionFormation.setDate_Deb(formatter.parse(date_deb));
			newSessionFormation.setDate_fin(formatter.parse(date_fin));
			newSessionFormation.setDuree(duree);
			if(link!=null)
			{
				newSessionFormation.setLink(link);
			}
			List<Date> allSessionFormatonTime = new ArrayList<Date>();
			Salle salle = salleService.getOne(salle_id);

			for(SessionFormation x : salle.getSession_formations())
			{
				for(SessionFormationTime y : x.getSession_formation_time())
				{
					allSessionFormatonTime.add(y.getDate_deb());
					allSessionFormatonTime.add(y.getDate_fin());
				}
			}

			//salle.setDisponibilite(0);
			salleService.save(salle);
			newSessionFormation.setSallef(salleService.getOne(salle_id));
			newSessionFormation.setFormateur(userServiceCrud.getOne(formateur_id));
			newSessionFormation.setFormation(formationService.getOne(formation_id));
			newSessionFormation.setNbre_stagiaires(nbr_stag);
			sessionFormationService.save(newSessionFormation);
			//newSessionFormation.setDescription(description);

			SessionFormation x =sessionFormationService.saveAndFlush(newSessionFormation);
			List<SessionFormationTime> list_time_session_formation = new ArrayList<SessionFormationTime>();

			System.out.println("Liste des dates choisi : ");

			String[] heures_deb_array = heures_deb.split(",");
			String[] heures_fin_array = heures_fin.split(",");
			String[] dates_array=date_to_inset_data.split(",");
			for(String l : heures_deb_array)
			{
				System.out.println("Date debut : "+dates_array);
			}

			DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			int i=0;

			for(String heure_deb_aux : heures_deb_array)
			{
				SessionFormationTime sft = new SessionFormationTime();
				String dateDebutString= date_deb+heure_deb_aux;
				System.out.println("Parsed Date debut : "+dateDebutString);


				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Calendar c = Calendar.getInstance();
				try{
					//Setting the date to the given date
					c.setTime(sdf.parse(date_deb));
				}catch(ParseException e){
					e.printStackTrace();
				}

				//Number of Days to add
				c.add(Calendar.DAY_OF_MONTH, i);  
				//Date after adding the days to the given date
				String newDate = sdf.format(c.getTime());  
				Date date_deb_toInsert=(format.parse(dates_array[i]+" "+heure_deb_aux));
				Date date_fin_toInsert=(format.parse(dates_array[i]+" "+heures_fin_array[i]));
				sft.setDate_deb(date_deb_toInsert);
				sft.setDate_fin(date_fin_toInsert);

				if(allSessionFormatonTime.contains(date_deb_toInsert))
				{
					sessionFormationService.delete(x);
					session.setAttribute("errors","Salle non disponbile pour le :"+format.parse(newDate+" "+heure_deb_aux));

					return "redirect:/admin/session_formations";
				}

				if(allSessionFormatonTime.contains(date_fin_toInsert))
				{
					sessionFormationService.delete(x);
					session.setAttribute("errors","Salle non disponbile pour le :"+format.parse(newDate+" "+heures_fin_array[i]));
					return "redirect:/admin/session_formations";
				}

				if(!testDateBetween(allSessionFormatonTime,date_deb_toInsert))
				{
					sessionFormationService.delete(x);
					session.setAttribute("errors","Salle non disponbile pour le :"+format.parse(newDate+" "+heure_deb_aux));
					return "redirect:/admin/session_formations";
				}

				if(!testDateBetween(allSessionFormatonTime,date_fin_toInsert))
				{
					sessionFormationService.delete(x);
					session.setAttribute("errors","Salle non disponbile pour le :"+format.parse(newDate+" "+heures_fin_array[i]));
					return "redirect:/admin/session_formations";
				}

				list_time_session_formation.add(sft);
				sft.setSession_formation(x);
				i++;
			}

			System.out.println("List times to : "+x.getId());
			System.out.println(Arrays.toString(list_time_session_formation.toArray()));
			x.getSession_formation_time().clear();
			x.getSession_formation_time().addAll(list_time_session_formation);
			//x.setSession_formation_time(list_time_session_formation);
			sessionFormationService.saveAndFlush(x);
			System.out.println("Done");
			session.setAttribute("success","Session ajoutée avec succès");

			return "redirect:/admin/session_formations";
		}
		return "redirect:/index";
	}

	public boolean testDateBetween(List<Date> dates,Date date)
	{
		Boolean result = true;
		for(int i=0;i<dates.size();i++)
		{
			System.out.println("Test __ Date deb :  "+dates.get(i)+"  /// Date fin : "+dates.get(i+1));
			if((dates.get(i).before(date))&&(dates.get(i+1).after(date)))
			{
				result=false;
				break;
			}
			i+=1;
		}
		return result;

	}

	@RequestMapping(value="/admin/session_formation/edit",method=RequestMethod.POST)
	public  Object editSessionFormation(
			@RequestParam("titre") String titre,
			@RequestParam("date_debut") String date_deb,
			@RequestParam(value="link",required=false) String link,
			@RequestParam("date_fin") String date_fin,
			@RequestParam("heures_deb") String heures_deb,
			@RequestParam("heures_fin") String heures_fin,
			@RequestParam("date_to_inset_data") String date_to_inset_data,
			@RequestParam("nbr_stag") int nbr_stag,
			@RequestParam("duree") int duree,
			@RequestParam("salle_id") long salle_id,
			@RequestParam("formateur_id") Long formateur_id,
			@RequestParam("formation_id") Long formation_id,
			@RequestParam("session_formation_id") Long session_formation_id,
			HttpSession session) throws ParseException
	{

		if((Middleware.isAdmin(session)||(Middleware.isSalari(session))))
		{
			Optional<SessionFormation> search=sessionFormationService.findById(session_formation_id);
			if(search.isPresent())
			{


				search.get().setStatus(1);
				search.get().setTitre(titre);

				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				search.get().setDate_Deb(formatter.parse(date_deb));
				search.get().setDate_fin(formatter.parse(date_fin));
				if(link!=null)
				{
					search.get().setLink(link);
				}
				Salle salleToUpdate = search.get().getSallef();
				salleToUpdate.setDisponibilite(1);

				salleService.save(salleToUpdate);


				Optional<Salle> salle = salleService.findById(salle_id);
				if(salle.isPresent())
				{
					if(salle.get().getDisponibilite()==1)
					{
						//	salle.get().setDisponibilite(0);
						salleService.save(salle.get());
					}
					else if(salle.get().getDisponibilite()==0)
					{
						session.setAttribute("errors", "Salle non disponbile");
						return "redirect:/admin/session_formation/";
					}
				}

				search.get().setSallef(salle.get());
				search.get().setFormateur(userServiceCrud.getOne(formateur_id));
				search.get().setFormation(formationService.getOne(formation_id));
				search.get().setDuree(duree);
				search.get().setNbre_stagiaires(nbr_stag);
				List<Date> allSessionFormatonTime = new ArrayList<Date>();

				sessionFormationService.save(search.get());
				List<SessionFormationTime> list_time_session_formation = new ArrayList<SessionFormationTime>();
				String[] heures_deb_array = heures_deb.split(",");
				String[] heures_fin_array = heures_fin.split(",");
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
				String[] dates_array=date_to_inset_data.split(",");
				int i=0;

				for(String heure_deb_aux : heures_deb_array)
				{
					SessionFormationTime sft = new SessionFormationTime();
					String dateDebutString= date_deb+heure_deb_aux;
					System.out.println("Parsed Date debut : "+dateDebutString);


					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Calendar c = Calendar.getInstance();
					try{
						//Setting the date to the given date
						c.setTime(sdf.parse(date_deb));
					}catch(ParseException e){
						e.printStackTrace();
					}

					//Number of Days to add
					c.add(Calendar.DAY_OF_MONTH, i);  
					//Date after adding the days to the given date
					String newDate = sdf.format(c.getTime());  
					Date date_deb_toInsert=(format.parse(dates_array[i]+" "+heure_deb_aux));
					Date date_fin_toInsert=(format.parse(dates_array[i]+" "+heures_fin_array[i]));
					sft.setDate_deb(date_deb_toInsert);
					sft.setDate_fin(date_fin_toInsert);

					if(allSessionFormatonTime.contains(date_deb_toInsert))
					{

						session.setAttribute("success","Salle indisponbile pour le :"+format.parse(newDate+" "+heure_deb_aux));

						return "redirect:/admin/session_formations";
					}

					if(allSessionFormatonTime.contains(date_fin_toInsert))
					{

						session.setAttribute("errors","Salle indisponbile pour le :"+format.parse(newDate+" "+heures_fin_array[i]));
						return "redirect:/admin/session_formations";
					}

					if(!testDateBetween(allSessionFormatonTime,date_deb_toInsert))
					{

						session.setAttribute("errors","Salle indisponbile pour le :"+format.parse(newDate+" "+heure_deb_aux));
						return "redirect:/admin/session_formations";
					}

					if(!testDateBetween(allSessionFormatonTime,date_fin_toInsert))
					{

						session.setAttribute("errors","Salle indisponbile pour le :"+format.parse(newDate+" "+heures_fin_array[i]));
						return "redirect:/admin/session_formations";
					}

					list_time_session_formation.add(sft);
					sft.setSession_formation(search.get());
					i++;
				}

				System.out.println("List times to : "+search.get().getId());
				System.out.println(Arrays.toString(list_time_session_formation.toArray()));
				search.get().getSession_formation_time().clear();
				search.get().getSession_formation_time().addAll(list_time_session_formation);
				//x.setSession_formation_time(list_time_session_formation);
				sessionFormationService.saveAndFlush(search.get());

				//newSessionFormation.setDescription(description);

				sessionFormationService.saveAndFlush(search.get());
			}
			System.out.println("Done");
			return "redirect:/admin/session_formations";
		}
		return "redirect:/index";
	}


	@RequestMapping("/admin/session_formations")
	public Object showSessionFormations(HttpSession session)
	{
		System.out.println("Passed from here");
		if((Middleware.isAdmin(session))||(Middleware.isSalari(session)))
		{
			ModelAndView showFormations=new ModelAndView("../pages/admin/AfficherSessionFormation");
			showFormations.addObject("session_formation_list",sessionFormationService.findAll());
			showFormations.addObject("formation_list",formationService.findAll());
			List<User> usersList=userServiceCrud.findAll();
			List<User> formersList = new ArrayList<User>();
			for(User x : usersList)
			{
				if(x.getRole().getId()==2)
					formersList.add(x);
			}
			/*	Map list_event_time=new HashMap<Integer,String>();
			for(SessionFormation sf : sessionFormationService.findAll())
			{
				for(SessionFormationTime sft : sf.getSession_formation_time())
				{
					list_event_time.put(sft.getId(), split(sft.getDate_deb(),' ')[1]);
				}
			}*/
			List<Salle> list_salle=salleService.findAll();
			System.out.println("Session number for salle "+list_salle.get(0).getNom()+" "+list_salle.get(0).getSession_formations().size());
			showFormations.addObject("formersList",formersList);
			showFormations.addObject("salle_list",list_salle);

			return showFormations;
		}
		return "redirect:/";
	}

	@RequestMapping("/admin/session_formation/delete/{id}")
	public Object deleteSessionFormation( @PathVariable("id") long session_formation_id,HttpSession session)
	{
		if((Middleware.isAdmin(session))||(Middleware.isSalari(session)))
		{
			Optional<SessionFormation> searched=sessionFormationService.findById(session_formation_id);
			if(searched.isPresent())
			{
				searched.get().setStatus(0);
				//sessionFormationService.save(searched.get());
				sessionFormationService.delete(searched.get());
			}

			return "redirect:/admin/session_formations";
		}
		return "redirect:/index";
	}


	@RequestMapping("/admin/session_formation/restore/{id}")
	public Object restoreSessionFormation( @PathVariable("id") long session_formation_id,HttpSession session)
	{
		if((Middleware.isAdmin(session))||(Middleware.isSalari(session)))
		{
			Optional<SessionFormation> searched=sessionFormationService.findById(session_formation_id);
			if(searched.isPresent())
			{
				searched.get().setStatus(1);
				sessionFormationService.save(searched.get());
			}


			return "redirect:/admin/session_formations";
		}
		return "redirect:/index";
	}


	//Crud formateurs 


	@RequestMapping("/admin/formateurs")
	public Object showFormers(HttpSession session)
	{
		System.out.println("Passed from here");
		if((Middleware.isAdmin(session))||(Middleware.isSalari(session)))
		{
			ModelAndView showFormations=new ModelAndView("../pages/admin/AfficherFormateur");
			List<User> usersList=userServiceCrud.findAll();
			List<User> formersList = new ArrayList<User>();
			for(User x : usersList)
			{
				if(x.getRole().getId()==2)
					formersList.add(x);
			}
			showFormations.addObject("formers_list",formersList);
			return showFormations;
		}
		return "redirect:/index";
	}

	@RequestMapping(value="/admin/add_former",method=RequestMethod.POST)
	public Object addFormer(@RequestParam("password") String password,@RequestParam("adresse") String adresse,@RequestParam("nom") String nom,@RequestParam("prenom") String prenom,@RequestParam("email") String email,@RequestParam("num_tel") String num_tel,HttpSession session)
	{
		if((Middleware.isAdmin(session))||(Middleware.isSalari(session)))
		{
			User user=userService.getUserByEmail(email);

			if(user!=null)
			{
				System.out.println("errors");
				System.out.println("User adresse : "+user.getAdresse());
				session.setAttribute("errors","Adresse e-mail déjà utilisée");
				return "redirect:/admin/formateurs";
			}
			User former=new User();
			former.setAdresse(adresse);
			former.setEmail(email);
			former.setNum_tel(num_tel);
			former.setNom(nom);
			former.setPrenom(prenom);
			former.setPassword(password);
			former.setStatus(1);

			try {
				smtpMailSender.send(email, "Inscription EFCO-FORMATION", "Bonjour,<br/> Vous avez reussi à avoir un compte,Bienvenue sur notre plateforme Votre identifiant est : "+email+" votre mot de passe est  : "+password);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//result=e.getMessage();

			}

			former.setRole(roleService.getRoleById(2));
			//envoie du email ou c'est pas obligatoire ? 
			userServiceCrud.saveAndFlush(former);
			System.out.println("Done");
			return "redirect:/admin/formateurs";
		}
		return "redirect:/index";
	}


	@RequestMapping(value="/admin/former/edit",method=RequestMethod.POST)
	public Object editFormer(@RequestParam("user_id") Long former_id,@RequestParam("password") String password,@RequestParam("adresse") String adresse,@RequestParam("nom") String nom,@RequestParam("prenom") String prenom,@RequestParam("email") String email,@RequestParam("num_tel") String num_tel,HttpSession session)
	{

		if((Middleware.isAdmin(session))||(Middleware.isSalari(session)))
		{

			Optional<User> searched = userServiceCrud.findById(former_id);
			if(searched.isPresent())
			{
				System.out.println("new Email : "+email+" old email :"+searched.get().getEmail());
				if(!searched.get().getEmail().equals(email))
				{
					User user=userService.getUserByEmail(email);

					if(user!=null)
					{
						System.out.println("errors");
						System.out.println("User adresse : "+user.getAdresse());
						session.setAttribute("errors","Adresse e-mail déjà utilisée");
						return "redirect:/admin/formateurs";
					}
				}
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

				return "redirect:/admin/formateurs"; 
			}
			System.out.println("Done");
			return "redirect:/admin/formateurs";
		}
		return "redirect:/index";
	}



	@RequestMapping("/admin/former/delete/{id}")
	public Object deleteFormer( @PathVariable("id") long former_id,HttpSession session)
	{
		if((Middleware.isAdmin(session))||(Middleware.isSalari(session)))
		{
			Optional<User> searched=userServiceCrud.findById(former_id);
			if(searched.isPresent())
			{
				if(searched.get().getRole().getId()==2)
				{
					searched.get().setStatus(0);
					//userServiceCrud.save(searched.get());
					userServiceCrud.delete(searched.get());
				}
			}

			return "redirect:/admin/formateurs";
		}
		return "redirect:/index";
	}


	@RequestMapping("/admin/former/restore/{id}")
	public Object restoreFormer( @PathVariable("id") long former_id,HttpSession session)
	{if((Middleware.isAdmin(session))||(Middleware.isSalari(session)))
	{
		Optional<User> searched=userServiceCrud.findById(former_id);
		if(searched.isPresent())
		{
			if(searched.get().getRole().getId()==2)
			{
				searched.get().setStatus(1);
				userServiceCrud.save(searched.get());
			}
		}

		return "redirect:/admin/formateurs";
	}
	return "redirect:/index";
	}


	// Crud actualités 

	@RequestMapping(value="admin/add_actualite",method=RequestMethod.POST)
	public Object addActualite(@RequestParam("image") MultipartFile file,@RequestParam("titre") String titre,@RequestParam("description") String desc,HttpSession session)
	{
		System.out.println("passed from here");

		if((Middleware.isAdmin(session))||(Middleware.isSalari(session)))
		{
			System.out.println("Here");
			Actualite actualite = new Actualite();
			actualite.setTitre(titre);
			actualite.setDescription(desc);
			actualite.setStatus(1);
			session.removeAttribute("erros_image_actualite");
			session.removeAttribute("success_add_actualite");

			String mimeType = file.getContentType();
			String type = mimeType.split("/")[0];
			System.out.println("Type : "+type);
			if (!type.equalsIgnoreCase("image")) {
				session.setAttribute("errors", "Veuillez vérifier votre image ! Type ");
				return "redirect:/admin/actualite";	
			}

			if (!file.isEmpty()) {
				try {
					String uploadsDir = "/uploads_actualite/";
					String realPathtoUploads =  request.getServletContext().getRealPath(uploadsDir);

					if(! new File(realPathtoUploads).exists())
					{
						new File(realPathtoUploads).mkdir();
					}

					System.out.println("realPathtoUploads = "+ realPathtoUploads);
					String[] fileFrags = file.getOriginalFilename().split("\\.");
					String extension = fileFrags[fileFrags.length-1];
					String newFileName=generatePassword()+"."+extension;
					String filePath = realPathtoUploads + newFileName;
					actualite.setImage(uploadsDir+newFileName);
					File dest = new File(filePath);
					file.transferTo(dest);

				}catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					session.setAttribute("errors", "Veuillez vérifier  la taille de votre image ! (taille max : 2.3 MB) ");
					return "redirect:/admin/actualite";

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					session.setAttribute("errors", "Veuillez vérifier la taille de votre image ! (taille max : 2.3 MB) ");
					return "redirect:/admin/actualite";

				}

				actualiteService.save(actualite);
			}
			session.setAttribute("success", "Actualité ajoutée avec succès");

			System.out.println("Done");
			return "redirect:/admin/actualite";
		}


		return "redirect:/index";
	}

	@RequestMapping("/admin/actualite")
	public Object showActualite(HttpSession session)
	{

		System.out.println("Passed from here");
		if((Middleware.isAdmin(session))||(Middleware.isSalari(session)))
		{
			ModelAndView showActualite=new ModelAndView("../pages/admin/AfficherActualites");
			showActualite.addObject("actualites_list",actualiteService.findAll());
			return showActualite;

		}
		return "redirect:/index";

	}


	@RequestMapping(value="admin/actualite/edit",method=RequestMethod.POST)
	public Object editActualite(@RequestParam("image") MultipartFile file,@RequestParam("titre") String titre,@RequestParam("desc") String desc,@RequestParam("actualite_id") long id,HttpSession session)
	{
		System.out.println("passed from here");

		if((Middleware.isAdmin(session))||(Middleware.isSalari(session)))
		{
			System.out.println("Here edit actualite");
			Optional<Actualite> actualiteToEdit=actualiteService.findById(id);
			if(actualiteToEdit.isPresent())
			{
				actualiteToEdit.get().setTitre(titre);
				actualiteToEdit.get().setDescription(desc);
				if (!file.isEmpty()) {
					try {
						String uploadsDir = "/uploads_actualite/";
						String realPathtoUploads =  request.getServletContext().getRealPath(uploadsDir);

						if(! new File(realPathtoUploads).exists())
						{
							new File(realPathtoUploads).mkdir();
						}

						System.out.println("realPathtoUploads = "+ realPathtoUploads);
						String[] fileFrags = file.getOriginalFilename().split("\\.");
						String extension = fileFrags[fileFrags.length-1];
						String newFileName=generatePassword()+"."+extension;
						String filePath = realPathtoUploads + newFileName;
						actualiteToEdit.get().setImage(uploadsDir+newFileName);
						File dest = new File(filePath);
						file.transferTo(dest);

					}catch(Exception ex)
					{
						System.out.println("Exception : "+ex.getMessage());
					}
					actualiteService.saveAndFlush(actualiteToEdit.get());
				}
				System.out.println("Done");
				session.setAttribute("success", "Actualité modifiée avec succès");
				return "redirect:/admin/actualite";
			}

			return "redirect:/admin/actualite";

		}

		return "redirect:/index";
	}

	@RequestMapping("/admin/actualite/delete/{id}")
	public Object deleteActualite( @PathVariable("id") long actualite_id,HttpSession session)
	{
		if(!(Middleware.isAdmin(session))&&(!Middleware.isSalari(session)))
		{
			return "redirect:/";
		}
		Optional<Actualite> searched=actualiteService.findById(actualite_id);
		if(searched.isPresent())
		{
			searched.get().setStatus(0);
			//actualiteService.save(searched.get());
			actualiteService.delete(searched.get());
			session.setAttribute("success", "Actualité supprimée avec succès");
			//typeFormation.delete(searched.get());
		}



		return "redirect:/admin/actualite";
	}


	@RequestMapping("/admin/actualite/restore/{id}")
	public Object restoreAcutalite( @PathVariable("id") long actualite_id,HttpSession session)
	{
		if((!Middleware.isAdmin(session))&(!Middleware.isSalari(session)))
		{
			return "redirect:/";
		}
		Optional<Actualite> searched=actualiteService.findById(actualite_id);
		if(searched.isPresent())
		{
			searched.get().setStatus(1);
			actualiteService.save(searched.get());
			//typeFormation.delete(searched.get());
		}



		return "redirect:/admin/actualite";
	}

	//Crud client

	@RequestMapping("/admin/clients")
	public Object showUsersList(HttpSession session) {

		if((Middleware.isAdmin(session))||(Middleware.isSalari(session)))
		{
			ModelAndView usersPage=new ModelAndView("../pages/admin/AfficherClients");

			List<User> usersList=userServiceCrud.findAll();
			List<User> clientsList = new ArrayList<User>();
			for(User x : usersList)
			{
				if(x.getRole().getId()==3)
					clientsList.add(x);
			}


			usersPage.addObject("users_clients_list",clientsList);
			return usersPage;
		}
		return "redirect:/index";

	}

	@RequestMapping(value="/admin/add_client",method=RequestMethod.POST)
	public Object addClient(
			@RequestParam("password") String password,
			@RequestParam("adresse") String adresse,
			@RequestParam("nom") String nom,
			@RequestParam("prenom") String prenom,
			@RequestParam("email") String email,
			@RequestParam("num_tel") String num_tel,
			@RequestParam(value="siren",required=false,defaultValue="0") Long siren,
			@RequestParam(value="siret",required=false,defaultValue="0") Long siret,
			@RequestParam(value="num_carte_bancaire",required=false,defaultValue="0") Long num_carte_bancaire,
			@RequestParam(value="assujetti_tva",required=false) int assujetti_tva,
			@RequestParam(value="rcs_rm",required=false,defaultValue="0")  Long rcs_rm,
			@RequestParam(value="code_postal",required=false,defaultValue="0") Long code_postal,
			@RequestParam(value="pays",required=false) String pays,
			@RequestParam(value="ville",required=false) String ville,
			@RequestParam(value="maison_mere",required=false) String maison_mere,
			@RequestParam(value="type_tier",required=false) String type_tier,
			@RequestParam(value="forme_juridique",required=false) String forme_juridique,
			HttpSession session) throws MessagingException
	{

		if((Middleware.isAdmin(session))||(Middleware.isSalari(session)))
		{
			User user=userService.getUserByEmail(email);

			if(user!=null)
			{
				System.out.println("errors");
				System.out.println("User adresse : "+user.getAdresse());
				session.setAttribute("errors","Adresse e-mail déjà utilisée");
				return "redirect:/admin/clients";
			}
			User newUser=new User();
			newUser.setAdresse(adresse);
			newUser.setEmail(email);
			newUser.setNum_tel(num_tel);
			newUser.setNom(nom);
			newUser.setPrenom(prenom);
			newUser.setPassword(password);
			newUser.setStatus(1);
			newUser.setRole(roleService.getRoleById(3));


			Client client = new Client();

			client.setStatus(0);
			System.out.println("assujetti_tva : "+assujetti_tva);
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
			System.out.println("Siren : "+siren);
			client.setSiren(siren);
			client.setSiret(siret);
			client.setNumeroCompteBanc(num_carte_bancaire);
			client.setPays(pays);
			client.setVille(ville);
			client.setType_tier(type_tier);
			client.setRcs_rm(rcs_rm);
			User userAdded=userServiceCrud.saveAndFlush(newUser);
			client.setUser(userAdded);
			clientService.insertClient(client);
			//envoie du email ou c'est pas obligatoire ? 

			smtpMailSender.send(userAdded.getEmail(), "Inscription EFCO-FORMATION", "Bonjour,<br/> Vous avez reussi à avoir un compte,Bienvenue sur notre plateforme Votre identifiant est : "+userAdded.getEmail()+" votre mot de passe est : "+password);

			session.setAttribute("success", "Client ajouté avec succès");
			System.out.println("Done");
			return "redirect:/admin/clients";
		}
		return "redirect:/index";
	}

	@RequestMapping(value="/admin/client/edit",method=RequestMethod.POST)
	public Object editClient(
			@RequestParam("user_id") long user_id,
			@RequestParam("password") String password,
			@RequestParam("adresse") String adresse,
			@RequestParam("nom") String nom,
			@RequestParam("prenom") String prenom,
			@RequestParam("email") String email,
			@RequestParam("num_tel") String num_tel,
			@RequestParam(value="siren",required=false,defaultValue="0") Long siren,
			@RequestParam(value="siret",required=false,defaultValue="0") Long siret,
			@RequestParam(value="num_carte_bancaire",required=false,defaultValue="0") Long num_carte_bancaire,
			@RequestParam(value="assujetti_tva",required=false) int assujetti_tva,
			@RequestParam(value="rcs_rm",required=false,defaultValue="0")  Long rcs_rm,
			@RequestParam(value="code_postal",required=false,defaultValue="0") Long code_postal,
			@RequestParam(value="pays",required=false) String pays,
			@RequestParam(value="ville",required=false) String ville,
			@RequestParam(value="maison_mere",required=false) String maison_mere,
			@RequestParam(value="type_tier",required=false) String type_tier,
			@RequestParam(value="forme_juridique",required=false) String forme_juridique,
			HttpSession session) 
	{

		if((Middleware.isAdmin(session))||(Middleware.isSalari(session)))
		{


			Optional<User> searchedClient = userServiceCrud.findById(user_id);
			if(searchedClient.isPresent())
			{
				System.out.println("new Email : "+email+" old email :"+searchedClient.get().getEmail());
				if(!searchedClient.get().getEmail().equals(email))
				{
					User user=userService.getUserByEmail(email);

					if(user!=null)
					{
						System.out.println("errors");
						System.out.println("User adresse : "+user.getAdresse());
						session.setAttribute("errors","Adresse e-mail déjà utilisée");
						return "redirect:/admin/clients";
					}
				}
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
				session.setAttribute("success", "Client modifié avec succès");
			}



			return "redirect:/admin/clients";
		}
		return "redirect:/index";
	}


	@RequestMapping("/admin/client/restore/{id}")
	public Object restoreClient( @PathVariable("id") long user_id,HttpSession session)
	{
		if((Middleware.isAdmin(session))||(Middleware.isSalari(session)))
		{
			Optional<User> searchedClient = userServiceCrud.findById(user_id);
			if(searchedClient.isPresent())
			{
				searchedClient.get().setStatus(1);
				userServiceCrud.saveAndFlush(searchedClient.get());
			}

			return "redirect:/admin/clients";
		}
		return "redirect:/index";
	}


	@RequestMapping("/admin/client/delete/{id}")
	public Object deleteClient( @PathVariable("id") long user_id,HttpSession session)
	{
		if((Middleware.isAdmin(session))||(Middleware.isSalari(session)))
		{
			Optional<User> searchedClient = userServiceCrud.findById(user_id);
			if(searchedClient.isPresent())
			{
				searchedClient.get().setStatus(0);
				//userServiceCrud.saveAndFlush(searchedClient.get());
				userServiceCrud.delete(searchedClient.get());
			}

			return "redirect:/admin/clients";
		}
		return "redirect:/index";
	}


	//Devis accept or denied

	@RequestMapping(value="/admin/devis/answer",method=RequestMethod.POST)
	public Object acceptDevis(@RequestParam("devis_id") Long devis_id,@RequestParam("message") String message,HttpSession session)
	{
		if((Middleware.isAdmin(session))||(Middleware.isSalari(session)))
		{
			Optional<Devis> searchDevis = devisService.findById(devis_id);
			if(searchDevis.isPresent())
			{
				if(searchDevis.get().getStatus()==0)
				{
					try {
						smtpMailSender.send(searchDevis.get().getEmail(), "EFCO-FORMATION Devis",message);
						searchDevis.get().setStatus(1);
						devisService.saveAndFlush(searchDevis.get());
					} catch (MessagingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						//result=e.getMessage();
					}
					return "redirect:/admin/devis";
				}
				return "redirect:/admin/devis";
			}
			return "redirect:/admin/devis";
		}
		return "redirect:/index";
	}

	@RequestMapping("/admin/devis")
	public Object showDevisList(HttpSession session) {

		if((Middleware.isAdmin(session))||(Middleware.isSalari(session)))
		{
			ModelAndView devisPage=new ModelAndView("../pages/admin/AfficherDevis");

			List<Devis> devisList=devisService.findAll();
			List<SousTypeFormation> sous_type_formation_list = sousTypeFormationService.findAll();
			Map<Long, String> sous_type_formation_hashmap = new HashMap<Long,String>();	
			for(SousTypeFormation tf : sous_type_formation_list)
			{
				sous_type_formation_hashmap.put(tf.getId(), tf.getTitre());
			}
			devisPage.addObject("devis_list",devisList);
			devisPage.addObject("sous_type_formation_hashmap",sous_type_formation_hashmap);

			return devisPage;
		}
		return "redirect:/index";

	}

	//get stagiaire from user 
	@RequestMapping("/admin/stag_list/user/{id}")
	public Object showStagList( @PathVariable("id") long user_id,HttpSession session)
	{
		if((Middleware.isAdmin(session))||(Middleware.isSalari(session)))
		{
			Optional<User> searchUser = userServiceCrud.findById(user_id);
			if(searchUser.isPresent())
			{
				ModelAndView list_stag_page=new ModelAndView("../../../pages/admin/AfficherStagiaire");
				list_stag_page.addObject("stag_list",searchUser.get().getClient().getList_stagiaires());
				list_stag_page.addObject("user",searchUser.get());
				return list_stag_page;

			}
		}
		return "redirect:/index";

	}


	@RequestMapping(value="/admin/restore/edit",method=RequestMethod.POST)
	public Object restoreSatg(@RequestParam("stag_id") Long stag_id,
			@RequestParam("message") String message,
			HttpSession session)
	{

		if((Middleware.isAdmin(session))||(Middleware.isSalari(session)))
		{
			return "redirect:/";
		}

		Optional<Stagiaire> searchStagiaire = stagiaireService.findById(stag_id);
		if(searchStagiaire.isPresent())
		{

			searchStagiaire.get().getUser().setStatus(1);

			stagiaireService.saveAndFlush(searchStagiaire.get());

		}

		return "redirect:/admin/stag_list/user/"+searchStagiaire.get().getClient().getUser().getId();

	}

	@RequestMapping("/admin/stag/delete/{id}")
	public Object deleteStag( @PathVariable("id") long stag_id,HttpSession session)
	{

		if(!(Middleware.isAdmin(session))&&(!Middleware.isSalari(session)))
		{
			return "redirect:/";
		}

		Optional<Stagiaire> searchStagiaire = stagiaireService.findById(stag_id);
		if(searchStagiaire.isPresent())
		{

			userServiceCrud.delete(searchStagiaire.get().getUser());

			//stagiaireService.saveAndFlush(searchStagiaire.get());
			stagiaireService.delete(searchStagiaire.get());
			session.setAttribute("success", "Stagiaire supprimé avec succès");
		}

		return "redirect:/admin/stag_list/user/"+searchStagiaire.get().getClient().getUser().getId();

	}

	@RequestMapping("/admin/stag/edit")
	public Object editStag( @RequestParam("stag_id") long stag_id,@RequestParam("adresse") String adresse,@RequestParam("nom") String nom,@RequestParam("prenom") String prenom,@RequestParam("email") String email,@RequestParam("num_tel") String num_tel,HttpSession session)
	{

		if((!Middleware.isAdmin(session))&&(!Middleware.isSalari(session)))
		{
			return "redirect:/";
		}



		Optional<Stagiaire> searchStagiaire = stagiaireService.findById(stag_id);
		if(searchStagiaire.isPresent())
		{

			System.out.println("new Email : "+email+" old email :"+searchStagiaire.get().getUser().getEmail());
			if(!searchStagiaire.get().getUser().getEmail().equals(email))
			{
				User user=userService.getUserByEmail(email);
				if(!searchStagiaire.get().getUser().getEmail().equals(email))
				{
					if(user!=null)
					{
						System.out.println("errors");
						System.out.println("User adresse : "+user.getAdresse());
						session.setAttribute("errors","Adresse e-mail déjà utilisée");
						return "redirect:/admin/stagiaires";
					}
				}
			}
			searchStagiaire.get().getUser().setAdresse(adresse);
			searchStagiaire.get().getUser().setEmail(email);
			searchStagiaire.get().getUser().setNom(nom);
			searchStagiaire.get().getUser().setPrenom(prenom);
			searchStagiaire.get().getUser().setNum_tel(num_tel);

			stagiaireService.saveAndFlush(searchStagiaire.get());
			session.setAttribute("success", "Stagiaire modifié avec succès");

		}

		return "redirect:/admin/stag_list/user/"+searchStagiaire.get().getClient().getUser().getId();


	}



	//Crud salarier

	@RequestMapping("/admin/salaries")
	public Object showSalaries( HttpSession session)
	{
		System.out.println("Passed from here");
		if(Middleware.isAdmin(session))
		{
			ModelAndView showFormations=new ModelAndView("../pages/admin/AfficherSalaries");
			List<User> usersList=userServiceCrud.findAll();
			List<User> salarieList = new ArrayList<User>();
			for(User x : usersList)
			{
				if(x.getRole().getId()==4)
					salarieList.add(x);
			}
			showFormations.addObject("salaries_list",salarieList);
			return showFormations;
		}
		return "redirect:/index";
	}


	@RequestMapping(value="/admin/add_salarie",method=RequestMethod.POST)
	public Object addSalarier(@RequestParam("password") String password,@RequestParam("adresse") String adresse,@RequestParam("nom") String nom,@RequestParam("prenom") String prenom,@RequestParam("email") String email,@RequestParam("num_tel") String num_tel,HttpSession session)
	{

		if(Middleware.isAdmin(session))
		{
			User user=userService.getUserByEmail(email);

			if(user!=null)
			{
				System.out.println("errors");
				System.out.println("User adresse : "+user.getAdresse());
				session.setAttribute("errors","Adresse e-mail déjà utilisée");
				return "redirect:/admin/salaries";
			}


			User salarie=new User();
			salarie.setAdresse(adresse);
			salarie.setEmail(email);
			salarie.setNum_tel(num_tel);
			salarie.setNom(nom);
			salarie.setPrenom(prenom);
			salarie.setPassword(password);
			salarie.setStatus(1);
			salarie.setRole(roleService.getRoleById(4));

			try {
				smtpMailSender.send(email, "Inscription EFCO-FORMATION", "Bonjour,<br/> Vous avez reussi à avoir un compte,Bienvenue sur notre plateforme Votre identifiant est : "+email+" votre mot de passe est : "+password);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//result=e.getMessage();

			}


			//envoie du email ou c'est pas obligatoire ? 
			userServiceCrud.saveAndFlush(salarie);
			System.out.println("Done");
			session.setAttribute("success", "Salairié ajouté avec succès");

			return "redirect:/admin/salaries";
		}
		return "redirect:/index";
	}


	@RequestMapping(value="/admin/salarier/edit",method=RequestMethod.POST)
	public Object editSalarier(@RequestParam("user_id") Long former_id,@RequestParam("password") String password,@RequestParam("adresse") String adresse,@RequestParam("nom") String nom,@RequestParam("prenom") String prenom,@RequestParam("email") String email,@RequestParam("num_tel") String num_tel,HttpSession session)
	{

		if(Middleware.isAdmin(session))
		{
			User user=userService.getUserByEmail(email);




			Optional<User> searched = userServiceCrud.findById(former_id);
			if(searched.isPresent())
			{
				if(!searched.get().getEmail().equals(email))
				{
					if(user!=null)

					{
						System.out.println("errors");
						System.out.println("User adresse : "+user.getAdresse());
						session.setAttribute("errors","Adresse e-mail déjà utilisée");
						return "redirect:/admin/salaries";
					}
				}
				if(searched.get().getRole().getId()==4)
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
					session.setAttribute("success", "Salairié modifié avec succès");

				}

				return "redirect:/admin/salaries"; 
			}
			System.out.println("Done");
			return "redirect:/admin/salaries";
		}
		return "redirect:/index";
	}


	//CRud factures


	@RequestMapping("/admin/factures")
	public Object showFactures( HttpSession session)
	{
		System.out.println("Passed from here");
		if((Middleware.isAdmin(session))||(Middleware.isSalari(session)))
		{
			ModelAndView showFacture=new ModelAndView("../pages/admin/AfficherFacture");
			List<Facture> facturesList=factureService.findAll();
			List<Formation> formationList = formationService.findAll();
			Map<Long,String> list_formation = new HashMap<Long,String>();
			for(Formation x : formationList)
			{
				list_formation.put(x.getId(), x.getTitre());
			}
			showFacture.addObject("factures_list",facturesList);
			showFacture.addObject("list_formation_",formationList);
			showFacture.addObject("list_formation",list_formation);
			System.out.println("Nom : "+facturesList.get(0).getNom()+ " Type formation id :"+facturesList.get(0).getFormation_id());
			System.out.println("test : "+list_formation.get(facturesList.get(0).getFormation_id()));
			return showFacture;
		}
		return "redirect:/index";
	}


	@RequestMapping(value="/admin/add_facture",method=RequestMethod.POST)
	public Object addFacture(@RequestParam("nom") String nom,@RequestParam("type_formation") Long type_formation,@RequestParam("total") String total,@RequestParam("nbr_stagiaires") int nbr_stagiaires,@RequestParam("montant_recu") String montant_recu,@RequestParam("nbr_heure") int nbr_heure,@RequestParam("methode_paiement") int methode_paiement,HttpSession session)
	{

		if((Middleware.isAdmin(session)||(Middleware.isSalari(session))))
		{
			Facture facture=new Facture();
			facture.setNom(nom);
			facture.setMontant_recu(0);
			facture.setTotale(Double.parseDouble(total));
			facture.setNbr_heure(nbr_heure);
			facture.setFormation_id(type_formation);
			facture.setNbr_stagiaires(nbr_stagiaires);
			facture.setMontant_recu(Double.parseDouble(montant_recu));
			factureService.save(facture);
			System.out.println("Done");
			session.setAttribute("success", "Facture ajoutée avec succès");

			return "redirect:/admin/factures";
		}
		return "redirect:/index";
	}


	@RequestMapping(value="/admin/facture/edit",method=RequestMethod.POST)
	public Object editFacture(@RequestParam("facture_id") Long facture_id,@RequestParam("nom") String nom,@RequestParam("type_formation") Long type_formation,@RequestParam("total") String total,@RequestParam("montant_recu") String montant_recu,@RequestParam("nbr_stagiaires") int nbr_stagiaires,@RequestParam("nbr_heure") int nbr_heure,@RequestParam("methode_paiement") int methode_paiement,HttpSession session)
	{

		if((Middleware.isAdmin(session))||(Middleware.isSalari(session)))
		{
			Optional<Facture> searched = factureService.findById(facture_id);
			if(searched.isPresent())
			{
				searched.get().setNom(nom);
				searched.get().setMontant_recu(0);
				searched.get().setNbr_heure(nbr_heure);
				searched.get().setTotale(Double.parseDouble(total));
				searched.get().setNbr_stagiaires(nbr_stagiaires);
				searched.get().setMontant_recu(Double.parseDouble(montant_recu));
				searched.get().setFormation_id(type_formation);
				if((searched.get().getMontant_recu())-(searched.get().getTotale())==0)
				{
					searched.get().setStatus(1);
				}

				factureService.saveAndFlush(searched.get());
				System.out.println("Done ");
				session.setAttribute("success", "Facture modifiée avec succès");

				return "redirect:/admin/factures"; 
			}
			System.out.println("Done");
			return "redirect:/admin/factures";
		}
		return "redirect:/index";
	}

	@RequestMapping("/admin/facture/delete/{id}")
	public Object deleteFacture( @PathVariable("id") long facture_id,HttpSession session)
	{

		if((!Middleware.isAdmin(session))&&(!Middleware.isSalari(session)))
		{
			return "redirect:/";
		}
		Optional<Facture> searched=factureService.findById(facture_id);
		if(searched.isPresent())
		{
			searched.get().setStatus(0);
			//factureService.save(searched.get());
			factureService.delete(searched.get());
			session.setAttribute("success", "Facture supprimée avec succès");
		}
		return "redirect:/admin/factures";
	}

	@RequestMapping("/admin/restore/delete/{id}")
	public Object restoreFacture( @PathVariable("id") long facture_id,HttpSession session)
	{

		if((!Middleware.isAdmin(session))&&(!Middleware.isSalari(session)))
		{
			return "redirect:/";
		}
		Optional<Facture> searched=factureService.findById(facture_id);
		if(searched.isPresent())
		{
			searched.get().setStatus(1);
			//	factureService.save(searched.get());
			factureService.delete(searched.get());
		}
		return "redirect:/admin/factures";
	}

	//Crud salles 


	@RequestMapping("/admin/salles/restore/{id}")
	public Object restoreSalle( @PathVariable("id") long salle_id,HttpSession session)
	{
		if((!Middleware.isAdmin(session))&&(!Middleware.isSalari(session)))
		{
			return "redirect:/";
		}

		Optional<Salle> searched=salleService.findById(salle_id);
		if(searched.isPresent())
		{
			searched.get().setDisponibilite(1);
			salleService.save(searched.get());
			//typeFormation.delete(searched.get());
		}

		return "redirect:/admin/salles";
	}

	@RequestMapping("/admin/salle/delete/{id}")
	public Object deleteSalle( @PathVariable("id") long salle_id,HttpSession session)
	{
		if((!Middleware.isAdmin(session))&&(!Middleware.isSalari(session)))
		{
			return "redirect:/";
		}

		Optional<Salle> searched=salleService.findById(salle_id);
		if(searched.isPresent())
		{
			//searched.get().setDisponibilite(0);
			//salleService.save(searched.get());
			salleService.delete(searched.get());
			//typeFormation.delete(searched.get());
			session.setAttribute("success", "Salle supprimée avec succès");
		}

		return "redirect:/admin/salles";
	}

	@RequestMapping(value="/admin/salle/add",method=RequestMethod.POST)
	public Object addFacture(@RequestParam("nom") String nom,@RequestParam("adresse") String adresse,@RequestParam("localisation") String localisation,HttpSession session)
	{

		if((Middleware.isAdmin(session)||(Middleware.isSalari(session))))
		{
			Salle salle = new Salle();
			salle.setNom(nom);
			salle.setAdresse(adresse);
			salle.setLocalisation(localisation);
			salle.setDisponibilite(1);
			salleService.save(salle);
			session.setAttribute("success", "Salle ajoutée avec succès");
			return "redirect:/admin/salles";
		}
		return "redirect:/index";
	}

	@RequestMapping(value="/admin/salle/edit",method=RequestMethod.POST)
	public Object editSalle(@RequestParam("nom") String nom,@RequestParam("adresse") String adresse,@RequestParam("localisation") String localisation,@RequestParam("salle_id") Long salle_id,HttpSession session)
	{

		if((Middleware.isAdmin(session)||(Middleware.isSalari(session))))
		{
			Optional<Salle> searched = salleService.findById(salle_id);
			if(searched.isPresent())
			{
				searched.get().setNom(nom);
				searched.get().setAdresse(adresse);
				searched.get().setLocalisation(localisation);
				salleService.save(searched.get());
				session.setAttribute("success", "Salle modifiée avec succès");
			}
			return "redirect:/admin/salles";
		}
		return "redirect:/index";
	}


	@RequestMapping("/admin/salles")
	public Object showSalles(HttpSession session)
	{
		if((Middleware.isAdmin(session))||(Middleware.isSalari(session)))
		{


			ModelAndView sallePage = new ModelAndView("../pages/admin/AfficherSalle");
			sallePage.addObject("list_salle",salleService.findAll());
			return sallePage;
		}
		return "redirect:/index";

	}

	//Calendar back-end


	@RequestMapping("/admin/calendar_v2")
	public Object showCalendarV2(HttpSession session) throws JsonProcessingException
	{
		if((Middleware.isAdmin(session))||(Middleware.isSalari(session)))
		{
			ModelAndView calendarPage = new ModelAndView("../pages/admin/AfficherCalendrierV2");
			String jsonMsg = null;
			List<SessionFormation> formationList=sessionFormationService.findAll();
			System.out.println("Size from controller :"+formationList.size() );
			List<Event> events = new ArrayList<Event>();
			Map<String,String> eventsList = new HashMap<String,String>();
			for(SessionFormation sf : formationList)
			{
				for(SessionFormationTime sft:sf.getSession_formation_time())
				{

					Event event = new Event();
					event.setId(sf.getId());
					event.setTitle(sf.getTitre());
					event.setStart(sft.getDate_deb().toString());
					event.setEnd(sft.getDate_fin().toString());
					event.setLink(sft.getSession_formation().getLink());
					event.setDuree(Integer.toString(sf.getDuree()));
					event.setFormation_date_debut(sf.getDate_Deb().toString());
					event.setFormation_date_fin(sf.getDate_fin().toString());
					event.setDescription(sf.getFormation().getDescription());
					event.setSalle(sf.getSallef().getNom());
					if(sf.getDate_Deb().before(new Date()))
					{
						event.setExpired(1);
					}
					event.setAdresse(sf.getSallef().getAdresse());
					event.setLocalisation(sf.getSallef().getLocalisation());
					Color color=new Color((int)(Math.random() * 0x1000000));
					String eventColor=String.format("#%02x%02x%02x", color.getRed(),color.getGreen() , color.getBlue());

					while(eventsList.containsValue(eventColor))
					{
						color=new Color((int)(Math.random() * 0x1000000));
						eventColor=String.format("#%02x%02x%02x", color.getRed(),color.getGreen() , color.getBlue());

						System.out.println("Color code : "+ eventColor);	
					}
					event.setColor(eventColor);


					System.out.println("Places restantes : "+(sf.getNbre_stagiaires()-sf.getStagiaires().size()));
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
			jsonMsg =  mapper.writerWithDefaultPrettyPrinter().writeValueAsString(events);
			calendarPage.addObject("events",jsonMsg);
			HashMap<Long,Long> session_formation_stagiaire = new HashMap<Long,Long>();
			List<SessionFormation> listSessionFormation= sessionFormationService.findAll();
			for(SessionFormation x : listSessionFormation)
			{	
				System.out.println("Stagiaires de la session de formation : "+x.getTitre()+" id : "+x.getId());
				for(Stagiaire stag : x.getStagiaires()) 
				{
					System.out.println("\tNom : "+stag.getUser().getNom()+" Prénom : "+stag.getUser().getPrenom());

					session_formation_stagiaire.put(x.getId()+stag.getUser().getId(), stag.getUser().getId());

				}

			}
			Map<Long,String> users_names=new HashMap<Long,String>();
			for(User u : userServiceCrud.findAll())
			{
				users_names.put(u.getId(), u.getNom()+" "+u.getPrenom());
			}
			calendarPage.addObject("sessionFormationList",listSessionFormation);
			calendarPage.addObject("all_stag_list",stagiaireService.findAll());
			calendarPage.addObject("events_list",session_formation_stagiaire);
			calendarPage.addObject("users_names",users_names);	
			return calendarPage;
		}
		return "redirect:/index";
	}

	@RequestMapping("/admin/calendar")
	public Object showCalendar(HttpSession session) throws JsonProcessingException
	{
		if((Middleware.isAdmin(session))||(Middleware.isSalari(session)))
		{
			ModelAndView calendarPage = new ModelAndView("../pages/admin/AfficherCalendrier");
			String jsonMsg = null;
			List<SessionFormation> formationList=sessionFormationService.findAll();
		//	System.out.println("Size from controller :"+formationList.size() );
			List<Event> events = new ArrayList<Event>();
			Map<String,String> eventsList = new HashMap<String,String>();
			for(SessionFormation sf : formationList)
			{
				for(SessionFormationTime sft:sf.getSession_formation_time())
				{

					Event event = new Event();
					event.setId(sf.getId());
					event.setTitle(sf.getTitre());
					event.setStart(sft.getDate_deb().toString());
					event.setEnd(sft.getDate_fin().toString());
					event.setLink(sft.getSession_formation().getLink());
					event.setDuree(Integer.toString(sf.getDuree()));
					event.setFormation_date_debut(sf.getDate_Deb().toString());
					event.setFormation_date_fin(sf.getDate_fin().toString());
					event.setDescription(sf.getFormation().getDescription());
					event.setSalle(sf.getSallef().getNom());
					if(sf.getDate_Deb().before(new Date()))
					{
						event.setExpired(1);
					}
					event.setAdresse(sf.getSallef().getAdresse());
					event.setLocalisation(sf.getSallef().getLocalisation());
					Color color=new Color((int)(Math.random() * 0x1000000));
					String eventColor=String.format("#%02x%02x%02x", color.getRed(),color.getGreen() , color.getBlue());

					while(eventsList.containsValue(eventColor))
					{
						color=new Color((int)(Math.random() * 0x1000000));
						eventColor=String.format("#%02x%02x%02x", color.getRed(),color.getGreen() , color.getBlue());

				//		System.out.println("Color code : "+ eventColor);	
					}
					event.setColor(eventColor);


				//	System.out.println("Places restantes : "+(sf.getNbre_stagiaires()-sf.getStagiaires().size()));
					event.setFree_stagiare_number(sf.getNbre_stagiaires()-sf.getStagiaires().size());
					if(eventsList.get(event.getTitle())!=null)
					{
						event.setColor(eventsList.get((event.getTitle())));
					}
					events.add(event);
					eventsList.put(event.getTitle(),event.getColor());
				//	System.out.println("Event : "+event.toString());
				}
			}

			ObjectMapper mapper = new ObjectMapper();
			jsonMsg =  mapper.writerWithDefaultPrettyPrinter().writeValueAsString(events);
			calendarPage.addObject("events",jsonMsg);
			HashMap<Long,Long> session_formation_stagiaire = new HashMap<Long,Long>();
			List<SessionFormation> listSessionFormation= sessionFormationService.findAll();
			for(SessionFormation x : listSessionFormation)
			{	
			//	System.out.println("Stagiaires de session formation : "+x.getTitre()+" id : "+x.getId());
				for(Stagiaire stag : x.getStagiaires()) 
				{
			//		System.out.println("\tNom : "+stag.getUser().getNom()+" Prénom : "+stag.getUser().getPrenom());

					session_formation_stagiaire.put(x.getId()+stag.getUser().getId(), stag.getUser().getId());

				}

			}
			Map<Long,String> users_names=new HashMap<Long,String>();
			for(User u : userServiceCrud.findAll())
			{
				users_names.put(u.getId(), u.getNom()+" "+u.getPrenom());
			}
			calendarPage.addObject("sessionFormationList",listSessionFormation);
			calendarPage.addObject("all_stag_list",stagiaireService.findAll());
			calendarPage.addObject("events_list",session_formation_stagiaire);
			calendarPage.addObject("users_names",users_names);	
			return calendarPage;
		}
		return "redirect:/index";
	}

	//delete stagiaire from session formation
	@RequestMapping("/admin/session_stag_delete/{user_id_stag}/{session_formation_id}")
	public Object deleteStagFromSession(@PathVariable("user_id_stag") long stagiaire_id,
			@PathVariable("session_formation_id") long session_formation_id,
			HttpSession session)
	{
		if((Middleware.isAdmin(session)||(Middleware.isSalari(session))))
		{
			Optional<User> user = userServiceCrud.findById(stagiaire_id);
			if(user.isPresent())
			{
				Optional<SessionFormation> sf=sessionFormationService.findById(session_formation_id);
				if(sf.isPresent())
				{
					sf.get().getStagiaires().remove(user.get().getStagiaire());
					sessionFormationService.saveAndFlush(sf.get());
					session.setAttribute("success", user.get().getNom()+" "+ user.get().getPrenom()+" a été bien supprimé(e) de la formation : "+sf.get().getTitre());
				}
				return "redirect:/admin/calendar";
			}
			return "redirect:/admin/calendar";
		}
		return "redirect:/admin/calendar";
	}

	//add stag to session formation
	@RequestMapping(value="/admin/add_stag_to_session",method=RequestMethod.POST)
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
		if((!Middleware.isAdmin(session)&&(!Middleware.isSalari(session))))
		{

			return "redirect:/index";
		}



		session.setAttribute("type_formation_headers", typeFormationService.findAll());


		Optional<SessionFormation> checkSessionFormation=sessionFormationService.findById(session_formation_signup_id);

		if(!checkSessionFormation.isPresent())
		{
			session.setAttribute("errors","Veuillez vérifier la session séléctionnée");
			return "redirect:/admin/calendar";
		}

		if(checkSessionFormation.get().getDate_Deb().before(new Date()))
		{
			session.setAttribute("info","Session de formation expriée");
			return "redirect:/admin/calendar";
		}

		
		boolean done=false;
		List<Stagiaire> currentStagInSF=checkSessionFormation.get().getStagiaires();
		String[] stags_id=null;
		int countOldStag=0;
		
		if(stag_list_to_session!=null)
		{
			stags_id = stag_list_to_session.split(",");
		}
		
		if(stags_id!=null)
		{
			countOldStag+=stags_id.length;
		}
		
		int cpt=0;
		int countNewStag=0;
		
		if(new_stag_name!=null)

		{

			String[] list_new_stag_name = new_stag_name.split(",");
			String[] list_new_stag_surname = new_stag_surname.split(",");
			String[] list_new_stag_email = new_stag_email.split(",");
			String[] list_new_stag_tel = new_stag_tel.split(",");
			String[] list_new_stag_password = new_stag_password.split(",");
			String[] list_genre=genre.split(",");
			countNewStag=list_new_stag_email.length;
			for(String new_name :list_new_stag_name)
			{
				User user=userService.getUserByEmail(list_new_stag_email[cpt]);
				//Role myRole =roleService.getRoleById(role);
				//System.out.println("Role name : "+myRole.getName());
				if(user!=null)
				{
					System.out.println("errors");
					System.out.println("User adresse : "+user.getAdresse());

					session.setAttribute("errors", "E-mail "+list_new_stag_email[cpt]+" Adresse e-mail déjà utilisée");
					return "redirect:/admin/calendar";
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
				//newStagiaire.setClient(currentUser.getClient());
				stagiaireService.saveAndFlush(newStagiaire);

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

				done=true;
			}
		}

		

		

		int x=countOldStag+countNewStag;
		if((checkSessionFormation.get().getNbre_stagiaires())-(checkSessionFormation.get().getStagiaires().size())-x<0)
		{
			
			session.setAttribute("errors","Il n'y a plus de places libres pour vos "+x+" stagiaires !");
			return "redirect:/admin/calendar";
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
					if(!currentStagInSF.contains(checkStagiaire.get()))
					{
						try {
							smtpMailSender.send(checkStagiaire.get().getUser().getEmail(), "Pré-inscription EFCO-Formation", "Bonjour "+checkStagiaire.get().getUser().getGender()+" "+checkStagiaire.get().getUser().getNom()+",<br/>Nous avons le plaisir de vous confirmer votre pré-inscription à notre session de formation "+checkSessionFormation.get().getTitre()+", du "+checkSessionFormation.get().getDate_Deb()+" à "+checkSessionFormation.get().getDate_fin()+",à "+checkSessionFormation.get().getSallef().getAdresse()+", "+checkSessionFormation.get().getSallef().getLocalisation()+", "+checkSessionFormation.get().getSallef().getNom()+"<br/>Un de nos conseillers formation vous contactera très prochainement afin de finaliser votre inscription.<br/> En attendant nous restons à votre disposition pour de plus amples informations.<br/>Cordialement");
							
						} catch (MessagingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						currentStagInSF.add(checkStagiaire.get());
						done=true;
					}
					else
					{
						stagExist=true;
						names_list+=checkStagiaire.get().getUser().getNom()+" "+checkStagiaire.get().getUser().getPrenom()+" | ";
					}

				}
				else
				{
					session.setAttribute("errors","Veuillez vérifier vos stagiaires séléctionné(e)s");
					return "redirect:/admin/calendar";
				}
			}
		}

		if(stagExist)
		{
			session.setAttribute("info", "Le(s) stagiaire(s)  : "+names_list+ " existe déja dans cette session ");
		}
		
		if(done)
		{
			
			System.out.println("Done");
		}

		checkSessionFormation.get().setStagiaires(currentStagInSF);
		sessionFormationService.save(checkSessionFormation.get());
		session.setAttribute("success","Vos stagiaires ont été inscrits pour cette session de formation");
		return "redirect:/admin/calendar";

	}


	//CRud stagiaires 


	@RequestMapping("/admin/stagiaires")
	public Object showStagiaires(HttpSession session)
	{
		System.out.println("Passed from here");
		if((Middleware.isAdmin(session))||(Middleware.isSalari(session)))
		{
			ModelAndView showStag=new ModelAndView("../pages/admin/AfficherStagiaires");
			List<Stagiaire> stag_list_=stagiaireService.findAll();
			List<Stagiaire> stag_list = new ArrayList<Stagiaire>();
			for(Stagiaire x : stag_list_)
			{
				if(x.getUser().getRole().getId()==5)
					stag_list.add(x);
			}
			List<Client> client_list = clientServiceCrud.findAll();
			showStag.addObject("formers_list",stag_list);
			showStag.addObject("client_list", client_list);
			Map my_list_ = new HashMap<Integer,String>();
			List<Client> list_client=clientServiceCrud.findAll();
			for(Client x : list_client)
			{
				my_list_.put(x.getId(), x.getUser().getNom()+" "+x.getUser().getPrenom());
			}
			showStag.addObject("my_list",my_list_);

			//System.out.println("nom client : "+stag_list.get(0).getClient().getUser().getNom());
			return showStag;
		}
		return "redirect:/index";
	}

	@RequestMapping(value="/admin/add_stagiaire",method=RequestMethod.POST)
	public Object addStagiaire(
			@RequestParam(value="client_id",required=false,defaultValue="-1") Long client_id,
			@RequestParam("password") String password,@RequestParam("adresse") String adresse,@RequestParam("nom") String nom,@RequestParam("prenom") String prenom,@RequestParam("email") String email,@RequestParam("num_tel") String num_tel,HttpSession session)
	{
		if((Middleware.isAdmin(session))||(Middleware.isSalari(session)))
		{
			User user=userService.getUserByEmail(email);

			if(user!=null)
			{
				System.out.println("errors");
				System.out.println("User adresse : "+user.getAdresse());
				session.setAttribute("errors","Adresse e-mail déjà utilisée");
				return "redirect:/admin/stagiaires";
			}


			User stag=new User();
			stag.setAdresse(adresse);
			stag.setEmail(email);
			stag.setNum_tel(num_tel);
			stag.setNom(nom);
			stag.setPrenom(prenom);
			stag.setPassword(password);
			stag.setStatus(1);
			//stag.setClient(null);
			stag.setRole(roleService.getRoleById(5));
			Stagiaire newStagiaire = new Stagiaire();

			User newUser=userServiceCrud.saveAndFlush(stag);
			newStagiaire.setUser(newUser);

			if(client_id!=-1)
			{
				Optional<Client> clientSearch= clientServiceCrud.findById(client_id);
				if(clientSearch.isPresent())
				{
					newStagiaire.setClient(clientSearch.get());
				}

			}

			try {
				smtpMailSender.send(email, "Inscription EFCO", "Bonjour,<br/> Merci pour votre inscription,</br>bienvenue sur notre plateforme Votre identifiant est : "+email+" votre mot de passe est  : "+password);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//result=e.getMessage();

			}
			stagiaireService.save(newStagiaire);
			session.setAttribute("success", "Stagiaire ajouté avec succès");


			System.out.println("Done");

			return "redirect:/admin/stagiaires";
		}
		return "redirect:/index";
	}


	@RequestMapping(value="/admin/stagiaire/edit",method=RequestMethod.POST)
	public Object editStagiaire(
			@RequestParam(value="client_id",required=false) Long client_id,
			@RequestParam("user_id") Long former_id,@RequestParam("password") String password,@RequestParam("adresse") String adresse,@RequestParam("nom") String nom,@RequestParam("prenom") String prenom,@RequestParam("email") String email,@RequestParam("num_tel") String num_tel,HttpSession session)
	{

		if((Middleware.isAdmin(session))||(Middleware.isSalari(session)))
		{
			User user=userService.getUserByEmail(email);

			if(user!=null)
			{
				System.out.println("errors");
				System.out.println("User adresse : "+user.getAdresse());
				session.setAttribute("errors","Adresse e-mail déjà utilisée");
				return "redirect:/admin/stagiaires";
			}

			Optional<Stagiaire> searched = stagiaireService.findById(former_id);
			if(searched.isPresent())
			{
				if(searched.get().getUser().getRole().getId()==5)
				{
					searched.get().getUser().setAdresse(adresse);
					searched.get().getUser().setEmail(email);
					searched.get().getUser().setNom(nom);
					searched.get().getUser().setPassword(password);
					searched.get().getUser().setEmail(email);
					searched.get().getUser().setNum_tel(num_tel);
					searched.get().getUser().setPrenom(prenom);
					if(password.length()!=0)
					{
						searched.get().getUser().setPassword(password);
					}
					userServiceCrud.saveAndFlush(searched.get().getUser());
					if(client_id!=null)
					{
						Optional<Client> clientSearch= clientServiceCrud.findById(client_id);
						if(clientSearch.isPresent())
						{
							searched.get().setClient(clientSearch.get());
							stagiaireService.save(searched.get());

						}

					}
					session.setAttribute("success", "Stagiaire modifié avec succès");
					System.out.println("Done ");
				}

				return "redirect:/admin/stagiaires"; 
			}
			System.out.println("Done");
			return "redirect:/admin/stagiaires";
		}
		return "redirect:/index";
	}

}
