package com.fa13.build.view;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.dnd.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

import com.fa13.build.controller.io.ReaderException;
import com.fa13.build.controller.io.TransferListReader;
import com.fa13.build.model.Transfer;
import com.fa13.build.model.TransferBid;
import com.fa13.build.model.TransferList;
import com.fa13.build.model.TransferPlayer;

public class TransferView implements UIItem {

	TabFolder parentInstance;
	TabItem mainItem;

	ScrolledComposite rightPanel;
	Composite rightComposite;
	Composite mainComposite;
	Composite topPanel;
	Composite bottomPanel;
	Button[] viewBtns;
	Table tableRequest;
	Table tableTList;
	TransferList tList = null;

	Button openTListButton;
	SimpleDateFormat dateFormat;
	Label dateLabel;
	List<TransferPlayer> playerList;
	Listener paintListener;
	Transfer transferBids;
	TransferBid[] bidList;
	TableEditor editor;

	public TransferView(TabFolder parent) {
		parentInstance = parent;

		transferBids = new Transfer();
		if (MainWindow.getAllInstance() != null) {
			updateAll();
		}
		mainItem = new TabItem(parentInstance, SWT.NONE);
		mainItem.setText(MainWindow.getMessage("transferTabName"));

		FormLayout formLayout = new FormLayout();
		mainComposite = new Composite(parent, SWT.NONE);
		mainItem.setControl(mainComposite);
		mainComposite.setRedraw(false);
		mainComposite.setLayout(formLayout);

		bottomPanel = new Composite(mainComposite, SWT.BORDER);
		topPanel = new Composite(mainComposite, SWT.BORDER);

		FormData data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0);
		data.bottom = new FormAttachment(bottomPanel, -3);
		data.top = new FormAttachment(0, 0);
		topPanel.setLayoutData(data);

