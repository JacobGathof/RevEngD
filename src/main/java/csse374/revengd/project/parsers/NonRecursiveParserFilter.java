package csse374.revengd.project.parsers;

import java.util.Arrays;
import java.util.List;

import csse374.revengd.project.umlobjects.IUMLObject;

public class NonRecursiveParserFilter implements IParserFilter{

    IParser parser;

    public NonRecursiveParserFilter(IParser parser){
        this.parser = parser;
    }

    public List<IUMLObject> process(List<IUMLObject> objects) {
        return objects;
    }

    public List<IUMLObject> parse(String path, String[] args) {
    	List<IUMLObject> sootObjects = parser.parse(path, args);
    	
    	if(Arrays.asList(args).contains("-r")){
    		return sootObjects;
    	} else {
    		for(int i = 0; i < sootObjects.size(); i++) {
    			IUMLObject obj = sootObjects.get(i);
    			if(!Arrays.asList(args).contains(obj.getSootClass().getName())) {
    				sootObjects.remove(obj);
    				i--;
    			}
    		}
    	}
    	
		/*for(String argument : args){
			if(argument.charAt(0) == '-'){
				if(argument.length() == 3){
					if(argument.charAt(1) == 'r'){
						switch(argument.charAt(2)){
						case 'r':
							privacy = -1;
							sootObjects.
							break;
							
						case 'o':
							privacy = 0;
							return process(sootObjects);
						case 'u':
							privacy = 1;
							return process(sootObjects);
						}
						return sootObjects;
					}
				}
			}
		}*/
		return sootObjects;
    }
}
