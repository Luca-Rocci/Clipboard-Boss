package it.rocci.clipboss.ui;

import it.rocci.clipboss.model.Theme;
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
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

public class AboutDialog extends Dialog {

	private Button btnVisit;
	private Button btnDonate;

	public AboutDialog() {
		super();

		String aText = "<center>"
                + "<h1>Clipboard Boss</h1>\n"
                + "<h2>Version: Beta 1.0  Developed by: Luca Rocci</h2>\n"
                + "<p>This software is free, you can redistribute it under the terms of the GNU General Public License</p>\n"
                + "<p>Copyright (C) 2015 Luca Rocci</p>"
                + "</center>";
		 String aCss =  "body {font-family: \"Nimbus Sans L\", \"URW Gothic L\", Verdana, Tahoma, Helvetica, \"Lucida Grande\" Geneva, \"DejaVu Sans\", \"Microsoft Sans Serif\", sans-serif; "
	                + "font-size: 12pt; }";
        JEditorPane aboutText = new JEditorPane(new HTMLEditorKit().getContentType(), aText);
        aboutText.setContentType("text/html");
         ((HTMLDocument) aboutText.getDocument()).getStyleSheet().addRule(aCss);

        aboutText.setEditable(false);
        aboutText.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        aboutText.setText(aText);
        aboutText.setBackground(Theme.getColorBackground());
		
		btnVisit = new Button(this);	
		btnDonate = new Button(this);

		this.footer.add(btnVisit, BorderLayout.LINE_START);
		this.footer.add(btnDonate, BorderLayout.CENTER);

		this.add(aboutText, BorderLayout.CENTER);

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		if (e.getSource() == btnDonate) {
			final Desktop desktop = Desktop.isDesktopSupported() ? Desktop
					.getDesktop() : null;
			if ((desktop != null)
					&& desktop.isSupported(Desktop.Action.BROWSE)) {
				try {
					desktop.browse(new URI("https://www.paypal.com/cgi-bin/webscr?cmd=_donations&business=AUNW694K5GQ8L&lc=IT&item_name=Clipboard%20Boss&item_number=supporter&currency_code=EUR&bn=PP%2dDonationsBF%3abtn_donate_SM%2egif%3aNonHosted"));
					AboutDialog.this.dispose();
				} catch (final Exception ex) {
					ex.printStackTrace();
				}
			}
		} else if (e.getSource() == btnVisit) {
			final Desktop desktop = Desktop.isDesktopSupported() ? Desktop
					.getDesktop() : null;
			if ((desktop != null)
					&& desktop.isSupported(Desktop.Action.BROWSE)) {
				try {
					desktop.browse(new URI("http://luca-rocci.github.io/Clipboard-Boss/"));
					AboutDialog.this.dispose();
				} catch (final Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}
	@Override
	public void updateUI() {
		super.updateUI();
		this.setTitle(Utils.getLabel("about.title"));
		this.setDescription(Utils.getLabel("about.description"));
		this.setIcon(Utils.getIcon("about.png"));
		
		btnVisit.setText(Utils.getLabel("about.visit"));
		btnVisit.setIcon("www-24");
		
		btnDonate.setText(Utils.getLabel("about.donate"));
		btnDonate.setIcon("donate-24");
	}

}