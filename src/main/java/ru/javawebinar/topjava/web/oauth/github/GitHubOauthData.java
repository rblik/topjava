package ru.javawebinar.topjava.web.oauth.github;

public class GitHubOauthData {
    protected static final String AUTHORIZE_URL = "https://github.com/login/oauth/authorize";
    protected static final String ACCESS_TOKEN_URL = "https://github.com/login/oauth/access_token";
    protected static final String GET_EMAIL_URL = "https://api.github.com/user/emails";
    protected static final String GET_LOGIN_URL = "https://api.github.com/user";
    protected static final String CLIENT_ID = "53cbe0812f6d580c9c2e";
    protected static final String CLIENT_SECRET = "6db047074c0960ed716b3247c53be9e16d86c390";
    protected static final String REDIRECT_URI = "http://rblik-topjava.herokuapp.com/oauth/github/callback";
    protected static final String CODE = "topjava_csrf_token_auth";
}
