package au.gov.qld.ssq.kiteworks;

import java.util.Objects;

public class KiteworksConfig {

    private String baseUri; // e.g. Kiteworks base uri  https://kiteworks.dcj.nsw.gov.au/
    private String clientId;
    private String clientSecret;
    private String signatureKey;
    private String userId; // e.g. the uuid of the user
    private String clientAppScopes; // e.g. 'folders/* files/* mail/*' or '*/*/*'
    private String redirectUri; // e.g. what you configured for the client redirect url
    private String accessTokenUri; // e.g. defaults to "baseURI + oauth/token" i.e. https://kiteworks.dcj.nsw.gov.au/oauth/token
    private String userAgent; // e.g. kiteworks-client 1.0
    private String kiteworksApiVersion = "24"; //default latest as of 2024/07


    public KiteworksConfig() {
        //default
    }
    public KiteworksConfig(
        String baseUri, String clientId, String clientSecret, String signatureKey,
        String userId, String clientAppScopes, String redirectUri, String accessTokenUri, String userAgent, String kiteworksApiVersion) {
        this.baseUri = baseUri;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.signatureKey = signatureKey;
        this.userId = userId;
        this.clientAppScopes = clientAppScopes;
        this.redirectUri = redirectUri;
        this.accessTokenUri = accessTokenUri;
        this.userAgent = userAgent;
        this.kiteworksApiVersion = kiteworksApiVersion;
    }

    public void setBaseUri(String baseUri) {
        this.baseUri = baseUri;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public void setSignatureKey(String signatureKey) {
        this.signatureKey = signatureKey;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setClientAppScopes(String clientAppScopes) {
        this.clientAppScopes = clientAppScopes;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public void setAccessTokenUri(String accessTokenUri) {
        this.accessTokenUri = accessTokenUri;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public void setKiteworksApiVersion(String kiteworksApiVersion) {
        this.kiteworksApiVersion = kiteworksApiVersion;
    }

    public String getBaseUri() {
        return baseUri;
    }

    public KiteworksConfig withBaseUri(String baseUri) {
        this.baseUri = baseUri;
        return this;
    }

    public String getClientId() {
        return clientId;
    }

    public KiteworksConfig withClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public KiteworksConfig withClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }

    public String getSignatureKey() {
        return signatureKey;
    }

    public KiteworksConfig withSignatureKey(String signatureKey) {
        this.signatureKey = signatureKey;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public KiteworksConfig withUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getClientAppScopes() {
        return clientAppScopes;
    }

    public KiteworksConfig withClientAppScopes(String clientAppScopes) {
        this.clientAppScopes = clientAppScopes;
        return this;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public KiteworksConfig withRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
        return this;
    }

    public String getAccessTokenUri() {
        if (Objects.nonNull(accessTokenUri)) {
            return accessTokenUri;
        } else {
            return baseUri + "oauth/token";
        }
    }

    public KiteworksConfig withAccessTokenUri(String accessTokenUri) {
        this.accessTokenUri = accessTokenUri;
        return this;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public KiteworksConfig withUserAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    public String getKiteworksApiVersion() {
        return kiteworksApiVersion;
    }

    public KiteworksConfig withKiteworksApiVersion(String kiteworksApiVersion) {
        this.kiteworksApiVersion = kiteworksApiVersion;
        return this;
    }
}
