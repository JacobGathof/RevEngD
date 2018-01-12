package csse374.revengd.project;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import csse374.revengd.project.builder.IBuilder;
import csse374.revengd.project.displayer.IDisplayer;
import csse374.revengd.project.parsers.IParser;
import csse374.revengd.project.umlobjects.IUMLObject;

public class Runner {
	
    private IParser parser;
    private IBuilder builder;
    private IDisplayer displayer;
    private Configuration config;

    public Runner(IParser parser, IBuilder builder, IDisplayer displayer, Configuration config){
        this.parser = parser;
        this.builder = builder;
        this.displayer = displayer;
        this.config = config;
    }

    public void run(String[] args){
        List<IUMLObject> objects = new ArrayList<>();
        for(String c : config.getClasses()){
            //File f = new File(c.getProtectionDomain().getCodeSource().getLocation().getPath());
            System.out.println("Parsing " + c);
            objects.addAll(parse(c, config));
        }
    	display(build(objects));
    	
        //display(build(parse(path)));
    }

    private List<IUMLObject> parse(String path, Configuration config){
        return parser.parse(path, config);
    }

    private String build(List<IUMLObject> objects){
        return builder.build(objects);
    }

    public void display(String UML){
        displayer.display(UML);
    }
}
