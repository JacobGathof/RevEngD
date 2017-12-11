package csse374.revengd.project;

import java.io.File;
import java.nio.file.Paths;

public class App {
    public static void main(String[] args){
        String path = args[0];
        
        IParser parser = new SourceParser();
        IBuilder builder = new PlantUMLBuilder();
        IDisplayer displayer = new PlantDisplayer();
        String p = new File("src\\main\\java\\csse374\\revengd\\examples\\fixtures").getAbsolutePath();
        p = Paths.get(System.getProperty("user.dir"), "build", "classes", "main").toString();
        
        Runner runner = new Runner(parser, builder, displayer, p);
        runner.run();
    }
}
