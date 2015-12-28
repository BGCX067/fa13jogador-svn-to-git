package com.fa13.build.model;

import java.util.ArrayList;
import java.util.List;

public class Training {
	public Training(String password, int minTalent, boolean scouting, List<PlayerTraining> players) {
		this.password = password;
		this.minTalent = minTalent;
		this.scouting = scouting;
		this.players = players;
	}
	String password;
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getMinTalent() {
		return minTalent;
	}
	public void setMinTalent(int minTalent) {
		this.minTalent = minTalent;
	}
	public boolean isScouting() {
		return scouting;
	}
	public void setScouting(boolean scouting) {
		this.scouting = scouting;
	}
	public boolean getScouting() {
		return this.scouting;
	}
	public List<PlayerTraining> getPlayers() {
		return players;
	}
	public void setPlayers(List<PlayerTraining> players) {
		this.players = players;
	}
	int minTalent;
	boolean scouting = true;
	List<PlayerTraining> players = new ArrayList<PlayerTraining>(15);
}
