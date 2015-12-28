package com.fa13.build.model;

import java.util.ArrayList;
import java.util.List;

public class PlayerActions {
	List<PlayerBid> bids = new ArrayList<PlayerBid>(5);
	String password;

	public PlayerActions(List<PlayerBid> bids, String password) {
		this.bids = bids;
		this.password = password;
	}

	public List<PlayerBid> getBids() {
		return bids;
	}

	public void setBids(List<PlayerBid> bids) {
		this.bids = bids;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
