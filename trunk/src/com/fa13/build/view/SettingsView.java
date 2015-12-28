package com.fa13.build.view;

import java.util.Properties;
import java.util.ResourceBundle;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class SettingsView extends Dialog {

	Properties settings;
	Properties settingsDef;

	public Properties getSettings() {
		return settings;
	}

	public void setSettings(Properties settings) {
		this.settings = settings;
	}

	public SettingsView(Shell parent) {
		super(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
	}

	Combo langCombo;
	Label langLabel;
	Button okButton;
	Button cancelButton;

	public Properties open(ResourceBundle messages, Properties settingsDefault) {
		final Shell shell = new Shell(getParent(), getStyle() | SWT.APPLICATION_MODAL);
		shell.setText(getText());

		settingsDef = settingsDefault;
		settings = settingsDefault;
		
		shell.setLayout(new GridLayout(2, false));
		GridData data = new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false);
		langLabel = new Label(shell, SWT.NONE);
		langLabel.setLayoutData(data);
		data = new GridData(SWT.END, SWT.BEGINNING, true, false);
		langCombo = new Combo(shell, SWT.DROP_DOWN);
		langCombo.setLayoutData(data);
		langLabel.setText(messages.getString("settings.language"));
		
		okButton = new Button(shell, SWT.PUSH);
		okButton.setText(messages.getString("global.ok"));
		okButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				widgetDefaultSelected(e);				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				shell.close();
			}
		});
		cancelButton = new Button(shell, SWT.PUSH);
		cancelButton.setText(messages.getString("global.cancel"));
		cancelButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				widgetDefaultSelected(e);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				settings = settingsDef;
				shell.close();
			}
		});
		
		langCombo.add("English");
		langCombo.add("Russian");
		langCombo.setData("English", "/com/fa13/build/resources/properties/MessagesBundle_en_US.properties");
		langCombo.setData("Russian", "/com/fa13/build/resources/properties/MessagesBundle_ru_RU.properties");
		langCombo.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				widgetDefaultSelected(e);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				int index =langCombo.getSelectionIndex();
				if (index >= 0) {
					settings.setProperty("langIndex", String.valueOf(index));
					String lang = langCombo.getItem(index);
					String data = (String) langCombo.getData(lang);
					settings.setProperty("lang", data);
				}
			}
		});
		int defIndex = Integer.valueOf(settingsDefault.getProperty("langIndex", "0")); 
		langCombo.select(defIndex);
		shell.setDefaultButton(okButton);
		shell.pack();
		shell.open();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return settings;
	}
}
