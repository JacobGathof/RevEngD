package csse374.revengd.project;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import csse374.revengd.project.builder.IBuilder;
import csse374.revengd.project.displayer.IDisplayer;
import csse374.revengd.project.parsers.filters.BlacklistParserFilter;
import csse374.revengd.project.parsers.IParser;
import csse374.revengd.project.parsers.detectors.IParserDetector;
import csse374.revengd.project.parsers.filters.IParserFilter;
import csse374.revengd.project.parsers.filters.RepeatParserFilter;
import csse374.revengd.project.parsers.filters.SyntheticParserFilter;
import csse374.revengd.project.parserstrategies.IParserStrategy;
import csse374.revengd.project.parserstrategies.SequenceDiagramParserStrategy;
import csse374.revengd.project.parserstrategies.resolutioncommands.ISDContextResolutionCommand;


public class Configuration {
	
	private Map<String, List<String>> parameters;
	private boolean isSequenceDiagram;

	private String defaultFile = Paths.get(System.getProperty("user.dir"), "settings", "default.prop").toString();

	public Configuration(String[] args) {
		
		isSequenceDiagram = false;
		parameters = new HashMap<String, List<String>>();		
		try {
			parseDefaultSettings();
			if(args.length > 0) {
				parseSettingsFile(args[0]);
			}
			if(args.length > 1) {
				parseComandLine(args);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		if(parameters.get("command") != null) {
			isSequenceDiagram = true;
		}
	}
	
	private void parseComandLine(String[] args) {
		for(int i = 1; i < args.length; i++) {
			String[] arg = args[i].split("=");
			String key = arg[0].trim().toLowerCase();
			String vals[] = arg[1].trim().split(",");
			parameters.put(key, Arrays.asList(vals));
		}
	}
	
	private void parseDefaultSettings() throws FileNotFoundException {
		parseSettingsFile(defaultFile);
	}
	
	private void parseSettingsFile(String filename) throws FileNotFoundException {
		
		Scanner scan = new Scanner(new File(filename));
		while(scan.hasNextLine()) {
			String line = scan.nextLine();
			if(line.length() < 2)
				continue;
			String args[] = line.split("=");
			if(args.length == 1) { 
				parameters.put(args[0].trim().toLowerCase(), null);
				continue;
			}
			
			String key = args[0].trim().toLowerCase();
			String vals[] = args[1].trim().split(",");	
			parameters.put(key, Arrays.asList(vals));
		}
		
		printParameters();
		
		scan.close();
	}
	
	private void printParameters() {
		for(String s : parameters.keySet()) {
			System.out.println("" + s + " -- " + parameters.get(s));
		}
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
	
	public List<String> getCommands() {
		return getValues("command");
	}

	public String getAggregate(){
		return getValue("aggregate");
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
					parser = (IParserDetector) clazz.getConstructor(IParser.class).newInstance(parser);
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
		parser = new RepeatParserFilter(parser);
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
		IDisplayer displayer = null;
		try {
			Class clazz = Class.forName(getValue("displayer"));
			if(IDisplayer.class.isAssignableFrom(clazz)){
				displayer = (IDisplayer) clazz.newInstance();
			}
			else{
				System.out.println("Given displayer " + clazz.getName() + " is not a valid displayer");
				System.exit(0);
			}
		}
		catch(Exception e){
			System.out.println("Could not create displayer " + getValue("displayer"));
			System.exit(0);
		}
		return displayer;
	}
	
	public List<String> getFilters() {
		return getValues("filters");
	}

	public boolean displaySynthetic(){
		return Boolean.parseBoolean(getValue("synthetic"));
	}

	public int getDepth(){
		return Integer.parseInt(getValue("depth"));
	}
	
	public IParser applyFilters(IParser parser) {
		parser = new RepeatParserFilter(parser);

		List<String> bl = getValues("blacklist");
		List<String> wl = getValues("whitelist");
		parser = new BlacklistParserFilter(parser, bl, wl);
		
		if(!displaySynthetic()){
			parser = new SyntheticParserFilter(parser);
		}

		List<String> filt = getValues("filters");
		if(filt == null) return parser;
		
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
			List<String> commands = getCommands();
			if(commands.size() == 1) {
				String command = commands.get(0);
				try {
					Class clazz = Class.forName(command);
					if (ISDContextResolutionCommand.class.isAssignableFrom(clazz)) {
						ISDContextResolutionCommand comm = (ISDContextResolutionCommand) clazz.newInstance();
						UMLStrategies.add(new SequenceDiagramParserStrategy("main", getDepth(), comm));
					} else {
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
			else if(commands.size() > 1 && getAggregate() != null){
				List<ISDContextResolutionCommand> instantiatedCommands = new ArrayList<>();
				for(String command : commands) {
					try {
						Class clazz = Class.forName(command);
						if (ISDContextResolutionCommand.class.isAssignableFrom(clazz)) {
							ISDContextResolutionCommand comm = (ISDContextResolutionCommand) clazz.newInstance();
							instantiatedCommands.add(comm);
						} else {
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
				try {
					Class clazz = Class.forName(getAggregate());
					if (ISDContextResolutionCommand.class.isAssignableFrom(clazz)) {
						ISDContextResolutionCommand agg = (ISDContextResolutionCommand) clazz.getConstructor(List.class).newInstance(instantiatedCommands);
						UMLStrategies.add(new SequenceDiagramParserStrategy("main", 2, agg));
					} else {
						System.out.println("Given command " + getAggregate() + " is not a valid command");
						System.exit(0);
					}
				} catch (Exception e) {
					System.out.println("Could not instantiate class " + getAggregate());
				}
			}
			else{
				System.out.println("Given sequence diagram parameters are not valid");
				System.exit(0);
			}
		}
		else {
			
			List<String> str = getValues("strategies");
			if(str == null) return UMLStrategies;
			
			for (String strategy : str) {
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
