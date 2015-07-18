package it.rocci.clipboss;

import it.rocci.clipboss.ui.MainWindow;
import it.rocci.clipboss.ui.MiniWindow;
import it.rocci.clipboss.ui.component.NotificationPanel;
import it.rocci.clipboss.utils.ClipboardMonitor;
import it.rocci.clipboss.utils.Utils;

import java.awt.AWTException;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

			trayIcon=new TrayIcon(image, Utils.getLabel("title"));
			trayIcon.setImageAutoSize(true);

			trayIcon.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (!mainWindow.isVisible()) {
						miniWindow.setVisible(e);
					}
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
//			mainWindow.setState(mainWindow.ICONIFIED);
		}

		Thread monitorThread = new Thread(new ClipboardMonitor(mainWindow.getModel()));
		monitorThread.setDaemon(true);
		monitorThread.start();

	}


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (Exception ex) {
					Utils.logger.log(Level.SEVERE, "UI Manager", ex);
				}
				new MainApp();
				NotificationPanel.showMessage(Utils.getLabel("start"), 2500);
			}
		});
	}

}
