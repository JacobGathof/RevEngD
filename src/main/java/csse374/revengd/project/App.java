package csse374.revengd.project;

import java.io.File;
import java.nio.file.Paths;

public class App {
    public static void main(String[] args){
        String path = args[0];
        int i = 1;
        if(i >= args.length){
	        if(args[i].charAt(0) == '-'){
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
		        		if(args[i].length() > 3 || args[i].length() < 3){
		        			System.out.println("Invlaid input in recursive command");
		        		}
		        		char yOrN = args[i].charAt(2);
		        		if (yOrN == 'y'){
		        			//TODO Recursive thing
		        		} else if (yOrN == 'n'){
		        			//TODO Not Recursively.
		        		} else {
		        			System.out.println("Invalid input in recursive command");
		        		}
	        	}
	        }
        }
        /*
        String[] classes = new String[args.length - 1];
        for(int i = 1; i < args.length; i++){
        	classes[i-1] = args[i];
        }*/
        
        
        
        IParser parser = new SourceParser();
        IBuilder builder = new PlantUMLBuilder();
        IDisplayer displayer = new PlantDisplayer();
        //String p = new File("src\\main\\java\\csse374\\revengd\\examples\\fixtures").getAbsolutePath();
        String p = new File(path).getAbsolutePath();
        //String what = System.getProperty("user.dir");
        //p = Paths.get(System.getProperty("user.dir"), "build", "classes", "main").toString();
        		
        Runner runner = new Runner(parser, builder, displayer, path);

        //Runner runner = new Runner(parser, builder, displayer, p);
        runner.run();
    }
}
