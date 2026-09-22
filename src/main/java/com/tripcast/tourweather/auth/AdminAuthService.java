package com.tripcast.tourweather.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminAuthService {

    private final String adminUsername;
    private final String adminPasswordHash;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AdminAuthService(
            @Value("${admin.username}") String adminUsername,
            @Value("${admin.password}") String adminPassword,
            PasswordEncoder passwordEncoder,
            JwtTokenProvider jwtTokenProvider
    ) {
        this.adminUsername = adminUsername;
        this.adminPasswordHash = passwordEncoder.encode(adminPassword);
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String login(String username, String password) {
        if (!adminUsername.equals(username)
                || !passwordEncoder.matches(password, adminPasswordHash)) {
            throw new InvalidCredentialsException();
        }

        return jwtTokenProvider.generateToken(username);
    }
}
