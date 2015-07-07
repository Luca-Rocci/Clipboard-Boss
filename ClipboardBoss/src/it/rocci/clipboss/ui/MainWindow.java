package it.rocci.clipboss.ui;

import it.rocci.clipboss.model.ClipboardItem;
import it.rocci.clipboss.ui.component.Button;
import it.rocci.clipboss.ui.component.ClipboardRenderer;
import it.rocci.clipboss.ui.component.Header;
import it.rocci.clipboss.ui.component.StatusBar;
import it.rocci.clipboss.utils.Utils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;


public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1111111100000000111L;

	private final StatusBar statusBar;
	
	private final Header header;

	private DefaultListModel model;

	private Point initialClick;

	private JList list;

	public DefaultListModel getModel() {
		return model;
	}

	public MainWindow() {

		// initialize
		this.statusBar = new StatusBar();
		this.statusBar.setBackground(Utils.getColorStart());
		
		this.header = new Header(Utils.getLabel("title"), "",
				Utils.getIcon("clipboard.png"));
		this.header.setDimension(new Dimension(100, 60));
		this.header.setColorStart(Utils.getColorStart());
		this.header.setColorEnd(Utils.getColorEnd());
		
		this.setForeground(Utils.getColorText());
		this.setFont(Utils.getFontText());
		this.setBackground(Utils.getColorBackground());
		
		JPanel pnlButton = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlButton.setBorder(new EmptyBorder(1, 1, 1, 1));
		pnlButton.setOpaque(false);

		Button btnClose= new Button();
		btnClose.setIcon("close-24");
		btnClose.setText(Utils.getLabel("close"));
		btnClose.addMouseListener(new MouseAdapter()  
		{  
		    public void mouseClicked(MouseEvent e)  
		    {  
		    	System.exit(0);
		    }  
		}); 
		
		Button btnMinimize = new Button();
		btnMinimize.setIcon("down-24");
		btnMinimize.setText(Utils.getLabel("minimize"));
		btnMinimize.addMouseListener(new MouseAdapter()  
		{  
		    public void mouseClicked(MouseEvent e)  
		    {  
		    	setVisible(false);
		    }  
		}); 
		
		Button btnSettings= new Button();
		btnSettings.setIcon("settings-24");
		btnSettings.setText(Utils.getLabel("settings"));
		btnSettings.addMouseListener(new MouseAdapter()  
		{  
		    public void mouseClicked(MouseEvent e)  
		    {  
				final SettingsDialog sd = new SettingsDialog();
				sd.setLocationRelativeTo(null);
				sd.setVisible(true);
		    }  
		}); 
		
		Button btnAbout= new Button();
		btnAbout.setIcon("about-24");
		btnAbout.setText(Utils.getLabel("about"));
		btnAbout.addMouseListener(new MouseAdapter()  
		{  
		    public void mouseClicked(MouseEvent e)  
		    {  
				final AboutDialog ad = new AboutDialog();
				ad.setLocationRelativeTo(null);
				ad.setVisible(true);
		    }  
		}); 
		
		Button btnDelete= new Button();
		btnDelete.setIcon("delete-24");
		btnDelete.setText(Utils.getLabel("delete"));
		btnDelete.addMouseListener(new MouseAdapter()  
		{  
		    public void mouseClicked(MouseEvent e)  
		    {  
		    	int selectedIndex = list.getSelectedIndex();
		    	   if (selectedIndex != -1) {
		    		   model.remove(selectedIndex);
		    	   }
		    }  
		}); 
		
		Button btnCopy = new Button();
		btnCopy.setIcon("copy-24");
		btnCopy.setText(Utils.getLabel("copy"));
		btnCopy.addMouseListener(new MouseAdapter()  
		{  
		    public void mouseClicked(MouseEvent e)  
		    {  
		    	int selectedIndex = list.getSelectedIndex();
		    	   if (selectedIndex != -1) {
		    		   ClipboardItem ci = (ClipboardItem) model.get(selectedIndex);
		    		   if (ci.getType() == 0) {
		    			   StringSelection selection = new StringSelection((String) ci.getValue());
					        Clipboard clip=Toolkit.getDefaultToolkit().getSystemClipboard();
					        clip.setContents(selection,selection);
		    		   }
		    		   if (ci.getType() == 1) {
					        ImageTransferable selection = new ImageTransferable((Image) ci.getValue());
					        Clipboard clip=Toolkit.getDefaultToolkit().getSystemClipboard();
					        clip.setContents(selection,selection);
		    		   }
		    	   }
		    }  
		}); 
		
		
		pnlButton.add(btnMinimize);
		pnlButton.add(btnClose);
		pnlButton.add(btnSettings);
		pnlButton.add(btnAbout);
		pnlButton.add(btnCopy);
		pnlButton.add(btnDelete);
		
		this.header.setLayout(new BorderLayout());
		this.header.add(pnlButton,BorderLayout.PAGE_END);
		
		this.setLayout(new BorderLayout());

		model = new DefaultListModel();

		list = new JList(model);
		list.setCellRenderer(new ClipboardRenderer());
		list.setSelectionBackground(Utils.getColorStart());
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		final JScrollPane spCenter = new JScrollPane(list);
		spCenter.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		spCenter.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		spCenter.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		spCenter.validate();
		
		this.add(this.header, BorderLayout.PAGE_START);
		this.add(spCenter, BorderLayout.CENTER);
		this.add(this.statusBar, BorderLayout.PAGE_END);
		
		setUndecorated(true);
		
		getRootPane().setBorder(BorderFactory.createLineBorder(Utils.getColorStart().darker(), 1));

		this.header.addMouseListener(new MouseAdapter() {
	        public void mousePressed(MouseEvent e) {
	            initialClick = e.getPoint();
	            getComponentAt(initialClick);
	        }
	    });

	    this.header.addMouseMotionListener(new MouseMotionAdapter() {
	        @Override
	        public void mouseDragged(MouseEvent e) {

	            int thisX = getLocation().x;
	            int thisY = getLocation().y;

	            int xMoved = (thisX + e.getX()) - (thisX + initialClick.x);
	            int yMoved = (thisY + e.getY()) - (thisY + initialClick.y);

	            int X = thisX + xMoved;
	            int Y = thisY + yMoved;
	            setLocation(X, Y);
	        }
	    });
	    
	}	

	 static class ImageTransferable implements Transferable, ClipboardOwner
	  {
	    Image image;
	    ImageTransferable(Image img)
	    {
	      this.image = img;
	    }
	    public DataFlavor[] getTransferDataFlavors()
	    {
	      return new DataFlavor[]{DataFlavor.imageFlavor};
	    }
	    public boolean isDataFlavorSupported(DataFlavor flavor)
	    {
	      return DataFlavor.imageFlavor.equals(flavor);
	    }
	    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException
	    {
	      return image;
	    }

	    public void lostOwnership(Clipboard clipboard, Transferable contents) {}
	  }

}