package it.rocci.clipboss.ui.component;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class ImagePanel extends JPanel {

	BufferedImage image;

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public ImagePanel(BufferedImage img) {
		this.image = img;
	}
	
	public ImagePanel() {
		this.image = null;
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (image != null) {
          Graphics2D g2d = (Graphics2D) g.create();
          g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
          g2d.drawImage(image, 0, 0, this);
          g2d.dispose();
      }
	}
	
}
