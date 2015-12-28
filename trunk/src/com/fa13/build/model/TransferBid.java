package com.fa13.build.model;

public class TransferBid {
	int id;
	String name;
	String previousTeam;
	int price;
	int tradeIn;
	
	public TransferBid(int id, String name, String previousTeam, int price,
			int tradeIn) {
		this.id = id;
		this.name = name;
		this.previousTeam = previousTeam;
		this.price = price;
		this.tradeIn = tradeIn;
	}
	
	public TransferBid() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPreviousTeam() {
		return previousTeam;
	}

	public void setPreviousTeam(String previousTeam) {
		this.previousTeam = previousTeam;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getTradeIn() {
		return tradeIn;
	}

	public void setTradeIn(int tradeIn) {
		this.tradeIn = tradeIn;
	}
	
	
}