		data = new FormData();
		data.bottom = new FormAttachment(100, 0);
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0);
		bottomPanel.setLayoutData(data);

		bottomPanel.setLayout(new GridLayout(10, false));

		GridData gridData = new GridData(SWT.BEGINNING, SWT.CENTER, false,
				false);
		viewBtns = new Button[2];
		viewBtns[0] = new Button(bottomPanel, SWT.TOGGLE);
		viewBtns[0].setText(MainWindow.getMessage("transferPanelButtonName"));
		viewBtns[0].pack();
		viewBtns[0].setLayoutData(gridData);
		viewBtns[0].setSelection(true);

		gridData = new GridData(SWT.BEGINNING, SWT.CENTER, false, false);
		viewBtns[1] = new Button(bottomPanel, SWT.TOGGLE);
		viewBtns[1].setText(MainWindow.getMessage("transferTradeInPanelButtonName"));
		viewBtns[1].pack();
		viewBtns[1].setLayoutData(gridData);

		topPanel.setLayout(new FormLayout());

		rightPanel = new ScrolledComposite(topPanel, SWT.H_SCROLL
				| SWT.V_SCROLL);
		rightPanel.setExpandHorizontal(false);
		rightPanel.setExpandVertical(true);
		rightComposite = new Composite(rightPanel, SWT.NONE);
		rightPanel.setContent(rightComposite);
		data = new FormData();
		data.bottom = new FormAttachment(100, 0);
		data.top = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0);
		data.left = new FormAttachment(0, 0);
		rightPanel.setLayoutData(data);

		data = new FormData();
		rightComposite.setLayoutData(data);
		rightComposite.setLayout(new FormLayout());

		tableRequest = new Table(rightComposite, SWT.BORDER);
		tableRequest.setHeaderVisible(true);
		tableRequest.setLinesVisible(true);

		String[] titles = { MainWindow.getMessage("PlayerName"),
				MainWindow.getMessage("PlayerPrice") };

		for (int i = 0; i < titles.length; i++) {
			TableColumn column = new TableColumn(tableRequest, SWT.NONE);
			// column.setText("1234");
			// column.pack();
			column.setText(titles[i]);
			column.pack();
			column.setResizable(false);
		}
		tableRequest.setItemCount(20);
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		Point size = tableRequest.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		data.right = new FormAttachment(tableRequest, size.x);
		data.top = new FormAttachment(0, 0);
		tableRequest.setLayoutData(data);

		tableTList = new Table(topPanel, SWT.BORDER | SWT.FULL_SELECTION);
		tableTList.setHeaderVisible(true);
		tableTList.setLinesVisible(true);

		data = new FormData();
		data.bottom = new FormAttachment(100, 0);
		data.top = new FormAttachment(0, 0);
		data.right = new FormAttachment(rightPanel, -5);
		data.left = new FormAttachment(0, 0);
		tableTList.setLayoutData(data);

		String[] titlesTList = { MainWindow.getMessage("PlayerNumber"),
				MainWindow.getMessage("PlayerPosition"),
				MainWindow.getMessage("PlayerAge"),
				MainWindow.getMessage("PlayerTalant"),
				MainWindow.getMessage("PlayerStrength"),
				MainWindow.getMessage("PlayerHealth"),
				MainWindow.getMessage("PlayerAbilities"),
				MainWindow.getMessage("PlayerSalary"),
				MainWindow.getMessage("PlayerPrice"),
				MainWindow.getMessage("PlayerCountry"),
				MainWindow.getMessage("PlayerName"),
				MainWindow.getMessage("PlayerHomeClub") };

		for (int i = 0; i < titlesTList.length; i++) {
			TableColumn column = new TableColumn(tableTList, SWT.NONE);
			column.setText("1234");
			// column.pack();
			column.setText(titlesTList[i]);
			column.setResizable(false);
		}

		Label fakeLabel = new Label(bottomPanel, SWT.NONE);
		gridData = new GridData(SWT.BEGINNING, SWT.CENTER, true, false);
		fakeLabel.setLayoutData(gridData);

		dateLabel = new Label(bottomPanel, SWT.NONE);

		openTListButton = new Button(bottomPanel, SWT.PUSH);
		openTListButton.setText(MainWindow.getMessage("openTListButton"));

		gridData = new GridData(SWT.END, SWT.CENTER, false, false);
		openTListButton.setLayoutData(gridData);
		openTListButton.addSelectionListener(new OpenTlistSelectionListner());

		dateLabel.setText("Date");
		gridData = new GridData(SWT.END, SWT.CENTER, false, false);
		dateLabel.setLayoutData(gridData);

		dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		final Image img = new Image(parent.getDisplay(), this.getClass()
				.getResourceAsStream("/com/fa13/build/resources/flags/not.png"));
		paintListener = new Listener() {
			public void handleEvent(Event event) {
				switch (event.type) {
				case SWT.MeasureItem: {
					if (event.index == 10) {
						Rectangle rect = img.getBounds();
						event.width = rect.width;
						event.height = Math.max(event.height, rect.height + 2);
					} else {
						TableItem item = (TableItem) event.item;
						String text = item.getText(event.index);
						int size = text.length();
						text = "";
						for (int i = 0; i < size + 1; i++) {
							text += "A";
						}
						Point extent = event.gc.stringExtent(text);
						// TODO: Remove Magic number 12 !!!
						if (event.index != 6) {
							event.width = Math.max(event.width, extent.x);
						}
					}
					break;
				}
				case SWT.PaintItem: {
					if (event.index == 9) {
						int x = event.x;
						Rectangle rect = img.getBounds();
						int yoffset = Math.max(0,
								(event.height - rect.height) / 2);
						event.gc
								.drawImage(
										(Image) event.item.getData(),
										x
												+ (tableTList.getColumn(9)
														.getWidth() - rect.width)
												/ 2, event.y + yoffset);
					}
					break;
				}
				}
			}
		};

		tableTList.addListener(SWT.PaintItem, paintListener);
		tableTList.addListener(SWT.MeasureItem, paintListener);

		editor = new TableEditor(tableRequest);
		editor.horizontalAlignment = SWT.LEFT;
		editor.grabHorizontal = true;
		editor.minimumWidth = 35;

		tableRequest.addListener(SWT.MouseDown, new TListTableListner());

		mainComposite.setRedraw(true);
		redrawList();

		setDragDropSource(tableTList);
		setDragDropTarget(tableRequest);
		viewBtns[0].setVisible(false);
		viewBtns[1].setVisible(false);
	}

	public void openTlistDlg() {
		FileDialog dlg = new FileDialog(parentInstance.getShell(), SWT.OPEN);

		String ext[] = new String[1];
		ext[0] = MainWindow.getMessage("FilterExtensionTlist");
		dlg.setFilterExtensions(ext);
		String extNames[] = new String[1];
		extNames[0] = MainWindow.getMessage("FilterExtensionTlistName");
		dlg.setFilterNames(extNames);
		String result = dlg.open();
		if (result != null) {
			openTList(result);
		}
	}

	public void openTList(String fname) {
		if (fname == null) {
			return;
		}
		try {
			tList = TransferListReader.readTransferListFile(fname);
			redrawList();
		} catch (ReaderException e) {
			e.printStackTrace();
			// TODO: Add error message
		}
	}

	private void redrawList() {
		if (tList == null) {
			topPanel.setVisible(false);
			tableTList.setVisible(false);
			tableTList.setEnabled(false);
			dateLabel.setVisible(false);
			return;
		}
		Date date = tList.getDate();
		dateLabel.setText(dateFormat.format(date));
		dateLabel.pack();
		bottomPanel.layout();
		playerList = tList.getPlayers();
		tableTList.setRedraw(false);
		tableTList.setItemCount(playerList.size());
		int curr = 0;
		for (TransferPlayer player: playerList) {
			TableItem item = tableTList.getItem(curr++);
			int index = 0;
			item.setText(index++, String.valueOf(player.getTransferID()));
			item.setText(index++, player.getPosition().toString());
			item.setText(index++, String.valueOf(player.getAge()));
			item.setText(index++, String.valueOf(player.getTalent()));
			item.setText(index++, String.valueOf(player.getStrength()));
			item.setText(index++, String.valueOf(player.getHealth()));
			item.setText(index++, player.getAbilities());
			item.setText(index++, String.valueOf(player.getSalary()));
			item.setText(index++, String.valueOf(player.getPrice()));
			item.setText(index++, "");
			item.setData(GameFormEditView.flags
					.get(player.getNationalityCode()));
			item.setText(index++, player.getName());
			item.setText(index++, player.getPreviousTeam());
		}
		tableTList.setRedraw(true);
		tableTList.setEnabled(true);
		tableTList.setVisible(true);
		dateLabel.setVisible(false);

		for (int i = 0; i < tableTList.getColumnCount(); i++) {
			TableColumn column = tableTList.getColumn(i);
			column.pack();
		}

		redrawBids();
		topPanel.setVisible(true);

	}

	private void redrawBids() {
		if (transferBids == null) {
			tableRequest.setVisible(false);
			return;
		}
		tableRequest.setVisible(false);
		bidList = transferBids.getBids();
		if (bidList == null) {
			return;
		}
		tableRequest.setRedraw(false);

		for (int i = 0; i < bidList.length; i++) {
			TransferBid player = bidList[i];
			TableItem item = tableRequest.getItem(i);
			if (player != null) {

				int index = 0;
				item.setText(index++, player.getName());
				item.setText(index++, String.valueOf(player.getPrice()));
			}
			item.setData("number", Integer.valueOf(i));
		}
		tableRequest.setRedraw(true);

		int width = 0;
		for (int i = 0; i < tableRequest.getColumnCount(); i++) {
			tableRequest.getColumn(i).pack();
			int widthColum = tableRequest.getColumn(i).getWidth();
			width += widthColum;
		}

		int heightAll = 0;
		int widthAll = 0;
		FormData data = (FormData) tableRequest.getLayoutData();
		width += tableRequest.getBorderWidth() * 2;
		data.left = new FormAttachment(0,
				rightPanel.getVerticalBar().getSize().x);
		data.right = new FormAttachment(tableRequest, width);
		int height = tableRequest.getItemCount() * tableRequest.getItemHeight();
		height += tableRequest.getHeaderHeight();
		height += tableRequest.getBorderWidth() * 3;
		data.bottom = new FormAttachment(tableRequest, height);
		heightAll += height;
		widthAll = width;

		widthAll = Math.max(widthAll, width);
		tableRequest.getParent().layout();

		rightComposite.setSize(widthAll
				+ rightPanel.getVerticalBar().getSize().x, heightAll);

		rightPanel.setMinWidth(widthAll);
		rightPanel.setExpandHorizontal(true);
		rightPanel.setMinHeight(heightAll);
		rightPanel.layout();
		
		data = (FormData) rightPanel.getLayoutData();
		int scrollWidth = rightPanel.computeSize(SWT.DEFAULT, SWT.DEFAULT).x;
		data.left = new FormAttachment(100, -scrollWidth);
		
		rightPanel.getParent().layout();
		tableRequest.setVisible(true);

	}

	public class OpenTlistSelectionListner implements SelectionListener {

		public void widgetSelected(SelectionEvent e) {
			widgetDefaultSelected(e);
		}

		public void widgetDefaultSelected(SelectionEvent e) {
			openTlistDlg();
		}
	}

	public void updateAll() {
		// TODO Auto-generated method stub

	}

	public void updatePassword(String password) {
		// TODO Auto-generated method stub

	}

	public void updateMessages() {
		// TODO Auto-generated method stub

	}

	public void setDragDropSource(final Table table) {
		org.eclipse.swt.dnd.Transfer[] types = new org.eclipse.swt.dnd.Transfer[] { TextTransfer
				.getInstance() };
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
		org.eclipse.swt.dnd.Transfer types[] = new org.eclipse.swt.dnd.Transfer[] { textTransfer };
		target.setTransfer(types);

		target.addDropListener(new DropTargetAdapter() {

			public void drop(DropTargetEvent event) {
				if (textTransfer.isSupportedType(event.currentDataType)) {
					TableItem item = null;
					if (event.item instanceof TableItem) {
						String text = (String) event.data;
						item = (TableItem) event.item;
						setPlayer(item, text);
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
		if (number != null) {
			if (0 <= number && number <= 19) {
				bidList[number] = new TransferBid();
				bidList[number].setName(playerItems[10]);
			}
		}
		redrawBids();
	}

	@Override
	public void redraw() {
		redrawBids();
		redrawList();
	}

	public class TListTableListner implements Listener {

		private int index;
		private Spinner spinner;

		@Override
		public void handleEvent(Event event) {
			TableItem item = null;
			int count = tableRequest.getItemCount();
			index = 0;
			while (item == null && index < count) {
				TableItem tmpItem = tableRequest.getItem(index);
				if (tmpItem.getBounds(1).contains(event.x, event.y)) {
					item = tmpItem;
				}
				index++;
			}
			if (item != null) {
				index = (Integer) item.getData("number");
				if (bidList[index] != null) {
					spinner = new Spinner(tableRequest, SWT.NONE);
					spinner.addModifyListener(new ModifyListener() {

						@Override
						public void modifyText(ModifyEvent e) {
							bidList[index].setPrice(spinner.getSelection());

						}
					});
					spinner.addFocusListener(new FocusListener() {

						@Override
						public void focusGained(FocusEvent e) {
							// TODO Auto-generated method stub

						}

						@Override
						public void focusLost(FocusEvent e) {
							spinner.dispose();
							redrawBids();
						}
					});

					spinner.setValues(bidList[index].getPrice(), 0, 1000000, 0,
							1, 10);
					editor.setEditor(spinner, item, 1);
					spinner.setFocus();
				}
			}
		}

	}

}
