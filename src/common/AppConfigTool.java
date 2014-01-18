package common;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

public class AppConfigTool {
	
	private String configPath ="/common/AppConfig.xml";
	private XMLConfiguration config;
	
	public AppConfigTool(){
		
	     try {
	    	 config =  new XMLConfiguration(configPath);
	     } catch (ConfigurationException e) {
			 // TODO: handle exception
			 e.printStackTrace();
	     }
	}
	
	public String getConfig(String name){
		String value="";
		try {
			value=config.getString("appconfig."+name);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return value;
	}
}
