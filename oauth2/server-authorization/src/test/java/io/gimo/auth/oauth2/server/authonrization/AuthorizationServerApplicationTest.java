package io.gimo.auth.oauth2.server.authonrization;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthorizationServerApplicationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void token_password() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("username", "admin");
        params.add("password", "admin");
        params.add("scope", "read");
        String response = restTemplate.withBasicAuth("baidu", "baidu").
                postForObject("/oauth/token", params, String.class);
        System.out.println(response);
    }

    @Test
    public void token_client() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "client_credentials");
        String response = restTemplate.withBasicAuth("baidu", "baidu").
                postForObject("/oauth/token", params, String.class);
        System.out.println(response);
    }

    @Test
    public void token_code() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("code", "dEZjtO");
        String response = restTemplate.withBasicAuth("baidu", "baidu").postForObject("/oauth/token", params, String.class);
        System.out.println(response);
    }
}
