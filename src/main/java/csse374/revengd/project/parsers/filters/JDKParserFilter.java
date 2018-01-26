package csse374.revengd.project.parsers.filters;

import java.util.List;

import csse374.revengd.project.parsers.IParser;
import csse374.revengd.project.parsers.filters.IParserFilter;
import csse374.revengd.project.umlobjects.IUMLObject;

public class JDKParserFilter implements IParserFilter {

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
			List<String> packages = obj.getPackage();
			for(String s : packages) {
				for(String ss : exclusions) {
					if(s.equals(ss)) {
						samePack = false;
					}
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
