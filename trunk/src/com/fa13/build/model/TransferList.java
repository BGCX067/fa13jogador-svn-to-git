package com.fa13.build.model;

import java.util.List;
import java.util.Date;

public class TransferList {
	
	public TransferList(Date date, List<TransferPlayer> players) {
		this.date = date;
		this.players = players;
	}
	Date date;
	List<TransferPlayer> players;
	
	public Date getDate() {
		return date;
	}
	
	public List<TransferPlayer> getPlayers() {
		return players;
	}
}
