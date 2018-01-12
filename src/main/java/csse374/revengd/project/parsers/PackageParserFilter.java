package csse374.revengd.project.parsers;

import java.util.Arrays;
import java.util.List;

import csse374.revengd.project.Configuration;
import csse374.revengd.project.umlobjects.IUMLObject;

public class PackageParserFilter implements IParserFilter{

    IParser parser;

    public PackageParserFilter(IParser parser){
        this.parser = parser;
    }

    public List<IUMLObject> process(List<IUMLObject> objects) {
        return objects;
    }

    public List<IUMLObject> parse(String className) {
    	List<IUMLObject> sootObjects = parser.parse(className);
		for(int i = 0; i < sootObjects.size(); i++) {
			IUMLObject obj = sootObjects.get(i);
			String pack = className.split("\\.")[0];
			boolean samePack = true;
			List<String> packages = obj.getPackage();
			for(String s : packages) {
				if(!s.equals(pack)) {
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
