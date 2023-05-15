package com.pragma.powerup.plazamicroservice.domain.spi;

import java.util.Map;

public interface IUserApiPersistencePort {

    Map findOwnerById(Long idOwner);

}
