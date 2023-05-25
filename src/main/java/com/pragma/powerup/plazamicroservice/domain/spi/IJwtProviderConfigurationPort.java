package com.pragma.powerup.plazamicroservice.domain.spi;

public interface IJwtProviderConfigurationPort {

    String getIdFromToken(String token);

    String getUserNameFromToken(String token);

    String getRoleFromToken(String token);

}
