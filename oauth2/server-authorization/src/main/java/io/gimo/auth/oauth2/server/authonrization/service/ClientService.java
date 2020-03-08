package io.gimo.auth.oauth2.server.authonrization.service;

import io.gimo.auth.oauth2.server.authonrization.dao.ClientDao;
import io.gimo.auth.oauth2.repository.po.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    private ClientDao clientDao;

    @Autowired
    public ClientService(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    public Client getByClientId(String clientId) {
        return clientDao.queryByClientId(clientId);
    }
}
