package com.fa13.build.model;

import com.fa13.build.model.BasicPlayer.PlayerAmplua;
import com.fa13.build.model.TeamTactics.Hardness;

public class PlayerPosition {
	int defenceX, defenceY, attackX, attackY, freekickX, freekickY;
	boolean goalkeeper = false;
	
	int number;
	int specialRole;
	int penaltyOrder;
	
	public static int SR_CAPTAIN = 32;
	public static int SR_PENALTY = 16;
	public static int SR_DIRECT_FREEKICK = 8;
	public static int SR_INDIRECT_FREEKICK = 4;
	public static int SR_LEFT_CORNER = 2;
	public static int SR_RIGHT_CORNER = 1;
	
	public static int SR_ASSISTANT = 6;
	
	boolean longShot;
	public static enum FreekicksAction {
		FK_NO, FK_YES, FK_DEFAULT
	};
	
	FreekicksAction actOnFreekick;
	
	boolean fantasista;
	boolean dispatcher;
	int personalDefence;
	boolean pressing;
	boolean keepBall;
	boolean defenderAttack;
	
	public static enum PassingStyle {
		PS_FORWARD, PS_EXACT, PS_BOTH
	};
		
	Hardness hardness;
	
	PassingStyle passingStyle;
	
	public int getNumber() {
		return number;
	}
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	public void reset() {
		this.number = -1;
	}
	
	public PlayerPosition validatePersonalSettings() {
		PlayerPosition res = new PlayerPosition(defenceX, defenceY, attackX, attackY,
				freekickX, freekickY, number, specialRole, penaltyOrder, longShot,
				actOnFreekick, fantasista, dispatcher, personalDefence, pressing,
				keepBall, defenderAttack, hardness, passingStyle, goalkeeper);
		switch (this.getAmplua()) {
		case LD:
		case CD:
		case RD:
		{
			res.fantasista = false;
			res.dispatcher = false;
			res.pressing = true;
			res.keepBall = false;
			break;
		}
		case LM:
		case CM:
		case RM:
		{
			res.keepBall = false;
			res.defenderAttack = false;
			break;
		}
		case LF:
		case CF:
		case RF:
		{
			res.actOnFreekick = FreekicksAction.FK_YES;
			res.dispatcher = false;
			res.defenderAttack = false;
			break;
		}
		}
		return res;
	}

	public int getDefenceX() {
		return defenceX;
	}

	public void setDefenceX(int defenceX) {
		this.defenceX = defenceX;
	}

	public int getDefenceY() {
		return defenceY;
	}

	public void setDefenceY(int defenceY) {
		this.defenceY = defenceY;
	}

	public int getAttackX() {
		return attackX;
	}

	public void setAttackX(int attackX) {
		this.attackX = attackX;
	}

	public int getAttackY() {
		return attackY;
	}

	public void setAttackY(int attackY) {
		this.attackY = attackY;
	}

	public int getFreekickX() {
		return freekickX;
	}

	public void setFreekickX(int freekickX) {
		this.freekickX = freekickX;
	}

	public int getFreekickY() {
		return freekickY;
	}

	public void setFreekickY(int freekickY) {
		this.freekickY = freekickY;
	}

	public int getSpecialRole() {
		return specialRole;
	}

	public void setSpecialRole(int specialRole) {
		this.specialRole = specialRole;
	}

	public int getPenaltyOrder() {
		return penaltyOrder;
	}

	public void setPenaltyOrder(int penaltyOrder) {
		this.penaltyOrder = penaltyOrder;
	}
	
	public boolean isCaptain() {
		return (this.specialRole & SR_CAPTAIN) != 0;
	}
	
	public void setCaptain(boolean value) {
		if (value) {
			this.specialRole |= SR_CAPTAIN;
		} else {
			this.specialRole &= ~SR_CAPTAIN;
		}
	}
	
	public boolean isCaptainAssistant () {
		return (this.specialRole & (SR_CAPTAIN << SR_ASSISTANT)) != 0;
	}
	
	public void setCaptainAssistant (boolean value) {
		if (value) {
			this.specialRole |= (SR_CAPTAIN << SR_ASSISTANT);
		} else {
			this.specialRole &= ~(SR_CAPTAIN << SR_ASSISTANT);
		}
	}
	
	public boolean isDirectFreekick() {
		return (this.specialRole & SR_DIRECT_FREEKICK) != 0;
	}
	
