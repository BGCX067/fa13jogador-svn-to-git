package com.fa13.build.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

public class StatisticsView implements UIItem {
	
	private TabFolder parentInstance;
	private final TabItem mainItem;
	private final Composite mainComposite;
	private final Composite topPanels[];
	private final Composite topPanel, bottomPanel;
	private final StackLayout topLayout;
	private final Button viewBtns[];
	
	private final StatisticsTeamView teamStatisticsView;
	private final StatisticsPlayerView playerStatisticsView;
	private final StatisticsManagerView managerStatisticsView;
	
	public StatisticsView(TabFolder parent) {
		parentInstance = parent;
		mainItem = new TabItem(parentInstance, SWT.NONE);
		FormLayout formLayout = new FormLayout();
		mainComposite = new Composite(parent, SWT.NONE);
		mainItem.setControl(mainComposite);
		mainComposite.setRedraw(false);
		mainComposite.setLayout(formLayout);
		
		topLayout = new StackLayout();
		
		topPanel = new Composite(mainComposite, SWT.BORDER);
		topPanel.setLayout(topLayout);
		bottomPanel = new Composite(mainComposite, SWT.BORDER);
		
		topPanels = new Composite[3];
		topPanels[0] = new Composite(topPanel, SWT.NONE);
		topPanels[1] = new Composite(topPanel, SWT.NONE);
		topPanels[2] = new Composite(topPanel, SWT.NONE);
		
		topLayout.topControl = topPanels[0];
		
		FormData data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0);
		data.bottom = new FormAttachment(bottomPanel, -3);
		data.top = new FormAttachment(0, 0);
		
		topPanel.setLayoutData(data);
		
		teamStatisticsView = new StatisticsTeamView(topPanels[0]);
		playerStatisticsView = new StatisticsPlayerView(topPanels[1]);
		managerStatisticsView = new StatisticsManagerView(topPanels[2]);

		data = new FormData();
		data.bottom = new FormAttachment(100, 0);
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0);
		bottomPanel.setLayoutData(data);

		bottomPanel.setLayout(new RowLayout());

		topPanels[0].setLayout(new FormLayout());
		topPanels[1].setLayout(new FormLayout());
		topPanels[2].setLayout(new FormLayout());
		
		viewBtns = new Button[3];
		viewBtns[0] = new Button(bottomPanel, SWT.TOGGLE);
		viewBtns[0].setSelection(true);
		viewBtns[1] = new Button(bottomPanel, SWT.TOGGLE);
		viewBtns[2] = new Button(bottomPanel, SWT.TOGGLE);
		

		viewBtns[0].addSelectionListener(new ViewBtnListner(0, viewBtns));
		viewBtns[1].addSelectionListener(new ViewBtnListner(1, viewBtns));
		viewBtns[2].addSelectionListener(new ViewBtnListner(2, viewBtns));
		
		updateMessages();
		mainComposite.setRedraw(true);
	}

	public void redraw() {
		bottomPanel.layout();
		topPanel.layout();
		for (Composite panel: topPanels) {
			panel.layout();
		}
	}

	public void updateAll() {
		teamStatisticsView.updateAll();
		playerStatisticsView.updateAll();
		managerStatisticsView.updateAll();
	}

	public void updateMessages() {
		teamStatisticsView.updateMessages();
		playerStatisticsView.updateMessages();
		managerStatisticsView.updateMessages();
		
		mainItem.setText(MainWindow.getMessage("statistics.tab.title"));
		
		viewBtns[0].setText(MainWindow.getMessage("statistics.team.title"));
		viewBtns[1].setText(MainWindow.getMessage("statistics.player.title"));
		viewBtns[2].setText(MainWindow.getMessage("statistics.manager.title"));
		redraw();
	}

	public void updatePassword(String password) {
		return;
	}
	
	public class ViewBtnListner implements SelectionListener {

		int index;
		Button btns[];

		public ViewBtnListner(int index, Button[] btns) {
			this.btns = btns;
			this.index = Math.max(Math.min(btns.length - 1, index), 0);
		}

		public void widgetDefaultSelected(SelectionEvent e) {
			for (Button button: btns) {
				button.setSelection(false);
			}
			btns[index].setSelection(true);
			topLayout.topControl = topPanels[index];
			topPanel.layout();
		}

		public void widgetSelected(SelectionEvent e) {
			widgetDefaultSelected(e);
		}

	}

}
