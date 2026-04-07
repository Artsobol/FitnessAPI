package io.github.artsobol.fitnessapi.feature.auth.service;

import io.github.artsobol.fitnessapi.feature.auth.dto.request.LoginRequest;
import io.github.artsobol.fitnessapi.feature.auth.dto.request.SessionMetadata;
import io.github.artsobol.fitnessapi.feature.auth.dto.response.AuthResponse;

public interface LoginService {

    AuthResponse login(LoginRequest request, SessionMetadata meta);
}
