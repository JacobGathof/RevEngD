package csse374.revengd.project;

import java.util.List;

public class RecursiveParserFilter implements IParserFilter{

    int depth;
    IParser parser;

    public RecursiveParserFilter(IParser parser, String arg){
        String[] splitArg = arg.split("_");
        int depth = Integer.parseInt(splitArg[1]);
        this.parser = parser;
        this.depth = depth;
    }

    public List<IUMLObject> process(List<IUMLObject> objects) {
        return objects;
    }

    public List<IUMLObject> parse(String path) {
        return process(parser.parse(path));
    }
}
