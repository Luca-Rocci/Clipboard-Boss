package it.rocci.clipboss.utils;

import it.rocci.clipboss.model.ClipboardItem;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.logging.Level;

import javax.swing.DefaultListModel;

public class ClipboardMonitor implements Runnable {

	private Object previousContent;
	private DataFlavor previousFlavor;

	private Object currentContent;
	private DataFlavor currentFlavor;

	private DefaultListModel model;
	private Integer iSleep;
	private Integer iMax;

	public ClipboardMonitor(DefaultListModel model) {
		this.model = model;
		iSleep = Integer.valueOf(Configuration.getString("settings.function.polling"));
		iMax = Integer.valueOf(Configuration.getString("settings.function.maxitem"));
	}

	@Override
	public void run() {
		getContents();
		previousContent = currentContent;
		previousFlavor = currentFlavor;

		while (true) {
			try {
				Thread.sleep(iSleep);
			} catch (InterruptedException ex) {
			}
			getContents();
			if (previousFlavor != null && previousFlavor.equals(currentFlavor)) {
				if(previousFlavor.equals(DataFlavor.stringFlavor) && !previousContent.equals(currentContent)) {
					previousContent = currentContent;
					hasChanged();
				} else if(previousFlavor.equals(DataFlavor.imageFlavor)) {
					BufferedImage b1 = Utils.convertImage((Image)previousContent);
					BufferedImage b2 = Utils.convertImage((Image)currentContent);
					if(!Utils.bufferedImageEquals(b1,b2)) {
						previousContent = currentContent;
						hasChanged();
					}
				}
			} else {
				previousContent = currentContent;
				previousFlavor = currentFlavor;
				hasChanged();
			}
		}
	}

	private void hasChanged() {
		Utils.logger.log(Level.INFO, "Clipboard content updated");
		if(previousFlavor.equals(DataFlavor.stringFlavor)) {
			model.add(0,new ClipboardItem(0,previousContent,new Date()));
		} 
		else if (previousFlavor.equals(DataFlavor.imageFlavor)) {
			model.add(0,new ClipboardItem(1,previousContent,new Date()));
		}  
		if (model.getSize()> iMax) {
			model.remove(model.getSize()-1);
		}
	}

	private void getContents() {

		try {                
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			Transferable content = clipboard.getContents(null);

			if (content == null) return;

			if(content.isDataFlavorSupported(DataFlavor.stringFlavor)) {
				currentContent = clipboard.getData(DataFlavor.stringFlavor);
				currentFlavor = DataFlavor.stringFlavor;
			} 
			else if (content.isDataFlavorSupported(DataFlavor.imageFlavor)) {
				currentContent = clipboard.getData(DataFlavor.imageFlavor);
				currentFlavor = DataFlavor.imageFlavor;                    
			}   
		}    
		catch (Exception ex) {
			Utils.logger.log(Level.SEVERE, "Clipboard Monitor");
		}     
	}
}