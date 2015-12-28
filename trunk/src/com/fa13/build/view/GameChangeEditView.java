package com.fa13.build.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.dnd.*;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

import com.fa13.build.model.*;
import com.fa13.build.model.GameForm.SubstitutionPreferences;

public class GameChangeEditView implements UIItem {

	Composite parentInstance;
	Composite leftPanel;
	ScrolledComposite rightPanelScrl;
	Composite rightPanel;
	Table substitutionTable;
	Table firstTeamTable;
	Table benchTeamTable;
	boolean homeBonus;
	GameForm gameFormInstance;
	TableEditor editor;
	TableEditor matchPlayersTableEditor;
	static Color tableColors[] = null;
	static Font tableFonts[] = null;
	Button changeByStrengthButton;
	Button changeByPositionButton;
	SpecialRoleEditor specialRoleEditor;
	SpecialRoleEditor specialRoleEditorSmall;
	
	
	public GameChangeEditView(Composite parent, GameForm gameForm) {
		if (tableColors == null) { 
			tableColors = new Color[3];
			tableColors[0] = new Color(parent.getDisplay(), 0, 0, 0);
			tableColors[1] = new Color(parent.getDisplay(), 0, 0, 0);
			tableColors[2] = new Color(parent.getDisplay(), 50, 50, 50);
		}
		if (tableFonts == null) { 
			tableFonts = new Font[3];
			FontData fontData = parent.getFont().getFontData()[0];
			tableFonts[0] = new Font(parent.getDisplay(), fontData);
			fontData.setStyle(SWT.BOLD);
			tableFonts[1] = new Font(parent.getDisplay(), fontData);
			fontData.setStyle(SWT.ITALIC | SWT.BOLD);
			tableFonts[2] = new Font(parent.getDisplay(), fontData);		
		}
		this.gameFormInstance = gameForm;
		parentInstance = parent;
		parent.setLayout(new FormLayout());

		leftPanel = new Composite(parent, SWT.NONE);
		GridLayout gridLayout = new GridLayout(2, true);
		gridLayout.horizontalSpacing = 0;
		gridLayout.verticalSpacing = 0;
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 0;
		leftPanel.setLayout(gridLayout);
		
		substitutionTable = new Table(leftPanel, SWT.BORDER | SWT.FULL_SELECTION);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.horizontalSpan = 2;
		substitutionTable.setLayoutData(gridData);
		substitutionTable.setHeaderVisible(true);
		substitutionTable.setLinesVisible(true);
		substitutionTable.setHeaderVisible(true);

		String[] titles = { MainWindow.getMessage("ChangeTime"),
				MainWindow.getMessage("MinDifference"),
				MainWindow.getMessage("MaxDifference"),
				MainWindow.getMessage("PlayerNumber"),
				MainWindow.getMessage("OutgoingPlayer"),
				MainWindow.getMessage("PlayerNumber"),
				MainWindow.getMessage("IngoingPlayer"),
				MainWindow.getMessage("Standard") };

		for (int i = 0; i < titles.length; i++) {
			TableColumn column = new TableColumn(substitutionTable, SWT.NONE);
			column.setText("1234");
			column.pack();
			column.setText(titles[i]);
			column.setResizable(false);
		}

		substitutionTable.getColumn(3).pack();
		
		editor = new TableEditor (substitutionTable);
		editor.horizontalAlignment = SWT.LEFT;
		editor.grabHorizontal = true;
		editor.minimumWidth = 35;
		substitutionTable.addListener(SWT.MouseDown, new SubstitutionTableEditListener());
		substitutionTable.addMenuDetectListener(new SubstitutionTableMouseListener());
		substitutionTable.setItemCount(gameForm.getSubstitutions().length);
		for (int i = 0; i < gameForm.getSubstitutions().length; i++) {
			gameForm.getSubstitutions()[i] = new PlayerSubstitution();
		}

		changeByStrengthButton = new Button(leftPanel, SWT.RADIO);
		gridData= new GridData(SWT.BEGINNING, SWT.END, false, false);
		changeByStrengthButton.setLayoutData(gridData);
		changeByStrengthButton.setText(MainWindow.getMessage("ForcedChangeByStrength"));
		changeByStrengthButton.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				widgetDefaultSelected(e);
			}
			
