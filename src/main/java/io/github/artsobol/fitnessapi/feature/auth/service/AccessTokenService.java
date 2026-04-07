package io.github.artsobol.fitnessapi.feature.auth.service;

import io.github.artsobol.fitnessapi.feature.user.entity.User;

public interface AccessTokenService {

    String createAccessToken(User user);
}
