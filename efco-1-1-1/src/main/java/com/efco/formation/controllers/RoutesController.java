package com.efco.formation.controllers;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.efco.formation.service.ActualiteService;
import com.efco.formation.service.DevisService;
import com.efco.formation.service.FormationService;
import com.efco.formation.service.SessionFormationService;
import com.efco.formation.service.TypeFormationService;
import com.efco.formation.service.UserService;
import com.efco.formation.service.UserServiceCrud;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.efco.formation.config.Middleware;
import com.efco.formation.config.SmtpMailSender;
import com.efco.formation.model.Devis;
import com.efco.formation.model.Event;
import com.efco.formation.model.Formation;
import com.efco.formation.model.SessionFormation;
import com.efco.formation.model.SessionFormationTime;
import com.efco.formation.model.Stagiaire;
import com.efco.formation.model.TypeFormation;
import com.efco.formation.model.User;

@Controller
public class RoutesController {

	@Autowired
	SessionFormationService sessionFormationService;

	@Autowired
	UserServiceCrud userServiceCrud;

	@Autowired
	TypeFormationService typeFormationService;

	@Autowired
	UserService userService;

	@Autowired
	ActualiteService actualiteService;

	@Autowired
	DevisService devisService;

	@Autowired
	FormationService formationService;

	@Autowired
	private Environment env;

	@Autowired
	private SmtpMailSender smtpMailSender;

	
	@GetMapping("/")
	public void hello()
	{
		System.out.println("Hello index");
	}
	@RequestMapping(value = "/")
	public Object showHome(HttpSession session) {
		if(Middleware.isAdmin(session))
		{
			return "redirect:/admin/dashboard";
		}
		System.out.println("Here");
		return new ModelAndView("index1");
	}


	@RequestMapping("/index")
	public Object firstPage(HttpSession session) {
		if(Middleware.isAdmin(session))
		{
			return "redirect:/admin/dashboard";
		}
		System.out.println("Here");
		ModelAndView index= new ModelAndView("index1");
		index.addObject("actualites",actualiteService.findAll());
		session.setAttribute("acualites_session",actualiteService.findAll());
		session.setAttribute("type_formation_headers", typeFormationService.findAll());
		return index;
	}

	@RequestMapping("/formation/{id}")
	public Object typeformationDetails(@PathVariable("id") long type_formation_id,HttpSession session) {
		if(Middleware.isAdmin(session))
		{
			return "redirect:/admin/dashboard";
		}
		System.out.println("Here");

		Optional<TypeFormation> searched=typeFormationService.findById(type_formation_id);
		if(searched.isPresent())
		{
			ModelAndView formationDetails = new ModelAndView("../pages/all/formation_details");
			formationDetails.addObject("type_formation_detail",searched.get());
			
			formationDetails.addObject("sous_type_formation_list",searched.get().getSous_type_formations());
			session.setAttribute("type_formation_headers", typeFormationService.findAll());
			return formationDetails;
		}
		return "redirect:/index";
	}


	@RequestMapping("/contact_us")
	public Object Contact(HttpSession session)
	{if(Middleware.isAdmin(session))
	{
		return "redirect:/admin/dashboard";
	}
	System.out.println("Contact us ");
	session.setAttribute("type_formation_headers", typeFormationService.findAll());
	return new ModelAndView("pages/all/contact");

	}

	@RequestMapping("/about_us")
	public Object aboutUs(HttpSession session)
	{if(Middleware.isAdmin(session))
	{
		return "redirect:/admin/dashboard";
	}
	System.out.println("About us");
	session.setAttribute("type_formation_headers", typeFormationService.findAll());
	return new ModelAndView("pages/all/QuiSommesNous");
	}

	@RequestMapping("/devis")
	public Object devis(HttpSession session)
	{
		if(Middleware.isAdmin(session))
		{
			return "redirect:/admin/dashboard";
		}
		System.out.println("Devis");

		ModelAndView devisPage= new ModelAndView("pages/all/Devis");
		List<TypeFormation> listTypeFormation=typeFormationService.findAll();
		List<TypeFormation> valideTypeFormation = new ArrayList<TypeFormation>();
		for(TypeFormation tf : listTypeFormation)
		{
			if(tf.getStatus()==1)
			{
				valideTypeFormation.add(tf);
			}
		}


		devisPage.addObject("type_formation_list",valideTypeFormation);
		session.setAttribute("type_formation_headers", typeFormationService.findAll());
		return devisPage;
	}

