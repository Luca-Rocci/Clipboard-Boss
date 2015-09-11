package it.rocci.clipboss.ui.component;

import it.rocci.clipboss.model.Theme;
import it.rocci.clipboss.utils.Utils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatusBar extends JPanel {
private JLabel message;
	  public StatusBar() {
	    setLayout(new BorderLayout());
	    setPreferredSize(new Dimension(10, 23));
	    setBackground(new Color(0x86, 0xab, 0xd9));
	    message = new JLabel();
	    add(message);
	  }

	  @Override
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    Color GRAY_LINE_COLOR = getBackground().darker();
        Color WHITE_LINE_COLOR = getBackground().brighter();   
	    String strText = "Clipboard Boss - Rocci Luca 2015";

      g.setFont(Theme.getFontText());
      
      FontMetrics fontMetrics = g.getFontMetrics(Theme.getFontText());
    int width = fontMetrics.stringWidth(strText);
    
    g.setColor(WHITE_LINE_COLOR);
    g.drawLine(0, 1, getWidth(), 1);
    
    g.drawLine( getWidth() -12, getHeight(), getWidth(), getHeight()-12);
    g.drawLine(getWidth() -7, getHeight(), getWidth(), getHeight() -7);
    g.drawLine(getWidth() -2, getHeight(), getWidth(), getHeight()-2);
    
    g.drawString(strText, getWidth()-(width+7), getHeight()-5);

    g.setColor(GRAY_LINE_COLOR);
    g.drawLine(0, 0, getWidth(), 0);
    
    g.drawLine(getWidth() -11, getHeight(), getWidth(), getHeight()-11);
    g.drawLine(getWidth() -10, getHeight(), getWidth(), getHeight()-10);
    g.drawLine(getWidth() -9, getHeight(), getWidth(), getHeight()-9);

    g.drawLine(getWidth() -6, getHeight(), getWidth(), getHeight()-6);
    g.drawLine(getWidth() -5, getHeight(), getWidth(), getHeight()-5);
    g.drawLine(getWidth() -4, getHeight(), getWidth(), getHeight()-4);

    g.drawLine(getWidth() -1, getHeight(), getWidth(), getHeight()-1);
    g.drawLine(getWidth() , getHeight(), getWidth(), getHeight());
    
    g.drawString(strText, getWidth()-(width+8), getHeight()-6);
	  }
	  
public void setMessage(String mess) {
	message.setText(mess);
}

public void setMessage(String mess, Icon icon) {
	message.setText(mess);
	message.setIcon(icon);
}
	}