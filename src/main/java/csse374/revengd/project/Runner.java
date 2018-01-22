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

    public Runner(IParser parser, IBuilder builder, IDisplayer displayer){
        this.parser = parser;
        this.builder = builder;
        this.displayer = displayer;
    }

    public void run(List<String> classes){
        List<IUMLObject> objects = new ArrayList<>();

        for(String c : classes){
            System.out.println("Parsing " + c);
            objects.addAll(parse(c));
        }
    	display(build(objects));
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
