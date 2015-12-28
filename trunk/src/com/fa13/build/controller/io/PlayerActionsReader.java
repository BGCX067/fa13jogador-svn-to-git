package com.fa13.build.controller.io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.fa13.build.model.PlayerActions;
import com.fa13.build.model.PlayerBid;
import com.fa13.build.model.RentBid;
import com.fa13.build.model.SellBid;
import com.fa13.build.model.RentBid.RentType;
import com.fa13.build.model.SellBid.SellType;

public class PlayerActionsReader {
	public static PlayerActions readPlayerActionsFile(String filename) throws ReaderException {
		try {
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "Cp1251"));
			} catch (UnsupportedEncodingException e) {
				System.err.println("Error while encoding training file");
			}
			PlayerActions actions = readPlayerActions(reader);
			reader.close();
			return actions;
		} catch (ReaderException e) {
			throw e;
		} catch (Exception e) {
			throw new ReaderException("Unable to open player file: " + filename);
		}
	}

	public static PlayerActions readPlayerActions(BufferedReader reader) throws ReaderException {
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
			String[] parsedData;
			List<PlayerBid> bids = new ArrayList<PlayerBid>();
			while (!(s = reader.readLine()).equals("*****")) {
				char c = s.charAt(0);
				parsedData = s.split(" ");
				switch (c) {
				case 'Т': {
					int number = Integer.valueOf(parsedData[1]);
					int value = Integer.valueOf(parsedData[2]);
					bids.add(new SellBid(SellType.SL_VALUE, number, value));
					break;
				}
				case 'П': {
					int number = Integer.valueOf(parsedData[1]);
					int value = Integer.valueOf(parsedData[2]);
					bids.add(new SellBid(SellType.SL_PERCENT, number, value));
					break;
				}
				case 'А': {
					int number = Integer.valueOf(parsedData[1]);
					StringBuilder sb = new StringBuilder();
					for (int i = 4; i < parsedData.length; i++) {
						sb.append(parsedData[i]);
						sb.append(' ');
					}
					String team = sb.toString().trim();
					bids.add(new RentBid(RentType.RT_OFFER, team, number));
					break;
				}
				case 'В': {
					int number = Integer.valueOf(parsedData[1]);
					StringBuilder sb = new StringBuilder();
					for (int i = 4; i < parsedData.length; i++) {
						sb.append(parsedData[i]);
						sb.append(' ');
					}
					String team = sb.toString().trim();
					bids.add(new RentBid(RentType.RT_TAKE, team, number));
					break;
				}
				default:
					break;
				}
			}
			return new PlayerActions(bids, password);
		} catch (Exception e) {
			throw new ReaderException("Incorrect file format");
		}
	}
}
