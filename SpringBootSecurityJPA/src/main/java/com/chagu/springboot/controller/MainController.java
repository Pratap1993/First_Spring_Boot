package com.chagu.springboot.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chagu.springboot.dao.AppRoleTestDao;
import com.chagu.springboot.dao.AppUserDAO;
import com.chagu.springboot.model.AppRole;
import com.chagu.springboot.model.AppUser;
import com.chagu.springboot.utils.WebUtils;

@Controller
public class MainController {

	@Autowired
	AppUserDAO dao;

	@Autowired
	AppRoleTestDao dao2;

	@RequestMapping(value = { "/", "/welcome" }, method = RequestMethod.GET)
	public String welcomePage(Model model) {
		model.addAttribute("title", "Welcome");
		model.addAttribute("message", "This is welcome page!");
		return "welcomePage";
	}

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String adminPage(Model model, Principal principal) {
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		String userInfo = WebUtils.toString(loginedUser);
		model.addAttribute("userInfo", userInfo);
		return "adminPage";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage(Model model) {
		return "loginPage";
	}

	@RequestMapping(value = "/logoutSuccessful", method = RequestMethod.GET)
	public String logoutSuccessfulPage(Model model) {
		model.addAttribute("title", "Logout");
		return "logoutSuccessfulPage";
	}

	@RequestMapping(value = "/userInfo", method = RequestMethod.GET)
	public String userInfo(Model model, Principal principal) {
		// After user login successfully.
		String userName = principal.getName();
		System.out.println("User Name: " + userName);
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		String userInfo = WebUtils.toString(loginedUser);
		model.addAttribute("userInfo", userInfo);
		return "userInfoPage";
	}

	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String accessDenied(Model model, Principal principal) {
		if (principal != null) {
			User loginedUser = (User) ((Authentication) principal).getPrincipal();
			String userInfo = WebUtils.toString(loginedUser);
			model.addAttribute("userInfo", userInfo);
			String message = "Hi " + principal.getName() //
					+ "<br> You do not have permission to access this page!";
			model.addAttribute("message", message);
		}
		return "403Page";
	}

	@GetMapping("/myPost-{userName}")
	@ResponseBody
	public AppUser postmanInfo(@PathVariable String userName) {
		System.out.println("hdjd");
		return dao.findUserAccount(userName);
	}

	// @GetMapping("/getRoll-{rollId}")
	// @ResponseBody
	// public AppRole getRoll(@PathVariable Long rollId) {
	// AppRole roll = dao2.findById(rollId).orElse(new AppRole());
	// return roll;
	// }

	@GetMapping("/saveRoll")
	public String gsaveRoll() {
		List<AppUser> user = (List<AppUser>) dao2.findAll();
		for (AppUser appUser : user) {
			System.out.println("Name " + appUser.getUserName());
		}
		return "userInfoPage";
	}

}
