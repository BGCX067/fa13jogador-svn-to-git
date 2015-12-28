package com.fa13.build.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import com.fa13.build.controller.io.ReaderException;
import com.fa13.build.controller.io.TrainingReader;
import com.fa13.build.controller.io.TrainingWriter;
import com.fa13.build.model.Player;
import com.fa13.build.model.PlayerTraining;
import com.fa13.build.model.Team;
import com.fa13.build.model.Training;

public class TrainingView implements UIItem, TeamUIItem {

	String password;
	TabItem mainItem;
	Table playersTable;
	Training trainInstance;
	Button openButton;
	Button saveButton;
	Composite topComposite;
	TabFolder parentInstance;
	Composite btnComposite;
	Composite labelComposite;
	DaysOfRestComposite daysOfRestComposite;
	Label mainCoachLabelName;
	Label defenderLabelName;
	Label midfielderLabelName;
	Label forwardLabelName;
	Label goalkeeperLabelName;
	Label mainCoachLabelVal;
	Label defenderLabelVal;
	Label midfielderLabelVal;
	Label forwardLabelVal;
	Label goalkeeperLabelVal;
	Label clubResLabelVal;
	Label scoutRestLabel;
	Label scoutTalent;
	Button scoutRestButton;
	Spinner scoutTalentSpinner;
	TableEditor editor;
	Group pointsGroup;
	Group scoutGroup;
	Group clubResGroup;
	Label restLabel;
	int mainCoachPoints;
	int goalkeeperPoints;
	int defenderPoints;
	int midfielderPoints;
	int forwardPoints;
	int clubResource;

	static final int FIRST_VALUE_INTERVAL = 20;
	static final int SECOND_VALUE_INTERVAL = 40;
	static final int THIRD_VALUE_INTERVAL = 60;
	static final int FOURTH_VALUE_INTERVAL = 80;
	static final int FIFTH_VALUE_INTERVAL = 100;
	static final int REST_SLIDER_MAX = 30;// 10=some specific
	static final int SCOUT_TALENT_MAX = 60;
	static final int SCOUT_TALENT_MIN = 40;
	
	final Color black;
	final Color blue;
	final Color green;
	final Color red;
	final Color some1;
	final Color some2;
	

