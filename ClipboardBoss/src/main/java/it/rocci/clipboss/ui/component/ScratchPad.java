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

	public final Timer timerShow = new Timer(6000, this);
	public final Timer timerUp = new Timer(10, this);
	public final Timer timerDown = new Timer(10, this);

	private Button btnMinimize;
	private Button btnMassimize;
	private JLabel lblMessage;
	private JLabel lblTitle;
	
	private int xPos;
	private int yPos;
	private int yEnd;
	
	private MainWindow main;

	private ImagePanel ipBackground;

	
	public ScratchPad(final MainWindow main) {
		super();
		
		this.main = main;
		
		ipBackground = new ImagePanel();
		ipBackground.setBorder(BorderFactory.createEmptyBorder(30,10,10,10));
		this.setContentPane(ipBackground);
		
		this.btnMinimize = new Button(this);	
		this.btnMassimize = new Button(this);
		this.lblMessage = new JLabel();
		this.lblTitle = new JLabel();
		
		this.setLayout(new BorderLayout());

		this.add(this.lblTitle, BorderLayout.PAGE_START);
		this.add(this.lblMessage, BorderLayout.CENTER);
		setFocusable(false);
		setFocusableWindowState(false);
		setUndecorated(true);
		setBackground(new Color(0, 0, 0, 0));
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		pack();
		setSize(new Dimension(380, 180));
		setPreferredSize(new Dimension(380, 180));
		setAlwaysOnTop(true);

		timerShow.setRepeats(true);
		
		addMouseListener(this);
	}

	public void updateUI() {
		this.ipBackground.setImage(Utils.convertImage(Theme.getIcon("scratchpad-background.png").getImage()));
		this.setTitle(Utils.getLabel("title"));
		this.lblTitle.setText(Utils.getLabel("title"));
		this.lblTitle.setFont(Theme.getFontTitle());
		this.btnMinimize.setToolTipText(Utils.getLabel("minimize"));
		this.btnMinimize.setIcon("down-24");
		this.btnMassimize.setToolTipText(Utils.getLabel("massimize"));
		this.btnMassimize.setIcon("up-24");
		this.setForeground(Theme.getColorText());
		this.setFont(Theme.getFontText());
		this.pack();
	}

	@Override
	public void setVisible(boolean b) {
		updateUI();
		super.setVisible(b);
	}
	
	private void showUp() {
		timerUp.start();
		timerShow.stop();
		timerDown.stop();
		
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		xPos = scrSize.width - getWidth();
		yPos = scrSize.height;
		yEnd = scrSize.height - getHeight();
		setLocation(xPos,yPos);
		setVisible(true);
	}

	private void showDown() {
		timerUp.stop();
		timerShow.stop();
		timerDown.start();
		
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		xPos = scrSize.width - getWidth();
		yPos = scrSize.height - getHeight();
		yEnd = scrSize.height;
	}
	
	public void showTextMessage(final String message) {
		this.lblMessage.setIcon(null);
		this.lblMessage.setText(message);
		showUp();
	}
	
	public void showImageMessage(final Image img) {
		this.lblMessage.setText("");
		this.lblMessage.setIcon(new ImageIcon(img));
		showUp();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnMinimize) {
			showDown();
		} else if (e.getSource() == btnMassimize) {
			showDown();
	    	main.setVisible(true);
		} else if (e.getSource() == timerShow) {
			showDown();
		} else if (e.getSource() == timerUp) {
			if(yPos > yEnd) {
				yPos -= 2;
				setLocation(xPos,yPos);
			} else if(yPos == yEnd) {
				timerShow.start();
				timerUp.stop();
			}
		}  else if (e.getSource() == timerDown) {
			if(yPos <= yEnd) {
				yPos += 2;
				setLocation(xPos,yPos);
			} else if(yPos == yEnd) {
				timerDown.stop();
				setVisible(false);
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