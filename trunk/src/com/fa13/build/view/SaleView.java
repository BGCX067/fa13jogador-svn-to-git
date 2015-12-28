package com.fa13.build.view;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.dnd.*;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.*;

import com.fa13.build.controller.io.PlayerActionsReader;
import com.fa13.build.controller.io.PlayerActionsWriter;
import com.fa13.build.controller.io.ReaderException;
import com.fa13.build.model.*;
import com.fa13.build.model.RentBid.RentType;
import com.fa13.build.model.SellBid.SellType;

public class SaleView implements UIItem {

	String password;
	TabFolder parentInstance;
	TabItem mainItem;
	Composite salePanel;
	Composite pricePanel;
	Composite topComposite;
	Composite gridComposite;
	Composite saveComposite;
	Composite toggleComposite;
	Composite someComposite;
	Composite rentPanel;
	ScrolledComposite rightPanelScrl;
	Table teamTable;
	Table rentTableIn;
	Table rentTableOut;
	Table saleTablePrice;
//	Table saleTablePercent;
	boolean homeBonus;
	GameForm gameForm;
	Button saleButton;
	Button rentButton;
	Button saveButton;
	Button openButton;
	PlayerActions Bid;
	List<RentBid> rentBidsIn;
	List<RentBid> rentBidsOut;
	List<SellBid> saleBids;
	TableEditor saleEditor;
	TableEditor rentInEditor;
	TableEditor	rentOutEditor;
	static Color tableColors[] = null;
	static Font tableFonts[] = null;
	
	static Map<String, Image> flags;
	
	static Listener paintListener;
	static String[] titles;
	boolean rentMode;
	static final int BID_SIZE = 5;
	
	public SaleView(TabFolder parent) {
		parentInstance = parent;
		rentBidsIn = new ArrayList<RentBid>(BID_SIZE);
		rentBidsOut = new ArrayList<RentBid>(BID_SIZE);
		saleBids = new ArrayList<SellBid>(BID_SIZE);
		for (int i = 0; i < BID_SIZE ; i++){
			RentBid rb = new RentBid(RentType.RT_OFFER, "", 0);
			rb.setEmpty(true);
			rentBidsOut.add(rb);
			rb = new RentBid(RentType.RT_TAKE, "", 0);
			rb.setEmpty(true);
			rentBidsIn.add(rb);
			SellBid sb = new SellBid(SellType.SL_VALUE, 0, 0);
			sb.setEmpty(true);
			saleBids.add(sb);
		}
		
		mainItem = new TabItem(parent, SWT.NONE);
		mainItem.setText(MainWindow.getMessage("saleEditorTabName"));
		topComposite = new Composite(parent, SWT.NONE);		
		mainItem.setControl(topComposite);
		topComposite.setLayout(new FormLayout());
		
		FormData data = new FormData();

		gridComposite = new Composite(topComposite, SWT.NONE);
		saveComposite = new Composite(topComposite, SWT.BORDER);
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0);
		data.bottom = new FormAttachment(saveComposite, -3);
		data.top = new FormAttachment(0, 0);
		gridComposite.setLayoutData(data);
		gridComposite.setLayout(new FormLayout());
		
