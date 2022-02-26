package com.barisaslan.readingisgood.security;

import com.barisaslan.readingisgood.common.exceptions.UserAuthenticationException;
import com.barisaslan.readingisgood.config.security.JWTAuthenticationFilter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
 class JWTAuthenticationFilterTest {

    @InjectMocks
    private JWTAuthenticationFilter jwtAuthenticationFilter;

    @Mock
    AuthenticationManager authenticationManager;

    @Test
    void attemptAuthenticationShouldThrowException() {
       var request = new MockHttpServletRequest();

       assertThrows(
               UserAuthenticationException.class,
               () -> jwtAuthenticationFilter.attemptAuthentication(request, null)
       );
    }

}
