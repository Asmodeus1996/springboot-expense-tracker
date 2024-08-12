package com.tracker.expense.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

import static io.jsonwebtoken.Jwts.parser;

@Service
public class JwtProvider {

    private KeyStore keyStore;

    @PostConstruct
    public void init() {
        try {
            keyStore = keyStore.getInstance("JKS");
            InputStream security = getClass().getResourceAsStream("/keystore.jks");
            keyStore.load(security, "password".toCharArray());
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException e) {
            throw new RuntimeException("Exception while loading keystore " + e);
        }
    }

    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(user.getUsername())
                .signWith(getPrivateKey()).compact();
    }

    private PrivateKey getPrivateKey() {
        try {
            return (PrivateKey) keyStore.getKey("expense", "password".toCharArray());
        } catch (UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException e) {
            throw new RuntimeException("Exception while getting key " + e);
        }
    }

    public boolean validateToken(String jwt) {
        parseToken(jwt);
        return true;
    }

    private Jws<Claims> parseToken(String jwt) {
        return parser().setSigningKey(getPublicKey()).parseClaimsJws(jwt);
    }
    private PublicKey getPublicKey() {
        try {
            return keyStore.getCertificate("expense").getPublicKey();
        } catch (KeyStoreException e) {
            throw new RuntimeException("Exception while getting public key " + e);
        }
    }

    public String getUserNameFromToken(String jwtFromRequest) {

        return parseToken(jwtFromRequest).getBody().getSubject();

    }
}
