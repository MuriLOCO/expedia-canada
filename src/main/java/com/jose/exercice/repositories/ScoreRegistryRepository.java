package com.jose.exercice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jose.exercice.entities.ScoreRegistry;
import com.jose.exercice.entities.Team;

public interface ScoreRegistryRepository extends JpaRepository<ScoreRegistry, Long>{

	List<ScoreRegistry> findByTeamScorer(final Team team);
}
