package com.fa13.build.controller.io;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.fa13.build.model.Transfer;
import com.fa13.build.model.TransferBid;

public class TransferWriter {
	public static void writeTransferFile(String filename, Transfer transfer) throws IOException{
		HashingOutputStream stream = new HashingOutputStream(new FileOutputStream(filename));
		writeTransfer(stream, transfer);
		stream.close(true);
		return;
	}
	
	public static void writeTransfer(OutputStream stream, Transfer transfer) throws IOException{
		String s = "Заявка составлена с помощью Build-1401";
		stream.write(s.getBytes(HashingOutputStream.WIN_CHARSET));
		stream.write(HashingOutputStream.WIN_LINE_BREAK);
		Date date = new Date();
		stream.write("LocalTime = ".getBytes(HashingOutputStream.WIN_CHARSET));
		SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy H:m:s");
		stream.write(sdf.format(date).getBytes(HashingOutputStream.WIN_CHARSET));
		stream.write(HashingOutputStream.WIN_LINE_BREAK);
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		stream.write("UTCTime = ".getBytes(HashingOutputStream.WIN_CHARSET));
		stream.write(sdf.format(date).getBytes(HashingOutputStream.WIN_CHARSET));
		stream.write(HashingOutputStream.WIN_LINE_BREAK);
		stream.write(HashingOutputStream.WIN_LINE_BREAK);
		stream.write(transfer.getPassword() == null ? new byte[0] : transfer.getPassword().getBytes(HashingOutputStream.WIN_CHARSET));
		stream.write(HashingOutputStream.WIN_LINE_BREAK);
		for (TransferBid bid: transfer.getBids()) {
			writeTransferBid(stream, bid);
		}
		StringBuilder sb = new StringBuilder();
		sb.append(transfer.getMaxGK());
		sb.append('/');
		sb.append(transfer.getMaxLD());
		sb.append('/');
		sb.append(transfer.getMaxCD());
		sb.append('/');
		sb.append(transfer.getMaxRD());
		sb.append('/');
		sb.append(transfer.getMaxLM());
		sb.append('/');
		sb.append(transfer.getMaxCM());
		sb.append('/');
		sb.append(transfer.getMaxRM());
		sb.append('/');
		sb.append(transfer.getMaxLF());
		sb.append('/');
		sb.append(transfer.getMaxCF());
		sb.append('/');
		sb.append(transfer.getMaxRF());
		sb.append('/');
		sb.append(transfer.getMaxPlayers());
		sb.append('/');
		sb.append(transfer.getMinCash());
		sb.append('/');
		stream.write(sb.toString().getBytes(HashingOutputStream.WIN_CHARSET));
		stream.write(HashingOutputStream.WIN_LINE_BREAK);
		stream.write("-987654/".getBytes(HashingOutputStream.WIN_CHARSET));
		stream.write(HashingOutputStream.WIN_LINE_BREAK);
		stream.write("*****".getBytes(HashingOutputStream.WIN_CHARSET));
		stream.write(HashingOutputStream.WIN_LINE_BREAK);
		return;
	}
	
	public static void writeTransferBid(OutputStream stream, TransferBid bid) throws IOException{
		StringBuilder sb = new StringBuilder();
		sb.append(bid.getId());
		sb.append('/');
		sb.append(bid.getName());
		sb.append('/');
		sb.append(bid.getPreviousTeam());
		sb.append('/');
		sb.append(bid.getPrice());
		sb.append('/');
		sb.append(bid.getTradeIn());
		sb.append('/');		
		stream.write(sb.toString().getBytes(HashingOutputStream.WIN_CHARSET));
		stream.write(HashingOutputStream.WIN_LINE_BREAK);
	}
}
