package it.rocci.clipboss.ui;

import it.rocci.clipboss.model.ClipboardItem;
import it.rocci.clipboss.ui.MainWindow.ImageTransferable;
import it.rocci.clipboss.ui.component.Button;
import it.rocci.clipboss.ui.component.ClipboardRendererMini;
import it.rocci.clipboss.ui.component.NotificationPanel;
import it.rocci.clipboss.utils.Utils;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

public class MiniWindow extends NotificationPanel {

	public MiniWindow(final MainWindow main) {
		super();
		
        setSize(new Dimension(240, 200));
		
		JPanel pnlButton = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlButton.setBorder(new EmptyBorder(1, 1, 1, 1));
		pnlButton.setOpaque(false);

		Button btnMinimize= new Button();
		btnMinimize.setToolTipText(Utils.getLabel("minimize"));
		btnMinimize.setIcon("down-24");
		btnMinimize.addMouseListener(new MouseAdapter()  
		{  
		    public void mouseClicked(MouseEvent e)  
		    {  
		    	setVisible(false);
		    }  
		}); 
		
		Button btnMassimize= new Button();
		btnMassimize.setToolTipText(Utils.getLabel("massimize"));
		btnMassimize.setIcon("up-24");
		btnMassimize.addMouseListener(new MouseAdapter()  
		{  
		    public void mouseClicked(MouseEvent e)  
		    {  
		    	setVisible(false);
		    	main.setVisible(true);
		    }  
		}); 

		this.header.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		this.header.add(btnMassimize);
		this.header.add(btnMinimize);
		
		final JList list = new JList(main.getModel());
		list.setCellRenderer(new ClipboardRendererMini());
		list.setSelectionBackground(Utils.getColorStart());
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					int selectedIndex = list.getSelectedIndex();
		    	   if (selectedIndex != -1) {
		    		   ClipboardItem ci = (ClipboardItem) main.getModel().get(selectedIndex);
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
				
			}
		});
		
		final JScrollPane spCenter = new JScrollPane(list);
		spCenter.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		spCenter.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		spCenter.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		spCenter.validate();
		
		this.add(spCenter, BorderLayout.CENTER);
	}

}
