package com.fa13.build.view;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import com.fa13.build.controller.io.AllReader;
import com.fa13.build.controller.io.ReaderException;
import com.fa13.build.model.All;
import com.fa13.build.model.Team;

public class MainWindow {
	
	private static final String SETTINGS_FILENAME = "settings.xml";
	private static final String DEFAULT_BUNDLE = 
			"/com/fa13/build/resources/properties/MessagesBundle_en_US.properties";

	//private Image ICON_OPEN;

	String password;
	Properties settings;
	Properties defaultSettings;

	ArrayList<UIItem> uiItems;
	TrainingView trainingView;
	GameEditView gameEditView;
	//ClubInfoView clubInfoView;
	SaleView saleView;
	TransferView transferView;
	StatisticsView statisticsView;

	Display display;

	Shell shell;

	TabFolder tabFolder;

	Combo teamCombo;

	Button openButton;
	Button settingsButton;

	CoolBar coolBar;
	CoolItem coolItem;

	private static ResourceBundle messageInstance;
	private static All allInstance;
	
	
	Label teamLabel;
	Label pwdLabel;
	Text pwdEditor;
	Label dateLabel;
	SimpleDateFormat dateFormat;

	SettingsView settingsDialog;

	Composite topComposite;

	public MainWindow() throws IOException {
		defaultSettings = new Properties();
		defaultSettings.setProperty("lang", DEFAULT_BUNDLE);

		settings = new Properties(defaultSettings);
		try {
			settings.loadFromXML(new FileInputStream(SETTINGS_FILENAME));
		} catch (Exception e) {
			settings = defaultSettings;
		}

		settings.storeToXML(new FileOutputStream(SETTINGS_FILENAME),
				"Fa13Manager Settings");

		String langPropName = settings.getProperty("lang", DEFAULT_BUNDLE);
		InputStream stream = this.getClass().getResourceAsStream(langPropName);
		InputStreamReader readerIs = new InputStreamReader(stream, "UTF-8");

		messageInstance = new PropertyResourceBundle(readerIs);

		display = new Display();
		// ICON_OPEN = new Image(display,
		// MainWindow.class.getResourceAsStream("/com/fa13/build/resources/Open-icon.png"));
		shell = new Shell(display);
		shell.setMaximized(true);

		GridLayout gridLayout = new GridLayout(1, true);
		GridData gridData;

		shell.setLayout(gridLayout);

		topComposite = new Composite(shell, SWT.BORDER);
		topComposite.setLayout(new GridLayout(7, false));

		openButton = new Button(topComposite, SWT.PUSH);
		gridData = new GridData(GridData.BEGINNING, GridData.CENTER, false,
				false);
		
		// openButton.setImage(ICON_OPEN);
		openButton.setLayoutData(gridData);
		openButton.addSelectionListener(new OpenButtonListner());

		teamLabel = new Label(topComposite, SWT.NONE);
		teamLabel.setText(messageInstance.getString("global.team"));
		teamLabel.setLayoutData(new GridData(GridData.BEGINNING,
				GridData.CENTER, false, false));

		teamCombo = new Combo(topComposite, SWT.DROP_DOWN | SWT.READ_ONLY);
		teamCombo.add(messageInstance.getString("global.team"));
		teamCombo.select(0);
		gridData = new GridData(GridData.BEGINNING, GridData.CENTER, false,
				false);
		gridData.widthHint = 150;
		teamCombo.setLayoutData(gridData);
		teamCombo.addSelectionListener(new TeamComboListener());
		teamCombo.addModifyListener((ModifyListener) new TeamComboListener());

		pwdLabel = new Label(topComposite, SWT.NONE);
		pwdLabel.setText(messageInstance.getString("global.password"));
		pwdLabel.setLayoutData(new GridData(GridData.BEGINNING,
				GridData.CENTER, false, false));
		gridData = new GridData(GridData.BEGINNING, GridData.CENTER, false,
				false);
		gridData.horizontalIndent = 15;
		pwdLabel.setLayoutData(gridData);

		pwdEditor = new Text(topComposite, SWT.PASSWORD | SWT.BORDER);
		gridData = new GridData(GridData.BEGINNING, GridData.CENTER, false,
				false);
		gridData.widthHint = 150;
		pwdEditor.setLayoutData(gridData);
		pwdEditor.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				password = pwdEditor.getText();
				updateTabsPassword();
			}
		});

		dateLabel = new Label(topComposite, SWT.NONE);
		Date date = new Date();

		dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		dateLabel.setText(dateFormat.format(date));
		dateLabel.setLayoutData(new GridData(GridData.CENTER, GridData.CENTER,
				true, false));

		settingsDialog = new SettingsView(shell);

		settingsButton = new Button(topComposite, SWT.PUSH);
		gridData = new GridData(GridData.BEGINNING, GridData.CENTER, false,
				false);
		settingsButton.setText(messageInstance.getString("settingsButton"));
		settingsButton.pack();
		settingsButton.setLayoutData(gridData);
		settingsButton.setVisible(true);

		settingsButton.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				widgetDefaultSelected(e);
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				settingsDialog.open(messageInstance, settings);
				setSettings(settings);
			}
		});

		topComposite.pack();

		gridData = new GridData(GridData.FILL, GridData.FILL, true, false);
		topComposite.setLayoutData(gridData);

		uiItems = new ArrayList<UIItem>();
		tabFolder = new TabFolder(shell, SWT.NONE);
		gameEditView = new GameEditView(tabFolder, this);
		uiItems.add(gameEditView);
		trainingView = new TrainingView(tabFolder);
		uiItems.add(trainingView);
		saleView = new SaleView(tabFolder);
		uiItems.add(saleView);
		transferView = new TransferView(tabFolder);
		uiItems.add(transferView);
