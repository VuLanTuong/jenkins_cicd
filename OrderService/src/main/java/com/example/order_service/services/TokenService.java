package com.example.order_service.services;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import org.springframework.stereotype.Service;
import com.nimbusds.jwt.*;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

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
    public String getUsernameFromToken(String token) {
        try {
            // Parse the token to get the signed JWT object
            SignedJWT signedJWT = SignedJWT.parse(token);

            // Verify the token signature
            JWSVerifier verifier = new MACVerifier(SECRET.getBytes());
//            if (!signedJWT.verify(verifier)) {
//                System.out.println("Signature verification failed");
//                return null;
//            }

            // Get the claims from the token
            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
            // Extract the "tien" object from the claims
            Map<String, Object> tien = (Map<String, Object>) claims.getClaim("tien");

            if (tien == null) {
                System.out.println("No tien object found in token");
                return null;
            }

            // Extract the username from the "tien" object
            String username = (String) tien.get("username");
            return username;
        } catch (ParseException | JOSEException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


}




