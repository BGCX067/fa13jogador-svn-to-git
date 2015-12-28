package com.fa13.build.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GameForm {
	String tournamentID;
	String gameType;
	String teamID;
	String password;
	
	int isDefault = -1;
	public int getDefault() {
		return isDefault;
	}

	public void setDefault(int isDefault) {
		this.isDefault = isDefault;
	}

	TeamTactics startTactics;
	
	public static enum CoachSettings {
		CH_NONE("нет установки"),
		CH_PRESSING("прессинг"),
		CH_ARYTHMIC("аритмия"),
		CH_PERSONAL_DEFENCE("персональная опека"),
		CH_COUNTER_ATTACK("контратака"),
		CH_CATENACCIO("каттеначо"),
		CH_STRAIGHT_PRESSURE("прямое давление"),
		CH_POSITION_ATTACK("позиционное нападение");
		private String settings;
		CoachSettings(String str) {
			settings = str;
		}
		
		public String toString(){
			return settings;
		}
	};
	
	public static final Map<String, CoachSettings> settingsMap;
	
	static {
		Map<String, CoachSettings> tmp = new HashMap<String, CoachSettings>();
		tmp.put("нет установки", CoachSettings.CH_NONE);
		tmp.put("прессинг", CoachSettings.CH_PRESSING);
		tmp.put("аритмия", CoachSettings.CH_ARYTHMIC);
		tmp.put("персональная опека", CoachSettings.CH_PERSONAL_DEFENCE);
		tmp.put("контратака", CoachSettings.CH_COUNTER_ATTACK);
		tmp.put("каттеначо", CoachSettings.CH_CATENACCIO);
		tmp.put("прямое давление", CoachSettings.CH_STRAIGHT_PRESSURE);
		tmp.put("позиционное нападение", CoachSettings.CH_POSITION_ATTACK);
		settingsMap = Collections.unmodifiableMap(tmp);
	}
	
	CoachSettings teamCoachSettings;
	
	PlayerPosition[] firstTeam = new PlayerPosition[11];
	
	int[] bench = new int[7];
	int price;
	
	PlayerSubstitution[] substitutions = new PlayerSubstitution[25];
	
	int attackTime, attackMin, attackMax;
	int defenceTime, defenceMin, defenceMax;
	
	public static enum SubstitutionPreferences {
		SP_POSITION,
		SP_STRENGTH
	};
	
	SubstitutionPreferences substitutionPreferences;
	
	public PlayerSubstitution[] getSubstitutions() {
		return substitutions;
	}

	public void setSubstitutions(PlayerSubstitution[] substitutions) {
		this.substitutions = substitutions;
	}

	public SubstitutionPreferences getSubstitutionPreferences() {
		return substitutionPreferences;
	}

	public void setSubstitutionPreferences(
			SubstitutionPreferences substitutionPreferences) {
		this.substitutionPreferences = substitutionPreferences;
	}

	public TeamTactics[] getHalftimeChanges() {
		return halftimeChanges;
	}

	public void setHalftimeChanges(TeamTactics[] halftimeChanges) {
		this.halftimeChanges = halftimeChanges;
	}

	TeamTactics[] halftimeChanges = new TeamTactics[5];

	public GameForm() {
		for (int i = 0; i < 11; i++) {
			firstTeam[i] = new PlayerPosition();
		}
		substitutionPreferences = SubstitutionPreferences.SP_POSITION;
	}

	public String getTournamentID() {
		return tournamentID;
	}

	public void setTournamentID(String tournamentID) {
		this.tournamentID = tournamentID;
	}

	public String getGameType() {
		return gameType;
	}

	public void setGameType(String gameType) {
		this.gameType = gameType;
	}

	public String getTeamID() {
		return teamID;
	}

	public void setTeamID(String teamID) {
		this.teamID = teamID;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public TeamTactics getStartTactics() {
		return startTactics;
	}

	public void setStartTactics(TeamTactics startTactics) {
		this.startTactics = startTactics;
	}

	public CoachSettings getTeamCoachSettings() {
		return teamCoachSettings;
	}

	public void setTeamCoachSettings(CoachSettings teamCoachSettings) {
		this.teamCoachSettings = teamCoachSettings;
	}

	public PlayerPosition[] getFirstTeam() {
		return firstTeam;
	}

	public void setFirstTeam(PlayerPosition[] firstTeam) {
		this.firstTeam = firstTeam;
	}

	public int[] getBench() {
		return bench;
	}

	public void setBench(int[] bench) {
		this.bench = bench;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getAttackTime() {
		return attackTime;
	}

	public void setAttackTime(int attackTime) {
		this.attackTime = attackTime;
	}

	public int getAttackMin() {
		return attackMin;
	}

	public void setAttackMin(int attackMin) {
		this.attackMin = attackMin;
	}

	public int getAttackMax() {
		return attackMax;
	}

	public void setAttackMax(int attackMax) {
		this.attackMax = attackMax;
	}

	public int getDefenceTime() {
		return defenceTime;
	}

	public void setDefenceTime(int defenceTime) {
		this.defenceTime = defenceTime;
	}

	public int getDefenceMin() {
		return defenceMin;
	}

	public void setDefenceMin(int defenceMin) {
		this.defenceMin = defenceMin;
	}

	public int getDefenceMax() {
		return defenceMax;
	}

	public void setDefenceMax(int defenceMax) {
		this.defenceMax = defenceMax;
	}

	public GameForm(String tournamentID, String gameType, String teamID,
			String password, TeamTactics startTactics, CoachSettings teamCoachSettings, 
			PlayerPosition[] firstTeam, int[] bench, int price, 
			PlayerSubstitution[] substitutions, SubstitutionPreferences substitutionPreferences,
			int defenceTime, int defenceMin, int defenceMax, int attackTime, 
			int attackMin, int attackMax, TeamTactics[] halftimeChanges) {
		this.tournamentID = tournamentID;
		this.gameType = gameType;
		this.teamID = teamID;
		this.password = password;
		this.startTactics = startTactics;
		this.teamCoachSettings = teamCoachSettings;
		this.firstTeam = firstTeam;
		this.bench = bench;
		this.price = price;
		this.substitutions = substitutions;
		this.substitutionPreferences = substitutionPreferences;
		this.attackTime = attackTime;
		this.attackMin = attackMin;
		this.attackMax = attackMax;
		this.defenceTime = defenceTime;
		this.defenceMin = defenceMin;
		this.defenceMax = defenceMax;
		this.halftimeChanges = halftimeChanges;
	}

	
}
