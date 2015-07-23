package it.rocci.clipboss.model;

import it.rocci.clipboss.utils.Utils;

import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.image.BufferedImage;
import java.util.Date;

import javax.swing.ImageIcon;

public class ClipboardItem {
	
	private int type;
	private Object value;
	private Date date;

	public ClipboardItem(int type, Object value, Date date) {
		super();
		this.type = type;
		this.value = value;
		this.date = date;
	}
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "ClipboardItem [type=" + type + ", date=" + date + "]";
	}

	@Override
	public boolean equals(Object obj) {
		ClipboardItem o = (ClipboardItem) obj;
		
		if ( o == null) {
			return false;
		}

		if (o.getType() == getType()) {
			if(o.getType() == 0 && o.getValue().equals(getValue())) {
				return true;
			} else if(o.getType() == 1) {
				BufferedImage b1 = Utils.convertImage((Image)getValue());
				BufferedImage b2 = Utils.convertImage((Image)o.getValue());
				if(Utils.bufferedImageEquals(b1,b2)) {
					return true;
				}
			}
		} else {
			return false;
		}
		return false;
	}

	
}
