package com.fa13.build.view;

import java.util.*;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.dnd.*;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.*;

import com.fa13.build.model.*;
import com.fa13.build.model.PlayerPosition.FreekicksAction;
import com.fa13.build.model.PlayerPosition.PassingStyle;
import com.fa13.build.model.TeamTactics.Hardness;

public class GameFormEditView implements TeamUIItem, UIItem {

	Composite parentInstance;
	Composite leftPanel;
	ScrolledComposite rightPanelScrl;
	Composite rightPanel;
	Table playersTable;
	Table matchPlayersTable;
	Table matchPlayersTable2;
	boolean homeBonus;
	GameForm gameFormInstance;
	Combo schemeCombo;
	boolean isSchemeUpdate = true;
	Button deleteScheme;
	static Color tableColors[] = null;
	static Font tableFonts[] = null;
	static Image yellowCards[] = null;
	static Image redCards[] = null;
	int daysOfRest;
	TableEditor matchPlayersTableEditor;
	static Map<String, Image> flags = null;
	SpecialRoleEditor specialRoleEditor;
	DaysOfRestComposite daysOfRestComposite;
	Button homeBonusButton;
	Label homeBonusLabel;

	static Listener paintListener;
	static String[] titles;
	static Map<String, PlayerPosition[]> schemesTemplates;

