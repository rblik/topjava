package ru.javawebinar.topjava.web.user;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.web.oauth.GitHubSource;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;
import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

@Controller
public class Oauth2Controller {
    @Autowired
    private UserDetailsService service;
    @Autowired
    private GitHubSource source;
    @Autowired
    private RestTemplate template;

    private static final String AUTHORIZE_URL = "https://github.com/login/oauth/authorize";
    private static final String ACCESS_TOKEN_URL = "https://github.com/login/oauth/access_token";

    @RequestMapping("/oauth/github/authorize")
    public String authorize(@RequestParam String action) {
        String code = "register".equals(action) ? "topjava_csrf_token_register" : "topjava_csrf_token_auth";
        return "redirect:" + AUTHORIZE_URL + "?client_id=" + source.getClientId() + "&client_secret=" + source.getClientSecret() + "&redirect_uri=" + source.getRedirectUri() + "&scope=" + source.getScope() + "&state=" + code;
    }

    @RequestMapping("/oauth/github/callback")
    public ModelAndView authenticate(@RequestParam String code, @RequestParam String state, HttpServletRequest request) {
        if (state.equals("topjava_csrf_token_register")) {
//            request.getParameterMap() = new HashMap<>();
            UserTo to = getUserTo(code);
            return new ModelAndView("profile").addObject("userTo", to).addObject("register", true).addObject("social", true);
        } else if (state.equals("topjava_csrf_token_auth")) {
            return getModelAndView(code, request);
        }
        return null;
    }

    private ModelAndView getModelAndView(String code, HttpServletRequest request) {
//        getting access token
        UriComponentsBuilder builder = fromHttpUrl(ACCESS_TOKEN_URL)
                .queryParam("client_id", source.getClientId())
                .queryParam("client_secret", source.getClientSecret())
                .queryParam("code", code);
        ResponseEntity<JsonNode> tokenEntity = template.postForEntity(builder.build().encode().toUri(), null, JsonNode.class);
        String accessToken = tokenEntity.getBody().get("access_token").asText();

//        getting user email
        builder = fromHttpUrl("https://api.github.com/user/emails").queryParam("access_token", accessToken);
        ResponseEntity<JsonNode> entityEmail = template.getForEntity(builder.build().encode().toUri(), JsonNode.class);
        String email = entityEmail.getBody().get(0).get("email").asText();

//        authenticate in context
        try {
            UserDetails userDetails = service.loadUserByUsername(email);
            getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
            return new ModelAndView("redirect:/meals");

        } catch (UsernameNotFoundException ex) {
            request.getSession().setAttribute("SPRING_SECURITY_LAST_EXCEPTION", new BadCredentialsException("Bad credentials"));
            return new ModelAndView("login").addObject("error", true);
        }
    }

    private UserTo getUserTo(String code) {
        UriComponentsBuilder builder = fromHttpUrl(ACCESS_TOKEN_URL)
                .queryParam("client_id", source.getClientId())
                .queryParam("client_secret", source.getClientSecret())
                .queryParam("code", code);
        ResponseEntity<JsonNode> tokenEntity = template.postForEntity(builder.build().encode().toUri(), null, JsonNode.class);
        String accessToken = tokenEntity.getBody().get("access_token").asText();

        builder = fromHttpUrl("https://api.github.com/user/emails").queryParam("access_token", accessToken);
        ResponseEntity<JsonNode> entityEmail = template.getForEntity(builder.build().encode().toUri(), JsonNode.class);
        String email = entityEmail.getBody().get(0).get("email").asText();

//        getting user login
        builder = fromHttpUrl("https://api.github.com/user").queryParam("access_token", accessToken);
        ResponseEntity<JsonNode> entityUser = template.getForEntity(builder.build().encode().toUri(), JsonNode.class);
        String login = entityUser.getBody().get("login").asText();

        return new UserTo(login, email);
    }
}
