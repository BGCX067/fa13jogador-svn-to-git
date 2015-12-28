package com.fa13.build.controller.io;

import com.fa13.build.model.*;
import com.fa13.build.model.GameForm.CoachSettings;
import com.fa13.build.model.GameForm.SubstitutionPreferences;
import com.fa13.build.model.PlayerPosition.*;
import com.fa13.build.model.TeamTactics.Hardness;
import com.fa13.build.model.TeamTactics.Style;
import com.fa13.build.model.TeamTactics.Tactics;

import java.io.*;

public abstract class GameReader {
	public static GameForm readGameFormFile(String filename) throws ReaderException {
		try {
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "Cp1251"));
			} catch (UnsupportedEncodingException e) {
				System.err.println("Error while encoding training file");
			}
			GameForm game = readGameForm(reader);
			reader.close();
			return game;
		} catch (ReaderException e) {
			throw e;
		} catch (Exception e) {
			throw new ReaderException("Unable to open game file: " + filename);
		}
	}

	public static GameForm readGameForm(BufferedReader reader) throws ReaderException {
		try {
			String[] parsedData;
			int cnt;
			String s = reader.readLine();
			if (s.compareTo("Заявка составлена с помощью Build-1401") != 0) {
				System.err.println("Inappropriate file format");
				return null;
			}
			s = reader.readLine();
			s = reader.readLine();
			s = reader.readLine();

			String tournamentID = reader.readLine();
			String gameType = reader.readLine();
			String teamID = reader.readLine();
			String password = reader.readLine();
			s = reader.readLine();
			Tactics tact = TeamTactics.tacticsMap.get(reader.readLine());
			Hardness hard = TeamTactics.hardnessMap.get(reader.readLine());
			Style style = TeamTactics.styleMap.get(reader.readLine());
			CoachSettings teamCoachSettings = GameForm.settingsMap.get(reader.readLine());
			TeamTactics startTactics = new TeamTactics(tact, hard, style, 0, 0);

			PlayerPosition[] firstTeam = new PlayerPosition[11];
			for (int i = 0; i < 11; i++) {
				firstTeam[i] = readPlayerPosition(reader.readLine());
			}
			firstTeam[0].setGoalkeeper(true);
			s = reader.readLine();
			int[] bench = new int[7];
			for (int i = 0; i < 7; i++) {
				bench[i] = Integer.valueOf(reader.readLine());
			}

			s = reader.readLine();
			for (int i = 0; i < 6; i++) {
				s = reader.readLine();
				parsedData = s.split(" ");
				int main = Integer.valueOf(parsedData[0]);
				int assist = Integer.valueOf(parsedData[1]);
				int role;
				switch (parsedData[2].charAt(0)) {
				case 'к':
					role = PlayerPosition.SR_CAPTAIN;
					break;
				case 'п':
					role = PlayerPosition.SR_PENALTY;
					break;
				case 'ш':
					role = PlayerPosition.SR_DIRECT_FREEKICK;
					break;
				case 'с':
					role = PlayerPosition.SR_INDIRECT_FREEKICK;
					break;
				case 'у': {
					switch (parsedData[2].charAt(1)) {
					case 'л':
						role = PlayerPosition.SR_LEFT_CORNER;
						break;
					case 'п':
						role = PlayerPosition.SR_RIGHT_CORNER;
						break;
					default:
						role = 0;
					}
					break;
				}
				default:
					role = 0;
				}
				for (int j = 0; j < 11; j++) {
					if (firstTeam[j].getNumber() == main) {
						firstTeam[j].setSpecialRole(firstTeam[j].getSpecialRole() | role);
					}

					if (firstTeam[j].getNumber() == assist) {
						firstTeam[j].setSpecialRole(firstTeam[j].getSpecialRole() | (role << PlayerPosition.SR_ASSISTANT));
					}
				}
			}

			s = reader.readLine();
			s = reader.readLine();
			parsedData = s.split(" ");
			for (int i = 0; i < parsedData.length; i++) {
				int curr = Integer.valueOf(parsedData[i]);
				for (int j = 0; j < 11; j++) {
					if (firstTeam[j].getNumber() == curr) {
						firstTeam[j].setPenaltyOrder(i + 1);
					}
				}
			}

			s = reader.readLine();
			int price = Integer.valueOf(reader.readLine());

			s = reader.readLine();
			PlayerSubstitution[] substitutions = new PlayerSubstitution[25];
			for (int i = 0; i < 25; i++) {
				substitutions[i] = readPlayerSubstitution(reader.readLine());
			}

			SubstitutionPreferences substitutionPreferences = Integer.valueOf(reader.readLine()) == 0 ? SubstitutionPreferences.SP_POSITION : SubstitutionPreferences.SP_STRENGTH;

			s = reader.readLine();

			s = reader.readLine();
			parsedData = s.split(" ");
			cnt = 0;
			int defenceTime = Integer.valueOf(parsedData[cnt++]);
			int defenceMin = Integer.valueOf(parsedData[cnt++]);
			int defenceMax = Integer.valueOf(parsedData[cnt++]);

			s = reader.readLine();
			parsedData = s.split(" ");
			cnt = 0;
			int attackTime = Integer.valueOf(parsedData[cnt++]);
			int attackMin = Integer.valueOf(parsedData[cnt++]);
			int attackMax = Integer.valueOf(parsedData[cnt++]);

			TeamTactics[] halftimeChanges = new TeamTactics[5];

			for (int i = 0; i < 5; i++) {
				s = reader.readLine();
				if (s.equals("1 0 тактика агрессивность стиль"))
					continue;
				parsedData = s.split(" ");
				int minD = Integer.valueOf(parsedData[0]);
				int maxD = Integer.valueOf(parsedData[1]);
				Tactics tc = TeamTactics.tacticsMap.get(parsedData[2]);
				Hardness hr = TeamTactics.hardnessMap.get(parsedData.length == 5 ? parsedData[3] : parsedData[3] + " " + parsedData[4]);
				Style st = TeamTactics.styleMap.get(parsedData.length == 5 ? parsedData[4] : parsedData[5]);
				halftimeChanges[i] = new TeamTactics(tc, hr, st, minD, maxD);
			}

			return new GameForm(tournamentID, gameType, teamID, password, startTactics, teamCoachSettings, firstTeam, bench, price, substitutions, substitutionPreferences, defenceTime, defenceMin,
					defenceMax, attackTime, attackMin, attackMax, halftimeChanges);
		} catch (Exception e) {
			throw new ReaderException("Incorrect file format");
		}
	}

	public static PlayerPosition readPlayerPosition(String line) throws ReaderException {
		try {
			String[] parsedData = line.split(" ");
			int cnt = 0;
			cnt++; // bypassing position value
			int number = Integer.valueOf(parsedData[cnt++]);
			int defenceX = Math.round((Float.valueOf(parsedData[cnt++]) + 60) * 5);
			int defenceY = Math.round((Float.valueOf(parsedData[cnt++]) + 45) * 5);
			int attackX = Math.round((Float.valueOf(parsedData[cnt++]) + 60) * 5);
			int attackY = Math.round((Float.valueOf(parsedData[cnt++]) + 45) * 5);
			cnt++; // magic -60.0, bypassing
			cnt++; // magic 0.0, bypassing
			int freekickX = Math.round((Float.valueOf(parsedData[cnt++]) + 60) * 5);
			int freekickY = Math.round((Float.valueOf(parsedData[cnt++]) + 45) * 5);
			int specialRole = 0;
			int penalty = 0;
			boolean longShot = Integer.valueOf(parsedData[cnt++]) == 1;
			FreekicksAction actOnFreekick;
			switch (Integer.valueOf(parsedData[cnt++])) {
			case 1:
				actOnFreekick = FreekicksAction.FK_YES;
				break;
			case 2:
				actOnFreekick = FreekicksAction.FK_NO;
				break;
			default:
				actOnFreekick = FreekicksAction.FK_DEFAULT;
				break;
			}
			boolean fantasista = Integer.valueOf(parsedData[cnt++]) == 1;
			boolean dispatcher = Integer.valueOf(parsedData[cnt++]) == 1;
			int personalDefence = Integer.valueOf(parsedData[cnt++]);
			boolean pressing = Integer.valueOf(parsedData[cnt++]) == 1;
			boolean keepBall = Integer.valueOf(parsedData[cnt++]) == 1;
			boolean defenderAttack = Integer.valueOf(parsedData[cnt++]) == 1;
			PassingStyle passingStyle;
			switch (Integer.valueOf(parsedData[cnt++])) {
			case 1:
				passingStyle = PassingStyle.PS_EXACT;
				break;
			case 2:
				passingStyle = PassingStyle.PS_FORWARD;
				break;
			default:
				passingStyle = PassingStyle.PS_BOTH;
				break;
			}

			Hardness hardness;
			switch (Integer.valueOf(parsedData[cnt++])) {
			case 1:
				hardness = Hardness.HR_SOFT;
				break;
			case 2:
				hardness = Hardness.HR_NORMAL;
				break;
			case 3:
				hardness = Hardness.HR_HARD;
				break;
			case 4:
				hardness = Hardness.HR_NIGHTMARE;
				break;
			default:
				hardness = Hardness.HR_DEFAULT;
				break;
			}
			return new PlayerPosition(defenceX, defenceY, attackX, attackY, freekickX, freekickY, number, specialRole, penalty, longShot, actOnFreekick, fantasista, dispatcher, personalDefence,
					pressing, keepBall, defenderAttack, hardness, passingStyle);
		} catch (Exception e) {
			throw new ReaderException("Incorrect file format");
		}
	}

	public static PlayerSubstitution readPlayerSubstitution(String line) throws ReaderException {
		try {
			String[] parsedData = line.split(" ");
			if (parsedData.length == 8) {
				return new PlayerSubstitution();
			}
			int cnt = 0;
			cnt++; // bypassing magic 0 value
			int time = Integer.valueOf(parsedData[cnt++]);
			int minDifference = Integer.valueOf(parsedData[cnt++]);
			int maxDifference = Integer.valueOf(parsedData[cnt++]);
			int substitutedPlayer = Integer.valueOf(parsedData[cnt++]);
			int number = Integer.valueOf(parsedData[cnt++]);
			int specialRole = Integer.valueOf(parsedData[cnt++]);
			if (specialRole < 0)
				specialRole = 0;
			int defenceX = Math.round((Float.valueOf(parsedData[cnt++]) + 60) * 5);
			int defenceY = Math.round((Float.valueOf(parsedData[cnt++]) + 45) * 5);
			int attackX = Math.round((Float.valueOf(parsedData[cnt++]) + 60) * 5);
			int attackY = Math.round((Float.valueOf(parsedData[cnt++]) + 45) * 5);
			int freekickX = Math.round((Float.valueOf(parsedData[cnt++]) + 60) * 5);
			int freekickY = Math.round((Float.valueOf(parsedData[cnt++]) + 45) * 5);

			int penalty = 0;
			boolean longShot = Integer.valueOf(parsedData[cnt++]) == 1;
			FreekicksAction actOnFreekick;
			switch (Integer.valueOf(parsedData[cnt++])) {
			case 1:
				actOnFreekick = FreekicksAction.FK_YES;
				break;
			case 2:
				actOnFreekick = FreekicksAction.FK_NO;
				break;
			default:
				actOnFreekick = FreekicksAction.FK_DEFAULT;
				break;
			}
			boolean fantasista = Integer.valueOf(parsedData[cnt++]) == 1;
			boolean dispatcher = Integer.valueOf(parsedData[cnt++]) == 1;
			int personalDefence = Integer.valueOf(parsedData[cnt++]);
			boolean pressing = Integer.valueOf(parsedData[cnt++]) == 1;
			boolean keepBall = Integer.valueOf(parsedData[cnt++]) == 1;
			boolean defenderAttack = Integer.valueOf(parsedData[cnt++]) == 1;
			PassingStyle passingStyle;
			switch (Integer.valueOf(parsedData[cnt++])) {
			case 1:
				passingStyle = PassingStyle.PS_EXACT;
				break;
			case 2:
				passingStyle = PassingStyle.PS_FORWARD;
				break;
			default:
				passingStyle = PassingStyle.PS_BOTH;
				break;
			}

			Hardness hardness;
			switch (Integer.valueOf(parsedData[cnt++])) {
			case 1:
				hardness = Hardness.HR_SOFT;
				break;
			case 2:
				hardness = Hardness.HR_NORMAL;
				break;
			case 3:
				hardness = Hardness.HR_HARD;
				break;
			case 4:
				hardness = Hardness.HR_NIGHTMARE;
				break;
			default:
				hardness = Hardness.HR_DEFAULT;
				break;
			}
			return new PlayerSubstitution(defenceX, defenceY, attackX, attackY, freekickX, freekickY, number, specialRole, penalty, longShot, actOnFreekick, fantasista, dispatcher, personalDefence,
					pressing, keepBall, defenderAttack, hardness, passingStyle, time, minDifference, maxDifference, substitutedPlayer);
		} catch (Exception e) {
			throw new ReaderException("Incorrect file format");
		}
	}
}