			public void widgetDefaultSelected(SelectionEvent e) {
				gameFormInstance.setSubstitutionPreferences(SubstitutionPreferences.SP_STRENGTH);
			}
		});
		
		changeByPositionButton = new Button(leftPanel, SWT.RADIO);
		gridData= new GridData(SWT.BEGINNING, SWT.END, false, false);
		changeByPositionButton.setLayoutData(gridData);
		changeByPositionButton.setText(MainWindow.getMessage("ForcedChangeByPosition"));
		changeByPositionButton.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				widgetDefaultSelected(e);
			}
			
			public void widgetDefaultSelected(SelectionEvent e) {
				gameFormInstance.setSubstitutionPreferences(SubstitutionPreferences.SP_POSITION);
			}
		});
		
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

		data = new FormData();
		data.right = new FormAttachment(100, 0);
		data.top = new FormAttachment(0, 0);
		
		firstTeamTable = new Table(rightPanel, SWT.BORDER | SWT.FULL_SELECTION);
		firstTeamTable.setHeaderVisible(true);
		firstTeamTable.setLinesVisible(true);
		firstTeamTable.setHeaderVisible(true);

		String[] titlesMatch = { MainWindow.getMessage("PlayerNumber"),
				MainWindow.getMessage("PlayerPosition"),
				MainWindow.getMessage("PlayerName"),
				MainWindow.getMessage("Standard") };

		String[] titlesBench = { MainWindow.getMessage("PlayerNumber"),
				MainWindow.getMessage("PlayerPosition"),
				MainWindow.getMessage("PlayerName"),
				"   ", };


		for (int i = 0; i < titlesMatch.length; i++) {
			TableColumn column = new TableColumn(firstTeamTable, SWT.NONE);
			column.setText("1234");
			column.pack();
			column.setText(titlesMatch[i]);
			column.setResizable(false);
		}
		firstTeamTable.setItemCount(11);

		for (int i = 0; i < firstTeamTable.getColumnCount(); i++) {
			firstTeamTable.getColumn(i).pack();
		}

		firstTeamTable.setLayoutData(data);

		benchTeamTable = new Table(rightPanel, SWT.BORDER
				| SWT.FULL_SELECTION);
		benchTeamTable.setHeaderVisible(true);
		benchTeamTable.setLinesVisible(true);
		benchTeamTable.setHeaderVisible(true);

		for (int i = 0; i < titlesBench.length; i++) {
			TableColumn column = new TableColumn(benchTeamTable, SWT.NONE);
			column.setText("1234");
			column.pack();
			column.setText(titlesBench[i]);
			column.setResizable(false);
		}
		benchTeamTable.setItemCount(7);

		for (int i = 0; i < benchTeamTable.getColumnCount(); i++) {
			benchTeamTable.getColumn(i).pack();
		}

		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0);
		data.top = new FormAttachment(firstTeamTable, 0);
		benchTeamTable.setLayoutData(data);

		matchPlayersTableEditor = new TableEditor (firstTeamTable);
		matchPlayersTableEditor.horizontalAlignment = SWT.LEFT;
		matchPlayersTableEditor.grabHorizontal = true;
		matchPlayersTableEditor.minimumWidth = 35;
		firstTeamTable.addListener(SWT.MouseDown, new MatchPlayersTableEditListener());

		/*
		 * size = rightPanel.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		 * rightPanelScrl.setMinWidth(size.x);
		 * rightPanelScrl.setMinHeight(size.y);
		 */

		setDragDropSource(firstTeamTable);
		setDragDropSource(benchTeamTable);
		setDragDropTargetSubstTable(substitutionTable);
		setDragDropTargetFirstTeamTable(firstTeamTable);
		setDragDropTargetBenchTeamTable(benchTeamTable);

		specialRoleEditor = new SpecialRoleEditor(firstTeamTable.getDisplay(), firstTeamTable.getShell(),
				null, null, gameFormInstance, true);

		specialRoleEditorSmall = new SpecialRoleEditor(substitutionTable.getDisplay(), substitutionTable.getShell(),
				null, null, gameFormInstance, false);

		updateAll();
	}

	public void updateAll() {
		substitutionTable.setItemCount(gameFormInstance.getSubstitutions().length);
		redraw();
	}

	public int getPlayerTypeGameForm(int playerNumber) {
		int res = 0;
		PlayerPosition playerPositions[] = gameFormInstance.getFirstTeam();
		for (int i = 0; i < playerPositions.length; i++) {
			if (playerPositions[i].getNumber() == playerNumber) {
				res = 1;
			}
		}
		int playerSubstitutions[] = gameFormInstance.getBench();
		for (int i = 0; i < playerSubstitutions.length; i++) {
			if (playerSubstitutions[i] == playerNumber) {
				res = 2;
			}
		}
		return res; 
	}
	
	public void redraw() {
		if (MainWindow.getAllInstance() != null && MainWindow.getAllInstance().getCurrentTeam() != null) {
			substitutionTable.setRedraw(false);
			PlayerSubstitution[] playerSubsts = gameFormInstance.getSubstitutions(); 
			Team curTeam = MainWindow.getAllInstance().getCurrentTeam();
			if ( playerSubsts.length != 0 ) {				
				for (int i = 0;  i < playerSubsts.length; i++ ) {
					PlayerSubstitution subst = playerSubsts[i];
					TableItem item = substitutionTable.getItem(i);
					if ( !subst.isEmpty()){
						int index = 0;
						String time = (subst.getTime() == -1) ? "" : String.valueOf(subst.getTime());
						item.setText(index++, time);
						item.setText(index++, String.valueOf(subst.getMinDifference()));
						item.setText(index++, String.valueOf(subst.getMaxDifference()));
						int number = subst.getSubstitutedPlayer();
						int type = getPlayerTypeGameForm(number);
						Player player = curTeam.getPlayerByNumber(number);
						String st = (player != null) ? String.valueOf(player.getNumber()) : "";
						item.setText(index++, st);
						item.setForeground(index, tableColors[type]);
						item.setFont(index, tableFonts[type]);
						st = (player != null) ? player.getName() : "";
						item.setText(index++, st);
						number = subst.getNumber();
						type = getPlayerTypeGameForm(number);
						player = curTeam.getPlayerByNumber(number);
						st = (player != null) ? String.valueOf(player.getNumber()) : "";
						item.setText(index++, st);
						item.setForeground(index, tableColors[type]);
						item.setFont(index, tableFonts[type]);
						st = (player != null) ? player.getName() : "";
						item.setText(index++, st);
						item.setText(index++, printSRflags(subst));
					} else {
						for ( int j = 0; j < substitutionTable.getColumnCount(); j++) 
							item.setText(j,"");
					}
				}
			}
			
			for (int i = 0; i < substitutionTable.getColumnCount(); i++) {
				substitutionTable.getColumn(i).pack();
			}
			substitutionTable.getParent().layout();
			substitutionTable.setRedraw(true);
		}
		redrawForm();
	}

	public void setDragDropSource(final Table table) {
		Transfer[] types = new Transfer[] { TextTransfer.getInstance() };
		int operations = DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_DEFAULT;
		final DragSource source = new DragSource(table, operations);
		source.setTransfer(types);
		source.addDragListener(new DragSourceListener() {
			
			public void dragStart(DragSourceEvent event) {
				TableItem items[] = table.getSelection();
				TableItem item = items[0];
				Player player = (Player) item.getData();
				if ( player == null ){
					event.doit = false;
				}
			}

			public void dragSetData(DragSourceEvent event) {

				TableItem items[] = table.getSelection();
				TableItem item = items[0];
				Player player = (Player) item.getData();
				if ( player != null ){
					event.data = String.valueOf(player.getNumber());
				} else {
					event.doit = false;
				}
			}

			public void dragFinished(DragSourceEvent event) {
			}
		});
	}

	public void setDragDropTargetSubstTable(final Table table) {
		int operations = DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_DEFAULT;
		DropTarget target = new DropTarget(table, operations);

		final TextTransfer textTransfer = TextTransfer.getInstance();
		Transfer types[] = new Transfer[] { textTransfer };
		target.setTransfer(types);

		target.addDropListener(new DropTargetAdapter() {

			public void drop(DropTargetEvent event) {
				if (textTransfer.isSupportedType(event.currentDataType)) {
					if (event.item instanceof TableItem) {
						PlayerSubstitution[] substs = gameFormInstance.getSubstitutions();
						if ( event.data != null){
							String st = (String) event.data;
							int playerNumber = Integer.valueOf(st);
							for (int i = 0; i < table.getItemCount(); i++){
								TableItem targetItem = table.getItem(i);
								Rectangle rect = targetItem.getDisplay().map(table, null, targetItem.getBounds(3));
								Rectangle rect2 = targetItem.getDisplay().map(table, null, targetItem.getBounds(4));
								if ( rect.contains(event.x, event.y) || rect2.contains(event.x, event.y) ) {
									substs[i].setSubstitutedPlayer(playerNumber);
									redraw();
								}
								rect = targetItem.getDisplay().map(table, null, targetItem.getBounds(5));
								rect2 = targetItem.getDisplay().map(table, null, targetItem.getBounds(6));
								if ( rect.contains(event.x, event.y) || rect2.contains(event.x, event.y) ){
									substs[i].setNumber(playerNumber);
									redraw();
								}
							}
						}
					}
				}
			}
		});
	}

	public void setDragDropTargetFirstTeamTable(final Table table) {
		int operations = DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_DEFAULT;
		DropTarget target = new DropTarget(table, operations);

		final TextTransfer textTransfer = TextTransfer.getInstance();
		Transfer types[] = new Transfer[] { textTransfer };
		target.setTransfer(types);

		target.addDropListener(new DropTargetAdapter() {

			public void drop(DropTargetEvent event) {
				if (textTransfer.isSupportedType(event.currentDataType)) {
					if (event.item instanceof TableItem) {
						PlayerPosition[] players = gameFormInstance.getFirstTeam();
						if ( event.data != null){
							String st = (String) event.data;
							int i =  (Integer) event.item.getData("number");
							int j = 0;
							players[i].setNumber(Integer.valueOf(st));
							j = firstTeamTable.getSelectionIndex();
							if (j != -1)
								players[j].setNumber(0);
							j = benchTeamTable.getSelectionIndex();
							if (j != -1)
								gameFormInstance.getBench()[j] = 0;
							redraw();
						}
					}
				}
			}
		});
	}

	public void setDragDropTargetBenchTeamTable(final Table table) {
		int operations = DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_DEFAULT;
		DropTarget target = new DropTarget(table, operations);

		final TextTransfer textTransfer = TextTransfer.getInstance();
		Transfer types[] = new Transfer[] { textTransfer };
		target.setTransfer(types);

		target.addDropListener(new DropTargetAdapter() {

			public void drop(DropTargetEvent event) {
				if (textTransfer.isSupportedType(event.currentDataType)) {
					if (event.item instanceof TableItem) {
						int[] players = gameFormInstance.getBench();
						if ( event.data != null){
							String st = (String) event.data;
							int i = (Integer) event.item.getData("number");
							int j = 0;
							players[i] = Integer.valueOf(st);
							j = benchTeamTable.getSelectionIndex();
							if (j != -1)
								players[j] = 0;
							j = firstTeamTable.getSelectionIndex();
							if (j != -1)
								gameFormInstance.getFirstTeam()[j].setNumber(0);
							redraw();
						}
					}
				}
			}
		});
	}

	public void setPlayer(TableItem item, String playerData) {
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
		if (MainWindow.getAllInstance() != null) {
			firstTeamTable.setRedraw(false);
			benchTeamTable.setRedraw(false);
			Team curTeam = MainWindow.getAllInstance().getCurrentTeam();
			PlayerPosition playerPositions[] = gameFormInstance.getFirstTeam();
			int playerSubstitution[] = gameFormInstance.getBench();
			int itemsCount = Math.max(11, playerPositions.length);
			firstTeamTable.setItemCount(itemsCount);
			benchTeamTable.setItemCount(7);
			for (int i = 0; i < itemsCount; i++) {
				TableItem item = firstTeamTable.getItem(i);
				item.setData("number", new Integer(i));
				item.setData("type", new Integer(0));
				item.setData(null);
			}
			for (int i = 0; i < benchTeamTable.getItemCount(); i++) {
				TableItem item = benchTeamTable.getItem(i);
				item.setData("number", new Integer(i));
				item.setData("type", new Integer(1));
				item.setData(null);
			}

			for (int i = 0; i < firstTeamTable.getItemCount(); i++) {
				TableItem item = firstTeamTable.getItem(i);
				for (int index = 0; index < firstTeamTable.getColumnCount(); index++)
					item.setText(index, "");
			}
			for (int i = 0; i < benchTeamTable.getItemCount(); i++) {
				TableItem item = benchTeamTable.getItem(i);
				for (int index = 0; index < benchTeamTable.getColumnCount(); index++)
					item.setText(index, "");
			}

			for (int i = 0; i < playerPositions.length; i++) {
				TableItem item = firstTeamTable.getItem(i);
				int index = 0;
				if (playerPositions[i] != null) {
					Player player = curTeam
							.getPlayerByNumber(playerPositions[i].getNumber());
					if (player != null) {
						item.setText(index++, String
								.valueOf(player.getNumber()));
					} else {
						index++;
					}
					item.setText(index++, playerPositions[i].getAmplua().toString());
					if (player != null) {
						item.setText(index++, player.getName());
						item.setData(player);
					} else {
						index++;
					}
					item.setText(index++, printSRflags(playerPositions[i]));
				}
			}
			for (int i = 0; i < playerSubstitution.length; i++) {
				TableItem item = benchTeamTable.getItem(i);
				int index = 0;
				Player player = curTeam
						.getPlayerByNumber(playerSubstitution[i]);
				if (player != null) {
					item.setText(index++, String.valueOf(player.getNumber()));
					item.setText(index++, player.getPosition().toString());
					item.setText(index++, player.getName());
					item.setData(player);
				}
			}

			int width = 0;
			for (int i = 0; i < firstTeamTable.getColumnCount(); i++) {
				int widthColum = 0;
				firstTeamTable.getColumn(i).pack();
				int dWidth = firstTeamTable.getColumn(i).getWidth();
				widthColum = dWidth;
				benchTeamTable.getColumn(i).pack();
				dWidth = benchTeamTable.getColumn(i).getWidth();
				widthColum = Math.max(widthColum, dWidth);
				width += widthColum;
				firstTeamTable.getColumn(i).setWidth(widthColum);
				benchTeamTable.getColumn(i).setWidth(widthColum);
			}

			int heightAll = 0;
			int widthAll = 0;
			FormData data = (FormData) firstTeamTable.getLayoutData();
			width += firstTeamTable.getBorderWidth() * 2;
			data.left = new FormAttachment(0, rightPanelScrl.getVerticalBar().getSize().x);
			data.right = new FormAttachment(firstTeamTable, width);
			int height = firstTeamTable.getItemCount()
					* firstTeamTable.getItemHeight();
			height += firstTeamTable.getHeaderHeight();
			height += firstTeamTable.getBorderWidth() * 3;
			data.bottom = new FormAttachment(firstTeamTable, height);
			heightAll += height;
			widthAll = width;

			data = (FormData) benchTeamTable.getLayoutData();
			width -= firstTeamTable.getBorderWidth() * 2;
			width += benchTeamTable.getBorderWidth() * 2;
			data.left = new FormAttachment(0, rightPanelScrl.getVerticalBar().getSize().x);
			data.right = new FormAttachment(benchTeamTable, width);
			height = benchTeamTable.getItemCount()
					* firstTeamTable.getItemHeight();
			height += benchTeamTable.getHeaderHeight();
			height += benchTeamTable.getBorderWidth() * 3;
			data.bottom = new FormAttachment(benchTeamTable, height);
			heightAll += height;
			widthAll = Math.max(widthAll, width);
			firstTeamTable.getParent().layout();

			data = (FormData) rightPanelScrl.getLayoutData();
			int scrollWidth = rightPanelScrl.computeSize(SWT.DEFAULT, SWT.DEFAULT).x;
			data.left = new FormAttachment(rightPanelScrl, -scrollWidth);
			rightPanel.setSize(widthAll, heightAll);
			rightPanelScrl.setMinWidth(widthAll);
			rightPanelScrl.setExpandHorizontal(true);
			rightPanelScrl.setMinHeight(heightAll);
			rightPanelScrl.layout();
			rightPanelScrl.getParent().layout();

			firstTeamTable.setRedraw(true);
			benchTeamTable.setRedraw(true);
		}
	}
	private class SubstitutionTableEditListener implements Listener {

		public void handleEvent (Event event) {
			Rectangle clientArea = substitutionTable.getClientArea ();
			Point pt = new Point (event.x, event.y);
			if ( event.button == 1 ){
				int index = substitutionTable.getTopIndex ();
				while (index < substitutionTable.getItemCount ()) {
					boolean visible = false;
					final TableItem item = substitutionTable.getItem (index);
					for (int i=0; i < substitutionTable.getColumnCount (); i++) {
						Rectangle rect = item.getBounds (i);
						if (rect.contains (pt) ) {
							final int column = i;
							final int row = index;
							if ( i < 3 ){
								final Text text = new Text (substitutionTable, SWT.NONE);
								Listener textListener = new Listener () {
									public void handleEvent (final Event e) {
										int val = -1;
										PlayerSubstitution subst;
										try {
											val = Integer.valueOf(text.getText());
										} catch (NumberFormatException nfe){
											val = -1;
										}
										switch (e.type) {
											case SWT.FocusOut:
												if (val > 0){
													if (val > 150) val=150;
													item.setText(column, String.valueOf(val));
													subst = gameFormInstance.getSubstitutions()[row];
													if ( column == 0 ) {
														subst.setTime(val);
													} else if ( column == 1 ) {
														subst.setMinDifference(val);
													} else {
														subst.setMaxDifference(val);
													}
													redraw();
												}
												text.dispose ();
												break;
											case SWT.Traverse:
												switch (e.detail) {
													case SWT.TRAVERSE_RETURN:
														if (val > 0){
															if (val > 150) val=150;
															item.setText(column, String.valueOf(val));
															subst = gameFormInstance.getSubstitutions()[row];
															if ( column == 0 ) {
																subst.setTime(val);
															} else if ( column == 1 ) {
																subst.setMinDifference(val);
															} else {
																subst.setMaxDifference(val);
															}
															redraw();
														}
														//FALL THROUGH
													case SWT.TRAVERSE_ESCAPE:
														text.dispose ();
														e.doit = false;
												}
												break;
											case SWT.Verify:
												String string = e.text;
												char [] chars = new char [string.length ()];
												string.getChars (0, chars.length, chars, 0);
												for (int i=0; i<chars.length; i++) {
													if (!('0' <= chars [i] && chars [i] <= '9')) {
														e.doit = false;
														return;
													}
												}
										}
									}
								};
								text.addListener (SWT.FocusOut, textListener);
								text.addListener (SWT.Traverse, textListener);
								text.addListener (SWT.Verify, textListener);
								text.setTextLimit(5);
								editor.setEditor (text, item, i);
								text.setText (item.getText (i));
								text.selectAll ();
								text.setFocus ();
							} else {
								if ( i == 7 ){ 
									Rectangle rect1 = substitutionTable.getDisplay().map(item.getParent(), null, rect);
									specialRoleEditorSmall.updatePlayerPosition(gameFormInstance.getSubstitutions()[row]);
									specialRoleEditorSmall.Show(new Point(rect1.x+rect1.width,rect1.y));
									redraw();
//									final Combo combo = new Combo(substitutionTable, SWT.READ_ONLY);
//									combo.add(MainWindow.getMessage("SRCaptain"));
//									combo.add(MainWindow.getMessage("SRDirectFreekick"));
//									combo.add(MainWindow.getMessage("SRIndirectFreekick"));
//									combo.add(MainWindow.getMessage("SRPenalty"));
//									combo.add(MainWindow.getMessage("SRLeftCorner"));
//									combo.add(MainWindow.getMessage("SRRightCorner"));
//									combo.add(MainWindow.getMessage("Clear"));
//									Listener comboListener = new Listener () {
//										public void handleEvent (final Event e) {
//											String flg = combo.getText();
//											PlayerSubstitution subst;
//											switch (e.type) {
//												case SWT.FocusOut:
//													if ( combo.getSelectionIndex() != -1){
//														subst = gameFormInstance.getSubstitutions()[row];
//														setSRflag(flg, subst, !getSRflag(flg, subst));
//														item.setText(column, printSRflags(subst));
//													}
//													redraw();
//													combo.dispose ();
//													break;
//												case SWT.Traverse:
//													switch (e.detail) {
//														case SWT.TRAVERSE_RETURN:
//														case SWT.FocusOut:
//															if ( combo.getSelectionIndex() != -1){
//																subst = gameFormInstance.getSubstitutions()[row];
//																setSRflag(flg, subst, !getSRflag(flg, subst));
//																item.setText(column, printSRflags(subst));
//															}
//															redraw();
//															//FALL THROUGH
//														case SWT.TRAVERSE_ESCAPE:
//															combo.dispose ();
//															e.doit = false;
//													}
//													break;
//											}
//										}
//									};
//									combo.addListener (SWT.FocusOut, comboListener);
//									combo.addListener (SWT.Traverse, comboListener);
//									editor.setEditor (combo, item, i);
//									combo.setText (item.getText (i));
//									combo.setFocus ();
								}
							}
							
							return;
						}
						if (!visible && rect.intersects (clientArea)) {
							visible = true;
						}
					}
					if (!visible) return;
					index++;
				}
			}
		}

	}
	
	private void setSRflag(String flg, PlayerSubstitution subst, boolean val){
		if ( flg.compareTo(MainWindow.getMessage("SRCaptain")) == 0 ){
			subst.setCaptain(val);
		} else if ( flg.compareTo(MainWindow.getMessage("SRDirectFreekick")) == 0 ) {
			subst.setDirectFreekick(val);
		} else  if ( flg.compareTo(MainWindow.getMessage("SRIndirectFreekick")) == 0) {
			subst.setIndirectFreekick(val);
		} else if ( flg.compareTo(MainWindow.getMessage("SRPenalty")) == 0 ){
			subst.setPenalty(val);
		} else if ( flg.compareTo(MainWindow.getMessage("SRLeftCorner")) == 0 ){
			subst.setLeftCorner(val);
		} else if ( flg.compareTo(MainWindow.getMessage("SRRightCorner")) == 0 ){
			subst.setRightCorner(val);
		} else if ( flg.compareTo(MainWindow.getMessage("Clear")) == 0 ){
			clearAllSRflags(subst);
		}
	}

	private boolean getSRflag(String flg, PlayerSubstitution subst){
		if ( flg.compareTo(MainWindow.getMessage("SRCaptain")) == 0 ){
			return subst.isCaptain();
		} else if ( flg.compareTo(MainWindow.getMessage("SRDirectFreekick")) == 0 ) {
			return subst.isDirectFreekick();
		} else  if ( flg.compareTo(MainWindow.getMessage("SRIndirectFreekick")) == 0 ){
			return subst.isIndirectFreekick();
		} else if ( flg.compareTo(MainWindow.getMessage("SRPenalty")) == 0 ){
			return subst.isPenalty();
		} else if ( flg.compareTo(MainWindow.getMessage("SRLeftCorner")) == 0 ){
			return subst.isLeftCorner();
		} else if ( flg.compareTo(MainWindow.getMessage("SRRightCorner")) == 0 ){
			return subst.isRightCorner();
		}
		return false;
	}
	
	private String printSRflags(PlayerSubstitution subst){
		String st= "";
		if ( subst.isCaptain() ) st += MainWindow.getMessage("SRCaptainShort");
		if ( subst.isDirectFreekick() ) st += MainWindow.getMessage("SRDirectFreekickShort");
		if ( subst.isIndirectFreekick() ) st += MainWindow.getMessage("SRIndirectFreekickShort");
		if ( subst.isPenalty() ) st += MainWindow.getMessage("SRPenaltyShort");
		if ( subst.isLeftCorner() ) st += MainWindow.getMessage("SRLeftCornerShort");
		if ( subst.isRightCorner() ) st += MainWindow.getMessage("SRRightCornerShort");
		return st;
	}

	private void clearAllSRflags(PlayerSubstitution subst){
		subst.setCaptain(false);
		subst.setDirectFreekick(false);
		subst.setIndirectFreekick(false);
		subst.setPenalty(false);
		subst.setLeftCorner(false);
		subst.setRightCorner(false);
	}
	
	public class SubstitutionTableMouseListener implements MenuDetectListener{

		public void menuDetected(MenuDetectEvent e) {
			Menu menu = new Menu (substitutionTable);
			MenuItem item = new MenuItem (menu, SWT.NONE);
			item.setText (MainWindow.getMessage("Clear"));
			item.addSelectionListener(new SelectionListener() {
				
				public void widgetSelected(SelectionEvent e) {
					widgetDefaultSelected(e);
					
				}
				
				public void widgetDefaultSelected(SelectionEvent e) {
					int i = substitutionTable.getSelectionIndex();
					gameFormInstance.getSubstitutions()[i].markEmpty();
					gameFormInstance.getSubstitutions()[i].setNumber(0);
					gameFormInstance.getSubstitutions()[i].setSubstitutedPlayer(0);
					gameFormInstance.getSubstitutions()[i].setTime(0);
//					gameForm.getSubstitutions()[i].setNumber(0);
					redraw();
					
				}
			});
			
			menu.setLocation(e.x, e.y);
			menu.setVisible (true);
		}
		
	}

	public void updateGameForm(GameForm gameForm) {
		gameFormInstance = gameForm;
		updateAll();
	}

	public void updatePassword(String password) {
		// TODO Auto-generated method stub
		
	}
	private String printSRflags(PlayerPosition subst){
		String st= "";
		if ( subst.isCaptain() ) st += MainWindow.getMessage("SRCaptainShort");
		if ( subst.isCaptainAssistant() ) st += MainWindow.getMessage("SRCaptainShort").toLowerCase();
		if ( subst.isDirectFreekick() ) st += MainWindow.getMessage("SRDirectFreekickShort");
		if ( subst.isDirectFreekickAssistant() ) st += MainWindow.getMessage("SRDirectFreekickShort").toLowerCase();
		if ( subst.isIndirectFreekick() ) st += MainWindow.getMessage("SRIndirectFreekickShort");
		if ( subst.isIndirectFreekickAssistant() ) st += MainWindow.getMessage("SRIndirectFreekickShort").toLowerCase();
		if ( subst.isPenalty() ) st += MainWindow.getMessage("SRPenaltyShort");
		if ( subst.isPenaltyAssistant() ) st += MainWindow.getMessage("SRPenaltyShort").toLowerCase();
		if ( subst.isLeftCorner() ) st += MainWindow.getMessage("SRLeftCornerShort");
		if ( subst.isLeftCornerAssistant() ) st += MainWindow.getMessage("SRLeftCornerShort").toLowerCase();
		if ( subst.isRightCorner() ) st += MainWindow.getMessage("SRRightCornerShort");
		if ( subst.isRightCornerAssistant() ) st += MainWindow.getMessage("SRRightCornerShort").toLowerCase();
		if ( subst.getPenaltyOrder() > 0 ) st += " " + subst.getPenaltyOrder();
		return st;
	}

	private class MatchPlayersTableEditListener implements Listener {

		public void handleEvent (Event event) {
			Rectangle clientArea = firstTeamTable.getClientArea ();
			Point pt = new Point (event.x, event.y);
			if ( event.button == 1 ){
				int index = firstTeamTable.getTopIndex ();
				while (index < firstTeamTable.getItemCount ()) {
					boolean visible = false;
					final TableItem item = firstTeamTable.getItem (index);
					for (int i = 0; i < firstTeamTable.getColumnCount (); i++) {
						Rectangle rect = item.getBounds (i);
						if (rect.contains (pt) ) {
							final int column = i;
							final int row = index;
							final PlayerPosition playerPos = gameFormInstance.getFirstTeam()[row];
							if ( column == 3 ){
								Rectangle rect1 = firstTeamTable.getDisplay().map(item.getParent(), null, rect);
								specialRoleEditor.updatePlayerPosition(playerPos);
								specialRoleEditor.Show(new Point(rect1.x+rect1.width,rect1.y));
								redraw();
							}

							return;
						}
						if (!visible && rect.intersects (clientArea)) {
							visible = true;
						}
					}
					if (!visible) return;
					index++;
				}
			}
		}

	}

	public void updateDaysOfRest(int daysOfRest) {
		// TODO Auto-generated method stub
		
	}

	public void updateMessages() {

		String[] titles = { MainWindow.getMessage("ChangeTime"),
				MainWindow.getMessage("MinDifference"),
				MainWindow.getMessage("MaxDifference"),
				MainWindow.getMessage("PlayerNumber"),
				MainWindow.getMessage("OutgoingPlayer"),
				MainWindow.getMessage("PlayerNumber"),
				MainWindow.getMessage("IngoingPlayer"),
				MainWindow.getMessage("Standard") };

		for (int i = 0; i < titles.length; ++i) {
			substitutionTable.getColumn(i).setText(titles[i]);
		}

		changeByStrengthButton.setText(MainWindow.getMessage("ForcedChangeByStrength"));
		changeByPositionButton.setText(MainWindow.getMessage("ForcedChangeByPosition"));

		String[] titlesMatch = { MainWindow.getMessage("PlayerNumber"),
				MainWindow.getMessage("PlayerPosition"),
				MainWindow.getMessage("PlayerName"),
				MainWindow.getMessage("Standard") };

		String[] titlesBench = { MainWindow.getMessage("PlayerNumber"),
				MainWindow.getMessage("PlayerPosition"),
				MainWindow.getMessage("PlayerName"),
				"   ", };


		for (int i = 0; i < titlesMatch.length; ++i) {
			firstTeamTable.getColumn(i).setText(titlesMatch[i]);
			benchTeamTable.getColumn(i).setText(titlesBench[i]);
		}

		specialRoleEditor.updateMessages();
		specialRoleEditorSmall.updateMessages();
		
		redraw();
	}

}
