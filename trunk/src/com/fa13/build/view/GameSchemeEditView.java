package com.fa13.build.view;

import java.util.Iterator;
import java.util.Map.Entry;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

import com.fa13.build.model.BasicPlayer.PlayerAmplua;
import com.fa13.build.model.*;
import com.fa13.build.model.GameForm.CoachSettings;
import com.fa13.build.model.PlayerPosition.FreekicksAction;
import com.fa13.build.model.PlayerPosition.PassingStyle;
import com.fa13.build.model.TeamTactics.Hardness;

public class GameSchemeEditView implements UIItem {

	int maxWidth = 0;
	int maxHeight = 0;

	enum SchemeViewType {
		ATTACK, DEFENCE, FREEKICK
	};

	CoachSettings coachSettingsEnum[] = { CoachSettings.CH_NONE,
			CoachSettings.CH_PRESSING, CoachSettings.CH_ARYTHMIC,
			CoachSettings.CH_PERSONAL_DEFENCE, CoachSettings.CH_COUNTER_ATTACK,
			CoachSettings.CH_CATENACCIO, CoachSettings.CH_STRAIGHT_PRESSURE,
			CoachSettings.CH_POSITION_ATTACK };
	String coachSettings[] = { "coachSettingsNone", "coachSettingsPressing",
			"coachSettingsArythmic", "coachSettingsPersonalDefence",
			"coachSettingsCounterAttack", "coachSettingsCatenaccio",
			"coachSettingsStraightpressure", "coachSettingsPositionAttack" };

	TeamTactics.Tactics teamTacticsTacticsEnum[] = {
			TeamTactics.Tactics.TC_DEFENCE, TeamTactics.Tactics.TC_BALANCE,
			TeamTactics.Tactics.TC_ATTACK };
	String teamTacticsTactics[] = { "TeamTacticsDefence", "TeamTacticsBalance",
			"TeamTacticsAttack" };

	TeamTactics.Style teamTacticsEnum[] = { TeamTactics.Style.ST_FLANG,
			TeamTactics.Style.ST_COMBINE, TeamTactics.Style.ST_CENTER,
			TeamTactics.Style.ST_ENGLAND, TeamTactics.Style.ST_LONGBALL,
			TeamTactics.Style.ST_UNIVERSAL };
	String teamTacticsStyles[] = { "TeamTacticsStyleFlang",
			"TeamTacticsStyleCombine", "TeamTacticsStyleCenter",
			"TeamTacticsStyleEngland", "TeamTacticsStyleLongball",
			"TeamTacticsStyleUniversal" };

	final PassingStyle passingStylesEnum[] = { PassingStyle.PS_BOTH,
			PassingStyle.PS_FORWARD, PassingStyle.PS_EXACT };
	final Hardness hardnessEnum[] = { Hardness.HR_DEFAULT, Hardness.HR_SOFT,
			Hardness.HR_NORMAL, Hardness.HR_HARD, Hardness.HR_NIGHTMARE };
	final String hardnessTypes[] = { "PersonalHardnessDefault",
			"PersonalHardnessSoft", "PersonalHardnessNormal",
			"PersonalHardnessHard", "PersonalHardnessNightmare" };
	final String passingStyles[] = { "PassingStyleBoth", "PassingStyleForward",
			"PassingStyleExact" };

	SchemeViewType schemeType = SchemeViewType.ATTACK;
	Label playerTestLabel;

	Image playerNormalImage;
	Image playerHighlightedImage;

	Composite parentInsance;
	Composite leftPanel;
	Composite rightPanel;
	ScrolledComposite rightPanelScrl;
	Composite playerPanel;
	GameForm gameFormInstance;
	Combo schemeCombo;
	boolean isSchemeUpdate = true;
	Button deleteScheme;
	Button attackButton;
	Button defenceButton;
	Button freeKickButton;

	Button mainPlayersButton;
	Button substitutionPlayersButton;

	Label playerNameLabel;

	Group playerGroup;

	Label playerXLabel;
	Label playerYLabel;
	Label longShotLabel;
	Label actOnFreekickLabel;
	Label fantasistaLabel;
	Label dispatcherLabel;
	Label personalDefenceLabel;
	Label pressingLabel;
	Label keepBallLabel;
	Label defenderAttackLabel;
	Label passingStyleLabel;
	Label hardnessLabel;

	Button playerXButton;
	Button playerYButton;
	Button longShotButton;
	Button actOnFreekickButton;
	Button fantasistaButton;
	Button dispatcherButton;
	Button personalDefenceButton;
	Button pressingButton;
	Button keepBallButton;
	Button defenderAttackButton;
	Button passingStyleButton;
	Button hardnessButton;

	Spinner personalDefenceSpinner;
	Spinner playerXSpinner;
	Spinner playerYSpinner;

	Combo passingStyleCombo;
	Combo hardnessCombo;

	ScrolledComposite fieldScrolledPanel;
	Composite fieldPanel;
	ScrolledComposite coachSettingsScrolledPanel;
	Composite coachSettingsPanel;

	Combo teamCoachSettingsCombo;
	Combo teamTacticsTacticsCombo;
	Combo teamTacticsHardnessCombo;
	Combo teamTacticsStyleCombo;
	Label teamCoachSettingsLabel;
	Label teamTacticsTacticsLabel;
	Label teamTacticsHardnessLabel;
	Label teamTacticsStyleLabel;

	Spinner attackTimeSpinner;
	Spinner attackMinSpinner;
	Spinner attackMaxSpinner;
	Spinner defenceTimeSpinner;
	Spinner defenceMinSpinner;
	Spinner defenceMaxSpinner;
	Spinner ticketPriceSpinner;

	Label timeLabel;
	Label resultLabel;
	Label timeLabelD;
	Label resultLabelD;
	Label ticketLabel;

	Group attackGroup;
	Group defenceGroup;
	Group teamTacticsGroup;
	Group ticketPriceGroup;

	Canvas fieldCanvas;
	Image fieldImage;

	int currentPlayer = -1;

