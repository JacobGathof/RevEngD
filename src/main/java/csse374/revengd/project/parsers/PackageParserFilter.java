package csse374.revengd.project.parsers;

import java.util.Arrays;
import java.util.List;

import com.beust.jcommander.internal.Lists;

import csse374.revengd.project.Configuration;
import csse374.revengd.project.umlobjects.IUMLObject;
import csse374.revengd.project.umlobjects.PackageHelper;

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
			boolean samePack = true;
			IUMLObject obj = sootObjects.get(i);
			List<String> pack = PackageHelper.getPackageNames(className);
			List<String> packages = obj.getPackage();
			for(String s : pack) {
				for(String ss : packages) {
					if(!ss.contains(s) && !s.contains(ss)) {
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
