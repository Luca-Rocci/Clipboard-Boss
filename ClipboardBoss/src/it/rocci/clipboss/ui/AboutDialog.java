package it.rocci.clipboss.ui;

import it.rocci.clipboss.ui.component.Button;
import it.rocci.clipboss.ui.component.Dialog;
import it.rocci.clipboss.utils.Utils;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.html.HTMLEditorKit;

public class AboutDialog extends Dialog {

	public AboutDialog() {
		super();
		this.setTitle(Utils.getLabel("about.title"));
		this.setDescription(Utils.getLabel("about.description"));
		this.setIcon(Utils.getIcon("about.png"));

		String aText = "<center>"
                + "<h1>Clipboard Boss</h1>\n"
                + "<h2>Version: Beta 1.0  Developed by: Luca Rocci</h2>\n"
                + "<p>This software is free, you can redistribute it under the terms of the GNU General Public License</p>\n"
                + "<p>Copyright (C) 2015 Luca Rocci</p>"
                + "</center>";
        JEditorPane aboutText = new JEditorPane(new HTMLEditorKit().getContentType(), aText);
        aboutText.setContentType("text/html");
        aboutText.setEditable(false);
        aboutText.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        aboutText.setText(aText);
        aboutText.setBackground(Utils.getColorBackground());
		
		final Button visit = new Button();
		visit.setText(Utils.getLabel("about.visit"));
		visit.setIcon("www-24");
		visit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				final Desktop desktop = Desktop.isDesktopSupported() ? Desktop
						.getDesktop() : null;
				if ((desktop != null)
						&& desktop.isSupported(Desktop.Action.BROWSE)) {
					try {
						desktop.browse(new URI("http://luca-rocci.github.io/Clipboard-Boss/"));
						AboutDialog.this.dispose();
					} catch (final Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		final Button donate = new Button();
		donate.setText(Utils.getLabel("about.donate"));
		donate.setIcon("donate-24");
		donate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				final Desktop desktop = Desktop.isDesktopSupported() ? Desktop
						.getDesktop() : null;
				if ((desktop != null)
						&& desktop.isSupported(Desktop.Action.BROWSE)) {
					try {
						desktop.browse(new URI("https://www.paypal.com/cgi-bin/webscr?cmd=_donations&business=AUNW694K5GQ8L&lc=IT&item_name=Clipboard%20Boss&item_number=supporter&currency_code=EUR&bn=PP%2dDonationsBF%3abtn_donate_SM%2egif%3aNonHosted"));
						AboutDialog.this.dispose();
					} catch (final Exception e) {
						e.printStackTrace();
					}
				}
			}
		});

		this.footer.add(visit, BorderLayout.LINE_START);
		this.footer.add(donate, BorderLayout.CENTER);

		this.add(aboutText, BorderLayout.CENTER);

	}

}