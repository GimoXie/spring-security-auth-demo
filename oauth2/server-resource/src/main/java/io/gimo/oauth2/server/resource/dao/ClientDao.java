package io.gimo.oauth2.server.resource.dao;

import io.gimo.auth.oauth2.repository.po.Client;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class ClientDao {

    private static List<Client> clients;

    static {
        clients = Arrays.asList(
                new Client(1, "baidu", "{bcrypt}$2a$10$WENWVhT9hUj0jkkyP/ojguyS4bEBwqrLDm4PaUW7qfyOEBIH0PTsu", "http://baidu.com", "authorization_code,client_credentials,refresh_token,password,implicit","read,search"),
                new Client(2, "weibo", "{bcrypt}$2a$10$x6JsrUWMR94bnmWqRoW.GuWZXiEWCWAA5zRzwnYoE.yrfYUu7RTC.", "http://weibo.com", "implicit","read,write")
        );
    }

    public Client queryByClientId(String clientId) {
        return clients.stream().filter(client -> clientId.equals(client.getClientId())).findFirst().orElse(null);
    }
}
