package com.fa13.build.controller.io;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.fa13.build.model.Club;
import com.fa13.build.model.Club.TriAction;

public class ClubWriter {
	public static void writeClubFile(String filename, Club club) throws IOException{
		HashingOutputStream stream = new HashingOutputStream(new FileOutputStream(filename));		
		writeClub(stream, club);
		stream.close(true);
		return;
	}
	
	public static void writeClub(OutputStream stream, Club club) throws IOException{		
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
		stream.write("parole=".getBytes(HashingOutputStream.WIN_CHARSET));
		stream.write(club.getPassword() == null ? new byte[0] : club.getPassword().getBytes(HashingOutputStream.WIN_CHARSET));
		stream.write(HashingOutputStream.WIN_LINE_BREAK);
		s = club.getEmail();
		if (s != null) {
			stream.write("email=".getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(s.getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(HashingOutputStream.WIN_LINE_BREAK);
		}
		
		int icq = club.getIcq();
		if (icq != -1) {
			stream.write("icq=".getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(Integer.toString(icq).getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(HashingOutputStream.WIN_LINE_BREAK);
		}
		
		TriAction act = club.getStadiumAction();
		if (act != TriAction.TR_NOTHING) {
			stream.write("stadion=".getBytes(HashingOutputStream.WIN_CHARSET));
			if (act == TriAction.TR_REPAIR) {
				stream.write("remont".getBytes(HashingOutputStream.WIN_CHARSET));
			} else {
				stream.write(Integer.toString(club.getStadium()).getBytes(HashingOutputStream.WIN_CHARSET));
			}
			stream.write(HashingOutputStream.WIN_LINE_BREAK);
		}
		
		act = club.getSportBaseAction();
		if (act != TriAction.TR_NOTHING) {
			stream.write("basa=".getBytes(HashingOutputStream.WIN_CHARSET));
			if (act == TriAction.TR_REPAIR) {
				stream.write("remont".getBytes(HashingOutputStream.WIN_CHARSET));
			} else {
				stream.write(Integer.toString(club.getSportBase()).getBytes(HashingOutputStream.WIN_CHARSET));
			}
			stream.write(HashingOutputStream.WIN_LINE_BREAK);
		}
		
		act = club.getSportBaseAction();
		if (act != TriAction.TR_NOTHING) {
			stream.write("schoole=".getBytes(HashingOutputStream.WIN_CHARSET));
			if (act == TriAction.TR_REPAIR) {
				stream.write("remont".getBytes(HashingOutputStream.WIN_CHARSET));
			} else {
				stream.write(club.isSchool() ? "yes".getBytes(HashingOutputStream.WIN_CHARSET) : "no".getBytes(HashingOutputStream.WIN_CHARSET));
			}
			stream.write(HashingOutputStream.WIN_LINE_BREAK);
		}
		
		int coachCoef = club.getCoachCoef();
		if (coachCoef != 0) {
			stream.write("KGT=".getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(Integer.toString(coachCoef).getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(HashingOutputStream.WIN_LINE_BREAK);
		}
		
		int coachGK = club.getCoachGK();
		if (coachGK != -1) {
			stream.write("gk=".getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(Integer.toString(coachGK).getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(HashingOutputStream.WIN_LINE_BREAK);
		}
		
		int coachDef = club.getCoachDef();
		if (coachDef != -1) {
			stream.write("def=".getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(Integer.toString(coachDef).getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(HashingOutputStream.WIN_LINE_BREAK);
		}
		
		int coachMid = club.getCoachMid();
		if (coachMid != -1) {
			stream.write("mid=".getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(Integer.toString(coachMid).getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(HashingOutputStream.WIN_LINE_BREAK);
		}
		
		int coachFor = club.getCoachFor();
		if (coachFor != -1) {
			stream.write("for=".getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(Integer.toString(coachFor).getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(HashingOutputStream.WIN_LINE_BREAK);
		}
		
		int coachFitness = club.getCoachFitness();
		if (coachFitness != -1) {
			stream.write("phys=".getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(Integer.toString(coachFitness).getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(HashingOutputStream.WIN_LINE_BREAK);
		}
		
		int coachMorale = club.getCoachMorale();
		if (coachMorale != -1) {
			stream.write("psy=".getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(Integer.toString(coachMorale).getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(HashingOutputStream.WIN_LINE_BREAK);
		}
		
		int doctor = club.getDoctorCount();
		if (doctor != -1) {
			stream.write("doc=".getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(Integer.toString(doctor).getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(",".getBytes(HashingOutputStream.WIN_CHARSET));
			doctor = club.getDoctorLevel();
			stream.write(Integer.toString(doctor).getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(HashingOutputStream.WIN_LINE_BREAK);
		}
		
		int scoutLevel = club.getScoutLevel();
		if (scoutLevel != -1) {
			stream.write("scout=".getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(Integer.toString(scoutLevel).getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(HashingOutputStream.WIN_LINE_BREAK);
		}
		
		int color1 = club.getColor1();
		if (color1 != -1) {
			stream.write("color1=".getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(Integer.toString(color1).getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(HashingOutputStream.WIN_LINE_BREAK);
		}
		
		int color2 = club.getColor2();
		if (color2 != -1) {
			stream.write("color2=".getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(Integer.toString(color2).getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(HashingOutputStream.WIN_LINE_BREAK);
		}
		
		int color3 = club.getColor3();
		if (color3 != -1) {
			stream.write("color3=".getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(Integer.toString(color3).getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(HashingOutputStream.WIN_LINE_BREAK);
		}
		
		int color4 = club.getColor4();
		if (color4 != -1) {
			stream.write("color4=".getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(Integer.toString(color4).getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(HashingOutputStream.WIN_LINE_BREAK);
		}
		stream.write("*****".getBytes(HashingOutputStream.WIN_CHARSET));
		stream.write(HashingOutputStream.WIN_LINE_BREAK);
		return;
	}
}
