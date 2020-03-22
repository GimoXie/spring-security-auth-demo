package io.gimo.oauth2.server.resource.controller;

import io.gimo.auth.oauth2.repository.po.Client;
import io.gimo.oauth2.server.resource.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final ClientService clientService;

    @Autowired
    public TestController(ClientService clientService) {
        this.clientService = clientService;
    }

    @RequestMapping("/client")
    public Client queryClient(@RequestParam("clientId") String clientId) {
        return clientService.getByClientId(clientId);
    }
}
