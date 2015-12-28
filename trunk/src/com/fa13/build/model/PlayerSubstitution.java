package com.fa13.build.model;

import com.fa13.build.model.TeamTactics.Hardness;

public class PlayerSubstitution extends PlayerPosition {
	int time;
	int minDifference, maxDifference;
	int substitutedPlayer;
	
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public int getMinDifference() {
		return minDifference;
	}
	public void setMinDifference(int minDifference) {
		this.minDifference = minDifference;
	}
	public int getMaxDifference() {
		return maxDifference;
	}
	public void setMaxDifference(int maxDifference) {
		this.maxDifference = maxDifference;
	}
	public int getSubstitutedPlayer() {
		return substitutedPlayer;
	}
	public void setSubstitutedPlayer(int substitutedPlayer) {
		this.substitutedPlayer = substitutedPlayer;
	}
	public PlayerSubstitution(int defenceX, int defenceY, int attackX,
			int attackY, int freekickX, int freekickY, int number,
			int specialRole, int penalty, boolean longShot,
			FreekicksAction actOnFreekick, boolean fantasista,
			boolean dispatcher, int personalDefence, boolean pressing,
			boolean keepBall, boolean defenderAttack, Hardness hardness,
			PassingStyle passingStyle, int time, int minDifference,
			int maxDifference, int substitutedPlayer, boolean goalkeeper) {
		super(defenceX, defenceY, attackX, attackY, freekickX, freekickY,
				number, specialRole, penalty, longShot, actOnFreekick,
				fantasista, dispatcher, personalDefence, pressing, keepBall,
				defenderAttack, hardness, passingStyle, goalkeeper);
		this.time = time;
		this.minDifference = minDifference;
		this.maxDifference = maxDifference;
		this.substitutedPlayer = substitutedPlayer;
	}
	
	public PlayerSubstitution(int defenceX, int defenceY, int attackX,
			int attackY, int freekickX, int freekickY, int number,
			int specialRole, int penalty, boolean longShot,
			FreekicksAction actOnFreekick, boolean fantasista,
			boolean dispatcher, int personalDefence, boolean pressing,
			boolean keepBall, boolean defenderAttack, Hardness hardness,
			PassingStyle passingStyle, int time, int minDifference,
			int maxDifference, int substitutedPlayer) {
		super(defenceX, defenceY, attackX, attackY, freekickX, freekickY,
				number, specialRole, penalty, longShot, actOnFreekick,
				fantasista, dispatcher, personalDefence, pressing, keepBall,
				defenderAttack, hardness, passingStyle, false);
		this.time = time;
		this.minDifference = minDifference;
		this.maxDifference = maxDifference;
		this.substitutedPlayer = substitutedPlayer;
	}
	
	public PlayerSubstitution() {
		this.time = 0;
		this.minDifference = -20;
		this.maxDifference = 20;
		this.substitutedPlayer = 0;
		this.number = 0;
		this.specialRole = 0;
	}

	public boolean isEmpty(){
		return ( (this.time == 0) && (this.number == 0)  && (this.substitutedPlayer == 0));
	}
	
	public void markEmpty(){
		this.number = 0;
		this.substitutedPlayer = 0;
		this.time = 0;
		this.specialRole = 0;
	}
	
}
