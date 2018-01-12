package csse374.revengd.project.parsers;

import java.util.Arrays;
import java.util.List;

import csse374.revengd.project.Configuration;
import csse374.revengd.project.umlobjects.IUMLObject;

public class JDKParserFilter implements IParserFilter{

    IParser parser;

    public JDKParserFilter(IParser parser){
        this.parser = parser;
    }

    public List<IUMLObject> process(List<IUMLObject> objects) {
        return objects;
    }

    public List<IUMLObject> parse(String className) {
    	List<IUMLObject> sootObjects = parser.parse(className);

		for(int i = 0; i < sootObjects.size(); i++) {
			IUMLObject obj = sootObjects.get(i);
			String[] exclusions = new String[] {"java", "javax", "sun", "lang"};
			boolean samePack = true;
			for(String s : exclusions) {
				if(obj.toUML(true).contains(s)){
					samePack = false;
				}
			}
			if(!samePack) {
				sootObjects.remove(obj);
				i--;
			}
		}
		return sootObjects;
    }
}
