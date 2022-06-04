package com.gestion_pfe.prod.service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestion_pfe.prod.dto.AuthenticationResponse;
import com.gestion_pfe.prod.dto.LoginRequest;
import com.gestion_pfe.prod.dto.RefreshTokenRequest;
import com.gestion_pfe.prod.dto.RegistrationRequest;
import com.gestion_pfe.prod.exceptions.PFE_RegistrationException;
import com.gestion_pfe.prod.model.Groupe;
import com.gestion_pfe.prod.model.Departement;

import com.gestion_pfe.prod.model.NotificationMail;
//import com.gestion_pfe.prod.model.Prof;
import com.gestion_pfe.prod.model.VerificationToken;
import com.gestion_pfe.prod.repositoy.DepartementRepository;

import com.gestion_pfe.prod.repositoy.GroupeRepository;
//import com.gestion_pfe.prod.repositoy.ProfRepository;
import com.gestion_pfe.prod.repositoy.VerificationTokenRepository;
import com.gestion_pfe.prod.security.JwtProvider;


import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {

	private final PasswordEncoder passwordEncoder;
	private final GroupeRepository groupeRepository;
	private final VerificationTokenRepository verificationTokenRepository;
	private final MailService mailService;
	private final AuthenticationManager authenticationManager;
	private final JwtProvider jwtProvider;
	private final RefreshTokenService refreshTokenService;
	
	private final DepartementRepository  depdao;
	
	private final ModelMapper modelMapper;
	
	
	// inscription du prof
	@Transactional
	public void signUp(RegistrationRequest registrationRequest) {
		Groupe groupe = new Groupe();
//		
//		groupe.setEmail(registrationRequest.getEmail());
//		groupe.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
//		groupe.setNiveau(registrationRequest.getNiveau());
//		
//		groupe.setCreated(Instant.now());
//		groupe.setEnabled(false);
//
//		
//		groupeRepository.save(groupe);
		Departement departement=depdao.findById(registrationRequest.getDepatementId()).get();
    	System.out.println("dep");
    	
    	 groupe=modelMapper.map(registrationRequest, Groupe.class);
    	groupe.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
    	departement.addGroup(groupe);
    	System.out.println("1");
    	
    
    	groupe.setAnne(LocalDate.now().getYear());
    	groupe.AddEtudiant(groupe.getEtudiants());
    	System.out.println("2");
    	groupeRepository.save(groupe);
		String token = generateVerificationToken(groupe);
		mailService.sendMail(new NotificationMail("activer votre compte :",
				groupe.getEmail()," "
						+ "visiter le lien ci-dessous pour activer votre compte : \n"
						+ "http://localhost:8080/api/auth/verifier-compte/"+token));
	}
	
	private String generateVerificationToken(Groupe groupe) {
		
		String token = UUID.randomUUID().toString();
		
		VerificationToken verificationToken = new VerificationToken();
		verificationToken.setToken(token);
		verificationToken.setGroupe(groupe);
		
		verificationTokenRepository.save(verificationToken);
		return token;
	}

	public void verifyCompte(String token) {
		// TODO Auto-generated method stub
		Optional<VerificationToken>  verificationToken = verificationTokenRepository.findByToken(token);
		
		verificationToken.orElseThrow(() -> new PFE_RegistrationException("token invalid"));
		
		fetchProfAndEnable(verificationToken.get());
	}

	@Transactional
	private void fetchProfAndEnable(VerificationToken verificationToken) {
		// TODO Auto-generated method stub
		String email = verificationToken.getGroupe().getEmail();
		
		Groupe groupe = groupeRepository.findByEmail(email).orElseThrow(()-> new PFE_RegistrationException(""
				+ "prof not found"));
		
		groupe.setEnabled(true);
		
		groupeRepository.save(groupe); // Update
	}

	public AuthenticationResponse login(LoginRequest loginRequest) {
		// TODO Auto-generated method stub
		
		Authentication authentication =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				loginRequest.getEmail(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtProvider.generateToken(authentication);
		return AuthenticationResponse.builder()
        .authenticationToken(token)
        .refreshToken(refreshTokenService.generateRefreshToken().getToken())
        .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
        .email(loginRequest.getEmail())
        .build();
		}

	public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        String token = jwtProvider.generateTokenWithUserName(refreshTokenRequest.getUsername());
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .email(refreshTokenRequest.getUsername())
                .build();
    }
}















