package io.gimo.auth.oauth2.server.authonrization.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;

// https://www.cnblogs.com/guos/p/11661972.html 非对称加密方式
@Configuration
public class JwtConfig {

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        /* 对称加密实现
        accessTokenConverter.setSigningKey("Gimo");*/
        KeyStoreKeyFactory factory =  new KeyStoreKeyFactory(new ClassPathResource("oauth2.jks"), "oauth2".toCharArray());
        KeyPair keyPair = factory.getKeyPair("oauth2");
        accessTokenConverter.setKeyPair(keyPair);
        return accessTokenConverter;
    }
}
