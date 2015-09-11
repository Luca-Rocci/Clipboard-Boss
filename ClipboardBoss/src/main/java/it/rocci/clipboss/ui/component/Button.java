package it.rocci.clipboss.ui.component;

import it.rocci.clipboss.model.Theme;

import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;

public class Button extends JButton implements MouseListener {

	public Button() {
		super();
		init();
	}
	
	
	public Button(ActionListener ai) {
		super();
		init();
		addActionListener(ai);
	}
	
	public Button(Action a) {
		super(a);
		init();
	}

	public Button(Icon icon) {
		super(icon);
		init();
	}

	public Button(String text, Icon icon) {
		super(text, icon);
		init();
	}

	public Button(String text) {
		super(text);
		init();
	}

	public void init() {
		setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		setRolloverEnabled(true);
		setContentAreaFilled(false);
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		setForeground(Theme.getColorText());
		setFont(Theme.getFontText());
		setBackground(Theme.getColorBackground());
		addMouseListener(this);
	}

	public void setIcon(String strIcon) {
		setIcon(Theme.getIcon(strIcon + ".png"));
		setRolloverIcon(Theme.getIcon(strIcon + "-h.png"));
	}

	@Override
	public void mouseClicked(MouseEvent e) { }

	@Override
	public void mousePressed(MouseEvent e) { }

	@Override
	public void mouseReleased(MouseEvent e) { }

	@Override
	public void mouseEntered(MouseEvent e) {
		setForeground(Theme.getColorTextHover());
	}

	@Override
	public void mouseExited(MouseEvent e) {
		setForeground(Theme.getColorText());
	}
}
