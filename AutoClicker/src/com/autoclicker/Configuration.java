package com.autoclicker;

import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.input.KeyCode;
import javafx.collections.FXCollections;

@SuppressWarnings("restriction")
public class Configuration {
	private static Configuration config_instance = null;
	private static ObservableList<String> keyList = FXCollections.observableArrayList();
	private static Map<String, KeyCode> keyMap = new HashMap<String, KeyCode>();
	
	private Configuration() {
		keyList.addAll("F9","F10", "F11");
		keyMap.put("F9", KeyCode.F9);
		keyMap.put("F10", KeyCode.F10);
		keyMap.put("F11", KeyCode.F11);
	}
	
	public static Configuration getInstance() {
		if(config_instance == null) {
			config_instance = new Configuration();
		}
		
		return config_instance;
	}
	
	public static ObservableList<String> getKeyConfig(){
		return keyList;
	}
	
	public static KeyCode getKeyCode(String key) {
		return keyMap.get(key);
	}
	
}