	public TrainingView(TabFolder parent) {
		parentInstance = parent;
		
		Display display = parentInstance.getDisplay();
		
		black = new Color(display, 0, 0, 0);
		blue = new Color(display, 0, 0, 0);
		green = new Color(display, 0, 255, 0);
		red = new Color(display, 255, 0, 0);
		some1 = new Color(display, 0, 128, 128);
		some2 = new Color(display, 0, 128, 128);

		if (MainWindow.getAllInstance() != null) {
			updateAll();
		}
		mainItem = new TabItem(parentInstance, SWT.NONE);
		mainItem.setText(MainWindow.getMessage("trainingTabName"));
		FontData[] fontData = parent.getFont().getFontData();
		for (int i = 0; i < fontData.length; i++) {
			fontData[i].setHeight(fontData[i].getHeight() - 2);
		}

		topComposite = new Composite(parentInstance, SWT.NONE);
		GridLayout topLayout = new GridLayout(1, false);
		topLayout.marginWidth = 0;
		topLayout.marginHeight = 0;
		topComposite.setLayout(topLayout);
		mainItem.setControl(topComposite);
		playersTable = new Table(topComposite, SWT.BORDER | SWT.FULL_SELECTION);
		playersTable.setLinesVisible(true);
		playersTable.setHeaderVisible(true);

		GridData gridData = new GridData(GridData.FILL, GridData.FILL, true,
				true);
		playersTable.setLayoutData(gridData);
		String[] titles = { MainWindow.getMessage("PlayerNumber"),
				MainWindow.getMessage("PlayerPosition"),
				MainWindow.getMessage("PlayerName"),
				MainWindow.getMessage("PTshooting"), "",
				MainWindow.getMessage("PTpassing"), "",
				MainWindow.getMessage("PTcross"), "",
				MainWindow.getMessage("PTdribbling"), "",
				MainWindow.getMessage("PTtackling"), "",
				MainWindow.getMessage("PTspeed"), "",
				MainWindow.getMessage("PTstamina"), "",
				MainWindow.getMessage("PTheading"), "",
				MainWindow.getMessage("PTreflexes"), "",
				MainWindow.getMessage("PThandling"), "",
				MainWindow.getMessage("PlayerFitness"), "",
				MainWindow.getMessage("PTmorale"),
				MainWindow.getMessage("PTmoraleFinance"),
				MainWindow.getMessage("PTfitnessFinance") };

		for (int i = 0; i < titles.length; i++) {
			TableColumn column = new TableColumn(playersTable, SWT.LEFT);
			column.setText("100.");
			playersTable.getColumn(i).pack();
			// playersTable.getColumn(i).setWidth(35);
			playersTable.getColumn(i).setResizable(false);
			column.setText(titles[i]);
		}

		labelComposite = new Composite(topComposite, SWT.BORDER);
		gridData = new GridData(GridData.FILL, GridData.FILL, true, false);
		labelComposite.setLayoutData(gridData);
		labelComposite.setLayout(new GridLayout(3, false));

		pointsGroup = new Group(labelComposite, SWT.SHADOW_NONE);
		gridData = new GridData(GridData.BEGINNING, GridData.END, false, true, 1, 2);
		pointsGroup.setText(MainWindow.getMessage("PTPointsGroup"));
		pointsGroup.setLayoutData(gridData);
		pointsGroup.setLayout(new GridLayout(2, false));

		mainCoachLabelName = new Label(pointsGroup, SWT.NONE);
		gridData = new GridData(GridData.BEGINNING, GridData.END, true, true);
		mainCoachLabelName.setText(MainWindow.getMessage("PTMainCoachPoints")
				+ ":");
		mainCoachLabelName.setLayoutData(gridData);
		mainCoachLabelName.pack();

		mainCoachLabelVal = new Label(pointsGroup, SWT.NONE);
		gridData = new GridData(GridData.END, GridData.END, true, true);
		mainCoachLabelVal.setLayoutData(gridData);

		goalkeeperLabelName = new Label(pointsGroup, SWT.NONE);
		gridData = new GridData(GridData.BEGINNING, GridData.END, true, true);
		goalkeeperLabelName.setText(MainWindow.getMessage("PTGoalkeeperPoints")
				+ ":");
		goalkeeperLabelName.setLayoutData(gridData);
		goalkeeperLabelName.pack();

		goalkeeperLabelVal = new Label(pointsGroup, SWT.NONE);
		gridData = new GridData(GridData.END, GridData.END, true, true);
		goalkeeperLabelVal.setLayoutData(gridData);

		defenderLabelName = new Label(pointsGroup, SWT.NONE);
		gridData = new GridData(GridData.BEGINNING, GridData.END, true, true);
		defenderLabelName.setText(MainWindow.getMessage("PTDefenderPoints")
				+ ":");
		defenderLabelName.setLayoutData(gridData);
		defenderLabelName.pack();

		defenderLabelVal = new Label(pointsGroup, SWT.NONE);
		gridData = new GridData(GridData.END, GridData.END, true, true);
		defenderLabelVal.setLayoutData(gridData);

		midfielderLabelName = new Label(pointsGroup, SWT.NONE);
		gridData = new GridData(GridData.BEGINNING, GridData.END, true, true);
		midfielderLabelName.setText(MainWindow.getMessage("PTMidfielderPoints")
				+ ":");
		midfielderLabelName.setLayoutData(gridData);
		midfielderLabelName.pack();

		midfielderLabelVal = new Label(pointsGroup, SWT.NONE);
		gridData = new GridData(GridData.END, GridData.END, true, true);
		midfielderLabelVal.setLayoutData(gridData);

		forwardLabelName = new Label(pointsGroup, SWT.NONE);
		gridData = new GridData(GridData.BEGINNING, GridData.END, true, true);
		forwardLabelName
				.setText(MainWindow.getMessage("PTForwardPoints") + ":");
		forwardLabelName.setLayoutData(gridData);
		forwardLabelName.pack();

		forwardLabelVal = new Label(pointsGroup, SWT.NONE);
		gridData = new GridData(GridData.END, GridData.END, true, true);
		forwardLabelVal.setLayoutData(gridData);

		pointsGroup.layout();

		scoutGroup = new Group(labelComposite, SWT.SHADOW_NONE);
		gridData = new GridData(GridData.BEGINNING, GridData.BEGINNING, false,
				true);
		scoutGroup.setText(MainWindow.getMessage("PTScoutGroup"));
		scoutGroup.setLayoutData(gridData);
		scoutGroup.setLayout(new GridLayout(2, false));

		scoutTalent = new Label(scoutGroup, SWT.NONE);
		gridData = new GridData(GridData.BEGINNING, GridData.END, true, true);
		scoutTalent.setText(MainWindow.getMessage("PTScoutTalent"));
		scoutTalent.pack();

		scoutTalentSpinner = new Spinner(scoutGroup, SWT.READ_ONLY);
		gridData = new GridData(GridData.END, GridData.END, true, true);
		scoutTalentSpinner.setLayoutData(gridData);
		scoutTalentSpinner.setMinimum(SCOUT_TALENT_MIN);
		scoutTalentSpinner.setMaximum(SCOUT_TALENT_MAX);
		scoutTalentSpinner.setIncrement(5);
		scoutTalentSpinner.pack();
		scoutTalentSpinner.addSelectionListener(new ScoutTalentListener());

		scoutRestLabel = new Label(scoutGroup, SWT.NONE);
		gridData = new GridData(GridData.BEGINNING, GridData.END, true, true);
		scoutRestLabel.setLayoutData(gridData);
		scoutRestLabel.setText(MainWindow.getMessage("PTScoutVacation"));
		scoutRestLabel.pack();

		scoutRestButton = new Button(scoutGroup, SWT.CHECK);
		gridData = new GridData(GridData.END, GridData.END, true, true);
		scoutRestButton.setLayoutData(gridData);
		scoutRestButton.addSelectionListener(new ScoutRestListener());

		
		daysOfRestComposite = new DaysOfRestComposite(labelComposite, SWT.NONE,
				this);
		gridData = new GridData(GridData.FILL, GridData.BEGINNING, false, false);
		daysOfRestComposite.setLayoutData(gridData);

		
		clubResGroup = new Group(labelComposite, SWT.SHADOW_NONE);
		gridData = new GridData(GridData.BEGINNING, GridData.END, false,
				false);
		clubResGroup.setText(MainWindow.getMessage("PTClubResGroup"));
		clubResGroup.setLayoutData(gridData);
		clubResGroup.setLayout(new GridLayout(1, false));
		clubResGroup.pack(true);

		clubResLabelVal = new Label(clubResGroup, SWT.NONE);
		gridData = new GridData(GridData.CENTER, GridData.CENTER, true, true);
		clubResLabelVal.setLayoutData(gridData);
		clubResLabelVal.setText("0" + " " + MainWindow.getMessage("Thousands"));
		clubResLabelVal.pack(true);
		clubResGroup.pack(true);

		labelComposite.layout();
		refreshLabel();

		btnComposite = new Composite(topComposite, SWT.BORDER);
		gridData = new GridData(GridData.FILL, GridData.FILL, true, false);
		btnComposite.setLayoutData(gridData);
		GridLayout gridLayout = new GridLayout(4, false);
		btnComposite.setLayout(gridLayout);

		openButton = new Button(btnComposite, SWT.PUSH);
		
		gridData = new GridData(GridData.END, GridData.CENTER, false, false);
		openButton.setLayoutData(gridData);
		openButton.addSelectionListener(new OpenButtonListner());

		saveButton = new Button(btnComposite, SWT.PUSH);
		
		gridData = new GridData(GridData.END, GridData.CENTER, false, false);
		saveButton.setLayoutData(gridData);
		saveButton.addSelectionListener(new SaveButtonListner());

		redraw();

		editor = new TableEditor(playersTable);
		editor.horizontalAlignment = SWT.LEFT;
		editor.grabHorizontal = true;
		editor.minimumWidth = 35;
		playersTable.addListener(SWT.MouseDown, new PlayerTableEditListener());

	}

