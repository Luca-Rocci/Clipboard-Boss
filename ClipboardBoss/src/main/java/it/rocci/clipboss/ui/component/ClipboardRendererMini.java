package it.rocci.clipboss.ui.component;

import it.rocci.clipboss.model.ClipboardItem;
import it.rocci.clipboss.utils.Utils;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Image;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

public class ClipboardRendererMini extends JPanel implements ListCellRenderer {

	private JLabel lblValue;

	public ClipboardRendererMini() {
		setLayout(new BorderLayout(2, 2));

		lblValue = new JLabel();
		lblValue.setBorder(BorderFactory.createEmptyBorder(1, 3, 3, 3));

		add(lblValue, BorderLayout.CENTER);

	}


	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		ClipboardItem ci = (ClipboardItem) value;
		lblValue.setText(null);
		lblValue.setIcon(null);

		switch (ci.getType()) {
		case 0:
			String strOrig = (String)ci.getValue();
			if (strOrig.length() > 150) {
				strOrig = strOrig.substring(0, 150) + "...";
			}
			lblValue.setText("<html><div style='width: 130px;' />" + strOrig + "</div></html>");
			break;
		case 1:
			Image imgOrig = ((Image)ci.getValue());

			if(imgOrig.getWidth(null) > 150) {
				imgOrig = imgOrig.getScaledInstance(150, -1, Image.SCALE_FAST);
			}
			if(imgOrig.getHeight(null) > 50) {
				imgOrig = imgOrig.getScaledInstance(-1, 50, Image.SCALE_FAST);
			}
			lblValue.setIcon(new ImageIcon(imgOrig));
			break;
		default:
			break;
		}

		lblValue.setOpaque(true);

		if (isSelected) {
			lblValue.setBackground(list.getSelectionBackground());
			setBackground(list.getSelectionBackground());

		} else {
			lblValue.setBackground(list.getBackground());
			setBackground(list.getBackground());
		}
		lblValue.invalidate();
		invalidate();
		return this;

	}
	
}
