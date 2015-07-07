package it.rocci.clipboss.ui;

import it.rocci.clipboss.ui.component.Dialog;
import it.rocci.clipboss.utils.Configuration;
import it.rocci.clipboss.utils.Utils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.logging.Level;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.TitledBorder;

public class SettingsDialog extends Dialog implements ActionListener {

	public SettingsDialog() {
		super();
		this.setTitle(Utils.getLabel("settings.title"));
		this.setDescription(Utils.getLabel("settings.description"));
		this.setIcon(Utils.getIcon("settings.png"));

		final JPanel panelCenter = new JPanel();
		panelCenter.setBackground(Utils.getColorBackground());
		panelCenter.setLayout(new BoxLayout(panelCenter, BoxLayout.PAGE_AXIS));
		panelCenter.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		TitledBorder title = BorderFactory.createTitledBorder(Utils
				.getLabel("settings.ui"));
		final JPanel panelUI = new JPanel();
		panelUI.setBorder(title);
		panelUI.setOpaque(false);
		panelUI.setLayout(new BoxLayout(panelUI, BoxLayout.PAGE_AXIS));

		panelUI.add(getLocaleSettings("settings.ui.locale"));

		panelUI.add(getThemeSettings("settings.ui.theme"));

		title = BorderFactory.createTitledBorder(Utils
				.getLabel("settings.function"));
		final JPanel panelFunc = new JPanel();
		panelFunc.setOpaque(false);
		panelFunc.setBorder(title);
		panelFunc.setLayout(new BoxLayout(panelFunc, BoxLayout.PAGE_AXIS));
		
		panelFunc.add(getBooleanSettings("settings.function.autorun"));
		panelFunc.add(getBooleanSettings("settings.function.minimizze"));
		panelFunc.add(getNumberSettings("settings.function.maxitem")); 
		
		panelCenter.add(panelUI);
		panelCenter.add(panelFunc);
		panelCenter.add(Box.createVerticalGlue());
		
		JLabel label = new JLabel(Utils.getLabel("settings.note"));
		this.footer.add(label, BorderLayout.LINE_START);
		
		this.add(panelCenter, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().contains("settings.ui.")) {
			final JComboBox cb = (JComboBox) e.getSource();
			final JLabel lbl = (JLabel) cb.getSelectedItem();
			Configuration.setString(e.getActionCommand(), lbl.getToolTipText());
		} else if (e.getActionCommand().contains("settings.function.")) {
			final JCheckBox chk = (JCheckBox) e.getSource();
			Configuration.setString(e.getActionCommand(), String.valueOf(chk.isSelected()));
		}
	}

	private JPanel getLocaleSettings(String key) {
		String value = Configuration.getString(key);

		JPanel locale = new JPanel();
		locale.setLayout(new BoxLayout(locale, BoxLayout.LINE_AXIS));
		locale.setBorder(BorderFactory.createEmptyBorder(2, 5, 5, 5));
		locale.setPreferredSize(new Dimension(500, 30));
		locale.setMaximumSize(new Dimension(1000,30));
		locale.setMinimumSize(new Dimension(200,30));
		locale.setOpaque(false);

		JLabel label = new JLabel(Utils.getLabel(key));
		label.setPreferredSize(new Dimension(200, 26));
		label.setOpaque(false);
		
		JComboBox combo = new JComboBox();
		combo.setPreferredSize(new Dimension(200, 26));
		combo.setRenderer(new Utils.LabelListRenderer());

		for (Locale l : Utils.supportedLocales) {
			final JLabel themeItem = new JLabel(l.getDisplayLanguage(), Utils.getIcon(l.getLanguage() +".png"),SwingConstants.LEFT);
			themeItem.setToolTipText(l.getLanguage());
			combo.addItem(themeItem);
					if (value.equals(l.getLanguage())) {
						combo.setSelectedItem(themeItem);
					}
		}

		combo.addActionListener(this);
		combo.setActionCommand(key);
		
		locale.add(label);
		locale.add(Box.createHorizontalGlue());
		locale.add(combo);
		return locale;
	}

