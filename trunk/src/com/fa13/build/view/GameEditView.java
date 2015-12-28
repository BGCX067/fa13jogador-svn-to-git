package com.fa13.build.view;

import java.io.IOException;
import java.util.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

import com.fa13.build.controller.io.*;
import com.fa13.build.model.*;

public class GameEditView implements UIItem {

	String password;
	TabItem mainItem;
	Composite mainComposite;
	Composite topPanels[];
	Composite bottomPanel;
	Slider restScale;
	Text restText;
	Label restLabel;
	Button viewBtns[];
	TabFolder parentInstance;
	GameFormEditView formEditView;
	GameSchemeEditView schemeEditView;
	GameChangeEditView changeEditView;
	Combo matchType;
	Combo matchRound;
	MainWindow mainWnd;
	Button openButton;
	Button saveButton;
	GameForm gameFormInstance;
	static final int REST_SLIDER_MAX = 30;// 10=some specific

	static final String[] roundTypes = { "тур-1", "тур-2", "тур-3", "тур-4",
			"тур-5", "тур-6", "тур-7", "тур-8", "тур-9", "тур-10", "тур-11",
			"тур-12", "тур-13", "тур-14", "тур-15", "тур-16", "тур-17",
			"тур-18", "тур-19", "тур-20", "тур-21", "тур-22", "тур-23",
			"тур-24", "тур-25", "тур-26", "тур-27", "тур-28", "тур-29",
			"тур-30", "квали - 1 игра", "квали - 2 игра", "1/64 ф - 1 игра",
			"1/64 ф - 2 игра", "1/32 ф - 1 игра", "1/32 ф - 2 игра",
			"1/16 ф - 1 игра", "1/16 ф - 2 игра", "1/8 ф - 1 игра",
			"1/8 ф - 2 игра", "1/4 ф - 1 игра", "1/4 ф - 2 игра",
			"1/2 ф - 1 игра", "1/2 ф - 2 игра", "финал" };

	public GameEditView(TabFolder parent, MainWindow mainWnd) {
		gameFormInstance = new GameForm();
		this.mainWnd = mainWnd;
		parentInstance = parent;
		mainItem = new TabItem(parent, SWT.NONE);
		mainItem.setText(MainWindow.getMessage("gameEditorTabName"));

		FormLayout formLayout = new FormLayout();
		mainComposite = new Composite(parent, SWT.NONE);
		mainItem.setControl(mainComposite);
		mainComposite.setRedraw(false);
		mainComposite.setLayout(formLayout);

		bottomPanel = new Composite(mainComposite, SWT.BORDER);
		topPanels = new Composite[3];

		topPanels[0] = new Composite(mainComposite, SWT.BORDER);
		topPanels[1] = new Composite(mainComposite, SWT.BORDER);
		topPanels[2] = new Composite(mainComposite, SWT.BORDER);

		formEditView = new GameFormEditView(topPanels[0], gameFormInstance);
		schemeEditView = new GameSchemeEditView(topPanels[1], gameFormInstance);
		changeEditView = new GameChangeEditView(topPanels[2], gameFormInstance);

		FormData data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0);
		data.bottom = new FormAttachment(bottomPanel, -3);
		data.top = new FormAttachment(0, 0);

		topPanels[0].setLayoutData(data);
		topPanels[1].setLayoutData(data);
		topPanels[2].setLayoutData(data);

