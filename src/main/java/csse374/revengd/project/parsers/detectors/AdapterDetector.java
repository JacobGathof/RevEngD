package csse374.revengd.project.parsers.detectors;

import java.util.List;

import csse374.revengd.project.parsers.IParser;
import csse374.revengd.project.umlobjects.ArrowCommentUMLObject;
import csse374.revengd.project.umlobjects.ClassUMLObject;
import csse374.revengd.project.umlobjects.IUMLObject;
import csse374.revengd.project.umlobjects.InterfaceUMLObject;
import csse374.revengd.project.umlobjects.StereotypeUMLObject;
import soot.SootClass;
import soot.SootField;
import soot.SootMethod;

public class AdapterDetector implements IParserDetector{
	IParser parser;

    public AdapterDetector(IParser parser){
        this.parser = parser;
    }

    @Override
    public List<IUMLObject> detect(List<IUMLObject> objects) {
        for(int i = 0; i < objects.size(); i++){
        	boolean foundTarget = false;
        	boolean foundAdaptee = false;
        	
            IUMLObject object = objects.get(i);
            IUMLObject target = null;
            IUMLObject adaptee = null;
            
            int targetIndex = -1;
            int adapteeIndex = -1;
            
            if(object instanceof ClassUMLObject || object instanceof InterfaceUMLObject){
                SootClass clazz = object.getSootClass();
                
                /*Check for Target*/
                for(int j = 0; j < objects.size(); j++){
                    IUMLObject object2 = objects.get(j);
                    if(object2 instanceof ClassUMLObject || object2 instanceof InterfaceUMLObject){
                    	
                        SootClass clazz2 = object2.getSootClass();
                        
                        if(clazz.getInterfaceCount() > 0 && clazz.getInterfaces().getFirst().toString().equals(clazz2.toString())) {
                        	for(SootMethod m1 : clazz2.getMethods()) {
                        		for(SootMethod m2 : clazz.getMethods()) {
                        			if(m1.getName().equals(m2.getName())) {
                            			target = object2;
                            			foundTarget = true;
                            			targetIndex = j;
                            		}
                        		}
                        	}
                        } 
                        
                    }
                    if(target != null) {
                    	break;
                    }
                }
                
                
                /*Check for Adaptee*/
                for(int j = 0; j < objects.size(); j++){
                    IUMLObject object2 = objects.get(j);
                    if(object2 instanceof ClassUMLObject || object2 instanceof InterfaceUMLObject){
                    	
                        SootClass clazz2 = object2.getSootClass();
                        
                        for(SootField f : clazz.getFields()) {
                        	System.out.println(clazz2.toString() + " " + f.getType().toString());
                        	if(clazz2.toString().equals(f.getType().toString())) {
                        		adaptee = object2;
                    			foundAdaptee = true;
                    			adapteeIndex = j;
                        	}
                        }
                    }
                    if(adaptee != null) {
                    	break;
                    }
                }
            }
            
            if(foundTarget && foundAdaptee) {
            	IUMLObject newObj = new StereotypeUMLObject(object, "Adapter");
                objects.remove(object);
                objects.add(i, newObj);
                
                IUMLObject newObj2 = new StereotypeUMLObject(adaptee, "Adaptee");
                objects.remove(adaptee);
                objects.add(adapteeIndex, newObj2);
                
                IUMLObject newObj3 = new StereotypeUMLObject(target, "Target");
                objects.remove(target);
                objects.add(targetIndex, newObj3);
                
                System.out.println(newObj3.toUML(true));
                System.out.println(newObj2.toUML(true));
                
                objects.add(new ArrowCommentUMLObject(newObj, "<<Adapts>>"));
            }
            
            
        }
                    
        return objects;
    }

    @Override
    public List<IUMLObject> parse(String className) {
        return detect(parser.parse(className));
    }
}
