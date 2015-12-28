package com.fa13.build.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.*;

public class DaysOfRestComposite extends Composite {

	Label restLabel;
	Slider restScale;
	Text restText;
	TeamUIItem uiItem = null;
	static final int REST_SLIDER_MAX = 30;

	public DaysOfRestComposite(Composite parent, int style, TeamUIItem uiItem) {
		super(parent, style);
		this.uiItem = uiItem;

		//GridLayout gridLayout = new GridLayout(2, false);
		setLayout(new FormLayout());

		restLabel = new Label(this, SWT.NONE);
//		GridData gridData = new GridData(GridData.BEGINNING, GridData.BEGINNING,
//				false, false);
//		restLabel.setLayoutData(gridData);
		FormData data = new FormData();
		data.left = new FormAttachment(0,0);
		data.top = new FormAttachment(0,0);
		restLabel.setText(MainWindow.getMessage("PTDaysOfRest") + ": ");

		restText = new Text(this, SWT.LEFT | SWT.BORDER);

		restScale = new Slider(this, SWT.NONE);

//		gridData = new GridData(GridData.FILL, GridData.BEGINNING, true,
//				false, 2, 1);
//		restScale.setLayoutData(gridData);
		
		data = new FormData();
		data.left = new FormAttachment(0,0);
		data.right = new FormAttachment(100,0);
		data.top = new FormAttachment(restText,5);
		restScale.setLayoutData(data);
		
		restScale.setMinimum(0);
		restScale.setMaximum(REST_SLIDER_MAX + 10);
		restScale.setPageIncrement(7);
		restScale.setIncrement(1);
		restScale.addSelectionListener(new SliderListener());

//		gridData = new GridData(GridData.BEGINNING, GridData.BEGINNING, false,
//				false);
//		gridData.widthHint = 20;
//		restText.setLayoutData(gridData);
		data = new FormData();
		data.left = new FormAttachment(restLabel,0);
		data.right = new FormAttachment(restText, 50);
		data.top = new FormAttachment(0,0);
		restText.setLayoutData(data);
		restText.setTextLimit(3);
		restText.setText("0");
		restText.addListener(SWT.Verify, new RestTextListener());
		restText.addListener(SWT.FocusOut, new RestTextListener());
		restText.addListener(SWT.Traverse, new RestTextListener());
	}

	public void setDaysOfRest(int daysOfRest) {
		if (restText != null) {
			restText.setText(String.valueOf(daysOfRest));
			if (uiItem != null)
				uiItem.updateDaysOfRest(daysOfRest);
		}
	}

	public int getDaysOfRest() {
		if (restText != null)
			return getIntegerValue(restText.getText());
		else
			return 0;
	}

	public static int getIntegerValue(String st) {
		if (st == null || st.isEmpty())
			return 0;
		return Integer.valueOf(st);
	}

	private class RestTextListener implements Listener {

		public void handleEvent(final Event e) {
			int val = -1;
			try {
				val = getIntegerValue(restText.getText());
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
					if (uiItem != null)
						uiItem.updateDaysOfRest(val);
				}
				break;
			case SWT.FocusOut:
			case SWT.Modify:
				restScale.setSelection(val);
				restText.setText(String.valueOf(val));
				if (uiItem != null)
					uiItem.updateDaysOfRest(val);
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

	private class SliderListener implements SelectionListener {

		public void widgetSelected(SelectionEvent e) {
			int val = restScale.getSelection();
			restText.setText(String.valueOf(val));
			if (uiItem != null)
				uiItem.updateDaysOfRest(val);
		}

		public void widgetDefaultSelected(SelectionEvent e) {
			int val = restScale.getSelection();
			restText.setText(String.valueOf(val));
			if (uiItem != null)
				uiItem.updateDaysOfRest(val);
		}
	}

	public void updateMessages() {
		restLabel.setText(MainWindow.getMessage("PTDaysOfRest") + ": ");
		layout();
	}
}
