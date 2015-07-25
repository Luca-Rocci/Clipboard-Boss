package it.rocci.clipboss;

import it.rocci.clipboss.ui.AboutDialog;
import it.rocci.clipboss.ui.MainWindow;
import it.rocci.clipboss.ui.MiniWindow;
import it.rocci.clipboss.ui.SettingsDialog;
import it.rocci.clipboss.ui.component.NotificationPanel;
import it.rocci.clipboss.utils.ClipboardMonitor;
import it.rocci.clipboss.utils.Utils;

import java.awt.AWTException;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.logging.Level;

import javax.swing.JFrame;
import javax.swing.UIManager;

public class MainApp {

	private TrayIcon trayIcon;
	private SystemTray tray;
	private MainWindow mainWindow;
	private MiniWindow miniWindow;

	public MainApp() {

		Utils.logger.log(Level.INFO, "Start Clipboard Boss");

		Image image=Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/it/rocci/clipboss/Clipboard.png"));

		mainWindow = new MainWindow();

		mainWindow.setSize(600, 400);
		mainWindow.setTitle(Utils.getLabel("title"));
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.setLocationRelativeTo(null);
		mainWindow.setIconImage(image);

		miniWindow = new MiniWindow(mainWindow);

		if(SystemTray.isSupported()){
			Utils.logger.log(Level.INFO, "System tray supported");
			tray=SystemTray.getSystemTray();

			PopupMenu menu = new PopupMenu();
			MenuItem item = new MenuItem(Utils.getLabel("close"));
			item.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
				 tray.remove(trayIcon);
			 System.exit(0);
			  }
			 });
			menu.add(item);
			menu.addSeparator();
			item = new MenuItem(Utils.getLabel("open"));
			item.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
				 mainWindow.setVisible(true);
			  }
			 });
			menu.add(item);
			item = new MenuItem(Utils.getLabel("about"));
			item.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
				 final AboutDialog ad = new AboutDialog();
					ad.setLocationRelativeTo(null);
					ad.setVisible(true);
			  }
			 });
			menu.add(item);
			item = new MenuItem(Utils.getLabel("settings"));
			item.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
				 final SettingsDialog sd = new SettingsDialog();
					sd.setLocationRelativeTo(null);
					sd.setVisible(true);
			  }
			 });
			menu.add(item);
					
			trayIcon=new TrayIcon(image, Utils.getLabel("title"), menu);
			trayIcon.setImageAutoSize(true);

			trayIcon.addMouseListener(new MouseAdapter() {
				
				@Override
				public void mouseClicked(MouseEvent e) {
					Utils.logger.log(Level.INFO, "e." + e.isPopupTrigger()); 
					Utils.logger.log(Level.INFO, "mouseClicked");
					super.mouseClicked(e);
					if(e.getButton() == MouseEvent.BUTTON1) {
						if (!mainWindow.isVisible()) {
							miniWindow.setVisible(e);
						}
					}
					
				}


				    @Override
				    public void mousePressed(MouseEvent e) {
						Utils.logger.log(Level.INFO, "mousePressed");
						super.mousePressed(e);
				    }

				    @Override
				    public void mouseReleased(MouseEvent e) {
						Utils.logger.log(Level.INFO, "mouseReleased");
						super.mouseReleased(e);
				    }

				    @Override
				    public void mouseEntered(MouseEvent e) {
						Utils.logger.log(Level.INFO, "mouseEntered");
						super.mouseEntered(e);
				    }

				    @Override
				    public void mouseExited(MouseEvent e) {
						Utils.logger.log(Level.INFO, "mouseExited");
						super.mouseExited(e);
				    }

				    @Override
				    public void mouseDragged(MouseEvent e) {
						Utils.logger.log(Level.INFO, "mouseDragged");
						super.mouseDragged(e);
				    }

				    @Override
				    public void mouseMoved(MouseEvent e) {
						Utils.logger.log(Level.INFO, "mouseMoved");
						super.mouseMoved(e);
				    }
				
			});
		}else{
			Utils.logger.log(Level.WARNING, "System tray not supported");
		}

		try {
			tray.add(trayIcon);
		} catch (AWTException ex) {
			Utils.logger.log(Level.WARNING, "Unable to add system tray ");
			mainWindow.setVisible(true);
		}

		Thread monitorThread = new Thread(new ClipboardMonitor(mainWindow.getModel()));
		monitorThread.setDaemon(true);
		monitorThread.start();

	}


	public static void main(String[] args) {
	
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (Exception ex) {
					Utils.logger.log(Level.SEVERE, "UI Manager", ex);
				}
				new MainApp();
				NotificationPanel.showMessage(Utils.getLabel("start"), 2500);
	}

}
