package com.fa13.build.controller.io;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fa13.build.model.Player;
import com.fa13.build.model.TransferList;
import com.fa13.build.model.TransferPlayer;

public class TransferListReader {
	private static int transferID;

	public static TransferList readTransferListFile(String filename) throws ReaderException {
		try {
			File transferListFile = new File(filename);
			InputStream transferListStream = new FileInputStream(transferListFile);
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new InputStreamReader(transferListStream, "Cp1251"));
			} catch (UnsupportedEncodingException e) {
				System.err.println("Error while encoding transfer list file");
			}
			TransferList tlist = readTransferList(reader);
			reader.close();
			return tlist;
		} catch (ReaderException e) {
			throw e;
		} catch (Exception e) {
			throw new ReaderException("Unable to open transfer list file: " + filename);
		}
	}

	public static TransferList readTransferList(BufferedReader reader) throws ReaderException {
		try {
			String s = reader.readLine();
			if (s.compareTo("format=1") != 0) {
				System.err.println("Inappropriate file format");
				return null;
			}
			s = reader.readLine();
			SimpleDateFormat dateParser = new SimpleDateFormat("dd.MM.yyyy");
			Date date = null;
			try {
				date = dateParser.parse(s);
			} catch (ParseException e) {
				System.err.println("Inappropriate date format");
				return null;
			}
			transferID = 1;
			List<TransferPlayer> players = new ArrayList<TransferPlayer>();
			TransferPlayer curr = readTransferPlayer(reader);
			while (curr != null) {
				players.add(curr);
				curr = readTransferPlayer(reader);
			}
			transferID = 1;
			return new TransferList(date, players);
		} catch (Exception e) {
			throw new ReaderException("Incorrect file format");
		}
	}

	public static TransferPlayer readTransferPlayer(BufferedReader reader) throws ReaderException {
		try {
			String s = reader.readLine();
			if (s == null)
				return null;
			if (s.trim().isEmpty())
				return null;
			String[] parsedData = s.split("/");
			if (parsedData.length == 1)
				return null;
			int pos = 0;
			int id = Integer.valueOf(parsedData[pos++]);
			String name = parsedData[pos++];
			String nationality = parsedData[pos++];
			String nationalityCode = Player.nationalities.get(nationality);
			String previousTeam = parsedData[pos++];
			Player.PlayerAmplua position = Player.positions.get(parsedData[pos++]);
			int age = Integer.valueOf(parsedData[pos++]);
			int talent = Integer.valueOf(parsedData[pos++]);
			int salary = Integer.valueOf(parsedData[pos++]);
			int strength = Integer.valueOf(parsedData[pos++]);
			int health = Integer.valueOf(parsedData[pos++]);
			String abilities = parsedData[pos++];
			int price = Integer.valueOf(parsedData[pos++]);
			int bids = 0;
			return new TransferPlayer(id, name, nationalityCode, previousTeam, position, age, talent, salary, strength, health, abilities, price, bids, transferID++);
		} catch (Exception e) {
			throw new ReaderException("Incorrect file format");
		}
	}
}
