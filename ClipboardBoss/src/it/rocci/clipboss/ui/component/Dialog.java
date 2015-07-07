package it.rocci.clipboss.ui.component;

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

public class Dialog extends JDialog {

	private static final long serialVersionUID = 1111111100000000112L;
	private final Header header;
	public JPanel footer;
	public Button close;
	private Point initialClick;

	public Dialog() {
		this.header = new Header();
		this.footer = new JPanel();
		
		this.setTitle(Utils.getLabel("title"));
		this.setLayout(new BorderLayout());

		this.setForeground(Utils.getColorText());
		this.setFont(Utils.getFontText());
		this.setBackground(Utils.getColorBackground());

		this.footer.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

		this.close = new Button();
		this.close.setIcon("close-24");
		this.close.setText(Utils.getLabel("close"));
		this.close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				Dialog.this.dispose();
			}
		});

		this.header.setColorStart(Utils.getColorStart());
		this.header.setColorEnd(Utils.getColorEnd());

		this.footer.setLayout(new BorderLayout());
		this.footer.setBackground(Utils.getColorBackground());
		this.footer.add(new Separator(), BorderLayout.PAGE_START);
		this.footer.add(this.close, BorderLayout.LINE_END);

		this.add(this.header, BorderLayout.PAGE_START);
		this.add(this.footer, BorderLayout.PAGE_END);

		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(450, 300);

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
}
