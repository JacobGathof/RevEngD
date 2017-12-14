package csse374.revengd.project;

import javax.lang.model.SourceVersion;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class App {
    public static void main(String[] args){
    	ArrayList<Class> classes = new ArrayList<>();
    	ArrayList<String> classNames = new ArrayList<>();

		Map<String, Class> filterMap = new HashMap<>();
		filterMap.put("-r", RecursiveParserFilter.class);

		IParser parser = new SourceParser();
		IBuilder builder = new PlantUMLBuilder();
		IDisplayer displayer = new PlantDisplayer();

    	for(String arg : args){
    		String[] parsedArg = arg.split("_");
    		if(filterMap.containsKey(parsedArg[0])){
    			try{
					parser = (IParser)filterMap.get(parsedArg[0]).getConstructors()[0].newInstance(parser, arg);
    			}
    			catch(Exception e){
    				System.out.println(e);
				}
			}
			else{
    			classNames.add(arg);
			}
		}


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
		runner.run();
		
		
    }
}
