package it.rocci.clipboss.utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;

public class Utils {

    private static Properties propTheme;
	
	public static final String FILE_SEPARATOR = System.getProperty("file.separator");
	public static final String CONFIG_FILE = "Config" + FILE_SEPARATOR + "config.properties";
	
	public static final Logger logger;
	public static final String LOG_FILE = "Log" + FILE_SEPARATOR + "LogFull.html";
	public static final String OsName = System.getProperty("os.name").toLowerCase();

	private static ResourceBundle resourceBundle = null;

	public static final String UserHome = System.getProperty("user.home");
	
	static {
		// logger
		logger = Logger.getLogger("it.rocci.clipboss");
		FileHandler fh = null;
		try {
			fh = new FileHandler(LOG_FILE);
		} catch (final Exception e) {
		}
		logger.addHandler(fh);
		logger.setLevel(Level.ALL);
		fh.setFormatter(new HtmlLogFormatter());

	}

	public static void cleanDirectory(File path) {
		if (path.exists()) {
			final File[] files = path.listFiles();
			for (final File file : files) {
				if (file.isDirectory()) {
					cleanDirectory(file);
				}
				file.delete();
			}
		}
	}

	public static ImageIcon getIcon(String icon) {
		String path = "Theme" + FILE_SEPARATOR + Configuration.getString("settings.ui.theme") + FILE_SEPARATOR + icon;
			ImageIcon imgIcon = new ImageIcon(path);
		return imgIcon;
	}

	public static String getLabel(String label) {
		if (resourceBundle == null) {
			File file = new File("Lang");
			URL[] urls = null;
			try {
				urls= new URL[] {file.toURI().toURL()};
			} catch (MalformedURLException e){
			e.printStackTrace();
			}
			ClassLoader loader = new URLClassLoader(urls);
			
			for (Locale locale : supportedLocales) {
				if (locale.getLanguage().equals(Configuration.getString("settings.ui.locale"))) {
					resourceBundle = ResourceBundle.getBundle("LanguagePack", locale, loader);
					break;				
				}
			}
			if (resourceBundle == null) {
				resourceBundle = ResourceBundle.getBundle("LanguagePack",
						Locale.getDefault(), loader);
			}
		}
		return resourceBundle.getString(label);
	};
	
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
	
	public static void loadThemeInfo() {
		if (propTheme == null) {
			 propTheme = new Properties();
	    	 try {
	    		 String path = "Theme" + FILE_SEPARATOR + Configuration.getString("settings.ui.theme") + FILE_SEPARATOR + "Theme.info";
	    		 propTheme.load(new FileInputStream(path));
	         } catch(IOException e) {
	        		Utils.logger.log(Level.CONFIG, "Load " + Utils.CONFIG_FILE +" failed", e);
	         }
		}
	}
	
	public static Font getFontTitle() {
		loadThemeInfo();
		String path = "Theme" + FILE_SEPARATOR + Configuration.getString("settings.ui.theme") + FILE_SEPARATOR + propTheme.getProperty("theme.font.title", "");
		try {
			File file = new File(path);
			return Font.createFont(Font.TRUETYPE_FONT, file).deriveFont(Font.PLAIN, 22);
		} catch (Exception e) {
            logger.log(Level.SEVERE, "load failed " + path);
		}
		return null;
	}
	
	public static Font getFontText() {
		String path = "Theme" + FILE_SEPARATOR + Configuration.getString("settings.ui.theme") + FILE_SEPARATOR + propTheme.getProperty("theme.font.text", "");
		try {
			File file = new File(path);
			return Font.createFont(Font.TRUETYPE_FONT, file).deriveFont(Font.PLAIN, 12);
		} catch (Exception e) {
            logger.log(Level.SEVERE, "load failed " + path);
		}
		return null;
	}

	public static boolean isLinux() {
		return (OsName.indexOf("linux") >= 0);
	}

	public static boolean isWindows() {
		return (OsName.indexOf("windows") >= 0);
	}
	
    public static boolean isMac() {
        return (OsName.indexOf("mac") >= 0);
    }

	
	public static Locale[] supportedLocales = {
	    Locale.ITALIAN,
	    Locale.ENGLISH,
		Locale.FRENCH,
		    Locale.GERMAN
		};

	public static String[] supportedPositions = { "Auto","TopRight", "TopLeft", "BottomRight", "BottomLeft" };
	
}