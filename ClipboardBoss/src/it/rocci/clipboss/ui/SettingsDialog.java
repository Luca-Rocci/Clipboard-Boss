package it.rocci.clipboss.ui;

import it.rocci.clipboss.MainApp;
import it.rocci.clipboss.model.Theme;
import it.rocci.clipboss.ui.component.Dialog;
import it.rocci.clipboss.utils.Configuration;
import it.rocci.clipboss.utils.Utils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.Locale;
import java.util.logging.Level;

import javax.sound.sampled.ReverbType;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SettingsDialog extends Dialog {

	private JPanel panelUI;
	private JPanel panelFunc;
	private JLabel lblLocale;
	private JLabel lblTheme;
	private JLabel lblPolling;
	private JLabel lblMaxItem;
	private JCheckBox chkAutorun;
	
	private String strWindowsLink = "\\AppData\\Roaming\\Microsoft\\Windows\\Start Menu\\Programs\\Clipboard Boss\\Clipboard Boss.lnk";
	private String strWindowsStartup = "\\AppData\\Roaming\\Microsoft\\Windows\\Start Menu\\Programs\\Startup\\Clipboard Boss.lnk";
	private String strLinuxLink = "/.local/share/applications/";
	private String strLinuxStartup = "/.config/share/autostart/ClipboardBoss.desktop";
			
	public SettingsDialog() {
		super();

		final JPanel panelCenter = new JPanel();
		panelCenter.setBackground(Theme.getColorBackground());
		panelCenter.setLayout(new BoxLayout(panelCenter, BoxLayout.PAGE_AXIS));
		panelCenter.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		TitledBorder title = BorderFactory.createTitledBorder(Utils.getLabel("settings.ui"));
		panelUI = new JPanel();
		panelUI.setBorder(title);
		panelUI.setOpaque(false);
		panelUI.setLayout(new BoxLayout(panelUI, BoxLayout.PAGE_AXIS));

		panelUI.add(getLocaleSettings());
		panelUI.add(getThemeSettings());

		title = BorderFactory.createTitledBorder(Utils.getLabel("settings.function"));
		panelFunc = new JPanel();
		panelFunc.setOpaque(false);
		panelFunc.setBorder(title);
		panelFunc.setLayout(new BoxLayout(panelFunc, BoxLayout.PAGE_AXIS));

		lblPolling = new JLabel();
		lblMaxItem = new JLabel();
		chkAutorun = new JCheckBox();

		panelFunc.add(getNumberSettings(lblMaxItem,"settings.function.maxitem", 10, 50, 1));
		panelFunc.add(getNumberSettings(lblPolling,"settings.function.polling", 200, 10000, 50));
		panelFunc.add(getBooleanSettings(chkAutorun,"settings.function.autorun"));
		panelCenter.add(panelUI);
		panelCenter.add(panelFunc);
		panelCenter.add(Box.createVerticalGlue());

		File startMenuFile = null;
		if (Utils.isWindows()) {
			startMenuFile = new File(Utils.UserHome	+ strWindowsLink);
			chkAutorun.setEnabled(startMenuFile.exists());
		} else if (Utils.isLinux()) {
			startMenuFile = new File(Utils.UserHome	+ strLinuxLink);
			for (File f : startMenuFile.listFiles()) {
				if (f.getName().startsWith("ClipboardBoss") && f.getName().endsWith(".desktop")) {
					chkAutorun.setEnabled(true);
				}
			}
		}

		this.add(panelCenter, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		if (e.getActionCommand().contains("settings.ui.")) {
			final JComboBox cb = (JComboBox) e.getSource();
			final JLabel lbl = (JLabel) cb.getSelectedItem();
			Configuration.setString(e.getActionCommand(), lbl.getToolTipText());
			Theme.cleanThemeInfo();
			Utils.cleanLangBundle();
			invalidate();
			updateUI();

		}
	}

	private JPanel getLocaleSettings() {
		String key = "settings.ui.locale";
		String value = Configuration.getString(key);

		JPanel locale = new JPanel();
		locale.setLayout(new BoxLayout(locale, BoxLayout.LINE_AXIS));
		locale.setBorder(BorderFactory.createEmptyBorder(2, 5, 5, 5));
		locale.setPreferredSize(new Dimension(500, 30));
		locale.setMaximumSize(new Dimension(1000, 30));
		locale.setMinimumSize(new Dimension(200, 30));
		locale.setOpaque(false);

		lblLocale = new JLabel(Utils.getLabel(key));
		lblLocale.setPreferredSize(new Dimension(200, 26));
		lblLocale.setOpaque(false);

		JComboBox combo = new JComboBox();
		combo.setPreferredSize(new Dimension(70, 26));
		combo.setRenderer(new LabelListRenderer());

		for (Locale l : Utils.supportedLocales) {
			final JLabel themeItem = new JLabel(l.getDisplayLanguage(),
					Utils.getIcon(l.getLanguage() + ".png"),
					SwingConstants.LEFT);
			themeItem.setToolTipText(l.getLanguage());
			combo.addItem(themeItem);
			if (value.equals(l.getLanguage())) {
				combo.setSelectedItem(themeItem);
			}
		}

		combo.addActionListener(this);
		combo.setActionCommand(key);

		locale.add(lblLocale);
		locale.add(Box.createHorizontalGlue());
		locale.add(combo);
		return locale;
	}

	private JPanel getThemeSettings() {
		String key = "settings.ui.theme";
		String value = Configuration.getString(key);

		JPanel theme = new JPanel();
		theme.setLayout(new BoxLayout(theme, BoxLayout.LINE_AXIS));
		theme.setBorder(BorderFactory.createEmptyBorder(2, 5, 5, 5));
		theme.setPreferredSize(new Dimension(500, 30));
		theme.setMaximumSize(new Dimension(1000, 30));
		theme.setMinimumSize(new Dimension(200, 30));
		theme.setOpaque(false);

		lblTheme = new JLabel(Utils.getLabel(key));
		lblTheme.setPreferredSize(new Dimension(200, 26));
		lblTheme.setOpaque(false);

		JComboBox combo = new JComboBox();
		combo.setPreferredSize(new Dimension(70, 26));
		combo.setRenderer(new LabelListRenderer());

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

		theme.add(lblTheme);
		theme.add(Box.createHorizontalGlue());
		theme.add(combo);
		return theme;
	}

	private JPanel getBooleanSettings(JCheckBox chkbox, final String key) {

		String value = Configuration.getString(key);

		JPanel function = new JPanel();
		function.setLayout(new BoxLayout(function, BoxLayout.LINE_AXIS));
		function.setBorder(BorderFactory.createEmptyBorder(2, 5, 5, 5));
		function.setPreferredSize(new Dimension(500, 30));
		function.setMaximumSize(new Dimension(1000, 30));
		function.setMinimumSize(new Dimension(200, 30));
		function.setOpaque(false);

		chkbox.setText(Utils.getLabel(key));
		chkbox.setPreferredSize(new Dimension(300, 26));
		chkbox.setOpaque(false);

		chkbox.setSelected(Boolean.valueOf(value));
		chkbox.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				final JCheckBox chk = (JCheckBox) e.getSource();
				Configuration.setString(key, String.valueOf(chk.isSelected()));
				File startMenuFile = null;
				File startupFile = null;
				if (Utils.isWindows()) {
					startMenuFile = new File(Utils.UserHome + strWindowsLink);
					startupFile = new File(Utils.UserHome + strWindowsStartup);
				} else if (Utils.isLinux()) {
					startMenuFile = new File(Utils.UserHome + strLinuxLink);		
					for (File f : startMenuFile.listFiles()) {
						if (f.getName().startsWith("ClipboardBoss") && f.getName().endsWith(".desktop")) {
							startMenuFile = f;
							break;
						}
					}
					
					startupFile = new File(Utils.UserHome + strLinuxStartup);
				}

				if (chk.isSelected()) {
					if (startMenuFile.exists()) {
						Utils.copyFile(startMenuFile,startupFile);
					}
				} else {
					if (startupFile.exists()) {
						startupFile.delete();
					}
				}
			}
		});

		function.add(chkbox);
		function.add(Box.createHorizontalGlue());

		return function;
	}

	private JPanel getNumberSettings(JLabel label, final String key, int min,
			int max, int step) {
		String value = Configuration.getString(key);

		JPanel theme = new JPanel();
		theme.setLayout(new BoxLayout(theme, BoxLayout.LINE_AXIS));
		theme.setBorder(BorderFactory.createEmptyBorder(2, 5, 5, 5));
		theme.setPreferredSize(new Dimension(500, 30));
		theme.setMaximumSize(new Dimension(1000, 30));
		theme.setMinimumSize(new Dimension(200, 30));
		theme.setOpaque(false);

		label.setText(Utils.getLabel(key));
		label.setPreferredSize(new Dimension(200, 26));
		label.setOpaque(false);
		SpinnerModel intModel = new SpinnerNumberModel(min, min, max, step);
		JSpinner spinner = new JSpinner(intModel);
		spinner.setEditor(new JSpinner.NumberEditor(spinner, "#"));
		spinner.setPreferredSize(new Dimension(70, 26));
		intModel.setValue(Integer.valueOf(value));
		intModel.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				Configuration.setString(key, String.valueOf(((SpinnerModel) e
						.getSource()).getValue()));

			}
		});

		theme.add(label);
		theme.add(Box.createHorizontalGlue());
		theme.add(spinner);
		return theme;
	}

	@Override
	public void updateUI() {
		super.updateUI();
		this.setTitle(Utils.getLabel("settings.title"));
		this.setDescription(Utils.getLabel("settings.description"));
		this.setIcon(Utils.getIcon("settings.png"));

		TitledBorder title = BorderFactory.createTitledBorder(Utils
				.getLabel("settings.ui"));
		panelUI.setBorder(title);

		title = BorderFactory.createTitledBorder(Utils
				.getLabel("settings.function"));
		panelFunc.setBorder(title);

		lblLocale.setText(Utils.getLabel("settings.ui.locale"));
		lblTheme.setText(Utils.getLabel("settings.ui.theme"));
		lblPolling.setText(Utils.getLabel("settings.function.polling"));
		lblMaxItem.setText(Utils.getLabel("settings.function.maxitem"));
		chkAutorun.setText(Utils.getLabel("settings.function.autorun"));

	}

	public static class LabelListRenderer extends DefaultListCellRenderer {

		@Override
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			final JLabel label = (JLabel) super.getListCellRendererComponent(
					list, value, index, isSelected, cellHasFocus);

			if (value != null) {
				label.setText(((JLabel) value).getText());
				label.setIcon(((JLabel) value).getIcon());
			}
			if (isSelected) {
				label.setForeground(Theme.getColorTextHover());
			}
			label.setOpaque(true);
			this.setOpaque(true);
			return label;
		}

	}
}
