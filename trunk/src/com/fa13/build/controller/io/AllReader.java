package com.fa13.build.controller.io;

import java.io.*;
import java.util.*;
import java.util.zip.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import com.fa13.build.model.*;

public abstract class AllReader {
	public static All readAllFile(String filename) throws ReaderException {
		try {
			File allFile = new File(filename);
			InputStream allStream;
			if (filename.endsWith(".zip")) {
				String allName = allFile.getName();
				allName = allName.substring(0, allName.length() - 4).concat(".b13");
				ZipFile allZip = new ZipFile(allFile);
				allStream = allZip.getInputStream(new ZipEntry(allZip.getEntry(allName)));
			} else {
				allStream = new FileInputStream(allFile);
			}
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new InputStreamReader(allStream, "Cp1251"));
			} catch (UnsupportedEncodingException e) {
				System.err.println("Error while encoding all file");
			}
			All all = readAll(reader);
			reader.close();
			allStream.close();
			return all;
		} catch (ReaderException e) {
			throw e;
		} catch (Exception e) {
			throw new ReaderException("Unable to open all file: " + filename);
		}
	}

	public static All readAll(BufferedReader reader) throws ReaderException {
		try {
			String s = reader.readLine();
			if (s.compareTo("format=4") != 0) {
				System.err.println("Inappropriate file format");
				return null;
			}
			s = reader.readLine();
			SimpleDateFormat dateParser = new SimpleDateFormat("/dd.MM.yyyy/");
			Date date = null;
			try {
				date = dateParser.parse(s);
			} catch (ParseException e) {
				System.err.println("Inappropriate date format");
				return null;
			}

			Map<String, String> competitions = new TreeMap<String, String>();
			s = reader.readLine();

			while (!s.equals("/888/")) {
				String[] curr = s.replace('/', ' ').trim().split("=");
				String competitionFull = curr[0];
				String competitionShort = curr[1];
				competitions.put(competitionShort, competitionFull);
				s = reader.readLine();
			}

			s = reader.readLine().replace('/', ' ').trim();
			int bankRate = Integer.valueOf(s);

			List<Team> teams = new ArrayList<Team>();
			Team current = readTeam(reader);
			while (current != null) {
				teams.add(current);
				current = readTeam(reader);
			}
			Collections.sort(teams);
			return new All(date, competitions, bankRate, teams);
		} catch (Exception e) {
			throw new ReaderException("Incorrect file format");
		}
	}

	public static Team readTeam(BufferedReader reader) throws ReaderException {
		try {
			String s = reader.readLine();
			if (s == null)
				return null;
			if (s.trim().isEmpty())
				return null;

			String[] parsedData = s.split("/");
			int cnt = 1;
			String name = parsedData[cnt++];
			String id = parsedData[cnt++];
			String town = parsedData[cnt++];
			String countryCode = Player.nationalities.get(parsedData[cnt++]);
			String stadium = parsedData[cnt++];
			
			String nationalTeam = Player.nationalities.get(name);
			if (name.endsWith("-Ю")) {
				nationalTeam = Player.nationalities.get(name.substring(0, name.length() - 2));
			}

			s = reader.readLine();
			parsedData = s.split("/");
			cnt = 1;

			int managerId = Integer.valueOf(parsedData[cnt++]);
			String managerName = parsedData[cnt++];
			String managerTown = parsedData[cnt++];
			String managerCountry = parsedData[cnt++];
			String email = parsedData[cnt++];
			String icq = parsedData[cnt++].substring(4);
			int uin = 0;
			if (!icq.isEmpty()) {
				try {
					uin = Integer.valueOf(icq);
				} catch (NumberFormatException nfe) {
					uin = 0;
				}
			}
			int games = Integer.valueOf(parsedData[cnt++]);

			s = reader.readLine();
			parsedData = s.split("/");
			cnt = 1;

			int stadiumCapacity = Integer.valueOf(parsedData[cnt++]);
			int stadiumState = Integer.valueOf(parsedData[cnt++]);
			int boom = Integer.valueOf(parsedData[cnt++]);
			int teamFinance = Integer.valueOf(parsedData[cnt++]);
			int managerFinance = Integer.valueOf(parsedData[cnt++]);
			int rating = Integer.valueOf(parsedData[cnt++]);
			int sportbase = Integer.valueOf(parsedData[cnt++]);
			int sportbaseState = Integer.valueOf(parsedData[cnt++]);

			s = reader.readLine();
			parsedData = s.split("/");
			cnt = 1;

			boolean sportschool = (Integer.valueOf(parsedData[cnt++]) != 0);
			int sportschoolState = Integer.valueOf(parsedData[cnt++]);
			int coach = Integer.valueOf(parsedData[cnt++]) - 200;
			int goalkeepersCoach = Integer.valueOf(parsedData[cnt++]);
			int defendersCoach = Integer.valueOf(parsedData[cnt++]);
			int midfieldersCoach = Integer.valueOf(parsedData[cnt++]);
			int forwardsCoach = Integer.valueOf(parsedData[cnt++]);
			int fitnessCoach = Integer.valueOf(parsedData[cnt++]);
			int moraleCoach = Integer.valueOf(parsedData[cnt++]);
			int doctorQualification = Integer.valueOf(parsedData[cnt++]);
			int doctorPlayers = Integer.valueOf(parsedData[cnt++]);
			int scout = Integer.valueOf(parsedData[cnt++]);

			s = reader.readLine();
			parsedData = s.split("/");
			cnt = 1;

			int homeTop = Integer.valueOf(parsedData[cnt++]);
			int awayTop = Integer.valueOf(parsedData[cnt++]);
			int homeBottom = Integer.valueOf(parsedData[cnt++]);
			int awayBottom = Integer.valueOf(parsedData[cnt++]);

			s = reader.readLine().replace('/', ' ').trim();
			parsedData = s.split(",");

			Set<String> competitions = new TreeSet<String>();
			for (String curr : parsedData) {
				competitions.add(curr);
			}

			List<Player> players = new ArrayList<Player>(15);
			Player current = readPlayer(reader);
			while (current != null) {
				players.add(current);
				current = readPlayer(reader);
			}
			if (nationalTeam != null) {
				for (Iterator<Player> iterator = players.iterator(); iterator.hasNext();) {
					Player player = iterator.next();
					player.setNationalityCode(nationalTeam);
				}
			}
			Collections.sort(players);

			return new Team(name, id, town, countryCode, stadium, managerId, managerName, managerTown, managerCountry, email, uin, games, stadiumCapacity, stadiumState, boom, teamFinance, managerFinance,
					rating, sportbase, sportbaseState, sportschool, sportschoolState, coach, goalkeepersCoach, defendersCoach, midfieldersCoach, forwardsCoach, fitnessCoach, moraleCoach,
					doctorQualification, doctorPlayers, scout, homeTop, awayTop, homeBottom, awayBottom, competitions, players);
		} catch (Exception e) {
			throw new ReaderException("Incorrect file format");
		}
	}

	public static Player readPlayer(BufferedReader reader) throws ReaderException {
		try {
			String s = reader.readLine();
			if (s.equals("/999/"))
				return null;
			String[] parsedData = s.split("/");
			int cnt = 1;
			int number = Integer.valueOf(parsedData[cnt++]);
			String name = parsedData[cnt++];
			String nationality = parsedData[cnt++];
			String nationalityCode = Player.nationalities.get(nationality);
			Player.PlayerAmplua position = Player.positions.get(parsedData[cnt++]);
			int age = Integer.valueOf(parsedData[cnt++]);
			int talent = Integer.valueOf(parsedData[cnt++]);
			int experience = Integer.valueOf(parsedData[cnt++]);
			int fitness = Integer.valueOf(parsedData[cnt++]);
			int morale = Integer.valueOf(parsedData[cnt++]);
			int strength = Integer.valueOf(parsedData[cnt++]);
			int health = Integer.valueOf(parsedData[cnt++]);
			int price = Integer.valueOf(parsedData[cnt++]);
			int salary = Integer.valueOf(parsedData[cnt++]);
			int shooting = Integer.valueOf(parsedData[cnt++]);
			int passing = Integer.valueOf(parsedData[cnt++]);
			int crossing = Integer.valueOf(parsedData[cnt++]);
			int dribbling = Integer.valueOf(parsedData[cnt++]);
			int tackling = Integer.valueOf(parsedData[cnt++]);
			int heading = Integer.valueOf(parsedData[cnt++]);
			int speed = Integer.valueOf(parsedData[cnt++]);
			int stamina = Integer.valueOf(parsedData[cnt++]);
			int reflexes = Integer.valueOf(parsedData[cnt++]);
			int handling = Integer.valueOf(parsedData[cnt++]);
			int disqualification = Integer.valueOf(parsedData[cnt++]);
			int rest = Integer.valueOf(parsedData[cnt++]);
			int teamwork = Integer.valueOf(parsedData[cnt++]);
			int games = Integer.valueOf(parsedData[cnt++]);
			int goalsTotal = Integer.valueOf(parsedData[cnt++]);
			int goalsMissed = Integer.valueOf(parsedData[cnt++]);
			int goalsChamp = Integer.valueOf(parsedData[cnt++]);
			double mark = Double.valueOf(parsedData[cnt++]) / 100.0;
			int gamesCareer = Integer.valueOf(parsedData[cnt++]);
			int goalsCareer = Integer.valueOf(parsedData[cnt++]);
			int yellowCards = Integer.valueOf(parsedData[cnt++]);
			int redCards = Integer.valueOf(parsedData[cnt++]);
			boolean transfer = (Integer.valueOf(parsedData[cnt++]) != 0);
			boolean lease = (Integer.valueOf(parsedData[cnt++]) != 0);
			String birthplace = parsedData[cnt++];
			String date = parsedData[cnt++];
			Date birthdate = null;
			int dateBegin = date.indexOf('(');
			int dateEnd = date.indexOf(')');
			String dateOnly = date.substring(0, dateBegin == - 1 ? date.length() : dateBegin);
			SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd");
			try {
				birthdate = dateParser.parse(dateOnly);
			} catch (ParseException e) {
			}
			int birthtour = 0;
			if (dateBegin != -1) {
				date = date.substring(dateBegin + 1, dateEnd);
				birthtour = Integer.valueOf(date);
			}
			int assists = Integer.valueOf(parsedData[cnt++]);
			int profit = Integer.valueOf(parsedData[cnt++]);
			int id = Integer.valueOf(parsedData[cnt++]);

			return new Player(number, name, nationalityCode, position, age, talent, experience, fitness, morale, strength, health, price, salary, shooting, passing, crossing, dribbling, tackling,
					speed, heading, stamina, reflexes, handling, disqualification, rest, teamwork, games, goalsTotal, goalsMissed, goalsChamp, mark, gamesCareer, goalsCareer, yellowCards, redCards,
					transfer, lease, birthplace, birthdate, birthtour, assists, profit, id);
		} catch (Exception e) {
			throw new ReaderException("Incorrect file format");
		}
	}
}
