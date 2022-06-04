package com.gestion_pfe.prod.repositoy;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gestion_pfe.prod.model.VerificationToken;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Integer>{

	Optional<VerificationToken> findByToken(String token);

}