	public void setDirectFreekick(boolean value) {
		if (value) {
			this.specialRole |= SR_DIRECT_FREEKICK;
		} else {
			this.specialRole &= ~SR_DIRECT_FREEKICK;
		}
	}
	
	public boolean isDirectFreekickAssistant () {
		return (this.specialRole & (SR_DIRECT_FREEKICK << SR_ASSISTANT)) != 0;
	}
	
	public void setDirectFreekickAssistant (boolean value) {
		if (value) {
			this.specialRole |= (SR_DIRECT_FREEKICK << SR_ASSISTANT);
		} else {
			this.specialRole &= ~(SR_DIRECT_FREEKICK << SR_ASSISTANT);
		}
	}
	
	public boolean isIndirectFreekick() {
		return (this.specialRole & SR_INDIRECT_FREEKICK) != 0;
	}
	
	public void setIndirectFreekick(boolean value) {
		if (value) {
			this.specialRole |= SR_INDIRECT_FREEKICK;
		} else {
			this.specialRole &= ~SR_INDIRECT_FREEKICK;
		}
	}
	
	public boolean isIndirectFreekickAssistant () {
		return (this.specialRole & (SR_INDIRECT_FREEKICK << SR_ASSISTANT)) != 0;
	}
	
	public void setIndirectFreekickAssistant (boolean value) {
		if (value) {
			this.specialRole |= (SR_INDIRECT_FREEKICK << SR_ASSISTANT);
		} else {
			this.specialRole &= ~(SR_INDIRECT_FREEKICK << SR_ASSISTANT);
		}
	}
	
	public boolean isPenalty() {
		return (this.specialRole & SR_PENALTY) != 0;
	}
	
	public void setPenalty(boolean value) {
		if (value) {
			this.specialRole |= SR_PENALTY;
		} else {
			this.specialRole &= ~SR_PENALTY;
		}
	}
	
	public boolean isPenaltyAssistant () {
		return (this.specialRole & (SR_PENALTY << SR_ASSISTANT)) != 0;
	}
	
	public void setPenaltyAssistant (boolean value) {
		if (value) {
			this.specialRole |= (SR_PENALTY << SR_ASSISTANT);
		} else {
			this.specialRole &= ~(SR_PENALTY << SR_ASSISTANT);
		}
	}
	
	public boolean isLeftCorner() {
		return (this.specialRole & SR_LEFT_CORNER) != 0;
	}
	
	public void setLeftCorner(boolean value) {
		if (value) {
			this.specialRole |= SR_LEFT_CORNER;
		} else {
			this.specialRole &= ~SR_LEFT_CORNER;
		}
	}
	
	public boolean isLeftCornerAssistant () {
		return (this.specialRole & (SR_LEFT_CORNER << SR_ASSISTANT)) != 0;
	}
	
	public void setLeftCornerAssistant (boolean value) {
		if (value) {
			this.specialRole |= (SR_LEFT_CORNER << SR_ASSISTANT);
		} else {
			this.specialRole &= ~(SR_LEFT_CORNER << SR_ASSISTANT);
		}
	}
	
	public boolean isRightCorner() {
		return (this.specialRole & SR_RIGHT_CORNER) != 0;
	}
	
	public void setRightCorner(boolean value) {
		if (value) {
			this.specialRole |= SR_RIGHT_CORNER;
		} else {
			this.specialRole &= ~SR_RIGHT_CORNER;
		}
	}
	
	public boolean isRightCornerAssistant () {
		return (this.specialRole & (SR_RIGHT_CORNER << SR_ASSISTANT)) != 0;
	}
	
	public void setRightCornerAssistant (boolean value) {
		if (value) {
			this.specialRole |= (SR_RIGHT_CORNER << SR_ASSISTANT);
		} else {
			this.specialRole &= ~(SR_RIGHT_CORNER << SR_ASSISTANT);
		}
	}

	public boolean isLongShot() {
		return longShot;
	}

	public void setLongShot(boolean longShot) {
		this.longShot = longShot;
	}

	public FreekicksAction getActOnFreekick() {
		return actOnFreekick;
	}

	public void setActOnFreekick(FreekicksAction actOnFreekick) {
		this.actOnFreekick = actOnFreekick;
	}

	public boolean isFantasista() {
		return fantasista;
	}

	public void setFantasista(boolean fantasista) {
		this.fantasista = fantasista;
	}

	public boolean isDispatcher() {
		return dispatcher;
	}

