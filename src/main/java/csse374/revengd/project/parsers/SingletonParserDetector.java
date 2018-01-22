package csse374.revengd.project.parsers;

import csse374.revengd.project.umlobjects.IUMLObject;

import java.util.List;

public class SingletonParserDetector implements IParserDetector{
    IParser parser;

    public SingletonParserDetector(IParser parser){
        this.parser = parser;
    }

    @Override
    public List<IUMLObject> detect(List<IUMLObject> objects) {
        return objects;
    }

    @Override
    public List<IUMLObject> parse(String className) {
        return detect(parser.parse(className));
    }
}
