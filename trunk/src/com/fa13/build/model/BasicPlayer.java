package com.fa13.build.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public abstract class BasicPlayer {
	
	protected int number;
	protected String name;
	protected PlayerAmplua position;
	protected final PlayerAmplua originalPosition;
	protected int id;
	
	protected BasicPlayer(){
		originalPosition = PlayerAmplua.CM;
	}
	
	protected BasicPlayer(PlayerAmplua position){
		originalPosition = position;
	}
	
	public static enum PlayerAmplua {
		GK("ВР"),
		LD("ЛЗ"), CD("ЦЗ"), RD("ПЗ"),
		LM("ЛП"), CM("ЦП"), RM("ПП"),
		LF("ЛФ"), CF("ЦФ"), RF("ПФ");
		private String position;
		PlayerAmplua(String s){
			position = s;
		}
		public String toString(){
			return position;
		}
		
		public static final double[][] positionCoefficients;
		
		static {
			positionCoefficients = new double[10][10];
			for (int i = 0; i < 10; i++) {
				Arrays.fill(positionCoefficients[i], 0.3);
				positionCoefficients[i][i] = 1;
			}
			positionCoefficients[PlayerAmplua.LD.ordinal()][PlayerAmplua.CD.ordinal()] = 0.9;
			positionCoefficients[PlayerAmplua.LD.ordinal()][PlayerAmplua.RD.ordinal()] = 0.9;
			positionCoefficients[PlayerAmplua.LD.ordinal()][PlayerAmplua.LM.ordinal()] = 0.75;
			positionCoefficients[PlayerAmplua.LD.ordinal()][PlayerAmplua.CM.ordinal()] = 0.5;
			positionCoefficients[PlayerAmplua.LD.ordinal()][PlayerAmplua.RM.ordinal()] = 0.5;
			
			positionCoefficients[PlayerAmplua.CD.ordinal()][PlayerAmplua.LD.ordinal()] = 0.9;
			positionCoefficients[PlayerAmplua.CD.ordinal()][PlayerAmplua.RD.ordinal()] = 0.9;
			positionCoefficients[PlayerAmplua.CD.ordinal()][PlayerAmplua.LM.ordinal()] = 0.5;
			positionCoefficients[PlayerAmplua.CD.ordinal()][PlayerAmplua.CM.ordinal()] = 0.75;
			positionCoefficients[PlayerAmplua.CD.ordinal()][PlayerAmplua.RM.ordinal()] = 0.5;
			
			positionCoefficients[PlayerAmplua.RD.ordinal()][PlayerAmplua.LD.ordinal()] = 0.9;
			positionCoefficients[PlayerAmplua.RD.ordinal()][PlayerAmplua.CD.ordinal()] = 0.9;
			positionCoefficients[PlayerAmplua.RD.ordinal()][PlayerAmplua.LM.ordinal()] = 0.5;
			positionCoefficients[PlayerAmplua.RD.ordinal()][PlayerAmplua.CM.ordinal()] = 0.5;
			positionCoefficients[PlayerAmplua.RD.ordinal()][PlayerAmplua.RM.ordinal()] = 0.75;
			
			positionCoefficients[PlayerAmplua.LM.ordinal()][PlayerAmplua.CM.ordinal()] = 0.9;
			positionCoefficients[PlayerAmplua.LM.ordinal()][PlayerAmplua.RM.ordinal()] = 0.9;
			positionCoefficients[PlayerAmplua.LM.ordinal()][PlayerAmplua.LD.ordinal()] = 0.75;
			positionCoefficients[PlayerAmplua.LM.ordinal()][PlayerAmplua.CD.ordinal()] = 0.5;
			positionCoefficients[PlayerAmplua.LM.ordinal()][PlayerAmplua.RD.ordinal()] = 0.5;
			positionCoefficients[PlayerAmplua.LM.ordinal()][PlayerAmplua.LF.ordinal()] = 0.75;
			positionCoefficients[PlayerAmplua.LM.ordinal()][PlayerAmplua.CF.ordinal()] = 0.5;
			positionCoefficients[PlayerAmplua.LM.ordinal()][PlayerAmplua.RF.ordinal()] = 0.5;
			
			positionCoefficients[PlayerAmplua.CM.ordinal()][PlayerAmplua.LM.ordinal()] = 0.9;
			positionCoefficients[PlayerAmplua.CM.ordinal()][PlayerAmplua.RM.ordinal()] = 0.9;
			positionCoefficients[PlayerAmplua.CM.ordinal()][PlayerAmplua.LD.ordinal()] = 0.5;
			positionCoefficients[PlayerAmplua.CM.ordinal()][PlayerAmplua.CD.ordinal()] = 0.75;
			positionCoefficients[PlayerAmplua.CM.ordinal()][PlayerAmplua.RD.ordinal()] = 0.5;
			positionCoefficients[PlayerAmplua.CM.ordinal()][PlayerAmplua.LF.ordinal()] = 0.5;
			positionCoefficients[PlayerAmplua.CM.ordinal()][PlayerAmplua.CF.ordinal()] = 0.75;
			positionCoefficients[PlayerAmplua.CM.ordinal()][PlayerAmplua.RF.ordinal()] = 0.5;
			
			positionCoefficients[PlayerAmplua.RM.ordinal()][PlayerAmplua.LM.ordinal()] = 0.9;
			positionCoefficients[PlayerAmplua.RM.ordinal()][PlayerAmplua.CM.ordinal()] = 0.9;
			positionCoefficients[PlayerAmplua.RM.ordinal()][PlayerAmplua.LD.ordinal()] = 0.5;
			positionCoefficients[PlayerAmplua.RM.ordinal()][PlayerAmplua.CD.ordinal()] = 0.5;
			positionCoefficients[PlayerAmplua.RM.ordinal()][PlayerAmplua.RD.ordinal()] = 0.75;
			positionCoefficients[PlayerAmplua.RM.ordinal()][PlayerAmplua.LF.ordinal()] = 0.5;
			positionCoefficients[PlayerAmplua.RM.ordinal()][PlayerAmplua.CF.ordinal()] = 0.5;
			positionCoefficients[PlayerAmplua.RM.ordinal()][PlayerAmplua.RF.ordinal()] = 0.75;
			
			positionCoefficients[PlayerAmplua.LF.ordinal()][PlayerAmplua.CF.ordinal()] = 0.9;
			positionCoefficients[PlayerAmplua.LF.ordinal()][PlayerAmplua.RF.ordinal()] = 0.9;
			positionCoefficients[PlayerAmplua.LF.ordinal()][PlayerAmplua.LM.ordinal()] = 0.75;
			positionCoefficients[PlayerAmplua.LF.ordinal()][PlayerAmplua.CM.ordinal()] = 0.5;
			positionCoefficients[PlayerAmplua.LF.ordinal()][PlayerAmplua.RM.ordinal()] = 0.5;
			
			positionCoefficients[PlayerAmplua.CF.ordinal()][PlayerAmplua.LF.ordinal()] = 0.9;
			positionCoefficients[PlayerAmplua.CF.ordinal()][PlayerAmplua.RF.ordinal()] = 0.9;
			positionCoefficients[PlayerAmplua.CF.ordinal()][PlayerAmplua.LM.ordinal()] = 0.5;
			positionCoefficients[PlayerAmplua.CF.ordinal()][PlayerAmplua.CM.ordinal()] = 0.75;
			positionCoefficients[PlayerAmplua.CF.ordinal()][PlayerAmplua.RM.ordinal()] = 0.5;
			
			positionCoefficients[PlayerAmplua.RF.ordinal()][PlayerAmplua.LF.ordinal()] = 0.9;
			positionCoefficients[PlayerAmplua.RF.ordinal()][PlayerAmplua.CF.ordinal()] = 0.9;
			positionCoefficients[PlayerAmplua.RF.ordinal()][PlayerAmplua.LM.ordinal()] = 0.5;
			positionCoefficients[PlayerAmplua.RF.ordinal()][PlayerAmplua.CM.ordinal()] = 0.5;
			positionCoefficients[PlayerAmplua.RF.ordinal()][PlayerAmplua.RM.ordinal()] = 0.75;
		}
	};
	
	public static final Map<String, PlayerAmplua> positions;
	
	static {
		Map<String, PlayerAmplua> tmp = new TreeMap<String, PlayerAmplua>();
		tmp.put("ВР", PlayerAmplua.GK);
		tmp.put("ЛЗ", PlayerAmplua.LD);
		tmp.put("ЦЗ", PlayerAmplua.CD);
		tmp.put("ПЗ", PlayerAmplua.RD);
		tmp.put("ЛП", PlayerAmplua.LM);
		tmp.put("ЦП", PlayerAmplua.CM);
		tmp.put("ПП", PlayerAmplua.RM);
		tmp.put("ЛФ", PlayerAmplua.LF);
		tmp.put("ЦФ", PlayerAmplua.CF);
		tmp.put("ПФ", PlayerAmplua.RF);
		positions = Collections.unmodifiableMap(tmp);
	}
}
