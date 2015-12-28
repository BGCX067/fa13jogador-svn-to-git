package com.fa13.build.view;

import java.util.*;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.*;

import com.fa13.build.model.Team;
import com.fa13.build.model.TeamStats;

public class StatisticsTeamView implements UIItem {

	private List<TeamStats> teamStats;

	private final Composite tablePanel;
	private final Composite buttonsPanel;
	private final Table teamsTable;
	private List<Comparator<TeamStats>> comps;
	private final Button[] buttons;
	private List<Set<Integer>> visibleColumns;
	private int currentTab = 0;
	private final Color gray;
	private Map<String, String> nationalities;
	
	public static final int TABLE_COLUMNS_COUNT = 23;
	public static final int TABLE_BUTTONS_COUNT = 10;

	public StatisticsTeamView(Composite parent) {
		gray = new Color(parent.getDisplay(), 216, 216, 216);
		nationalities = new HashMap<String, String>(216);

		tablePanel = new Composite(parent, SWT.NONE);
		buttonsPanel = new Composite(parent, SWT.NONE);

		FormData data;

		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(buttonsPanel, 0);
		data.top = new FormAttachment(0, 0);
		data.bottom = new FormAttachment(100, 0);
		tablePanel.setLayoutData(data);
		tablePanel.setLayout(new FillLayout());

		data = new FormData();
		data.top = new FormAttachment(0, 0);
		data.bottom = new FormAttachment(100, 0);
		data.right = new FormAttachment(100, 0);
		buttonsPanel.setLayoutData(data);

		RowLayout buttonsLayout = new RowLayout(SWT.VERTICAL);
		buttonsLayout.fill = true;
		buttonsLayout.wrap = true;

		buttonsPanel.setLayout(buttonsLayout);

		teamsTable = new Table(tablePanel, SWT.BORDER);
		teamsTable.setLayout(new FillLayout());
		teamsTable.setHeaderVisible(true);
		teamsTable.setLinesVisible(true);

		comps = new ArrayList<Comparator<TeamStats>>(TABLE_COLUMNS_COUNT);

		comps.add(new Comparator<TeamStats>() {
			public int compare(TeamStats o1, TeamStats o2) {
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
		});

		comps.add(new Comparator<TeamStats>() {
			public int compare(TeamStats o1, TeamStats o2) {
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
		});
		
		comps.add(new Comparator<TeamStats>() {
			public int compare(TeamStats o1, TeamStats o2) {
				String n1 = nationalities.get(o1.getCountryCode());
				if (n1 == null) {
					n1 = MainWindow.getMessage("country." + o1.getCountryCode());
					nationalities.put(o1.getCountryCode(), n1);
				}
				String n2 = nationalities.get(o2.getCountryCode());
				if (n2 == null) {
					n2 = MainWindow.getMessage("country." + o2.getCountryCode());
					nationalities.put(o2.getCountryCode(), n2);
				}
				int country = n1.compareToIgnoreCase(n2);
				if (country != 0) {
					return country;
				}
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
		});

		comps.add(new Comparator<TeamStats>() {
			public int compare(TeamStats o1, TeamStats o2) {
				int rating = o2.getRating() - o1.getRating();
				if (rating != 0) {
					return rating;
				}
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
		});

		comps.add(new Comparator<TeamStats>() {
			public int compare(TeamStats o1, TeamStats o2) {
				int stadium = o2.getStadium() - o1.getStadium();
				if (stadium != 0) {
					return stadium;
				}
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
		});
		
		comps.add(new Comparator<TeamStats>() {
			public int compare(TeamStats o1, TeamStats o2) {
				int finance = o2.getFinance() - o1.getFinance();
				if (finance != 0) {
					return finance;
				}
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
		});

		comps.add(new Comparator<TeamStats>() {
			public int compare(TeamStats o1, TeamStats o2) {
				int playersPrice = o2.getPlayersPrice() - o1.getPlayersPrice();
				if (playersPrice != 0) {
					return playersPrice;
				}
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
		});

		comps.add(new Comparator<TeamStats>() {
			public int compare(TeamStats o1, TeamStats o2) {
				int infrastructurePrice = o2.getInfrastructurePrice()
						- o1.getInfrastructurePrice();
				if (infrastructurePrice != 0) {
					return infrastructurePrice;
				}
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
		});

		comps.add(new Comparator<TeamStats>() {
			public int compare(TeamStats o1, TeamStats o2) {
				int teamPrice = o2.getTeamPrice() - o1.getTeamPrice();
				if (teamPrice != 0) {
					return teamPrice;
				}
				teamPrice = o2.getPlayersPrice() - o1.getPlayersPrice();
				if (teamPrice != 0) {
					return teamPrice;
				}
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
		});
		
		comps.add(new Comparator<TeamStats>() {
			public int compare(TeamStats o1, TeamStats o2) {
				int teamPrice = o2.getTeamPrice() + o2.getFinance() - o1.getTeamPrice() - o1.getFinance();
				if (teamPrice != 0) {
					return teamPrice;
				}
				teamPrice = o2.getTeamPrice() - o1.getTeamPrice();
				if (teamPrice != 0) {
					return teamPrice;
				}
				teamPrice = o2.getPlayersPrice() - o1.getPlayersPrice();
				if (teamPrice != 0) {
					return teamPrice;
				}
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
		});

		comps.add(new Comparator<TeamStats>() {
			public int compare(TeamStats o1, TeamStats o2) {
				int playersSalary = o2.getPlayersSalary()
						- o1.getPlayersSalary();
				if (playersSalary != 0) {
					return playersSalary;
				}
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
		});

		comps.add(new Comparator<TeamStats>() {
			public int compare(TeamStats o1, TeamStats o2) {
				int trainersSalary = o2.getTrainersSalary()
						- o1.getTrainersSalary();
				if (trainersSalary != 0) {
					return trainersSalary;
				}
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
		});

		comps.add(new Comparator<TeamStats>() {
			public int compare(TeamStats o1, TeamStats o2) {
				int teamSalary = o2.getTeamSalary() - o1.getTeamSalary();
				if (teamSalary != 0) {
					return teamSalary;
				}
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
		});

		comps.add(new Comparator<TeamStats>() {
			public int compare(TeamStats o1, TeamStats o2) {
				double averageStrength = o2.getAverageStrength()
						- o1.getAverageStrength();
				if (Math.abs(averageStrength) > 1e-6) {
					return averageStrength < 0 ? -1 : 1;
				}
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
		});

		comps.add(new Comparator<TeamStats>() {
			public int compare(TeamStats o1, TeamStats o2) {
				double average11Strength = o2.getAverage11Strength()
						- o1.getAverage11Strength();
				if (Math.abs(average11Strength) > 1e-6) {
					return average11Strength < 0 ? -1 : 1;
				}
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
		});

		comps.add(new Comparator<TeamStats>() {
			public int compare(TeamStats o1, TeamStats o2) {
				double average15Strength = o2.getAverage15Strength()
						- o1.getAverage15Strength();
				if (Math.abs(average15Strength) > 1e-6) {
					return average15Strength < 0 ? -1 : 1;
				}
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
		});

		comps.add(new Comparator<TeamStats>() {
			public int compare(TeamStats o1, TeamStats o2) {
				double averageTalent = o2.getAverageTalent()
						- o1.getAverageTalent();
				if (Math.abs(averageTalent) > 1e-6) {
					return averageTalent < 0 ? -1 : 1;
				}
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
		});

		comps.add(new Comparator<TeamStats>() {
			public int compare(TeamStats o1, TeamStats o2) {
				double averageAge = o1.getAverageAge() - o2.getAverageAge();
				if (Math.abs(averageAge) > 1e-6) {
					return averageAge < 0 ? -1 : 1;
				}
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
		});

		comps.add(new Comparator<TeamStats>() {
			public int compare(TeamStats o1, TeamStats o2) {
				int summarySP = o2.getTeamSpecialPoints() - o1.getTeamSpecialPoints();
				if (summarySP != 0) {
					return summarySP;
				}
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
		});

		comps.add(new Comparator<TeamStats>() {
			public int compare(TeamStats o1, TeamStats o2) {
				double averageSP = o2.getAverageSpecialPoints()
						- o1.getAverageSpecialPoints();
				if (Math.abs(averageSP) > 1e-6) {
					return averageSP < 0 ? -1 : 1;
				}
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
		});
		
		comps.add(new Comparator<TeamStats>() {
			public int compare(TeamStats o1, TeamStats o2) {
				int cards = o2.getYellowCards() - o1.getYellowCards();
				if (cards != 0) {
					return cards;
				}
				cards = o2.getRedCards() - o1.getRedCards();
				if (cards != 0) {
					return cards;
				}
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
		});
		
		comps.add(new Comparator<TeamStats>() {
			public int compare(TeamStats o1, TeamStats o2) {
				int cards = o2.getRedCards() - o1.getRedCards();
				if (cards != 0) {
					return cards;
				}
				cards = o2.getYellowCards() - o1.getYellowCards();
				if (cards != 0) {
					return cards;
				}
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
		});

		comps.add(new Comparator<TeamStats>() {
			public int compare(TeamStats o1, TeamStats o2) {
				int cards = o2.getCards() - o1.getCards();
				if (cards != 0) {
					return cards;
				}
				cards = o2.getRedCards() - o1.getRedCards();
				if (cards != 0) {
					return cards;
				}
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
		});

		Listener sortListener = new Listener() {
			public void handleEvent(Event e) {
				teamsTable.setRedraw(false);
				TableColumn sortColumn = teamsTable.getSortColumn();
				TableColumn currentColumn = (TableColumn) e.widget;
				int dir = teamsTable.getSortDirection();
				if (sortColumn == currentColumn) {
					dir = dir == SWT.UP ? SWT.DOWN : SWT.UP;
				} else {
					teamsTable.setSortColumn(currentColumn);
					dir = SWT.UP;
				}
				int index = teamsTable.indexOf(currentColumn);
				Collections.sort(teamStats, comps.get(index));
				if (dir == SWT.DOWN) {
					Collections.reverse(teamStats);
				}
				teamsTable.setSortDirection(dir);
				updateTables();
				teamsTable.setRedraw(true);
			}
		};

		for (int i = 0; i < TABLE_COLUMNS_COUNT; i++) {
			TableColumn column = new TableColumn(teamsTable, SWT.NONE);
			column.setResizable(false);
			column.setAlignment(SWT.RIGHT);
			column.addListener(SWT.Selection, sortListener);
		}
		
		teamsTable.getColumn(1).setAlignment(SWT.LEFT);
		teamsTable.getColumn(2).setAlignment(SWT.LEFT);

		visibleColumns = new ArrayList<Set<Integer>>(TABLE_BUTTONS_COUNT);
		Set<Integer> curr;
		curr = new HashSet<Integer>(4);
		Collections.addAll(curr, 0, 1, 2, 3);
		visibleColumns.add(curr);
		curr = new HashSet<Integer>(4);
		Collections.addAll(curr, 0, 1, 2, 4);
		visibleColumns.add(curr);
		curr = new HashSet<Integer>(7);
		Collections.addAll(curr, 0, 1, 2, 5, 6, 7, 8, 9);
		visibleColumns.add(curr);
		curr = new HashSet<Integer>(6);
		Collections.addAll(curr, 0, 1, 2, 10, 11, 12);
		visibleColumns.add(curr);
		curr = new HashSet<Integer>(6);
		Collections.addAll(curr, 0, 1, 2, 13, 14, 15);
		visibleColumns.add(curr);
		curr = new HashSet<Integer>(4);
		Collections.addAll(curr, 0, 1, 2, 16);
		visibleColumns.add(curr);
		curr = new HashSet<Integer>(4);
		Collections.addAll(curr, 0, 1, 2, 17);
		visibleColumns.add(curr);
		curr = new HashSet<Integer>(5);
		Collections.addAll(curr, 0, 1, 2, 18, 19);
		visibleColumns.add(curr);
		curr = new HashSet<Integer>(6);
		Collections.addAll(curr, 0, 1, 2, 20, 21, 22);
		visibleColumns.add(curr);

		curr = new HashSet<Integer>(TABLE_COLUMNS_COUNT);
		Collections.addAll(curr, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13,
				14, 15, 16, 17, 18, 19, 20, 21, 22);
		visibleColumns.add(curr);

		buttons = new Button[TABLE_BUTTONS_COUNT];
		for (int i = 0; i < TABLE_BUTTONS_COUNT; i++) {
			buttons[i] = new Button(buttonsPanel, SWT.TOGGLE);
			buttons[i].addSelectionListener(new StatsButtonListener(i));
		}

		buttons[0].setSelection(true);

		updateAll();

		teamsTable.setSortColumn(teamsTable.getColumn(1));
		teamsTable.setSortDirection(SWT.UP);

		updateMessages();
	}
	
	public void updateTables() {
		if (MainWindow.getAllInstance() == null) return;
		int ind = 0;
		for (TeamStats stats : teamStats) {
			TableItem item = teamsTable.getItem(ind++);
			int cnt = 0;
			if (ind % 2 == 0) {
				item.setBackground(gray);
			}
			item.setText(cnt++, Integer.toString(ind));
			item.setText(cnt++, stats.getName());
			String nation = nationalities.get(stats.getCountryCode());
			if (nation == null) {
				nation = MainWindow.getMessage("country." + stats.getCountryCode());
				nationalities.put(stats.getCountryCode(), nation);
			}
			item.setText(cnt++, nation);
			item.setText(cnt++, Integer.toString(stats.getRating()));
			item.setText(cnt++, Integer.toString(stats.getStadium()));
			item.setText(cnt++, Integer.toString(stats.getFinance()));
			item.setText(cnt++, Integer.toString(stats.getPlayersPrice()));
			item.setText(cnt++, Integer.toString(stats.getInfrastructurePrice()));
			item.setText(cnt++, Integer.toString(stats.getTeamPrice()));
			item.setText(cnt++, Integer.toString(stats.getTeamPrice() + stats.getFinance()));
			item.setText(cnt++, Integer.toString(stats.getPlayersSalary()));
			item.setText(cnt++, Integer.toString(stats.getTrainersSalary()));
			item.setText(cnt++, Integer.toString(stats.getTeamSalary()));
			item.setText(cnt++, String.format("%.2f", stats.getAverageStrength()));
			item.setText(cnt++, String.format("%.2f", stats.getAverage11Strength()));
			item.setText(cnt++, String.format("%.2f", stats.getAverage15Strength()));
			item.setText(cnt++, String.format("%.2f", stats.getAverageTalent()));
			item.setText(cnt++, String.format("%.2f", stats.getAverageAge()));
			item.setText(cnt++, Integer.toString(stats.getTeamSpecialPoints()));
			item.setText(cnt++, String.format("%.2f", stats.getAverageSpecialPoints()));
			item.setText(cnt++, Integer.toString(stats.getYellowCards()));
			item.setText(cnt++, Integer.toString(stats.getRedCards()));
			item.setText(cnt++, Integer.toString(stats.getCards()));
		}
		redraw();
	}

	public void redraw() {
		teamsTable.setRedraw(false);
		Set<Integer> visible = visibleColumns.get(currentTab);
		for (int i = 0; i < teamsTable.getColumnCount(); i++) {
			if (visible.contains(i)) {
				teamsTable.getColumn(i).pack();
			} else {
				teamsTable.getColumn(i).setWidth(0);
			}
		}
		teamsTable.setRedraw(true);
		tablePanel.layout();
		buttonsPanel.layout();
	}

	public void updateAll() {
		if (MainWindow.getAllInstance() == null) return;
		
		List<Team> allTeams = MainWindow.getAllInstance().getTeams();

		teamStats = new ArrayList<TeamStats>(allTeams.size());
		for (Team team : allTeams) {
			teamStats.add(new TeamStats(team));
		}
		teamsTable.setRedraw(false);
		teamsTable.clearAll();
		teamsTable.setItemCount(teamStats.size());
		teamsTable.setRedraw(true);
		
		updateTables();
	}

	public void updateMessages() {

		String[] tableTitles = { 
				MainWindow.getMessage("global.number"),
				MainWindow.getMessage("statistics.team.name"),
				MainWindow.getMessage("statistics.team.country"),
				MainWindow.getMessage("statistics.team.rating"),
				MainWindow.getMessage("statistics.team.stadium"),
				MainWindow.getMessage("statistics.team.finance"),
				MainWindow.getMessage("statistics.team.price.players"),
				MainWindow.getMessage("statistics.team.price.infrastructure"),
				MainWindow.getMessage("statistics.team.price.overall"),
				MainWindow.getMessage("statistics.team.price.capitalization"),
				MainWindow.getMessage("statistics.team.salary.players"),
				MainWindow.getMessage("statistics.team.salary.trainers"),
				MainWindow.getMessage("statistics.team.salary.overall"),
				MainWindow.getMessage("statistics.team.strength.average"),
				MainWindow.getMessage("statistics.team.strength.best11"),
				MainWindow.getMessage("statistics.team.strength.best15"),
				MainWindow.getMessage("statistics.team.talent"),
				MainWindow.getMessage("statistics.team.age"),
				MainWindow.getMessage("statistics.team.sp.summary"),
				MainWindow.getMessage("statistics.team.sp.average"),
				MainWindow.getMessage("statistics.team.cards.yellow"),
				MainWindow.getMessage("statistics.team.cards.red"),
				MainWindow.getMessage("statistics.team.cards.overall")
			};

		int cnt = 0;
		for (String title : tableTitles) {
			TableColumn curr = teamsTable.getColumn(cnt++);
			curr.setText(title);
		}

		cnt = 0;
		buttons[cnt++].setText(MainWindow.getMessage("statistics.team.rating"));
		buttons[cnt++].setText(MainWindow.getMessage("statistics.team.stadium"));
		buttons[cnt++].setText(MainWindow.getMessage("statistics.team.finance"));
		buttons[cnt++].setText(MainWindow.getMessage("statistics.team.salary.overall"));
		buttons[cnt++].setText(MainWindow.getMessage("statistics.team.strength.average"));
		buttons[cnt++].setText(MainWindow.getMessage("statistics.team.talent"));
		buttons[cnt++].setText(MainWindow.getMessage("statistics.team.age"));
		buttons[cnt++].setText(MainWindow.getMessage("statistics.team.sp.overall"));
		buttons[cnt++].setText(MainWindow.getMessage("statistics.team.cards.overall"));
		buttons[cnt++].setText(MainWindow.getMessage("statistics.team.overall"));
		
		nationalities.clear();
		updateTables();
	}

	public void updatePassword(String password) {
	}

	public class StatsButtonListener implements SelectionListener {

		int index;

		public StatsButtonListener(int index) {
			if (index < 0)
				index = 0;
			if (index >= buttons.length) {
				index = buttons.length - 1;
			}
			this.index = index;
		}

		public void widgetDefaultSelected(SelectionEvent e) {
			if (buttons[index].getSelection()) {
				for (Button button : buttons) {
					if (button != buttons[index]) {
						button.setSelection(false);
					}
				}
			} else {
				buttons[index].setSelection(true);
			}
			currentTab = index;
			redraw();
		}

		public void widgetSelected(SelectionEvent e) {
			widgetDefaultSelected(e);
		}

	}
}
