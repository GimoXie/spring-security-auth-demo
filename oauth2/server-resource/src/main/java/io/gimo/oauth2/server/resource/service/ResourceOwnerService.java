package io.gimo.oauth2.server.resource.service;

import io.gimo.auth.oauth2.repository.po.ResourceOwner;
import io.gimo.oauth2.server.resource.dao.ResourceOwnerDao;
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
