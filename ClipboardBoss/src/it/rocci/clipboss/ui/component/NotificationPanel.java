package it.rocci.clipboss.ui.component;

import it.rocci.clipboss.model.Theme;
import it.rocci.clipboss.ui.MainWindow;
import it.rocci.clipboss.utils.Utils;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

import com.sun.awt.AWTUtilities;

public class NotificationPanel extends JDialog implements ActionListener, MouseListener {

	private static final long serialVersionUID = 1111111100000000112L;
	public final Header header;
	public final Timer timerIn = new Timer(50, this);
	public final Timer timerOut = new Timer(50, this);
	public final Timer timerShow = new Timer(2500, this);

	private Button btnMinimize;
	private Button btnMassimize;
	private JLabel lblMessage;
	
	private MainWindow main;
	
	public NotificationPanel(final MainWindow main) {
		super();
		
		this.main = main;
		
		this.btnMinimize = new Button(this);	
		this.btnMassimize = new Button(this);
		this.lblMessage = new JLabel();
		
		this.header = new Header(Utils.getLabel("title"), "", null);
		this.header.setDimension(new Dimension(100, 32));
		this.header.setLayout(new FlowLayout(FlowLayout.RIGHT));
		this.header.add(btnMassimize);
		this.header.add(btnMinimize);
		
		this.setLayout(new BorderLayout());

		this.add(this.header, BorderLayout.PAGE_START);
		this.add(this.lblMessage, BorderLayout.CENTER);
		
		setUndecorated(true);
		AWTUtilities.setWindowOpaque(this, false);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		pack();
		setSize(new Dimension(340, 170));
		setPreferredSize(new Dimension(340, 170));
		setAlwaysOnTop(true);

		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		Insets toolHeight = Toolkit.getDefaultToolkit().getScreenInsets(this.getGraphicsConfiguration());
		setLocation(scrSize.width - getWidth(), scrSize.height - toolHeight.bottom - getHeight());

		timerIn.setRepeats(true);
		timerOut.setRepeats(true);
		timerShow.setRepeats(true);
		addMouseListener(this);
	}

	public void updateUI() {
		this.setTitle(Utils.getLabel("title"));
		this.header.setTitle(Utils.getLabel("title"));
		this.header.setColorStart(Theme.getColorStart());
		this.header.setColorEnd(Theme.getColorEnd());
		this.btnMinimize.setToolTipText(Utils.getLabel("minimize"));
		this.btnMinimize.setIcon("down-24");
		this.btnMassimize.setToolTipText(Utils.getLabel("massimize"));
		this.btnMassimize.setIcon("up-24");
		this.setForeground(Theme.getColorText());
		this.setFont(Theme.getFontText());
		this.setBackground(Theme.getColorBackground());
		this.getRootPane().setBorder(BorderFactory.createLineBorder(Theme.getColorStart().darker(),1));
		pack();
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

	public void showMessage(final String message) {
		this.lblMessage.setText(message);
		fadeIn();
		timerShow.start();
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
			if (opacity >= 1)
				timerIn.stop();
		} else if (e.getSource() == timerOut) {
			opacity = getOpacity();
			opacity -= 0.05f;
			setOpacity(Math.max(opacity, 0));
			if (opacity <= 0) {
				timerOut.stop();
				timerShow.stop();
				setVisible(false);
			}
		} else if (e.getSource() == timerShow) {
			fadeOut();
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