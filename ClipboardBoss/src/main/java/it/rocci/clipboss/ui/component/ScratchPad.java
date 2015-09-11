package it.rocci.clipboss.ui.component;

import it.rocci.clipboss.model.Theme;
import it.rocci.clipboss.ui.MainWindow;
import it.rocci.clipboss.utils.Utils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

import com.sun.awt.AWTUtilities;

public class ScratchPad extends JDialog implements ActionListener, MouseListener {

	private static final long serialVersionUID = 1111111100000000112L;

	public final Timer timerIn = new Timer(70, this);
	public final Timer timerOut = new Timer(50, this);
	public final Timer timerShow = new Timer(7000, this);
	public final Timer timerUp = new Timer(10, this);

	private Button btnMinimize;
	private Button btnMassimize;
	private JLabel lblMessage;
	private JLabel lblTitle;
	
	private int yEnd;
	private int xPos;
	private int yPos;
	
	private MainWindow main;
	
	public ScratchPad(final MainWindow main) {
		super();
		
		ImagePanel ip = new ImagePanel(Utils.convertImage(Theme.getIcon("scratchpad-background.png").getImage()));
		
		ip.setBorder(BorderFactory.createEmptyBorder(30,10,10,10));
		setContentPane(ip);
		
		
		this.main = main;
		
		this.btnMinimize = new Button(this);	
		this.btnMassimize = new Button(this);
		this.lblMessage = new JLabel();
		this.lblTitle = new JLabel();
		
		this.setLayout(new BorderLayout());

		this.add(this.lblTitle, BorderLayout.PAGE_START);
		this.add(this.lblMessage, BorderLayout.CENTER);
		setFocusable(false);
		//setAutoRequestFocus(false);
		setFocusableWindowState(false);
		setUndecorated(true);
		setBackground(new Color(20, 0, 20, 20));
		AWTUtilities.setWindowOpaque(this, false);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		pack();
		setSize(new Dimension(380, 180));
		setPreferredSize(new Dimension(380, 180));
		setAlwaysOnTop(true);

		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		yEnd = scrSize.height - getHeight();
		xPos = scrSize.width - getWidth() - 10;
		yPos = scrSize.height;
		setLocation(xPos,yPos);

		timerIn.setRepeats(true);
		timerOut.setRepeats(true);
		timerShow.setRepeats(true);
		timerUp.setRepeats(true);
		
		addMouseListener(this);
	}

	public void updateUI() {
		this.setTitle(Utils.getLabel("title"));
		this.lblTitle.setText(Utils.getLabel("title"));
		this.lblTitle.setFont(Theme.getFontTitle());
		this.btnMinimize.setToolTipText(Utils.getLabel("minimize"));
		this.btnMinimize.setIcon("down-24");
		this.btnMassimize.setToolTipText(Utils.getLabel("massimize"));
		this.btnMassimize.setIcon("up-24");
		this.setForeground(Theme.getColorText());
		this.setFont(Theme.getFontText());
		//this.setBackground(Theme.getColorBackground());
		//this.getRootPane().setBorder(BorderFactory.createLineBorder(Theme.getColorStart().darker(),1));
//		this.background = Theme.getIcon("scratchpad-background.png").getImage();
		this.pack();
	}

	@Override
	public void setVisible(boolean b) {
		updateUI();
		super.setVisible(b);
	}

	private void fadeIn() {
		setOpacity(0);
		timerIn.start();
		setVisible(true);
	}

	private void fadeOut() {
		setOpacity(1);
		timerOut.start();
	}
	
	private void showUp() {
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		yEnd = scrSize.height - getHeight();
		xPos = scrSize.width - getWidth();
		yPos = scrSize.height;
		setLocation(xPos,yPos);
		timerUp.start();
	}

	public void showTextMessage(final String message) {
		this.lblMessage.setIcon(null);
		this.lblMessage.setText(message);
		fadeIn();
		showUp();
	}
	
	public void showImageMessage(final Image img) {
		this.lblMessage.setText("");
		this.lblMessage.setIcon(new ImageIcon(img));
		fadeIn();
		showUp();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		float opacity;

		if (e.getSource() == btnMinimize) {
			timerShow.stop();
			fadeOut();
		} else if (e.getSource() == btnMassimize) {
			timerShow.stop();
			fadeOut();
	    	main.setVisible(true);
		} else if (e.getSource() == timerIn) {
			opacity = getOpacity();
			opacity += 0.05f;
			setOpacity(Math.min(opacity, 1));
			if (opacity >= 1) {
				timerIn.stop();
			}
		} else if (e.getSource() == timerOut) {
			opacity = getOpacity();
			opacity -= 0.05f;
			setOpacity(Math.max(opacity, 0));
			if (opacity <= 0) {
				timerOut.stop();
				setVisible(false);
			}
		} else if (e.getSource() == timerShow) {
			fadeOut();
			timerShow.stop();
		} else if (e.getSource() == timerUp) {
			if(yPos > yEnd) {
				yPos -= 2;
				setLocation(xPos,yPos);
			} else if(yPos == yEnd) {
				timerShow.start();
				timerUp.stop();
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent paramMouseEvent) {
		timerShow.stop();
	}

	@Override
	public void mouseExited(MouseEvent paramMouseEvent) {
		timerShow.start();
	}

	@Override
	public void mouseClicked(MouseEvent paramMouseEvent) { }

	@Override
	public void mousePressed(MouseEvent paramMouseEvent) { }

	@Override
	public void mouseReleased(MouseEvent paramMouseEvent) { }

}