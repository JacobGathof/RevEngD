package csse374.revengd.project;

import java.util.List;

public class NonRecursiveParserFilter implements IParserFilter{

    IParser parser;

    public NonRecursiveParserFilter(IParser parser){
        this.parser = parser;
    }

    public List<IUMLObject> process(List<IUMLObject> objects) {
        return objects;
    }

    public List<IUMLObject> parse(String path, String[] args) {
        return process(parser.parse(path, args));
    }
}
