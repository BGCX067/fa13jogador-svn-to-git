package com.fa13.build.controller.io;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import com.fa13.build.model.GameForm;
import com.fa13.build.model.PlayerPosition;
import com.fa13.build.model.PlayerSubstitution;
import com.fa13.build.model.TeamTactics;
import com.fa13.build.model.BasicPlayer.PlayerAmplua;

public class GameWriter {
	public static void writeGameFormFile(String filename, GameForm game) throws IOException{
		HashingOutputStream stream = new HashingOutputStream(new FileOutputStream(filename));		
		writeGameForm(stream, game);
		stream.close(true);
		return;
	}
	
	public static void writeGameForm(OutputStream stream, GameForm game) throws IOException{
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
		stream.write(game.getTournamentID().getBytes(HashingOutputStream.WIN_CHARSET));
		stream.write(HashingOutputStream.WIN_LINE_BREAK);
		stream.write(game.getGameType().getBytes(HashingOutputStream.WIN_CHARSET));
		stream.write(HashingOutputStream.WIN_LINE_BREAK);
		stream.write(game.getTeamID().getBytes(HashingOutputStream.WIN_CHARSET));
		stream.write(HashingOutputStream.WIN_LINE_BREAK);
		stream.write(game.getPassword() == null ? new byte[0] : game.getPassword().getBytes(HashingOutputStream.WIN_CHARSET));
		stream.write(HashingOutputStream.WIN_LINE_BREAK);
		
		PlayerPosition[] firstTeam = game.getFirstTeam();
		int defNum = 0, midNum = 0, forNum = 0; 
		for (PlayerPosition player: firstTeam) {
			PlayerAmplua amp = player.getAmplua();
			switch (amp) {
				case LD:
				case CD:
				case RD:
					defNum++; break;
				case LM:
				case CM:
				case RM:
					midNum++; break;
				case LF:
				case CF:
				case RF:
					forNum++; break;
				default:
					break;
			}
		}
		stream.write(String.format("M-%d-%d-%d", defNum, midNum, forNum).getBytes(HashingOutputStream.WIN_CHARSET));
		stream.write(HashingOutputStream.WIN_LINE_BREAK);
		stream.write(game.getStartTactics().getTeamTactics().toString().getBytes(HashingOutputStream.WIN_CHARSET));
		stream.write(HashingOutputStream.WIN_LINE_BREAK);
		stream.write(game.getStartTactics().getTeamHardness().toString().getBytes(HashingOutputStream.WIN_CHARSET));
		stream.write(HashingOutputStream.WIN_LINE_BREAK);
		stream.write(game.getStartTactics().getTeamStyle().toString().getBytes(HashingOutputStream.WIN_CHARSET));
		stream.write(HashingOutputStream.WIN_LINE_BREAK);
		stream.write(game.getTeamCoachSettings().toString().getBytes(HashingOutputStream.WIN_CHARSET));
		stream.write(HashingOutputStream.WIN_LINE_BREAK);
		
		for (PlayerPosition player: firstTeam) {
			PlayerPosition validPosition = player.validatePersonalSettings();
			stream.write(validPosition.getAmplua().toString().getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(" ".getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(Integer.toString(validPosition.getNumber()).getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(String.format(Locale.US, " %.1f ", validPosition.getDefenceX() * 0.2 - 60).getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(String.format(Locale.US, "%.1f ", validPosition.getDefenceY() * 0.2 - 45).getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(String.format(Locale.US, "%.1f ", validPosition.getAttackX() * 0.2 - 60).getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(String.format(Locale.US, "%.1f ", validPosition.getAttackY() * 0.2 - 45).getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write("-60.0 0.0 ".getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(String.format(Locale.US, "%.1f ", validPosition.getFreekickX() * 0.2 - 60).getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(String.format(Locale.US, "%.1f ", validPosition.getFreekickY() * 0.2 - 45).getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(validPosition.isLongShot() ? "1 ".getBytes(HashingOutputStream.WIN_CHARSET) : "0 ".getBytes(HashingOutputStream.WIN_CHARSET));
			switch (validPosition.getActOnFreekick()) {
				case FK_YES: stream.write("1 ".getBytes(HashingOutputStream.WIN_CHARSET)); break;
				case FK_NO: stream.write("2 ".getBytes(HashingOutputStream.WIN_CHARSET)); break;
				default: stream.write("0 ".getBytes(HashingOutputStream.WIN_CHARSET)); break;
			}
			stream.write(validPosition.isFantasista() ? "1 ".getBytes(HashingOutputStream.WIN_CHARSET) : "0 ".getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(validPosition.isDispatcher() ? "1 ".getBytes(HashingOutputStream.WIN_CHARSET) : "0 ".getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(Integer.toString(validPosition.getPersonalDefence()).getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(validPosition.isPressing() ? " 1 ".getBytes(HashingOutputStream.WIN_CHARSET) : " 0 ".getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(validPosition.isKeepBall() ? "1 ".getBytes(HashingOutputStream.WIN_CHARSET) : "0 ".getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(validPosition.isDefenderAttack() ? "1 ".getBytes(HashingOutputStream.WIN_CHARSET) : "0 ".getBytes(HashingOutputStream.WIN_CHARSET));
			switch (validPosition.getPassingStyle()) {
				case PS_EXACT: stream.write("1 ".getBytes(HashingOutputStream.WIN_CHARSET)); break;
				case PS_FORWARD: stream.write("2 ".getBytes(HashingOutputStream.WIN_CHARSET)); break;
				default: stream.write("0 ".getBytes(HashingOutputStream.WIN_CHARSET)); break;
			}
			stream.write(validPosition.getHardness().ordinal());
			stream.write(" 3 3 3 3".getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(HashingOutputStream.WIN_LINE_BREAK);
		}
		stream.write("запасные:".getBytes(HashingOutputStream.WIN_CHARSET));
		stream.write(HashingOutputStream.WIN_LINE_BREAK);
		int[] bench = game.getBench();
		for (int i = 0; i < bench.length; i++) {
			stream.write(Integer.toString(bench[i]).getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(HashingOutputStream.WIN_LINE_BREAK);
		}
		stream.write("стандарты:".getBytes(HashingOutputStream.WIN_CHARSET));
		stream.write(HashingOutputStream.WIN_LINE_BREAK);
		int main = 0, assist = 0;
		for (int i = 0; i < firstTeam.length; i++) {
			if (firstTeam[i].isCaptain()) main = firstTeam[i].getNumber();
			if (firstTeam[i].isCaptainAssistant()) assist = firstTeam[i].getNumber();
		}
		stream.write(String.format("%d %d к", main, assist).getBytes(HashingOutputStream.WIN_CHARSET));
		stream.write(HashingOutputStream.WIN_LINE_BREAK);
		
		main = 0; assist = 0;
		for (int i = 0; i < firstTeam.length; i++) {
			if (firstTeam[i].isPenalty()) main = firstTeam[i].getNumber();
			if (firstTeam[i].isPenaltyAssistant()) assist = firstTeam[i].getNumber();
		}
		stream.write(String.format("%d %d п", main, assist).getBytes(HashingOutputStream.WIN_CHARSET));
		stream.write(HashingOutputStream.WIN_LINE_BREAK);
		
		main = 0; assist = 0;
		for (int i = 0; i < firstTeam.length; i++) {
			if (firstTeam[i].isDirectFreekick()) main = firstTeam[i].getNumber();
			if (firstTeam[i].isDirectFreekickAssistant()) assist = firstTeam[i].getNumber();
		}
		stream.write(String.format("%d %d ш", main, assist).getBytes(HashingOutputStream.WIN_CHARSET));
		stream.write(HashingOutputStream.WIN_LINE_BREAK);
		
		main = 0; assist = 0;
		for (int i = 0; i < firstTeam.length; i++) {
			if (firstTeam[i].isIndirectFreekick()) main = firstTeam[i].getNumber();
			if (firstTeam[i].isIndirectFreekickAssistant()) assist = firstTeam[i].getNumber();
		}
		stream.write(String.format("%d %d с", main, assist).getBytes(HashingOutputStream.WIN_CHARSET));
		stream.write(HashingOutputStream.WIN_LINE_BREAK);
		
		main = 0; assist = 0;
		for (int i = 0; i < firstTeam.length; i++) {
			if (firstTeam[i].isLeftCorner()) main = firstTeam[i].getNumber();
			if (firstTeam[i].isLeftCornerAssistant()) assist = firstTeam[i].getNumber();
		}
		stream.write(String.format("%d %d ул", main, assist).getBytes(HashingOutputStream.WIN_CHARSET));
		stream.write(HashingOutputStream.WIN_LINE_BREAK);
		
		main = 0; assist = 0;
		for (int i = 0; i < firstTeam.length; i++) {
			if (firstTeam[i].isRightCorner()) main = firstTeam[i].getNumber();
			if (firstTeam[i].isRightCornerAssistant()) assist = firstTeam[i].getNumber();
		}
		stream.write(String.format("%d %d уп", main, assist).getBytes(HashingOutputStream.WIN_CHARSET));
		stream.write(HashingOutputStream.WIN_LINE_BREAK);
		
		stream.write("пенальти:".getBytes(HashingOutputStream.WIN_CHARSET));
		stream.write(HashingOutputStream.WIN_LINE_BREAK);
		
		int penalties[] = new int[11];
		
		for (int i = 0; i < firstTeam.length; i++) {
			int curr = firstTeam[i].getPenaltyOrder();
			if (curr != 0) {
				penalties[curr-1] = firstTeam[i].getNumber();
			}
		}
		
		for (int i = 0; i < penalties.length; i++) {
			stream.write(String.format("%d ", penalties[i]).getBytes(HashingOutputStream.WIN_CHARSET));
		}
		stream.write(HashingOutputStream.WIN_LINE_BREAK);
		
		stream.write("цена билета:".getBytes(HashingOutputStream.WIN_CHARSET));
		stream.write(HashingOutputStream.WIN_LINE_BREAK);
		stream.write(Integer.toString(game.getPrice()).getBytes(HashingOutputStream.WIN_CHARSET));
		stream.write(HashingOutputStream.WIN_LINE_BREAK);
		
		PlayerSubstitution[] substitutions = game.getSubstitutions();
		
		stream.write("замены:".getBytes(HashingOutputStream.WIN_CHARSET));
		stream.write(HashingOutputStream.WIN_LINE_BREAK);
		for (int i = 0; i < substitutions.length; i++) {
			if (substitutions[i].getNumber() == 0) {
				stream.write("0 0 -20 20 0 0 -1 НН".getBytes(HashingOutputStream.WIN_CHARSET));
				stream.write(HashingOutputStream.WIN_LINE_BREAK);
				continue;
			}
			stream.write(String.format("0 %d %d %d %d %d ", substitutions[i].getTime(), 
					substitutions[i].getMinDifference(), substitutions[i].getMaxDifference(),
					substitutions[i].getSubstitutedPlayer(), substitutions[i].getNumber()).getBytes(HashingOutputStream.WIN_CHARSET));
			int tmp = substitutions[i].getSpecialRole();
			stream.write(Integer.toString(tmp == 0 ? -1 : tmp).getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(" ".getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(String.format(Locale.US, "%.1f ", substitutions[i].getDefenceX() * 0.2 - 60).getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(String.format(Locale.US, "%.1f ", substitutions[i].getDefenceY() * 0.2 - 45).getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(String.format(Locale.US, "%.1f ", substitutions[i].getAttackX() * 0.2 - 60).getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(String.format(Locale.US, "%.1f ", substitutions[i].getAttackY() * 0.2 - 45).getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(String.format(Locale.US, "%.1f ", substitutions[i].getFreekickX() * 0.2 - 60).getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(String.format(Locale.US, "%.1f ", substitutions[i].getFreekickY() * 0.2 - 45).getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(substitutions[i].isLongShot() ? "1 ".getBytes(HashingOutputStream.WIN_CHARSET) : "0 ".getBytes(HashingOutputStream.WIN_CHARSET));
			switch (substitutions[i].getActOnFreekick()) {
				case FK_YES: stream.write("1 ".getBytes(HashingOutputStream.WIN_CHARSET)); break;
				case FK_NO: stream.write("2 ".getBytes(HashingOutputStream.WIN_CHARSET)); break;
				default: stream.write("0 ".getBytes(HashingOutputStream.WIN_CHARSET)); break;
			}
			stream.write(substitutions[i].isFantasista() ? "1 ".getBytes(HashingOutputStream.WIN_CHARSET) : "0 ".getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(substitutions[i].isDispatcher() ? "1 ".getBytes(HashingOutputStream.WIN_CHARSET) : "0 ".getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(Integer.toString(substitutions[i].getPersonalDefence()).getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(substitutions[i].isPressing() ? " 1 ".getBytes(HashingOutputStream.WIN_CHARSET) : " 0 ".getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(substitutions[i].isKeepBall() ? "1 ".getBytes(HashingOutputStream.WIN_CHARSET) : "0 ".getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(substitutions[i].isDefenderAttack() ? "1 ".getBytes(HashingOutputStream.WIN_CHARSET) : "0 ".getBytes(HashingOutputStream.WIN_CHARSET));
			switch (substitutions[i].getPassingStyle()) {
				case PS_EXACT: stream.write("1 ".getBytes(HashingOutputStream.WIN_CHARSET)); break;
				case PS_FORWARD: stream.write("2 ".getBytes(HashingOutputStream.WIN_CHARSET)); break;
				default: stream.write("0 ".getBytes(HashingOutputStream.WIN_CHARSET)); break;
			}
			stream.write(substitutions[i].getHardness().ordinal());
			stream.write(HashingOutputStream.WIN_LINE_BREAK);
		}
		stream.write(game.getSubstitutionPreferences().ordinal());
		stream.write(HashingOutputStream.WIN_LINE_BREAK);
		
		stream.write("глобал:".getBytes(HashingOutputStream.WIN_CHARSET));
		stream.write(HashingOutputStream.WIN_LINE_BREAK);
		
		stream.write(String.format("%d %d %d", game.getDefenceTime(), game.getDefenceMin(), game.getDefenceMax()).getBytes(HashingOutputStream.WIN_CHARSET));
		stream.write(HashingOutputStream.WIN_LINE_BREAK);
		stream.write(String.format("%d %d %d", game.getAttackTime(), game.getAttackMin(), game.getAttackMax()).getBytes(HashingOutputStream.WIN_CHARSET));
		stream.write(HashingOutputStream.WIN_LINE_BREAK);
		
		TeamTactics[] halftime = game.getHalftimeChanges();
		for (int i = 0; i < halftime.length; i++) {
			if (halftime[i] == null) {
				stream.write("1 0 тактика агрессивность стиль".getBytes(HashingOutputStream.WIN_CHARSET));
				stream.write(HashingOutputStream.WIN_LINE_BREAK);
				continue;
			}
			stream.write(Integer.toString(halftime[i].getMinimumDifference()).getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(" ".getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(Integer.toString(halftime[i].getMaximumDifference()).getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(" ".getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(halftime[i].getTeamTactics().toString().getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(" ".getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(halftime[i].getTeamHardness().toString().getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(" ".getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(halftime[i].getTeamStyle().toString().getBytes(HashingOutputStream.WIN_CHARSET));
			stream.write(HashingOutputStream.WIN_LINE_BREAK);
		}
		stream.write("*****".getBytes(HashingOutputStream.WIN_CHARSET));
		stream.write(HashingOutputStream.WIN_LINE_BREAK);
	}
}
