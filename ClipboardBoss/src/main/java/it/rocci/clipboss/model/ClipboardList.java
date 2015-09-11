package it.rocci.clipboss.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;

public class ClipboardList extends AbstractListModel<ClipboardItem> {

	private List<ClipboardItem> lClipboard = new ArrayList<ClipboardItem>();
	
	@Override
	public ClipboardItem getElementAt(int index) {
		return lClipboard.get(index);
	}

	@Override
	public int getSize() {
		return lClipboard.size();
	}
	
	public void remove(int index) {
		lClipboard.remove(index);
		 fireContentsChanged(this, 0, getSize());
	}
	
	public ClipboardItem get(int index) {
		return lClipboard.get(index);
	}

	public void add(ClipboardItem item) {
		if (!lClipboard.contains(item)) {
			lClipboard.add(0,item);	
			fireContentsChanged(this, 0, getSize());
		}
	}
	
	public void clear() {
		lClipboard.clear();
		fireContentsChanged(this, 0, getSize());
	}
	
}
