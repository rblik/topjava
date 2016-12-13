package ru.javawebinar.topjava.web.oauth.github;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.UserTo;

import java.util.List;

import static java.util.Arrays.asList;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;
import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;
import static ru.javawebinar.topjava.web.oauth.github.GitHubOauthData.*;

@Controller
@RequestMapping("/oauth/github")
public class Oauth2Controller {
    @Autowired
    private RestTemplate template;
    @Autowired
    private UserService userService;

    @RequestMapping("/authorize")
    public String authorize(@RequestParam String action) {
        return "redirect:" + AUTHORIZE_URL + "?client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET + "&redirect_uri=" + REDIRECT_URI + "&scope=" + SCOPE + "&state=" + CODE;
    }

    @RequestMapping("/callback")
    public ModelAndView authenticate(@RequestParam String code, @RequestParam String state) {
        if (state.equals("topjava_csrf_token_auth")) {
            String accessToken = getAccessToken(code);
            return authorizeAndRedirect(getLoginAndId(accessToken), getEmail(accessToken));
        }
        return null;
    }

    private String getAccessToken(String code) {
        UriComponentsBuilder builder = fromHttpUrl(ACCESS_TOKEN_URL)
                .queryParam("client_id", CLIENT_ID)
                .queryParam("client_secret", CLIENT_SECRET)
                .queryParam("code", code)
                .queryParam("redirect_uri", REDIRECT_URI);
        ResponseEntity<JsonNode> tokenEntity = template.postForEntity(builder.build().encode().toUri(), null, JsonNode.class);
        return tokenEntity.getBody().get("access_token").asText();
    }

    private String getEmail(String accessToken) {
        UriComponentsBuilder builder = fromHttpUrl(GET_EMAIL_URL).queryParam("access_token", accessToken);
        ResponseEntity<JsonNode> entityEmail = template.getForEntity(builder.build().encode().toUri(), JsonNode.class);
        return entityEmail.getBody().get(0).get("email").asText();
    }

    private List<String> getLoginAndId(String accessToken) {
        UriComponentsBuilder builder = fromHttpUrl(GET_LOGIN_URL).queryParam("access_token", accessToken);
        ResponseEntity<JsonNode> entityUser = template.getForEntity(builder.build().encode().toUri(), JsonNode.class);
        String login = entityUser.getBody().get("login").asText();
        String id = entityUser.getBody().get("id").asText();
        return asList(login, id);
    }

    private ModelAndView authorizeAndRedirect(List<String> loginAndId, String email) {
        UserDetails userDetails = userService.loadOrSaveByEmail(email, new UserTo(loginAndId.get(0), email, CLIENT_ID + loginAndId.get(1)));
        getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
        return new ModelAndView("redirect:/meals");
    }
}
