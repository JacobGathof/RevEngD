package csse374.revengd.project;

import java.util.List;

import org.apache.commons.cli.*;

public class Configuration {
	
	private Options options;
	private CommandLine cmd;
	
	
	public void addParameters(String shortName, String longName, String description,
			 boolean required, boolean hasOptionalArgs, int numValues, String defaultValue) {
		
		JJOption option = new JJOption(shortName, longName, description, hasOptionalArgs, numValues, required, defaultValue);
		options.addOption(option);
	}
	
	public Configuration(String[] args) {
		
		options = new Options();
		
		addParameters("c",  "classes", 		"classes to analyze", 	true, 	true, -2, 	null);	
		addParameters("p",  "path", 		"path",					true, 	true,  1,	"");	
		addParameters("r",  "recursive",	"recursive depth", 		false, 	true,  1,	"5");	
		addParameters("pu", "public", 		"public scope", 		false, 	false, 1, 	null);	
		addParameters("pr", "private", 		"private scope", 		false, 	false, 1, 	null);	
		addParameters("po", "protected",	"protected scope", 		false, 	false, 1, 	null);	
		addParameters("sd", "sequence", 	"sequence diagram",		false, 	false, 1, 	null);	
		addParameters("d",  "dependency", 	"show dependencies",	false, 	false, 1, 	null);	
		addParameters("b",  "basic", 		"basic parsers",		false, 	false, 1, 	null);
		addParameters("e",  "expand", 		"don't expand JDK",		false, 	false, 1, 	null);
		addParameters("pa",  "package", 		"don't expand outside of package",		false, 	false, 1, 	null);

		//ShortName	/ LongName / Description /  Required? / optional args? / Allowed Argument #
		
		loadArguments(args);
	}
	
	private void loadArguments(String[] args) {
		
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
		
		System.out.println("=== Arguments ===");
		for(Option o : cmd.getOptions()) {
			System.out.println(o.getOpt() + " " + (o.getValuesList() == null ? "" : o.getValuesList().toString()));
		}
		System.out.println("=====");
	}

	public String getValue(String s) {
		String ss = cmd.getOptionValue(s);
		return ss;
	}
	
	public boolean hasArg(String s) {
		return cmd.hasOption(s);
	}
	
	
	/*
	 * Convenience more than anything. You don't need to know the argument name to get the classes or path
	 */
	public String[] getClasses() {
		return cmd.getOptionValues("c");
	}
	public String getPath() {
		return cmd.getOptionValue("p");
	}
	
	
	private class JJOption extends Option{
		
		private final String defaultValue;
		
		public JJOption(String opt, String longOpt, String description, 
						boolean hasArg, int numberOfArgs, boolean required, String defaultValue) throws IllegalArgumentException {
			super(opt, longOpt, hasArg, description);
			
			setOptionalArg(hasArg);
			if(hasArg) {
				setArgs(numberOfArgs);
			}
			setRequired(required);
			
			this.defaultValue = defaultValue;
		}
		
		@Override
		public List<String> getValuesList(){
			List<String> ls = super.getValuesList();
			if(ls.isEmpty()) {
				ls.add(defaultValue);
			}
			return ls;
		}
	}
	
}
