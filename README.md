## Kiteworks integration

This library is a swagger generated item that connects to a kiteworks private server.
It is made in two parts, a native java 21 client and a spring-boot wrapper when required to
DI wire into a spring boot application.

### Testing

Please look up 1password for test secrets in note "kiteworks-integration"

example command for secretes required for integration testing:
```bash
KITEWORKS_CLIENT_APP_SCOPES="" KITEWORKS_USER_ID="" KITEWORKS_CLIENT_SECRET="" KITEWORKS_USER_AGENT="" KITEWORKS_ACCESS_TOKEN_URI="https://${SERVER}/oauth/token" KITEWORKS_CLIENT_ID="" KITEWORKS_SIGNATURE_KEY="" KITEWORKS_BASE_URI="https://SERVER/" KITEWORKS_REDIRECT_URI="https://REDIRECTURI" mvn verify
```

The system is designed to skip Integration Testing if secrets are not available.
