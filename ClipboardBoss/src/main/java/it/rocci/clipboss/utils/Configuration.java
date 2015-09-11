package it.rocci.clipboss.utils;

	import java.io.FileInputStream;
	import java.io.FileOutputStream;
	import java.io.IOException;
	import java.util.Enumeration;
	import java.util.Properties;
	import java.util.logging.Level;
	 
	public class Configuration {

	    private static Properties properties = new Properties();
	     
	    static {
	    	 try {
	    		 properties.load(new FileInputStream(Utils.CONFIG_FILE));
	         } catch(IOException e) {
	        		Utils.logger.log(Level.CONFIG, "Load " + Utils.CONFIG_FILE +" failed", e);
	         }
	    }
	          
	      public static synchronized String getString(String key) {
	    	  return properties.getProperty(key, "");
	      }

	      public static synchronized void setString(String key, String value)  {
	              properties.setProperty(key, value);         
	            try {
	                properties.store(new FileOutputStream(Utils.CONFIG_FILE), Utils.getLabel("title"));
	            } catch (IOException e) {
	            	Utils.logger.log(Level.SEVERE, "Save " + Utils.CONFIG_FILE +" failed", e);
	            }
	    }
	      
	      public static synchronized Enumeration getkeys() {
	    	  return properties.keys();
	      }     

	}