package com.gestion_pfe.prod.security;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.time.Instant;
import java.util.Date;


import static io.jsonwebtoken.Jwts.parser;
import static java.util.Date.from;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.gestion_pfe.prod.exceptions.PFE_RegistrationException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class JwtProvider {

	private KeyStore keyStore;
   @Value("${jwt.expiration.time}")
   private Long jwtExpirationInMillis;

   @PostConstruct
   public void init() {
       try {
           keyStore = KeyStore.getInstance("JKS");
           InputStream resourceAsStream = getClass().getResourceAsStream("/springblog.jks");
           keyStore.load(resourceAsStream, "secret".toCharArray());
       } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
           throw new PFE_RegistrationException("Exception occurred while loading keystore", e);
       }

   }

   public String generateToken(Authentication authentication) {
       org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
       return Jwts.builder()
               .setSubject(principal.getUsername())
               .setIssuedAt(from(Instant.now()))
               .signWith(getPrivateKey())
               .setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis+(19*60*60))))
               .compact();
   }

   public String generateTokenWithUserName(String email) {
       return Jwts.builder()
               .setSubject(email)
               .setIssuedAt(from(Instant.now()))
               .signWith(getPrivateKey())
               .setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis)))
               .compact();
   }

   private PrivateKey getPrivateKey() {
       try {
           return (PrivateKey) keyStore.getKey("springblog", "secret".toCharArray());
       } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
           throw new PFE_RegistrationException("Exception occured while retrieving public key from keystore", e);
       }
   }

//   @SuppressWarnings("deprecation")
	public boolean validateToken(String jwt) {
       parser().setSigningKey(getPublickey()).parseClaimsJws(jwt);
       return true;
   }

   private PublicKey getPublickey() {
       try {
           return keyStore.getCertificate("springblog").getPublicKey();
       } catch (KeyStoreException e) {
           throw new PFE_RegistrationException("Exception occured while " +
                   "retrieving public key from keystore", e);
       }
   }

   public String getUsernameFromJwt(String token) {
//       @SuppressWarnings("deprecation")
		Claims claims = parser()
               .setSigningKey(getPublickey())
               .parseClaimsJws(token)
               .getBody();

       return claims.getSubject();
   }

   public Long getJwtExpirationInMillis() {
       return jwtExpirationInMillis;
   }
}