//		clubInfoView = new ClubInfoView(tabFolder);
//		uiItems.add(clubInfoView);
		statisticsView = new StatisticsView(tabFolder);
		uiItems.add(statisticsView);
		gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		tabFolder.setLayoutData(gridData);
		
		updateMessages();
		
		shell.setMinimumSize(800, 450);
		shell.setText("FA13 Jogador");
		shell.open();
		updateTabsAll();
		if (settings.getProperty("allPath") == null) {
			this.openAllDlg();
		} else {
			this.openAll(settings.getProperty("allPath"));
		}
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	public class OpenButtonListner implements SelectionListener {

		public void widgetDefaultSelected(SelectionEvent arg0) {
			openAllDlg();
		}

		public void widgetSelected(SelectionEvent arg0) {
			openAllDlg();
		}
	}

	public void openAllDlg() {
		FileDialog dlg = new FileDialog(shell, SWT.OPEN);

		String ext[] = new String[2];
		ext[0] = messageInstance.getString("FilterExtensionALLZIP");
		ext[1] = messageInstance.getString("FilterExtensionALL");
		dlg.setFilterExtensions(ext);
		String extNames[] = new String[2];
		extNames[0] = messageInstance.getString("FilterExtensionALLZIPName");
		extNames[1] = messageInstance.getString("FilterExtensionALLName");
		dlg.setFilterNames(extNames);
		String result = dlg.open();
		if (result != null) {
			if (this.openAll(result)) {
				settings.setProperty("allPath", result);
				saveSettings();
			}
			updateView();
		} else {
			updateTabsAll();
		}
	}

	public void saveSettings() {
		try {
			settings.storeToXML(new FileOutputStream(SETTINGS_FILENAME),
					"Fa13Manager Settings");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean openAll(String fname) {
		if (fname == null) {
			return false;
		}
		boolean result = true;
		try {
			allInstance = AllReader.readAllFile(fname);
		} catch (ReaderException e) {
			MessageBox dlg = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
			dlg.setText("Reader Error");
			dlg.setMessage("Error while reading all file");
			dlg.open();
			result = false;
		}
		updateView();
		updateTabsAll();
		return result;
	}

	public void updateView() {
		teamCombo.removeAll();
		if (allInstance == null) {
			return;
		}
		String defaultTeam = settings.getProperty("currentTeam");
		int i = 0;
		int index = 0;
		for (Team team: allInstance.getTeams()) {
			teamCombo.add(team.getName());
			if (defaultTeam != null
					&& defaultTeam.compareTo(team.getName()) == 0) {
				index = i;
			}
			++i;
		}
		teamCombo.select(index);

		dateLabel.setText(dateFormat.format(allInstance.getDate()));
	}

	public void teamChanged() {
		int index = teamCombo.getSelectionIndex();
		if (index >= 0) {
			if (allInstance != null) {
				allInstance.setCurrentTeam(index);
				Team team = allInstance.getCurrentTeam();
				if (team != null) {
					settings.setProperty("currentTeam", team.getName());
					saveSettings();
				}
			}
		}
		updateTabsAll();
	}

	public class TeamComboListener implements SelectionListener, ModifyListener {

		public void widgetDefaultSelected(SelectionEvent arg0) {
			teamChanged();
		}

		public void widgetSelected(SelectionEvent arg0) {
			teamChanged();
		}

		public void modifyText(ModifyEvent arg0) {
			teamChanged();
		}
	}

	public void updateTabsAll() {
		if (allInstance == null) {
			tabFolder.setEnabled(false);
			tabFolder.setVisible(false);
			teamCombo.setEnabled(false);
			pwdEditor.setEnabled(false);
			dateLabel.setVisible(false);
		} else {
			tabFolder.setEnabled(true);
			tabFolder.setVisible(true);
			teamCombo.setEnabled(true);
			pwdEditor.setEnabled(true);
			dateLabel.setVisible(true);
			for (UIItem uiItem: uiItems) {
				if (uiItem != null) {
					uiItem.updateAll();
				}
			}
		}
	}

	public void updateTabsPassword() {
		for (UIItem uiItem: uiItems) {
			if (uiItem != null) {
				uiItem.updatePassword(password);
			}
		}
	}

	public Properties getSettings() {
		return settings;
	}

	public void setSettings(Properties settings) {
		this.settings = settings;
		String langPropName = settings.getProperty("lang", DEFAULT_BUNDLE);
		InputStream stream = this.getClass().getResourceAsStream(langPropName);

		try {
			InputStreamReader readerIs = new InputStreamReader(stream, "UTF-8");
			messageInstance = new PropertyResourceBundle(readerIs);
		} catch (IOException e1) {
			stream = this.getClass().getResourceAsStream(DEFAULT_BUNDLE);
			try {
				InputStreamReader readerIs = new InputStreamReader(stream, "UTF-8");
				messageInstance = new PropertyResourceBundle(readerIs);
			} catch (IOException e2) {
				e1.printStackTrace();
			}
		}
		updateMessages();
		saveSettings();
	}

	
	public void updateMessages() {
		openButton.setText(messageInstance.getString("global.open"));
		teamLabel.setText(messageInstance.getString("global.team"));
		pwdLabel.setText(messageInstance.getString("global.password"));
		settingsButton.setText(messageInstance.getString("settingsButton"));
		topComposite.layout();
		
		for (UIItem uiItem: uiItems) {
			if (uiItem != null) {
				uiItem.updateMessages();
			}
		}
	}
	
	public static All getAllInstance() {
		return allInstance;
	}
	
	public static ResourceBundle getMessageInstance() {
		return messageInstance;
	}
	
	public static String getMessage(String key) {
		return messageInstance.getString(key);
	}
}
