package csse374.revengd.project.parsers;

import java.util.List;

import com.google.common.collect.Lists;

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
			String cl = obj.getSootClass().getName();
			
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
					if (cl.equals(ss)) {
						samePack = true;
					}
				}
			}
			if (!samePack) {
				sootObjects.remove(obj);
				i--;
			}
		}
		return sootObjects;
	}
}