	public GameFormEditView(Composite parent, GameForm gameForm) {
		if (schemesTemplates == null) {
			schemesTemplates = new HashMap<String, PlayerPosition[]>();
			PlayerPosition scheme[] = new PlayerPosition[11];
			scheme[0] = new PlayerPosition(0, 0, 0, 0, 0, 0, 0, 0, 0, false,
					FreekicksAction.FK_NO, false, false, 0, false, false,
					false, Hardness.HR_DEFAULT, PassingStyle.PS_BOTH, true);
			scheme[1] = new PlayerPosition(90, 105, 315, 105, 10, 90, 0, 0, 0,
					false, FreekicksAction.FK_NO, false, false, 0, false,
					false, false, Hardness.HR_DEFAULT, PassingStyle.PS_BOTH);
			scheme[2] = new PlayerPosition(60, 195, 285, 195, 10, 120, 0, 0, 0,
					false, FreekicksAction.FK_NO, false, false, 0, false,
					false, false, Hardness.HR_DEFAULT, PassingStyle.PS_BOTH);
			scheme[3] = new PlayerPosition(60, 255, 285, 255, 10, 150, 0, 0, 0,
					false, FreekicksAction.FK_NO, false, false, 0, false,
					false, false, Hardness.HR_DEFAULT, PassingStyle.PS_BOTH);
			scheme[4] = new PlayerPosition(90, 345, 315, 345, 10, 180, 0, 0, 0,
					false, FreekicksAction.FK_NO, false, false, 0, false,
					false, false, Hardness.HR_DEFAULT, PassingStyle.PS_BOTH);
			scheme[5] = new PlayerPosition(200, 45, 500, 45, 10, 210, 0, 0, 0,
					false, FreekicksAction.FK_NO, false, false, 0, false,
					false, false, Hardness.HR_DEFAULT, PassingStyle.PS_BOTH);
			scheme[6] = new PlayerPosition(150, 195, 425, 195, 10, 240, 0, 0,
					0, false, FreekicksAction.FK_NO, false, false, 0, false,
					false, false, Hardness.HR_DEFAULT, PassingStyle.PS_BOTH);
			scheme[7] = new PlayerPosition(150, 255, 425, 255, 10, 270, 0, 0,
					0, false, FreekicksAction.FK_NO, false, false, 0, false,
					false, false, Hardness.HR_DEFAULT, PassingStyle.PS_BOTH);
			scheme[8] = new PlayerPosition(200, 405, 500, 405, 10, 300, 0, 0,
					0, false, FreekicksAction.FK_NO, false, false, 0, false,
					false, false, Hardness.HR_DEFAULT, PassingStyle.PS_BOTH);
			scheme[9] = new PlayerPosition(290, 185, 540, 185, 10, 330, 0, 0,
					0, false, FreekicksAction.FK_NO, false, false, 0, false,
					false, false, Hardness.HR_DEFAULT, PassingStyle.PS_BOTH);
			scheme[10] = new PlayerPosition(290, 265, 540, 265, 10, 360, 0, 0,
					0, false, FreekicksAction.FK_NO, false, false, 0, false,
					false, false, Hardness.HR_DEFAULT, PassingStyle.PS_BOTH);
			schemesTemplates.put("4-4-2", scheme);

			scheme = new PlayerPosition[11];
			scheme[0] = new PlayerPosition(0, 0, 0, 0, 0, 0, 0, 0, 0, false,
					FreekicksAction.FK_NO, false, false, 0, false, false,
					false, Hardness.HR_DEFAULT, PassingStyle.PS_BOTH, true);
			scheme[1] = new PlayerPosition(90, 125, 315, 125, 10, 90, 0, 0, 0,
					false, FreekicksAction.FK_NO, false, false, 0, false,
					false, false, Hardness.HR_DEFAULT, PassingStyle.PS_BOTH);
			scheme[2] = new PlayerPosition(60, 225, 285, 225, 10, 120, 0, 0, 0,
					false, FreekicksAction.FK_NO, false, false, 0, false,
					false, false, Hardness.HR_DEFAULT, PassingStyle.PS_BOTH);
			scheme[3] = new PlayerPosition(90, 325, 315, 325, 10, 150, 0, 0, 0,
					false, FreekicksAction.FK_NO, false, false, 0, false,
					false, false, Hardness.HR_DEFAULT, PassingStyle.PS_BOTH);
			scheme[4] = new PlayerPosition(200, 45, 500, 45, 10, 180, 0, 0, 0,
					false, FreekicksAction.FK_NO, false, false, 0, false,
					false, false, Hardness.HR_DEFAULT, PassingStyle.PS_BOTH);
			scheme[5] = new PlayerPosition(150, 195, 425, 195, 10, 210, 0, 0,
					0, false, FreekicksAction.FK_NO, false, false, 0, false,
					false, false, Hardness.HR_DEFAULT, PassingStyle.PS_BOTH);
			scheme[6] = new PlayerPosition(150, 255, 425, 255, 10, 240, 0, 0,
					0, false, FreekicksAction.FK_NO, false, false, 0, false,
					false, false, Hardness.HR_DEFAULT, PassingStyle.PS_BOTH);
			scheme[7] = new PlayerPosition(200, 405, 500, 405, 10, 270, 0, 0,
					0, false, FreekicksAction.FK_NO, false, false, 0, false,
					false, false, Hardness.HR_DEFAULT, PassingStyle.PS_BOTH);
			scheme[8] = new PlayerPosition(295, 125, 545, 125, 10, 300, 0, 0,
					0, false, FreekicksAction.FK_NO, false, false, 0, false,
					false, false, Hardness.HR_DEFAULT, PassingStyle.PS_BOTH);
			scheme[9] = new PlayerPosition(280, 225, 530, 225, 10, 330, 0, 0,
					0, false, FreekicksAction.FK_NO, false, false, 0, false,
					false, false, Hardness.HR_DEFAULT, PassingStyle.PS_BOTH);
			scheme[10] = new PlayerPosition(295, 325, 545, 325, 10, 360, 0, 0,
					0, false, FreekicksAction.FK_NO, false, false, 0, false,
					false, false, Hardness.HR_DEFAULT, PassingStyle.PS_BOTH);

			schemesTemplates.put("3-4-3", scheme);
		}
		if (tableColors == null) {
			tableColors = new Color[11];
			tableColors[0] = new Color(parent.getDisplay(), 0, 0, 0);
			tableColors[1] = new Color(parent.getDisplay(), 0, 0, 192);
			tableColors[2] = new Color(parent.getDisplay(), 0, 192, 0);
			tableColors[3] = new Color(parent.getDisplay(), 0, 0, 0);
			tableColors[4] = new Color(parent.getDisplay(), 0, 0, 192);
			tableColors[5] = new Color(parent.getDisplay(), 0, 192, 0);
			tableColors[6] = new Color(parent.getDisplay(), 50, 50, 50);
			tableColors[7] = new Color(parent.getDisplay(), 50, 50, 128);
			tableColors[8] = new Color(parent.getDisplay(), 50, 128, 50);
			tableColors[9] = new Color(parent.getDisplay(), 255, 0, 0);
			tableColors[10] = new Color(parent.getDisplay(), 250, 250, 0);
		}
		if (tableFonts == null) {
			tableFonts = new Font[11];
			FontData fontData = parent.getFont().getFontData()[0];
			tableFonts[0] = new Font(parent.getDisplay(), fontData);
			tableFonts[1] = new Font(parent.getDisplay(), fontData);
			tableFonts[2] = new Font(parent.getDisplay(), fontData);
			fontData.setStyle(SWT.BOLD);
			tableFonts[3] = new Font(parent.getDisplay(), fontData);
			tableFonts[4] = new Font(parent.getDisplay(), fontData);
			tableFonts[5] = new Font(parent.getDisplay(), fontData);
			
			fontData.setStyle(SWT.BOLD | SWT.ITALIC);
			tableFonts[6] = new Font(parent.getDisplay(), fontData);
			tableFonts[7] = new Font(parent.getDisplay(), fontData);
			tableFonts[8] = new Font(parent.getDisplay(), fontData);
			
			fontData.setStyle(SWT.ITALIC);
			tableFonts[9] = new Font(parent.getDisplay(), fontData);
			
			fontData.setHeight(9);
			fontData.setStyle(SWT.BOLD | SWT.ITALIC);
			tableFonts[10] = new Font(parent.getDisplay(), fontData);			
		}

		this.gameFormInstance = gameForm;
		parentInstance = parent;
		parent.setLayout(new FormLayout());

		leftPanel = new Composite(parent, SWT.NONE);

		leftPanel.setLayout(new FillLayout());

		rightPanelScrl = new ScrolledComposite(parent, SWT.H_SCROLL
				| SWT.V_SCROLL);
		rightPanelScrl.setExpandHorizontal(false);
		rightPanelScrl.setExpandVertical(true);
		rightPanel = new Composite(rightPanelScrl, SWT.NONE);
		rightPanelScrl.setContent(rightPanel);

		FormData data = new FormData();

		data.left = new FormAttachment(0, 0);
		data.top = new FormAttachment(0, 0);
		data.bottom = new FormAttachment(100, 0);
		data.right = new FormAttachment(rightPanelScrl, -5);
		leftPanel.setLayoutData(data);

		data = new FormData();
		data.left = new FormAttachment(100, 0);
		data.top = new FormAttachment(0, 0);
		data.bottom = new FormAttachment(100, 0);
		data.right = new FormAttachment(100, 0);
		rightPanelScrl.setLayoutData(data);

		data = new FormData();
		rightPanel.setLayoutData(data);
		rightPanel.setLayout(new FormLayout());

		playersTable = new Table(leftPanel, SWT.BORDER | SWT.FULL_SELECTION);
		playersTable.setHeaderVisible(true);
		playersTable.setLinesVisible(true);
		playersTable.setHeaderVisible(true);

		String[] defaultTitles = { MainWindow.getMessage("PlayerNumber"),
				MainWindow.getMessage("PlayerPosition"),
				MainWindow.getMessage("PlayerName"),
				MainWindow.getMessage("PlayerCountry"),
				MainWindow.getMessage("PlayerAge"),
				MainWindow.getMessage("PlayerTalant"),
				MainWindow.getMessage("PlayerExperience"),
				MainWindow.getMessage("PlayerFitness"),
				MainWindow.getMessage("PlayerMorale"),
				MainWindow.getMessage("PlayerStrength"),
				MainWindow.getMessage("PlayerRealStrength"),
				MainWindow.getMessage("PlayerHealth"),
				MainWindow.getMessage("PlayerRest"),
				MainWindow.getMessage("PlayerCards") };

		titles = defaultTitles;

		for (int i = 0; i < titles.length; i++) {
			TableColumn column = new TableColumn(playersTable, SWT.NONE);
			column.setText("1234");
			column.pack();
			column.setText(titles[i]);
			column.setResizable(false);
		}

		playersTable.getColumn(3).pack();

		schemeCombo = new Combo(rightPanel, SWT.DROP_DOWN | SWT.READ_ONLY);
		for (Iterator<Entry<String, PlayerPosition[]>> iterator = schemesTemplates
				.entrySet().iterator(); iterator.hasNext();) {
			Entry<String, PlayerPosition[]> entry = (Entry<String, PlayerPosition[]>) iterator
					.next();
			schemeCombo.add(entry.getKey());
			schemeCombo.setData(entry.getKey(), entry.getValue());
		}

		schemeCombo.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				Combo combo = (Combo) e.widget;
				if (isSchemeUpdate) {
					applyScheme(gameFormInstance.getFirstTeam(),
							(PlayerPosition[]) combo.getData(combo.getText()));
				}
				gameFormInstance.setDefault(combo.getSelectionIndex());
				redrawForm();
			}
		});
		schemeCombo.select(0);
		deleteScheme = new Button(rightPanel, SWT.PUSH);

		int scrollWidth = rightPanelScrl.getVerticalBar().getSize().x;
		data = new FormData();
		data.left = new FormAttachment(0, scrollWidth);
		data.right = new FormAttachment(deleteScheme, -scrollWidth);
		data.top = new FormAttachment(0, 1);
		schemeCombo.setLayoutData(data);

		deleteScheme.setText(MainWindow.getMessage("DeleteSchemeButton"));
		data = new FormData();
		data.left = new FormAttachment(deleteScheme, -deleteScheme.computeSize(
				SWT.DEFAULT, SWT.DEFAULT).x);
		data.right = new FormAttachment(100, 0);
		data.top = new FormAttachment(0, 0);
		deleteScheme.setLayoutData(data);

		matchPlayersTable = new Table(rightPanel, SWT.BORDER
				| SWT.FULL_SELECTION);
		matchPlayersTable.setHeaderVisible(true);
		matchPlayersTable.setLinesVisible(true);
		matchPlayersTable.setHeaderVisible(true);

		String[] titlesMatch = { MainWindow.getMessage("PlayerNumber"),
				MainWindow.getMessage("PlayerPosition"),
				MainWindow.getMessage("PlayerName"),
				MainWindow.getMessage("Standard") };

		String[] titlesBench = { MainWindow.getMessage("PlayerNumber"),
				MainWindow.getMessage("PlayerPosition"),
				MainWindow.getMessage("PlayerName"), "   " };

		for (int i = 0; i < titlesMatch.length; i++) {
			TableColumn column = new TableColumn(matchPlayersTable, SWT.NONE);
			column.setText("1234");
			column.pack();
			column.setText(titlesMatch[i]);
			column.setResizable(false);
		}
		matchPlayersTable.setItemCount(11);

		for (int i = 0; i < matchPlayersTable.getColumnCount(); i++) {
			matchPlayersTable.getColumn(i).pack();
		}

		data = new FormData();
		data.left = new FormAttachment(0, 0);
		Point size = matchPlayersTable.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		data.right = new FormAttachment(matchPlayersTable, size.x);
		data.top = new FormAttachment(deleteScheme, 4);
		matchPlayersTable.setLayoutData(data);

		matchPlayersTable2 = new Table(rightPanel, SWT.BORDER
				| SWT.FULL_SELECTION);
		matchPlayersTable2.setHeaderVisible(true);
		matchPlayersTable2.setLinesVisible(true);
		matchPlayersTable2.setHeaderVisible(true);

		for (int i = 0; i < titlesBench.length; i++) {
			TableColumn column = new TableColumn(matchPlayersTable2, SWT.NONE);
			column.setText("1234");
			column.pack();
			column.setText(titlesBench[i]);
			column.setResizable(false);
		}
		matchPlayersTable2.setItemCount(7);

		for (int i = 0; i < matchPlayersTable2.getColumnCount(); i++) {
			matchPlayersTable2.getColumn(i).pack();
		}

		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0);
		data.top = new FormAttachment(matchPlayersTable, 0);
		matchPlayersTable2.setLayoutData(data);

		setDragDropSource(playersTable);
		setDragDropSource(matchPlayersTable);
		setDragDropSource(matchPlayersTable2);
		setDragDropTarget(matchPlayersTable);
		setDragDropTarget(matchPlayersTable2);

		daysOfRestComposite = new DaysOfRestComposite(rightPanel, SWT.NONE,
				this);
		data = new FormData();
		data.left = new FormAttachment(0, rightPanelScrl.getVerticalBar()
				.getSize().x);
		data.right = new FormAttachment(100, -rightPanelScrl.getVerticalBar()
				.getSize().x);
		data.top = new FormAttachment(matchPlayersTable2, 10);
		daysOfRestComposite.setLayoutData(data);
		
		homeBonusButton = new Button(rightPanel, SWT.CHECK);
		data = new FormData();
		data.left = new FormAttachment(0, rightPanelScrl.getVerticalBar()
				.getSize().x);
		data.top = new FormAttachment(daysOfRestComposite, 10);
		homeBonusButton.setLayoutData(data);
		
		homeBonusButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				widgetDefaultSelected(e);				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				homeBonus = homeBonusButton.getSelection();
				redraw();
			}
		});
		
		homeBonusLabel = new Label(rightPanel, SWT.NONE);
		data = new FormData();
		data.left = new FormAttachment(homeBonusButton, 5);
		data.top = new FormAttachment(daysOfRestComposite, 10);
		homeBonusLabel.setLayoutData(data);
		homeBonusLabel.setText(MainWindow.getMessage("homeBonus"));
		
		Map<String, Image> tmp = new HashMap<String, Image>();
		Set<Map.Entry<String, String>> nations = Player.nationalities
				.entrySet();

		if (flags == null) {
			for (Iterator<Map.Entry<String, String>> iterator = nations
					.iterator(); iterator.hasNext();) {
				Map.Entry<String, String> currEntry = iterator.next();
				String curr = currEntry.getValue();
				Image img = new Image(parent.getDisplay(), this.getClass()
						.getResourceAsStream(
								"/com/fa13/build/resources/flags/" + curr
										+ ".png"));
				tmp.put(curr, img);
			}
			flags = Collections.unmodifiableMap(tmp);
		}

		final Image img = new Image(parent.getDisplay(), this.getClass()
				.getResourceAsStream("/com/fa13/build/resources/flags/not.png"));
		if (yellowCards == null) {
			yellowCards = new Image[3];
			for (int i = 0; i < 3; i++) {
				yellowCards[i] = new Image(parent.getDisplay(), this.getClass()
						.getResourceAsStream(
								"/com/fa13/build/resources/cards/yellow"
										+ String.valueOf(i) + ".png"));
			}
		}

		if (redCards == null) {
			redCards = new Image[5];
			for (int i = 0; i < 5; i++) {
				redCards[i] = new Image(parent.getDisplay(), this.getClass()
						.getResourceAsStream(
								"/com/fa13/build/resources/cards/red"
										+ String.valueOf(i) + ".png"));
			}
		}
		paintListener = new Listener() {
			public void handleEvent(Event event) {
				switch (event.type) {
				case SWT.MeasureItem: {
					if (event.index == 3) {
						Rectangle rect = img.getBounds();
						event.width = rect.width;
						event.height = Math.max(event.height, rect.height);
					} else if (event.index == 13) {
						Rectangle rect = yellowCards[0].getBounds();
						event.width = rect.width;
						event.height = Math.max(event.height, rect.height);
					} else {
						TableItem item = (TableItem) event.item;
						String text = item.getText(event.index);
						int size = text.length();
						text = "";
						for (int i = 0; i < size + 1; i++) {
							text += "A";
						}
						Point extent = event.gc.stringExtent(text);
						event.width = Math.max(event.width, extent.x + 6);
					}
					break;
				}
				case SWT.PaintItem: {
					if (event.index == 3) {
						int x = event.x;
						Rectangle rect = img.getBounds();
						int yoffset = Math.max(0,
								(event.height - rect.height) / 2);
						Image flag = (Image) event.item.getData("flag");
						if (flag != null) {
							event.gc.drawImage(flag,
									x
											+ (playersTable.getColumn(3)
													.getWidth() - rect.width)
											/ 2, event.y + yoffset);
						}
					}
					if (event.index == 13) {
						int x = event.x;
						Rectangle rect = yellowCards[0].getBounds();
						int yoffset = Math.max(0,
								(event.height - rect.height) / 2);
						Image card = (Image) event.item.getData("card");
						if (card != null) {
							event.gc.drawImage(card,
									x + (playersTable.getColumn(13).getWidth() - rect.width) / 2,
									event.y + yoffset);
						}
					}
					break;
				}
				}
			}
		};

		playersTable.addListener(SWT.PaintItem, paintListener);
		playersTable.addListener(SWT.MeasureItem, paintListener);

		deleteScheme.setEnabled(false);

		matchPlayersTableEditor = new TableEditor(matchPlayersTable);
		matchPlayersTableEditor.horizontalAlignment = SWT.LEFT;
		matchPlayersTableEditor.grabHorizontal = true;
		matchPlayersTableEditor.minimumWidth = 35;
		matchPlayersTable.addListener(SWT.MouseDown,
				new MatchPlayersTableEditListener());

		specialRoleEditor = new SpecialRoleEditor(matchPlayersTable.getDisplay(), 
				matchPlayersTable.getShell(), null, null, gameFormInstance, true);

		updateAll();
		redrawForm();
	}

	public void updateAll() {
		redraw();
	}

	public void updateDaysOfRest(int newVal) {
		this.daysOfRest = newVal;
		redrawRest();
	}

	public int getPlayerTypeGameForm(int playerNumber) {
		PlayerPosition playerPositions[] = gameFormInstance.getFirstTeam();
		for (PlayerPosition playerPosition: playerPositions) {
			if (playerPosition.getNumber() == playerNumber) {
				Player player = MainWindow.getAllInstance().getCurrentTeam().getPlayerByNumber(playerNumber);
				if (player.getFitness(daysOfRest) < 70) {
					return 5;
				}
				if (player.getFitness(daysOfRest) < 90) {
					return 4;
				}
				return 3;
			}
		}
		int playerSubstitutions[] = gameFormInstance.getBench();
		for (int i = 0; i < playerSubstitutions.length; i++) {
			if (playerSubstitutions[i] == playerNumber) {
				Player player = MainWindow.getAllInstance().getCurrentTeam().getPlayerByNumber(playerNumber);
				if (player.getFitness(daysOfRest) < 70) {
					return 8;
				}
				if (player.getFitness(daysOfRest) < 90) {
					return 7;
				}
				return 6;
			}
		}
		Player player = MainWindow.getAllInstance().getCurrentTeam()
				.getPlayerByNumber(playerNumber);
		
		if (player.getDisqualification() > 0) {
			return 9;
		}
		if (player.getFitness(daysOfRest) < 70) {
			return 2;
		}
		if (player.getFitness(daysOfRest) < 90) {
			return 1;
		}
		return 0;
	}

	public void redraw() {
		if (MainWindow.getAllInstance() != null) {
			playersTable.setRedraw(false);
			Team curTeam = MainWindow.getAllInstance().getCurrentTeam();
			List<Player> curPlayers = curTeam.getPlayers();
			playersTable.removeAll();
			playersTable.setItemCount(curPlayers.size());
			int i = 0;

			for (Player player: curPlayers) {
				TableItem item = playersTable.getItem(i);
				int index = 0;
				int type = getPlayerTypeGameForm(player.getNumber());
				item.setForeground(tableColors[type]);
				item.setFont(tableFonts[type]);
				item.setText(index++, String.valueOf(player.getNumber()));
				item.setText(index++, player.getPosition().toString());
				item.setText(index++, player.getName());
				item.setText(index++, "");
				item.setData("flag", flags.get(player.getNationalityCode()));
				item.setText(index++, String.valueOf(player.getAge()));
				item.setText(index++, String.valueOf(player.getTalent()));
				item.setText(index++, String.valueOf(player.getExperience()));
				item.setText(index++, String.valueOf(player
						.getFitness(daysOfRest)));
				item.setText(index++, String.valueOf(player.getMorale()));
				item.setText(index++, String.valueOf(player.getStrength()));
				double RS = player.getRealStrength(homeBonus, daysOfRest);
				if (player.getDisqualification() == 0) {
					item.setText(index++, String.format("%.1f", RS));
				} else {
					item.setText(index++, MainWindow.getMessage("PlayerDisqualified"));
				}
				item.setText(index++, String.valueOf(player.getHealth()));
				String rest = "";
				int restDays = player.getRest() + daysOfRest;
				rest = String.valueOf(restDays);
				item.setText(index++, rest);
				if (player.getDisqualification() > 0) {
					item.setData("card",redCards[player.getDisqualification()]);
				} else if (player.getYellowCards() % 3 != 0) {
					item.setData("card",yellowCards[player.getYellowCards() % 3]);
				}
				
				i++;
			}

			for (i = 0; i < titles.length; i++) {
				TableColumn column = playersTable.getColumn(i);
				column.setText(titles[i]);
				column.pack();

				column.setResizable(false);
			}
			playersTable.getParent().layout();
			playersTable.setRedraw(true);
		} else {
			playersTable.setItemCount(0);
		}
		redrawForm();
	}

	public void redrawRest() {
		if (MainWindow.getAllInstance() != null) {
			playersTable.setRedraw(false);
			Team curTeam = MainWindow.getAllInstance().getCurrentTeam();
			List<Player> curPlayers = curTeam.getPlayers();
			int i = 0;

			for (Player player: curPlayers) {
				TableItem item = playersTable.getItem(i);
				int index = 0;

				int type = getPlayerTypeGameForm(player.getNumber());
				item.setForeground(tableColors[type]);
				item.setFont(tableFonts[type]);

				index += 7;
				item.setText(index++, String.valueOf(player
						.getFitness(daysOfRest)));
				index += 2;
				double RS = player.getRealStrength(homeBonus, daysOfRest);
				if (player.getDisqualification() == 0) {
					item.setText(index++, String.format("%.1f", RS));
				} else {
					item.setText(index++, MainWindow.getMessage("PlayerDisqualified"));
				}
				i++;
				index += 1;
				String rest = "";
				int restDays = player.getRest() + daysOfRest;
				rest = String.valueOf(restDays);
				item.setText(index++, rest);
			}

			int index = 0;
			index += 7;
			playersTable.getColumn(index++).pack();
			index += 2;
			playersTable.getColumn(index++).pack();
			index += 1;
			playersTable.getColumn(index++).pack();

			playersTable.setRedraw(true);
		}

	}

	public void setDragDropSource(final Table table) {
		Transfer[] types = new Transfer[] { TextTransfer.getInstance() };
		int operations = DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_DEFAULT;
		final DragSource source = new DragSource(table, operations);
		source.setTransfer(types);
		source.addDragListener(new DragSourceListener() {
			public void dragStart(DragSourceEvent event) {
				TableItem items[] = table.getSelection();

				if (items.length <= 0) {
					event.doit = false;
					return;
				}
				if (items[0].getText().length() == 0) {
					event.doit = false;
					return;
				}
				int number = Integer.valueOf(items[0].getText());
				if (MainWindow.getAllInstance().getCurrentTeam().getPlayerByNumber(number)
						.getDisqualification() > 0) {
					event.doit = false;
					return;
				}
			}

			public void dragSetData(DragSourceEvent event) {
				TableItem items[] = table.getSelection();
				StringBuffer stringBuffer = new StringBuffer();
				for (int i = 0; i < items.length; i++) {
					for (int j = 0; j < table.getColumnCount(); j++) {
						stringBuffer.append(items[i].getText(j));
						stringBuffer.append("/");
					}
					stringBuffer.append("\n");
				}
				event.data = stringBuffer.toString();
			}

			public void dragFinished(DragSourceEvent event) {

			}
		});
	}

	public void setDragDropTarget(final Table table) {
		int operations = DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_DEFAULT;
		DropTarget target = new DropTarget(table, operations);

		final TextTransfer textTransfer = TextTransfer.getInstance();
		Transfer types[] = new Transfer[] { textTransfer };
		target.setTransfer(types);

		target.addDropListener(new DropTargetAdapter() {

			public void drop(DropTargetEvent event) {
				if (textTransfer.isSupportedType(event.currentDataType)) {
					TableItem item = null;
					if (event.item instanceof TableItem) {
						String text = (String) event.data;
						item = (TableItem) event.item;
						if ((Integer) item.getData("type") == 0) {
							setPlayer(item, text);
						}
						if ((Integer) item.getData("type") == 1) {
							setPlayerSubstitution(item, text);
						}
					}
				}
			}
		});

	}

	public void setPlayer(TableItem item, String playerData) {
		if (playerData == null)
			return;
		String playerItems[] = playerData.split("/");
		Integer number = (Integer) item.getData("number");
		int playerNumber = Integer.valueOf(playerItems[0]);
		PlayerPosition playerPositions[] = gameFormInstance.getFirstTeam();
		for (int i = 0; i < playerPositions.length; i++) {
			if (playerPositions[i].getNumber() == playerNumber) {
				playerPositions[i].reset();
			}
		}
		int playerSubstitutions[] = gameFormInstance.getBench();
		for (int i = 0; i < playerSubstitutions.length; i++) {
			if (playerSubstitutions[i] == playerNumber) {
				playerSubstitutions[i] = 0;
			}
		}
		if (number < playerPositions.length && number >= 0) {
			playerPositions[number].setNumber(playerNumber);
		}
		redraw();
	}

	public void setPlayerSubstitution(TableItem item, String playerData) {
		if (playerData == null)
			return;
		String playerItems[] = playerData.split("/");
		Integer number = (Integer) item.getData("number");
		int playerNumber = Integer.valueOf(playerItems[0]);
		PlayerPosition playerPositions[] = gameFormInstance.getFirstTeam();
		for (int i = 0; i < playerPositions.length; i++) {
			if (playerPositions[i].getNumber() == playerNumber) {
				playerPositions[i].reset();
			}
		}
		int playerSubstitutions[] = gameFormInstance.getBench();
		for (int i = 0; i < playerSubstitutions.length; i++) {
			if (playerSubstitutions[i] == playerNumber) {
				playerSubstitutions[i] = 0;
			}
		}
		if (number < playerSubstitutions.length && number >= 0) {
			playerSubstitutions[number] = playerNumber;
		}
		redraw();
	}

	public void redrawForm() {
		if (matchPlayersTable == null || matchPlayersTable2 == null)
			return;
		matchPlayersTable.setRedraw(false);
		matchPlayersTable2.setRedraw(false);
		Team curTeam = null;
		if (MainWindow.getAllInstance() != null) {
			curTeam = MainWindow.getAllInstance().getCurrentTeam();
		}
		PlayerPosition playerPositions[] = gameFormInstance.getFirstTeam();
		int playerSubstitution[] = gameFormInstance.getBench();
		int itemsCount = Math.max(11, playerPositions.length);
		matchPlayersTable.setItemCount(itemsCount);
		matchPlayersTable2.setItemCount(7);
		for (int i = 0; i < itemsCount; i++) {
			TableItem item = matchPlayersTable.getItem(i);
			item.setData("number", new Integer(i));
			item.setData("type", new Integer(0));
		}
		for (int i = 0; i < matchPlayersTable2.getItemCount(); i++) {
			TableItem item = matchPlayersTable2.getItem(i);
			item.setData("number", new Integer(i));
			item.setData("type", new Integer(1));
		}

		for (int i = 0; i < matchPlayersTable.getItemCount(); i++) {
			TableItem item = matchPlayersTable.getItem(i);
			for (int index = 0; index < matchPlayersTable.getColumnCount(); index++)
				item.setText(index, "");
		}
		for (int i = 0; i < matchPlayersTable2.getItemCount(); i++) {
			TableItem item = matchPlayersTable2.getItem(i);
			for (int index = 0; index < matchPlayersTable2.getColumnCount(); index++)
				item.setText(index, "");
		}

		for (int i = 0; i < playerPositions.length; i++) {
			TableItem item = matchPlayersTable.getItem(i);
			int index = 0;
			if (playerPositions[i] != null) {
				Player player = null;
				if (curTeam != null) {
					player = curTeam.getPlayerByNumber(playerPositions[i]
							.getNumber());
				}
				if (player != null) {
					item.setText(index++, String.valueOf(player.getNumber()));
				} else {
					index++;
				}
				item
						.setText(index++, playerPositions[i].getAmplua()
								.toString());
				if (player != null) {
					item.setText(index++, player.getName());
				} else {
					index++;
				}
				item.setText(index++, printSRflags(playerPositions[i]));
			}
		}
		for (int i = 0; i < playerSubstitution.length; i++) {
			TableItem item = matchPlayersTable2.getItem(i);
			int index = 0;
			Player player = null;
			if (curTeam != null) {
				player = curTeam.getPlayerByNumber(playerSubstitution[i]);
			}
			if (player != null) {
				item.setText(index++, String.valueOf(player.getNumber()));
				item.setText(index++, player.getPosition().toString());
				item.setText(index++, player.getName());
			}
		}

		int width = 0;
		for (int i = 0; i < matchPlayersTable.getColumnCount(); i++) {
			int widthColum = 0;
			matchPlayersTable.getColumn(i).pack();
			int dWidth = matchPlayersTable.getColumn(i).getWidth();
			widthColum = dWidth;
			matchPlayersTable2.getColumn(i).pack();
			dWidth = matchPlayersTable2.getColumn(i).getWidth();
			widthColum = Math.max(widthColum, dWidth);
			width += widthColum;
			matchPlayersTable.getColumn(i).setWidth(widthColum);
			matchPlayersTable2.getColumn(i).setWidth(widthColum);
		}

		// TODO : optimize memory
		int heightAll = 0;
		int widthAll = 0;
		FormData data = (FormData) matchPlayersTable.getLayoutData();
		width += matchPlayersTable.getBorderWidth() * 2;
		data.left = new FormAttachment(0, rightPanelScrl.getVerticalBar()
				.getSize().x);
		data.right = new FormAttachment(matchPlayersTable, width);
		int height = matchPlayersTable.getItemCount()
				* matchPlayersTable.getItemHeight();
		height += matchPlayersTable.getHeaderHeight();
		height += matchPlayersTable.getBorderWidth() * 3;
		data.bottom = new FormAttachment(matchPlayersTable, height);
		heightAll += height;
		heightAll += 10;
		heightAll += daysOfRestComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
		heightAll += 10;
		heightAll += Math.max(homeBonusButton.computeSize(SWT.DEFAULT, SWT.DEFAULT).y, 
				homeBonusLabel.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		heightAll += 16;
		widthAll = width;

		data = (FormData) matchPlayersTable2.getLayoutData();
		width -= matchPlayersTable.getBorderWidth() * 2;
		width += matchPlayersTable2.getBorderWidth() * 2;
		data.left = new FormAttachment(0, rightPanelScrl.getVerticalBar()
				.getSize().x);
		data.right = new FormAttachment(matchPlayersTable2, width);
		height = matchPlayersTable2.getItemCount()
				* matchPlayersTable.getItemHeight();
		height += matchPlayersTable2.getHeaderHeight();
		height += matchPlayersTable2.getBorderWidth() * 3;
		data.bottom = new FormAttachment(matchPlayersTable2, height);
		heightAll += height;
		heightAll += schemeCombo.getBorderWidth() * 2;
		heightAll += schemeCombo.getSize().y;
		widthAll = Math.max(widthAll, width);
		matchPlayersTable.getParent().layout();

		data = (FormData) schemeCombo.getLayoutData();
		data.left = new FormAttachment(0, rightPanelScrl.getVerticalBar()
				.getSize().x);
		data.right = new FormAttachment(deleteScheme, -4);

		data = (FormData) deleteScheme.getLayoutData();
		data.right = new FormAttachment(0, rightPanelScrl.getVerticalBar()
				.getSize().x
				+ widthAll);
		data.left = new FormAttachment(deleteScheme, -deleteScheme.computeSize(
				SWT.DEFAULT, SWT.DEFAULT).x);
		
		daysOfRestComposite.layout();
		daysOfRestComposite.getParent().layout();
		
		
		rightPanel.setSize(widthAll, heightAll);
		rightPanelScrl.setExpandHorizontal(true);
		rightPanelScrl.setMinWidth(widthAll);		
		rightPanelScrl.setMinHeight(heightAll);
		
		rightPanelScrl.layout();
		
		data = (FormData) rightPanelScrl.getLayoutData();

		int scrollWidth = rightPanelScrl.computeSize(SWT.DEFAULT, SWT.DEFAULT).x;
		data.left = new FormAttachment(rightPanelScrl, -scrollWidth);
		
		rightPanelScrl.layout();
		rightPanelScrl.getParent().layout();
		
		matchPlayersTable.setRedraw(true);
		matchPlayersTable2.setRedraw(true);
	}

	public static void applyScheme(PlayerPosition[] dstScheme,
			PlayerPosition[] srcScheme) {
		if (dstScheme == null)
			return;
		if (srcScheme == null)
			return;
		for (int i = 0; i < srcScheme.length; i++) {
			if (dstScheme[i] == null)
				dstScheme[i] = new PlayerPosition();
			if (srcScheme[i] == null)
				continue;
			dstScheme[i].setAttackX(srcScheme[i].getAttackX());
			dstScheme[i].setAttackY(srcScheme[i].getAttackY());
			dstScheme[i].setDefenceX(srcScheme[i].getDefenceX());
			dstScheme[i].setDefenceY(srcScheme[i].getDefenceY());
			dstScheme[i].setFreekickX(srcScheme[i].getFreekickX());
			dstScheme[i].setFreekickY(srcScheme[i].getFreekickY());
			dstScheme[i].setGoalkeeper(srcScheme[i].isGoalkeeper());
		}
	}

	public void updateGameForm(GameForm gameForm) {
		gameFormInstance = gameForm;
		isSchemeUpdate = false;
		schemeCombo.select(gameFormInstance.getDefault());
		isSchemeUpdate = true;
		updateAll();
	}

	public void updatePassword(String password) {

	}

	private class MatchPlayersTableEditListener implements Listener {

		public void handleEvent(Event event) {
			Rectangle clientArea = matchPlayersTable.getClientArea();
			Point pt = new Point(event.x, event.y);
			if (event.button == 1) {
				int index = matchPlayersTable.getTopIndex();
				while (index < matchPlayersTable.getItemCount()) {
					boolean visible = false;
					final TableItem item = matchPlayersTable.getItem(index);
					for (int i = 0; i < matchPlayersTable.getColumnCount(); i++) {
						Rectangle rect = item.getBounds(i);
						if (rect.contains(pt)) {
							final int column = i;
							final int row = index;
							final PlayerPosition playerPos = gameFormInstance
									.getFirstTeam()[row];
							if (column == 3) {
								Rectangle rect1 = matchPlayersTable
										.getDisplay().map(item.getParent(),
												null, rect);
								specialRoleEditor
										.updatePlayerPosition(playerPos);
								specialRoleEditor.Show(new Point(rect1.x
										+ rect1.width, rect1.y));
								redraw();
							}

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

	}

	private String printSRflags(PlayerPosition subst) {
		String st = "";
		if (subst.isCaptain())
			st += MainWindow.getMessage("SRCaptainShort");
		if (subst.isCaptainAssistant())
			st += MainWindow.getMessage("SRCaptainShort").toLowerCase();
		if (subst.isDirectFreekick())
			st += MainWindow.getMessage("SRDirectFreekickShort");
		if (subst.isDirectFreekickAssistant())
			st += MainWindow.getMessage("SRDirectFreekickShort").toLowerCase();
		if (subst.isIndirectFreekick())
			st += MainWindow.getMessage("SRIndirectFreekickShort");
		if (subst.isIndirectFreekickAssistant())
			st += MainWindow.getMessage("SRIndirectFreekickShort")
					.toLowerCase();
		if (subst.isPenalty())
			st += MainWindow.getMessage("SRPenaltyShort");
		if (subst.isPenaltyAssistant())
			st += MainWindow.getMessage("SRPenaltyShort").toLowerCase();
		if (subst.isLeftCorner())
			st += MainWindow.getMessage("SRLeftCornerShort");
		if (subst.isLeftCornerAssistant())
			st += MainWindow.getMessage("SRLeftCornerShort").toLowerCase();
		if (subst.isRightCorner())
			st += MainWindow.getMessage("SRRightCornerShort");
		if (subst.isRightCornerAssistant())
			st += MainWindow.getMessage("SRRightCornerShort").toLowerCase();
		if (subst.getPenaltyOrder() > 0)
			st += " " + subst.getPenaltyOrder();
		return st;
	}

	/*private void clearAllSRAflags(PlayerPosition subst) {
		subst.setCaptainAssistant(false);
		subst.setDirectFreekickAssistant(false);
		subst.setIndirectFreekickAssistant(false);
		subst.setPenaltyAssistant(false);
		subst.setLeftCornerAssistant(false);
		subst.setRightCornerAssistant(false);
	}*/

	public void updateMessages() {

		String[] defaultTitles = { MainWindow.getMessage("PlayerNumber"),
				MainWindow.getMessage("PlayerPosition"),
				MainWindow.getMessage("PlayerName"),
				MainWindow.getMessage("PlayerCountry"),
				MainWindow.getMessage("PlayerAge"),
				MainWindow.getMessage("PlayerTalant"),
				MainWindow.getMessage("PlayerExperience"),
				MainWindow.getMessage("PlayerFitness"),
				MainWindow.getMessage("PlayerMorale"),
				MainWindow.getMessage("PlayerStrength"),
				MainWindow.getMessage("PlayerRealStrength"),
				MainWindow.getMessage("PlayerHealth"),
				MainWindow.getMessage("PlayerRest"),
				MainWindow.getMessage("PlayerCards") };

		titles = defaultTitles;
		for (int i = 0; i < defaultTitles.length; i++) {
			playersTable.getColumn(i).setText(defaultTitles[i]);
		}

		deleteScheme.setText(MainWindow.getMessage("DeleteSchemeButton"));

		String[] titlesMatch = { MainWindow.getMessage("PlayerNumber"),
				MainWindow.getMessage("PlayerPosition"),
				MainWindow.getMessage("PlayerName"),
				MainWindow.getMessage("Standard") };

		String[] titlesBench = { MainWindow.getMessage("PlayerNumber"),
				MainWindow.getMessage("PlayerPosition"),
				MainWindow.getMessage("PlayerName"), "   " };

		for (int i = 0; i < titlesMatch.length; i++) {
			matchPlayersTable.getColumn(i).setText(titlesMatch[i]);
			matchPlayersTable2.getColumn(i).setText(titlesBench[i]);
		}

		specialRoleEditor.updateMessages();
		homeBonusLabel.setText(MainWindow.getMessage("homeBonus"));

		redraw();
	}

}
