package com.jose.exercice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jose.exercice.entities.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {

	Team findByTeamName(final String teamName);
}
