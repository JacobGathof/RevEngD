package csse374.revengd.project;

import java.util.ArrayList;

import csse374.revengd.project.parserstrategies.*;
import csse374.revengd.project.builder.IBuilder;
import csse374.revengd.project.builder.PlantUMLBuilder;
import csse374.revengd.project.displayer.IDisplayer;
import csse374.revengd.project.displayer.PlantDisplayer;
import csse374.revengd.project.parsers.IParser;
import csse374.revengd.project.parsers.MasterParser;
import csse374.revengd.project.parsers.NonRecursiveParserFilter;
import csse374.revengd.project.parsers.PrivacyParserFilter;

public class App {
    public static void main(String[] args){
    	ArrayList<String> classNames = new ArrayList<>();

		//Create and decorate parser with its filters
		ArrayList<IParserStrategy> defaultUMLStrategies = new ArrayList<>();
		defaultUMLStrategies.add(new InstanceVariableParserStrategy());
		defaultUMLStrategies.add(new InheritanceParserStrategy());
		defaultUMLStrategies.add(new MethodParserStrategy());
		defaultUMLStrategies.add(new SuperClassParserStrategy());
		defaultUMLStrategies.add(new DependencyParserStrategy());

		IParser parser = new MasterParser(args[0], defaultUMLStrategies);
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
