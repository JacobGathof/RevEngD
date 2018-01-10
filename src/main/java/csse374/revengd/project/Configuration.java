package csse374.revengd.project;

import java.util.ArrayList;
import org.apache.commons.cli.*;
import com.beust.jcommander.internal.Lists;

public class Configuration {
	
	private Options options;
	private CommandLine cmd;
	private String path;
	private ArrayList<ArgumentParameters> arguments;
	
	/*
	 * Does not currently do anything, as the constructor immediately calls loadArguments();
	 */
	public void addParameters(String shortName, String longName, 
			boolean hasOptionalArgs, String description, boolean required) {
		arguments.add(new ArgumentParameters(shortName, longName, hasOptionalArgs, description, required));
	}
	
	
	public Configuration(String[] args) {
		
		path = args[0];
		String[] newArgs = new String[args.length-1];
		for(int i = 0; i < newArgs.length; i++) {
			newArgs[i] = args[i+1];
		}
		
		arguments = (ArrayList<ArgumentParameters>) Lists.newArrayList(
				
			//ShortName	/ LongName / HasOptionalArgs / Description / Required 
			new ArgumentParameters("r", 	"recursive", 	true, 	"recursive depth", 		false),
			new ArgumentParameters("pu", 	"public", 		false, 	"public scope", 		false),
			new ArgumentParameters("pr", 	"private", 		false, 	"private scope", 		false),	
			new ArgumentParameters("po", 	"protected", 	false, 	"protected scope", 		false),	
			new ArgumentParameters("sd", 	"sequence", 	false, 	"sequence diagram",		false), 
			new ArgumentParameters("e", 	"expand", 		false, 	"expand",				false)	
			
		);
		
		options = new Options();
		loadArguments(arguments, newArgs);
		
	}
	
	private void loadArguments(ArrayList<ArgumentParameters> argumentList, String[] args) {
		
		for(ArgumentParameters ap : argumentList) {
			Option option = new Option(ap.shortName, ap.longName, ap.hasArg, ap.desc);
			option.setOptionalArg(ap.hasArg);
			option.setRequired(ap.required);
			options.addOption(option);
		}
		
		CommandLineParser parser = new DefaultParser();
	    HelpFormatter formatter = new HelpFormatter();

        try {
            cmd = parser.parse(options, args, true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            formatter.printHelp("cmd-arguments", options);
            System.exit(0);
        }
	}
	
	public void printArguments() {
		
		System.out.println("Path: " + path);
		System.out.println("=== Arguments ===");
		for(Option o : cmd.getOptions()) {
			System.out.println(o.toString());
		}
		System.out.println("=== Additional Arguments ===");
		for(String s : cmd.getArgs()) {
			System.out.println(s);
		}
		System.out.println("=====");
	}

	public String getValue(String s) {
		return cmd.getOptionValue(s);
	}
	
	public String[] getClasses() {
		return cmd.getArgs();
	}
	public String getPath() {
		return path;
	}
	
	class ArgumentParameters {		
		public final String shortName;
		public final String longName;
		public final boolean hasArg;
		public final String desc;
		public final boolean required;
		
		public ArgumentParameters(String sn, String ln, boolean ha, String d, boolean r) {
			shortName = sn;
			longName = ln;
			hasArg = ha;
			desc = d;
			required = r;
		}
	}
	
}
