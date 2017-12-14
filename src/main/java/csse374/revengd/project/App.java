package csse374.revengd.project;

import javax.lang.model.SourceVersion;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;

public class App {
    public static void main(String[] args){
    	ArrayList<Class> classes = new ArrayList<>();
    	int depth = 0;
    	
        String path = args[0];
        int i = 1;
        if(i < args.length){
	        while(args[i].charAt(0) == '-'){
	        	if (args[i].length() == 1){
	        		System.out.println("Invalid input option");
	        		return;
	        	}
	        	switch (args[i].charAt(1)){
		        	case 'p':
		        		if(args[i].length() <= 2){
		        			System.out.println("Invalid input in access level command");
		        			return;
		        		}
		        		char level = args[i].charAt(2);
		        		if(level == 'r'){
		        			//TODO Private access
		        		} else if (level == 'o'){
		        			//TODO Protected Access
		        		} else if (level == 'u'){
		        			//TODO Public Access
		        		} else {
		        			System.out.println("Invalid input in access level command");
		        		} break;
		        	
		        	case 'r':
		        		depth = -1;
		        		if(args[i].length() > 2 || args[i].length() < 2){
		        			System.out.println("Invlaid input in recursive command");
		        		} else {
		        			i++;
		        			//TODO Make Recursive thing
		        			if (i < args[i].length()){
		        				try{
		        					depth = Integer.parseInt(args[i]);
		        				}
		        				catch (NumberFormatException e){
		        					depth = -1;
		        					continue;
		        				}
		        			}
		        			
		        			
		        		}
		        			//TODO Recursive thing
		        		
		        		break;
	        	}
	        	i++;
	        } 
	        try{
	        	
		        for (;i < args.length; i++){
		        	String thisClass = args[i];
		        	String testing = path + "\\" + thisClass;
					Class clazz = Class.forName(testing);
					classes.add(clazz);
		        }
	        }
	        catch(ClassNotFoundException c){
				System.out.println("No valid class " + path);
				return;
			}
        }
        /*
        String[] classes = new String[args.length - 1];
        for(int i = 1; i < args.length; i++){
        	classes[i-1] = args[i];
        }*/


		
			
			IParser parser = new SourceParser(depth);
			IBuilder builder = new PlantUMLBuilder();
			IDisplayer displayer = new PlantDisplayer();
			//String p = new File("src\\main\\java\\csse374\\revengd\\examples\\fixtures").getAbsolutePath();
			String p = new File(path).getAbsolutePath();
			//String what = System.getProperty("user.dir");
			//p = Paths.get(System.getProperty("user.dir"), "build", "classes", "main").toString();

			Runner runner = new Runner(parser, builder, displayer, classes);

			//Runner runner = new Runner(parser, builder, displayer, p);
			runner.run();
		
		
    }
}
