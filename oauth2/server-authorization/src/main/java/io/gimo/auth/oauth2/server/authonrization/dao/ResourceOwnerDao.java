package io.gimo.auth.oauth2.server.authonrization.dao;

import io.gimo.auth.oauth2.repository.po.ResourceOwner;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class ResourceOwnerDao {

    private static List<ResourceOwner> resourceOwners;

    static {
        resourceOwners = Arrays.asList(
                new ResourceOwner(1, "admin", "{bcrypt}$2a$10$4ERxsFQctVJleZZFWzJNr.d8Qu6.5qYekWd4RwrFtNUzRYCl/BfIq"),
                new ResourceOwner(2, "root", "{bcrypt}$2a$10$oytnaRT89JFzZPE10EWHNeFlAa1T/PMnq5WMmIjSH66giGZ/vYKCG"));
    }

    public ResourceOwner queryByUsername(String userName) {
        return resourceOwners.stream().filter(resourceOwner -> userName.equals(resourceOwner.getUsername())).findFirst().orElse(null);
    }
}
