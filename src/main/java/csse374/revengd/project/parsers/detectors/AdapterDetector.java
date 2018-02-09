package csse374.revengd.project.parsers.detectors;

import java.util.List;
import java.util.Random;

import csse374.revengd.project.parsers.IParser;
import csse374.revengd.project.umlobjects.AbstractClassUMLObject;
import csse374.revengd.project.umlobjects.ArrowCommentUMLObject;
import csse374.revengd.project.umlobjects.AssociationUMLObject;
import csse374.revengd.project.umlobjects.ClassUMLObject;
import csse374.revengd.project.umlobjects.IUMLObject;
import csse374.revengd.project.umlobjects.InterfaceUMLObject;
import csse374.revengd.project.umlobjects.StereotypeUMLObject;
import soot.SootClass;
import soot.SootField;
import soot.SootMethod;
import soot.Type;

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
            
            IUMLObject adaptsArrow = null;
            
            int targetIndex = -1;
            int adapteeIndex = -1;
            
            if(object instanceof ClassUMLObject || object instanceof InterfaceUMLObject || object instanceof AbstractClassUMLObject){
                SootClass clazz = object.getSootClass();
                
                /*Check for Target*/
                for(int j = 0; j < objects.size(); j++){
                    IUMLObject object2 = objects.get(j);
                    if(object2 instanceof ClassUMLObject || object2 instanceof InterfaceUMLObject || object2 instanceof AbstractClassUMLObject){
                    	
                        SootClass clazz2 = object2.getSootClass();
                        
                        if(clazz.getInterfaceCount() > 0 && clazz.getInterfaces().getFirst().toString().equals(clazz2.toString()) || clazz.getSuperclass().getName().equals(clazz2.getName().toString())) {
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
                    if(object2 instanceof ClassUMLObject || object2 instanceof InterfaceUMLObject || object2 instanceof AbstractClassUMLObject){
                    	
                        SootClass clazz2 = object2.getSootClass();
                        
                        
                        for(SootField f : clazz.getFields()) {
                        	if(clazz2.toString().equals(f.getType().toString())) {
                        		adaptee = object2;
                    			foundAdaptee = true;
                    			adapteeIndex = j;
                        	}
                        }
                        
                        
                        
                        for(SootMethod method : clazz.getMethods()) {
                        	if (method.getName().contains("<init>")) {
                        		
                                for (Type paramType : method.getParameterTypes()) {
                                    if (paramType.toString().equals(clazz2.toString())) {
                                    	adaptee = object2;
                            			foundAdaptee = true;
                            			adapteeIndex = j;
                                    }
                                }
                            }
                        }
                        
                        /*
                        if(adaptee != null) {
                        	for(int k = 0; k < objects.size(); k++) {
                        		IUMLObject potArrow = objects.get(k);
                        		if(potArrow instanceof AssociationUMLObject) {
                        			if(potArrow.toUML(true).contains(clazz.getName()) && potArrow.toUML(true).contains(clazz2.getName())) {
                        				adaptsArrow = potArrow;
                        				break;
                        			}
                        		}
                        	}
                        	break;
                        } 
                        */  
                    }
                }
            }
            
            if(foundTarget && foundAdaptee) {
            	
            	
            	Random r = new Random();
                int colorVal = r.nextInt(0xAAAAAA);
                String color = Integer.toHexString(colorVal);
            	
            	IUMLObject newObj = new StereotypeUMLObject(object, "Adapter", color);
                objects.remove(object);
                objects.add(i, newObj);
                
                IUMLObject newObj2 = new StereotypeUMLObject(adaptee, "Adaptee", color);
                objects.remove(adaptee);
                objects.add(adapteeIndex, newObj2);
                
                IUMLObject newObj3 = new StereotypeUMLObject(target, "Target", color);
                objects.remove(target);
                objects.add(targetIndex, newObj3);
                
                System.out.println(newObj3.toUML(true));
                System.out.println(newObj2.toUML(true));
                
                if(adaptsArrow != null) {
	                objects.remove(adaptsArrow);
	                objects.add(new ArrowCommentUMLObject(adaptsArrow, "<<Adapts>>"));
                }
            }
            
        }
                    
        return objects;
    }

    @Override
    public List<IUMLObject> parse(String className) {
        return detect(parser.parse(className));
    }
}
