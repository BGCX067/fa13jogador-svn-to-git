package com.fa13.build.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Team implements Comparable<Team>{
	public Team(String name, String id, String town, String countryCode, String stadium, int managerId, String managerName, String managerTown, String managerCountry, String email, int uin, int games,
			int stadiumCapacity, int stadiumState, int boom, int teamFinance, int managerFinance, int rating, int sportbase, int sportbaseState, boolean sportschool, int sportschoolState, int coach,
			int goalkeepersCoach, int defendersCoach, int midfieldersCoach, int forwardsCoach, int fitnessCoach, int moraleCoach, int doctorQualification, int doctorPlayers, int scout, int homeTop,
			int awayTop, int homeBottom, int awayBottom, Set<String> competitions, List<Player> players) {
		super();
		this.name = name;
		this.id = id;
		this.town = town;
		this.countryCode = countryCode;
		this.stadium = stadium;
		this.managerId = managerId;
		this.managerName = managerName;
		this.managerTown = managerTown;
		this.managerCountry = managerCountry;
		this.email = email;
		this.uin = uin;
		this.games = games;
		this.stadiumCapacity = stadiumCapacity;
		this.stadiumState = stadiumState;
		this.boom = boom;
		this.teamFinance = teamFinance;
		this.managerFinance = managerFinance;
		this.rating = rating;
		this.sportbase = sportbase;
		this.sportbaseState = sportbaseState;
		this.sportschool = sportschool;
		this.sportschoolState = sportschoolState;
		this.coach = coach;
		this.goalkeepersCoach = goalkeepersCoach;
		this.defendersCoach = defendersCoach;
		this.midfieldersCoach = midfieldersCoach;
		this.forwardsCoach = forwardsCoach;
		this.fitnessCoach = fitnessCoach;
		this.moraleCoach = moraleCoach;
		this.doctorQualification = doctorQualification;
		this.doctorPlayers = doctorPlayers;
		this.scout = scout;
		this.homeTop = homeTop;
		this.awayTop = awayTop;
		this.homeBottom = homeBottom;
		this.awayBottom = awayBottom;
		this.competitions = competitions;
		this.players = players;
	}

	String name;
	String id;
	String town;
	String countryCode;
	String stadium;
	
	int managerId;
	String managerName;
	String managerTown;
	String managerCountry;
	String email;
	int uin;
	int games;
	
	int stadiumCapacity;
	int stadiumState;
	int boom;
	int teamFinance;
	int managerFinance;
	int rating;
	int sportbase;
	int sportbaseState;
	
	boolean sportschool;
	int sportschoolState;
	int coach;
	int goalkeepersCoach;
	int defendersCoach;
	int midfieldersCoach;
	int forwardsCoach;
	int fitnessCoach;
	int moraleCoach;
	int doctorQualification;
	int doctorPlayers;
	int scout;
	
	int homeTop;
	int awayTop;
	int homeBottom;
	int awayBottom;
	
	Set<String> competitions = new TreeSet<String>();
	List<Player> players = new ArrayList<Player>(15);
	
	public List<Player> getPlayers() {
		return players;
	}
	
	public Player getPlayerByNumber(int number) {
		for (Player player: players) {
			if (player.number == number) {
				return player;
			}
		}
		return null;
	}

	public int compareTo(Team o) {
		return this.name.compareTo(o.name);
	}

	public String getName() {
		return name;
	}

	public Set<String> getCompetitions() {
		return competitions;
	}
	
	public String getManagerName() {
		return managerName;
	}
	
	public String getManagerTown() {
		return managerTown;
	}
	
	public String getManagerCountry() {
		return managerCountry;
	}
	
	public String getEmail() {
		return email;
	}

	public int getCoach() {
		return coach;
	}

	public void setCoach(int coach) {
		this.coach = coach;
	}

	public int getGoalkeepersCoach() {
		return goalkeepersCoach;
	}

	public void setGoalkeepersCoach(int goalkeepersCoach) {
		this.goalkeepersCoach = goalkeepersCoach;
	}

	public int getDefendersCoach() {
		return defendersCoach;
	}

	public void setDefendersCoach(int defendersCoach) {
		this.defendersCoach = defendersCoach;
	}

	public int getMidfieldersCoach() {
		return midfieldersCoach;
	}

	public void setMidfieldersCoach(int midfieldersCoach) {
		this.midfieldersCoach = midfieldersCoach;
	}

	public int getForwardsCoach() {
		return forwardsCoach;
	}

	public void setForwardsCoach(int forwardsCoach) {
		this.forwardsCoach = forwardsCoach;
	}
	
	public Integer getUIN() {
		return uin;
	}
	
	public Integer getGames() {
		return games;
	}
	
	public Integer getManagerFinance() {
		return managerFinance;
	}
	
	public Integer getManagerId() {
		return managerId;
	}
	
	public String getId() {
		return id;
	}
	
	public Integer getBoom() {
		return boom;
	}
	
	public Integer getRating() {
		return rating;
	}
	
	public Integer getTeamFinance() {
		return teamFinance;
	}
	
	public String getTown() {
		return town;
	}
	
	public String getCountryCode() {
		return countryCode;
	}
	
	public String getStadium() {
		return stadium;
	}
	
	public Integer getStadiumCapacity() {
		return stadiumCapacity;
	}
	
	public boolean isSportschool() {
		return sportschool;
	}
	
	public Integer getSportbase() {
		return sportbase;
	}
	
	public Integer getStadiumState() {
		return stadiumState;
	}
	
	public Integer getSportbaseState() {
		return sportbaseState;
	}
	
	public Integer getSportschoolState() {
		return sportschoolState;
	}
	
	public int getFitnessCoach() {
		return fitnessCoach;
	}
	
	public int getMoraleCoach() {
		return moraleCoach;
	}
	
	public int getScout() {
		return scout;
	}
	
	public int getDoctorQualification() {
		return doctorQualification;
	}
	
	public int getDoctorPlayers() {
		return doctorPlayers;
	}
	
	public int getHomeTop() {
		return homeTop;
	}
	
	public int getHomeBottom() {
		return homeBottom;
	}
	
	public int getAwayTop() {
		return awayTop;
	}
	
	public int getAwayBottom() {
		return awayBottom;
	}
}
