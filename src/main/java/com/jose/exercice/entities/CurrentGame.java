package com.jose.exercice.entities;

import java.util.ArrayList;
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
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames = {"home_team_id", "away_team_id", "progress"})}) //This allows only 1 HomeTeam and AwayTeam play at the same time
public class CurrentGame {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;	
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Team homeTeam;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Team awayTeam;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)	
	private List<ScoreRegistry> scoreRegistryList = new ArrayList<>();
	
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
