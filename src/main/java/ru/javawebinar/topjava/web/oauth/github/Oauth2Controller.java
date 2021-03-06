package ru.javawebinar.topjava.web.oauth.github;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.web.oauth.OauthSource;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;
import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

@Controller
@RequestMapping("/oauth/github")
public class Oauth2Controller {
    @Autowired
    private RestTemplate template;
    @Autowired
    private UserDetailsService service;
    @Autowired
    private OauthSource source;

    @RequestMapping("/authorize")
    public String authorize() {
        return "redirect:" + source.getAuthorizeUrl() + "?client_id=" + source.getClientId() + "&client_secret=" + source.getClientSecret() + "&redirect_uri=" + source.getRedirectUri() + "&state=" + source.getCode();
    }

    @RequestMapping("/callback")
    public ModelAndView authenticate(@RequestParam String code, @RequestParam String state, RedirectAttributes attr) {
        if (state.equals("topjava_csrf_token_auth")) {
            String accessToken = getAccessToken(code);
            return authorizeAndRedirect(getLogin(accessToken), getEmail(accessToken), attr);
        }
        return null;
    }

    private String getAccessToken(String code) {
        UriComponentsBuilder builder = fromHttpUrl(source.getAccessTokenUrl())
                .queryParam("client_id", source.getClientId())
                .queryParam("client_secret", source.getClientSecret())
                .queryParam("code", code)
                .queryParam("redirect_uri", source.getRedirectUri());
        ResponseEntity<JsonNode> tokenEntity = template.postForEntity(builder.build().encode().toUri(), null, JsonNode.class);
        return tokenEntity.getBody().get("access_token").asText();
    }

    private String getEmail(String accessToken) {
        UriComponentsBuilder builder = fromHttpUrl(source.getMailUrl()).queryParam("access_token", accessToken);
        ResponseEntity<JsonNode> entityEmail = template.getForEntity(builder.build().encode().toUri(), JsonNode.class);
        return entityEmail.getBody().get(0).get("email").asText();
    }

    private String getLogin(String accessToken) {
        UriComponentsBuilder builder = fromHttpUrl(source.getLoginUrl()).queryParam("access_token", accessToken);
        ResponseEntity<JsonNode> entityUser = template.getForEntity(builder.build().encode().toUri(), JsonNode.class);
        return entityUser.getBody().get("login").asText();
    }

    private ModelAndView authorizeAndRedirect(String login, String email, RedirectAttributes attr) {
        try {
            UserDetails userDetails = service.loadUserByUsername(email);
            getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
            return new ModelAndView("redirect:/meals");
        } catch (UsernameNotFoundException ex) {
            attr.addFlashAttribute("userTo", new UserTo(login, email));
            return new ModelAndView("redirect:/register");
        }
    }
}