		data = new FormData();
		data.bottom = new FormAttachment(100, 0);
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0); 
		saveComposite.setLayoutData(data);
		saveComposite.setLayout(new FormLayout());
		
		saveButton = new Button(saveComposite, SWT.PUSH);

		data = new FormData();
		data.bottom = new FormAttachment(100, -5);
		data.top = new FormAttachment(0, 5);
		data.right = new FormAttachment(100, -5);
		saveButton.setLayoutData(data);
		saveButton.addSelectionListener(new SaveButtonListner());

		openButton = new Button(saveComposite, SWT.PUSH);
		
		data = new FormData();
		data.bottom = new FormAttachment(100, -5);
		data.top = new FormAttachment(0, 5);
		data.right = new FormAttachment(saveButton, -5);
		openButton.setLayoutData(data);
		openButton.addSelectionListener(new OpenButtonListner());
		
		saveComposite.layout();
		
		salePanel = new Composite(gridComposite, SWT.NONE);
		someComposite = new Composite(gridComposite, SWT.NONE);
		someComposite.setLayout(new FormLayout());
		salePanel.setLayout(new FillLayout());
	
		teamTable = new Table(salePanel, SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE);
		teamTable.setHeaderVisible(true);
		teamTable.setLinesVisible(true);
		teamTable.setHeaderVisible(true);
		
		String[] titles = { MainWindow.getMessage("PlayerNumber"),
				MainWindow.getMessage("PlayerPosition"),
				MainWindow.getMessage("PlayerName"),
				MainWindow.getMessage("PlayerPrice"),
				MainWindow.getMessage("PlayerSalary"),
				MainWindow.getMessage("PlayerGames"),
				MainWindow.getMessage("PlayerGoalsTotal"),
				MainWindow.getMessage("PlayerChampGoals"),
				MainWindow.getMessage("PlayerGoalsMissed"),
				MainWindow.getMessage("PlayerAssists"),
				MainWindow.getMessage("PlayerProfit"),
				MainWindow.getMessage("PlayerMark"),
				MainWindow.getMessage("PlayerGamesCareer"),
				MainWindow.getMessage("PlayerGoalsCareer"),
				MainWindow.getMessage("PlayerState"),
				MainWindow.getMessage("PlayerYellowCards"),
				MainWindow.getMessage("PlayerRedCards"),
				MainWindow.getMessage("PlayerHomeClub"),
				MainWindow.getMessage("PlayerBirthTour"),
				MainWindow.getMessage("PlayerBirthDate") };

		for (int i = 0; i < titles.length; i++) {
			TableColumn column = new TableColumn(teamTable, SWT.NONE);
			column.setText("1234");
			column.pack();
			column.setText(titles[i]);
			column.setResizable(false);
		}

		teamTable.getColumn(3).pack();
		
		toggleComposite = new Composite(someComposite, SWT.NONE);
		toggleComposite.setLayout(new FormLayout());
		rightPanelScrl = new ScrolledComposite(someComposite, SWT.NONE | SWT.H_SCROLL
				| SWT.V_SCROLL);
		rightPanelScrl.setExpandHorizontal(false);
		rightPanelScrl.setExpandVertical(true);
		rentPanel = new Composite(rightPanelScrl, SWT.NONE);
		pricePanel = new Composite(rightPanelScrl, SWT.NONE);
		rightPanelScrl.setContent(rentPanel);
		
		saleButton = new Button(toggleComposite, SWT.TOGGLE);
		rentButton = new Button(toggleComposite, SWT.TOGGLE);

		
		int scrollWidth = rightPanelScrl.getVerticalBar().getSize().x;
		saleButton.setText(MainWindow.getMessage("SaleToggleSale"));
		data = new FormData();
		data.left = new FormAttachment(0, scrollWidth);
		//data.right = new FormAttachment(saleButton, saleButton.computeSize(SWT.DEFAULT, SWT.DEFAULT).x);
		data.top = new FormAttachment(0, 0);
		saleButton.setLayoutData(data);
		saleButton.addSelectionListener(new SaleBtnListener());
		
		rentButton.setText(MainWindow.getMessage("SaleToggleRent"));
		data = new FormData();
		data.left = new FormAttachment(saleButton, 0);
		//data.right = new FormAttachment(rentButton, rentButton.computeSize(SWT.DEFAULT, SWT.DEFAULT).x);
		data.top = new FormAttachment(0,0);
		rentButton.setLayoutData(data);
		rentButton.addSelectionListener(new RentBtnListener());

		Point toggleCompositeSize = toggleComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		data = new FormData();
		data.top = new FormAttachment(0, 0);
		data.left = new FormAttachment(0,0);
		data.right = new FormAttachment(toggleComposite,0);
		data.bottom = new FormAttachment(toggleComposite, toggleCompositeSize.y);
		toggleComposite.setLayoutData(data);
		
		rentTableIn = new Table(rentPanel, SWT.BORDER
				| SWT.FULL_SELECTION);
		rentTableIn.setHeaderVisible(true);
		rentTableIn.setLinesVisible(true);
		rentTableIn.setHeaderVisible(true);

		String[] titlesRentIn = { MainWindow.getMessage("PlayerNumber"),
				MainWindow.getMessage("Club"),
				"    "};

		for (int i = 0; i < titlesRentIn.length; i++) {
			TableColumn column = new TableColumn(rentTableIn, SWT.NONE);
			column.setText("1234");
			column.pack();
			column.setText(titlesRentIn[i]);
			column.setResizable(false);
		}
		rentTableIn.setItemCount(BID_SIZE);
		for (int i = 0; i < BID_SIZE; i++) {
			rentTableIn.getItem(i).setData(rentBidsIn.get(i));
		}

		for (int i = 0; i < rentTableIn.getColumnCount(); i++) {
			rentTableIn.getColumn(i).pack();
		}

		data = new FormData();
		data.left = new FormAttachment(0, 0);
		Point size = rentTableIn.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		data.right = new FormAttachment(rentTableIn, size.x);
		data.top = new FormAttachment(toggleComposite, 4);
		rentTableIn.setLayoutData(data);

		saleTablePrice = new Table(pricePanel, SWT.BORDER
				| SWT.FULL_SELECTION);
		saleTablePrice.setHeaderVisible(true);
		saleTablePrice.setLinesVisible(true);
		saleTablePrice.setHeaderVisible(true);

		String[] titlesPrice = { MainWindow.getMessage("PlayerNumber"),
				MainWindow.getMessage("PlayerName"),
				MainWindow.getMessage("SalePrice"),
				MainWindow.getMessage("SaleType")};

		for (int i = 0; i < titlesPrice.length; i++) {
			TableColumn column = new TableColumn(saleTablePrice, SWT.NONE);
			column.setText("1234");
			column.pack();
			column.setText(titlesPrice[i]);
			column.setResizable(false);
		}
		saleTablePrice.setItemCount(BID_SIZE);
		for (int i = 0; i < BID_SIZE; i++) {
			saleTablePrice.getItem(i).setData(saleBids.get(i));
		}
		
		for (int i = 0; i < saleTablePrice.getColumnCount(); i++) {
			saleTablePrice.getColumn(i).pack();
		}
		
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		size = saleTablePrice.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		data.right = new FormAttachment(saleTablePrice, size.x);
		data.top = new FormAttachment(toggleComposite, 4);
		saleTablePrice.setLayoutData(data);

		rentTableOut = new Table(rentPanel, SWT.BORDER
				| SWT.FULL_SELECTION);
		rentTableOut.setHeaderVisible(true);
		rentTableOut.setLinesVisible(true);
		rentTableOut.setHeaderVisible(true);

		String[] titlesRentOut = { MainWindow.getMessage("PlayerNumber"),
				MainWindow.getMessage("PlayerName"),
				MainWindow.getMessage("Club")};

		for (int i = 0; i < titlesRentOut.length; i++) {
			TableColumn column = new TableColumn(rentTableOut, SWT.NONE);
			column.setText("1234");
			column.pack();
			column.setText(titlesRentOut[i]);
			column.setResizable(false);
		}
		rentTableOut.setItemCount(BID_SIZE);
		for (int i = 0; i < BID_SIZE; i++) {
			rentTableOut.getItem(i).setData(rentBidsOut.get(i));
		}

		for (int i = 0; i < rentTableOut.getColumnCount(); i++) {
			rentTableOut.getColumn(i).pack();
		}

		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0);
		data.top = new FormAttachment(rentTableIn, 0);
		rentTableOut.setLayoutData(data);
		
