package csse374.revengd.project.parsers;

import java.util.List;

import csse374.revengd.project.Configuration;
import csse374.revengd.project.umlobjects.IUMLObject;
import soot.Modifier;

public class PrivacyParserFilter implements IParserFilter {

	//Privacy codes:
	//-1 = private
	//0 = protected
	//1 = public
	int privacy = -1;
	
	IParser parser;
	
	@Override
	public List<IUMLObject> parse(String path, Configuration config){
		List<IUMLObject> sootObjects = parser.parse(path, config);
		
		if(config.hasArg("pr")) {
			privacy = -1;
		}
		if(config.hasArg("po")) {
			privacy = 0;
		}
		if(config.hasArg("pu")) {
			privacy = 1;
		}
		
		return sootObjects;
	}

    public PrivacyParserFilter(IParser parser){
        this.parser = parser;
    }

    public List<IUMLObject> process(List<IUMLObject> objects) {
    	if(privacy == 1){
    		FilterProtected(objects);
    	}
    	FilterPrivate(objects);
        return objects;
    }
    private void FilterPrivate(List<IUMLObject> objects){
    	for(int i = 0; i < objects.size(); i++){
			IUMLObject sootO = objects.get(i);
			if(Modifier.isPrivate(sootO.getModifiers())){
				objects.remove(i);
				i--;
			}
		}
    }
    private void FilterProtected(List<IUMLObject> objects){
    	for(int i = 0; i < objects.size(); i++){
			IUMLObject sootO = objects.get(i);
			if(Modifier.isProtected(sootO.getModifiers())){
				objects.remove(i);
				i--;
			}
		}
    }

}
