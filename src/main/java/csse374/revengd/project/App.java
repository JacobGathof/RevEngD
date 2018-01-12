package csse374.revengd.project;

import java.util.ArrayList;

import csse374.revengd.project.parsers.*;
import csse374.revengd.project.parserstrategies.*;
import csse374.revengd.project.builder.IBuilder;
import csse374.revengd.project.builder.PlantUMLBuilder;
import csse374.revengd.project.displayer.IDisplayer;
import csse374.revengd.project.displayer.PlantDisplayer;

public class App {
    public static void main(String[] args){
    	
    	Configuration config = new Configuration(args);
    	config.printArguments();

		//Create and decorate parser with its filters
		ArrayList<IParserStrategy> defaultUMLStrategies = new ArrayList<>();
		if(config.hasArg("basic")) {
			defaultUMLStrategies.add(new InstanceVariableParserStrategy());
			defaultUMLStrategies.add(new InheritanceParserStrategy());
			defaultUMLStrategies.add(new MethodParserStrategy());
			defaultUMLStrategies.add(new SuperClassParserStrategy());
		}
		if(config.hasArg("dependency")) {
			defaultUMLStrategies.add(new MethodSignatureDependencyParserStrategy());
			defaultUMLStrategies.add(new AssociationParserStrategy());
			defaultUMLStrategies.add(new LocalVariableDependencyParserStrategy());
		}

		IParser parser = new MasterParser(config, defaultUMLStrategies);
		parser = new NonRecursiveParserFilter(parser);
		parser = new PrivacyParserFilter(parser);
		parser = new PackageParserFilter(parser);

		IBuilder builder = new PlantUMLBuilder();
		IDisplayer displayer = new PlantDisplayer();

		Runner runner = new Runner(parser, builder, displayer, config);

		runner.run(args);
		
		
    }
}
