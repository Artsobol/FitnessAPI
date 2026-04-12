package io.github.artsobol.fitnessapi.feature.auth.refreshtoken.service;

import io.github.artsobol.fitnessapi.feature.auth.refreshtoken.dto.request.CreateRefreshTokenRequest;
import io.github.artsobol.fitnessapi.feature.auth.refreshtoken.dto.request.RotateRefreshTokenRequest;
import io.github.artsobol.fitnessapi.feature.auth.refreshtoken.dto.response.RefreshTokenRotationResult;

public interface RefreshTokenService {

    String createRefreshToken(CreateRefreshTokenRequest request);

    RefreshTokenRotationResult rotate(RotateRefreshTokenRequest request);
}
