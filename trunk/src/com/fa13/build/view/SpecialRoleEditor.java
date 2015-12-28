package com.fa13.build.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import com.fa13.build.model.GameForm;
import com.fa13.build.model.PlayerPosition;

public class SpecialRoleEditor {
	
	private Display display;
	private Shell parent;
	private PlayerPosition playerPosition;
	private Point topRightCorner;
	private GameForm gameFormInstance;
	private Shell popupShell;
	private Button mainButtons[];
	private Label roleNameLabels[];
	private Button assistButtons[];
	private Label mainLabel;
	private Label roleLabel;
	private Label assistLabel;
	private Button closeButton;	
	private Button clearButton;
	private Label penaltyLabel;
	private Combo penaltyCombo;
	private String flags[];
	private boolean fullView;
	
	public SpecialRoleEditor(Display parentDisplay,
			Shell parentShell, PlayerPosition playerPos, Point rightCorner,
			GameForm gameFormInst, boolean fullView) {
		super();
		this.display = parentDisplay;
		this.parent = parentShell;
		this.topRightCorner = rightCorner;
		this.gameFormInstance = gameFormInst;
		this.fullView = fullView;

		int numberOfColumns = (fullView) ? 6 : 4; 
		
		String default_flags[] = {
				MainWindow.getMessage("SRCaptain"),
				MainWindow.getMessage("SRDirectFreekick"),
				MainWindow.getMessage("SRIndirectFreekick"),
				MainWindow.getMessage("SRPenalty"),
				MainWindow.getMessage("SRLeftCorner"),
				MainWindow.getMessage("SRRightCorner"),
		};
		flags = default_flags;
		int k;
		popupShell = new Shell(display, SWT.ON_TOP);
		popupShell.setParent(parent);
//		popupShell.setBounds(rightCorner);
		GridLayout layout = new GridLayout(numberOfColumns, false);
		layout.horizontalSpacing = 0;
		popupShell.setLayout(layout);
		mainButtons = new Button[flags.length];
		assistButtons = new Button[flags.length];
		roleNameLabels = new Label[flags.length];
		GridData gridData;

		mainLabel = new Label(popupShell, SWT.NONE);
		gridData = new GridData(SWT.CENTER, SWT.END, true, false);
		gridData.horizontalSpan = 2;
		mainLabel.setLayoutData(gridData);
		mainLabel.setText(MainWindow.getMessage("SRMainRole"));
		
		roleLabel = new Label(popupShell, SWT.NONE);
		gridData = new GridData(SWT.CENTER, SWT.END, true, false);
		gridData.horizontalSpan = 2;
		roleLabel.setLayoutData(gridData);
		roleLabel.setText(MainWindow.getMessage("SRRoleName"));
		
		if (fullView ){
			assistLabel = new Label(popupShell, SWT.NONE);
			gridData = new GridData(SWT.CENTER, SWT.END, true, false);
			gridData.horizontalSpan = 2;
			assistLabel.setLayoutData(gridData);
			assistLabel.setText(MainWindow.getMessage("SRAssistantRole"));
		}
		
		for (k = 0; k < flags.length; k++) {
			mainButtons[k] = new Button(popupShell, SWT.CHECK);
			//mainButton.setText(flags[k]);
			mainButtons[k].setData("flag", flags[k]);
			mainButtons[k].addSelectionListener(new SelectionListener() {
				public void widgetSelected(SelectionEvent e) {
					widgetDefaultSelected(e);
				}

				public void widgetDefaultSelected(SelectionEvent e) {
					String flg = (String) e.widget.getData("flag");
					Button btn = ((Button)e.widget); 
					boolean val = setSRflag(flg, playerPosition, btn.getSelection());
					btn.setSelection(val);
					Redraw();
				}
			});
			gridData = new GridData(SWT.BEGINNING, SWT.END, true, false);
			gridData.horizontalSpan = 2;
			mainButtons[k].setLayoutData(gridData);
			
			roleNameLabels[k] = new Label(popupShell, SWT.NONE);
			roleNameLabels[k].setAlignment(SWT.CENTER);
			roleNameLabels[k].setText(flags[k]);
			gridData = new GridData(SWT.CENTER, SWT.END, true, false);
			gridData.horizontalSpan = 2;
			roleNameLabels[k].setLayoutData(gridData);
			
			if ( fullView ) {
				assistButtons[k] = new Button(popupShell, SWT.CHECK);
				gridData = new GridData(SWT.END, SWT.END, true, false);
				gridData.horizontalSpan = 2;
				assistButtons[k].setLayoutData(gridData);
				assistButtons[k].setData("flag", flags[k]);
				assistButtons[k].addSelectionListener(new SelectionListener() {
					public void widgetSelected(SelectionEvent e) {
						widgetDefaultSelected(e);
					}
	
					public void widgetDefaultSelected(SelectionEvent e) {
						String flg = (String) e.widget.getData("flag");
						Button btn = ((Button)e.widget); 
						boolean val = setSRAflag(flg, playerPosition, btn.getSelection());
						btn.setSelection(val);
						Redraw();
					}
				});
			}
		}

		if ( fullView ) {
			penaltyLabel = new Label(popupShell, SWT.NONE);
			gridData = new GridData(SWT.BEGINNING, SWT.CENTER, false, false);
			gridData.horizontalSpan = 2;
			penaltyLabel.setLayoutData(gridData);
			penaltyLabel.setText(MainWindow.getMessage("SRPenalty"));
			
			penaltyCombo = new Combo(popupShell, SWT.DROP_DOWN | SWT.READ_ONLY);
			gridData = new GridData(SWT.BEGINNING, SWT.CENTER, true, false);
			gridData.horizontalSpan = 4;
			penaltyCombo.setLayoutData(gridData);
			for (int j = 0; j < 12; j++){
				penaltyCombo.add(String.valueOf(j));
			}
			penaltyCombo.addModifyListener(new ModifyListener() {
				
				public void modifyText(ModifyEvent e) {
					Combo cmb = (Combo)e.widget;
					int order = Integer.valueOf(cmb.getText());
					for ( int i = 0; i < gameFormInstance.getFirstTeam().length; i++) {
						if (gameFormInstance.getFirstTeam()[i].getPenaltyOrder() == order ){
							gameFormInstance.getFirstTeam()[i].setPenaltyOrder(0);
						}
					}
					playerPosition.setPenaltyOrder(order);
				}
			});
		}

		closeButton = new Button(popupShell, SWT.PUSH);
		closeButton.setText(MainWindow.getMessage("global.ok"));
		gridData = new GridData(SWT.FILL, SWT.END, true, false);
		gridData.horizontalSpan = numberOfColumns/2;
		closeButton.setLayoutData(gridData);
		closeButton.addSelectionListener( new SelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				widgetDefaultSelected(e);
			}
			
			public void widgetDefaultSelected(SelectionEvent e) {
				popupShell.setVisible(false);
				//caller.redraw();
			}
		});

		clearButton = new Button(popupShell, SWT.PUSH);
		clearButton.setText(MainWindow.getMessage("Clear"));
		gridData = new GridData(SWT.END, SWT.END, true, false);
		gridData.horizontalSpan = numberOfColumns/2;
		clearButton.setLayoutData(gridData);
		clearButton.addSelectionListener( new SelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				widgetDefaultSelected(e);
			}
			
			public void widgetDefaultSelected(SelectionEvent e) {
				clearAllSRAflags(playerPosition);
				clearAllSRflags(playerPosition);
				playerPosition.setPenaltyOrder(0);
				if ( !SpecialRoleEditor.this.fullView ){
					playerPosition.setNumber(0);
				}
				Redraw();
			}
		});

		popupShell.pack();
		popupShell.addShellListener(new ShellListener() {

			public void shellIconified(ShellEvent e) {
			}
			public void shellDeiconified(ShellEvent e) {
			}

			public void shellDeactivated(ShellEvent e) {
				popupShell.setVisible(false);
				//caller.redraw();
			}
			public void shellClosed(ShellEvent e) {
			}
			public void shellActivated(ShellEvent e) {
			}
		});
		
		popupShell.setVisible(false);
