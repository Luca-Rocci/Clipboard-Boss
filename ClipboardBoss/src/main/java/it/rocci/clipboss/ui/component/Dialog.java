package it.rocci.clipboss.ui.component;

import it.rocci.clipboss.model.Theme;
import it.rocci.clipboss.utils.Utils;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JToolBar.Separator;

public class Dialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1111111100000000112L;
	private final Header header;
	public JPanel footer;
	public Button btnClose;
	private Point initialClick;

	public Dialog() {
		this.header = new Header();
		this.footer = new JPanel();
		
		this.setTitle(Utils.getLabel("title"));
		this.setLayout(new BorderLayout());

		this.btnClose = new Button(this);

		this.footer.setLayout(new BorderLayout());
		this.footer.setBackground(Theme.getColorBackground());
		this.footer.add(new Separator(), BorderLayout.PAGE_START);
		this.footer.add(this.btnClose, BorderLayout.LINE_END);

		this.add(this.header, BorderLayout.PAGE_START);
		this.add(this.footer, BorderLayout.PAGE_END);

		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(450, 300);

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
	
	public void updateUI() {
		this.setForeground(Theme.getColorText());
		this.setFont(Theme.getFontText());
		this.setBackground(Theme.getColorBackground());

		this.footer.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

		this.setTitle(Utils.getLabel("title"));
		this.header.setColorStart(Theme.getColorStart());
		this.header.setColorEnd(Theme.getColorEnd());
		
		this.btnClose.setIcon("close-24");
		this.btnClose.setText(Utils.getLabel("close"));
		this.btnClose.init();
		
		getRootPane().setBorder(BorderFactory.createLineBorder(Theme.getColorStart().darker(), 1));
	}

	public void setDescription(String description) {
		this.header.setDescription(description);
	}

	public void setIcon(ImageIcon image) {
		this.header.setIcon(image);
	}

	@Override
	public void setTitle(String title) {
		if (this.header == null) {
			super.setTitle(title);
		} else {
			this.header.setTitle(title);
		}
	}
	
	@Override
	public void setVisible(boolean b) {
		updateUI();
		super.setVisible(b);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnClose) {
			Dialog.this.dispose();	
		}
	}
}