		data = new FormData();
		data.bottom = new FormAttachment(100, 0);
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0);
		bottomPanel.setLayoutData(data);

		bottomPanel.setLayout(new FormLayout());

		topPanels[0].setVisible(true);
		topPanels[1].setVisible(false);
		topPanels[2].setVisible(false);

		viewBtns = new Button[3];
		viewBtns[0] = new Button(bottomPanel, SWT.TOGGLE);
		viewBtns[0].setText(MainWindow.getMessage("fixturesPanelButtonName"));
		Point size = viewBtns[0].computeSize(SWT.DEFAULT, SWT.DEFAULT);
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.top = new FormAttachment(50, -size.y/2);
		viewBtns[0].setLayoutData(data);
		viewBtns[0].setSelection(true);

		viewBtns[1] = new Button(bottomPanel, SWT.TOGGLE);
		viewBtns[1].setText(MainWindow.getMessage("positionsPanelButtonName"));
		size = viewBtns[1].computeSize(SWT.DEFAULT, SWT.DEFAULT);
		data = new FormData();
		data.left = new FormAttachment(viewBtns[0], 0);
		data.top = new FormAttachment(50, -size.y/2);
		viewBtns[1].setLayoutData(data);

		viewBtns[2] = new Button(bottomPanel, SWT.TOGGLE);
		viewBtns[2].setText(MainWindow.getMessage("substitutionsPanelButtonName"));
		size = viewBtns[2].computeSize(SWT.DEFAULT, SWT.DEFAULT);
		data = new FormData();
		data.left = new FormAttachment(viewBtns[1], 0);
		data.top = new FormAttachment(50, -size.y/2);
		viewBtns[2].setLayoutData(data);

		viewBtns[0].addSelectionListener(new ViewBtnListner(0, viewBtns,
				topPanels));
		viewBtns[1].addSelectionListener(new ViewBtnListner(1, viewBtns,
				topPanels));
		viewBtns[2].addSelectionListener(new ViewBtnListner(2, viewBtns,
				topPanels));

		
		matchType = new Combo(bottomPanel, SWT.DROP_DOWN | SWT.READ_ONLY);
		matchType.addSelectionListener(new MatchTypeListner());
		size = matchType.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
		data = new FormData();
		data.top = new FormAttachment(50, -size.y/2);
		data.left = new FormAttachment(viewBtns[2], 5);
		matchType.setLayoutData(data);

	
		matchRound = new Combo(bottomPanel, SWT.DROP_DOWN | SWT.READ_ONLY);
		matchRound.addSelectionListener(new MatchRoundListner());
		
		data = new FormData();
		size = matchRound.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
		data.top = new FormAttachment(50, -size.y/2);
		data.left = new FormAttachment(matchType, 5);
		matchRound.setLayoutData(data);
		for (int i = 0; i < roundTypes.length; i++) {
			matchRound.add(roundTypes[i]);
		}
		
		matchRound.select(0);
		matchRound.setVisibleItemCount(10);

		saveButton = new Button(bottomPanel, SWT.PUSH);
		
		data = new FormData();
		size = saveButton.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		data.top = new FormAttachment(50, -size.y/2);
		data.right = new FormAttachment(100, -5);
		saveButton.setLayoutData(data);
		saveButton.addSelectionListener(new SaveButtonListner());

		openButton = new Button(bottomPanel, SWT.PUSH);

		data = new FormData();
		size = openButton.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		data.top = new FormAttachment(50, -size.y/2);
		data.right = new FormAttachment(saveButton, -5);
		openButton.setLayoutData(data);
		openButton.addSelectionListener(new OpenButtonListner());
				
		mainComposite.setRedraw(true);
	}

	public void matchTypeChange() {
		int index = matchType.getSelectionIndex();
		if (index >= 0) {
			Properties properties = mainWnd.getSettings();
			String key = matchType.getItem(index);
			String value = (String) matchType.getData(key);
			properties.setProperty("MatchDefaultType", key + "=" + value);
			mainWnd.saveSettings();
			gameFormInstance.setTournamentID(key);
		}
	}

	public void matchRoundChange() {
		int index = matchRound.getSelectionIndex();
		if (index >= 0) {
			Properties properties = mainWnd.getSettings();
			String value = matchRound.getItem(index);
			properties.setProperty("MatchDefaultRound", value);
			mainWnd.saveSettings();
			gameFormInstance.setGameType(value);
		}
	}

	public class MatchTypeListner implements SelectionListener {

		public void widgetDefaultSelected(SelectionEvent e) {
			widgetSelected(e);
		}

		public void widgetSelected(SelectionEvent e) {
			matchTypeChange();
		}
	}

	public class MatchRoundListner implements SelectionListener {

		public void widgetDefaultSelected(SelectionEvent e) {
			widgetSelected(e);
		}

		public void widgetSelected(SelectionEvent e) {
			matchRoundChange();
		}
	}

	public void updateAll() {
		updateTeam();
	}

	public void updateTeam() {
		matchType.removeAll();
		Properties properties = mainWnd.getSettings();
		String property = properties.getProperty("MatchDefaultType");
		if (MainWindow.getAllInstance() != null) {
			Team currentTeam = MainWindow.getAllInstance().getCurrentTeam();
			if (currentTeam != null) {
				gameFormInstance.setTeamID(currentTeam.getId());

				Set<String> competitionsTeam = currentTeam.getCompetitions();
				Map<String, String> competitionsAll = MainWindow.getAllInstance().getCompetitions();
				int index = 0;
				int i = 0;
				for (String entry: competitionsTeam) {
					String key = competitionsAll.get(entry);
					String entryString = key + "=" + entry;
					if (property != null && entryString.equals(property)) {
						index = i;
					}
					i++;
					matchType.add(key);
					matchType.setData(key, entry);
				}
				matchType.select(index);
			}
			/*Point size = matchType.computeSize(SWT.DEFAULT, SWT.DEFAULT);
			FormData data = (FormData) matchType.getLayoutData();
			data.right.offset = size.x;
			matchType.setLayoutData(data);*/
			bottomPanel.layout();

			property = properties.getProperty("MatchDefaultRound");
			if (property != null) {
				int index = 0;
				for (int i = 0; i < roundTypes.length; i++) {
					if (roundTypes[i].compareTo(property) == 0) {
						index = i;
					}
				}
				matchRound.select(index);
			}
			PlayerPosition[] firstTeam = gameFormInstance.getFirstTeam();
			for (int i = 0; i < firstTeam.length; i++) {
				firstTeam[i].setNumber(0);
			}
			gameFormInstance.setFirstTeam(firstTeam);
			formEditView.updateAll();
			changeEditView.updateAll();
			schemeEditView.updateAll();
		}
	}

	public class ViewBtnListner implements SelectionListener {

		int index;
		Button btns[];
		Composite views[];

		public ViewBtnListner(int index, Button[] btns, Composite views[]) {
			this.btns = btns;
			if (index < 0)
				index = 0;
			if (index > btns.length) {
				index = btns.length - 1;
			}
			this.views = views;
			this.index = index;
		}

		public void widgetDefaultSelected(SelectionEvent e) {
			if (btns[index].getSelection()) {
				for (int i = 0; i < btns.length; i++) {
					if (i != index) {
						btns[i].setSelection(false);
						views[i].setVisible(false);
					} else {
						updateGameForm(gameFormInstance);
						views[i].setVisible(true);
					}
				}
			} else {
				btns[index].setSelection(true);
			}
		}

		public void widgetSelected(SelectionEvent e) {
			widgetDefaultSelected(e);
		}

	}

	private class SliderListener implements SelectionListener {

		public void widgetSelected(SelectionEvent e) {
			restText.setText(String.valueOf(restScale.getSelection()));
			formEditView.updateDaysOfRest(restScale.getSelection());
		}

		public void widgetDefaultSelected(SelectionEvent e) {
			restText.setText(String.valueOf(restScale.getSelection()));
			formEditView.updateDaysOfRest(restScale.getSelection());
		}
	}

	private class RestTextListener implements Listener {

		public void handleEvent(final Event e) {
			int val = -1;
			try {
				val = Integer.valueOf(restText.getText());
			} catch (NumberFormatException nfe) {
				val = 0;
			}
			if (val > REST_SLIDER_MAX)
				val = REST_SLIDER_MAX;
			switch (e.type) {
			case SWT.Traverse:
				if (e.detail == SWT.TRAVERSE_RETURN) {
					restScale.setSelection(val);
					restText.setText(String.valueOf(val));
				}
				break;
			case SWT.FocusOut:
			case SWT.Modify:
				restScale.setSelection(val);
				restText.setText(String.valueOf(val));
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
	}

	public class OpenButtonListner implements SelectionListener {

		public void widgetDefaultSelected(SelectionEvent arg0) {
			openGameFormDlg();
		}

		public void widgetSelected(SelectionEvent arg0) {
			openGameFormDlg();
		}
	}

	public class SaveButtonListner implements SelectionListener {

		public void widgetDefaultSelected(SelectionEvent arg0) {
			saveGameFormDlg();
		}

		public void widgetSelected(SelectionEvent arg0) {
			saveGameFormDlg();
		}
	}

	public void openGameForm(String fname) {
		if (fname == null) {
			return;
		}
		try {
			gameFormInstance = GameReader.readGameFormFile(fname);
		} catch (ReaderException e) {
			e.printStackTrace();
		}
	}

	public void saveGameForm(String fname) {
		if (fname == null) {
			return;
		}
		try {
			gameFormInstance.setPassword(password);
			GameWriter.writeGameFormFile(fname, gameFormInstance);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void openGameFormDlg() {

		FileDialog dlg = new FileDialog(parentInstance.getShell(), SWT.OPEN);

		String ext[] = new String[1];
		ext[0] = MainWindow.getMessage("FilterExtensionRequest");
		dlg.setFilterExtensions(ext);
		String extNames[] = new String[1];
		extNames[0] = MainWindow.getMessage("FilterExtensionRequestName");
		dlg.setFilterNames(extNames);
		String result = dlg.open();
		if (result != null) {
			this.openGameForm(result);
			updateGameForm(gameFormInstance);
		}
	}

	public void saveGameFormDlg() {

		FileDialog dlg = new FileDialog(parentInstance.getShell(), SWT.SAVE);

		String ext[] = new String[1];
		ext[0] = MainWindow.getMessage("FilterExtensionRequest");
		dlg.setFilterExtensions(ext);
		String extNames[] = new String[1];
		extNames[0] = MainWindow.getMessage("FilterExtensionRequestName");
		dlg.setFilterNames(extNames);
		String result = dlg.open();
		if (result != null) {
			this.saveGameForm(result);
		}
	}

	public void updateGameForm(GameForm gameForm) {
		gameFormInstance = gameForm;
		formEditView.updateGameForm(gameForm);
		changeEditView.updateGameForm(gameForm);
		schemeEditView.updateGameForm(gameForm);
		String id = gameForm.getGameType();
		for (int i = 0; i < GameEditView.roundTypes.length && id != null; i++) {
			if (id.equals(GameEditView.roundTypes[i])) {
				matchRound.select(i);
				break;
			}
		}
		String type = gameForm.getTournamentID();
		for (int i = 0; i < matchType.getItemCount() && type != null; i++) {
			if (type.equals(matchType.getItem(i))) {
				matchType.select(i);
				break;
			}
		}
	}

	public void updatePassword(String password) {
		this.password = password;
	}

	public void updateDaysOfRest(int daysOfRest) {
		formEditView.updateDaysOfRest(daysOfRest);
	}

	public void updateMessages() {
		mainItem.setText(MainWindow.getMessage("gameEditorTabName"));
		
		formEditView.updateMessages();
		changeEditView.updateMessages();
		schemeEditView.updateMessages();
		
		openButton.setText(MainWindow.getMessage("global.open"));
		saveButton.setText(MainWindow.getMessage("global.save"));
		
		
		viewBtns[0].setText(MainWindow.getMessage("fixturesPanelButtonName"));
		viewBtns[1].setText(MainWindow.getMessage("positionsPanelButtonName"));
		viewBtns[2].setText(MainWindow.getMessage("substitutionsPanelButtonName"));

		bottomPanel.layout();
	}

	public void redraw(){
	}
	
}
