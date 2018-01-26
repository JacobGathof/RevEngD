package csse374.revengd.project.parsers.filters;

import csse374.revengd.project.parsers.IParser;
import csse374.revengd.project.umlobjects.IUMLObject;

import java.util.List;

public class SyntheticParserFilter implements IParserFilter{
    IParser parser;

    public SyntheticParserFilter(IParser inner){
        parser = inner;
    }

    @Override
    public List<IUMLObject> process(List<IUMLObject> objects) {
        for(int i = 0; i < objects.size(); i++){
            IUMLObject object = objects.get(i);
            if(object.toUML(true).contains("lambda$")){
                objects.remove(object);
            }
        }
        return objects;
    }

    @Override
    public List<IUMLObject> parse(String className) {
        return process(parser.parse(className));
    }
}
