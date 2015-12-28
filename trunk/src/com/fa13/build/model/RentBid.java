package com.fa13.build.model;

public class RentBid extends PlayerBid {
	public static enum RentType {
		RT_OFFER,
		RT_TAKE
	};
	
	RentType type;
	String team;
	
	public RentBid(RentType type, String team, int number) {
		this.type = type;
		this.team = team;
		this.number = number;
		this.empty = false;
	}
	public RentType getType() {
		return type;
	}
	public void setType(RentType type) {
		this.type = type;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	
	public String toString() {
		return ((type == RentType.RT_OFFER) ? "А " : "В ") + number + " 1 1 " + team;
	}
	
	
}
