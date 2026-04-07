package io.github.artsobol.fitnessapi.feature.auth.service;

import io.github.artsobol.fitnessapi.feature.auth.dto.response.AuthResponse;
import io.github.artsobol.fitnessapi.feature.refreshtoken.dto.request.RotateRefreshTokenRequest;

public interface RefreshService {

    AuthResponse refresh(RotateRefreshTokenRequest request);
}
