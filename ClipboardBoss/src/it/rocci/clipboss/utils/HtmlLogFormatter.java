package it.rocci.clipboss.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class HtmlLogFormatter extends Formatter {

	private final SimpleDateFormat dateFormatter = new SimpleDateFormat(
			"dd/MM/yyyy HH:mm:ss");

	@Override
	public String format(LogRecord rec) {
		final StringBuffer buf = new StringBuffer(1000);

		buf.append("<tr>");
		buf.append("<td>");
		if (rec.getLevel().intValue() >= Level.WARNING.intValue()) {
			buf.append("<b>");
			buf.append(rec.getLevel());
			buf.append("</b>");
		} else {
			buf.append(rec.getLevel());
		}
		buf.append("</td>");
		buf.append("<td>");
		buf.append(this.dateFormatter.format(rec.getMillis()));
		buf.append("</td>");
		buf.append("<td>");
		buf.append(rec.getSourceClassName());
		buf.append("</td>");
		buf.append("<td>");
		buf.append(rec.getSourceMethodName());
		buf.append("</td>");
		buf.append("<td>");
		buf.append(this.formatMessage(rec));
		buf.append("<td>");
		buf.append("<tr>\n");
		return buf.toString();
	}

	@Override
	public String getHead(Handler h) {
		String strHead = "<!DOCTYPE html>\n";
		strHead += "<html>\n";
		strHead += "<body>\n";
		strHead += "<h3>Clipboard Boss Log</h3>\n";
		strHead += "<h4>last execution start at "
				+ this.dateFormatter.format(new Date()) + " </h4>\n";
		strHead += "<table border=\"0\" width=\"100%\">\n";
		strHead += "<tr>\n";
		strHead += "<th>Level</th>\n";
		strHead += "<th>Time</th>\n";
		strHead += "<th>Class</th>\n";
		strHead += "<th>Method</th>\n";
		strHead += "<th>Message</th>\n";
		strHead += "</tr>\n";
		return strHead;
	}

	@Override
	public String getTail(Handler h) {
		String strTail = "</table>\n";
		strTail += "</body>\n";
		strTail += "</html>\n";
		return strTail;
	}
}