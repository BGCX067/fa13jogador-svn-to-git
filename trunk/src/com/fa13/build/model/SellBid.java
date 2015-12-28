package com.fa13.build.model;

public class SellBid extends PlayerBid {
	public static enum SellType {
		SL_VALUE,
		SL_PERCENT
	};
	
	SellType type;
	public SellType getType() {
		return type;
	}

	public void setType(SellType type) {
		this.type = type;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	int value;
	
	public SellBid(SellType type, int number, int value) {
		this.type = type;
		this.number = number;
		this.value = value;
		this.empty = false;
	}
	
	public String toString() {
		return ((type == SellType.SL_VALUE) ? "Т " : "П ") + number + " " + value;
	}
}
