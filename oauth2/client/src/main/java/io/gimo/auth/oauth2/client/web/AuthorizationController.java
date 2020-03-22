package io.gimo.auth.oauth2.client.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;


@Controller
public class AuthorizationController {

	private String messagesBaseUri = "http://localhost:8081/client?clientId=baidu";

	@Autowired
	private OAuth2RestTemplate authorizationCodeClientRestTemplate;

	@Autowired
	private OAuth2RestTemplate clientCredentialsRestTemplate;

	@Autowired
	private OAuth2RestTemplate passwordRestTemplate;

	@GetMapping(value = "/authorize", params = "grant_type=authorization_code")
	public String authorization_code_grant(Model model) {
		String messages = this.authorizationCodeClientRestTemplate.getForObject(this.messagesBaseUri, String.class);
		model.addAttribute("messages", messages);
		return "index";
	}

	@GetMapping("/authorized")		// registered redirect_uri for authorization_code
	public String authorized(Model model) {
		String messages = this.authorizationCodeClientRestTemplate.getForObject(this.messagesBaseUri, String.class);
		model.addAttribute("messages", messages);
		return "index";
	}

	@GetMapping(value = "/authorize", params = "grant_type=client_credentials")
	public String client_credentials_grant(Model model) {
		String messages = this.clientCredentialsRestTemplate.getForObject(this.messagesBaseUri, String.class);
		model.addAttribute("messages", messages);
		return "index";
	}

	@PostMapping(value = "/authorize", params = "grant_type=password")
	public String password_grant(Model model, HttpServletRequest request) {
		ResourceOwnerPasswordResourceDetails passwordResourceDetails =
				(ResourceOwnerPasswordResourceDetails) this.passwordRestTemplate.getResource();
		passwordResourceDetails.setUsername(request.getParameter("username"));
		passwordResourceDetails.setPassword(request.getParameter("password"));

		String messages = this.passwordRestTemplate.getForObject(this.messagesBaseUri, String.class);
		model.addAttribute("messages", messages);

		// Never store the user's credentials
		passwordResourceDetails.setUsername(null);
		passwordResourceDetails.setPassword(null);

		return "index";
	}
}