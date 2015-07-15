package it.rocci.clipboss.utils;

import it.rocci.clipboss.model.ClipboardItem;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.util.Date;
import java.util.logging.Level;

public class ClipboardMonitor implements Runnable {

         @Override
         public void run() {
             String previous = getClipboardContents();
             while (true) {
                 try {
                     Thread.sleep(1000);
                 } catch (InterruptedException ex) {
                 }
                 String text = getClipboardContents();
                 if (text != null && !text.equals(previous)) {
                     setText(text);
                     previous = text;
                 }
             }
         }

         private String getContents() {
        	 http://stackoverflow.com/questions/16140151/adding-copy-action-in-windows-to-java-app
         }
         
         Utils.logger.log(Level.INFO, "Clipboard updated: " + e.getSource() + " " + e.toString());

			try {                
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				Transferable content = clipboard.getContents(null);
				
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