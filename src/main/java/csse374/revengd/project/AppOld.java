package csse374.revengd.project;

import java.util.ArrayList;

import csse374.revengd.project.builder.SequenceDiagramBuilder;
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

		IBuilder builder;

		//Create and decorate parser with its filters
		ArrayList<IParserStrategy> defaultUMLStrategies = new ArrayList<>();
		if(config.hasArg("sd")) {
			builder = new SequenceDiagramBuilder();
			defaultUMLStrategies.add(new SequenceDiagramParserStrategy("main", 5));
		}
		else{
			builder = new PlantUMLBuilder(true);
			if (config.hasArg("basic")) {
				defaultUMLStrategies.add(new InstanceVariableParserStrategy());
				defaultUMLStrategies.add(new InheritanceParserStrategy());
				defaultUMLStrategies.add(new MethodParserStrategy());
				defaultUMLStrategies.add(new SuperClassParserStrategy());
			}
			if (config.hasArg("dependency")) {
				defaultUMLStrategies.add(new MethodSignatureDependencyParserStrategy());
				defaultUMLStrategies.add(new AssociationParserStrategy());
				defaultUMLStrategies.add(new LocalVariableDependencyParserStrategy());
			}
		}

		IParser parser = new MasterParser(config.getPath(), defaultUMLStrategies);

		if(!config.hasArg("r")) {
			parser = new NonRecursiveParserFilter(parser);
		}

		if(config.hasArg("pr")){
			parser = new PrivacyParserFilter(parser, -1);
		}
		else if(config.hasArg("po")){
			parser = new PrivacyParserFilter(parser, 0);
		}
		else if(config.hasArg("pu")){
			parser = new PrivacyParserFilter(parser, 1);
		}

		if(config.hasArg("pa")) {
			//builder = new PlantUMLBuilder(false);
			parser = new PackageParserFilter(parser);
		}

		if(!config.hasArg("e")) {
			parser = new JDKParserFilter(parser);
		}

		IDisplayer displayer = new PlantDisplayer();

		Runner runner = new Runner(parser, builder, displayer);

		runner.run(config.getClasses());
		
		
    }
}
