package com.nnk.springboot.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.security.Principal;
import java.util.Map;

@Controller
@SessionAttributes("userInfo")
public class HomeController
{
	private static final Logger logger = LogManager.getLogger("HomeController");

	private final OAuth2AuthorizedClientService authorizedClientService;

	public HomeController(OAuth2AuthorizedClientService authorizedClientService) {
		this.authorizedClientService = authorizedClientService;
	}


	@RequestMapping("/admin/home")
	public String adminHome(Model model)
	{
		logger.info("New request: show admin homepage in the view ");
		return "redirect:/bidList/list";
	}

	@RequestMapping("/*")
	public String getUserInfo(Principal user, Model model) {
		StringBuffer userInfo= new StringBuffer();
		if(user instanceof UsernamePasswordAuthenticationToken){
			userInfo.append(getUsernamePasswordLoginInfo(user));
		}
		else if(user instanceof OAuth2AuthenticationToken){
			userInfo.append(getOauth2LoginInfo(user));
		}
		model.addAttribute("userInfo", userInfo.toString());
		return "home";
	}

	private StringBuffer getOauth2LoginInfo(Principal user){
		StringBuffer protectedInfo = new StringBuffer();
		OAuth2AuthenticationToken authToken = ((OAuth2AuthenticationToken) user);
		OAuth2AuthorizedClient authClient = this.authorizedClientService.loadAuthorizedClient(authToken.getAuthorizedClientRegistrationId(), authToken.getName());
		if(authToken.isAuthenticated()){
			Map<String,Object> userAttributes = ((DefaultOAuth2User) authToken.getPrincipal()).getAttributes();
			String userToken = authClient.getAccessToken().getTokenValue();
			protectedInfo.append(userAttributes.get("name"));
		}
		else{
			protectedInfo.append("NA");
		}
		return protectedInfo;
	}

	private StringBuffer getUsernamePasswordLoginInfo(Principal user)
	{
		StringBuffer usernameInfo = new StringBuffer();

		UsernamePasswordAuthenticationToken token = ((UsernamePasswordAuthenticationToken) user);
		if(token.isAuthenticated()){
			User u = (User) token.getPrincipal();
			usernameInfo.append(u.getUsername());
		}
		else{
			usernameInfo.append("NA");
		}
		return usernameInfo;
	}


}
