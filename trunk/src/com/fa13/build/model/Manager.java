package com.fa13.build.model;

public class Manager {
	int managerId;
	String managerName;
	String managerTown;
	String managerCountry;
	String email;
	int uin;
	int games;
	
	private Manager(int managerId, String managerName, String managerTown,
			String managerCountry, String email, int uin, int games) {
		this.managerId = managerId;
		this.managerName = managerName;
		this.managerTown = managerTown;
		this.managerCountry = managerCountry;
		this.email = email;
		this.uin = uin;
		this.games = games;
	}

	public static Manager extractManager(Team team) {
		if (team.managerId == 0) {
			return null;
		} else {
			return new Manager(team.managerId, team.managerName, team.managerTown, team.managerCountry, team.email, team.uin, team.games); 
		}
	}
}
