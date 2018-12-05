package com.efco.formation.config;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.efco.formation.model.User;
import com.efco.formation.service.UserServiceCrud;

@Controller
public class Middleware {

	public static boolean isAdmin(HttpSession session)
	{

		if(session.getAttribute("user")!=null)
		{
			User user= (User)session.getAttribute("user");



			return (user.getRole().getId()==1);
		}
		return false;
	}

	public static boolean isUser(HttpSession session)
	{

		if(session.getAttribute("user")!=null)
		{
			User user= (User)session.getAttribute("user");

			return (user.getRole().getId()==3);
		}
		return false;
	}

	public static boolean isFormateur(HttpSession session)
	{

		if(session.getAttribute("user")!=null)
		{
			User user= (User)session.getAttribute("user");

			return (user.getRole().getId()==2);
		}
		return false;
	}

	public static boolean isConnected(HttpSession session)
	{
		if(session.getAttribute("user")!=null)
		{

			return true;
		}
		return false;
	}

	public static boolean isSalari(HttpSession session)
	{
		if(session.getAttribute("user")!=null)
		{
			User user= (User)session.getAttribute("user");

			return (user.getRole().getId()==4);
		}
		return false;
	}

	public static boolean isStagiare(HttpSession session)
	{
		if(session.getAttribute("user")!=null)
		{
			User user= (User)session.getAttribute("user");

			return (user.getRole().getId()==5);
		}
		return false;
	}




}
