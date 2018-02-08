package csse374.revengd.project.parsers.detectors;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import csse374.revengd.project.parsers.IParser;
import csse374.revengd.project.umlobjects.AbstractClassUMLObject;
import csse374.revengd.project.umlobjects.ArrowCommentUMLObject;
import csse374.revengd.project.umlobjects.AssociationUMLObject;
import csse374.revengd.project.umlobjects.ClassUMLObject;
import csse374.revengd.project.umlobjects.DependencyUMLObject;
import csse374.revengd.project.umlobjects.IUMLObject;
import csse374.revengd.project.umlobjects.IUMLObjectDecorator;
import csse374.revengd.project.umlobjects.InheritanceRelationUMLObject;
import csse374.revengd.project.umlobjects.InterfaceUMLObject;
import csse374.revengd.project.umlobjects.MethodUMLObject;
import csse374.revengd.project.umlobjects.StereotypeUMLObject;
import csse374.revengd.project.umlobjects.SuperclassUMLObject;
import soot.SootClass;
import soot.SootField;
import soot.SootMethod;
import soot.tagkit.Tag;
import soot.util.Chain;

public class DependencyInversionViolationDetector implements IParserDetector {

	 IParser parser;

    public DependencyInversionViolationDetector(IParser parser){
        this.parser = parser;
    }
    
	@Override
	public List<IUMLObject> detect(List<IUMLObject> objects) {
		List<SootClass> concreteClasses= new ArrayList<SootClass>();
		List<SootClass> classes= new ArrayList<SootClass>();
		List<IUMLObject> arrows = new ArrayList<IUMLObject>();
		
		for(int i = 0; i < objects.size(); i++){
			IUMLObject object = objects.get(i);
			while(object instanceof IUMLObjectDecorator){
				object = ((IUMLObjectDecorator)object).getInner();
			}
			if (object instanceof ClassUMLObject || object instanceof AbstractClassUMLObject || object instanceof InterfaceUMLObject){
				SootClass clazz = object.getSootClass();
				classes.add(clazz);
				if (clazz.isConcrete()){
					concreteClasses.add(clazz);
				}
			}
			if (isArrow(object)){
				arrows.add(object);
			}
		}
		Iterator <IUMLObject> arrowIt = arrows.iterator();
		while(arrowIt.hasNext()){
			IUMLObject individualArrow = arrowIt.next();
			String str = individualArrow.toUML(true);
			String[] splitStr = str.split(" ");
			Iterator <SootClass> classIt = concreteClasses.iterator();
			while (classIt.hasNext()){
				SootClass clazz = classIt.next();
			
			//classes.forEach(clazz -> {
				String temp = clazz.getName();
				if (individualArrow instanceof InheritanceRelationUMLObject){
					if (splitStr[0].contains(temp)){
						objects.remove(objects.indexOf(individualArrow));
						objects.add(new ArrowCommentUMLObject(individualArrow, "Dependency Inversion Violation"));
					}
				}
				if (individualArrow instanceof InheritanceRelationUMLObject || individualArrow instanceof SuperclassUMLObject){
					if (splitStr[0].contains(temp)){
						objects.remove(objects.indexOf(individualArrow));
						objects.add(new ArrowCommentUMLObject(individualArrow, "Dependency Inversion Violation"));
					}
				} else {
					if (splitStr[splitStr.length - 1].contains(temp)){
						objects.remove(objects.indexOf(individualArrow));
						objects.add(new ArrowCommentUMLObject(individualArrow, "Dependency Inversion Violation"));
					}
				}
				String wut = clazz.getName();
				String holder = "1";
			}
		}
		/*for(int i = 0; i < objects.size(); i++){
            IUMLObject object = objects.get(i);
            if(object instanceof ClassUMLObject){
                SootClass clazz = object.getSootClass();
                SootClass superClazz = clazz.getSuperclass();
                

                List<SootMethod> myMethods  = clazz.getMethods();
                if (!superClazz.isAbstract() && !superClazz.getName().contains("java.lang.Object")){
                	
                	
                	
                	IUMLObject newObj = new StereotypeUMLObject(object, "Depedency Inversion Violation");
                	objects.remove(object);
                    objects.add(newObj);
                }
            }
            else if (object instanceof AssociationUMLObject){
            	AssociationUMLObject assocUML = (AssociationUMLObject) object;
            	SootClass clazz = assocUML.getSootClass();
            	if(!clazz.isAbstract()){
            	  	if(){
            	  	}
            	}
            }
            else if (object instanceof MethodUMLObject){
            
            	MethodUMLObject methodUML = (MethodUMLObject) object;
            	SootClass clazz = methodUML.getSootClass();
            	if(!clazz.isAbstract()){
            		clazz.getMethodByName(name)
            	}
            }
                
                for(SootField f:fields){
                	//System.out.println(f.getSignature());
                	System.out.println(f.getDeclaration());
                	System.out.println(f.getName());
                	System.out.println(f.getSubSignature());
                	System.out.println(f.getSubSignature().replace(f.getName(), ""));
                	
                	
                }
                */
                /*List<SootMethod> methods = clazz.getMethods();
                for(SootMethod method : methods){
                    
                }
                if(privCon && getIn){
                    IUMLObject newObj = new StereotypeUMLObject(object, "Singleton");
                    objects.remove(object);
                    objects.add(newObj);
                }*/
            //}
	//}
        return objects;
	}
	
	private boolean isArrow(IUMLObject object){
		return (object instanceof AssociationUMLObject) ||
				(object instanceof DependencyUMLObject) ||
				(object instanceof InheritanceRelationUMLObject) ||
				(object instanceof SuperclassUMLObject);
	}

	@Override
	public List<IUMLObject> parse(String className) {
        return detect(parser.parse(className));

	}

}
