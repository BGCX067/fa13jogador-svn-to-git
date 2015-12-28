package com.fa13.build.controller.io;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import com.fa13.build.model.Club;
import com.fa13.build.model.Club.TriAction;

public class ClubReader {
	public static Club readClubFile(String filename) throws ReaderException {
		try {
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "Cp1251"));
			} catch (UnsupportedEncodingException e) {
				System.err.println("Error while encoding training file");
			}
			Club club = readClub(reader);
			reader.close();
			return club;
		} catch (ReaderException e) {
			throw e;
		} catch (Exception e) {
			throw new ReaderException("Unable to open club file: " + filename);
		}
	}

	public static Club readClub(BufferedReader reader) throws ReaderException {
		try {
			String s = reader.readLine();
			if (s.compareTo("Заявка составлена с помощью Build-1401") != 0) {
				System.err.println("Inappropriate file format");
				return null;
			}
			s = reader.readLine();
			s = reader.readLine();
			s = reader.readLine();
			String password = reader.readLine().split("=")[1];

			Map<String, String> tmp = new HashMap<String, String>();
			s = reader.readLine();

			while (!s.equals("*****")) {
				String[] parsedData = s.split("=");
				tmp.put(parsedData[0], parsedData[1]);
				s = reader.readLine();
			}

			String email = tmp.get("email");
			s = tmp.get("icq");
			int icq = (s == null) ? -1 : Integer.valueOf(s);

			s = tmp.get("stadion");
			TriAction stadiumAction = TriAction.TR_NOTHING;
			int stadium = -1;
			if (s != null) {
				if (s.equals("remont")) {
					stadiumAction = TriAction.TR_REPAIR;
				} else {
					stadiumAction = TriAction.TR_BUILD;
					stadium = Integer.valueOf(s);
				}
			}

			s = tmp.get("basa");
			TriAction sportBaseAction = TriAction.TR_NOTHING;
			int sportBase = -1;
			if (s != null) {
				if (s.equals("remont")) {
					sportBaseAction = TriAction.TR_REPAIR;
				} else {
					sportBaseAction = TriAction.TR_BUILD;
					sportBase = Integer.valueOf(s);
				}
			}

			s = tmp.get("schoole");
			TriAction sportSchoolAction = TriAction.TR_NOTHING;
			boolean school = false;
			if (s != null) {
				if (s.equals("remont")) {
					sportSchoolAction = TriAction.TR_REPAIR;
				} else {
					sportSchoolAction = TriAction.TR_BUILD;
					school = s.equals("yes");
				}
			}

			s = tmp.get("KGT");
			int coachCoef = (s == null) ? 0 : Integer.valueOf(s);

			s = tmp.get("gk");
			int coachGK = (s == null) ? -1 : Integer.valueOf(s);

			s = tmp.get("def");
			int coachDef = (s == null) ? -1 : Integer.valueOf(s);

			s = tmp.get("mid");
			int coachMid = (s == null) ? -1 : Integer.valueOf(s);

			s = tmp.get("for");
			int coachFor = (s == null) ? -1 : Integer.valueOf(s);

			s = tmp.get("phys");
			int coachFitness = (s == null) ? -1 : Integer.valueOf(s);

			s = tmp.get("psy");
			int coachMorale = (s == null) ? -1 : Integer.valueOf(s);

			s = tmp.get("doc");
			int doctorLevel = -1;
			int doctorCount = -1;
			if (s != null) {
				String[] parsedData = s.split(",");
				doctorLevel = Integer.valueOf(parsedData[1]);
				doctorCount = Integer.valueOf(parsedData[0]);
			}

			s = tmp.get("scout");
			int scoutLevel = (s == null) ? -1 : Integer.valueOf(s);

			s = tmp.get("color1");
			int color1 = (s == null) ? -1 : Integer.valueOf(s);

			s = tmp.get("color2");
			int color2 = (s == null) ? -1 : Integer.valueOf(s);

			s = tmp.get("color3");
			int color3 = (s == null) ? -1 : Integer.valueOf(s);

			s = tmp.get("color4");
			int color4 = (s == null) ? -1 : Integer.valueOf(s);

			return new Club(password, email, icq, stadiumAction, stadium, sportBaseAction, sportBase, sportSchoolAction, school, coachCoef, coachGK, coachDef, coachMid, coachFor, coachFitness,
					coachMorale, doctorLevel, doctorCount, scoutLevel, color1, color2, color3, color4);
		} catch (Exception e) {
			throw new ReaderException("Incorrect file format");
		}
	}
}
