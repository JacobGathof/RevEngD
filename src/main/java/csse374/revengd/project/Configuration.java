package csse374.revengd.project;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import csse374.revengd.project.builder.IBuilder;
import csse374.revengd.project.displayer.IDisplayer;
import csse374.revengd.project.displayer.PlantDisplayer;
import csse374.revengd.project.parsers.IParser;
import csse374.revengd.project.parsers.IParserDetector;
import csse374.revengd.project.parsers.IParserFilter;
import csse374.revengd.project.parserstrategies.IParserStrategy;
import csse374.revengd.project.parserstrategies.SequenceDiagramParserStrategy;
import csse374.revengd.project.parserstrategies.resolutioncommands.ISDContextResolutionCommand;


public class Configuration {
	
	private Map<String, List<String>> parameters;
	private final List<String> requiredParameters = Arrays.asList();
	private boolean isSequenceDiagram;
	private String defaultFile = "C:\\Users\\gathofjd\\git\\SoftDesignProject\\settings\\default.prop";
	
	public Configuration(String[] args) {
		
		isSequenceDiagram = false;
		parameters = new HashMap<String, List<String>>();		
		try {
			if(args.length == 0) {
				parseSettingsFile(Paths.get(defaultFile).toString());
			}else {
				parseSettingsFile(args[0]);
			}
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
		
		if(parameters.get("command") != null && !parameters.get("command").isEmpty()) {
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

	public IParser applyDetectors(IParser parser) {
		
		List<String> det = getValues("detectors");
		if(det == null) return parser;
		for(String detector : det){
			try {
				Class clazz = Class.forName(detector);
				if(IParserDetector.class.isAssignableFrom(clazz)){
					parser = (IParserFilter) clazz.getConstructor(IParser.class).newInstance(parser);
				}
				else{
					System.out.println("Given detector " + detector + " is not a valid detector");
					System.exit(0);
				}
			}
			catch (IllegalAccessException e){
				System.out.println("Could not access class " + detector);
			}
			catch (ClassNotFoundException e){
				System.out.println("Could not find class " + detector);
			}
			catch(Exception e){
				System.out.println("Could not instantiate class " + detector);
			}
		}
		return parser;
	}

	public IBuilder getBuilder() {
		
		IBuilder builder = null;
		try {
			Class clazz = Class.forName(getValue("builder"));
			if(IBuilder.class.isAssignableFrom(clazz)){
				if(isSequenceDiagram)
					builder = (IBuilder) clazz.newInstance();
				else
					builder = (IBuilder) clazz.getConstructor(boolean.class).newInstance(true);
			}
			else{
				System.out.println("Given builder " + clazz.getName() + " is not a valid builder");
				System.exit(0);
			}
		}
		catch(Exception e){
			System.out.println("Could not create builder " + getValue("builder"));
			System.exit(0);
		}
		return builder;
		
	}
	
	public IDisplayer getDisplayer() {
		return new PlantDisplayer();
	}
	
	public List<String> getFilters() {
		return getValues("filters");
	}
	
	public IParser applyFilters(IParser parser) {
		for(String filter : getValues("filters")){
			try {
				Class clazz = Class.forName(filter);
				if(IParserFilter.class.isAssignableFrom(clazz)){
					parser = (IParserFilter) clazz.getConstructor(IParser.class).newInstance(parser);
				}
				else{
					System.out.println("Given filter " + filter + " is not a valid filter");
					System.exit(0);
				}
			}
			catch (IllegalAccessException e){
				System.out.println("Could not access class " + filter);
			}
			catch (ClassNotFoundException e){
				System.out.println("Could not find class " + filter);
			}
			catch(Exception e){
				System.out.println("Could not instantiate class " + filter);
			}
		}
		return parser;
	}
	
	public List<IParserStrategy> getStrategies() {
		List<IParserStrategy> UMLStrategies = new ArrayList<>();
		
		if(isSequenceDiagram()){
			String command = getCommand();
			try {
				Class clazz = Class.forName(command);
				if (ISDContextResolutionCommand.class.isAssignableFrom(clazz)) {
					ISDContextResolutionCommand comm = (ISDContextResolutionCommand) clazz.newInstance();
					UMLStrategies.add(new SequenceDiagramParserStrategy("main", 2, comm));
				}else {
					System.out.println("Given strategy " + command + " is not a valid command");
					System.exit(0);
				}
			} catch (InstantiationException e) {
				System.out.println("Could not instantiate class " + command);
			} catch (IllegalAccessException e) {
				System.out.println("Could not access class " + command);
			} catch (ClassNotFoundException e) {
				System.out.println("Could not find class " + command);
			}
		}
		else {
			for (String strategy : getValues("strategies")) {
				try {
					Class clazz = Class.forName(strategy);
					if (IParserStrategy.class.isAssignableFrom(clazz)) {
						IParserStrategy strat = (IParserStrategy) clazz.newInstance();
						UMLStrategies.add(strat);
					}
					else{
						System.out.println("Given strategy " + strategy + " is not a valid strategy");
						System.exit(0);
					}
				} catch (InstantiationException e) {
					System.out.println("Could not instantiate class " + strategy);
				} catch (IllegalAccessException e) {
					System.out.println("Could not access class " + strategy);
				} catch (ClassNotFoundException e) {
					System.out.println("Could not find class " + strategy);
				}
			}
			
		}
		
		return UMLStrategies;
	}
	
	public String getPath() {
		return getValue("path");
	}

	public boolean isSequenceDiagram(){ return isSequenceDiagram; }
	
}
