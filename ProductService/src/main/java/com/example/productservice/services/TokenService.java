package com.example.productservice.services;


import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import org.springframework.stereotype.Service;
import com.nimbusds.jwt.*;

import javax.naming.AuthenticationException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class TokenService {
    private static final String SECRET = "hey Group 19 the secrect length must be at least 256 bits" +
            " please no reveal!";


    public boolean isAdmin(String token){
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
    public void readToken(String token) {
        try {
            // Parse token and get the claims

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
        } catch (ParseException | JOSEException e) {
            System.out.println(e.getMessage());



        }
    }

}




