package it.rocci.clipboss.ui.component;

import it.rocci.clipboss.utils.Utils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Method;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class NotificationPanel extends JFrame {

	private static final long serialVersionUID = 1111111100000000112L;
	public final Header header;	
	
	public NotificationPanel() {
		super();
		this.header = new Header(Utils.getLabel("title"), "",null);
		this.header.setDimension(new Dimension(100, 32));
		this.setLayout(new BorderLayout());

		this.setForeground(Utils.getColorText());
		this.setFont(Utils.getFontText());

		this.header.setColorStart(Utils.getColorStart());
		this.header.setColorEnd(Utils.getColorEnd());
		this.add(this.header, BorderLayout.PAGE_START);
		
		getRootPane().setBorder(BorderFactory.createLineBorder(Utils.getColorStart().darker(), 1));
	
	        setUndecorated( true );
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setBackground(Utils.getColorBackground());
	        pack();
	        setSize(new Dimension(240, 200));
	        setAlwaysOnTop(true);

	}

	public void setVisible(MouseEvent e) {
		Point pos = e.getLocationOnScreen();
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

}