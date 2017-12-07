package csse374.revengd.project;

import java.util.List;

public class Runner {
    private IParser parser;

    private IBuilder builder;

    private IDisplayer displayer;

    String path;

    public Runner(IParser parser, IBuilder builder, IDisplayer displayer, String path){
        this.parser = parser;
        this.builder = builder;
        this.displayer = displayer;
        this.path = path;
    }

    public void run(){
        display(build(parse(path)));
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
