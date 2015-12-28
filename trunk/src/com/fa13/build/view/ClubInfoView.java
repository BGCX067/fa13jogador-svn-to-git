package com.fa13.build.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import com.fa13.build.model.Team;

public class ClubInfoView implements UIItem{
	
	private TabFolder parentInstance;
	private final TabItem mainItem;
	private final Composite mainComposite;
	private final Composite topPanel, bottomPanel;
	
	private Team currentTeam;
	
	private String password;
	
	public ClubInfoView(TabFolder parent) {
		parentInstance = parent;
		mainItem = new TabItem(parentInstance, SWT.NONE);
		FormLayout formLayout = new FormLayout();
		mainComposite = new Composite(parent, SWT.NONE);
		mainItem.setControl(mainComposite);
		mainComposite.setRedraw(false);
		mainComposite.setLayout(formLayout);
		
		topPanel = new Composite(mainComposite, SWT.BORDER);
		topPanel.setLayout(new FormLayout());
		bottomPanel = new Composite(mainComposite, SWT.BORDER);

		
		FormData data;
		data = new FormData();
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

		bottomPanel.setLayout(new RowLayout());
		
		

		updateMessages();
		mainComposite.setRedraw(true);
	}

	public void updateAll() {
		if (MainWindow.getAllInstance() == null) return;
		currentTeam = MainWindow.getAllInstance().getCurrentTeam();
		redraw();
	}

	public void updatePassword(String password) {
		this.password = password;
	}

	public void updateMessages() {
		
	}

	public void redraw() {
		// TODO Auto-generated method stub
		
	}
	
}
