package com.jose.exercice.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class ScoreRegistry {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(nullable = false)
	private long minute;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Team scoredTeam;
	
	@Column(nullable = false)
	private String nameOfScorer;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getMinute() {
		return minute;
	}

	public void setMinute(long minute) {
		this.minute = minute;
	}

	public Team getScoredTeam() {
		return scoredTeam;
	}

	public void setScoredTeam(Team scoredTeam) {
		this.scoredTeam = scoredTeam;
	}

	public String getNameOfScorer() {
		return nameOfScorer;
	}

	public void setNameOfScorer(String nameOfScorer) {
		this.nameOfScorer = nameOfScorer;
	}
	
	
	
	
}
