package it.rocci.clipboss.ui;

import it.rocci.clipboss.model.ClipboardItem;
import it.rocci.clipboss.model.Theme;
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class MiniWindow extends NotificationPanel implements ActionListener {

	private Button btnMinimize;
	private Button btnMassimize;
	private MainWindow main;
	private JList list;

	public MiniWindow(final MainWindow main) {
		
		super(main);

		btnMinimize= new Button(this);	
		btnMassimize= new Button(this);

		this.main = main;
		
        setSize(new Dimension(240, 200));

		this.header.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		this.header.add(btnMassimize);
		this.header.add(btnMinimize);
		
		list = new JList(main.getModel());
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addListSelectionListener(new ListSelectionListener() {
		      public void valueChanged(ListSelectionEvent evt) {
		          if (evt.getValueIsAdjusting())
		            return;
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
		      });
		
		final JScrollPane spCenter = new JScrollPane(list);
		spCenter.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		spCenter.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		spCenter.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		spCenter.validate();
		
		this.add(spCenter, BorderLayout.CENTER);

	}
	
@Override
public void updateUI() {
	super.updateUI();

		btnMinimize.setToolTipText(Utils.getLabel("minimize"));
		btnMinimize.setIcon("down-24");

		btnMassimize.setToolTipText(Utils.getLabel("massimize"));
		btnMassimize.setIcon("up-24");
		
		list.setCellRenderer(new ClipboardRendererMini());
		list.setSelectionBackground(Theme.getColorStart());

}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnMinimize) {
			setVisible(false);
		} else if (e.getSource() == btnMassimize) {
			setVisible(false);
	    	main.setVisible(true);
		}
	}

}
