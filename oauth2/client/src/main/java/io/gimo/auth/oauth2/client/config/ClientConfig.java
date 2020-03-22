package io.gimo.auth.oauth2.client.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@Configuration
@EnableOAuth2Client
public class ClientConfig {

    @ConfigurationProperties(prefix = "security.oauth2.client.authorization-code")
    @Bean
    public OAuth2ProtectedResourceDetails authorizationCodeClientDetails() {
        return new AuthorizationCodeResourceDetails();
    }

    @ConfigurationProperties(prefix = "security.oauth2.client.client-credentials")
    @Bean
    public OAuth2ProtectedResourceDetails clientCredentialsDetails() {
        return new ClientCredentialsResourceDetails();
    }

    @ConfigurationProperties(prefix = "security.oauth2.client.password")
    @Bean
    public OAuth2ProtectedResourceDetails passwordDetails() {
        return new ResourceOwnerPasswordResourceDetails();
    }

    @Bean
    public OAuth2RestTemplate authorizationCodeClientRestTemplate(
            @Qualifier("authorizationCodeClientDetails") OAuth2ProtectedResourceDetails resourceDetails,
            OAuth2ClientContext oauth2ClientContext) {
        return new OAuth2RestTemplate(resourceDetails, oauth2ClientContext);
    }

    @Bean
    public OAuth2RestTemplate clientCredentialsRestTemplate(
            @Qualifier("clientCredentialsDetails") OAuth2ProtectedResourceDetails resourceDetails) {
        return new OAuth2RestTemplate(resourceDetails);
    }

    @Bean
    public OAuth2RestTemplate passwordRestTemplate(
            @Qualifier("passwordDetails") OAuth2ProtectedResourceDetails resourceDetails) {
        return new OAuth2RestTemplate(resourceDetails);
    }

}
