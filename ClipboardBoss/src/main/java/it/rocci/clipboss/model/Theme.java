package it.rocci.clipboss.model;

import it.rocci.clipboss.utils.Configuration;
import it.rocci.clipboss.utils.Utils;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;

import javax.swing.ImageIcon;

public class Theme {

    private static Properties propTheme;
    
	public static void cleanThemeInfo() {
		propTheme = null;
	}
	
	public static void loadThemeInfo() {
		if (propTheme == null) {
			 propTheme = new Properties();
	    	 try {
	    		 String path = "Theme" + Utils.FILE_SEPARATOR + Configuration.getString("settings.ui.theme") + Utils.FILE_SEPARATOR + "Theme.info";
	    		 propTheme.load(new FileInputStream(path));
	         } catch(IOException e) {
	        		Utils.logger.log(Level.CONFIG, "Load " + Utils.CONFIG_FILE +" failed", e);
	         }
		}
	}
	
	public static Color getColorStart() {
		loadThemeInfo();
			return Color.decode(propTheme.getProperty("theme.color.start", "#86abd9"));
	}
	
	public static Color getColorEnd() {
		loadThemeInfo();
			return Color.decode(propTheme.getProperty("theme.color.end", "#ffffff"));
	}
	
	public static Color getColorText() {
		loadThemeInfo();
			return Color.decode(propTheme.getProperty("theme.color.text", "#000000"));
	}
	
	public static Color getColorTextHover() {
		loadThemeInfo();
			return Color.decode(propTheme.getProperty("theme.color.texthover", "#666666"));
	}
	
	public static Color getColorBackground() {
		loadThemeInfo();
			return Color.decode(propTheme.getProperty("theme.color.background", "#ffffff"));
	}
	
	public static Font getFontTitle() {
		loadThemeInfo();
		String path = "Theme" + Utils.FILE_SEPARATOR + Configuration.getString("settings.ui.theme") + Utils.FILE_SEPARATOR + propTheme.getProperty("theme.font.title", "");
		try {
			File file = new File(path);
			return Font.createFont(Font.TRUETYPE_FONT, file).deriveFont(Font.PLAIN, 22);
		} catch (Exception e) {
			Utils.logger.log(Level.SEVERE, "load failed " + path);
		}
		return null;
	}
	
	public static Font getFontText() {
		String path = "Theme" + Utils.FILE_SEPARATOR + Configuration.getString("settings.ui.theme") + Utils.FILE_SEPARATOR + propTheme.getProperty("theme.font.text", "");
		try {
			File file = new File(path);
			return Font.createFont(Font.TRUETYPE_FONT, file).deriveFont(Font.PLAIN, 12);
		} catch (Exception e) {
			Utils.logger.log(Level.SEVERE, "load failed " + path);
		}
		return null;
	}

	public static ImageIcon getIcon(String icon) {
		String path = "Theme" + Utils.FILE_SEPARATOR + Configuration.getString("settings.ui.theme") + Utils.FILE_SEPARATOR + icon;
			ImageIcon imgIcon = new ImageIcon(path);
		return imgIcon;
	}
	
}