//		popupShell.open();
		popupShell.setVisible(false);
		updatePlayerPosition(playerPos);
	}

	private void Redraw(){
		int i = 0;
		for ( i = 0; i< mainButtons.length ; i++){
			String st = (String)mainButtons[i].getData("flag");
			mainButtons[i].setSelection(getSRflag(st, playerPosition));
			if ( assistButtons[i] != null )
				assistButtons[i].setSelection(getSRAflag(st, playerPosition));
		}
		if ( penaltyCombo != null )
			penaltyCombo.select(playerPosition.getPenaltyOrder());
	}
	
	public void Show(Point rightCorner){
		if ( rightCorner != null ){
			Rectangle rect = popupShell.getBounds();
			rect.x = rightCorner.x - rect.width;
			rect.y = rightCorner.y;
			popupShell.setBounds(rect);
		}
		popupShell.open();
		popupShell.setVisible(true);
		//while (popupShell.isVisible()) {
		while (popupShell.isVisible()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}
	
	private boolean setSRflag(String flg, PlayerPosition subst, boolean val){
		PlayerPosition[] team = gameFormInstance.getFirstTeam();
		PlayerPosition[] bench = gameFormInstance.getSubstitutions();
		int i = 0;
		if ( flg.compareTo(MainWindow.getMessage("SRCaptain")) == 0 ){
			if ( fullView ){
				for (i = 0; i < team.length ; i++){
					if (val && team[i].isCaptain()) team[i].setCaptain(false);
				}
				for (i = 0; i < bench.length ; i++){
					if (val && bench[i].isCaptain()) team[i].setCaptain(false);
				}
				if ( val && subst.isCaptainAssistant() ) subst.setCaptainAssistant(false);
			}
			subst.setCaptain(val);
		} else if ( flg.compareTo(MainWindow.getMessage("SRDirectFreekick")) == 0 ) {
			if ( fullView ){
				for (i = 0; i < team.length ; i++){
					if (val && team[i].isDirectFreekick()) team[i].setDirectFreekick(false);
				}
				for (i = 0; i < bench.length ; i++){
					if (val && bench[i].isDirectFreekick()) team[i].setDirectFreekick(false);
				}
				if (val && subst.isDirectFreekickAssistant()) subst.setDirectFreekickAssistant(false);
			}
			subst.setDirectFreekick(val);
		} else  if ( flg.compareTo(MainWindow.getMessage("SRIndirectFreekick")) == 0) {
			if ( fullView ){
				for (i = 0; i < team.length ; i++){
					if (val && team[i].isIndirectFreekick()) team[i].setIndirectFreekick(false);
				}
				for (i = 0; i < bench.length ; i++){
					if (val && bench[i].isIndirectFreekick()) team[i].setIndirectFreekick(false);
				}
				if (val && subst.isIndirectFreekickAssistant()) subst.setIndirectFreekickAssistant(false);
			}
			subst.setIndirectFreekick(val);
		} else if ( flg.compareTo(MainWindow.getMessage("SRPenalty")) == 0 ){
			if ( fullView ){
				for (i = 0; i < team.length ; i++){
					if (val && team[i].isPenalty()) team[i].setPenalty(false);
				}
				for (i = 0; i < bench.length ; i++){
					if (val && bench[i].isPenalty()) team[i].setPenalty(false);
				}
				if (val && subst.isPenaltyAssistant()) subst.setPenaltyAssistant(false);
			}
			subst.setPenalty(val);
		} else if ( flg.compareTo(MainWindow.getMessage("SRLeftCorner")) == 0 ){
			if ( fullView ){
				for (i = 0; i < team.length ; i++){
					if (val && team[i].isLeftCorner()) team[i].setLeftCorner(false);
				}
				for (i = 0; i < bench.length ; i++){
					if (val && bench[i].isLeftCorner()) team[i].setLeftCorner(false);
				}
				if (val && subst.isLeftCornerAssistant()) subst.setLeftCornerAssistant(false);
			}
			subst.setLeftCorner(val);
		} else if ( flg.compareTo(MainWindow.getMessage("SRRightCorner")) == 0 ){
			if ( fullView ){
				for (i = 0; i < team.length ; i++){
					if (val && team[i].isRightCorner()) team[i].setRightCorner(false);
				}
				for (i = 0; i < bench.length ; i++){
					if (val && bench[i].isRightCorner()) team[i].setRightCorner(false);
				}
				if (val && subst.isRightCornerAssistant()) subst.setRightCornerAssistant(false);
			}
			subst.setRightCorner(val);
		} else if ( flg.compareTo(MainWindow.getMessage("Clear")) == 0 ){
			val = false;
			clearAllSRAflags(subst);
			clearAllSRflags(subst);
		}
		return val;
	}

	private boolean getSRflag(String flg, PlayerPosition subst){
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
	
	private boolean setSRAflag(String flg, PlayerPosition subst, boolean val){
		PlayerPosition[] team = gameFormInstance.getFirstTeam();
		PlayerPosition[] bench = gameFormInstance.getSubstitutions();
		int i = 0;
		if ( flg.compareTo(MainWindow.getMessage("SRCaptain")) == 0 ){
			for (i = 0; i < team.length ; i++){
				if (val && team[i].isCaptainAssistant()) team[i].setCaptainAssistant(false);
			}
			for (i = 0; i < bench.length ; i++){
				if (val && bench[i].isCaptainAssistant()) team[i].setCaptainAssistant(false);
			}
			if ( val && subst.isCaptain() ) subst.setCaptain(false);
			subst.setCaptainAssistant(val);
		} else if ( flg.compareTo(MainWindow.getMessage("SRDirectFreekick")) == 0 ) {
			for (i = 0; i < team.length ; i++){
				if (val && team[i].isDirectFreekickAssistant()) team[i].setDirectFreekickAssistant(false);
			}
			for (i = 0; i < bench.length ; i++){
				if (val && bench[i].isDirectFreekickAssistant()) team[i].setDirectFreekickAssistant(false);
			}
			if (val && subst.isDirectFreekick()) subst.setDirectFreekick(false);
			subst.setDirectFreekickAssistant(val);
		} else  if ( flg.compareTo(MainWindow.getMessage("SRIndirectFreekick")) == 0) {
			for (i = 0; i < team.length ; i++){
				if (val && team[i].isIndirectFreekickAssistant()) team[i].setIndirectFreekickAssistant(false);
			}
			for (i = 0; i < bench.length ; i++){
				if (val && bench[i].isIndirectFreekickAssistant()) team[i].setIndirectFreekickAssistant(false);
			}
			if (val && subst.isIndirectFreekick()) subst.setIndirectFreekick(false);
			subst.setIndirectFreekickAssistant(val);
		} else if ( flg.compareTo(MainWindow.getMessage("SRPenalty")) == 0 ){
			for (i = 0; i < team.length ; i++){
				if (val && team[i].isPenaltyAssistant()) team[i].setPenaltyAssistant(false);
			}
			for (i = 0; i < bench.length ; i++){
				if (val && bench[i].isPenaltyAssistant()) team[i].setPenaltyAssistant(false);
			}
			if (val && subst.isPenalty()) subst.setPenalty(false);
			subst.setPenaltyAssistant(val);
		} else if ( flg.compareTo(MainWindow.getMessage("SRLeftCorner")) == 0 ){
			for (i = 0; i < team.length ; i++){
				if (val && team[i].isLeftCornerAssistant()) team[i].setLeftCornerAssistant(false);
			}
			for (i = 0; i < bench.length ; i++){
				if (val && bench[i].isLeftCornerAssistant()) team[i].setLeftCornerAssistant(false);
			}
			if (val && subst.isLeftCorner()) subst.setLeftCorner(false);
			subst.setLeftCornerAssistant(val);
		} else if ( flg.compareTo(MainWindow.getMessage("SRRightCorner")) == 0 ){
			for (i = 0; i < team.length ; i++){
				if (val && team[i].isRightCornerAssistant()) team[i].setRightCornerAssistant(false);
			}
			for (i = 0; i < bench.length ; i++){
				if (val && bench[i].isRightCornerAssistant()) team[i].setRightCornerAssistant(false);
			}
			if (val && subst.isRightCorner()) subst.setRightCorner(false);
			subst.setRightCornerAssistant(val);
		} else if ( flg.compareTo(MainWindow.getMessage("Clear")) == 0 ){
			val = false;
			clearAllSRAflags(subst);
		}
		return val;
	}

	private boolean getSRAflag(String flg, PlayerPosition subst){
		if ( flg.compareTo(MainWindow.getMessage("SRCaptain")) == 0 ){
			return subst.isCaptainAssistant();
		} else if ( flg.compareTo(MainWindow.getMessage("SRDirectFreekick")) == 0 ) {
			return subst.isDirectFreekickAssistant();
		} else  if ( flg.compareTo(MainWindow.getMessage("SRIndirectFreekick")) == 0 ){
			return subst.isIndirectFreekickAssistant();
		} else if ( flg.compareTo(MainWindow.getMessage("SRPenalty")) == 0 ){
			return subst.isPenaltyAssistant();
		} else if ( flg.compareTo(MainWindow.getMessage("SRLeftCorner")) == 0 ){
			return subst.isLeftCornerAssistant();
		} else if ( flg.compareTo(MainWindow.getMessage("SRRightCorner")) == 0 ){
			return subst.isRightCornerAssistant();
		}
		return false;
	}
	
	private void clearAllSRAflags(PlayerPosition subst){
		subst.setCaptainAssistant(false);
		subst.setDirectFreekickAssistant(false);
		subst.setIndirectFreekickAssistant(false);
		subst.setPenaltyAssistant(false);
		subst.setLeftCornerAssistant(false);
		subst.setRightCornerAssistant(false);
	}
	
	private void clearAllSRflags(PlayerPosition subst){
		subst.setCaptain(false);
		subst.setDirectFreekick(false);
		subst.setIndirectFreekick(false);
		subst.setPenalty(false);
		subst.setLeftCorner(false);
		subst.setRightCorner(false);
	}
	
	public void updatePlayerPosition(PlayerPosition playerPos){
		 this.playerPosition = playerPos;
		 if (playerPos != null){
			for ( int  k = 0; k < flags.length; k++) {
				mainButtons[k].setSelection(getSRflag(flags[k], playerPos));
				if ( assistButtons[k] != null )
					assistButtons[k].setSelection(getSRAflag(flags[k], playerPos));
			}
			if ( penaltyCombo != null )
				penaltyCombo.select(playerPos.getPenaltyOrder());
		 }
	}
	
	public void updateMessages() {
		String defaultFlags[] = {
				MainWindow.getMessage("SRCaptain"),
				MainWindow.getMessage("SRDirectFreekick"),
				MainWindow.getMessage("SRIndirectFreekick"),
				MainWindow.getMessage("SRPenalty"),
				MainWindow.getMessage("SRLeftCorner"),
				MainWindow.getMessage("SRRightCorner"),
		};
		flags = defaultFlags;
		mainLabel.setText(MainWindow.getMessage("SRMainRole"));
		roleLabel.setText(MainWindow.getMessage("SRRoleName"));
		if ( assistLabel != null )
			assistLabel.setText(MainWindow.getMessage("SRAssistantRole"));
		for (int k = 0; k < flags.length; k++) {
			mainButtons[k].setData("flag", flags[k]);
			roleNameLabels[k].setText(flags[k]);
			if ( assistButtons[k] != null )
				assistButtons[k].setData("flag", flags[k]);
		}
		if ( penaltyLabel != null )
			penaltyLabel.setText(MainWindow.getMessage("SRPenalty"));
		closeButton.setText(MainWindow.getMessage("global.ok"));
		clearButton.setText(MainWindow.getMessage("Clear"));
		popupShell.pack();
		popupShell.layout(true);
	}
}
