package csse374.revengd.project;

import java.util.ArrayList;
import java.util.List;

import csse374.revengd.project.builder.SequenceDiagramBuilder;
import csse374.revengd.project.parsers.*;
import csse374.revengd.project.parserstrategies.*;
import csse374.revengd.project.builder.IBuilder;
import csse374.revengd.project.builder.PlantUMLBuilder;
import csse374.revengd.project.displayer.IDisplayer;
import csse374.revengd.project.displayer.PlantDisplayer;
import csse374.revengd.project.parserstrategies.resolutioncommands.ISDContextResolutionCommand;

public class App {
    public static void main(String[] args){
    	
    	Configuration config = new Configuration(args);

		List<String> strategies = config.getStrategies();
		List<String> filters = config.getFilters();
		List<String> detectors = config.getDetectors();

		List<IParserStrategy> UMLStrategies = new ArrayList<>();

		if(config.isSequenceDiagram()){
			String command = config.getCommand();
			try {
				Class clazz = Class.forName(command);
				if (clazz.isAssignableFrom(ISDContextResolutionCommand.class)) {
					ISDContextResolutionCommand comm = (ISDContextResolutionCommand) clazz.newInstance();
					UMLStrategies.add(new SequenceDiagramParserStrategy("main", 2, comm));
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
			for (String strategy : strategies) {
				try {
					Class clazz = Class.forName(strategy);
					if (clazz.isAssignableFrom(IParserStrategy.class)) {
						IParserStrategy strat = (IParserStrategy) clazz.newInstance();
						UMLStrategies.add(strat);
					}
					else{
						System.out.println("Given strategy " + strategy + " is not a valid strategy");
						return;
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

		IParser parser = new MasterParser(config.getPath(), UMLStrategies);

		for(String filter : filters){
			try {
				Class clazz = Class.forName(filter);
				if(clazz.isAssignableFrom(IParserFilter.class)){
					parser = (IParserFilter) clazz.getConstructor(IParser.class).newInstance(parser);
				}
				else{
					System.out.println("Given filter " + filter + " is not a valid filter");
					return;
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

		for(String detector : detectors){
			try {
				Class clazz = Class.forName(detector);
				if(clazz.isAssignableFrom(IParserDetector.class)){
					parser = (IParserFilter) clazz.getConstructor(IParser.class).newInstance(parser);
				}
				else{
					System.out.println("Given detector " + detector + " is not a valid detector");
					return;
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

		IBuilder builder;
		try {
			Class clazz = Class.forName(config.getBuilders().get(0));
			if(clazz.isAssignableFrom(IBuilder.class)){
				builder = (IBuilder) clazz.getConstructor(boolean.class).newInstance(true);
			}
			else{
				System.out.println("Given builder " + clazz.getName() + " is not a valid builder");
				return;
			}
		}
		catch(Exception e){
			System.out.println("Could not create builder " + config.getBuilders().get(0));
			return;
		}

		IDisplayer displayer = new PlantDisplayer();

		Runner runner = new Runner(parser, builder, displayer);

		runner.run(config.getClasses());
    }
}
