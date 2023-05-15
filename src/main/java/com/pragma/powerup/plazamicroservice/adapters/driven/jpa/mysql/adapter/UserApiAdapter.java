package com.pragma.powerup.plazamicroservice.adapters.driven.jpa.mysql.adapter;


import com.pragma.powerup.plazamicroservice.domain.spi.IUserApiPersistencePort;

import java.util.Map;

public class UserApiAdapter implements IUserApiPersistencePort {



    @Override
    public Map findOwnerById(Long idOwner) {
        return null;
    }
}
