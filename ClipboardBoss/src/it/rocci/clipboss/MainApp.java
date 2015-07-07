package it.rocci.clipboss;

import it.rocci.clipboss.model.ClipboardItem;
import it.rocci.clipboss.ui.AboutDialog;
import it.rocci.clipboss.ui.MainWindow;
import it.rocci.clipboss.ui.MiniWindow;
import it.rocci.clipboss.ui.SettingsDialog;
import it.rocci.clipboss.ui.component.NotificationPanel;
import it.rocci.clipboss.utils.Configuration;
import it.rocci.clipboss.utils.Utils;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.FlavorEvent;
import java.awt.datatransfer.FlavorListener;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.logging.Level;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

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
		miniWindow.setIconImage(image);

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
			mainWindow.setVisible(false);
		} catch (AWTException ex) {
			Utils.logger.log(Level.WARNING, "Unable to add system tray ");
		}


		Toolkit.getDefaultToolkit().getSystemClipboard().addFlavorListener(new FlavorListener() { 
			@Override 
			public void flavorsChanged(FlavorEvent e) {
				Utils.logger.log(Level.INFO, "Clipboard updated: " + e.getSource() + " " + e.toString());

				try {                
					Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

					Transferable content = clipboard.getContents(this);
					if (content == null) return;

					if(content.isDataFlavorSupported(DataFlavor.stringFlavor)) {
						Object o = clipboard.getData(DataFlavor.stringFlavor);
						mainWindow.getModel().addElement(new ClipboardItem(0,o,new Date()));

					} 
					else if (content.isDataFlavorSupported(DataFlavor.imageFlavor)) {
						Object o = clipboard.getData(DataFlavor.imageFlavor);                        
						mainWindow.getModel().addElement(new ClipboardItem(1,o,new Date()));
					}   
					//trayIcon.displayMessage(Utils.getLabel("title"), Utils.getLabel("message"), TrayIcon.MessageType.INFO);
				}    
				catch (Exception ex) {
					Utils.logger.log(Level.SEVERE, "Clipboard Listener");
				}      
			} 
			
		}); 
	}


	public static void main(String[] args) {
		new MainApp();
	}

}
