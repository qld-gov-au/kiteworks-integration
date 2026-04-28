package au.gov.qld.ssq.kiteworks;

import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Instant;
import java.util.*;
import java.util.function.Consumer;

/**
 * Interceptor for Kiteworks API requests that uses OAuth 2.0 with a JWT-based authorization code.
 *
 * Ensure "JWT Authorization" is enabled
 *
 * Only use this class for high trust applications that need to access the Kiteworks API as different users. Use with caution.
 *
 * Please use {@link KiteworksUserCredentialOAuthInterceptor} for low trust applications that need to access the Kiteworks API as a single user.
 * Or If your site can allow user interactions then Authorization code style https://developer.kiteworks.com/api-overview/authorization-code.htm
 *
 * Please see the Kiteworks API documentation for more information on OAuth 2.0 authorization.
 * <a href="https://developer.kiteworks.com/api-overview/jwt-authorization.htm">JWT Authorization flow</a>
 *
 * Note: JWT authentication flow allows for impersonation of Kiteworks users. It is intended to be used only by clients operating in secure environments.
 * Using it for publicly available clients should be considered a security risk.
 *
 */
public class KitworksJwtOAuthInterceptor extends AbstractOAuthInterceptor implements Consumer<HttpRequest.Builder> {

    private static final Logger LOG = LoggerFactory.getLogger(KitworksJwtOAuthInterceptor.class);

    private final KiteworksConfig config;

    private static final int validitySeconds = 5 * 60; //jwt limited to 5min max

    /**
     * @param config        Object: KiteworksConfig config
     *                      Requires:
     *                      ClientID
     *                      ClientSecret
     *                      Issuer: This must match the iss (issuer) attribute of the JWT.
     *                      PrivateKey: The PrivateKey which will match the public key loaded into ClientID insideKiteworks which will be used to verify the JWT signature.
     *                      Audience: This must match the aud (audience) attribute of the JWT. It can be the URL of the Kiteworks machine or any ID specified by the JWT issuer.
     *                      Username: Subject (UID Attribute) Attribute in JWT indicating the user email address. Inside kiteworks set to 'sub'
     */
    public KitworksJwtOAuthInterceptor(KiteworksConfig config) {
        if (isEmpty(config.getClientId()) || isEmpty(config.getClientSecret())) {
            throw new IllegalArgumentException("Client ID and secret must be provided");
        }
        if (isEmpty(config.getIssuer())
                || isEmpty(config.getPrivateKey())
                || isEmpty(config.getUsername())
                || isEmpty(config.getAudience())) {
            throw new IllegalArgumentException("Issuer, PrivateKey, Username(email), Audience must be provided");
        }
        if (isEmpty(config.getScope())) {
            throw new IllegalArgumentException("Client app scopes must be provided");
        }
        this.config = config;
    }

    @Override
    protected KiteworksConfig getConfig() {
        return config;
    }

    /**
     * Requests an OAuth 2.0 access token using the JWT Bearer assertion.
     */
    protected HttpResponse<String> fetchAccessTokenInternal() throws IOException, InterruptedException, GeneralSecurityException {

        // Create the signed JWT
        String assertion = createJwtAssertion(validitySeconds);

        Map<String, String> formData = new HashMap<>();
        formData.put("client_id", config.getClientId());
        formData.put("client_secret", config.getClientSecret());
        formData.put("grant_type", "urn:ietf:params:oauth:grant-type:jwt-bearer");
        formData.put("assertion", assertion);
        formData.put("scope", config.getScope());

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(config.getAccessTokenUri()))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(getFormDataAsString(formData)))
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() >= 400 && response.statusCode() <= 499) {
            LOG.error("Failed to fetch access token, incorrect username or jwt configuration?, username: {}..., client: {}... status code: {} body: {}",
                    (Objects.isNull(config.getUsername())) ? "Is Null" : config.getUsername().substring(0, config.getUsername().length() / 2),
                    (Objects.isNull(config.getClientId())) ? "Is Null" : config.getClientId().substring(0, config.getClientId().length() / 2),
                    response.statusCode(), response.body());
            throw new RuntimeException("Failed to fetch access token, status code: " + response.statusCode() + " body: " + response.body());
        }

        return response;
    }

    /**
     * Creates and signs a JWT assertion using the RSA private key.
     */
    public String createJwtAssertion(long validitySeconds) throws GeneralSecurityException {
        Instant now = Instant.now();

        return Jwts.builder()
                .issuer(config.getIssuer())
                .subject(config.getUsername())
                .audience().add(config.getAudience()).and()
                .issuedAt(Date.from(now))
                .notBefore(Date.from(now))
                .expiration(Date.from(now.plusSeconds(validitySeconds)))
                .id(UUID.randomUUID().toString())
                .signWith(parsePrivateKey(config.getPrivateKey()), Jwts.SIG.RS256)
                .compact();
    }

    /**
     * Helper to convert PEM string to PrivateKey object.
     * note, this is vanilla and expects private key to already be decrypted, i.e. no bcprov-jdk18on.
     *
     */
    private PrivateKey parsePrivateKey(String pem) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String privateKeyPemClean = pem
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s+", "");

        try {
            byte[] encoded = Base64.getDecoder().decode(privateKeyPemClean);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(keySpec);
        } catch (Exception e) {
            LOG.error("Failed to parse private key, ", e);
            throw e;
        }
    }
}