	@RequestMapping(value="/devis/add",method=RequestMethod.POST)
	public Object addDevis(HttpSession session,
			@RequestParam("email") String email,
			@RequestParam("nom") String nom,
			@RequestParam("type_formation") long type_formation,
			@RequestParam(value="sous_type_formation_name",required=false) String sous_type_formation_name,
			@RequestParam("nombre_stagiaire") long nombre_stagiaire,
			@RequestParam("num_tel") String num_tel
			)
	{
		if(Middleware.isAdmin(session))
		{
			return "redirect:/admin/dashboard";
		}
		session.removeAttribute("errors_devis");
		session.removeAttribute("success_devis");
		User user=userService.getUserByEmail(email);
		if(user!=null)
		{
			session.setAttribute("errors", "Adresse e-mail déjà utilisée");
			return "redirect:/devis"; 
		}
		Devis devis = new Devis();
		devis.setNom(nom);
		devis.setEmail(email);

		if(type_formation==-1)
		{
			devis.setType_formation_name(sous_type_formation_name);
		}

		devis.setType_formation(type_formation);


		devis.setStatus(0);
		devis.setNum_tel(num_tel);
		devis.setNombre_stagiaire(nombre_stagiaire); 
		devisService.save(devis);
		session.setAttribute("success", "Votre devis a été envoyer avec succès");
		return "redirect:/devis"; 
	}
	@RequestMapping(value="/contact/send",method=RequestMethod.POST)
	public Object sendContactMessage(HttpSession session,@RequestParam("from") String from,@RequestParam("nom") String nom,@RequestParam("prenom") String prenom,@RequestParam("message") String message,@RequestParam("object") String object)
	{
		try {

			smtpMailSender.send(env.getProperty("spring.mail.username"), object,"Nom : "+nom+" Prénom : " +prenom+" Email : "+from+" Message : "+message);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			session.setAttribute("errors", "Erreur lors de l'envoi du message, réessayer plus tard.");
			e.printStackTrace();
		}
		session.setAttribute("success", "Votre message a été envoyé avec succès");
		return "redirect:/contact"; 

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

	@RequestMapping("/formation_techniques")
	public Object showFormationTechniques(HttpSession session)
	{
		if(Middleware.isAdmin(session))
		{
			return "redirect:/admin/dashboard";
		}
		ModelAndView formation_technique= new ModelAndView("/pages/all/formationsTechniques");
		List<Formation> formatList=formationService.findAll();
		formation_technique.addObject("formations",formatList);
		session.setAttribute("type_formation_headers", typeFormationService.findAll());
		return formation_technique;
	}
	@RequestMapping("/formation_tertiaires")
	public ModelAndView showFormationTertiaires(HttpSession session)
	{
		session.setAttribute("type_formation_headers", typeFormationService.findAll());
		return new ModelAndView("/pages/all/formationsTertiaires");
	}
	@RequestMapping("/formation_caces")
	public ModelAndView showFormationCaces(HttpSession session)
	{
		session.setAttribute("type_formation_headers", typeFormationService.findAll());
		return new ModelAndView("/pages/all/formationsCaces");
	}

	@RequestMapping("/formation_securite")
	public ModelAndView showFormationSecurite(HttpSession session)
	{session.setAttribute("type_formation_headers", typeFormationService.findAll());
	return new ModelAndView("/pages/all/formationSecurite");
	}
	@RequestMapping("/formation_langues")
	public ModelAndView showFormationLangues(HttpSession session)
	{
		session.setAttribute("type_formation_headers", typeFormationService.findAll());
		return new ModelAndView("/pages/all/FormationsLangues");
	}

	@RequestMapping("/planning_version_2")
	public Object planningV2(HttpSession session) throws JsonProcessingException
	{
		ModelAndView planningPage=new ModelAndView("pages/all/PlanningFormationsVersion2");
		if(Middleware.isAdmin(session))
		{
			return "redirect:/admin/dashboard";
		}
		if(Middleware.isUser(session))
		{
			updateUser(session);
			User user = (User)session.getAttribute("user");
			List<SessionFormation> listSessionFormation= sessionFormationService.findAll();
			HashMap<Long,Long> session_formation_stagiaire = new HashMap<Long,Long>();
			Map<Long,String> users_names=new HashMap<Long,String>();
			for(User u : userServiceCrud.findAll())
			{
				users_names.put(u.getId(), u.getNom()+" "+u.getPrenom());
			}
			System.out.println("Stagiaires count :"+user.getClient().getList_stagiaires().size());
			for(SessionFormation x : listSessionFormation)
			{	
				for(Stagiaire stag : user.getClient().getList_stagiaires() )
				{
					if(x.getStagiaires().contains(stag))
					{
						session_formation_stagiaire.put(x.getId()+stag.getUser().getId(), stag.getUser().getId());
					}
				}

			}
			System.out.println(" events count :"+session_formation_stagiaire.size());
			planningPage.addObject("events_list",session_formation_stagiaire);	
			planningPage.addObject("users_names",users_names);	
		}
		System.out.println("Planning");
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
				event.setDescription(sf.getFormation().getDescription());
				event.setSalle(sf.getSallef().getNom());
				event.setFormation_date_debut(sf.getDate_Deb().toString());
				event.setFormation_date_fin(sf.getDate_fin().toString());
				event.setAdresse(sf.getSallef().getAdresse());
				event.setLocalisation(sf.getSallef().getLocalisation());
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
		jsonMsg =  mapper.writerWithDefaultPrettyPrinter().writeValueAsString(events);

		planningPage.addObject("sessionFormationList",sessionFormationService.findAll());


		planningPage.addObject("events",jsonMsg);
		session.setAttribute("type_formation_headers", typeFormationService.findAll());
		return planningPage;

	}

	@RequestMapping("/planning")
	public Object planning(HttpSession session) throws JsonProcessingException
	{
		ModelAndView planningPage=new ModelAndView("pages/all/PlanningFormations");
		if(Middleware.isAdmin(session))
		{
			return "redirect:/admin/dashboard";
		}
		if(Middleware.isUser(session))
		{
			updateUser(session);
			User user = (User)session.getAttribute("user");
			List<SessionFormation> listSessionFormation= sessionFormationService.findAll();
			HashMap<Long,Long> session_formation_stagiaire = new HashMap<Long,Long>();
			Map<Long,String> users_names=new HashMap<Long,String>();
			for(User u : userServiceCrud.findAll())
			{
				users_names.put(u.getId(), u.getNom()+" "+u.getPrenom());
			}
			System.out.println("Stagiaires count :"+user.getClient().getList_stagiaires().size());
			for(SessionFormation x : listSessionFormation)
			{	
				for(Stagiaire stag : user.getClient().getList_stagiaires() )
				{
					if(x.getStagiaires().contains(stag))
					{
						session_formation_stagiaire.put(x.getId()+stag.getUser().getId(), stag.getUser().getId());
					}
				}

			}
			System.out.println(" events count :"+session_formation_stagiaire.size());
			planningPage.addObject("events_list",session_formation_stagiaire);	
			planningPage.addObject("users_names",users_names);	
		}
		System.out.println("Planning");
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
				event.setDescription(sf.getFormation().getDescription());
				event.setSalle(sf.getSallef().getNom());
				event.setDuree(Integer.toString(sf.getDuree()));
				event.setFormation_date_debut(sf.getDate_Deb().toString());
				event.setFormation_date_fin(sf.getDate_fin().toString());
				event.setAdresse(sf.getSallef().getAdresse());
				event.setLocalisation(sf.getSallef().getLocalisation());
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
		jsonMsg =  mapper.writerWithDefaultPrettyPrinter().writeValueAsString(events);

		planningPage.addObject("sessionFormationList",sessionFormationService.findAll());


		planningPage.addObject("events",jsonMsg);
		session.setAttribute("type_formation_headers", typeFormationService.findAll());
		return planningPage;

	}
}
