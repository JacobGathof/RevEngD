package csse374.revengd.project;

import java.util.List;

import soot.Modifier;

public class PrivacyParserFilter implements IParserFilter {

	//Privacy codes:
	//-1 = private
	//0 = protected
	//1 = public
	int privacy = -1;
	
	IParser parser;
	
	@Override
	public List<IUMLObject> parse(String path, String[] args){
		List<IUMLObject> sootObjects = parser.parse(path, args);
		for(String argument : args){
			if(argument.charAt(0) == '-'){
				if(argument.length() == 3){
					if(argument.charAt(1) == 'p'){
						switch(argument.charAt(2)){
						case 'r':
							privacy = -1;
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
