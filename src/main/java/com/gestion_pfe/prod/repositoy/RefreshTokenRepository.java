package com.gestion_pfe.prod.repositoy;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.gestion_pfe.prod.model.RefrechToken;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefrechToken, Integer>{
	
	Optional<RefrechToken> findByToken(String token);

    void deleteByToken(String token);
}
