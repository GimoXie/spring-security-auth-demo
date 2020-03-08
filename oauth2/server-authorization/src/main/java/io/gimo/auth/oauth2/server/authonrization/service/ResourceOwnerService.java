package io.gimo.auth.oauth2.server.authonrization.service;

import io.gimo.auth.oauth2.server.authonrization.dao.ResourceOwnerDao;
import io.gimo.auth.oauth2.repository.po.ResourceOwner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceOwnerService {

    private ResourceOwnerDao resourceOwnerDao;

    @Autowired
    public ResourceOwnerService(ResourceOwnerDao resourceOwnerDao) {
        this.resourceOwnerDao = resourceOwnerDao;
    }

    public ResourceOwner getByUsername(String userName) {
        return resourceOwnerDao.queryByUsername(userName);
    }

}
