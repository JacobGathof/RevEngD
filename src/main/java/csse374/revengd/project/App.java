package csse374.revengd.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args){
    	ArrayList<Class> classes = new ArrayList<>();
    	ArrayList<String> classNames = new ArrayList<>();

		IParser parser = new MasterParser();
		parser = new RecursiveParserFilter(parser);

		IBuilder builder = new PlantUMLBuilder();
		IDisplayer displayer = new PlantDisplayer();

    	try{
			for (String name : classNames){
				Class clazz = Class.forName(name);
				classes.add(clazz);
			}
		}
		catch(ClassNotFoundException c){
			System.out.println("Invalid class input");
			return;
		}

		Runner runner = new Runner(parser, builder, displayer, classes);

		//Runner runner = new Runner(parser, builder, displayer, p);
		runner.run(args);
		
		
    }
}
