package io.github.artsobol.fitnessapi.feature.auth.service;

import io.github.artsobol.fitnessapi.feature.auth.dto.request.RegistrationRequest;
import io.github.artsobol.fitnessapi.feature.auth.dto.request.SessionMetadata;
import io.github.artsobol.fitnessapi.feature.auth.dto.response.AuthResponse;

public interface RegistrationService {

    AuthResponse register(RegistrationRequest request, SessionMetadata meta);
}
