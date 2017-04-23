package com.jose.exercice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jose.exercice.entities.CurrentGame;

public interface CurrentGameRepository extends JpaRepository<CurrentGame, Long>{

}
