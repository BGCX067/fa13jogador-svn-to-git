package com.fa13.build.controller.io;

import java.io.*;

import com.fa13.build.model.Transfer;
import com.fa13.build.model.TransferBid;

public class TransferReader {

	public static Transfer readTransferFile(String filename) throws ReaderException {
		try {
			File transferFile = new File(filename);
			InputStream transferStream = new FileInputStream(transferFile);
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new InputStreamReader(transferStream, "Cp1251"));
			} catch (UnsupportedEncodingException e) {
				System.err.println("Error while encoding transfer list file");
			}
			Transfer trans = readTransfer(reader);
			reader.close();
			return trans;
		} catch (ReaderException e) {
			throw e;
		} catch (Exception e) {
			throw new ReaderException("Unable to open transfer bids file: " + filename);
		}
	}

	public static Transfer readTransfer(BufferedReader reader) throws ReaderException {
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

			TransferBid[] bids = new TransferBid[20];
			int pos = 0;
			TransferBid curr;
			do {
				s = reader.readLine();
				curr = readTransferBid(s);
				if (curr == null)
					break;
				bids[pos++] = curr;
			} while (curr != null);

			String[] parsedData = s.split("/");
			int cnt = 0;
			int maxGK = Integer.valueOf(parsedData[cnt++]);
			int maxLD = Integer.valueOf(parsedData[cnt++]);
			int maxCD = Integer.valueOf(parsedData[cnt++]);
			int maxRD = Integer.valueOf(parsedData[cnt++]);
			int maxLM = Integer.valueOf(parsedData[cnt++]);
			int maxCM = Integer.valueOf(parsedData[cnt++]);
			int maxRM = Integer.valueOf(parsedData[cnt++]);
			int maxLF = Integer.valueOf(parsedData[cnt++]);
			int maxCF = Integer.valueOf(parsedData[cnt++]);
			int maxRF = Integer.valueOf(parsedData[cnt++]);
			int maxPlayers = Integer.valueOf(parsedData[cnt++]);
			int minCash = Integer.valueOf(parsedData[cnt++]);

			return new Transfer(password, maxGK, maxLD, maxCD, maxRD, maxLM, maxCM, maxRM, maxLF, maxCF, maxRF, maxPlayers, minCash, bids);
		} catch (Exception e) {
			throw new ReaderException("Incorrect file format");
		}
	}

	public static TransferBid readTransferBid(String line) {
		String[] parsedData = line.split("/");
		if (parsedData.length != 5)
			return null;
		int cnt = 0;
		int id = Integer.valueOf(parsedData[cnt++]);
		String name = parsedData[cnt++];
		String previousTeam = parsedData[cnt++];
		int price = Integer.valueOf(parsedData[cnt++]);
		int tradeIn = Integer.valueOf(parsedData[cnt++]);

		return new TransferBid(id, name, previousTeam, price, tradeIn);
	}
}
