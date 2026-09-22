package com.tripcast.tourweather.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class JwtTokenProviderTest {

    private final JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(
            "test-jwt-secret-key-for-signing-minimum-256-bits-required-000",
            60
    );

    @Test
    void 발급한_토큰에서_사용자명을_그대로_복원한다() {
        String token = jwtTokenProvider.generateToken("test-admin");

        assertTrue(jwtTokenProvider.isValid(token));
        assertEquals("test-admin", jwtTokenProvider.getUsername(token));
    }

    @Test
    void 형식이_잘못된_토큰은_유효하지_않다() {
        assertFalse(jwtTokenProvider.isValid("not-a-jwt"));
    }

    @Test
    void 다른_비밀키로_서명된_토큰은_유효하지_않다() {
        JwtTokenProvider otherProvider = new JwtTokenProvider(
                "different-jwt-secret-key-for-signing-minimum-256-bits-000",
                60
        );
        String token = otherProvider.generateToken("test-admin");

        assertFalse(jwtTokenProvider.isValid(token));
    }
}
