package com.fa13.build.model;

public class TransferPlayer extends Player {
	private String previousTeam;
	private String abilities;
	private int transferID;
	private int bids;
	
	public TransferPlayer() {
		super();
	}
	
	public TransferPlayer(int id, String name, String nationalityCode,
			String previousTeam, PlayerAmplua position, int age, int talent,
			int salary, int strength, int health, String abilities, int price, 
			int bids, int transferID) {
		super(position);
		this.id = id;
		this.name = name;
		this.nationalityCode = nationalityCode;
		this.previousTeam = previousTeam;
		this.position = position;
		this.age = age;
		this.talent = talent;
		this.salary = salary;
		this.strength = strength;
		this.health = health;
		this.abilities = abilities;
		this.price = price;
		this.bids = bids;
		this.transferID = transferID;
	}

	public String getPreviousTeam() {
		return previousTeam;
	}

	public void setPreviousTeam(String previousTeam) {
		this.previousTeam = previousTeam;
	}

	public String getAbilities() {
		return abilities;
	}

	public void setAbilities(String abilities) {
		this.abilities = abilities;
	}

	public int getTransferID() {
		return transferID;
	}

	public void setTransferID(int transferID) {
		this.transferID = transferID;
	}
	
	public int getBids() {
		return bids;
	}
	
	public void setBids(int bids) {
		this.bids = bids;
	}
}
