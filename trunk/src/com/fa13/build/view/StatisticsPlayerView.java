package com.fa13.build.view;

import java.util.*;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.*;

import com.fa13.build.model.Player;
import com.fa13.build.model.Team;

public class StatisticsPlayerView implements UIItem {
	
	private final Composite tablePanel;
	private final Composite buttonsPanel;
	
	private final Table playersTable;
	private final Table u21playersTable;
	private final StackLayout tableLayout;
	
	List<Player> players;
	List<Player> u21players;
	
	private final Color gray;
	
	private List<Integer> activeTable;
	private List<Set<Integer>> visibleColumns;
	
	private Map<String, String> nationalities;
	
	private int currentTab;
	
	private List<Comparator<Player>> comps;
	
	private final Button[] buttons;
	
	public static final int TABLE_COLUMNS_COUNT = 17;
	public static final int TABLE_BUTTONS_COUNT = 4;
	
	public StatisticsPlayerView(Composite parent) {
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
		
		tableLayout = new StackLayout();
		
		tablePanel.setLayout(tableLayout);

		data = new FormData();
		data.top = new FormAttachment(0, 0);
		data.bottom = new FormAttachment(100, 0);
		data.right = new FormAttachment(100, 0);
		buttonsPanel.setLayoutData(data);

		RowLayout buttonsLayout = new RowLayout(SWT.VERTICAL);
		buttonsLayout.fill = true;
		buttonsLayout.wrap = true;

		buttonsPanel.setLayout(buttonsLayout);
		
		playersTable = new Table(tablePanel, SWT.BORDER);
		playersTable.setLayout(new FillLayout());
		playersTable.setHeaderVisible(true);
		playersTable.setLinesVisible(true);
		
		u21playersTable = new Table(tablePanel, SWT.BORDER);
		u21playersTable.setLayout(new FillLayout());
		u21playersTable.setHeaderVisible(true);
		u21playersTable.setLinesVisible(true);
		
		comps = new ArrayList<Comparator<Player>>(TABLE_COLUMNS_COUNT);
		
		comps.add(new Comparator<Player>() {
			public int compare(Player o1, Player o2) {
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
		});
		
		comps.add(new Comparator<Player>() {
			public int compare(Player o1, Player o2) {
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
		});
		
		comps.add(new Comparator<Player>() {
			public int compare(Player o1, Player o2) {
				int position = o1.getOriginalPosition().compareTo(o2.getOriginalPosition());
				if (position != 0) {
					return position;
				}
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
		});
		
		comps.add(new Comparator<Player>() {
			public int compare(Player o1, Player o2) {
				String n1 = nationalities.get(o1.getNationalityCode());
				if (n1 == null) {
					n1 = MainWindow.getMessage("country." + o1.getNationalityCode());
					nationalities.put(o1.getNationalityCode(), n1);
				}
				String n2 = nationalities.get(o2.getNationalityCode());
				if (n2 == null) {
					n2 = MainWindow.getMessage("country." + o2.getNationalityCode());
					nationalities.put(o2.getNationalityCode(), n2);
				}
				int country = n1.compareToIgnoreCase(n2);
				if (country != 0) {
					return country;
				}
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
		});
		
		comps.add(new Comparator<Player>() {
			public int compare(Player o1, Player o2) {
				int age = o1.getAge() - o2.getAge();
				if (age != 0) {
					return age;
				}
				age = o1.getBirthtour() - o2.getBirthtour();
				if (age != 0) {
					return age;
				}
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
		});
		
		comps.add(new Comparator<Player>() {
			public int compare(Player o1, Player o2) {
				int strength = o2.getStrength() - o1.getStrength();
				if (strength != 0) {
					return strength;
				}
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
		});
		
		comps.add(new Comparator<Player>() {
			public int compare(Player o1, Player o2) {
				int talent = o2.getTalent() - o1.getTalent();
				if (talent != 0) {
					return talent;
				}
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
		});
		
/*		comps.add(new Comparator<Player>() {
			public int compare(Player o1, Player o2) {
				int sp = 0;
				
				sp += o2.getShooting() - o1.getShooting();
				sp += o2.getPassing() - o1.getPassing();
				sp += o2.getCross() - o1.getCross();
				sp += o2.getDribbling() - o1.getDribbling();
				sp += o2.getTackling() - o1.getTackling();
				sp += o2.getSpeed() - o1.getSpeed();
				sp += o2.getStamina() - o1.getStamina();
				sp += o2.getHeading() - o1.getHeading();
				sp += o2.getReflexes() - o1.getReflexes();
				sp += o2.getHandling() - o1.getHandling();
				
				if (sp != 0) {
					return sp;
				}
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
		});
		
		comps.add(new Comparator<Player>() {
			public int compare(Player o1, Player o2) {
				int sp = 0;
				
				sp += o2.getShooting() - o1.getShooting();
				sp += o2.getPassing() - o1.getPassing();
				sp += o2.getCross() - o1.getCross();
				sp += o2.getDribbling() - o1.getDribbling();
				sp += o2.getTackling() - o1.getTackling();
				sp += o2.getSpeed() - o1.getSpeed();
				sp += o2.getStamina() - o1.getStamina();
				sp += o2.getHeading() - o1.getHeading();
				sp += o2.getReflexes() - o1.getReflexes();
				sp += o2.getHandling() - o1.getHandling();
				
				if (sp != 0) {
					return sp;
				}
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
		});*/
		
		comps.add(new Comparator<Player>() {
			public int compare(Player o1, Player o2) {
				int goals = o2.getGoalsChamp() - o1.getGoalsChamp();
				if (goals != 0) {
					return goals;
				}
				int games = o1.getGames() - o2.getGames();
				if (games != 0) {
					return games;
				}
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
		});
		
		comps.add(new Comparator<Player>() {
			public int compare(Player o1, Player o2) {
				int goals = o2.getGoalsTotal() - o1.getGoalsTotal();
				if (goals != 0) {
					return goals;
				}
				goals = o2.getGoalsChamp() - o1.getGoalsChamp();
				if (goals != 0) {
					return goals;
				}
				int games = o1.getGames() - o2.getGames();
				if (games != 0) {
					return games;
				}
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
		});
		
		comps.add(new Comparator<Player>() {
			public int compare(Player o1, Player o2) {
				int assists = o2.getAssists() - o1.getAssists();
				if (assists != 0) {
					return assists;
				}
				int games = o1.getGames() - o2.getGames();
				if (games != 0) {
					return games;
				}
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
		});

		comps.add(new Comparator<Player>() {
			public int compare(Player o1, Player o2) {
				int goals = o2.getGoalsTotal() + o2.getAssists() - o1.getGoalsTotal() - o1.getAssists();
				if (goals != 0) {
					return goals;
				}
				goals = o2.getGoalsTotal() - o1.getGoalsTotal();
				if (goals != 0) {
					return goals;
				}
				goals = o2.getGoalsChamp() - o1.getGoalsChamp();
				if (goals != 0) {
					return goals;
				}
				int games = o1.getGames() - o2.getGames();
				if (games != 0) {
					return games;
				}
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
		});
		
		comps.add(new Comparator<Player>() {
			public int compare(Player o1, Player o2) {
				double mark = o2.getMark() - o1.getMark();
				if (Math.abs(mark) > 1e-6) {
					return mark > 0 ? 1 : -1;
				}
				int games = o2.getGames() - o1.getGames();
				if (games != 0) {
					return games;
				}
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
		});
		
		comps.add(new Comparator<Player>() {
			public int compare(Player o1, Player o2) {
				int yellowCards = o2.getYellowCards() - o1.getYellowCards();
				if (yellowCards != 0) {
					return yellowCards;
				}
				int games = o1.getGames() - o2.getGames();
				if (games != 0) {
					return games;
				}
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
		});
		
		comps.add(new Comparator<Player>() {
			public int compare(Player o1, Player o2) {
				int redCards = o2.getRedCards() - o1.getRedCards();
				if (redCards != 0) {
					return redCards;
				}
				int games = o1.getGames() - o2.getGames();
				if (games != 0) {
					return games;
				}
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
		});
		
		comps.add(new Comparator<Player>() {
			public int compare(Player o1, Player o2) {
				int cards = 3 * o2.getRedCards() + o2.getYellowCards() - 3 * o1.getRedCards() - o1.getYellowCards();
				if (cards != 0) {
					return cards;
				}
				int redCards = o2.getRedCards() - o1.getRedCards();
				if (redCards != 0) {
					return redCards;
				}
				int games = o1.getGames() - o2.getGames();
				if (games != 0) {
					return games;
				}
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
		});
		
		comps.add(new Comparator<Player>() {
			public int compare(Player o1, Player o2) {
				int price = o2.getPrice() - o1.getPrice();
				if (price != 0) {
					return price;
				}
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
		});
		
		comps.add(new Comparator<Player>() {
			public int compare(Player o1, Player o2) {
				int salary = o2.getSalary() - o1.getSalary();
				if (salary != 0) {
					return salary;
				}
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
		});
		
		Listener playersSortListener = new Listener() {
			public void handleEvent(Event e) {
				playersTable.setRedraw(false);
				TableColumn sortColumn = playersTable.getSortColumn();
				TableColumn currentColumn = (TableColumn) e.widget;
				int dir = playersTable.getSortDirection();
				if (sortColumn == currentColumn) {
					dir = dir == SWT.UP ? SWT.DOWN : SWT.UP;
				} else {
					playersTable.setSortColumn(currentColumn);
					dir = SWT.UP;
				}
				int index = playersTable.indexOf(currentColumn);
				Collections.sort(players, comps.get(index));
				if (dir == SWT.DOWN) {
					Collections.reverse(players);
				}
				playersTable.setSortDirection(dir);
				updateTables();
				playersTable.setRedraw(true);
			}
		};
		
		Listener u21playersSortListener = new Listener() {
			public void handleEvent(Event e) {
				u21playersTable.setRedraw(false);
				TableColumn sortColumn = u21playersTable.getSortColumn();
				TableColumn currentColumn = (TableColumn) e.widget;
				int dir = u21playersTable.getSortDirection();
				if (sortColumn == currentColumn) {
					dir = dir == SWT.UP ? SWT.DOWN : SWT.UP;
				} else {
					u21playersTable.setSortColumn(currentColumn);
					dir = SWT.UP;
				}
				int index = u21playersTable.indexOf(currentColumn);
				Collections.sort(u21players, comps.get(index));
				if (dir == SWT.DOWN) {
					Collections.reverse(u21players);
				}
				u21playersTable.setSortDirection(dir);
				updateTables();
				u21playersTable.setRedraw(true);
			}
		};
		
		activeTable = Arrays.asList(0, 0, 0, 1);
		
		visibleColumns = new ArrayList<Set<Integer>>(TABLE_BUTTONS_COUNT);
		Set<Integer> curr;
		curr = new HashSet<Integer>(7);
		Collections.addAll(curr, 0, 1, 2, 3, 4, 5, 6, 15, 16);
		visibleColumns.add(curr);
		curr = new HashSet<Integer>(10);
		Collections.addAll(curr, 0, 1, 2, 3, 7, 8, 9, 10, 11, 12, 13, 14);
		visibleColumns.add(curr);
		curr = new HashSet<Integer>(15);
		Collections.addAll(curr, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16);
		visibleColumns.add(curr);
		curr = new HashSet<Integer>(15);
		Collections.addAll(curr, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16);
		visibleColumns.add(curr);

		for (int i = 0; i < TABLE_COLUMNS_COUNT; i++) {
			TableColumn column = new TableColumn(playersTable, SWT.NONE);
			column.setResizable(false);
			column.addListener(SWT.Selection, playersSortListener);
		}
		
		for (int i = 0; i < TABLE_COLUMNS_COUNT; i++) {
			TableColumn column = new TableColumn(u21playersTable, SWT.NONE);
			column.setResizable(false);
			column.addListener(SWT.Selection, u21playersSortListener);
		}

		buttons = new Button[TABLE_BUTTONS_COUNT];
		for (int i = 0; i < TABLE_BUTTONS_COUNT; i++) {
			buttons[i] = new Button(buttonsPanel, SWT.TOGGLE);
			buttons[i].addSelectionListener(new StatsButtonListener(i));
		}

		buttons[0].setSelection(true);

		updateAll();

		playersTable.setSortColumn(playersTable.getColumn(1));
		playersTable.setSortDirection(SWT.UP);
		
		u21playersTable.setSortColumn(playersTable.getColumn(1));
		u21playersTable.setSortDirection(SWT.UP);

		updateMessages();
	}

	public void redraw() {
		int table = activeTable.get(currentTab);
		Set<Integer> visible = visibleColumns.get(currentTab);
		if (table == 0) {
			playersTable.setRedraw(false);			
			for (int i = 0; i < playersTable.getColumnCount(); i++) {
				if (visible.contains(i)) {
					playersTable.getColumn(i).pack();
				} else {
					playersTable.getColumn(i).setWidth(0);
				}
			}
			playersTable.setRedraw(true);
			tableLayout.topControl = playersTable;
		} else {
			u21playersTable.setRedraw(false);			
			for (int i = 0; i < u21playersTable.getColumnCount(); i++) {
				if (visible.contains(i)) {
					u21playersTable.getColumn(i).pack();
				} else {
					u21playersTable.getColumn(i).setWidth(0);
				}
			}
			u21playersTable.setRedraw(true);
			tableLayout.topControl = u21playersTable;
		}
		tablePanel.layout();
		buttonsPanel.layout();

	}
	
	private void updateTables() {
		if (MainWindow.getAllInstance() == null) return;
		playersTable.setRedraw(false);
		playersTable.clearAll();
		playersTable.setItemCount(players.size());
		
		int ind = 0;
		for (Player player : players) {
			TableItem item = playersTable.getItem(ind++);
			int cnt = 0;
			if (ind % 2 == 0) {
				item.setBackground(gray);
			}
			item.setText(cnt++, Integer.toString(ind));
			item.setText(cnt++, player.getName());
			item.setText(cnt++, player.getOriginalPosition().toString());
			String nation = nationalities.get(player.getNationalityCode());
			if (nation == null) {
				nation = MainWindow.getMessage("country." + player.getNationalityCode());
				nationalities.put(player.getNationalityCode(), nation);
			}
			item.setText(cnt++, nation);
			item.setText(cnt++, Integer.toString(player.getAge()));
			item.setText(cnt++, Integer.toString(player.getStrength()));
			item.setText(cnt++, Integer.toString(player.getTalent()));
			item.setText(cnt++, Integer.toString(player.getGoalsChamp()));
			item.setText(cnt++, Integer.toString(player.getGoalsTotal()));
			item.setText(cnt++, Integer.toString(player.getAssists()));
			item.setText(cnt++, Integer.toString(player.getGoalsTotal() + player.getAssists()));
			item.setText(cnt++, String.format("%.2f", player.getMark()));
			item.setText(cnt++, Integer.toString(player.getYellowCards()));
			item.setText(cnt++, Integer.toString(player.getRedCards()));
			item.setText(cnt++, Integer.toString(3 * player.getRedCards() + player.getYellowCards()));
			item.setText(cnt++, Integer.toString(player.getPrice()));
			item.setText(cnt++, Integer.toString(player.getSalary()));
		}
		
		playersTable.setRedraw(true);
		
		u21playersTable.setRedraw(false);
		u21playersTable.clearAll();
		u21playersTable.setItemCount(u21players.size());
		
		ind = 0;
		for (Player player : u21players) {
			TableItem item = u21playersTable.getItem(ind++);
			int cnt = 0;
			if (ind % 2 == 0) {
				item.setBackground(gray);
			}
			item.setText(cnt++, Integer.toString(ind));
			item.setText(cnt++, player.getName());
			item.setText(cnt++, player.getOriginalPosition().toString());
			String nation = nationalities.get(player.getNationalityCode());
			if (nation == null) {
				nation = MainWindow.getMessage("country." + player.getNationalityCode());
				nationalities.put(player.getNationalityCode(), nation);
			}
			item.setText(cnt++, nation);
			item.setText(cnt++, Integer.toString(player.getAge()));
			item.setText(cnt++, Integer.toString(player.getStrength()));
			item.setText(cnt++, Integer.toString(player.getTalent()));
			item.setText(cnt++, Integer.toString(player.getGoalsChamp()));
			item.setText(cnt++, Integer.toString(player.getGoalsTotal()));
			item.setText(cnt++, Integer.toString(player.getAssists()));
			item.setText(cnt++, Integer.toString(player.getGoalsTotal() + player.getAssists()));
			item.setText(cnt++, String.format("%.2f", player.getMark()));
			item.setText(cnt++, Integer.toString(player.getYellowCards()));
			item.setText(cnt++, Integer.toString(player.getRedCards()));
			item.setText(cnt++, Integer.toString(3 * player.getRedCards() + player.getYellowCards()));
			item.setText(cnt++, Integer.toString(player.getPrice()));
			item.setText(cnt++, Integer.toString(player.getSalary()));
		}
		
		u21playersTable.setRedraw(true);
		redraw();
	}

	public void updateAll() {
		if (MainWindow.getAllInstance() == null) {
			return;
		}
		List<Team> allTeams = MainWindow.getAllInstance().getTeams(); 
		players = new ArrayList<Player>(15 * allTeams.size());
		u21players = new ArrayList<Player>(3 * allTeams.size());
		
		for (Team team: allTeams) {
			for (Player player: team.getPlayers()) {
				players.add(player);
				if (player.getAge() <= 21) {
					u21players.add(player);
				}
			}
		}
		
		updateTables();
	}

	public void updateMessages() {
		
		String[] tableTitles = { 
				MainWindow.getMessage("global.number"),
				MainWindow.getMessage("statistics.player.name"),
				MainWindow.getMessage("statistics.player.position"),
				MainWindow.getMessage("statistics.player.nationality"),
				MainWindow.getMessage("statistics.player.age"),
				MainWindow.getMessage("statistics.player.strength"),
				MainWindow.getMessage("statistics.player.talent"),
				MainWindow.getMessage("statistics.player.goals.champ"),
				MainWindow.getMessage("statistics.player.goals.total"),
				MainWindow.getMessage("statistics.player.assists"),
				MainWindow.getMessage("statistics.player.goalsplusassists"),
				MainWindow.getMessage("statistics.player.mark"),
				MainWindow.getMessage("statistics.player.cards.yellow"),
				MainWindow.getMessage("statistics.player.cards.red"),
				MainWindow.getMessage("statistics.player.cards.overall"),
				MainWindow.getMessage("statistics.player.price"),
				MainWindow.getMessage("statistics.player.salary")
			};

		int cnt = 0;
		for (String title : tableTitles) {
			TableColumn curr = playersTable.getColumn(cnt++);
			curr.setText(title);
		}
		
		cnt = 0;
		for (String title : tableTitles) {
			TableColumn curr = u21playersTable.getColumn(cnt++);
			curr.setText(title);
		}

		cnt = 0;
		buttons[cnt++].setText(MainWindow.getMessage("statistics.player.personal"));
		buttons[cnt++].setText(MainWindow.getMessage("statistics.player.performance"));
		buttons[cnt++].setText(MainWindow.getMessage("statistics.player.overall.all"));
		buttons[cnt++].setText(MainWindow.getMessage("statistics.player.overall.u21"));

		nationalities.clear();
		updateTables();
	}

	public void updatePassword(String password) {
		return;
	}

	public class StatsButtonListener implements SelectionListener {

		int index;

		public StatsButtonListener(int index) {
			if (index < 0) {
				index = 0;
			}
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