	public void updateAll() {
		if (MainWindow.getAllInstance() == null) {
			return;
		}
		ArrayList<PlayerTraining> trainPlayers = new ArrayList<PlayerTraining>();
		int money = 0;
		int mainCoachPoints = 0;
		int defenderPoints = 0;
		int midfielderPoints = 0;
		int forwardPoints = 0;
		int goalkeeperPoints = 0;
		Team curTeam = MainWindow.getAllInstance().getCurrentTeam();
		if (curTeam != null) {
			int val = curTeam.getCoach();
			mainCoachPoints = 20 + val / 10;
			val = curTeam.getDefendersCoach();
			defenderPoints = 3 * val + ((val != 0) ? 2 : 0);
			val = curTeam.getMidfieldersCoach();
			midfielderPoints = 3 * val + ((val != 0) ? 2 : 0);
			val = curTeam.getForwardsCoach();
			forwardPoints = 3 * val + ((val != 0) ? 2 : 0);
			val = curTeam.getGoalkeepersCoach();
			goalkeeperPoints = 3 * val + ((val != 0) ? 2 : 0);
			money = curTeam.getManagerFinance() / 1000;

			List<Player> players = curTeam.getPlayers();
			for (Iterator<Player> iterator = players.iterator(); iterator
					.hasNext();) {
				Player player = iterator.next();
				trainPlayers.add(new PlayerTraining(player.getNumber(), 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
			}
		}
		
		this.mainCoachPoints = mainCoachPoints;
		this.defenderPoints = defenderPoints;
		this.midfielderPoints = midfielderPoints;
		this.forwardPoints = forwardPoints;
		this.goalkeeperPoints = goalkeeperPoints;
		this.trainInstance = new Training("", 0, false, trainPlayers);
		this.clubResource = money;
		redraw();

	}

	public void redraw() {

		playersTable.setRedraw(false);
		if (MainWindow.getAllInstance() != null) {

			Team curTeam = MainWindow.getAllInstance().getCurrentTeam();
			List<Player> curPlayers = curTeam.getPlayers();
			/*
			 * if (playersTable.getItemCount() > curPlayers.size()) {
			 * playersTable.remove(curPlayers.size() + 1,
			 * playersTable.getItemCount() - 1); } while
			 * (playersTable.getItemCount() < curPlayers.size()) { TableItem
			 * item = new TableItem (playersTable, SWT.NONE); }
			 */
			playersTable.setItemCount(curPlayers.size());
			int i = 0;
			for (Player player: curPlayers) {
				PlayerTraining trainPlayer = findPlayerbyNumber(player
						.getNumber());
				TableItem item = playersTable.getItem(i);
				item.setData(player);
				item.setText(0, String.valueOf(player.getNumber()));
				item.setText(1, player.getPosition().toString());
				item.setText(2, player.getName());
				item.setText(3, getStringValue(player.getShooting()));
				item.setForeground(3, getColourForValue(player.getShooting()));
				item.setText(5, getStringValue(player.getPassing()));
				item.setText(7, getStringValue(player.getCross()));
				item.setText(9, getStringValue(player.getDribbling()));
				item.setText(11, getStringValue(player.getTackling()));
				item.setText(13, getStringValue(player.getSpeed()));
				item.setText(15, getStringValue(player.getStamina()));
				item.setText(17, getStringValue(player.getHeading()));
				item.setText(19, getStringValue(player.getReflexes()));
				item.setText(21, getStringValue(player.getHandling()));
				item.setText(23, getStringValue(player
						.getFitness(daysOfRestComposite.getDaysOfRest())));
				item.setText(25, getStringValue(player.getMorale()));
				if (trainPlayer != null) {
					item.setText(4, getStringValue(trainPlayer
							.getShootingPoints()));
					item.setText(6, getStringValue(trainPlayer
							.getPassingPoints()));
					item.setText(8,
							getStringValue(trainPlayer.getCrossPoints()));
					item.setText(10, getStringValue(trainPlayer
							.getDribblingPoints()));
					item.setText(12, getStringValue(trainPlayer
							.getTacklingPoints()));
					item.setText(14, getStringValue(trainPlayer
							.getSpeedPoints()));
					item.setText(16, getStringValue(trainPlayer
							.getStaminaPoints()));
					item.setText(18, getStringValue(trainPlayer
							.getHeadingPoints()));
					item.setText(20, getStringValue(trainPlayer
							.getReflexesPoints()));
					item.setText(22, getStringValue(trainPlayer
							.getHandlingPoints()));
					item.setText(24, getStringValue(trainPlayer
							.getFitnessPoints()));
					item.setText(26, getStringValue(trainPlayer
							.getMoraleFinance()));
					item.setText(27, getStringValue(trainPlayer
							.getFitnessFinance()));
				} else {
					item.setText(4, "");
					item.setText(6, "");
					item.setText(8, "");
					item.setText(10, "");
					item.setText(12, "");
					item.setText(14, "");
					item.setText(16, "");
					item.setText(18, "");
					item.setText(20, "");
					item.setText(22, "");
					item.setText(24, "");
					item.setText(26, "");
					item.setText(27, "");
				}
				for (int j = 3; j < playersTable.getColumnCount(); j++) {
					int val = getIntegerValue(item.getText(j));
					Color color = getColourForValue(val);
					if (j < playersTable.getColumnCount() - 5)
						item.setForeground(j, color);
				}
				i++;
			}
		}

		for (int j = 0; j < playersTable.getColumnCount(); j++) {
			//if (j != 23)
				playersTable.getColumn(j).pack();
			// playersTable.getColumn(j).setWidth(35);
		}
		playersTable.setRedraw(true);
		topComposite.layout();
		labelComposite.layout();
		refreshLabel();
	}

	public class OpenButtonListner implements SelectionListener {

		public void widgetDefaultSelected(SelectionEvent arg0) {
			openTrainingDlg();
		}

		public void widgetSelected(SelectionEvent arg0) {
			openTrainingDlg();
		}
	}

	public class SaveButtonListner implements SelectionListener {

		public void widgetDefaultSelected(SelectionEvent arg0) {
			saveTrainingDlg();
		}

		public void widgetSelected(SelectionEvent arg0) {
			saveTrainingDlg();
		}
	}

	public void openTraining(String fname) {
		if (fname == null) {
			return;
		}
		try {
			trainInstance = TrainingReader.readTrainingFile(fname);
		} catch (ReaderException e) {
			e.printStackTrace();
		}
		redraw();
	}

	public void saveTraining(String fname) {
		if (fname == null) {
			return;
		}
		try {
			trainInstance.setPassword(password);
			TrainingWriter.writeTrainingFile(fname, trainInstance);
		} catch (IOException e) {
			e.printStackTrace();
		}
		redraw();
	}

	public void openTrainingDlg() {

		FileDialog dlg = new FileDialog(parentInstance.getShell(), SWT.OPEN);

		String ext[] = new String[1];
		ext[0] = MainWindow.getMessage("FilterExtensionRequest");
		dlg.setFilterExtensions(ext);
		String extNames[] = new String[1];
		extNames[0] = MainWindow.getMessage("FilterExtensionRequestName");
		dlg.setFilterNames(extNames);
		String result = dlg.open();
		if (result != null) {
			this.openTraining(result);
			redraw();
		}
	}

	public void saveTrainingDlg() {

		FileDialog dlg = new FileDialog(parentInstance.getShell(), SWT.SAVE);

		int sum = 0;

		for (Iterator<PlayerTraining> iterator = trainInstance.getPlayers()
				.iterator(); iterator.hasNext();) {
			PlayerTraining trainPlayer = iterator.next();
			sum += trainPlayer.getFitnessFinance()
					+ trainPlayer.getMoraleFinance();
		}

		if (sum > MainWindow.getAllInstance().getCurrentTeam().getManagerFinance() / 1000) {
			MessageBox mbx = new MessageBox(parentInstance.getShell(), SWT.OK
					| SWT.CANCEL);
			mbx.setMessage(MainWindow.getMessage("PTSaveError"));
			if (mbx.open() != SWT.OK)
				return;
		}

		String ext[] = new String[1];
		ext[0] = MainWindow.getMessage("FilterExtensionRequest");
		dlg.setFilterExtensions(ext);
		String extNames[] = new String[1];
		extNames[0] = MainWindow.getMessage("FilterExtensionRequestName");
		dlg.setFilterNames(extNames);
		String result = dlg.open();
		if (result != null) {
			this.saveTraining(result);
			redraw();
		}
	}

	private PlayerTraining findPlayerbyNumber(int number) {
		if (trainInstance != null) {
			List<PlayerTraining> curPlayers = trainInstance.getPlayers();
			for (Iterator<PlayerTraining> playerIt = curPlayers.iterator(); playerIt
					.hasNext();) {
				PlayerTraining player = playerIt.next();
				if (player.getNumber() == number) {
					return player;
				}
			}
		}
		return null;
	}

	private String getStringValue(int i) throws NumberFormatException {
		if (i == 0)
			return "";
		return String.valueOf(i);
	}

	private int getIntegerValue(String st) {
		if (st == null || st.isEmpty())
			return 0;
		return Integer.valueOf(st);
	}

	private Color getColourForValue(int value) {
		if (value <= FIRST_VALUE_INTERVAL) {
			return black;
		} else if (value < SECOND_VALUE_INTERVAL) {
			return blue;
		} else if (value < THIRD_VALUE_INTERVAL) {
			return green;
		} else if (value < FOURTH_VALUE_INTERVAL) {
			return red;
		} else if (value < FIFTH_VALUE_INTERVAL) {
			return some1;
		} else {
			return some2;
		}
	}

	private int distributePlayerPoints(Player player, int column, int newPoints) {
		if (column == 27) column++;
		int skill = (column - 3) / 2;

		Team curTeam = MainWindow.getAllInstance().getCurrentTeam();
		List<Player> players = curTeam.getPlayers();
		int val = curTeam.getCoach();
		int mainCoachPointsMax = 20 + val / 10;
		val = curTeam.getDefendersCoach();
		int defenderPointsMax = 3 * val + ((val != 0) ? 2 : 0);
		val = curTeam.getMidfieldersCoach();
		int midfielderPointsMax = 3 * val + ((val != 0) ? 2 : 0);
		val = curTeam.getForwardsCoach();
		int forwardPointsMax = 3 * val + ((val != 0) ? 2 : 0);
		val = curTeam.getGoalkeepersCoach();
		int goalkeeperPointsMax = 3 * val + ((val != 0) ? 2 : 0);
		int playerPointsTotal = 0;
		int rest = 0;
		PlayerTraining trainPlayer;

		//System.out.printf("1: %d\n", mainCoachPointsMax);

		for (Player curr: players) {
			trainPlayer = findPlayerbyNumber(curr.getNumber());
			playerPointsTotal = trainPlayer.getShootingPoints()
					+ trainPlayer.getPassingPoints()
					+ trainPlayer.getCrossPoints()
					+ trainPlayer.getDribblingPoints()
					+ trainPlayer.getTacklingPoints()
					+ trainPlayer.getSpeedPoints()
					+ trainPlayer.getStaminaPoints()
					+ trainPlayer.getHeadingPoints()
					+ trainPlayer.getReflexesPoints()
					+ trainPlayer.getHandlingPoints()
					+ trainPlayer.getFitnessPoints();
			if (trainPlayer.getNumber() == player.getNumber()) {
				switch (skill) {
				case 0:
					playerPointsTotal -= trainPlayer.getShootingPoints();
					break;
				case 1:
					playerPointsTotal -= trainPlayer.getPassingPoints();
					break;
				case 2:
					playerPointsTotal -= trainPlayer.getCrossPoints();
					break;
				case 3:
					playerPointsTotal -= trainPlayer.getDribblingPoints();
					break;
				case 4:
					playerPointsTotal -= trainPlayer.getTacklingPoints();
					break;
				case 5:
					playerPointsTotal -= trainPlayer.getSpeedPoints();
					break;
				case 6:
					playerPointsTotal -= trainPlayer.getStaminaPoints();
					break;
				case 7:
					playerPointsTotal -= trainPlayer.getHeadingPoints();
					break;
				case 8:
					playerPointsTotal -= trainPlayer.getReflexesPoints();
					break;
				case 9:
					playerPointsTotal -= trainPlayer.getHandlingPoints();
					break;
				case 10:
					playerPointsTotal -= trainPlayer.getFitnessPoints();
					break;
				default:
					break;
				}
			}
			switch (curr.getPosition()) {
			case LF:
			case CF:
			case RF:
				rest = forwardPointsMax - playerPointsTotal;
				if (rest > 0) {
					forwardPointsMax = rest;
					rest = 0;
				} else {
					forwardPointsMax = 0;
				}
				break;
			case LM:
			case CM:
			case RM:
				rest = midfielderPointsMax - playerPointsTotal;
				if (rest > 0) {
					midfielderPointsMax = rest;
					rest = 0;
				} else {
					midfielderPointsMax = 0;
				}
				break;
			case LD:
			case CD:
			case RD:
				rest = defenderPointsMax - playerPointsTotal;
				if (rest > 0) {
					defenderPointsMax = rest;
					rest = 0;
				} else {
					defenderPointsMax = 0;
				}
				break;
			case GK:
				rest = goalkeeperPointsMax - playerPointsTotal;
				if (rest > 0) {
					goalkeeperPointsMax = rest;
					rest = 0;
				} else {
					goalkeeperPointsMax = 0;
				}
				break;
			}
			mainCoachPointsMax += rest;
		}

		switch (player.getPosition()) {
		case LF:
		case CF:
		case RF:
			newPoints = (newPoints < (mainCoachPointsMax + forwardPointsMax)) ? newPoints
					: (mainCoachPointsMax + forwardPointsMax);
			rest = forwardPointsMax - newPoints;
			if (rest > 0) {
				forwardPointsMax = rest;
				rest = 0;
			} else {
				forwardPointsMax = 0;
				mainCoachPointsMax += rest;
			}
			break;
		case LM:
		case CM:
		case RM:
			newPoints = (newPoints < (mainCoachPointsMax + midfielderPointsMax)) ? newPoints
					: (mainCoachPointsMax + midfielderPointsMax);
			rest = midfielderPointsMax - newPoints;
			if (rest > 0) {
				midfielderPointsMax = rest;
				rest = 0;
			} else {
				midfielderPointsMax = 0;
				mainCoachPointsMax += rest;
			}
			break;
		case LD:
		case CD:
		case RD:
			newPoints = (newPoints < (mainCoachPointsMax + defenderPointsMax)) ? newPoints
					: (mainCoachPointsMax + defenderPointsMax);
			rest = defenderPointsMax - newPoints;
			if (rest > 0) {
				defenderPointsMax = rest;
				rest = 0;
			} else {
				defenderPointsMax = 0;
				mainCoachPointsMax += rest;
			}
			break;
		case GK:
			newPoints = (newPoints < (mainCoachPointsMax + goalkeeperPointsMax)) ? newPoints
					: (mainCoachPointsMax + goalkeeperPointsMax);
			rest = goalkeeperPointsMax - newPoints;
			if (rest > 0) {
				goalkeeperPointsMax = rest;
				rest = 0;
			} else {
				goalkeeperPointsMax = 0;
				mainCoachPointsMax += rest;
			}
			break;
		}
		trainPlayer = findPlayerbyNumber(player.getNumber());
		switch (skill) {
		case 0:
			trainPlayer.setShootingPoints(newPoints);
			break;
		case 1:
			trainPlayer.setPassingPoints(newPoints);
			break;
		case 2:
			trainPlayer.setCrossPoints(newPoints);
			break;
		case 3:
			trainPlayer.setDribblingPoints(newPoints);
			break;
		case 4:
			trainPlayer.setTacklingPoints(newPoints);
			break;
		case 5:
			trainPlayer.setSpeedPoints(newPoints);
			break;
		case 6:
			trainPlayer.setStaminaPoints(newPoints);
			break;
		case 7:
			trainPlayer.setHeadingPoints(newPoints);
			break;
		case 8:
			trainPlayer.setReflexesPoints(newPoints);
			break;
		case 9:
			trainPlayer.setHandlingPoints(newPoints);
			break;
		case 10:
			trainPlayer.setFitnessPoints(newPoints);
			break;
		case 11:
			trainPlayer.setMoraleFinance(newPoints);
			break;
		case 12:
			trainPlayer.setFitnessFinance(newPoints);
			break;
		}

		this.mainCoachPoints = mainCoachPointsMax;
		this.defenderPoints = defenderPointsMax;
		this.midfielderPoints = midfielderPointsMax;
		this.forwardPoints = forwardPointsMax;
		this.goalkeeperPoints = goalkeeperPointsMax;
		//System.out.printf("4: %d\n", mainCoachPointsMax);

		return newPoints;
	}

	private void refreshLabel() {
		labelComposite.setRedraw(false);

		mainCoachLabelVal.setText(String.valueOf(mainCoachPoints));
		goalkeeperLabelVal.setText(String.valueOf(goalkeeperPoints));
		defenderLabelVal.setText(String.valueOf(defenderPoints));
		forwardLabelVal.setText(String.valueOf(forwardPoints));
		midfielderLabelVal.setText(String.valueOf(midfielderPoints));
		clubResLabelVal.setText(String.valueOf(clubResource) + " "
				+ MainWindow.getMessage("Thousands"));

		mainCoachLabelVal.pack();
		goalkeeperLabelVal.pack();
		defenderLabelVal.pack();
		forwardLabelVal.pack();
		midfielderLabelVal.pack();
		pointsGroup.layout();
		clubResLabelVal.pack();
		clubResGroup.pack();
		labelComposite.layout();
		labelComposite.setRedraw(true);
	}

	private class PlayerTableEditListener implements Listener {

		public void handleEvent(Event event) {
			Rectangle clientArea = playersTable.getClientArea();
			Point pt = new Point(event.x, event.y);
			int index = playersTable.getTopIndex();
			while (index < playersTable.getItemCount()) {
				boolean visible = false;
				final TableItem item = playersTable.getItem(index);
				for (int i = 0; i < playersTable.getColumnCount(); i++) {
					Rectangle rect = item.getBounds(i);
					if (rect.contains(pt)
							&& (((i > 3) && (i % 2 == 0)) || (i == playersTable
									.getColumnCount() - 1))) {
						final int column = i;
						final Spinner spin = new Spinner(playersTable, SWT.NONE);
						final Player player = (Player) item.getData();
						final PlayerTraining trainPlayer = findPlayerbyNumber(player
								.getNumber());
						spin.setValues(getPlayerPoints(column, trainPlayer), 0,
								100, 0, 1, 1);
						Listener textListener = new Listener() {
							public void handleEvent(final Event e) {
								int val = -1;
								try {
									val = getIntegerValue(spin.getText());
								} catch (NumberFormatException nfe) {
									val = -1;
								}
								switch (e.type) {
								case SWT.Modify:
									if (val == -1)
										val = 0;
									if (column != 26 && column != 27) {
										val = distributePlayerPoints(player,
												column, val);
										refreshLabel();
									} else {
										if (column == 26) {
											trainPlayer.setMoraleFinance(val);
										}
										if (column == 27) {
											trainPlayer.setFitnessFinance(val);
										}
									}
									item.setText(column, getStringValue(val));
									if (val != spin.getSelection()) {
										spin.setSelection(val);
									}
									playersTable.setRedraw(false);
									playersTable.getColumn(column).pack();
									playersTable.setRedraw(true);
									
									break;
								case SWT.FocusOut:
									if (val == -1)
										val = 0;
									if (column != 26 && column != 27) {
										val = distributePlayerPoints(player,
												column, val);
										refreshLabel();
									} else {
										if (column == 26) {
											trainPlayer.setMoraleFinance(val);
										}
										if (column == 27) {
											trainPlayer.setFitnessFinance(val);
										}
									}
									item.setText(column, getStringValue(val));
									playersTable.setRedraw(false);
									playersTable.getColumn(column).pack();
									playersTable.setRedraw(true);
									if (val != spin.getSelection()) {
										spin.setSelection(val);
									}
									
									spin.dispose();
									break;
								case SWT.Verify:
									String string = e.text;
									char[] chars = new char[string.length()];
									string.getChars(0, chars.length, chars, 0);
									for (int i = 0; i < chars.length; i++) {
										if (!('0' <= chars[i] && chars[i] <= '9')) {
											e.doit = false;
											return;
										}
									}
								}
							}
						};
						spin.addListener(SWT.FocusOut, textListener);
						spin.addListener(SWT.Modify, textListener);
						spin.addListener(SWT.Verify, textListener);
						spin.setTextLimit(5);
						editor.setEditor(spin, item, i);
						spin.setSelection(getIntegerValue(item.getText(i)));
						spin.setFocus();
						return;
					}
					if (!visible && rect.intersects(clientArea)) {
						visible = true;
					}
				}
				if (!visible)
					return;
				index++;
			}
		}

	}

	private class ScoutRestListener implements SelectionListener {

		public void widgetDefaultSelected(SelectionEvent e) {
			if (trainInstance != null)
				trainInstance.setScouting(scoutRestButton.getSelection());
		}

		public void widgetSelected(SelectionEvent e) {
			if (trainInstance != null)
				trainInstance.setScouting(scoutRestButton.getSelection());
		}

	}

	private class ScoutTalentListener implements SelectionListener {

		public void widgetSelected(SelectionEvent e) {
			if (trainInstance != null)
				trainInstance.setMinTalent(Integer.valueOf(scoutTalentSpinner
						.getText()));
		}

		public void widgetDefaultSelected(SelectionEvent e) {
			if (trainInstance != null)
				trainInstance.setMinTalent(Integer.valueOf(scoutTalentSpinner
						.getText()));
		}
	}

	public void updatePassword(String password) {
		this.password = password;
	}

	public void updateDaysOfRest(int daysOfRest) {
		for (int i = 0; i < playersTable.getItemCount(); i++) {
			TableItem item = playersTable.getItem(i);
			Player player = (Player) item.getData();
			item.setText(23, String.valueOf(player.getFitness(daysOfRest)));
		}
		playersTable.setRedraw(false);
		playersTable.getColumn(23).pack();
		playersTable.setRedraw(true);
	}

	private int getPlayerPoints(int column, PlayerTraining trainPlayer) {
		int ret = 0;
		int skill = (column - 3) / 2;

		switch (skill) {
		case 0:
			ret = trainPlayer.getShootingPoints();
			break;
		case 1:
			ret = trainPlayer.getPassingPoints();
			break;
		case 2:
			ret = trainPlayer.getCrossPoints();
			break;
		case 3:
			ret = trainPlayer.getDribblingPoints();
			break;
		case 4:
			ret = trainPlayer.getTacklingPoints();
			break;
		case 5:
			ret = trainPlayer.getSpeedPoints();
			break;
		case 6:
			ret = trainPlayer.getStaminaPoints();
			break;
		case 7:
			ret = trainPlayer.getHeadingPoints();
			break;
		case 8:
			ret = trainPlayer.getReflexesPoints();
			break;
		case 9:
			ret = trainPlayer.getHandlingPoints();
			break;
		case 10:
			ret = trainPlayer.getFitnessPoints();
			break;
		case 11:
			ret = trainPlayer.getMoraleFinance();
			break;
		}
		return ret;
	}

	public void updateMessages() {
		int cnt = 0;

		mainItem.setText(MainWindow.getMessage("trainingTabName"));

		playersTable.getColumn(cnt++).setText(
				MainWindow.getMessage("PlayerNumber"));
		playersTable.getColumn(cnt++).setText(
				MainWindow.getMessage("PlayerPosition"));
		playersTable.getColumn(cnt++)
				.setText(MainWindow.getMessage("PlayerName"));
		playersTable.getColumn(cnt++)
				.setText(MainWindow.getMessage("PTshooting"));
		cnt++;
		playersTable.getColumn(cnt++).setText(MainWindow.getMessage("PTpassing"));
		cnt++;
		playersTable.getColumn(cnt++).setText(MainWindow.getMessage("PTcross"));
		cnt++;
		playersTable.getColumn(cnt++).setText(
				MainWindow.getMessage("PTdribbling"));
		cnt++;
		playersTable.getColumn(cnt++)
				.setText(MainWindow.getMessage("PTtackling"));
		cnt++;
		playersTable.getColumn(cnt++).setText(MainWindow.getMessage("PTspeed"));
		cnt++;
		playersTable.getColumn(cnt++).setText(MainWindow.getMessage("PTstamina"));
		cnt++;
		playersTable.getColumn(cnt++).setText(MainWindow.getMessage("PTheading"));
		cnt++;
		playersTable.getColumn(cnt++)
				.setText(MainWindow.getMessage("PTreflexes"));
		cnt++;
		playersTable.getColumn(cnt++)
				.setText(MainWindow.getMessage("PThandling"));
		cnt++;
		playersTable.getColumn(cnt++).setText(
				MainWindow.getMessage("PlayerFitness"));
		cnt++;
		playersTable.getColumn(cnt++).setText(MainWindow.getMessage("PTmorale"));
		playersTable.getColumn(cnt++).setText(
				MainWindow.getMessage("PTmoraleFinance"));
		playersTable.getColumn(cnt++).setText(
				MainWindow.getMessage("PTfitnessFinance"));

		pointsGroup.setText(MainWindow.getMessage("PTPointsGroup"));
		pointsGroup.getParent().layout();
		mainCoachLabelName.setText(MainWindow.getMessage("PTMainCoachPoints")
				+ ":");
		mainCoachLabelName.getParent().layout();
		goalkeeperLabelName.setText(MainWindow.getMessage("PTGoalkeeperPoints")
				+ ":");
		goalkeeperLabelName.getParent().layout();
		defenderLabelName.setText(MainWindow.getMessage("PTDefenderPoints")
				+ ":");
		defenderLabelName.getParent().layout();
		midfielderLabelName.setText(MainWindow.getMessage("PTMidfielderPoints")
				+ ":");
		midfielderLabelName.getParent().layout();
		forwardLabelName
				.setText(MainWindow.getMessage("PTForwardPoints") + ":");
		forwardLabelName.getParent().layout();

		scoutGroup.setText(MainWindow.getMessage("PTScoutGroup"));
		scoutGroup.getParent().layout();
		scoutRestLabel.setText(MainWindow.getMessage("PTScoutVacation"));
		scoutRestLabel.getParent().layout();
		clubResGroup.setText(MainWindow.getMessage("PTClubResGroup"));
		clubResGroup.getParent().layout();
		scoutTalent.setText(MainWindow.getMessage("PTScoutTalent"));
		scoutTalent.getParent().layout();

		clubResLabelVal.setText(String.valueOf(clubResource) + " "
				+ MainWindow.getMessage("Thousands"));

		openButton.setText(MainWindow.getMessage("global.open"));
		saveButton.setText(MainWindow.getMessage("global.save"));
		daysOfRestComposite.updateMessages();
		daysOfRestComposite.getParent().layout();
		redraw();
	}
}
