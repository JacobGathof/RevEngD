package csse374.revengd.project;

import java.util.ArrayList;
import java.util.List;


public class Configuration {
	
	private List<Option> options;
	
	public void addParameters(String shortName, String longName, String description,
			 boolean required, String defaultValue) {
		
		Option option = new Option(shortName, longName, description, required, defaultValue);
		options.add(option);
	}
	
	public Configuration(String[] args) {
		
		options = new ArrayList<Option>();
		
		addParameters("c",  "classes", 		"classes to analyze", 	true,	null);	
		addParameters("p",  "path", 		"path",					true, 	"");	
		addParameters("r",  "recursive",	"recursive depth", 		false, 	"5");	
		addParameters("pu", "public", 		"public scope", 		false, 	null);	
		addParameters("pr", "private", 		"private scope", 		false, 	null);	
		addParameters("po", "protected",	"protected scope", 		false, 	null);	
		addParameters("sd", "sequence", 	"sequence diagram",		false, 	null);	
		addParameters("d",  "dependency", 	"show dependencies",	false, 	null);	
		addParameters("b",  "basic", 		"basic parsers",		false, 	null);
		addParameters("e",  "expand", 		"don't expand JDK",		false, 	null);
		addParameters("pa",  "package", 	"don't expand outside of package",	false, null);

		//ShortName	/ LongName / Description /  Required? / default
		
		loadArguments(args);
	}
	
	private void loadArguments(String[] args) {
		
		Option currentOption = null;
		
		for(int i = 0; i < args.length; i++) {
			String arg = args[i];
			if(arg.charAt(0) == '-') {
				if(arg.charAt(1) == '-') {
					currentOption = findOption(arg.substring(2));
				}else {
					currentOption = findOption(arg.substring(1));
				}
				if(currentOption!=null) {
					currentOption.setActive();
				}else {
					System.out.println("Unknown argument : " + arg);
				}
			}else {
				if(currentOption != null) {
					currentOption.addValue(arg);
				}
			}
		}
		
		fillDefaults();
		List<String> missing = checkRequirements();
		
		if(!missing.isEmpty()) {
			printHelp(missing);
	        System.exit(0);
		}
	}
	
	
	public void fillDefaults() {
		for(Option o : options) {
			if(o.isActive() && o.values.isEmpty() && o.defaultValue != null) {
				o.addValue(o.defaultValue);
			}
		}
	}
	
	
	public void printArguments() {
		
		System.out.println("=== Arguments ===");
		for(Option o : options) {
			if(o.isActive()) {
				System.out.println(o);
			}
		}
		System.out.println("=====");
	}
	

	public String[] getValues(String s) {
		Option o = findOption(s);
		if(o == null  || !o.active)
			return null;
		return o.getValues();
	}
	
	
	public String getValue(String s) {
		Option o = findOption(s);
		if(o == null  || !o.active)
			return null;
		return o.getValues()[0];
	}
	
	
	public boolean hasArg(String s) {
		Option o = findOption(s);
		if(o == null)
			return false;
		return o.isActive();
	}
	
	
	
	public String[] getClasses() {
		return getValues("c");
	}
	
	public String getPath() {
		return getValue("p");
	}
	
	
	public Option findOption(String s) {
		for(Option o : options) {
			if(o.equals(s)) {
				return o;
			}
		}
		return null;
	}
	
	public List<String> checkRequirements() {
		List<String> missing = new ArrayList<String>();
		for(int i = 0; i < options.size(); i++) {
			Option o = options.get(i);
			if(!o.isActive() && o.required) {
				missing.add(o.shortName);
			}
		}
		return missing;
	}
	
	public void printHelp(List<String> missingRequirements) {
		System.out.println("Missing required arguments :" + missingRequirements.toString());
		for(int i = 0; i < options.size(); i++) {
			Option o = options.get(i);
			System.out.println("\t" + "-"+o.shortName + " " + "\t--" + o.longName + " \t " + o.description);
		}
	}
	
	
	
	class Option{
		public final String shortName;
		public final String longName;
		public final String description;
		public final boolean required;
		public final String defaultValue;
		
		private List<String> values;
		private boolean active;
		
		public Option(String sn, String ln, String desc, boolean req, String defaultValue) {
			this.shortName = sn;
			this.longName = ln;
			this.description = desc;
			this.required = req;
			this.defaultValue = defaultValue;
			this.values = new ArrayList<String>();
		}
		
		public String[] getValues() {
			return values.toArray(new String[] {});
		}
		
		public String getValue() {
			return values.get(0);
		}
		
		public boolean equals(String s) {
			return (s.equals(shortName) || s.equals(longName));
		}
		
		public void addValue(String s) {
			values.add(s);
		}
		
		public void setActive() {
			this.active = true;
		}
		
		public boolean isActive() {
			return this.active;
		}
		
		public String toString() {
			return shortName + " " + longName + " " + values.toString();
		}
	}
	
}
