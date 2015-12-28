package com.fa13.build.controller.io;

import com.fa13.build.model.*;

import java.io.*;
import java.util.*;

public abstract class TrainingReader {
	public static Training readTrainingFile(String filename) throws ReaderException {
		try {
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "Cp1251"));
			} catch (UnsupportedEncodingException e) {
				System.err.println("Error while encoding training file");
			}
			Training training = readTraining(reader);
			reader.close();
			return training;
		} catch (ReaderException e) {
			throw e;
		} catch (Exception e) {
			throw new ReaderException("Unable to open training file: " + filename);
		}
	}

	public static Training readTraining(BufferedReader reader) throws ReaderException {
		try {
			String s = reader.readLine();
			if (s.compareTo("Заявка составлена с помощью Build-1401") != 0) {
				System.err.println("Inappropriate file format");
				return null;
			}
			s = reader.readLine();
			s = reader.readLine();
			s = reader.readLine();
			String password = reader.readLine();

			int minTalent = Integer.valueOf(reader.readLine());
			boolean scouting = (minTalent != 0);
			List<PlayerTraining> players = new ArrayList<PlayerTraining>(15);
			PlayerTraining current = readPlayerTraining(reader);
			while (current != null) {
				players.add(current);
				current = readPlayerTraining(reader);
			}

			return new Training(password, minTalent, scouting, players);
		} catch (Exception e) {
			throw new ReaderException("Incorrect file format");
		}
	}

	public static PlayerTraining readPlayerTraining(BufferedReader reader) throws ReaderException {
		try {
			String s = reader.readLine().concat("0/");
			String[] parsedData = s.split("/");
			int cnt = 0;
			int number = Integer.valueOf(parsedData[cnt++]);
			if (number == 999)
				return null;

			int shootingPoints = 0;
			if (!parsedData[cnt].isEmpty()) {
				shootingPoints = Integer.valueOf(parsedData[cnt]);
			}
			cnt++;

			int passingPoints = 0;
			if (!parsedData[cnt].isEmpty()) {
				passingPoints = Integer.valueOf(parsedData[cnt]);
			}
			cnt++;

			int crossPoints = 0;
			if (!parsedData[cnt].isEmpty()) {
				crossPoints = Integer.valueOf(parsedData[cnt]);
			}
			cnt++;

			int dribblingPoints = 0;
			if (!parsedData[cnt].isEmpty()) {
				dribblingPoints = Integer.valueOf(parsedData[cnt]);
			}
			cnt++;

			int tacklingPoints = 0;
			if (!parsedData[cnt].isEmpty()) {
				tacklingPoints = Integer.valueOf(parsedData[cnt]);
			}
			cnt++;

			int speedPoints = 0;
			if (!parsedData[cnt].isEmpty()) {
				speedPoints = Integer.valueOf(parsedData[cnt]);
			}
			cnt++;

			int staminaPoints = 0;
			if (!parsedData[cnt].isEmpty()) {
				staminaPoints = Integer.valueOf(parsedData[cnt]);
			}
			cnt++;

			int headingPoints = 0;
			if (!parsedData[cnt].isEmpty()) {
				headingPoints = Integer.valueOf(parsedData[cnt]);
			}
			cnt++;

			int reflexesPoints = 0;
			if (!parsedData[cnt].isEmpty()) {
				reflexesPoints = Integer.valueOf(parsedData[cnt]);
			}
			cnt++;

			int handlingPoints = 0;
			if (!parsedData[cnt].isEmpty()) {
				handlingPoints = Integer.valueOf(parsedData[cnt]);
			}
			cnt++;

			int fitnessPoints = 0;
			if (!parsedData[cnt].isEmpty()) {
				fitnessPoints = Integer.valueOf(parsedData[cnt]);
			}
			cnt++;

			int moraleFinance = 0;
			if (!parsedData[cnt].isEmpty()) {
				moraleFinance = Integer.valueOf(parsedData[cnt]);
			}
			cnt++;

			int fitnessFinance = 0;
			if (!parsedData[cnt].isEmpty()) {
				fitnessFinance = Integer.valueOf(parsedData[cnt]);
			}
			cnt++;

			return new PlayerTraining(number, shootingPoints, passingPoints, crossPoints, dribblingPoints, tacklingPoints, speedPoints, staminaPoints, headingPoints, reflexesPoints, handlingPoints,
					fitnessPoints, moraleFinance, fitnessFinance);
		} catch (Exception e) {
			throw new ReaderException("Incorrect file format");
		}
	}
}
