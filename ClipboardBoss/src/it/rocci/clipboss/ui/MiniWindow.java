package it.rocci.clipboss.ui;

import it.rocci.clipboss.ui.component.Button;
import it.rocci.clipboss.ui.component.ClipboardRendererMini;
import it.rocci.clipboss.ui.component.NotificationPanel;
import it.rocci.clipboss.utils.Utils;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

public class MiniWindow extends NotificationPanel {

	public MiniWindow(final MainWindow main) {
		super();
		
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
		
		JList list = new JList(main.getModel());
		list.setCellRenderer(new ClipboardRendererMini());
		list.setSelectionBackground(Utils.getColorStart());
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		final JScrollPane spCenter = new JScrollPane(list);
		spCenter.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		spCenter.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		spCenter.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		spCenter.validate();
		
		this.add(spCenter, BorderLayout.CENTER);
	}

}
