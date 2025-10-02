package com.newProject.first.configurations;

import com.nimbusds.jose.Algorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Configuration
public class jwtConfiguration {
    // env var
    @Value("${jwt.secret}")
    String secretKey;

    // generate the Secret Key
    @Bean
    public SecretKey secretKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    // that allow spring security to underStand the token
    @Bean
    public JwtDecoder jwtDecoder(){
        return NimbusJwtDecoder.withSecretKey(
                new SecretKeySpec(secretKey.getBytes(),"HmacSHA256")
        ).build();
    }

}
