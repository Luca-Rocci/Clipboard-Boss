package it.rocci.clipboss.model;

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

	
}
