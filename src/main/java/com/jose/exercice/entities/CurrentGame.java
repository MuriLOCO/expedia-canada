package com.jose.exercice.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class CurrentGame {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;	
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Team homeTeam;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Team awayTeam;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@Column(nullable = false)
	private List<ScoreRegistry> scoreRegistryList;

	@Column(nullable = false)
	private boolean progress;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Team getHomeTeam() {
		return homeTeam;
	}

	public void setHomeTeam(Team homeTeam) {
		this.homeTeam = homeTeam;
	}

	public Team getAwayTeam() {
		return awayTeam;
	}

	public void setAwayTeam(Team awayTeam) {
		this.awayTeam = awayTeam;
	}

	public List<ScoreRegistry> getScoreRegistryList() {
		return scoreRegistryList;
	}

	public void setScoreRegistryList(List<ScoreRegistry> scoreRegistryList) {
		this.scoreRegistryList = scoreRegistryList;
	}

	public boolean isProgress() {
		return progress;
	}

	public void setProgress(boolean progress) {
		this.progress = progress;
	}
	
	
}
