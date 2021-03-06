package it.rocci.clipboss.utils;

import it.rocci.clipboss.model.ClipboardItem;
import it.rocci.clipboss.model.ClipboardList;
import it.rocci.clipboss.ui.component.ScratchPad;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.logging.Level;

public class ClipboardMonitor implements Runnable {

	private Object previousContent;
	private DataFlavor previousFlavor;

	private Object currentContent;
	private DataFlavor currentFlavor;

	private ClipboardList model;
	private ScratchPad notifier;
	
	private Integer iSleep;
	private Integer iMax;
	private boolean pause = false; 

	public ClipboardMonitor(ClipboardList clipboardList, ScratchPad notifier) {
		this.model = clipboardList;
		this.notifier = notifier;
		this.updateConfig();
	}

	public void updateConfig() {
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
			if (!pause) {
				getContents();
				if ( currentContent == null || currentContent == null) {
					continue;
				}
				
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
	}

	private void hasChanged() {
		Utils.logger.log(Level.INFO, "Clipboard content updated");
		if(previousFlavor.equals(DataFlavor.stringFlavor)) {
			model.add(new ClipboardItem(0,previousContent,new Date()));
			notifier.showTextMessage((String)previousContent);
		} 
		else if (previousFlavor.equals(DataFlavor.imageFlavor)) {
			model.add(new ClipboardItem(1,previousContent,new Date()));
			notifier.showImageMessage((Image)previousContent);
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
			Utils.logger.log(Level.INFO, "DataFlavor getContents: -------------");
			for (DataFlavor d : content.getTransferDataFlavors()) {
				Utils.logger.log(Level.INFO, "DataFlavor toString: " + d.toString());
				Utils.logger.log(Level.INFO, "DataFlavor getMimeType: " + d.getMimeType());
				Utils.logger.log(Level.INFO, "DataFlavor getHumanPresentableName: " + d.getHumanPresentableName());
			}	
			
			if(content.isDataFlavorSupported(DataFlavor.stringFlavor)) {
				currentContent = clipboard.getData(DataFlavor.stringFlavor);
				currentFlavor = DataFlavor.stringFlavor;
				Utils.logger.log(Level.INFO, "DataFlavor: stringFlavor");
			} 
			if (content.isDataFlavorSupported(DataFlavor.imageFlavor)) {
				currentContent = clipboard.getData(DataFlavor.imageFlavor);
				currentFlavor = DataFlavor.imageFlavor;  
				Utils.logger.log(Level.INFO, "DataFlavor: imageFlavor");
			}   
		}    
		catch (Exception ex) {
			Utils.logger.log(Level.SEVERE, "Clipboard Monitor");
		}     
	}
	
	 public synchronized void setPause(boolean pause) {
	      this.pause = pause; 
	   }
	   
	   public synchronized boolean isPause() {
	      return this.pause; 
	   }
}