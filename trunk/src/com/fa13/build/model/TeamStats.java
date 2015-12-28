package com.fa13.build.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TeamStats {
	String countryCode;
	int rating;
	int finance;
	int stadium;
	int playersPrice;
	int infrastructurePrice;
	int teamPrice;
	int playersSalary;
	int trainersSalary;
	int teamSalary;
	double averageStrength;
	double average11Strength;
	double average15Strength;
	double averageTalent;
	double averageAge;
	int teamSpecialPoints;
	double averageSpecialPoints;
	int yellowCards;
	int redCards;
	String id;
	String name; 
	
	public TeamStats(Team team) {
		countryCode = team.countryCode;
		rating = team.rating;
		stadium = team.stadiumCapacity;
		finance = team.teamFinance / 1000;
		playersPrice = 0;
		playersSalary = 0;
		averageTalent = 0;
		yellowCards = 0;
		redCards = 0;
		List<Integer> strength = new ArrayList<Integer>(team.players.size());
		for (Player player: team.players) {
			playersPrice += player.price;
			playersSalary += player.salary;
			strength.add(player.strength);
			averageStrength += player.strength;
			averageTalent += player.talent;
			yellowCards += player.yellowCards;
			redCards += player.redCards;
			averageAge += player.age;
			
			teamSpecialPoints += player.shooting - 20;
			teamSpecialPoints += player.passing - 20;
			teamSpecialPoints += player.cross - 20;
			teamSpecialPoints += player.dribbling - 20;
			teamSpecialPoints += player.tackling - 20;
			teamSpecialPoints += player.speed - 20;
			teamSpecialPoints += player.stamina - 20;
			teamSpecialPoints += player.heading - 20;
			teamSpecialPoints += player.reflexes - 20;
			teamSpecialPoints += player.handling - 20;
		}
				
		Collections.sort(strength);
		Collections.reverse(strength);
		
		int len11 = Math.min(11, team.players.size());
		int len15 = Math.min(15, team.players.size());
		
		for (int i = 0; i < len11; i++) {
			int curr = strength.get(i);
			average11Strength += curr;
			average15Strength += curr;
		}
		
		for (int i = 11; i < len15; i++) {
			int curr = strength.get(i);
			average15Strength += curr;
		}
		
		averageStrength /= team.players.size();
		average11Strength /= len11;
		average15Strength /= len15;
		averageTalent /= team.players.size();
		averageAge /= team.players.size();
		
		averageSpecialPoints = teamSpecialPoints;
		averageSpecialPoints /= team.players.size();
		
		infrastructurePrice = 0;
		trainersSalary = 0;
		for (int i = 9; i < Math.min(team.stadiumCapacity / 1000, 50); i++) {
			infrastructurePrice += 20000 / (63 - i);
		}
		infrastructurePrice += Math.max(team.stadiumCapacity / 1000 - 50, 0) * 1500;
		
		if (team.sportbase > 0) {
			infrastructurePrice += 2000;
		}
		infrastructurePrice += 3000 * team.sportbase;

		if (team.sportschool) {
			infrastructurePrice += 5000;
		}
		infrastructurePrice += team.coach * 100;
		trainersSalary += 32 + team.coach * 1.6;
		
		if (team.goalkeepersCoach > 0) {
			infrastructurePrice += 100;
		}
		infrastructurePrice += Math.max(team.goalkeepersCoach - 1, 0) * 500;
		trainersSalary += team.goalkeepersCoach * 8;
		
		if (team.defendersCoach > 0) {
			infrastructurePrice += 100;
		}
		infrastructurePrice += Math.max(team.defendersCoach - 1, 0) * 500;
		trainersSalary += team.defendersCoach * 8;
		
		if (team.midfieldersCoach > 0) {
			infrastructurePrice += 100;
		}
		infrastructurePrice += Math.max(team.midfieldersCoach - 1, 0) * 500;
		trainersSalary += team.midfieldersCoach * 8;
		
		if (team.forwardsCoach > 0) {
			infrastructurePrice += 100;
		}
		infrastructurePrice += Math.max(team.forwardsCoach - 1, 0) * 500;
		trainersSalary += team.forwardsCoach * 8;
		
		if (team.fitnessCoach > 0) {
			infrastructurePrice += 100;
		}
		infrastructurePrice += Math.max(team.fitnessCoach - 1, 0) * 500;
		trainersSalary += team.fitnessCoach * 8;
		
		if (team.moraleCoach > 0) {
			infrastructurePrice += 100;
		}
		infrastructurePrice += Math.max(team.moraleCoach - 1, 0) * 500;
		trainersSalary += team.moraleCoach * 8;
		
		if (team.doctorPlayers + team.doctorQualification > 0) {
			infrastructurePrice += 100;
		}
		infrastructurePrice += Math.max(team.doctorPlayers + team.doctorQualification - 1, 0) * 250;
		trainersSalary += (team.doctorPlayers + team.doctorQualification) * 8;
		
		if (team.scout > 0) {
			infrastructurePrice += 100;
		}
		infrastructurePrice += Math.max(team.scout - 1, 0) * 500;
		trainersSalary += team.scout * 24;
		
		teamPrice = playersPrice + infrastructurePrice;
		teamSalary = playersSalary + trainersSalary;
		
		id = team.id;
		name = team.name;
	}

	public int getRating() {
		return rating;
	}

	public int getFinance() {
		return finance;
	}

	public int getStadium() {
		return stadium;
	}

	public int getPlayersPrice() {
		return playersPrice;
	}

	public int getInfrastructurePrice() {
		return infrastructurePrice;
	}

	public int getTeamPrice() {
		return teamPrice;
	}

	public int getPlayersSalary() {
		return playersSalary;
	}

	public int getTrainersSalary() {
		return trainersSalary;
	}

	public int getTeamSalary() {
		return teamSalary;
	}

	public double getAverageStrength() {
		return averageStrength;
	}

	public double getAverage11Strength() {
		return average11Strength;
	}

	public double getAverage15Strength() {
		return average15Strength;
	}

	public double getAverageTalent() {
		return averageTalent;
	}

	public double getAverageAge() {
		return averageAge;
	}

	public int getTeamSpecialPoints() {
		return teamSpecialPoints;
	}

	public double getAverageSpecialPoints() {
		return averageSpecialPoints;
	}

	public int getYellowCards() {
		return yellowCards;
	}
	
	public int getRedCards() {
		return redCards;
	}
	
	public int getCards() {
		return yellowCards + 3 * redCards;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public String getCountryCode() {
		return countryCode;
	}
	
}