	private JPanel getThemeSettings(String key) {
		String value = Configuration.getString(key);
		
		JPanel theme = new JPanel();
		theme.setLayout(new BoxLayout(theme, BoxLayout.LINE_AXIS));
		theme.setBorder(BorderFactory.createEmptyBorder(2, 5, 5, 5));
		theme.setPreferredSize(new Dimension(500, 30));
		theme.setMaximumSize(new Dimension(1000,30));
		theme.setMinimumSize(new Dimension(200,30));
		theme.setOpaque(false);
		
		JLabel label = new JLabel(Utils.getLabel(key));
		label.setPreferredSize(new Dimension(200, 26));
		label.setOpaque(false);
		
		JComboBox combo = new JComboBox();
		combo.setPreferredSize(new Dimension(200, 26));
		combo.setRenderer(new Utils.LabelListRenderer());
		
		File themeList = new File("Theme");
		
		for (File themeDir : themeList.listFiles()) {
		    if (themeDir.isDirectory()) {
		    	File themeInfo = new File("Theme" + Utils.FILE_SEPARATOR + themeDir.getName() + Utils.FILE_SEPARATOR + "Theme.info");
		    	if (themeInfo.exists() && themeInfo.isFile()) {
		    		final JLabel themeItem = new JLabel(themeDir.getName());
		    		themeItem.setToolTipText(themeDir.getName());
					combo.addItem(themeItem);
					if (value.equals(themeDir.getName())) {
						combo.setSelectedItem(themeItem);
					}
		    	}
		    }
		}

		combo.addActionListener(this);
		combo.setActionCommand(key);
		
		theme.add(label);
		theme.add(Box.createHorizontalGlue());
		theme.add(combo);
		return theme;
	}
	
	private JPanel getBooleanSettings(String key) {
		String value = Configuration.getString(key);
		
		JPanel function = new JPanel();
		function.setLayout(new BoxLayout(function, BoxLayout.LINE_AXIS));
		function.setBorder(BorderFactory.createEmptyBorder(2, 5, 5, 5));
		function.setPreferredSize(new Dimension(500, 30));
		function.setMaximumSize(new Dimension(1000,30));
		function.setMinimumSize(new Dimension(200,30));
		function.setOpaque(false);
		
		JCheckBox chkbox = new JCheckBox(Utils.getLabel(key));
		chkbox.setPreferredSize(new Dimension(300, 26));
		chkbox.setOpaque(false);
		
		chkbox.setSelected(Boolean.valueOf(value));
		chkbox.addActionListener(this);
		chkbox.setActionCommand(key);
		
		function.add(chkbox);
		function.add(Box.createHorizontalGlue());
		
		return function;
	}
	
	private JPanel getNumberSettings(String key) {
		String value = Configuration.getString(key);
		
		JPanel theme = new JPanel();
		theme.setLayout(new BoxLayout(theme, BoxLayout.LINE_AXIS));
		theme.setBorder(BorderFactory.createEmptyBorder(2, 5, 5, 5));
		theme.setPreferredSize(new Dimension(500, 30));
		theme.setMaximumSize(new Dimension(1000,30));
		theme.setMinimumSize(new Dimension(200,30));
		theme.setOpaque(false);
		
		JLabel label = new JLabel(Utils.getLabel(key));
		label.setPreferredSize(new Dimension(200, 26));
		label.setOpaque(false);
		
		SpinnerModel intModel = new SpinnerNumberModel(20,5,50,1);
		JSpinner spinner = new JSpinner(intModel);
		spinner.setEditor(new JSpinner.NumberEditor(spinner, "#"));
	
		intModel.setValue(Integer.valueOf(value));
		//intModel.addChangeListener(l);
		
		theme.add(label);
		theme.add(Box.createHorizontalGlue());
		theme.add(spinner);
		return theme;
	}
}
