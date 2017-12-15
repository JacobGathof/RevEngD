package csse374.revengd.project;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Runner {
	
    private IParser parser;
    private IBuilder builder;
    private IDisplayer displayer;
    private List<String> classes;

    public Runner(IParser parser, IBuilder builder, IDisplayer displayer, List<String> classes){
        this.parser = parser;
        this.builder = builder;
        this.displayer = displayer;
        this.classes = classes;
    }

    public void run(String[] args){
        List<IUMLObject> objects = new ArrayList<>();
        for(String c : classes){
            //File f = new File(c.getProtectionDomain().getCodeSource().getLocation().getPath());
            System.out.println("Parsing " + c);
            objects.addAll(parse(c, args));
        }
    	display(build(objects));
    	
        //display(build(parse(path)));
    }

    private List<IUMLObject> parse(String path, String[] args){
        return parser.parse(path, args);
    }

    private String build(List<IUMLObject> objects){
        return builder.build(objects);
    }

    public void display(String UML){
        displayer.display(UML);
    }
}
