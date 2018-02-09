package csse374.revengd.project.parsers.filters;

import java.util.List;

import com.google.common.collect.Lists;

import csse374.revengd.project.parsers.IParser;
import csse374.revengd.project.parsers.filters.IParserFilter;
import csse374.revengd.project.umlobjects.IUMLObject;
import csse374.revengd.project.umlobjects.PackageHelper;

public class BlacklistParserFilter implements IParserFilter {
	IParser parser;
	List<String> blacklist;
	List<String> whitelist;

	public BlacklistParserFilter(IParser parser, List<String> bl, List<String> wl) {
		this.parser = parser;
		this.blacklist = bl;
		this.whitelist = wl;
	}

	public List<IUMLObject> process(List<IUMLObject> objects) {
		return objects;
	}

	public List<IUMLObject> parse(String className) {
		List<IUMLObject> sootObjects = parser.parse(className);

		for (int i = 0; i < sootObjects.size(); i++) {
			IUMLObject obj = sootObjects.get(i);
			
			boolean samePack = true;
			
			List<String> packages = obj.getPackage();
			
			if (blacklist != null) {
				for(String s : blacklist) {
					for(String ss : packages) {
						if(ss.contains(s)) {
							samePack = false;
						}
					}
				}
			}
			if (whitelist != null) {
				for (String ss : whitelist) {
					if (obj.toUML(true).contains(ss)) {
						samePack = true;
					}
				}
			}
			if (!samePack || isPrimitive(obj)) {
				sootObjects.remove(obj);
				i--;
			}
		}
		return sootObjects;
	}
    
    public boolean isPrimitive(IUMLObject obj) {
    	return obj.toUML(true).equals("class boolean") || 
    			obj.toUML(true).equals("class float") || 
    			obj.toUML(true).equals("class double") || 
    			obj.toUML(true).equals("class char") || 
    			obj.toUML(true).equals("class *") || 
    			obj.toUML(true).equals("class int") ||
    			obj.toUML(true).equals("class long") || 
    			obj.toUML(true).equals("class byte");
    }
}