////		saleTablePercent = new Table(pricePanel, SWT.BORDER
////				| SWT.FULL_SELECTION);
////		saleTablePercent.setHeaderVisible(true);
////		saleTablePercent.setLinesVisible(true);
////		saleTablePercent.setHeaderVisible(true);
////
////		String[] titlesPercent = { MainWindow.getMessage("PlayerNumber"),
////				MainWindow.getMessage("PlayerName"),
////				MainWindow.getMessage("SalePercent")};
////
////		for (int i = 0; i < titlesRentOut.length; i++) {
////			TableColumn column = new TableColumn(saleTablePercent, SWT.NONE);
////			column.setText("1234");
////			column.pack();
////			column.setText(titlesPercent[i]);
////			column.setResizable(false);
////		}
////		saleTablePercent.setItemCount(BID_SIZE);
//
//		for (int i = 0; i < saleTablePrice.getColumnCount(); i++) {
//			saleTablePrice.getColumn(i).pack();
//		}
//
//		data = new FormData();
//		data.left = new FormAttachment(0, 0);
//		data.right = new FormAttachment(100, 0);
//		data.top = new FormAttachment(saleTablePrice, 0);
//		saleTablePercent.setLayoutData(data);
		
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.top = new FormAttachment(toggleComposite, 0);
		data.bottom = new FormAttachment(100, 0);
		data.right = new FormAttachment(toggleComposite, 10);
		rightPanelScrl.setLayoutData(data);

		data = new FormData();
		data.top = new FormAttachment(0, 0);
		data.bottom = new FormAttachment(100, 0);
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0);
		rentPanel.setLayoutData(data);
		rentPanel.setLayout(new FormLayout());
		
		data = new FormData();
		data.top = new FormAttachment(0, 0);
		data.bottom = new FormAttachment(100, 0);
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0);
		pricePanel.setLayoutData(data);
		pricePanel.setLayout(new FormLayout());
		
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.top = new FormAttachment(0, 0);
		data.bottom = new FormAttachment(100, 0);
		data.right = new FormAttachment(someComposite, -5);
		salePanel.setLayoutData(data);

		data = new FormData();
		data.left = new FormAttachment(100, -200);
		data.top = new FormAttachment(0, 0);
		data.bottom = new FormAttachment(100, 0);
		data.right = new FormAttachment(100, 0);
		someComposite.setLayoutData(data);

		pricePanel.setVisible(false);
		rentMode = true;
		rentButton.setSelection(true);
		
		/*
		 * size = rightPanel.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		 * rightPanelScrl.setMinWidth(size.x);
		 * rightPanelScrl.setMinHeight(size.y);
		 */

		Map<String, Image> tmp = new HashMap<String, Image>();
		Set<Map.Entry<String, String>> nations = Player.nationalities.entrySet();

		for (Iterator<Map.Entry<String, String>> iterator = nations.iterator(); iterator.hasNext();) {
			Map.Entry<String, String> currEntry = iterator.next();
			String curr = currEntry.getValue();
			Image img = new Image(gridComposite.getDisplay(), this.getClass().getResourceAsStream("/com/fa13/build/resources/flags/"+ curr +".png"));
			tmp.put(curr, img);
		}
		flags = Collections.unmodifiableMap(tmp);
		final Image img = new Image(gridComposite.getDisplay(), this.getClass().getResourceAsStream("/com/fa13/build/resources/flags/not.png"));
		
		paintListener = new Listener() {
			public void handleEvent(Event event) {		
				switch(event.type) {
					case SWT.MeasureItem: {
						if (event.index == 3) {
							Rectangle rect = img.getBounds();
							event.width = rect.width;
							event.height = Math.max(event.height, rect.height + 2);
						}
						break;
					}
					case SWT.PaintItem: {
						if (event.index == 3) {
							int x = event.x;
							Rectangle rect = img.getBounds();
							int yoffset =  Math.max(0, (event.height - rect.height) / 2);
							event.gc.drawImage((Image)event.item.getData(), x + (teamTable.getColumn(3).getWidth() - rect.width) / 2 , event.y + yoffset);
						}
						break;
					}
				}
			}
		};
		

		setDragDropSource(teamTable);
		setDragDropTarget(rentTableOut);
		setDragDropTarget(saleTablePrice);
		rentTableIn.addMenuDetectListener(new BidTableMenuListener(rentTableIn));
		rentTableOut.addMenuDetectListener(new BidTableMenuListener(rentTableOut));
		saleTablePrice.addMenuDetectListener(new BidTableMenuListener(saleTablePrice));
		
		saleEditor = new TableEditor (saleTablePrice);
		saleEditor.horizontalAlignment = SWT.LEFT;
		saleEditor.grabHorizontal = true;
		saleEditor.minimumWidth = 35;
		saleTablePrice.addListener (SWT.MouseDown, new SaleEditListener());
		
		rentInEditor = new TableEditor (rentTableIn);
		rentInEditor.horizontalAlignment = SWT.LEFT;
		rentInEditor.grabHorizontal = true;
		rentInEditor.minimumWidth = 35;
		rentTableIn.addListener (SWT.MouseDown, new RentInEditListener());
		
		rentOutEditor = new TableEditor (rentTableOut);
		rentOutEditor.horizontalAlignment = SWT.LEFT;
		rentOutEditor.grabHorizontal = true;
		rentOutEditor.minimumWidth = 35;
		rentTableOut.addListener (SWT.MouseDown, new RentOutEditListener());
		
		updateAll();
		
	}

	public void updateAll() {
		for (int i = 0; i < BID_SIZE; ++i){
			rentBidsIn.get(i).setEmpty(true);
			rentBidsOut.get(i).setEmpty(true);
			saleBids.get(i).setEmpty(true);
		}
		redraw();
	}
	
	public void redraw() {
		teamTable.setRedraw(false);
		rentTableIn.setRedraw(false);
		rentTableOut.setRedraw(false);
		saleTablePrice.setRedraw(false);
		teamTable.removeAll();
		if (MainWindow.getAllInstance() != null) {
			Team curTeam = MainWindow.getAllInstance().getCurrentTeam();
			if (curTeam != null){
				int i = 0;
				List<Player> players = curTeam.getPlayers();
				teamTable.setItemCount(players.size());
				for (Player player: players) {
					int index = 0;
					TableItem item = teamTable.getItem(i);
					item.setData(player);
					item.setText(index++, String.valueOf(player.getNumber()));
					item.setText(index++, String.valueOf(player.getPosition()));
					item.setText(index++, player.getName());
					item.setText(index++, String.valueOf(player.getPrice()));
					item.setText(index++, String.valueOf(player.getSalary()));
					item.setText(index++, String.valueOf(player.getGames()));
					item.setText(index++, String.valueOf(player.getGoalsTotal()));
					item.setText(index++, String.valueOf(player.getGoalsChamp()));
					item.setText(index++, String.valueOf(player.getGoalsMissed()));
					item.setText(index++, String.valueOf(player.getAssists()));
					item.setText(index++, String.valueOf(player.getProfit()));
					item.setText(index++, String.valueOf(player.getMark()));
					item.setText(index++, String.valueOf(player.getGamesCareer()));
					item.setText(index++, String.valueOf(player.getGoalsCareer()));
					String tmpString = "";
					if (player.isTransfer()){
						tmpString = "T";
					} else if (player.isLease()){
						tmpString = "A";
					} else {
						tmpString = "K";
					}
					item.setText(index++, tmpString);
					item.setText(index++, String.valueOf(player.getYellowCards()));
					item.setText(index++, String.valueOf(player.getRedCards()));
					item.setText(index++, player.getBirthplace());
					item.setText(index++, String.valueOf(player.getBirthtour()));
					if (player.getBirthdate() == null){
						tmpString = "";
					} else {
						SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
						tmpString = dateFormat.format(player.getBirthdate());
					}
					item.setText(index++, tmpString);
					i++;
				}
				for (i = 0; i < teamTable.getColumnCount(); i++)
					teamTable.getColumn(i).pack();
				
				for ( i = 0; i < BID_SIZE; i++){
					TableItem item = saleTablePrice.getItem(i);
					SellBid sb = saleBids.get(i);
					RentBid rbIn = rentBidsIn.get(i);
					RentBid rbOut = rentBidsOut.get(i);
					if (!sb.isEmpty()){
						Player player = curTeam.getPlayerByNumber(sb.getNumber());
						item.setText(0, String.valueOf(sb.getNumber()));
						item.setText(1, (player == null) ? "" : player.getName());
						item.setText(2, String.valueOf(sb.getValue()));
						item.setText(3, printSaleType(sb.getType()));
					} else {
						item.setText(0, "");
						item.setText(1, "");
						item.setText(2, "");
					}
					item = rentTableIn.getItem(i);
					if (!rbIn.isEmpty()){
						item.setText(0, String.valueOf(rbIn.getNumber()));
						item.setText(1, rbIn.getTeam());
					} else {
						item.setText(0, "");
						item.setText(1, "");
					}
					item = rentTableOut.getItem(i);
					if (!rbOut.isEmpty()){
						Player player = curTeam.getPlayerByNumber(rbOut.getNumber());
						item.setText(0, String.valueOf(rbOut.getNumber()));
						item.setText(1, (player == null) ? "" : player.getName());
						item.setText(2, rbOut.getTeam());
					} else {
						item.setText(0, "");
						item.setText(1, "");
						item.setText(2, "");
					}
				}
			}
		}
		teamTable.setRedraw(true);
		saleTablePrice.setRedraw(true);
		rentTableIn.setRedraw(true);
		rentTableOut.setRedraw(true);
		
		topComposite.layout();
		
		redrawForm();
	}

	public void setDragDropSource(final Table table) {
		Transfer[] types = new Transfer[] { TextTransfer.getInstance() };
		int operations = DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_DEFAULT;
		final DragSource source = new DragSource(table, operations);
		source.setTransfer(types);
		source.addDragListener(new DragSourceListener() {
			public void dragStart(DragSourceEvent event) {

			}

			public void dragSetData(DragSourceEvent event) {
				TableItem items[] = table.getSelection();
				Player player = (Player)items[0].getData();
				try {
					event.data = String.valueOf(player.getNumber());
				} catch (SWTException e){
					event.doit = false;
					return;
				}
			}

			public void dragFinished(DragSourceEvent event) {

			}
		});
	}

	private class RentInEditListener implements Listener {
	
		public void handleEvent (Event event) {
			Rectangle clientArea = rentTableIn.getClientArea ();
			Point pt = new Point (event.x, event.y);
			if ( event.button == 1 ){
				int index = rentTableIn.getTopIndex ();
				while (index < rentTableIn.getItemCount ()) {
					boolean visible = false;
					final TableItem item = rentTableIn.getItem (index);
					for (int i=0; i < rentTableIn.getColumnCount (); i++) {
						Rectangle rect = item.getBounds (i);
						if (rect.contains (pt) ) {
							final int column = i;
							final int row = index;
							final Text text = new Text (rentTableIn, SWT.NONE);
							Listener textListener = new Listener () {
								public void handleEvent (final Event e) {
									RentBid rb = (RentBid) item.getData();
									switch (e.type) {
										case SWT.FocusOut:
											switch (column) {
												case 0:
													int val = 1;
													try {
														val = Integer.valueOf(text.getText());
													} catch (NumberFormatException nbe){
														val = -1;
													} finally {
														if ((val < 1) || (val > 25))
															val = 0;
													}
													rb.setNumber(val);
													break;
												case 1:
													rb.setTeam(text.getText());
											}
											rb.setEmpty(false);
											redraw();
											text.dispose ();
											break;
										case SWT.Traverse:
											switch (e.detail) {
												case SWT.TRAVERSE_RETURN:
													switch (column) {
													case 0:
														int val = 1;
														try {
															val = Integer.valueOf(text.getText());
														} catch (NumberFormatException nbe){
															val = -1;
														} finally {
															if ((val < 1) || (val > 25))
																val = 0;
														}
														rb.setNumber(val);
														break;
													case 1:
														rb.setTeam(text.getText());
													}
													rb.setEmpty(false);
													redraw();
													//FALL THROUGH
												case SWT.TRAVERSE_ESCAPE:
													text.dispose ();
													e.doit = false;
											}
											break;
										case SWT.Verify:
											if (column == 0){
												String string = e.text;
												char [] chars = new char [string.length ()];
												string.getChars (0, chars.length, chars, 0);
												for (int i=0; i<chars.length; i++) {
													if (!('0' <= chars [i] && chars [i] <= '9')) {
														e.doit = false;
														return;
													}
												}
											} else e.doit = true;
									}
								}
							};
							text.addListener (SWT.FocusOut, textListener);
							text.addListener (SWT.Traverse, textListener);
							text.addListener (SWT.Verify, textListener);
							rentInEditor.setEditor (text, item, i);
							text.setText (item.getText (i));
							text.selectAll ();
							text.setFocus ();
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

	public void setDragDropTarget(final Table table) {
		int operations = DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_DEFAULT;
		DropTarget target = new DropTarget(table, operations);

		final TextTransfer textTransfer = TextTransfer.getInstance();
		Transfer types[] = new Transfer[] { textTransfer };
		target.setTransfer(types);

		target.addDropListener(new DropTargetAdapter() {

			public void drop(DropTargetEvent event) {
				if (textTransfer.isSupportedType(event.currentDataType)) {
					if (event.item instanceof TableItem) {
						if ( event.data != null){
							String st = (String) event.data;
							int playerNumber = Integer.valueOf(st);
							for (int i = 0; i < table.getItemCount(); i++){
								TableItem targetItem = table.getItem(i);
								PlayerBid pb = (PlayerBid) targetItem.getData();
								for ( int j = 0; j < table.getColumnCount(); j++){
									if ( pb.getNumber() == playerNumber ){
										return;
									}
									Rectangle rect = targetItem.getDisplay().map(targetItem.getParent(), null, targetItem.getBounds(j));
									//Rectangle rect = targetItem.getBounds();
									if ( rect.contains(event.x, event.y) ) {
										pb.setNumber(playerNumber);
										pb.setEmpty(false);
										redraw();
										return;
									}
								}
							}
						}
					}
				}
			}
		});
	}

	public void redrawForm() {
		if (MainWindow.getAllInstance() != null) {
			if (rentMode){
				rentTableIn.setRedraw(false);
				rentTableOut.setRedraw(false);
	
				int width = 0;
				for (int i = 0; i < rentTableIn.getColumnCount(); i++) {
					int widthColum = 0;
					rentTableIn.getColumn(i).pack();
					int dWidth = rentTableIn.getColumn(i).getWidth();
					widthColum = dWidth;
					rentTableOut.getColumn(i).pack();
					dWidth = rentTableOut.getColumn(i).getWidth();
					widthColum = Math.max(widthColum, dWidth);
					width += widthColum;
					rentTableIn.getColumn(i).setWidth(widthColum);
					rentTableOut.getColumn(i).setWidth(widthColum);
				}
	
				int heightAll = 0;
				int widthAll = 0;
				FormData data = (FormData) rentTableIn.getLayoutData();
				width += rentTableIn.getBorderWidth() * 2;
				data.left = new FormAttachment(0, rightPanelScrl.getVerticalBar().getSize().x);
				data.right = new FormAttachment(rentTableIn, width);
				int height = rentTableIn.getItemCount()
						* rentTableIn.getItemHeight();
				height += rentTableIn.getHeaderHeight();
				height += rentTableIn.getBorderWidth() * 3;
				data.bottom = new FormAttachment(rentTableIn, height);
				heightAll += height;
				widthAll = width;
	
				data = (FormData) rentTableOut.getLayoutData();
				width -= rentTableIn.getBorderWidth() * 2;
				width += rentTableOut.getBorderWidth() * 2;
				data.left = new FormAttachment(0, rightPanelScrl.getVerticalBar().getSize().x);
				data.right = new FormAttachment(rentTableOut, width);
				height = rentTableOut.getItemCount()
						* rentTableIn.getItemHeight();
				height += rentTableOut.getHeaderHeight();
				height += rentTableOut.getBorderWidth() * 3;
				data.bottom = new FormAttachment(rentTableOut, height);
				heightAll += height;
				widthAll = Math.max(widthAll, width);
				rentTableIn.getParent().layout();
	
				
				//rentPanel.setSize(widthAll, heightAll);
				rightPanelScrl.setMinWidth(widthAll);
				rightPanelScrl.setExpandHorizontal(true);
				rightPanelScrl.setMinHeight(heightAll);
				rightPanelScrl.layout();
				
				data = (FormData) rightPanelScrl.getLayoutData();
				int scrollWidth = rightPanelScrl.computeSize(SWT.DEFAULT, SWT.DEFAULT).x;
				data.left = new FormAttachment(rightPanelScrl, -scrollWidth);
								
				data = (FormData) toggleComposite.getLayoutData();
				data.right.offset = scrollWidth;
				data = (FormData) someComposite.getLayoutData();
				data.left.offset = -scrollWidth;
				rightPanelScrl.getParent().layout();
				rightPanelScrl.layout();
				
				rentTableIn.setRedraw(true);
				rentTableOut.setRedraw(true);
			} else {
				saleTablePrice.setRedraw(false);
//				saleTablePercent.setRedraw(false);

				int width = 0;
				for (int i = 0; i < saleTablePrice.getColumnCount(); i++) {
					int widthColum = 0;
					saleTablePrice.getColumn(i).pack();
					int dWidth = saleTablePrice.getColumn(i).getWidth();
					widthColum = dWidth;
//					saleTablePercent.getColumn(i).pack();
					dWidth = 0;//saleTablePercent.getColumn(i).getWidth();
					widthColum = Math.max(widthColum, dWidth);
					width += widthColum;
					saleTablePrice.getColumn(i).setWidth(widthColum);
//					saleTablePercent.getColumn(i).setWidth(widthColum);
				}

				int heightAll = 0;
				int widthAll = 0;
				FormData data = (FormData) saleTablePrice.getLayoutData();
				width += saleTablePrice.getBorderWidth() * 2;
				data.left = new FormAttachment(0, rightPanelScrl.getVerticalBar().getSize().x);
				data.right = new FormAttachment(saleTablePrice, width);
				int height = saleTablePrice.getItemCount()
						* saleTablePrice.getItemHeight();
				height += saleTablePrice.getHeaderHeight();
				height += saleTablePrice.getBorderWidth() * 3;
				data.bottom = new FormAttachment(saleTablePrice, height);
				heightAll += height;
				widthAll = width;

//				data = (FormData) saleTablePercent.getLayoutData();
//				width -= saleTablePrice.getBorderWidth() * 2;
//				width += saleTablePercent.getBorderWidth() * 2;
//				data.left = new FormAttachment(0, rightPanelScrl.getVerticalBar().getSize().x);
//				data.right = new FormAttachment(saleTablePercent, width);
//				height = saleTablePercent.getItemCount()
//						* saleTablePrice.getItemHeight();
//				height += saleTablePercent.getHeaderHeight();
//				height += saleTablePercent.getBorderWidth() * 3;
//				data.bottom = new FormAttachment(saleTablePercent, height);
				heightAll += height;
				widthAll = Math.max(widthAll, width);
				saleTablePrice.getParent().layout();

				
				pricePanel.setSize(widthAll, heightAll);
				rightPanelScrl.setMinWidth(widthAll);
				rightPanelScrl.setExpandHorizontal(true);
				rightPanelScrl.setMinHeight(heightAll);
				rightPanelScrl.layout();
				
				data = (FormData) rightPanelScrl.getLayoutData();
				int scrollWidth = rightPanelScrl.computeSize(SWT.DEFAULT, SWT.DEFAULT).x;
				data.left = new FormAttachment(rightPanelScrl, -scrollWidth);
				
				scrollWidth = rightPanelScrl.computeSize(SWT.DEFAULT, SWT.DEFAULT).x;
				
				data = (FormData) toggleComposite.getLayoutData();
				data.right.offset = scrollWidth;
				data = (FormData) someComposite.getLayoutData();
				data.left.offset = -scrollWidth;
				rightPanelScrl.getParent().layout();
				
				saleTablePrice.setRedraw(true);
//				saleTablePercent.setRedraw(true);
			}
			someComposite.getParent().layout();
			someComposite.layout();
			rightPanelScrl.layout();
		}
	}

	public class RentBtnListener implements SelectionListener{


		public void widgetDefaultSelected(SelectionEvent e) {
			if (rentButton.getSelection()){
				saleButton.setSelection(false);
				rightPanelScrl.setRedraw(false);
				rentPanel.setVisible(true);
				rightPanelScrl.setContent(rentPanel);
				pricePanel.setVisible(false);
				rentMode = true;
				rightPanelScrl.setRedraw(true);
			} else {
				saleButton.setSelection(true);
				rightPanelScrl.setRedraw(false);
				rentPanel.setVisible(false);
				rightPanelScrl.setContent(pricePanel);
				pricePanel.setVisible(true);
				rentMode = false;
				rightPanelScrl.setRedraw(true);
			}
			redrawForm();
		}

		public void widgetSelected(SelectionEvent e) {
			 widgetDefaultSelected(e);
		}
		
	}
	public class SaleBtnListener implements SelectionListener{


		public void widgetDefaultSelected(SelectionEvent e) {
			if (saleButton.getSelection()){
				rentButton.setSelection(false);
				rightPanelScrl.setRedraw(false);
				rentPanel.setVisible(false);
				rightPanelScrl.setContent(pricePanel);
				pricePanel.setVisible(true);
				rentMode = false;
				rightPanelScrl.setRedraw(true);
			} else {
				rentButton.setSelection(true);
				rightPanelScrl.setRedraw(false);
				rentPanel.setVisible(true);
				rightPanelScrl.setContent(rentPanel);
				pricePanel.setVisible(false);
				rentMode = true;
				rightPanelScrl.setRedraw(true);
			}
			redraw();
			redraw();
		}

		public void widgetSelected(SelectionEvent e) {
			widgetDefaultSelected(e);
		}
		
	}

	private class SaleEditListener implements Listener {

		public void handleEvent (Event event) {
			Rectangle clientArea = saleTablePrice.getClientArea ();
			Point pt = new Point (event.x, event.y);
			if ( event.button == 1 ){
				int index = saleTablePrice.getTopIndex ();
				while (index < saleTablePrice.getItemCount ()) {
					boolean visible = false;
					final TableItem item = saleTablePrice.getItem (index);
					for (int i=0; i < saleTablePrice.getColumnCount (); i++) {
						Rectangle rect = item.getBounds (i);
						if (rect.contains (pt) ) {
							final int column = i;
							final int row = index;
							if ( i ==2 ){
								final Text text = new Text (saleTablePrice, SWT.NONE);
								Listener textListener = new Listener () {
									public void handleEvent (final Event e) {
										int val = -1;
										try {
											val = Integer.valueOf(text.getText());
										} catch (NumberFormatException nfe){
											val = -1;
										}
										SellBid sb = (SellBid) item.getData();
										switch (e.type) {
											case SWT.FocusOut:
												if ( val > 0){
													sb.setValue(val);
													sb.setEmpty(false);
													redraw();
												}
												text.dispose ();
												break;
											case SWT.Traverse:
												switch (e.detail) {
													case SWT.TRAVERSE_RETURN:
														if (val > 0){
															sb.setValue(val);
															sb.setEmpty(false);
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
								saleEditor.setEditor (text, item, i);
								text.setText (item.getText (i));
								text.selectAll ();
								text.setFocus ();
							} else {
								if ( i == 3 ){ 
									final Combo combo = new Combo(saleTablePrice, SWT.READ_ONLY);
									combo.add(MainWindow.getMessage("SalePercent"));
									combo.add(MainWindow.getMessage("SalePrice"));
									combo.select(0);
									Listener comboListener = new Listener () {
										public void handleEvent (final Event e) {
											switch (e.type) {
												case SWT.FocusOut:
													if ( combo.getSelectionIndex() != -1){
														SellBid sb = (SellBid)item.getData();
														sb.setEmpty(false);
														SellType st = (combo.getSelectionIndex() == 0) ? SellType.SL_PERCENT : SellType.SL_VALUE;
														sb.setType(st);
													}
													redraw();
													combo.dispose ();
													break;
												case SWT.Traverse:
													switch (e.detail) {
														case SWT.TRAVERSE_RETURN:
														case SWT.FocusOut:
															if ( combo.getSelectionIndex() != -1){
																if ( combo.getSelectionIndex() != -1){
																	SellBid sb = (SellBid)item.getData();
																	sb.setEmpty(false);
																	SellType st = (combo.getSelectionIndex() == 0) ? SellType.SL_PERCENT : SellType.SL_VALUE;
																	sb.setType(st);
																}
																redraw();
															}
															redraw();
															//FALL THROUGH
														case SWT.TRAVERSE_ESCAPE:
															combo.dispose ();
															e.doit = false;
													}
													break;
											}
										}
									};
									combo.addListener (SWT.FocusOut, comboListener);
									combo.addListener (SWT.Traverse, comboListener);
									saleEditor.setEditor (combo, item, i);
									combo.setText (item.getText (i));
									combo.setFocus ();
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
	
	private String printSaleType(SellType st){
		switch (st){
			case SL_PERCENT:
				return MainWindow.getMessage("SalePercent");
			case SL_VALUE:
				return MainWindow.getMessage("SalePrice");
			default:
				return "";
		}
	}

	public class BidTableMenuListener implements MenuDetectListener{

		Table listenTable;
		public BidTableMenuListener(Table tbl){
			listenTable = tbl;
		}
		public void menuDetected(MenuDetectEvent e) {
			Menu menu = new Menu (listenTable);
			MenuItem item = new MenuItem (menu, SWT.NONE);
			item.setText (MainWindow.getMessage("Clear"));
			item.addSelectionListener(new SelectionListener() {
				
				public void widgetSelected(SelectionEvent e) {
					widgetDefaultSelected(e);
					
				}
				
				public void widgetDefaultSelected(SelectionEvent e) {
					int i = listenTable.getSelectionIndex();
					((PlayerBid)listenTable.getItem(i).getData()).setEmpty(true);
					((PlayerBid)listenTable.getItem(i).getData()).setNumber(0);
					redraw();
					
				}
			});
			
			menu.setLocation(e.x, e.y);
			menu.setVisible (true);
		}
		
	}

	private class RentOutEditListener implements Listener {

		public void handleEvent (Event event) {
			Rectangle clientArea = rentTableOut.getClientArea ();
			Point pt = new Point (event.x, event.y);
			if ( event.button == 1 ){
				int index = rentTableOut.getTopIndex ();
				while (index < rentTableOut.getItemCount ()) {
					boolean visible = false;
					final TableItem item = rentTableOut.getItem (index);
					for (int i=0; i < rentTableOut.getColumnCount (); i++) {
						Rectangle rect = item.getBounds (i);
						if (rect.contains (pt) ) {
							final int column = i;
							final int row = index;
							if ( column == 2 ){
								final Text text = new Text (rentTableOut, SWT.NONE);
								Listener textListener = new Listener () {
									public void handleEvent (final Event e) {
										RentBid rb = (RentBid) item.getData();
										switch (e.type) {
											case SWT.FocusOut:
												rb.setTeam(text.getText());
												rb.setEmpty(false);
												redraw();
												text.dispose ();
												break;
											case SWT.Traverse:
												switch (e.detail) {
													case SWT.TRAVERSE_RETURN:
														rb.setTeam(text.getText());
														rb.setEmpty(false);
														redraw();
														//FALL THROUGH
													case SWT.TRAVERSE_ESCAPE:
														text.dispose ();
														e.doit = false;
												}
												break;
										}
									}
								};
								text.addListener (SWT.FocusOut, textListener);
								text.addListener (SWT.Traverse, textListener);
								text.addListener (SWT.Verify, textListener);
								text.setTextLimit(5);
								rentOutEditor.setEditor (text, item, i);
								text.setText (item.getText (i));
								text.selectAll ();
								text.setFocus ();
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
			PlayerActions actions = PlayerActionsReader.readPlayerActionsFile(fname);
			List<PlayerBid> bids = actions.getBids();
			PlayerBid pb;
			rentBidsIn = new ArrayList<RentBid>(BID_SIZE);
			rentBidsOut = new ArrayList<RentBid>(BID_SIZE);
			saleBids = new ArrayList<SellBid>(BID_SIZE);
			int m = 0;
			int j = 0;
			int k = 0;
			for (int i = 0; i < bids.size(); i++) {
				pb = bids.get(i);
				if ( pb.getClass() == SellBid.class  && m < 5){
					saleBids.add((SellBid)pb);
					m++;
				} else if (pb.getClass() == RentBid.class){
					if (((RentBid)pb).getType() == RentType.RT_TAKE && j < 5){
						rentBidsIn.add((RentBid)pb);
						j++;
					} else if ( k < 5 ){
						rentBidsOut.add((RentBid)pb);
						k++;
					}
				}
			}
			while (m < 5){
				saleBids.add(new SellBid(SellType.SL_VALUE, 0, 0));
				m++;
			}
			while (j < 5){
				rentBidsIn.add(new RentBid(RentType.RT_TAKE, "", 0));
				j++;
			}
			while (k < 5){
				rentBidsOut.add(new RentBid(RentType.RT_OFFER, "", 0));
				k++;
			}
			for (int i = 0; i < BID_SIZE; i++) {
				rentTableIn.getItem(i).setData(rentBidsIn.get(i));
				rentTableOut.getItem(i).setData(rentBidsOut.get(i));
				saleTablePrice.getItem(i).setData(saleBids.get(i));
			}

		} catch (ReaderException e) {
			e.printStackTrace();
		}
		redraw();
	} 

	public void saveSale(String fname) {
		if (fname == null) {
			return;			
		}
		try {
			PlayerActions pa;
			if (rentMode){
				List<PlayerBid> concatBids = new ArrayList<PlayerBid>(2*BID_SIZE);
				for (int i = 0; i < BID_SIZE; i++) {
					concatBids.add(rentBidsIn.get(i));
					concatBids.add(rentBidsOut.get(i));
				}
				pa = new PlayerActions(concatBids, "");
			} else {
				List<PlayerBid> concatBids = new ArrayList<PlayerBid>(BID_SIZE);
				for (int i = 0; i < BID_SIZE; i++) {
					concatBids.add(saleBids.get(i));
				}
				pa = new PlayerActions(concatBids, "");
			}
			pa.setPassword(password);
			PlayerActionsWriter.writePlayerActionsFile(fname, pa);
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
		
		String ext[] = new String[1];
		ext[0] = MainWindow.getMessage("FilterExtensionRequest");
		dlg.setFilterExtensions(ext);
		String extNames[] = new String[1];
		extNames[0] = MainWindow.getMessage("FilterExtensionRequestName");
		dlg.setFilterNames(extNames);
		String result = dlg.open();
		if (result != null) {
			this.saveSale(result);
			redraw();
		}
	}

	/*public void updateGameForm(GameForm gameForm) {
		this.gameForm = gameForm;
		updateTeam();
	}*/

	public void updatePassword(String password) {
		this.password = password;
	}

	public void updateDaysOfRest(int daysOfRest) {
		// TODO Auto-generated method stub
		
	}

	public void updateMessages() {
		topComposite.setVisible(false);
		
		mainItem.setText(MainWindow.getMessage("saleEditorTabName"));
		
		openButton.setText(MainWindow.getMessage("global.open"));
		saveButton.setText(MainWindow.getMessage("global.save"));
		
		String[] titles = { MainWindow.getMessage("PlayerNumber"),
				MainWindow.getMessage("PlayerPosition"),
				MainWindow.getMessage("PlayerName"),
				MainWindow.getMessage("PlayerPrice"),
				MainWindow.getMessage("PlayerSalary"),
				MainWindow.getMessage("PlayerGames"),
				MainWindow.getMessage("PlayerGoalsTotal"),
				MainWindow.getMessage("PlayerChampGoals"),
				MainWindow.getMessage("PlayerGoalsMissed"),
				MainWindow.getMessage("PlayerAssists"),
				MainWindow.getMessage("PlayerProfit"),
				MainWindow.getMessage("PlayerMark"),
				MainWindow.getMessage("PlayerGamesCareer"),
				MainWindow.getMessage("PlayerGoalsCareer"),
				MainWindow.getMessage("PlayerState"),
				MainWindow.getMessage("PlayerYellowCards"),
				MainWindow.getMessage("PlayerRedCards"),
				MainWindow.getMessage("PlayerHomeClub"),
				MainWindow.getMessage("PlayerBirthTour"),
				MainWindow.getMessage("PlayerBirthDate") };

		teamTable.setRedraw(false);
		for (int i = 0; i < titles.length; i++) {
			teamTable.getColumn(i).setText("1234");
			teamTable.getColumn(i).pack();
			teamTable.getColumn(i).setText(titles[i]);
			teamTable.getColumn(i).setResizable(false);
		}
		teamTable.setRedraw(true);

		saleButton.setText(MainWindow.getMessage("SaleToggleSale"));
		saleButton.getParent().layout();
		rentButton.setText(MainWindow.getMessage("SaleToggleRent"));
		saleButton.getParent().layout();
		
		String[] titlesRentIn = { MainWindow.getMessage("PlayerNumber"),
				MainWindow.getMessage("Club"),
				"    "};

		rentTableIn.setRedraw(false);
		for (int i = 0; i < titlesRentIn.length; i++) {
			rentTableIn.getColumn(i).setText("1234");
			rentTableIn.getColumn(i).pack();
			rentTableIn.getColumn(i).setText(titlesRentIn[i]);
			rentTableIn.getColumn(i).setResizable(false);
		}
		rentTableIn.setRedraw(true);

		String[] titlesRentOut = { MainWindow.getMessage("PlayerNumber"),
				MainWindow.getMessage("PlayerName"),
				MainWindow.getMessage("Club")};

		rentTableOut.setRedraw(false);
		for (int i = 0; i < titlesRentOut.length; i++) {
			rentTableOut.getColumn(i).setText("1234");
			rentTableOut.getColumn(i).pack();
			rentTableOut.getColumn(i).setText(titlesRentOut[i]);
			rentTableOut.getColumn(i).setResizable(false);
		}
		rentTableOut.setRedraw(true);
		
		String[] titlesPrice = { MainWindow.getMessage("PlayerNumber"),
				MainWindow.getMessage("PlayerName"),
				MainWindow.getMessage("SalePrice"),
				MainWindow.getMessage("SaleType")};

		saleTablePrice.setRedraw(false);
		for (int i = 0; i < titlesPrice.length; i++) {
			saleTablePrice.getColumn(i).setText("1234");
			saleTablePrice.getColumn(i).pack();
			saleTablePrice.getColumn(i).setText(titlesPrice[i]);
			saleTablePrice.getColumn(i).setResizable(false);
		}
		saleTablePrice.setRedraw(true);

		topComposite.layout();
		salePanel.layout();
		pricePanel.layout();
		gridComposite.layout();
		saveComposite.layout();
		toggleComposite.layout();
		someComposite.layout();
		rentPanel.layout();
		redraw();
		topComposite.setVisible(true);
	}

}
