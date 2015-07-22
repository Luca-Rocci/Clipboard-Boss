package it.rocci.clipboss.ui;

import it.rocci.clipboss.model.ClipboardItem;
import it.rocci.clipboss.model.Theme;
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class MainWindow extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1111111100000000111L;
	private final StatusBar statusBar;
	private final Header header;
	private ListModel<ClipboardItem> model;
	private Point initialClick;
	private JList list;
	private Button btnClose;
	private Button btnMinimize;
	private Button btnSettings;
	private Button btnAbout;
	private Button btnDelete;

	public DefaultListModel getModel() {
		return model;
	}

	public MainWindow() {

		// initialize
		this.statusBar = new StatusBar();
		this.statusBar.setBackground(Theme.getColorStart());
		
		this.header = new Header(Utils.getLabel("title"), "",
				Theme.getIcon("clipboard.png"));
		this.header.setDimension(new Dimension(100, 60));
		
		JPanel pnlButton = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlButton.setBorder(new EmptyBorder(1, 1, 1, 1));
		pnlButton.setOpaque(false);

		btnClose = new Button(this);
		btnMinimize = new Button(this);
		btnSettings= new Button(this);
		btnAbout= new Button(this);
		btnDelete= new Button(this);
		
		btnSettings.addMouseListener(new MouseAdapter()  
		{  

		    @Override
		    public void mouseExited(MouseEvent e) {
		    	invalidate();
		    	updateUI();
		    	super.mouseExited(e);
		    }
		    
		}); 
		
		
		pnlButton.add(btnMinimize);
		pnlButton.add(btnClose);
		pnlButton.add(btnSettings);
		pnlButton.add(btnAbout);
		pnlButton.add(btnDelete);
		
		this.header.setLayout(new BorderLayout());
		this.header.add(pnlButton,BorderLayout.PAGE_END);
		
		this.setLayout(new BorderLayout());

		model = new ListModel<ClipboardItem>() {

			@Override
			public void addListDataListener(ListDataListener l) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public ClipboardItem getElementAt(int index) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public int getSize() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public void removeListDataListener(ListDataListener l) {
				// TODO Auto-generated method stub
				
			}
			
		};();

		list = new JList(model);
		list.setCellRenderer(new ClipboardRenderer());
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setFixedCellWidth(500);
		list.addListSelectionListener(new ListSelectionListener() {
		      public void valueChanged(ListSelectionEvent evt) {
		          if (evt.getValueIsAdjusting())
		            return;
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
		
		final JScrollPane spCenter = new JScrollPane(list);
		spCenter.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		spCenter.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		spCenter.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		spCenter.validate();
		
		this.add(this.header, BorderLayout.PAGE_START);
		this.add(spCenter, BorderLayout.CENTER);
		this.add(this.statusBar, BorderLayout.PAGE_END);
		
		setUndecorated(true);

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

	@Override
	public void setVisible(boolean b) {
    	updateUI();
		super.setVisible(b);
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

		public void updateUI() {
		
			this.statusBar.setBackground(Theme.getColorStart());
			
			this.header.setTitle(Utils.getLabel("title"));
			this.header.setIcon(Theme.getIcon("clipboard.png"));
			this.header.setColorStart(Theme.getColorStart());
			this.header.setColorEnd(Theme.getColorEnd());
			
			this.setForeground(Theme.getColorText());
			this.setFont(Theme.getFontText());
			this.setBackground(Theme.getColorBackground());
			
			this.btnClose.setIcon("close-24");
			this.btnClose.setText(Utils.getLabel("close"));
			this.btnClose.init();
			
			this.btnMinimize.setIcon("down-24");
			this.btnMinimize.setText(Utils.getLabel("minimize"));
			this.btnMinimize.init();
			
			this.btnSettings.setIcon("settings-24");
			this.btnSettings.setText(Utils.getLabel("settings"));
			this.btnSettings.init();
			
			this.btnAbout.setIcon("about-24");
			this.btnAbout.setText(Utils.getLabel("about"));
			this.btnAbout.init();
			
			this.btnDelete.setIcon("delete-24");
			this.btnDelete.setText(Utils.getLabel("delete"));
			this.btnDelete.init();
			
			this.list.setCellRenderer(new ClipboardRenderer());
			this.list.setSelectionBackground(Theme.getColorStart());
			
			this.getRootPane().setBorder(BorderFactory.createLineBorder(Theme.getColorStart().darker(), 1));

		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnClose) {
				System.exit(0);
			} else if (e.getSource() == btnMinimize) {
				setVisible(false);
			}else if (e.getSource() == btnSettings) {
				final SettingsDialog sd = new SettingsDialog();
				sd.setLocationRelativeTo(null);
				sd.setVisible(true);
			}else if (e.getSource() == btnAbout) {
				final AboutDialog ad = new AboutDialog();
				ad.setLocationRelativeTo(null);
				ad.setVisible(true);
			}else if (e.getSource() == btnDelete) {
				int selectedIndex = list.getSelectedIndex();
	    	   if (selectedIndex != -1) {
	    		   model.remove(selectedIndex);
	    	   }
			}
			
		}
}