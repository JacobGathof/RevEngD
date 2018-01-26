package csse374.revengd.project.parsers.filters;

import java.util.List;

import csse374.revengd.project.parsers.IParser;
import csse374.revengd.project.parsers.filters.IParserFilter;
import csse374.revengd.project.umlobjects.IUMLObject;

public class NonRecursiveParserFilter implements IParserFilter {

    IParser parser;

    public NonRecursiveParserFilter(IParser parser){
        this.parser = parser;
    }

    public List<IUMLObject> process(List<IUMLObject> objects) {
        return objects;
    }

    public List<IUMLObject> parse(String className) {
    	List<IUMLObject> sootObjects = parser.parse(className);
		for(int i = 0; i < sootObjects.size(); i++) {
			IUMLObject obj = sootObjects.get(i);
			if(className.contains(obj.getSootClass().getName())) {
				sootObjects.remove(obj);
				i--;
			}
		}
		return sootObjects;
    }
}
