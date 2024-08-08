package com.example.training.service;

import com.example.training.role.Role;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration
@ExtendWith(MockitoExtension.class)
class JwtServiceTest {
    String token;

    // Créez un faux objet Claims
    Claims claims;
    Function<Claims, String> claimsResolver;

    // Définir le comportement de la méthode extractAllClaims
    JwtService jwtService;
    UserDetails userDetails;

    boolean isValid;

    @BeforeEach
    void setUp() {
        token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0YXRhMkBnbWFpbC5jb20iLCJpYXQiOjE3MTExOTY2MjksImV4cCI6MTcxMTI4MzAyOX0.ssSbvk8isMNXBi-__X2N6S9Y7YpYyZHz2ggguU8aHf4";
        claims =  Mockito.mock(Claims.class);
        jwtService  = new JwtService();
        userDetails=new User("tata2@gmail.com", "kkdms1165956q**/",
                List.of(new SimpleGrantedAuthority(Role.TEST.name())));
    }

    @AfterEach
    void tearDown() {
        token = null;
        claims = null;
        jwtService = null;
    }

    @Test
    void extractUsername() {
        jwtService.extractUsername(token);
        assertEquals("tata2@gmail.com", userDetails.getUsername());
    }

    @Test
    void extractClaim() {

        jwtService.extractUsername(token);

        // Créez une fausse fonction claimsResolver
        claimsResolver = Claims::getSubject;
        // Appelez la méthode extractClaim avec le token et la fonction claimsResolver
        String extractedClaim = jwtService.extractClaim(token, claimsResolver);
        // Vérifiez si le résultat extrait est celui attendu
        assertEquals(userDetails.getUsername(), extractedClaim);
    }

    @Test
    void generateToken() {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", "TEST");
        String token2 = jwtService.generateToken(extraClaims, userDetails);
        assertNotNull(token2);
    }

    @Test
    void isTokenValid() {
        jwtService.extractUsername(token);

        // Créez une fausse fonction claimsResolver
        claimsResolver = Claims::getSubject;
        jwtService.extractExpiration(token);
        isValid=jwtService.isTokenValid(token, userDetails);
        assertTrue(isValid);
    }

    @Test
    void extractExpiration() {
        assertNotNull(jwtService.extractExpiration(token));
    }
}