package com.fa13.build.view;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Composite;

import com.fa13.build.model.Manager;
import com.fa13.build.model.Team;

public class StatisticsManagerView implements UIItem {
	
	private List<Manager> managers;

	public StatisticsManagerView(Composite parent) {		
		updateAll();
		updateMessages();
	}

	public void redraw() {
		// TODO Auto-generated method stub

	}

	public void updateAll() {
		if (MainWindow.getAllInstance() == null) {
			return;
		}
		List<Team> allTeams = MainWindow.getAllInstance().getTeams();
		managers = new ArrayList<Manager>(4*allTeams.size()/5);
		for (Team team: allTeams) {
			Manager curr = Manager.extractManager(team);
			if (curr != null) {
				managers.add(curr);
			}
		}

	}

	public void updateMessages() {
		// TODO Auto-generated method stub

	}

	public void updatePassword(String password) {
		// TODO Auto-generated method stub

	}

}
