package com.fa13.build.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class All {
	public All(Date date, Map<String, String> competitions, int bankRate, List<Team> teams) {
		this.date = date;
		this.competitions = competitions;
		this.bankRate = bankRate;
		this.teams = teams;
		this.currentTeam = 0;
	}
	Date date;
	Map<String, String> competitions;
	int bankRate;
	List<Team> teams;
	int currentTeam;
	
	public Date getDate() {
		return date;
	}
	
	public List<Team> getTeams() {
		return teams;
	}	
	
	public Team getCurrentTeam() {
		if (currentTeam >= 0 && teams.size() > currentTeam) {
			return teams.get(currentTeam);
		} else {
			return null;
		}
	}

	public void setCurrentTeam(int currentTeam) {
		this.currentTeam = currentTeam;
	}
	
	public Map<String, String> getCompetitions() {
		return competitions;
	}
	
	public Team getTeamByName(String name) {
		for (Team curr: teams) {
			if (curr.getName().equals(name)) {
				return curr;
			}
		}
		return null;
	}
	
	public Team getTeamById(String id) {
		for (Team curr: teams) {
			if (curr.getId().equals(id)) {
				return curr;
			}
		}
		return null;
	}
}
