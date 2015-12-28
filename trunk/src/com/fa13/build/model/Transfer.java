package com.fa13.build.model;

public class Transfer {
	String password;
	int maxGK, maxLD, maxCD, maxRD, maxLM, maxCM, maxRM, maxLF, maxCF, maxRF;
	int maxPlayers, minCash;
	TransferBid[] bids;
	
	public Transfer(String password, int maxGK, int maxLD, int maxCD,
			int maxRD, int maxLM, int maxCM, int maxRM, int maxLF, int maxCF,
			int maxRF, int maxPlayers, int minCash, TransferBid[] bids) {
		this.password = password;
		this.maxGK = maxGK;
		this.maxLD = maxLD;
		this.maxCD = maxCD;
		this.maxRD = maxRD;
		this.maxLM = maxLM;
		this.maxCM = maxCM;
		this.maxRM = maxRM;
		this.maxLF = maxLF;
		this.maxCF = maxCF;
		this.maxRF = maxRF;
		this.maxPlayers = maxPlayers;
		this.minCash = minCash;
		this.bids = bids;
	}
	
	public Transfer() {
		super();
		this.password = "";
		this.maxGK = 25;
		this.maxLD = 25;
		this.maxCD = 25;
		this.maxRD = 25;
		this.maxLM = 25;
		this.maxCM = 25;
		this.maxRM = 25;
		this.maxLF = 25;
		this.maxCF = 25;
		this.maxRF = 25;
		this.maxPlayers = 25;
		this.minCash = 0;
		this.bids = new TransferBid[20];
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getMaxGK() {
		return maxGK;
	}

	public void setMaxGK(int maxGK) {
		this.maxGK = maxGK;
	}

	public int getMaxLD() {
		return maxLD;
	}

	public void setMaxLD(int maxLD) {
		this.maxLD = maxLD;
	}

	public int getMaxCD() {
		return maxCD;
	}

	public void setMaxCD(int maxCD) {
		this.maxCD = maxCD;
	}

	public int getMaxRD() {
		return maxRD;
	}

	public void setMaxRD(int maxRD) {
		this.maxRD = maxRD;
	}

	public int getMaxLM() {
		return maxLM;
	}

	public void setMaxLM(int maxLM) {
		this.maxLM = maxLM;
	}

	public int getMaxCM() {
		return maxCM;
	}

	public void setMaxCM(int maxCM) {
		this.maxCM = maxCM;
	}

	public int getMaxRM() {
		return maxRM;
	}

	public void setMaxRM(int maxRM) {
		this.maxRM = maxRM;
	}

	public int getMaxLF() {
		return maxLF;
	}

	public void setMaxLF(int maxLF) {
		this.maxLF = maxLF;
	}

	public int getMaxCF() {
		return maxCF;
	}

	public void setMaxCF(int maxCF) {
		this.maxCF = maxCF;
	}

	public int getMaxRF() {
		return maxRF;
	}

	public void setMaxRF(int maxRF) {
		this.maxRF = maxRF;
	}

	public int getMaxPlayers() {
		return maxPlayers;
	}

	public void setMaxPlayers(int maxPlayers) {
		this.maxPlayers = maxPlayers;
	}

	public int getMinCash() {
		return minCash;
	}

	public void setMinCash(int minCash) {
		this.minCash = minCash;
	}

	public TransferBid[] getBids() {
		return bids;
	}

	public void setBids(TransferBid[] bids) {
		this.bids = bids;
	}
	
	public TransferBid getBid(int bid) {
		return bids[bid];
	}
	
	public void setBid(int bid, TransferBid value) {
		bids[bid] = value;
	}
}
