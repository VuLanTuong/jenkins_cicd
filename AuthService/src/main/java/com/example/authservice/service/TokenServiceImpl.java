package com.example.authservice.service;


import com.example.authservice.entity.Token;
import com.example.authservice.repositories.TokenRepository;
import com.example.authservice.util.JwtUtil;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Service
public class TokenServiceImpl implements TokenService {

    private static final String SECRET = "hey Group 19 the secrect length must be at least 256 bits" +
            " please no reveal!";

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public Token createToken(Token token) {
        return tokenRepository.saveAndFlush(token);
    }

    @Override
    public Token findByToken(String token) {
        return tokenRepository.findByToken(token);
    }

    @Override
    public boolean isAdmin(String token) {
        try {


            SignedJWT signedJWT = SignedJWT.parse(token);
            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();

            System.out.println("Claims"+ claims);

            // Verify the token signature
            JWSVerifier verifier = new MACVerifier(SECRET.getBytes());
            if (!signedJWT.verify(verifier)) {
                //
                System.out.println("Signature verification failed");
            }
            // Check if token has expired

            //
            System.out.println("Token is valid");
            List<Map<String, String>> authorities = (List<Map<String, String>>) claims.getClaim("authorities");

            for (Map<String, String> authority : authorities) {
                if (authority.containsKey("role") && authority.get("role").equals("ADMIN")) {
                    return true;
                }
            }
        } catch (ParseException | JOSEException e) {
            System.out.println(e.getMessage());

        }


        return false;
    }
}