	public GameSchemeEditView(Composite parent, GameForm gameForm) {
		this.gameFormInstance = gameForm;
		parentInsance = parent;

		parent.setLayout(new FormLayout());

		FormData data = null;

		leftPanel = new Composite(parent, SWT.NONE);

		rightPanelScrl = new ScrolledComposite(parent, SWT.V_SCROLL);
		rightPanelScrl.setExpandHorizontal(true);
		rightPanelScrl.setExpandVertical(true);
		rightPanel = new Composite(rightPanelScrl, SWT.NONE);
		rightPanel.setLayout(new FormLayout());
		rightPanelScrl.setContent(rightPanel);
		rightPanelScrl.setLayout(new FillLayout());
		int scrollWidth = rightPanelScrl.getVerticalBar().getSize().x;

		fieldScrolledPanel = new ScrolledComposite(leftPanel, SWT.V_SCROLL
				| SWT.H_SCROLL);
		fieldScrolledPanel.setExpandHorizontal(false);
		fieldScrolledPanel.setExpandVertical(false);
		fieldPanel = new Composite(fieldScrolledPanel, SWT.NONE);
		fieldPanel.setLayout(new FormLayout());
		fieldScrolledPanel.setContent(fieldPanel);
		fieldScrolledPanel.setMinHeight(620);
		fieldPanel.setBackground(new Color(parent.getDisplay(), 0, 0, 0));

		coachSettingsScrolledPanel = new ScrolledComposite(leftPanel, SWT.NONE
				| SWT.H_SCROLL);
		coachSettingsScrolledPanel.setExpandHorizontal(true);
		coachSettingsScrolledPanel.setExpandVertical(true);
		coachSettingsPanel = new Composite(coachSettingsScrolledPanel, SWT.NONE);
		coachSettingsPanel.setLayout(new FormLayout());
		coachSettingsScrolledPanel.setContent(coachSettingsPanel);
		coachSettingsScrolledPanel.setLayout(new FillLayout());

		leftPanel.setLayout(new FormLayout());

		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(rightPanelScrl, 0);
		data.top = new FormAttachment(0, 0);
		data.bottom = new FormAttachment(100, 0);
		leftPanel.setLayoutData(data);

		schemeCombo = new Combo(rightPanel, SWT.DROP_DOWN | SWT.READ_ONLY);
		for (Iterator<Entry<String, PlayerPosition[]>> iterator = GameFormEditView.schemesTemplates
				.entrySet().iterator(); iterator.hasNext();) {
			Entry<String, PlayerPosition[]> entry = iterator.next();
			schemeCombo.add(entry.getKey());
			schemeCombo.setData(entry.getKey(), entry.getValue());
		}

		schemeCombo.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				Combo combo = (Combo) e.widget;
				String text = combo.getText();
				if (isSchemeUpdate) {
					GameFormEditView.applyScheme(gameFormInstance
							.getFirstTeam(), (PlayerPosition[]) combo
							.getData(combo.getText()));
				}
				gameFormInstance.setDefault(combo.getSelectionIndex());
				if (fieldCanvas != null) {
					fieldCanvas.redraw();
				}
			}
		});
		schemeCombo.select(0);
		deleteScheme = new Button(rightPanel, SWT.PUSH);
		deleteScheme.setText(MainWindow.getMessage("DeleteSchemeButton"));
		attackButton = new Button(rightPanel, SWT.TOGGLE);
		defenceButton = new Button(rightPanel, SWT.TOGGLE);
		freeKickButton = new Button(rightPanel, SWT.TOGGLE);
		mainPlayersButton = new Button(rightPanel, SWT.TOGGLE);
		substitutionPlayersButton = new Button(rightPanel, SWT.TOGGLE);
		attackButton.setText(MainWindow.getMessage("AttackViewButton"));
		defenceButton.setText(MainWindow.getMessage("DefenceViewButton"));
		freeKickButton.setText(MainWindow.getMessage("FreeKickViewButton"));
		mainPlayersButton.setText(MainWindow.getMessage("MainPlayersPositionsView"));
		substitutionPlayersButton.setText(MainWindow.getMessage("SubstitutionPlayersPositionsView"));

		playerNameLabel = new Label(rightPanel, SWT.NONE);
		playerNameLabel.setText("Your ad can be placed here");

		playerGroup = new Group(rightPanel, SWT.NONE);
		playerGroup.setText(MainWindow.getMessage("PlayerSettingsGroupLabel"));
		playerGroup.setLayout(new FormLayout());

		String labelsNames[] = { MainWindow.getMessage("PositionPlayerX"),
				MainWindow.getMessage("PositionPlayerY"),
				MainWindow.getMessage("PositionLongShot"),
				MainWindow.getMessage("PositionActOnFreekick"),
				MainWindow.getMessage("PositionFantasista"),
				MainWindow.getMessage("PositionDispatcher"),
				MainWindow.getMessage("PositionPersonalDefence"),
				MainWindow.getMessage("PositionPressing"),
				MainWindow.getMessage("PositionKeepBall"),
				MainWindow.getMessage("PositionDefenderAttack"),
				MainWindow.getMessage("PositionPassingStyle"),
				MainWindow.getMessage("PositionHardness") };

		Label labels[] = { playerXLabel, playerYLabel, longShotLabel,
				actOnFreekickLabel, fantasistaLabel, dispatcherLabel,
				personalDefenceLabel, pressingLabel, keepBallLabel,
				defenderAttackLabel, passingStyleLabel, hardnessLabel };

		for (int i = 0; i < labelsNames.length; i++) {
			labels[i] = new Label(playerGroup, SWT.NONE);
			labels[i].setText(labelsNames[i]);
		}

		playerXButton = new Button(playerGroup, SWT.CHECK);
		playerXButton.setVisible(false);
		playerYButton = new Button(playerGroup, SWT.CHECK);
		playerYButton.setVisible(false);
		longShotButton = new Button(playerGroup, SWT.CHECK);
		actOnFreekickButton = new Button(playerGroup, SWT.CHECK);
		fantasistaButton = new Button(playerGroup, SWT.CHECK);
		dispatcherButton = new Button(playerGroup, SWT.CHECK);
		personalDefenceButton = new Button(playerGroup, SWT.CHECK);
		pressingButton = new Button(playerGroup, SWT.CHECK);
		keepBallButton = new Button(playerGroup, SWT.CHECK);
		defenderAttackButton = new Button(playerGroup, SWT.CHECK);
		passingStyleButton = new Button(playerGroup, SWT.CHECK);
		hardnessButton = new Button(playerGroup, SWT.CHECK);

		longShotButton.addSelectionListener(new CheckButtonListner(0));
		actOnFreekickButton.addSelectionListener(new CheckButtonListner(1));
		fantasistaButton.addSelectionListener(new CheckButtonListner(2));
		dispatcherButton.addSelectionListener(new CheckButtonListner(3));
		personalDefenceButton.addSelectionListener(new CheckButtonListner(4));
		pressingButton.addSelectionListener(new CheckButtonListner(5));
		keepBallButton.addSelectionListener(new CheckButtonListner(6));
		defenderAttackButton.addSelectionListener(new CheckButtonListner(7));

		passingStyleButton.setVisible(false);
		hardnessButton.setVisible(false);

		passingStyleCombo = new Combo(playerGroup, SWT.DROP_DOWN
				| SWT.READ_ONLY);
		hardnessCombo = new Combo(playerGroup, SWT.DROP_DOWN | SWT.READ_ONLY);

		Button buttons[] = { playerXButton, playerYButton, longShotButton,
				actOnFreekickButton, fantasistaButton, dispatcherButton,
				personalDefenceButton, pressingButton, keepBallButton,
				defenderAttackButton, passingStyleButton, hardnessButton };

		String buttonNames[] = { "playerXButton", "playerYButton",
				"longShotButton", "actOnFreekickButton", "fantasistaButton",
				"dispatcherButton", "personalDefenceButton", "pressingButton",
				"keepBallButton", "defenderAttackButton", "passingStyleButton",
				"hardnessButton" };

		int index = 0;
		playerXLabel = labels[index++];
		playerYLabel = labels[index++];
		longShotLabel = labels[index++];
		actOnFreekickLabel = labels[index++];
		fantasistaLabel = labels[index++];
		dispatcherLabel = labels[index++];
		personalDefenceLabel = labels[index++];
		pressingLabel = labels[index++];
		keepBallLabel = labels[index++];
		defenderAttackLabel = labels[index++];
		passingStyleLabel = labels[index++];
		hardnessLabel = labels[index++];

		personalDefenceSpinner = new Spinner(playerGroup, SWT.BORDER);
		personalDefenceSpinner.setTextLimit(3);
		personalDefenceSpinner.setMinimum(1);
		personalDefenceSpinner.setMaximum(25);
		personalDefenceSpinner.setIncrement(1);
		Point spinnerSize = personalDefenceSpinner.computeSize(SWT.DEFAULT,
				SWT.DEFAULT);
		personalDefenceSpinner.setDigits(0);
		personalDefenceSpinner.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				PlayerPosition pos = getCurrentPlayer();
				if (pos != null) {
					if (personalDefenceButton.getSelection()) {
						pos.setPersonalDefence(personalDefenceSpinner
								.getSelection());
					}
				}
			}
		});

		playerXSpinner = new Spinner(playerGroup, SWT.BORDER);
		playerXSpinner.setTextLimit(3);
		playerXSpinner.setMinimum(0);
		playerXSpinner.setMaximum(600);
		playerXSpinner.setIncrement(1);
		playerXSpinner.setPageIncrement(10);
		playerXSpinner.setDigits(0);

		playerYSpinner = new Spinner(playerGroup, SWT.BORDER);
		playerYSpinner.setTextLimit(3);
		playerYSpinner.setMinimum(0);
		playerYSpinner.setMaximum(450);
		playerYSpinner.setIncrement(1);
		playerYSpinner.setPageIncrement(10);
		playerYSpinner.setDigits(0);

		spinnerSize.y = Math.max(spinnerSize.y, hardnessCombo.computeSize(
				SWT.DEFAULT, SWT.DEFAULT).y);
		spinnerSize.y += 2;

		for (int i = 0; i < passingStyles.length; i++) {
			String bundle = MainWindow.getMessage(passingStyles[i]);
			if (bundle == null) {
				bundle = passingStylesEnum[i].toString();
			}
			passingStyleCombo.add(bundle);
			passingStyleCombo.setData(bundle, passingStylesEnum[i]);
		}

		passingStyleCombo.select(0);
		passingStyleCombo.addModifyListener(new PassingStyleComboListener());

		for (int i = 0; i < hardnessTypes.length; i++) {
			String bundle = MainWindow.getMessage(hardnessTypes[i]);
			if (bundle == null) {
				bundle = hardnessEnum[i].toString();
			}
			hardnessCombo.add(bundle);
			hardnessCombo.setData(bundle, hardnessEnum[i]);
		}
		hardnessCombo.select(0);
		hardnessCombo.addModifyListener(new HardnessComboListener());

		data = new FormData();
		data.right = new FormAttachment(100, -10);
		data.left = new FormAttachment(100, -10
				- passingStyleCombo.computeSize(SWT.DEFAULT, SWT.DEFAULT).x);
		data.top = new FormAttachment(passingStyleButton, 3 - spinnerSize.y);
		passingStyleCombo.setLayoutData(data);

		data = new FormData();
		data.right = new FormAttachment(100, -10);
		data.left = new FormAttachment(100, -10
				- hardnessCombo.computeSize(SWT.DEFAULT, SWT.DEFAULT).x);
		data.top = new FormAttachment(hardnessButton, 3 - spinnerSize.y);
		hardnessCombo.setLayoutData(data);

		data = new FormData();
		data.right = new FormAttachment(100, -10);
		data.left = new FormAttachment(100, -10 - spinnerSize.x);
		int dy = personalDefenceButton.computeSize(SWT.DEFAULT, SWT.DEFAULT).y
				- personalDefenceSpinner.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
		dy /= 2;
		data.top = new FormAttachment(personalDefenceButton, dy
				- personalDefenceButton.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		personalDefenceSpinner.setLayoutData(data);

		data = new FormData();
		data.right = new FormAttachment(100, -10);
		dy = playerXButton.computeSize(SWT.DEFAULT, SWT.DEFAULT).y
				- playerXSpinner.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
		dy /= 2;
		data.top = new FormAttachment(playerXButton, dy
				- playerXButton.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		playerXSpinner.setLayoutData(data);

		data = new FormData();
		data.right = new FormAttachment(100, -10);
		dy = playerYButton.computeSize(SWT.DEFAULT, SWT.DEFAULT).y
				- playerYSpinner.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
		dy /= 2;
		data.top = new FormAttachment(playerYButton, dy
				- playerXButton.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		playerYSpinner.setLayoutData(data);

		data = new FormData();
		data.left = new FormAttachment(0, 6);
		data.right = new FormAttachment(buttons[0], buttons[0].computeSize(
				SWT.DEFAULT, SWT.DEFAULT).x);
		data.top = new FormAttachment(0, spinnerSize.y / 4);
		buttons[0].setLayoutData(data);

		for (int i = 1; i < buttons.length; i++) {
			data = new FormData();
			data.left = new FormAttachment(0, 6);
			data.right = new FormAttachment(buttons[i], buttons[i].computeSize(
					SWT.DEFAULT, SWT.DEFAULT).x);
			data.top = new FormAttachment(buttons[i - 1], spinnerSize.y
					- buttons[i].computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
			buttons[i].setLayoutData(data);
		}

		data = new FormData();
		data.left = new FormAttachment(buttons[0], 10);
		data.right = new FormAttachment(labels[0], labels[0].computeSize(
				SWT.DEFAULT, SWT.DEFAULT).x);
		data.top = new FormAttachment(buttons[0], -buttons[0].computeSize(
				SWT.DEFAULT, SWT.DEFAULT).y);
		labels[0].setLayoutData(data);

		for (int i = 1; i < labels.length; i++) {
			data = new FormData();
			data.left = new FormAttachment(buttons[i], 10);
			data.right = new FormAttachment(labels[i], labels[i].computeSize(
					SWT.DEFAULT, SWT.DEFAULT).x);
			int dy1 = buttons[i].computeSize(SWT.DEFAULT, SWT.DEFAULT).y
					- labels[i].computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
			data.top = new FormAttachment(buttons[i], dy1 / 2
					- buttons[i].computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
			labels[i].setLayoutData(data);
		}

		data = new FormData();
		data.left = new FormAttachment(0, scrollWidth);
		data.right = new FormAttachment(deleteScheme, -10);
		data.top = new FormAttachment(0, 1);
		schemeCombo.setLayoutData(data);

		data = new FormData();
		data.left = new FormAttachment(deleteScheme, -deleteScheme.computeSize(
				SWT.DEFAULT, SWT.DEFAULT).x);
		data.right = new FormAttachment(100, -scrollWidth);
		data.top = new FormAttachment(0, 0);
		deleteScheme.setLayoutData(data);

		data = new FormData();
		data.left = new FormAttachment(0, scrollWidth);
		// data.right = new FormAttachment(defenceButton, defenceButton
		// .computeSize(SWT.DEFAULT, SWT.DEFAULT).x);
		data.top = new FormAttachment(schemeCombo, 10);
		defenceButton.setLayoutData(data);

		data = new FormData();
		data.left = new FormAttachment(defenceButton, 0);
		// data.right = new FormAttachment(attackButton,
		// attackButton.computeSize(
		// SWT.DEFAULT, SWT.DEFAULT).x);
		data.top = new FormAttachment(schemeCombo, 10);
		attackButton.setLayoutData(data);

		data = new FormData();
		data.left = new FormAttachment(attackButton, 0);
		// data.right = new FormAttachment(freeKickButton, freeKickButton
		// .computeSize(SWT.DEFAULT, SWT.DEFAULT).x);
		data.top = new FormAttachment(schemeCombo, 10);
		freeKickButton.setLayoutData(data);

		data = new FormData();
		data.left = new FormAttachment(0, scrollWidth);
		data.right = new FormAttachment(mainPlayersButton, mainPlayersButton
				.computeSize(SWT.DEFAULT, SWT.DEFAULT).x);
		data.top = new FormAttachment(defenceButton, 3);
		mainPlayersButton.setLayoutData(data);

		data = new FormData();
		data.left = new FormAttachment(mainPlayersButton, 0);
		data.right = new FormAttachment(
				substitutionPlayersButton,
				substitutionPlayersButton.computeSize(SWT.DEFAULT, SWT.DEFAULT).x);
		data.top = new FormAttachment(defenceButton, 3);
		substitutionPlayersButton.setLayoutData(data);

		data = new FormData();
		data.left = new FormAttachment(0, 10);
		data.right = new FormAttachment(100, -10);
		data.top = new FormAttachment(attackButton/* mainPlayersButton */, 6);
		playerNameLabel.setLayoutData(data);

		int maxLabelsWidth = 0;
		for (int i = 0; i < labels.length - 2; i++) {
			maxLabelsWidth = Math.max(maxLabelsWidth, labels[i].computeSize(
					SWT.DEFAULT, SWT.DEFAULT).x);
		}
		int groupWidth = 0;
		groupWidth = 10;
		groupWidth += buttons[0].computeSize(SWT.DEFAULT, SWT.DEFAULT).x;
		groupWidth += 6;
		groupWidth += maxLabelsWidth;
		groupWidth += 6;
		groupWidth += spinnerSize.x;
		groupWidth += 10;
		groupWidth = Math
				.max(playerGroup.computeSize(SWT.DEFAULT, SWT.DEFAULT).x,
						groupWidth);

		maxLabelsWidth = 6;
		maxLabelsWidth += buttons[0].computeSize(SWT.DEFAULT, SWT.DEFAULT).x;
		maxLabelsWidth += 10;
		maxLabelsWidth += passingStyleCombo.computeSize(SWT.DEFAULT,
				SWT.DEFAULT).x;
		maxLabelsWidth += 10;
		maxLabelsWidth += passingStyleLabel.computeSize(SWT.DEFAULT,
				SWT.DEFAULT).x;
		maxLabelsWidth += 10;
		groupWidth = Math.max(groupWidth, maxLabelsWidth);

		maxLabelsWidth = 6;
		maxLabelsWidth += buttons[0].computeSize(SWT.DEFAULT, SWT.DEFAULT).x;
		maxLabelsWidth += 10;
		maxLabelsWidth += hardnessCombo.computeSize(SWT.DEFAULT, SWT.DEFAULT).x;
		maxLabelsWidth += 10;
		maxLabelsWidth += hardnessLabel.computeSize(SWT.DEFAULT, SWT.DEFAULT).x;
		maxLabelsWidth += 10;
		groupWidth = Math.max(groupWidth, maxLabelsWidth);

		data = new FormData();
		data.left = new FormAttachment(0, 10);
		data.right = new FormAttachment(playerGroup, groupWidth);
		data.top = new FormAttachment(playerNameLabel, 6);
		data.bottom = new FormAttachment(playerGroup, playerGroup.computeSize(
				SWT.DEFAULT, SWT.DEFAULT).y
				+ spinnerSize.y / 2);
		playerGroup.setLayoutData(data);

		int rightPanelWidth = 0;

		rightPanelWidth = 2 * scrollWidth;
		rightPanelWidth += attackButton.computeSize(SWT.DEFAULT, SWT.DEFAULT).x;
		rightPanelWidth += defenceButton.computeSize(SWT.DEFAULT, SWT.DEFAULT).x;
		rightPanelWidth += freeKickButton.computeSize(SWT.DEFAULT, SWT.DEFAULT).x;
		rightPanelWidth = Math.max(rightPanelWidth, playerGroup.computeSize(
				SWT.DEFAULT, SWT.DEFAULT).x);
		rightPanelWidth = Math.max(rightPanelWidth, groupWidth + 20);

		data = new FormData();
		data.left = new FormAttachment(rightPanelScrl, -rightPanelWidth);
		data.right = new FormAttachment(100, 0);
		data.top = new FormAttachment(0, 0);
		data.bottom = new FormAttachment(100, 0);
		rightPanelScrl.setLayoutData(data);

		int scrollHeight = 0;
		scrollHeight = schemeCombo.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
		scrollHeight += 10;
		scrollHeight += freeKickButton.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
		// scrollHeight += 3;
		// scrollHeight += mainPlayersButton.computeSize(SWT.DEFAULT,
		// SWT.DEFAULT).y;
		scrollHeight += 6;
		scrollHeight += playerNameLabel.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
		scrollHeight += 6;
		scrollHeight += playerGroup.computeSize(SWT.DEFAULT, SWT.DEFAULT).y
				+ spinnerSize.y / 2;
		// scrollHeight += 10;

		rightPanelScrl.setMinWidth(rightPanelWidth);
		rightPanelScrl.setMinHeight(scrollHeight);

		coachSettingsPanel.setLayout(new GridLayout(4, false));

		ticketPriceGroup = new Group(coachSettingsPanel, SWT.NONE);

		teamTacticsGroup = new Group(coachSettingsPanel, SWT.NONE);
		teamTacticsGroup.setLayout(new GridLayout(4, false));
		teamTacticsGroup.setText(MainWindow.getMessage("teamTacticsGroup"));

		teamCoachSettingsLabel = new Label(teamTacticsGroup, SWT.NONE);
		teamCoachSettingsLabel.setText(MainWindow.getMessage("teamCoachSettingsLabel"));
		teamTacticsTacticsLabel = new Label(teamTacticsGroup, SWT.NONE);
		teamTacticsTacticsLabel.setText(MainWindow.getMessage("teamTacticsTacticsLabel"));
		teamTacticsHardnessLabel = new Label(teamTacticsGroup, SWT.NONE);
		teamTacticsHardnessLabel.setText(MainWindow.getMessage("teamTacticsHardnessLabel"));
		teamTacticsStyleLabel = new Label(teamTacticsGroup, SWT.NONE);
		teamTacticsStyleLabel.setText(MainWindow.getMessage("teamTacticsStyleLabel"));

		teamCoachSettingsCombo = new Combo(teamTacticsGroup, SWT.DROP_DOWN
				| SWT.READ_ONLY);
		teamTacticsTacticsCombo = new Combo(teamTacticsGroup, SWT.DROP_DOWN
				| SWT.READ_ONLY);
		teamTacticsHardnessCombo = new Combo(teamTacticsGroup, SWT.DROP_DOWN
				| SWT.READ_ONLY);
		teamTacticsStyleCombo = new Combo(teamTacticsGroup, SWT.DROP_DOWN
				| SWT.READ_ONLY);

		for (int i = 1; i < hardnessTypes.length; i++) {
			String bundle = MainWindow.getMessage(hardnessTypes[i]);
			if (bundle == null) {
				bundle = hardnessEnum[i].toString();
			}
			teamTacticsHardnessCombo.add(bundle);
			teamTacticsHardnessCombo.setData(bundle, hardnessEnum[i]);
		}
		teamTacticsHardnessCombo.select(0);
		teamTacticsHardnessCombo.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				if (gameFormInstance != null) {
					String text = teamTacticsHardnessCombo.getText();
					Hardness hd = (Hardness) teamTacticsHardnessCombo
							.getData(text);
					if (gameFormInstance.getStartTactics() != null) {
						gameFormInstance.getStartTactics().setTeamHardness(hd);
					} else {
						gameFormInstance.setStartTactics(new TeamTactics(
								(TeamTactics.Tactics) teamTacticsTacticsCombo
										.getData(teamTacticsTacticsCombo
												.getText()),
								(Hardness) teamTacticsHardnessCombo
										.getData(teamTacticsHardnessCombo
												.getText()),
								(TeamTactics.Style) teamTacticsStyleCombo
										.getData(teamTacticsStyleCombo
												.getText()), -20, 20));
					}
				}
			}
		});

		for (int i = 0; i < teamTacticsStyles.length; i++) {
			String bundle = MainWindow.getMessage(teamTacticsStyles[i]);
			if (bundle == null) {
				bundle = teamTacticsEnum[i].toString();
			}
			teamTacticsStyleCombo.add(bundle);
			teamTacticsStyleCombo.setData(bundle, teamTacticsEnum[i]);
		}
		teamTacticsStyleCombo.select(0);
		teamTacticsStyleCombo.setVisibleItemCount(teamTacticsStyles.length);
		teamTacticsStyleCombo.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				if (gameFormInstance != null) {
					if (gameFormInstance.getStartTactics() != null) {
						String text = teamTacticsStyleCombo.getText();
						TeamTactics.Style hd = (TeamTactics.Style) teamTacticsStyleCombo
								.getData(text);
						gameFormInstance.getStartTactics().setTeamStyle(hd);
					} else {
						gameFormInstance.setStartTactics(new TeamTactics(
								(TeamTactics.Tactics) teamTacticsTacticsCombo
										.getData(teamTacticsTacticsCombo
												.getText()),
								(Hardness) teamTacticsHardnessCombo
										.getData(teamTacticsHardnessCombo
												.getText()),
								(TeamTactics.Style) teamTacticsStyleCombo
										.getData(teamTacticsStyleCombo
												.getText()), -20, 20));
					}
				}
			}
		});

		for (int i = 0; i < teamTacticsTactics.length; i++) {
			String bundle = MainWindow.getMessage(teamTacticsTactics[i]);
			if (bundle == null) {
				bundle = teamTacticsTacticsEnum[i].toString();
			}
			teamTacticsTacticsCombo.add(bundle);
			teamTacticsTacticsCombo.setData(bundle, teamTacticsTacticsEnum[i]);
		}
		teamTacticsTacticsCombo.select(1);
		teamTacticsTacticsCombo.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				if (gameFormInstance != null) {
					String text = teamTacticsTacticsCombo.getText();
					TeamTactics.Tactics hd = (TeamTactics.Tactics) teamTacticsTacticsCombo
							.getData(text);
					if (gameFormInstance.getStartTactics() != null) {
						gameFormInstance.getStartTactics().setTeamTactics(hd);
					} else {
						gameFormInstance.setStartTactics(new TeamTactics(
								(TeamTactics.Tactics) teamTacticsTacticsCombo
										.getData(teamTacticsTacticsCombo
												.getText()),
								(Hardness) teamTacticsHardnessCombo
										.getData(teamTacticsHardnessCombo
												.getText()),
								(TeamTactics.Style) teamTacticsStyleCombo
										.getData(teamTacticsStyleCombo
												.getText()), -20, 20));
					}
				}
			}
		});

		for (int i = 0; i < coachSettings.length; i++) {
			String bundle = MainWindow.getMessage(coachSettings[i]);
			if (bundle == null) {
				bundle = coachSettingsEnum[i].toString();
			}
			teamCoachSettingsCombo.add(bundle);
			teamCoachSettingsCombo.setData(bundle, coachSettingsEnum[i]);
		}
		teamCoachSettingsCombo.select(0);
		teamCoachSettingsCombo.setVisibleItemCount(coachSettings.length);
		teamCoachSettingsCombo.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				if (gameFormInstance != null) {
					String text = teamCoachSettingsCombo.getText();
					CoachSettings hd = (CoachSettings) teamCoachSettingsCombo
							.getData(text);
					gameFormInstance.setTeamCoachSettings(hd);
				}
			}
		});

		attackGroup = new Group(coachSettingsPanel, SWT.NONE);
		defenceGroup = new Group(coachSettingsPanel, SWT.NONE);
		attackGroup.setLayout(new GridLayout(3, false));
		attackGroup.setText(MainWindow.getMessage("attackGroup"));
		defenceGroup.setText(MainWindow.getMessage("defenceGroup"));
		defenceGroup.setLayout(new GridLayout(3, false));
		ticketPriceGroup.setText(MainWindow.getMessage("ticketPriceGroup"));
		ticketPriceGroup.setLayout(new GridLayout(1, false));

		ticketLabel = new Label(ticketPriceGroup, SWT.NONE);
		ticketLabel.setText(MainWindow.getMessage("ticketLabel"));
		timeLabel = new Label(attackGroup, SWT.NONE);
		timeLabel.setText(MainWindow.getMessage("timeLabel"));
		resultLabel = new Label(attackGroup, SWT.NONE);
		resultLabel.setText(MainWindow.getMessage("resultLabel"));

		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.horizontalSpan = 2;
		gridData.horizontalAlignment = SWT.CENTER;
		resultLabel.setLayoutData(gridData);

		timeLabelD = new Label(defenceGroup, SWT.NONE);
		timeLabelD.setText(MainWindow.getMessage("timeLabel"));
		resultLabelD = new Label(defenceGroup, SWT.NONE);
		resultLabelD.setText(MainWindow.getMessage("resultLabel"));

		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.horizontalSpan = 2;
		gridData.horizontalAlignment = SWT.CENTER;
		resultLabelD.setLayoutData(gridData);

		ticketPriceSpinner = new Spinner(ticketPriceGroup, SWT.BORDER);
		ticketPriceSpinner.setMinimum(0);
		ticketPriceSpinner.setMaximum(999);
		ticketPriceSpinner.setTextLimit(3);
		ticketPriceSpinner.setSelection(40);
		ticketPriceSpinner.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				if (gameFormInstance != null) {
					gameFormInstance
							.setPrice(ticketPriceSpinner.getSelection());
				}
			}
		});

		attackTimeSpinner = new Spinner(attackGroup, SWT.BORDER);
		attackTimeSpinner.setMinimum(0);
		attackTimeSpinner.setMaximum(120);
		attackTimeSpinner.setTextLimit(3);
		attackTimeSpinner.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				if (gameFormInstance != null) {
					gameFormInstance.setAttackTime(attackTimeSpinner
							.getSelection());
				}
			}
		});
		attackMinSpinner = new Spinner(attackGroup, SWT.BORDER | SWT.READ_ONLY);
		attackMinSpinner.setMinimum(-20);
		attackMinSpinner.setMaximum(20);
		attackMinSpinner.setTextLimit(3);
		attackMinSpinner.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				if (gameFormInstance != null) {
					gameFormInstance.setAttackMin(attackMinSpinner
							.getSelection());
				}
			}
		});
		attackMaxSpinner = new Spinner(attackGroup, SWT.BORDER | SWT.READ_ONLY);
		attackMaxSpinner.setMinimum(-20);
		attackMaxSpinner.setMaximum(20);
		attackMaxSpinner.setTextLimit(3);
		attackMaxSpinner.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				if (gameFormInstance != null) {
					gameFormInstance.setAttackMax(attackMaxSpinner
							.getSelection());
				}
			}
		});

		defenceTimeSpinner = new Spinner(defenceGroup, SWT.BORDER);
		defenceTimeSpinner.setMinimum(0);
		defenceTimeSpinner.setMaximum(120);
		defenceTimeSpinner.setTextLimit(3);
		defenceTimeSpinner.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				if (gameFormInstance != null) {
					gameFormInstance.setDefenceTime(defenceTimeSpinner
							.getSelection());
				}
			}
		});

		defenceMinSpinner = new Spinner(defenceGroup, SWT.BORDER
				| SWT.READ_ONLY);
		defenceMinSpinner.setMinimum(-20);
		defenceMinSpinner.setMaximum(20);
		defenceMinSpinner.setTextLimit(3);
		defenceMinSpinner.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				if (gameFormInstance != null) {
					gameFormInstance.setDefenceMin(defenceMinSpinner
							.getSelection());
				}
			}
		});

		defenceMaxSpinner = new Spinner(defenceGroup, SWT.BORDER
				| SWT.READ_ONLY);
		defenceMaxSpinner.setMinimum(-20);
		defenceMaxSpinner.setMaximum(20);
		defenceMaxSpinner.setTextLimit(3);
		defenceMaxSpinner.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				if (gameFormInstance != null) {
					gameFormInstance.setDefenceMax(defenceMaxSpinner
							.getSelection());
				}
			}
		});

		gridData = new GridData();
		teamTacticsGroup.setLayoutData(gridData);
		gridData = new GridData();
		attackGroup.setLayoutData(gridData);
		gridData = new GridData();
		defenceGroup.setLayoutData(gridData);
		gridData = new GridData(SWT.BEGINNING, SWT.FILL, false, false);
		ticketPriceGroup.setLayoutData(gridData);
		coachSettingsPanel.pack();
		coachSettingsScrolledPanel.setMinSize(coachSettingsPanel.computeSize(
				SWT.DEFAULT, SWT.DEFAULT));

		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0);
		data.top = new FormAttachment(0, 0);
		data.bottom = new FormAttachment(100, -coachSettingsScrolledPanel
				.getHorizontalBar().getSize().y
				- coachSettingsPanel.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		fieldScrolledPanel.setLayoutData(data);

		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0);
		data.top = new FormAttachment(fieldScrolledPanel, 0);
		data.bottom = new FormAttachment(100, 0);
		coachSettingsScrolledPanel.setLayoutData(data);

		fieldPanel.setSize(698, 460);
		fieldPanel.setLayout(new FormLayout());
		fieldImage = new Image(parent.getDisplay(), this.getClass()
				.getResourceAsStream(
						"/com/fa13/build/resources/images/field.jpg"));
		fieldCanvas = new Canvas(fieldPanel, SWT.NO_BACKGROUND
				| SWT.NO_REDRAW_RESIZE);

		fieldCanvas.setSize(698, 460);

		fieldCanvas.addPaintListener(new FieldPaintListner());
		playerTestLabel = new Label(fieldCanvas, SWT.NO_BACKGROUND);
		ImageData playerNormal = new ImageData(this.getClass()
				.getResourceAsStream(
						"/com/fa13/build/resources/images/player.png"));
		int whitePixel = playerNormal.palette.getPixel(new RGB(0, 0, 0));
		playerNormal.transparentPixel = whitePixel;
		playerNormalImage = new Image(parent.getDisplay(), playerNormal);
		ImageData playerHighlighted = new ImageData(this.getClass()
				.getResourceAsStream(
						"/com/fa13/build/resources/images/player_hl.png"));
		whitePixel = playerHighlighted.palette.getPixel(new RGB(0, 0, 0));
		playerHighlighted.transparentPixel = whitePixel;
		playerHighlightedImage = new Image(parent.getDisplay(),
				playerHighlighted);

		fieldCanvas.setData("canvas");
		Listener listener = new Listener() {
			static final int JITTER = 8;
			Cursor tmp;
			int index = -1;
			PlayerPosition positions[];
			int offsetX;
			int offsetY;
			int oldX;
			int oldY;
			int width;
			int height;
			Cursor oldCursor;

			public void handleEvent(Event event) {
				switch (event.type) {
				case SWT.MouseDown:
					positions = gameFormInstance.getFirstTeam();
					index = -1;
					width = playerNormalImage.getBounds().width / 2;
					height = playerNormalImage.getBounds().height / 2;
					offsetX = playerNormalImage.getBounds().width / 2;
					offsetY = playerNormalImage.getBounds().height / 2;
					for (int i = 1; i < positions.length; i++) {
						int x = mapVirtToRealX(getPosX(positions[i]));
						int y = mapVirtToRealY(getPosY(positions[i]));
						if (x + offsetX >= event.x && x - offsetX <= event.x
								&& y + offsetY >= event.y
								&& y - offsetY <= event.y) {
							index = i;
						}
					}

					oldCursor = fieldCanvas.getCursor();
					if (index >= 0) {
						String amplua = positions[index].getAmplua().toString();
						// .setFont(GameFormEditView.tableFonts[1]);
						// Point extent = event.gc.stringExtent(amplua);
						// width += extent.x;
						int x = mapVirtToRealX(getPosX(positions[index]));
						int y = mapVirtToRealY(getPosY(positions[index]));
						offsetX = event.x - x;
						offsetY = event.y - y;
						fieldCanvas.setCursor(event.display
								.getSystemCursor(SWT.CURSOR_HAND));
						setCurrentPlayer(index);
						playerXSpinner.setSelection(getPosX(positions[index]));
						playerYSpinner.setSelection(getPosY(positions[index]));
						fieldCanvas.redraw();
					}

					break;
				case SWT.MouseMove:
					if (index >= 0) {
						setCurrentPlayer(currentPlayer);
						positions = gameFormInstance.getFirstTeam();
						if (index > positions.length)
							return;
						PlayerPosition position = getCurrentPlayer();

						fieldCanvas.redraw(mapVirtToRealX(getPosX(position))
								- 2 * width - maxWidth,
								mapVirtToRealY(getPosY(position)) - 2 * height,
								width * 4 + maxWidth, height * 4 + maxHeight
										+ 5, true);

						setPosX(position, mapRealToVirtX(event.x - offsetX));
						setPosY(position, mapRealToVirtY(event.y - offsetY));

						playerXSpinner.setSelection(mapRealToVirtX(event.x
								- offsetX));
						playerYSpinner.setSelection(mapRealToVirtY(event.y
								- offsetY));

						fieldCanvas.redraw(mapVirtToRealX(getPosX(position))
								- 2 * width - maxWidth,
								mapVirtToRealY(getPosY(position)) - 2 * height,
								width * 4 + maxWidth, height * 4 + maxHeight
										+ 5, true);

						/*
						 * fieldCanvas.redraw(oldX - width * 2 - offsetX, oldY -
						 * height * 2 - offsetY, width * 4, height * 4, true);
						 * fieldCanvas.redraw(event.x - width * 2 - offsetX,
						 * event.y - height * 2 - offsetY, width * 4, height *
						 * 4, true);
						 */
						oldX = event.x;
						oldY = event.y;
					}
					break;
				case SWT.MouseUp:
					index = -1;
					fieldCanvas.setCursor(oldCursor);
					playerTestLabel.setCursor(tmp);
					fieldCanvas.redraw();
					setCurrentPlayer(currentPlayer);
					break;
				}

			}
		};
		fieldCanvas.addListener(SWT.MouseMove, listener);
		fieldCanvas.addListener(SWT.MouseDown, listener);
		fieldCanvas.addListener(SWT.MouseUp, listener);

		Button btns[] = { attackButton, defenceButton, freeKickButton };

		attackButton.addSelectionListener(new ViewBtnListner(
				SchemeViewType.ATTACK, btns, 0));
		defenceButton.addSelectionListener(new ViewBtnListner(
				SchemeViewType.DEFENCE, btns, 1));
		freeKickButton.addSelectionListener(new ViewBtnListner(
				SchemeViewType.FREEKICK, btns, 2));
		defenceButton.setSelection(true);
		schemeType = SchemeViewType.DEFENCE;

		mainPlayersButton.setVisible(false);
		substitutionPlayersButton.setVisible(false);

		playerXSpinner.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				PlayerPosition position = getCurrentPlayer();
				int width = playerNormalImage.getBounds().width / 2;
				int height = playerNormalImage.getBounds().height / 2;
				fieldCanvas.redraw(mapVirtToRealX(getPosX(position)) - 2
						* width,
						mapVirtToRealY(getPosY(position)) - 2 * height, width
								* 4 + maxWidth, height * 4, true);
				setPosX(position, playerXSpinner.getSelection());

				fieldCanvas.redraw(mapVirtToRealX(getPosX(position)) - 2
						* width,
						mapVirtToRealY(getPosY(position)) - 2 * height, width
								* 4 + maxWidth, height * 4, true);
				// fieldCanvas.redraw();
			}
		});
		playerYSpinner.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				PlayerPosition position = getCurrentPlayer();
				int width = playerNormalImage.getBounds().width / 2;
				int height = playerNormalImage.getBounds().height / 2;
				fieldCanvas.redraw(mapVirtToRealX(getPosX(position)) - 2
						* width,
						mapVirtToRealY(getPosY(position)) - 2 * height, width
								* 4 + maxWidth, height * 4, true);
				setPosY(position, playerYSpinner.getSelection());
				fieldCanvas.redraw(mapVirtToRealX(getPosX(position)) - 2
						* width,
						mapVirtToRealY(getPosY(position)) - 2 * height, width
								* 4 + maxWidth, height * 4, true);
			}
		});

		for (int i = 0; i < btns.length; i++) {
			btns[i].setSelection(false);
		}
		deleteScheme.setEnabled(false);
		playerTestLabel.setVisible(false);

		if (this.gameFormInstance.getStartTactics() == null) {
			this.gameFormInstance
					.setStartTactics(new TeamTactics(
							(TeamTactics.Tactics) teamTacticsTacticsCombo
									.getData(teamTacticsTacticsCombo.getText()),
							(Hardness) teamTacticsHardnessCombo
									.getData(teamTacticsHardnessCombo.getText()),
							(TeamTactics.Style) teamTacticsStyleCombo
									.getData(teamTacticsStyleCombo.getText()),
							-20, 20));
		}
	}

	public class ViewBtnListner implements SelectionListener {

		int index;
		Button btns[];
		SchemeViewType type;

		public ViewBtnListner(SchemeViewType type, Button[] btns, int index) {
			this.type = type;
			this.btns = btns;
			if (index < 0)
				index = 0;
			if (index > btns.length) {
				index = btns.length - 1;
			}
			this.index = index;
		}

		public void widgetDefaultSelected(SelectionEvent e) {
			if (btns[index].getSelection()) {
				for (int i = 0; i < btns.length; i++) {
					if (i != index) {
						btns[i].setSelection(false);
					} else {
						schemeType = type;
						updateAll();
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

	public int mapVirtToRealX(int x) {
		return (int) (((double) 678.0 * x / 600.0) + 10);
	}

	public int mapVirtToRealY(int y) {
		return (int) (((double) 440.0 * y / 450.0) + 10);
	}

	public int mapRealToVirtX(int x) {
		int res = (int) ((double) (x - 10) * 600 / 678.0);
		if (res < 0)
			res = 0;
		if (res > 600)
			res = 600;
		return res;
	}

	public int mapRealToVirtY(int y) {
		int res = (int) ((double) (y - 10) * 450 / 440.0);
		if (res < 0)
			res = 0;
		if (res > 450)
			res = 450;
		return res;
	}

	public class FieldPaintListner implements PaintListener {
		public void paintControl(PaintEvent e) {
			GC gc = e.gc;
			Rectangle rect = fieldImage.getBounds();

			gc.drawImage(fieldImage, (rect.width - 698) / 2,
					(rect.height - 460) / 2, 698, 460, 0, 0, (int) (698),
					(int) (460));
			PlayerPosition positions[] = gameFormInstance.getFirstTeam();
			int offsetX = playerNormalImage.getBounds().width / 2;
			int offsetY = playerNormalImage.getBounds().height / 2;
			for (int i = 1; i < positions.length; i++) {
				int x = mapVirtToRealX(getPosX(positions[i]));
				int y = mapVirtToRealY(getPosY(positions[i]));
				if (i != currentPlayer) {
					gc.drawImage(playerNormalImage, -offsetX + x, -offsetY + y);
				}
			}

			for (int i = 1; i < positions.length; i++) {
				int x = mapVirtToRealX(getPosX(positions[i]));
				int y = mapVirtToRealY(getPosY(positions[i]));
				String numberStr = String.valueOf(positions[i].getNumber());
				if (positions[i].getNumber() > 0 && i != currentPlayer) {
					gc.setFont(GameFormEditView.tableFonts[10]);
					Color oldColor = gc.getForeground();
					gc.setForeground(GameFormEditView.tableColors[10]);
					Point extent = gc.stringExtent(numberStr);
					gc.drawString(numberStr, x - extent.x / 2, y + 1, true);
					gc.setForeground(oldColor);

					String amplua = positions[i].getAmplua().toString();
					extent = gc.stringExtent(amplua);
					gc.drawString(amplua, -offsetX + x - extent.x, -offsetY + y
							+ extent.y, true);

					StringBuffer sb = new StringBuffer();
					if (positions[i].isLongShot()) {
						sb.append(MainWindow.getMessage("PositionLongShotShort"));
					}
					if (positions[i].isFantasista()) {
						sb.append(MainWindow.getMessage("PositionFantasistaShort"));
					}
					if (positions[i].isDispatcher()) {
						sb.append(MainWindow.getMessage("PositionDispatcherShort"));
					}
					if (positions[i].getPersonalDefence() > 0) {
						sb.append(MainWindow.getMessage("PositionPersonalDefenceShort"));
						sb.append(positions[i].getPersonalDefence());
					}
					if (positions[i].isPressing()) {
						sb.append(MainWindow.getMessage("PositionPressingShort"));
					}
					if (positions[i].isKeepBall()) {
						sb.append(MainWindow.getMessage("PositionKeepBallShort"));
					}
					if (positions[i].isDefenderAttack()) {
						sb.append(MainWindow.getMessage("PositionDefenderAttackShort"));
					}
					String specialRoles = sb.toString();
					extent = gc.stringExtent(specialRoles);
					gc.drawString(specialRoles, offsetX + x, -offsetY + y
							+ extent.y, true);

					Player player = null;
					if (MainWindow.getAllInstance() != null) {
						player = MainWindow.getAllInstance().getCurrentTeam()
								.getPlayerByNumber(positions[i].getNumber());
					}
					if (player != null) {
						gc.setFont(GameFormEditView.tableFonts[10]);
						Point extent2 = gc.stringExtent(player.getName());
						gc.drawString(player.getName(), x - extent2.x / 2,
								-offsetY + y + 32, true);
						maxWidth = Math.max(maxWidth, extent2.x / 2);
						maxHeight = Math.max(maxHeight, extent2.y);
					}
					maxWidth = Math.max(maxWidth, extent.x);
					maxHeight = Math.max(maxHeight, extent.y);
				}
			}

			if (currentPlayer > 0 && currentPlayer < positions.length) {
				int x = mapVirtToRealX(getPosX(positions[currentPlayer]));
				int y = mapVirtToRealY(getPosY(positions[currentPlayer]));
				int i = currentPlayer;
				gc
						.drawImage(playerHighlightedImage, -offsetX + x,
								-offsetY + y);
				String numberStr = String.valueOf(positions[i].getNumber());
				if (positions[i].getNumber() > 0) {
					gc.setFont(GameFormEditView.tableFonts[10]);
					Color oldColor = gc.getForeground();
					gc.setForeground(GameFormEditView.tableColors[10]);
					Point extent = gc.stringExtent(numberStr);
					gc.drawString(numberStr, x - extent.x / 2, y + 1, true);
					gc.setForeground(oldColor);

					String amplua = positions[i].getAmplua().toString();
					extent = gc.stringExtent(amplua);
					gc.drawString(amplua, -offsetX + x - extent.x, -offsetY + y
							+ extent.y, true);

					StringBuffer sb = new StringBuffer();
					if (positions[i].isLongShot()) {
						sb.append(MainWindow.getMessage("PositionLongShotShort"));
					}
					if (positions[i].isFantasista()) {
						sb.append(MainWindow.getMessage("PositionFantasistaShort"));
					}
					if (positions[i].isDispatcher()) {
						sb.append(MainWindow.getMessage("PositionDispatcherShort"));
					}
					if (positions[i].getPersonalDefence() > 0) {
						sb.append(MainWindow.getMessage("PositionPersonalDefenceShort"));
						sb.append(positions[i].getPersonalDefence());
					}
					if (positions[i].isPressing()) {
						sb.append(MainWindow.getMessage("PositionPressingShort"));
					}
					if (positions[i].isKeepBall()) {
						sb.append(MainWindow.getMessage("PositionKeepBallShort"));
					}
					if (positions[i].isDefenderAttack()) {
						sb.append(MainWindow.getMessage("PositionDefenderAttackShort"));
					}
					String specialRoles = sb.toString();
					extent = gc.stringExtent(specialRoles);
					gc.drawString(specialRoles, offsetX + x, -offsetY + y
							+ extent.y, true);

					Player player = null;
					if (MainWindow.getAllInstance() != null) {
						player = MainWindow.getAllInstance().getCurrentTeam()
								.getPlayerByNumber(positions[i].getNumber());
					}
					if (player != null) {
						gc.setFont(GameFormEditView.tableFonts[10]);
						Point extent2 = gc.stringExtent(player.getName());
						gc.drawString(player.getName(), x - extent2.x / 2,
								-offsetY + y + 32, true);
						maxWidth = Math.max(maxWidth, extent2.x / 2);
						maxHeight = Math.max(maxHeight, extent2.y);
					}
					maxWidth = Math.max(maxWidth, extent.x);
					maxHeight = Math.max(maxHeight, extent.y);
				}
			}

			rect = new Rectangle(0, 0, 698, 460);
			Rectangle client = fieldCanvas.getClientArea();
			int marginWidth = client.width - rect.width;
			if (marginWidth > 0) {
				gc.fillRectangle(rect.width, 0, marginWidth, client.height);
			}
			int marginHeight = client.height - rect.height;
			if (marginHeight > 0) {
				gc.fillRectangle(0, rect.height, client.width, marginHeight);
			}

			fieldPanel.redraw();
		}
	}

	public void setCurrentPlayer(int playerIndex) {
		if (playerIndex >= 0) {
			currentPlayer = playerIndex;

			PlayerPosition playerPos = gameFormInstance.getFirstTeam()[playerIndex];
			Player player = null;
			if (MainWindow.getAllInstance() != null) {
				player = MainWindow.getAllInstance().getCurrentTeam().getPlayerByNumber(
						playerPos.getNumber());
			}

			playerXSpinner.setEnabled(true);
			playerYSpinner.setEnabled(true);

			playerXSpinner.setSelection(getPosX(playerPos));
			playerYSpinner.setSelection(getPosY(playerPos));
			if (player != null) {
				playerNameLabel.setText(player.getNumber() + " "
						+ playerPos.getAmplua().toString() + " "
						+ player.getName());
			} else {
				playerNameLabel.setText(MainWindow.getMessage("PositionPlayerChoose"));
			}

			if (playerPos.getActOnFreekick() == FreekicksAction.FK_YES) {
				actOnFreekickButton.setGrayed(false);
				actOnFreekickButton.setSelection(true);
			}
			if (playerPos.getActOnFreekick() == FreekicksAction.FK_DEFAULT) {
				actOnFreekickButton.setGrayed(true);
				actOnFreekickButton.setSelection(true);

			}
			if (playerPos.getActOnFreekick() == FreekicksAction.FK_NO) {
				actOnFreekickButton.setGrayed(false);
				actOnFreekickButton.setSelection(false);
			}

			longShotButton.setSelection(playerPos.isLongShot());
			fantasistaButton.setSelection(playerPos.isFantasista());
			dispatcherButton.setSelection(playerPos.isDispatcher());
			if (playerPos.getPersonalDefence() > 0) {
				personalDefenceButton.setSelection(true);				
			} else {
				personalDefenceButton.setSelection(false);				
			}
			
			pressingButton.setSelection(playerPos.isPressing());
			keepBallButton.setSelection(playerPos.isKeepBall());
			defenderAttackButton.setSelection(playerPos.isDefenderAttack());

			if (playerPos.getAmplua() == PlayerAmplua.CD
					|| playerPos.getAmplua() == PlayerAmplua.LD
					|| playerPos.getAmplua() == PlayerAmplua.RD) {
				longShotButton.setEnabled(true);
				actOnFreekickButton.setEnabled(true);
				fantasistaButton.setEnabled(false);
				dispatcherButton.setEnabled(false);
				personalDefenceButton.setEnabled(true);				
				pressingButton.setEnabled(false);
				keepBallButton.setEnabled(false);
				defenderAttackButton.setEnabled(true);
			
		
				fantasistaButton.setSelection(false);
				dispatcherButton.setSelection(false);
				pressingButton.setSelection(true);
				keepBallButton.setSelection(false);
			}
			
			
			
			
			if (playerPos.getAmplua() == PlayerAmplua.CM
					|| playerPos.getAmplua() == PlayerAmplua.LM
					|| playerPos.getAmplua() == PlayerAmplua.RM) {
				longShotButton.setEnabled(true);
				actOnFreekickButton.setEnabled(true);
				fantasistaButton.setEnabled(true);
				dispatcherButton.setEnabled(true);
				personalDefenceButton.setEnabled(true);				
				pressingButton.setEnabled(true);
				keepBallButton.setEnabled(false);
				defenderAttackButton.setEnabled(false);
				
				keepBallButton.setSelection(false);
				defenderAttackButton.setSelection(false);
			}
			
			if (playerPos.getAmplua() == PlayerAmplua.CF
					|| playerPos.getAmplua() == PlayerAmplua.LF
					|| playerPos.getAmplua() == PlayerAmplua.RF) {
				longShotButton.setEnabled(true);
				actOnFreekickButton.setEnabled(false);
				fantasistaButton.setEnabled(true);
				dispatcherButton.setEnabled(false);
				personalDefenceButton.setEnabled(false);				
				pressingButton.setEnabled(true);
				keepBallButton.setEnabled(true);
				defenderAttackButton.setEnabled(false);
				
				actOnFreekickButton.setSelection(true);
				dispatcherButton.setSelection(false);
				personalDefenceButton.setSelection(false);
				defenderAttackButton.setSelection(false);
			}
			
			if (personalDefenceButton.getSelection()) {			
				personalDefenceSpinner.setEnabled(true);
				personalDefenceSpinner.setSelection(playerPos
						.getPersonalDefence());
			} else {
				personalDefenceSpinner.setEnabled(false);
				personalDefenceSpinner.setSelection(1);
			}
			
			int ind = 0;
			for (ind = 0; ind < passingStylesEnum.length; ind++) {
				if (passingStylesEnum[ind] == playerPos.getPassingStyle()) {
					passingStyleCombo.select(ind);
					break;
				}
			}
			for (ind = 0; ind < hardnessEnum.length; ind++) {
				if (hardnessEnum[ind] == playerPos.getHardness()) {
					hardnessCombo.select(ind);
					break;
				}
			}
		} else {
			playerXSpinner.setEnabled(false);
			playerYSpinner.setEnabled(false);
			personalDefenceSpinner.setEnabled(false);
		}

	}

	public PlayerPosition getCurrentPlayer() {
		if (currentPlayer >= 0) {
			return gameFormInstance.getFirstTeam()[currentPlayer];
		} else {
			return null;
		}
	}

	public void updateAll() {
		setCurrentPlayer(currentPlayer);
		fieldCanvas.redraw();
	}

	public void setPosX(PlayerPosition position, int x) {
		if (position == null)
			return;
		switch (schemeType) {
		case ATTACK:
			position.setAttackX(x);
			break;
		case DEFENCE:
			position.setDefenceX(x);
			break;
		case FREEKICK:
			position.setFreekickX(x);
			break;
		}
	}

	public void setPosY(PlayerPosition position, int y) {
		if (position == null)
			return;
		switch (schemeType) {
		case ATTACK:
			position.setAttackY(y);
			break;
		case DEFENCE:
			position.setDefenceY(y);
			break;
		case FREEKICK:
			position.setFreekickY(y);
			break;
		}
	}

	public int getPosX(PlayerPosition position) {
		switch (schemeType) {
		case ATTACK:
			return position.getAttackX();
		case DEFENCE:
			return position.getDefenceX();
		case FREEKICK:
			return position.getFreekickX();
		}
		return 0;
	}

	public int getPosY(PlayerPosition position) {
		switch (schemeType) {
		case ATTACK:
			return position.getAttackY();
		case DEFENCE:
			return position.getDefenceY();
		case FREEKICK:
			return position.getFreekickY();
		}
		return 0;
	}

	public void updateGameForm(GameForm gameForm) {
		gameFormInstance = gameForm;
		isSchemeUpdate = false;
		schemeCombo.select(gameForm.getDefault());
		isSchemeUpdate = true;
		updateAll();
		int ind = 0;
		if (gameFormInstance != null) {
			if (gameFormInstance.getStartTactics() != null) {
				for (ind = 0; ind < teamTacticsTacticsEnum.length; ind++) {
					if (teamTacticsTacticsEnum[ind] == gameFormInstance
							.getStartTactics().getTeamTactics()) {
						teamTacticsTacticsCombo.select(ind);
						break;
					}
				}
				for (ind = 0; ind < teamTacticsEnum.length; ind++) {
					if (teamTacticsEnum[ind] == gameFormInstance
							.getStartTactics().getTeamStyle()) {
						teamTacticsStyleCombo.select(ind);
						break;
					}
				}
				for (ind = 0; ind < hardnessEnum.length; ind++) {
					if (hardnessEnum[ind] == gameFormInstance.getStartTactics()
							.getTeamHardness()) {
						teamTacticsHardnessCombo.select(ind);
						break;
					}
				}
			}
			for (ind = 0; ind < coachSettingsEnum.length; ind++) {
				if (coachSettingsEnum[ind] == gameFormInstance
						.getTeamCoachSettings()) {
					teamCoachSettingsCombo.select(ind);
					break;
				}
			}
			defenceTimeSpinner.setSelection(gameFormInstance.getDefenceTime());
			defenceMaxSpinner.setSelection(gameFormInstance.getDefenceMax());
			defenceMinSpinner.setSelection(gameFormInstance.getDefenceMin());
			attackTimeSpinner.setSelection(gameFormInstance.getAttackTime());
			attackMaxSpinner.setSelection(gameFormInstance.getAttackMax());
			attackMinSpinner.setSelection(gameFormInstance.getAttackMin());
		}

	}

	public void updatePassword(String password) {

	}

	public class CheckButtonListner implements SelectionListener {
		int type;

		public CheckButtonListner(int type) {
			this.type = type;
		}

		public void widgetDefaultSelected(SelectionEvent e) {
			PlayerPosition pos = getCurrentPlayer();
			if (pos != null) {
				boolean select = ((Button) e.widget).getSelection();
				switch (type) {
				case 0:
					pos.setLongShot(select);
					break;
				case 1:		
					if (pos.getActOnFreekick() == FreekicksAction.FK_DEFAULT) {
						pos.setActOnFreekick(FreekicksAction.FK_YES);
						actOnFreekickButton.setSelection(true);
						actOnFreekickButton.setGrayed(false);
					} else 
					if (pos.getActOnFreekick() == FreekicksAction.FK_YES) {
						pos.setActOnFreekick(FreekicksAction.FK_NO);
						actOnFreekickButton.setSelection(false);
						actOnFreekickButton.setGrayed(false);
					} else 
					if (pos.getActOnFreekick() == FreekicksAction.FK_NO) {
						pos.setActOnFreekick(FreekicksAction.FK_DEFAULT);
						actOnFreekickButton.setSelection(true);
						actOnFreekickButton.setGrayed(true);
					}
					
					break;
				case 2:
					pos.setFantasista(select);
					break;
				case 3:
					pos.setDispatcher(select);
					break;
				case 4:
					personalDefenceSpinner.setEnabled(select);
					if (select) {
						personalDefenceSpinner.setSelection(pos
								.getPersonalDefence());
					} else {
						personalDefenceSpinner.setSelection(1);
						pos.setPersonalDefence(-1);
					}
					break;
				case 5:
					pos.setPressing(select);
					break;
				case 6:
					pos.setKeepBall(select);
					break;
				case 7:
					pos.setDefenderAttack(select);
					break;
				default:
					break;
				}
			}
		}

		public void widgetSelected(SelectionEvent e) {
			widgetDefaultSelected(e);
		}

	}

	private class PassingStyleComboListener implements ModifyListener {

		public void modifyText(ModifyEvent e) {
			PlayerPosition pos = getCurrentPlayer();
			if (pos != null) {
				int selected = passingStyleCombo.getSelectionIndex();
				switch (selected) {
				case 0:
					pos.setPassingStyle(PassingStyle.PS_BOTH);
					break;
				case 1:
					pos.setPassingStyle(PassingStyle.PS_FORWARD);
					break;
				case 2:
					pos.setPassingStyle(PassingStyle.PS_EXACT);
					break;
				}
			}
		}
	}

	private class HardnessComboListener implements ModifyListener {

		public void modifyText(ModifyEvent e) {
			PlayerPosition pos = getCurrentPlayer();
			if (pos != null) {
				int selected = hardnessCombo.getSelectionIndex();
				switch (selected) {
				case 0:
					pos.setHardness(Hardness.HR_DEFAULT);
					break;
				case 1:
					pos.setHardness(Hardness.HR_SOFT);
					break;
				case 2:
					pos.setHardness(Hardness.HR_NORMAL);
					break;
				case 3:
					pos.setHardness(Hardness.HR_HARD);
					break;
				case 4:
					pos.setHardness(Hardness.HR_NIGHTMARE);
					break;
				}
			}
		}
	}

	public void updateMessages() {
		parentInsance.setRedraw(false);

		deleteScheme.setText(MainWindow.getMessage("DeleteSchemeButton"));
		attackButton.setText(MainWindow.getMessage("AttackViewButton"));
		defenceButton.setText(MainWindow.getMessage("DefenceViewButton"));
		freeKickButton.setText(MainWindow.getMessage("FreeKickViewButton"));
		mainPlayersButton.setText(MainWindow.getMessage("MainPlayersPositionsView"));
		substitutionPlayersButton.setText(MainWindow.getMessage("SubstitutionPlayersPositionsView"));
		playerGroup.setText(MainWindow.getMessage("PlayerSettingsGroupLabel"));

		String labelsNames[] = { MainWindow.getMessage("PositionPlayerX"),
				MainWindow.getMessage("PositionPlayerY"),
				MainWindow.getMessage("PositionLongShot"),
				MainWindow.getMessage("PositionActOnFreekick"),
				MainWindow.getMessage("PositionFantasista"),
				MainWindow.getMessage("PositionDispatcher"),
				MainWindow.getMessage("PositionPersonalDefence"),
				MainWindow.getMessage("PositionPressing"),
				MainWindow.getMessage("PositionKeepBall"),
				MainWindow.getMessage("PositionDefenderAttack"),
				MainWindow.getMessage("PositionPassingStyle"),
				MainWindow.getMessage("PositionHardness") };
		Label labels[] = { playerXLabel, playerYLabel, longShotLabel,
				actOnFreekickLabel, fantasistaLabel, dispatcherLabel,
				personalDefenceLabel, pressingLabel, keepBallLabel,
				defenderAttackLabel, passingStyleLabel, hardnessLabel };

		for (int i = 0; i < labelsNames.length; i++) {
			labels[i].setText(labelsNames[i]);
		}

		passingStyleCombo.removeAll();
		for (int i = 0; i < passingStyles.length; i++) {
			String bundle = MainWindow.getMessage(passingStyles[i]);
			if (bundle == null) {
				bundle = passingStylesEnum[i].toString();
			}
			passingStyleCombo.add(bundle);
			passingStyleCombo.setData(bundle, passingStylesEnum[i]);
		}

		passingStyleCombo.select(0);

		hardnessCombo.removeAll();
		for (int i = 0; i < hardnessTypes.length; i++) {
			String bundle = MainWindow.getMessage(hardnessTypes[i]);
			if (bundle == null) {
				bundle = hardnessEnum[i].toString();
			}
			hardnessCombo.add(bundle);
			hardnessCombo.setData(bundle, hardnessEnum[i]);
		}
		hardnessCombo.select(0);

		teamTacticsGroup.setText(MainWindow.getMessage("teamTacticsGroup"));

		teamCoachSettingsLabel.setText(MainWindow.getMessage("teamCoachSettingsLabel"));
		teamTacticsTacticsLabel.setText(MainWindow.getMessage("teamTacticsTacticsLabel"));
		teamTacticsHardnessLabel.setText(MainWindow.getMessage("teamTacticsHardnessLabel"));
		teamTacticsStyleLabel.setText(MainWindow.getMessage("teamTacticsStyleLabel"));

		teamTacticsHardnessCombo.removeAll();
		for (int i = 1; i < hardnessTypes.length; i++) {
			String bundle = MainWindow.getMessage(hardnessTypes[i]);
			if (bundle == null) {
				bundle = hardnessEnum[i].toString();
			}
			teamTacticsHardnessCombo.add(bundle);
			teamTacticsHardnessCombo.setData(bundle, hardnessEnum[i]);
		}
		teamTacticsHardnessCombo.select(0);

		teamTacticsTacticsCombo.removeAll();
		for (int i = 0; i < teamTacticsTactics.length; i++) {
			String bundle = MainWindow.getMessage(teamTacticsTactics[i]);
			if (bundle == null) {
				bundle = teamTacticsTacticsEnum[i].toString();
			}
			teamTacticsTacticsCombo.add(bundle);
			teamTacticsTacticsCombo.setData(bundle, teamTacticsTacticsEnum[i]);
		}
		teamTacticsTacticsCombo.select(1);

		teamTacticsStyleCombo.removeAll();
		for (int i = 0; i < teamTacticsStyles.length; i++) {
			String bundle = MainWindow.getMessage(teamTacticsStyles[i]);
			if (bundle == null) {
				bundle = teamTacticsEnum[i].toString();
			}
			teamTacticsStyleCombo.add(bundle);
			teamTacticsStyleCombo.setData(bundle, teamTacticsEnum[i]);
		}
		teamTacticsStyleCombo.select(0);
		teamTacticsStyleCombo.setVisibleItemCount(teamTacticsStyles.length);

		teamCoachSettingsCombo.removeAll();
		for (int i = 0; i < coachSettings.length; i++) {
			String bundle = MainWindow.getMessage(coachSettings[i]);
			if (bundle == null) {
				bundle = coachSettingsEnum[i].toString();
			}
			teamCoachSettingsCombo.add(bundle);
			teamCoachSettingsCombo.setData(bundle, coachSettingsEnum[i]);
		}
		teamCoachSettingsCombo.select(0);

		attackGroup.setText(MainWindow.getMessage("attackGroup"));
		defenceGroup.setText(MainWindow.getMessage("defenceGroup"));
		ticketPriceGroup.setText(MainWindow.getMessage("ticketPriceGroup"));
		ticketLabel.setText(MainWindow.getMessage("ticketLabel"));
		timeLabel.setText(MainWindow.getMessage("timeLabel"));
		resultLabel.setText(MainWindow.getMessage("resultLabel"));
		timeLabelD.setText(MainWindow.getMessage("timeLabel"));
		resultLabelD.setText(MainWindow.getMessage("resultLabel"));

		playerGroup.layout();
		attackGroup.layout();
		defenceGroup.layout();
		teamTacticsGroup.layout();

		playerGroup.layout();

		Point spinnerSize = personalDefenceSpinner.computeSize(SWT.DEFAULT,
				SWT.DEFAULT);
		FormData data = new FormData();
		data.right = new FormAttachment(100, -10);
		// data.left = new FormAttachment(100, -10
		// - passingStyleCombo.computeSize(SWT.DEFAULT, SWT.DEFAULT).x);
		data.top = new FormAttachment(passingStyleButton, 3 - spinnerSize.y);
		passingStyleCombo.setLayoutData(data);
		data = new FormData();
		data.right = new FormAttachment(100, -10);
		// data.left = new FormAttachment(100, -10
		// - hardnessCombo.computeSize(SWT.DEFAULT, SWT.DEFAULT).x);
		data.top = new FormAttachment(hardnessButton, 3 - spinnerSize.y);
		hardnessCombo.setLayoutData(data);

		int scrollWidth = rightPanelScrl.getVerticalBar().getSize().x;

		data = new FormData();
		data.left = new FormAttachment(deleteScheme, -deleteScheme.computeSize(
				SWT.DEFAULT, SWT.DEFAULT).x);
		data.right = new FormAttachment(100, -scrollWidth);
		data.top = new FormAttachment(0, 0);
		deleteScheme.setLayoutData(data);

		Button buttons[] = { playerXButton, playerYButton, longShotButton,
				actOnFreekickButton, fantasistaButton, dispatcherButton,
				personalDefenceButton, pressingButton, keepBallButton,
				defenderAttackButton, passingStyleButton, hardnessButton };

		int rightPanelWidth = 0;

		data = new FormData();
		data.left = new FormAttachment(buttons[0], 10);
		data.right = new FormAttachment(labels[0], labels[0].computeSize(
				SWT.DEFAULT, SWT.DEFAULT).x);
		data.top = new FormAttachment(buttons[0], -buttons[0].computeSize(
				SWT.DEFAULT, SWT.DEFAULT).y);
		labels[0].setLayoutData(data);

		for (int i = 1; i < labels.length; i++) {
			data = new FormData();
			data.left = new FormAttachment(buttons[i], 10);
			data.right = new FormAttachment(labels[i], labels[i].computeSize(
					SWT.DEFAULT, SWT.DEFAULT).x);
			int dy1 = buttons[i].computeSize(SWT.DEFAULT, SWT.DEFAULT).y
					- labels[i].computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
			data.top = new FormAttachment(buttons[i], dy1 / 2
					- buttons[i].computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
			labels[i].setLayoutData(data);
		}

		int maxLabelsWidth = 0;
		for (int i = 0; i < labels.length - 2; i++) {
			maxLabelsWidth = Math.max(maxLabelsWidth, labels[i].computeSize(
					SWT.DEFAULT, SWT.DEFAULT).x);
		}

		int groupWidth = 0;
		groupWidth = 10;
		groupWidth += buttons[0].computeSize(SWT.DEFAULT, SWT.DEFAULT).x;
		groupWidth += 6;
		groupWidth += maxLabelsWidth;
		groupWidth += 6;
		groupWidth += spinnerSize.x;
		groupWidth += 10;
		groupWidth = Math
				.max(playerGroup.computeSize(SWT.DEFAULT, SWT.DEFAULT).x,
						groupWidth);

		maxLabelsWidth = 6;
		maxLabelsWidth += buttons[0].computeSize(SWT.DEFAULT, SWT.DEFAULT).x;
		maxLabelsWidth += 10;
		maxLabelsWidth += passingStyleCombo.computeSize(SWT.DEFAULT,
				SWT.DEFAULT).x;
		maxLabelsWidth += 10;
		maxLabelsWidth += passingStyleLabel.computeSize(SWT.DEFAULT,
				SWT.DEFAULT).x;
		maxLabelsWidth += 10;
		groupWidth = Math.max(groupWidth, maxLabelsWidth);

		maxLabelsWidth = 6;
		maxLabelsWidth += buttons[0].computeSize(SWT.DEFAULT, SWT.DEFAULT).x;
		maxLabelsWidth += 10;
		maxLabelsWidth += hardnessCombo.computeSize(SWT.DEFAULT, SWT.DEFAULT).x;
		maxLabelsWidth += 10;
		maxLabelsWidth += hardnessLabel.computeSize(SWT.DEFAULT, SWT.DEFAULT).x;
		maxLabelsWidth += 10;
		groupWidth = Math.max(groupWidth, maxLabelsWidth);

		data = new FormData();
		data.left = new FormAttachment(0, 10);
		data.right = new FormAttachment(playerGroup, groupWidth);
		data.top = new FormAttachment(playerNameLabel, 6);
		data.bottom = new FormAttachment(playerGroup, playerGroup.computeSize(
				SWT.DEFAULT, SWT.DEFAULT).y
				+ spinnerSize.y / 2);
		playerGroup.setLayoutData(data);

		rightPanelWidth = 2 * scrollWidth;
		rightPanelWidth += attackButton.computeSize(SWT.DEFAULT, SWT.DEFAULT).x;
		rightPanelWidth += defenceButton.computeSize(SWT.DEFAULT, SWT.DEFAULT).x;
		rightPanelWidth += freeKickButton.computeSize(SWT.DEFAULT, SWT.DEFAULT).x;
		rightPanelWidth = Math.max(rightPanelWidth, playerGroup.computeSize(
				SWT.DEFAULT, SWT.DEFAULT).x);
		rightPanelWidth = Math.max(rightPanelWidth, groupWidth + 20);

		data = (FormData) rightPanelScrl.getLayoutData();
		data.left = new FormAttachment(rightPanelScrl, -rightPanelWidth);

		int scrollHeight = 0;
		scrollHeight = schemeCombo.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
		scrollHeight += 10;
		scrollHeight += freeKickButton.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
		scrollHeight += 6;
		scrollHeight += playerNameLabel.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
		scrollHeight += 6;
		scrollHeight += playerGroup.computeSize(SWT.DEFAULT, SWT.DEFAULT).y
				+ spinnerSize.y / 2;

		rightPanelScrl.setMinWidth(rightPanelWidth);
		rightPanelScrl.setMinHeight(scrollHeight);

		playerGroup.layout();
		rightPanel.layout();
		parentInsance.layout();
		parentInsance.setRedraw(true);
	}

	@Override
	public void redraw() {
	}

}
