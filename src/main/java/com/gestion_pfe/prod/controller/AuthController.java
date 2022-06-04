package com.gestion_pfe.prod.controller;

//import java.net.http.HttpRequest;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestion_pfe.prod.dto.AuthenticationResponse;
import com.gestion_pfe.prod.dto.LoginRequest;
import com.gestion_pfe.prod.dto.RefreshTokenRequest;
import com.gestion_pfe.prod.dto.RegistrationRequest;
import com.gestion_pfe.prod.service.AuthService;
import com.gestion_pfe.prod.service.RefreshTokenService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
//@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {
	
	private final AuthService authService;
	private final RefreshTokenService refreshTokenService;
	
	@GetMapping("/home")
	public ResponseEntity<String> home(){
		return new ResponseEntity<>("this is a home page",HttpStatus.OK);
	}
	
	@PostMapping("/signup")
	public ResponseEntity<String> signUp(@RequestBody 
			RegistrationRequest registrationRequest){
		authService.signUp(registrationRequest);
		return  new ResponseEntity<>("groupe bien enregistrer visiter votre email pour "
				+ "activer votre compte",HttpStatus.OK);
	}
	
	@GetMapping("/verifier-compte/{token}")
	public ResponseEntity<String> verifierCompte(@PathVariable String token){
		authService.verifyCompte(token);
		return new ResponseEntity<>("votre compte est activé avec succée", HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
		return authService.login(loginRequest);
	}
	
	@PostMapping("/refresh/token")
    public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.status(HttpStatus.OK).body("Refresh Token Deleted Successfully!!");
    }
}
