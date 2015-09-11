package it.rocci.clipboss.ui.component;

import it.rocci.clipboss.model.Theme;
import it.rocci.clipboss.utils.Utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.InputStream;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.UIDefaults;

public class Header extends JComponent {

	private Color colorStart;
	private Color colorEnd;
	private ImageIcon imageIcon;
	private String strTitle;
	private String strDescription;

	public Header() {
		super();
		this.colorStart = new Color(140, 200, 255);
		this.colorEnd = Color.WHITE;
		this.setPreferredSize(new Dimension(100, 40));
		this.setSize(new Dimension(100, 40));
		setMinimumSize(new Dimension(100, 40));
		setMaximumSize(new Dimension(2048, 40));
	}

	public Header(String title, String description, ImageIcon image) {
		super();
		this.strTitle = title;
		this.strDescription = description;
		this.imageIcon = image;
		this.colorStart = new Color(140, 200, 255);
		this.colorEnd = Color.WHITE;
		this.setPreferredSize(new Dimension(100, 40));
		this.setSize(new Dimension(100, 40));
		this.setMinimumSize(new Dimension(100, 40));
		this.setMaximumSize(new Dimension(2048, 40));
	}

	public Color getColorEnd() {
		return this.colorEnd;
	}

	public Color getColorStart() {
		return this.colorStart;
	}

	public String getDescription() {
		return this.strDescription;
	}

	public ImageIcon getIcon() {
		return this.imageIcon;
	}

	public String getTitle() {
		return this.strTitle;
	}

	@Override
	public void paint(Graphics g) {
		final int width = this.getWidth();
		final int height = this.getHeight();

		final RenderingHints rha = new RenderingHints(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		final RenderingHints rht = new RenderingHints(
				RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		final GradientPaint paintBackground = new GradientPaint(0, 0,
				this.colorStart, width, height, this.colorEnd, true);

		final GradientPaint paintLine = new GradientPaint(0, 0,
				this.colorStart.darker(), width, height, this.colorEnd, true);

		final Graphics2D g2d = (Graphics2D) g;
		g2d.addRenderingHints(rha);
		g2d.addRenderingHints(rht);

		final Paint oldPaint = g2d.getPaint();

		g2d.setPaint(paintBackground);

		g2d.fillRect(0, 0, width, height);

		g2d.setPaint(paintLine);

		g2d.fillRect(0, height - 1, width, 1);

		g2d.setPaint(oldPaint);

		g2d.setFont(Theme.getFontTitle());
		g2d.drawString(this.strTitle, 5, 22);
		g2d.setFont(Theme.getFontText());
		g2d.drawString(this.strDescription, 6, 35);

		if (this.imageIcon != null) {
		g2d.drawImage(this.imageIcon.getImage(),
				width - this.imageIcon.getIconWidth() - 5, height
				- this.imageIcon.getIconHeight() - 5, null);
		}
		super.paint(g2d);
	}

	public void setColorEnd(Color color) {
		this.colorEnd = color;
	}

	public void setColorStart(Color color) {
		this.colorStart = color;
	}

	public void setDescription(String description) {
		this.strDescription = description;
	}

	public void setIcon(ImageIcon image) {
		this.imageIcon = image;
	}

	public void setTitle(String title) {
		this.strTitle = title;
	}

	public void setDimension(Dimension d) {
		this.setPreferredSize(d);
		this.setSize(d);
		this.setMinimumSize(d);
		this.setMaximumSize(new Dimension(2048, (int) d.getHeight()));
	}

}
