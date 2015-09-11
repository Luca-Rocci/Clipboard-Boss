package it.rocci.clipboss.ui.component;

import it.rocci.clipboss.model.ClipboardItem;
import it.rocci.clipboss.model.Theme;
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

public class ClipboardRenderer extends JPanel implements ListCellRenderer {

	private JLabel lblValue;
	private JLabel lblType;
	private JPanel pnlTop;
	private JLabel lblSize;
	private JLabel lblTime;

	public ClipboardRenderer() {
		setLayout(new BorderLayout(5, 5));

		lblValue = new JLabel();
		lblValue.setBorder(BorderFactory.createEmptyBorder(1, 3, 3, 3));

		lblType = new JLabel();

		lblTime = new JLabel();
		lblTime.setIcon(Theme.getIcon("time-24.png"));

		lblSize = new JLabel();
		lblSize.setIcon(Theme.getIcon("size-24.png"));	

		pnlTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlTop.setBackground(Theme.getColorEnd().darker());
		pnlTop.add(lblTime);
		pnlTop.add(lblType);
		pnlTop.add(lblSize);

		add(lblValue, BorderLayout.CENTER);
		add(pnlTop, BorderLayout.PAGE_START);

	}


	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		ClipboardItem ci = (ClipboardItem) value;
		lblValue.setText(null);
		lblValue.setIcon(null);

		SimpleDateFormat sdf = new SimpleDateFormat(Utils.getLabel("datetime"));

		String formattedDate = sdf.format(ci.getDate());

		lblTime.setText(formattedDate);
		switch (ci.getType()) {
		case 0:
			lblType.setText(Utils.getLabel("text"));
			lblType.setIcon(Theme.getIcon("text-24.png"));
			String strOrig = (String)ci.getValue();
			lblSize.setText(strOrig.length() + " " + Utils.getLabel("char"));
			if (strOrig.length() > 400) {
				strOrig = strOrig.substring(0, 400) + "...";
			}
			lblValue.setText("<html><div style='width: 440px;' />" + strOrig + "</div></html>");
			break;
		case 1:
			lblType.setText(Utils.getLabel("image"));
			lblType.setIcon(Theme.getIcon("image-24.png"));

			Image imgOrig = ((Image)ci.getValue());
			lblSize.setText(imgOrig.getWidth(null) + " x " + imgOrig.getHeight(null) + " px");
			if(imgOrig.getWidth(null) > 570) {
				imgOrig = imgOrig.getScaledInstance(570, -1, Image.SCALE_FAST);
			}
			if(imgOrig.getHeight(null) > 200) {
				imgOrig = imgOrig.getScaledInstance(-1, 200, Image.SCALE_FAST);
			}
			lblValue.setIcon(new ImageIcon(imgOrig));
			break;
		default:
			break;
		}

		lblValue.setOpaque(true);
		lblType.setOpaque(false);
		lblTime.setOpaque(false);
		lblSize.setOpaque(false);

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