	public void setDispatcher(boolean dispatcher) {
		this.dispatcher = dispatcher;
	}

	public int getPersonalDefence() {
		return personalDefence;
	}

	public void setPersonalDefence(int personalDefence) {
		this.personalDefence = personalDefence;
	}

	public boolean isPressing() {
		return pressing;
	}

	public void setPressing(boolean pressing) {
		this.pressing = pressing;
	}

	public boolean isKeepBall() {
		return keepBall;
	}

	public void setKeepBall(boolean keepBall) {
		this.keepBall = keepBall;
	}

	public boolean isDefenderAttack() {
		return defenderAttack;
	}

	public void setDefenderAttack(boolean defenderAttack) {
		this.defenderAttack = defenderAttack;
	}

	public Hardness getHardness() {
		return hardness;
	}

	public void setHardness(Hardness hardness) {
		this.hardness = hardness;
	}

	public PassingStyle getPassingStyle() {
		return passingStyle;
	}

	public void setPassingStyle(PassingStyle passingStyle) {
		this.passingStyle = passingStyle;
	}

	public PlayerPosition(int defenceX, int defenceY, int attackX, int attackY,
			int freekickX, int freekickY, int number, int specialRole,
			int penaltyOrder, boolean longShot, FreekicksAction actOnFreekick,
			boolean fantasista, boolean dispatcher, int personalDefence,
			boolean pressing, boolean keepBall, boolean defenderAttack,
			Hardness hardness, PassingStyle passingStyle, boolean goalkeeper) {
		this.defenceX = defenceX;
		this.defenceY = defenceY;
		this.attackX = attackX;
		this.attackY = attackY;
		this.freekickX = freekickX;
		this.freekickY = freekickY;
		this.number = number;
		this.specialRole = specialRole;
		this.penaltyOrder = penaltyOrder;
		this.longShot = longShot;
		this.actOnFreekick = actOnFreekick;
		this.fantasista = fantasista;
		this.dispatcher = dispatcher;
		this.personalDefence = personalDefence;
		this.pressing = pressing;
		this.keepBall = keepBall;
		this.defenderAttack = defenderAttack;
		this.hardness = hardness;
		this.passingStyle = passingStyle;
		this.goalkeeper = goalkeeper;
	}
	
	public PlayerPosition(int defenceX, int defenceY, int attackX, int attackY,
			int freekickX, int freekickY, int number, int specialRole,
			int penaltyOrder, boolean longShot, FreekicksAction actOnFreekick,
			boolean fantasista, boolean dispatcher, int personalDefence,
			boolean pressing, boolean keepBall, boolean defenderAttack,
			Hardness hardness, PassingStyle passingStyle) {
		this.defenceX = defenceX;
		this.defenceY = defenceY;
		this.attackX = attackX;
		this.attackY = attackY;
		this.freekickX = freekickX;
		this.freekickY = freekickY;
		this.number = number;
		this.specialRole = specialRole;
		this.penaltyOrder = penaltyOrder;
		this.longShot = longShot;
		this.actOnFreekick = actOnFreekick;
		this.fantasista = fantasista;
		this.dispatcher = dispatcher;
		this.personalDefence = personalDefence;
		this.pressing = pressing;
		this.keepBall = keepBall;
		this.defenderAttack = defenderAttack;
		this.hardness = hardness;
		this.passingStyle = passingStyle;
		this.goalkeeper = false;
	}
	
	public PlayerPosition() {
		this.actOnFreekick = FreekicksAction.FK_DEFAULT;
		this.passingStyle = PassingStyle.PS_BOTH;
		this.hardness = Hardness.HR_DEFAULT;
	}
	
	public boolean isGoalkeeper() {
		return goalkeeper;
	}

	public void setGoalkeeper(boolean goalkeeper) {
		this.goalkeeper = goalkeeper;
	}

	public PlayerAmplua getAmplua () {
		if (this.goalkeeper) return PlayerAmplua.GK;
		String s = "";
		if (defenceY + attackY <= 295) {
			s += 'Л';
		} else if (defenceY + attackY >= 605) {
			s += 'П';
		} else {
			s += 'Ц';
		}
		
		if (defenceX + attackX < 500) {
			s += 'З';
		} else if (defenceX + attackX >= 780) {
			s += 'Ф';
		} else {
			s += 'П';
		}
		
		return Player.positions.get(s);
	}
}
