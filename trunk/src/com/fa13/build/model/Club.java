package com.fa13.build.model;

public class Club {
	String password;
	String email = null;
	int icq = -1;
	
	public static enum TriAction { 
		TR_NOTHING,
		TR_BUILD,
		TR_REPAIR
	}
	
	TriAction stadiumAction;
	int stadium = -1;
	
	TriAction sportBaseAction;
	int sportBase = -1;
	
	TriAction sportSchoolAction;
	boolean school = false;
	
	int coachCoef = 0;
	int coachGK = -1;
	int coachDef = -1;
	int coachMid = -1;
	int coachFor = -1;
	int coachFitness = -1;
	int coachMorale = -1;
	int doctorLevel = -1;
	int doctorCount = -1;
	int scoutLevel = -1;
	int color1 = -1;
	int color2 = -1;
	int color3 = -1;
	int color4 = -1;
	
	public Club(String password, String email, int icq, TriAction stadiumAction,
			int stadium, TriAction sportBaseAction, int sportBase,
			TriAction sportSchoolAction, boolean school, int coachCoef,
			int coachGK, int coachDef, int coachMid, int coachFor,
			int coachFitness, int coachMorale, int doctorLevel,
			int doctorCount, int scoutLevel, int color1, int color2,
			int color3, int color4) {
		this.password = password;
		this.email = email;
		this.icq = icq;
		this.stadiumAction = stadiumAction;
		this.stadium = stadium;
		this.sportBaseAction = sportBaseAction;
		this.sportBase = sportBase;
		this.sportSchoolAction = sportSchoolAction;
		this.school = school;
		this.coachCoef = coachCoef;
		this.coachGK = coachGK;
		this.coachDef = coachDef;
		this.coachMid = coachMid;
		this.coachFor = coachFor;
		this.coachFitness = coachFitness;
		this.coachMorale = coachMorale;
		this.doctorLevel = doctorLevel;
		this.doctorCount = doctorCount;
		this.scoutLevel = scoutLevel;
		this.color1 = color1;
		this.color2 = color2;
		this.color3 = color3;
		this.color4 = color4;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getIcq() {
		return icq;
	}

	public void setIcq(int icq) {
		this.icq = icq;
	}

	public TriAction getStadiumAction() {
		return stadiumAction;
	}

	public void setStadiumAction(TriAction stadiumAction) {
		this.stadiumAction = stadiumAction;
	}

	public int getStadium() {
		return stadium;
	}

	public void setStadium(int stadium) {
		this.stadium = stadium;
	}

	public TriAction getSportBaseAction() {
		return sportBaseAction;
	}

	public void setSportBaseAction(TriAction sportBaseAction) {
		this.sportBaseAction = sportBaseAction;
	}

	public int getSportBase() {
		return sportBase;
	}

	public void setSportBase(int sportBase) {
		this.sportBase = sportBase;
	}

	public TriAction getSportSchoolAction() {
		return sportSchoolAction;
	}

	public void setSportSchoolAction(TriAction sportSchoolAction) {
		this.sportSchoolAction = sportSchoolAction;
	}

	public boolean isSchool() {
		return school;
	}

	public void setSchool(boolean school) {
		this.school = school;
	}

	public int getCoachCoef() {
		return coachCoef;
	}

	public void setCoachCoef(int coachCoef) {
		this.coachCoef = coachCoef;
	}

	public int getCoachGK() {
		return coachGK;
	}

	public void setCoachGK(int coachGK) {
		this.coachGK = coachGK;
	}

	public int getCoachDef() {
		return coachDef;
	}

	public void setCoachDef(int coachDef) {
		this.coachDef = coachDef;
	}

	public int getCoachMid() {
		return coachMid;
	}

	public void setCoachMid(int coachMid) {
		this.coachMid = coachMid;
	}

	public int getCoachFor() {
		return coachFor;
	}

	public void setCoachFor(int coachFor) {
		this.coachFor = coachFor;
	}

	public int getCoachFitness() {
		return coachFitness;
	}

	public void setCoachFitness(int coachFitness) {
		this.coachFitness = coachFitness;
	}

	public int getCoachMorale() {
		return coachMorale;
	}

	public void setCoachMorale(int coachMorale) {
		this.coachMorale = coachMorale;
	}

	public int getDoctorLevel() {
		return doctorLevel;
	}

	public void setDoctorLevel(int doctorLevel) {
		this.doctorLevel = doctorLevel;
	}

	public int getDoctorCount() {
		return doctorCount;
	}

	public void setDoctorCount(int doctorCount) {
		this.doctorCount = doctorCount;
	}

	public int getScoutLevel() {
		return scoutLevel;
	}

	public void setScoutLevel(int scoutLevel) {
		this.scoutLevel = scoutLevel;
	}

	public int getColor1() {
		return color1;
	}

	public void setColor1(int color1) {
		this.color1 = color1;
	}

	public int getColor2() {
		return color2;
	}

	public void setColor2(int color2) {
		this.color2 = color2;
	}

	public int getColor3() {
		return color3;
	}

	public void setColor3(int color3) {
		this.color3 = color3;
	}

	public int getColor4() {
		return color4;
	}

	public void setColor4(int color4) {
		this.color4 = color4;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
