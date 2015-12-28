package com.fa13.build.controller.io;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.fa13.build.model.PlayerActions;
import com.fa13.build.model.PlayerBid;

public class PlayerActionsWriter {
	public static void writePlayerActionsFile(String filename, PlayerActions actions) throws IOException {
		HashingOutputStream stream = new HashingOutputStream(new FileOutputStream(filename));
		writePlayerActions(stream, actions);
		stream.close(true);
		return;
	}
	
	public static void writePlayerActions(OutputStream stream, PlayerActions actions) throws IOException {
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
		stream.write(actions.getPassword() == null ? new byte[0] : actions.getPassword().getBytes(HashingOutputStream.WIN_CHARSET));
		stream.write(HashingOutputStream.WIN_LINE_BREAK);
		
		for (PlayerBid bid: actions.getBids()) {
			stream.write(bid.toString().getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(HashingOutputStream.WIN_LINE_BREAK);
		}
		stream.write("*****".getBytes(HashingOutputStream.WIN_CHARSET));
		stream.write(HashingOutputStream.WIN_LINE_BREAK);
	}
}
