package io.gimo.auth.oauth2.server.authonrization.config;

import io.gimo.auth.oauth2.repository.po.Client;
import io.gimo.auth.oauth2.server.authonrization.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

/**
 * document :
 * https://projects.spring.io/spring-security-oauth/docs/oauth2.html
 * https://docs.spring.io/spring-security-oauth2-boot/docs/2.2.5.RELEASE/reference/html5/
 * https://github.com/spring-projects/spring-security/blob/5.3.x/samples/boot/oauth2authorizationserver/src/main/java/sample/AuthorizationServerConfiguration.java
 * https://www.cnblogs.com/LOVE0612/p/9913336.html
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final ClientService clientService;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final TokenStore tokenStore;
    private final JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    public AuthorizationServerConfig(ClientService clientService, UserDetailsService userDetailsService,
                                     PasswordEncoder passwordEncoder, TokenStore tokenStore,
                                     JwtAccessTokenConverter jwtAccessTokenConverter) {
        this.clientService = clientService;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.tokenStore = tokenStore;
        this.jwtAccessTokenConverter = jwtAccessTokenConverter;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.passwordEncoder(passwordEncoder)
                //.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService());
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.userDetailsService(userDetailsService)
                .tokenStore(tokenStore)
                .accessTokenConverter(jwtAccessTokenConverter)
                /*.authorizationCodeServices(authorizationCodeServices)*/
                .authenticationManager(authenticationManager());
    }

    public ClientDetailsService clientDetailsService() {
        return clientId -> {
            Client client = clientService.getByClientId(clientId);
            if (client == null) {
                throw new NoSuchClientException(clientId);
            }
            BaseClientDetails clientDetails = new BaseClientDetails();
            clientDetails.setClientId(client.getClientId());
            clientDetails.setClientSecret(client.getClientSecret());
            clientDetails.setRegisteredRedirectUri(new HashSet<>(Collections.singletonList(client.getRedirectUrl())));
            // spring security 默认有如下方式的类型 authorization_code implicit password client_credentials refresh_token
            clientDetails.setAuthorizedGrantTypes(Arrays.asList(client.getGrantType().split(",")));
            clientDetails.setScope(Arrays.asList(client.getScope().split(",")));
            //clientDetails.setAccessTokenValiditySeconds(600_000);
            //clientDetails.setRefreshTokenValiditySeconds(600_000);
            return clientDetails;
        };
    }

    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(Collections.singletonList(provider));
    }

}
