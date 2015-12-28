package com.fa13.build.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class TeamTactics {
	public static enum Tactics {
		TC_DEFENCE("защита"),
		TC_BALANCE("баланс"),
		TC_ATTACK("атака");
		String type;
		Tactics (String s) {
			type = s;
		}
		
		public String toString() {
			return type;
		}
	};
	
	public static final Map<String, Tactics> tacticsMap;
	
	static {
		Map<String, Tactics> tmp = new TreeMap<String, Tactics>();
		tmp.put("защита", Tactics.TC_DEFENCE);
		tmp.put("баланс", Tactics.TC_BALANCE);
		tmp.put("атака", Tactics.TC_ATTACK);
		tacticsMap = Collections.unmodifiableMap(tmp);
	}
	
	Tactics teamTactics;
	
	public static enum Hardness {
		HR_DEFAULT("по умолчанию"),
		HR_SOFT("аккуратно"),
		HR_NORMAL("нормально"),
		HR_HARD("жёстко"),
		HR_NIGHTMARE("максимально жёстко");
		String type;
		
		Hardness (String s) {
			type = s;
		}
		
		public String toString() {
			return type;
		}
	};
	
	public static final Map<String, Hardness> hardnessMap;
	
	static {
		Map<String, Hardness> tmp = new HashMap<String, Hardness>();
		tmp.put("по умолчанию", Hardness.HR_DEFAULT);
		tmp.put("аккуратно", Hardness.HR_SOFT);
		tmp.put("нормально", Hardness.HR_NORMAL);
		tmp.put("жёстко", Hardness.HR_HARD);
		tmp.put("максимально жёстко", Hardness.HR_NIGHTMARE);
		hardnessMap = Collections.unmodifiableMap(tmp);
	}
	
	Hardness teamHardness;
	
	public static enum Style {
		ST_FLANG("фланг"),
		ST_COMBINE("комбина"),
		ST_CENTER("центр"),
		ST_ENGLAND("англия"),
		ST_LONGBALL("длина"),
		ST_UNIVERSAL("универ");
		
		String type;
		Style (String s) {
			type = s;
		}
		
		public String toString() {
			return type;
		}
	};
	
	public static final Map<String, Style> styleMap;
	
	static {
		Map<String, Style> tmp = new HashMap<String, Style>();
		tmp.put("фланг", Style.ST_FLANG);
		tmp.put("комбина", Style.ST_COMBINE);
		tmp.put("центр", Style.ST_CENTER);
		tmp.put("англия", Style.ST_ENGLAND);
		tmp.put("длина", Style.ST_LONGBALL);
		tmp.put("универ", Style.ST_UNIVERSAL);
		styleMap = Collections.unmodifiableMap(tmp);
	}
	
	Style teamStyle;
	
	int minimumDifference, maximumDifference;
	
	public TeamTactics(Tactics teamTactics, Hardness teamHardness,
			Style teamStyle, int minimumDifference, int maximumDifference) {
		this.teamTactics = teamTactics;
		this.teamHardness = teamHardness;
		this.teamStyle = teamStyle;
		this.minimumDifference = minimumDifference;
		this.maximumDifference = maximumDifference;
	}

	public Tactics getTeamTactics() {
		return teamTactics;
	}

	public void setTeamTactics(Tactics teamTactics) {
		this.teamTactics = teamTactics;
	}

	public Hardness getTeamHardness() {
		return teamHardness;
	}

	public void setTeamHardness(Hardness teamHardness) {
		this.teamHardness = teamHardness;
	}

	public Style getTeamStyle() {
		return teamStyle;
	}

	public void setTeamStyle(Style teamStyle) {
		this.teamStyle = teamStyle;
	}

	public int getMinimumDifference() {
		return minimumDifference;
	}

	public void setMinimumDifference(int minimumDifference) {
		this.minimumDifference = minimumDifference;
	}

	public int getMaximumDifference() {
		return maximumDifference;
	}

	public void setMaximumDifference(int maximumDifference) {
		this.maximumDifference = maximumDifference;
	}
}
