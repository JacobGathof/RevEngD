package csse374.revengd.project;


import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

public class App {
    public static void main(String[] args){
    	ArrayList<String> classNames = new ArrayList<>();

		//Create and decorate parser with its filters
		IParser parser = new MasterParser(args[0]);
		parser = new NonRecursiveParserFilter(parser);
		parser = new PrivacyParserFilter(parser);

		IBuilder builder = new PlantUMLBuilder();
		IDisplayer displayer = new PlantDisplayer();

		for(String arg : args){
			if(!arg.startsWith("-") && arg != args[0]){
				classNames.add(arg);
			}
		}

		Runner runner = new Runner(parser, builder, displayer, classNames);

		runner.run(args);
		
		
    }
}
