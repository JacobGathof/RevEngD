package csse374.revengd.project;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Runner {
	
    private IParser parser;
    private IBuilder builder;
    private IDisplayer displayer;
    private List<Class> classes;

    public Runner(IParser parser, IBuilder builder, IDisplayer displayer, List<Class> classes){
        this.parser = parser;
        this.builder = builder;
        this.displayer = displayer;
        this.classes = classes;
    }

    public void run(){
        List<IUMLObject> objects = new ArrayList<>();
        for(Class c : classes){
            //File f = new File(c.getProtectionDomain().getCodeSource().getLocation().getPath());
            System.out.println("Parsing " + c.getName());
            objects.addAll(parse(c.getName()));
        }
    	System.out.println(build(objects));
    	
        //display(build(parse(path)));
    }

    private List<IUMLObject> parse(String path){
        return parser.parse(path);
    }

    private String build(List<IUMLObject> objects){
        return builder.build(objects);
    }

    public void display(String UML){
        displayer.display(UML);
    }
}
