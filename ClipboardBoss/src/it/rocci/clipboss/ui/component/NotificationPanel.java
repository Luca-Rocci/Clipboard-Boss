package it.rocci.clipboss.ui.component;

import it.rocci.clipboss.model.Theme;
import it.rocci.clipboss.utils.Utils;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class NotificationPanel extends JDialog {

	private static final long serialVersionUID = 1111111100000000112L;
	public final Header header;	
	
	public NotificationPanel() {
		super();
		this.header = new Header(Utils.getLabel("title"), "",null);
		this.header.setDimension(new Dimension(100, 32));
		this.setLayout(new BorderLayout());

		this.add(this.header, BorderLayout.PAGE_START);
		
	        setUndecorated( true );
	        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	        pack();
	        setSize(new Dimension(240, 70));
	        setAlwaysOnTop(true);

	}
		
	public void updateUI() {
		
		this.header.setTitle(Utils.getLabel("title"));
		this.header.setColorStart(Theme.getColorStart());
		this.header.setColorEnd(Theme.getColorEnd());
		this.setForeground(Theme.getColorText());
		this.setFont(Theme.getFontText());
		this.setBackground(Theme.getColorBackground());
		this.getRootPane().setBorder(BorderFactory.createLineBorder(Theme.getColorStart().darker(), 1));
	}

	public void setVisible(MouseEvent e) {
		Point pos;
		if (e == null) {
			pos = new Point(20,20);
		} else {
			pos = e.getLocationOnScreen();
		}

        Rectangle screen = getScreenBoundsAt(pos);

        if (pos.x + getWidth() > screen.x + screen.width) {
            pos.x = screen.x + screen.width - getWidth();
        }
        if (pos.x < screen.x) {
            pos.x = screen.x;
        }

        if (pos.y + getHeight() > screen.y + screen.height) {
            pos.y = screen.y + screen.height - getHeight();
        }
        if (pos.y < screen.y) {
            pos.y = screen.y;
        }

        setLocation(pos);
        setVisible(true);
	}
	
	@Override
	public void setVisible(boolean b) {
        updateUI();
		super.setVisible(b);
	}
	
	 public static Rectangle getScreenBoundsAt(Point pos) {
	        GraphicsDevice gd = getGraphicsDeviceAt(pos);
	        Rectangle bounds = null;

	        if (gd != null) {
	            bounds = gd.getDefaultConfiguration().getBounds();
	            Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(gd.getDefaultConfiguration());

	            bounds.x += insets.left;
	            bounds.y += insets.top;
	            bounds.width -= (insets.left + insets.right);
	            bounds.height -= (insets.top + insets.bottom);
	        }
	        return bounds;
	    }

	    public static GraphicsDevice getGraphicsDeviceAt(Point pos) {
	        GraphicsDevice device = null;

	        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	        GraphicsDevice lstGDs[] = ge.getScreenDevices();

	        ArrayList<GraphicsDevice> lstDevices = new ArrayList<GraphicsDevice>(lstGDs.length);

	        for (GraphicsDevice gd : lstGDs) {
	            GraphicsConfiguration gc = gd.getDefaultConfiguration();
	            Rectangle screenBounds = gc.getBounds();

	            if (screenBounds.contains(pos)) {
	                lstDevices.add(gd);
	            }
	        }

	        if (lstDevices.size() == 1) {
	            device = lstDevices.get(0);
	        }
	        return device;
	    }
	    
	    public static void showMessage(String message, int time) {
	    	final NotificationPanel np = new NotificationPanel();
	    	np.setLocationRelativeTo(null);
	    	JLabel l = new JLabel(message);
	    	l.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
	    	l.setAlignmentX(CENTER_ALIGNMENT);
	    	l.setAlignmentY(CENTER_ALIGNMENT);
	    	l.setBackground(Theme.getColorBackground());
	    	l.setOpaque(true);
	    	np.add(l, BorderLayout.CENTER);
	    	np.setVisible(true);
	    	try {
				Thread.sleep(time);
			} catch (InterruptedException e) { }
	    	np.setVisible(false);
	    	np.dispose();
	    }

}