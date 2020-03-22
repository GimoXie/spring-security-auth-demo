package io.gimo.auth.oauth2.server.authonrization.dao;

import io.gimo.auth.oauth2.repository.po.Client;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class ClientDao {

    private static List<Client> clients;

    static {
        // root帐号用于资源服务器鉴权
        clients = Arrays.asList(
                new Client(1, "baidu", "{bcrypt}$2a$10$WENWVhT9hUj0jkkyP/ojguyS4bEBwqrLDm4PaUW7qfyOEBIH0PTsu", "http://localhost:8082/authorized", "authorization_code,client_credentials,refresh_token,password,implicit","read,search"),
                new Client(2, "weibo", "{bcrypt}$2a$10$x6JsrUWMR94bnmWqRoW.GuWZXiEWCWAA5zRzwnYoE.yrfYUu7RTC.", "http://weibo.com", "implicit","read,write"),
                new Client(3, "root", "{bcrypt}$2a$10$NOe9phZNOE7mqkldFvAKh.9.D3pynX2HrbYbeg1cc4EuXB37JWl4m", "http://weibo.com", "","")
        );
    }

    public Client queryByClientId(String clientId) {
        return clients.stream().filter(client -> clientId.equals(client.getClientId())).findFirst().orElse(null);
    }
}
