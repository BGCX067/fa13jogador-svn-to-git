package com.fa13.build.controller.io;

import com.fa13.build.model.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public abstract class TrainingWriter {
	public static void writeTrainingFile(String filename, Training training) throws IOException{
		HashingOutputStream stream = new HashingOutputStream(new FileOutputStream(filename));		
		writeTraining(stream, training);
		stream.close(true);
		return;
	}
	
	public static void writeTraining(OutputStream stream, Training training) throws IOException{
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
		stream.write(training.getPassword() == null ? new byte[0] : training.getPassword().getBytes(HashingOutputStream.WIN_CHARSET));
		stream.write(HashingOutputStream.WIN_LINE_BREAK);
		stream.write(Integer.toString(training.isScouting() ? training.getMinTalent() : 0).getBytes(HashingOutputStream.WIN_CHARSET));
		stream.write(HashingOutputStream.WIN_LINE_BREAK);
		List<PlayerTraining> playerTrainings = training.getPlayers();
		for (Iterator<PlayerTraining> iterator = playerTrainings.iterator(); iterator.hasNext();) {
			PlayerTraining curr = iterator.next();
			writePlayerTraining(stream, curr);
		}
		stream.write("999/".getBytes(HashingOutputStream.WIN_CHARSET));
		stream.write(HashingOutputStream.WIN_LINE_BREAK);
		stream.write("*****".getBytes(HashingOutputStream.WIN_CHARSET));
		stream.write(HashingOutputStream.WIN_LINE_BREAK);
	}
	
	public static void writePlayerTraining(OutputStream stream, PlayerTraining training) throws IOException{
		StringBuilder sb = new StringBuilder();
		sb.append(training.getNumber());
		sb.append('/');
		if (training.getShootingPoints() != 0) {
			sb.append(training.getShootingPoints());
		}
		sb.append('/');
		
		if (training.getPassingPoints() != 0) {
			sb.append(training.getPassingPoints());
		}
		sb.append('/');

		if (training.getCrossPoints() != 0) {
			sb.append(training.getCrossPoints());
		}
		sb.append('/');

		if (training.getDribblingPoints() != 0) {
			sb.append(training.getDribblingPoints());
		}
		sb.append('/');

		if (training.getTacklingPoints() != 0) {
			sb.append(training.getTacklingPoints());
		}
		sb.append('/');

		if (training.getSpeedPoints() != 0) {
			sb.append(training.getSpeedPoints());
		}
		sb.append('/');

		if (training.getStaminaPoints() != 0) {
			sb.append(training.getStaminaPoints());
		}
		sb.append('/');

		if (training.getHeadingPoints() != 0) {
			sb.append(training.getHeadingPoints());
		}
		sb.append('/');

		if (training.getReflexesPoints() != 0) {
			sb.append(training.getReflexesPoints());
		}
		sb.append('/');

		if (training.getHandlingPoints() != 0) {
			sb.append(training.getHandlingPoints());
		}
		sb.append('/');

		if (training.getFitnessPoints() != 0) {
			sb.append(training.getFitnessPoints());
		}
		sb.append('/');

		if (training.getMoraleFinance() != 0) {
			sb.append(training.getMoraleFinance());
		}
		sb.append('/');

		if (training.getFitnessFinance() != 0) {
			sb.append(training.getFitnessFinance());
		}
		sb.append('/');
		stream.write(sb.toString().getBytes(HashingOutputStream.WIN_CHARSET));
		stream.write(HashingOutputStream.WIN_LINE_BREAK);
	}
}
