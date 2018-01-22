package csse374.revengd.project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class Configuration {
	
	private Map<String, List<String>> parameters;
	private final List<String> requiredParameters = Arrays.asList();
	private boolean isSequenceDiagram;
	
	public Configuration(String[] args) {
		
		isSequenceDiagram = false;
		parameters = new HashMap<String, List<String>>();		
		try {
			parseSettingsFile(args[0]);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void parseSettingsFile(String filename) throws FileNotFoundException {
		
		Scanner scan = new Scanner(new File(filename));
		while(scan.hasNextLine()) {
			String line = scan.nextLine();
			if(line.length() < 2)
				continue;
			String args[] = line.split("=");
			if(args.length != 2) 
				continue;
			
			String key = args[0].trim().toLowerCase();
			String vals[] = args[1].trim().replace(" ", "").split(",");	
			parameters.put(key, Arrays.asList(vals));
		}
		
		if(parameters.get("commands") != null && !parameters.get("commands").isEmpty()) {
			isSequenceDiagram = true;
		}
		
		printParameters();
		
		if(!checkRequiredArguments()) {
			System.exit(0);
		}
		
		scan.close();
	}
	
	private void printParameters() {
		for(String s : parameters.keySet()) {
			System.out.println("" + s + " -- " + parameters.get(s).toString());
		}
	}
	
	
	private boolean checkRequiredArguments() {
		if(parameters.keySet().containsAll(requiredParameters))
			return true;
		List<String> missing = new ArrayList<>();
		for(String s : requiredParameters) {
			if(!parameters.containsKey(s)) {
				missing.add(s);
			}
		}
		System.err.println("Missing : " + missing.toString());
		return false;
	}

	public List<String> getValues(String s) {
		return parameters.get(s);
	}
	
	public String getValue(String s) {
		List<String> ls = getValues(s);
		if(ls==null) 
			return null;
		return ls.get(0);
	}
	
	public String getCommand() {
		return getValue("command");
	}
	
	public List<String> getClasses() {
		return getValues("classes");
	}

	public List<String> getDetectors() {
		return getValues("detectors");
	}

	public List<String> getBuilders() {
		if(isSequenceDiagram) {
			return Arrays.asList("PlantSequenceBuilder");
		}
		return Arrays.asList("PlantUMLBuilder");
	}
	
	public List<String> getDisplayers() {
		if(isSequenceDiagram) {
			return Arrays.asList("PlantSequenceBuilder");
		}
		return Arrays.asList("PlantUMLBuilder");
	}
	
	public List<String> getFilters() {
		return getValues("filters");
	}
	
	public List<String> getStrategies() {
		return getValues("strategies");
	}
	
	public String getPath() {
		return getValue("path");
	}

	public boolean isSequenceDiagram(){ return isSequenceDiagram; }
	
}
