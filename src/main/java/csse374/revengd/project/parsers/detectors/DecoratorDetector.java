package csse374.revengd.project.parsers.detectors;

import csse374.revengd.project.parsers.IParser;
import csse374.revengd.project.umlobjects.*;
import org.jf.util.Hex;
import soot.SootClass;
import soot.SootField;
import soot.SootMethod;
import soot.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DecoratorDetector implements IParserDetector{
    IParser inner;

    public DecoratorDetector(IParser inner){
        this.inner = inner;
    }

    @Override
    public List<IUMLObject> detect(List<IUMLObject> objects) {
        for(int a = 0; a < objects.size(); a++){
            IUMLObject object = objects.get(a);
            if(object instanceof AbstractClassUMLObject || object instanceof InterfaceUMLObject || object instanceof ClassUMLObject){
                boolean superField = false;
                boolean superArgument = false;
                boolean allMethods = false;
                SootClass clazz = object.getSootClass();
                Type type;
                if(clazz.hasSuperclass() && !clazz.getSuperclass().getType().toString().contains("java.lang.Object")){
                    type = clazz.getSuperclass().getType();
                    for (SootField field : clazz.getFields()) {
                        if (field.getType().toString().equals(type.toString())) {
                            superField = true;
                        }
                    }
                    List<SootMethod> superMethods = new ArrayList<>();
                    for (SootMethod method : clazz.getSuperclass().getMethods()) {
                        superMethods.add(method);
                    }
                    for (SootMethod method : clazz.getMethods()) {
                        if (method.getName().contains("<init>")) {
                            for (Type paramType : method.getParameterTypes()) {
                                if (paramType.toString().equals(type.toString())) {
                                    superArgument = true;
                                }
                            }
                        }
                        for (int i = 0; i < superMethods.size(); i++) {
                            if (superMethods.get(i).getName().equals(method.getName())) {
                                superMethods.remove(i);
                                break;
                            }
                        }
                    }
                    if(superMethods.size() == 0){
                        allMethods = true;
                    }
                }
                else if(clazz.getInterfaceCount() == 1){
                    type = clazz.getInterfaces().getFirst().getType();
                    for (SootField field : clazz.getFields()) {
                        if (field.getType().toString().equals(type.toString())) {
                            superField = true;
                        }
                    }
                    List<SootMethod> interMethods = new ArrayList<>();
                    for (SootMethod method : clazz.getInterfaces().getFirst().getMethods()) {
                        interMethods.add(method);
                    }
                    for (SootMethod method : clazz.getMethods()) {
                        if (method.getName().contains("<init>")) {
                            for (Type paramType : method.getParameterTypes()) {
                                if (paramType.toString().equals(type.toString())) {
                                    superArgument = true;
                                }
                            }
                        }
                        for (int i = 0; i < interMethods.size(); i++) {
                            if (interMethods.get(i).getName().equals(method.getName())) {
                                interMethods.remove(i);
                                break;
                            }
                        }
                    }
                    if(interMethods.size() == 0){
                        allMethods = true;
                    }
                }
                else{
                    continue;
                }
                if (superField && superArgument && allMethods) {
                    Random r = new Random();
                    int colorVal = r.nextInt(0xAAAAAA);
                    String color = Integer.toHexString(colorVal);
                    objects.add(new StereotypeUMLObject(object, "Decorator" + color, color));
                    objects.remove(object);
                    for(IUMLObject obj : objects){
                        if((obj instanceof ClassUMLObject || obj instanceof AbstractClassUMLObject || obj instanceof InterfaceUMLObject)
                                && obj.getSootClass().getType().equals(type)){
                            objects.add(new StereotypeUMLObject(obj, "Component" + color, color));
                            objects.remove(obj);
                            break;
                        }
                    }
                    for(IUMLObject obj : objects){
                        if((obj instanceof InheritanceRelationUMLObject || obj instanceof SuperclassUMLObject)
                                && obj.getSootClass().getType().equals(clazz.getType())){
                            objects.add(new ArrowCommentUMLObject(obj, "<<Decorates" + color + ">>"));
                            objects.remove(obj);
                            break;
                        }
                    }
                    for(IUMLObject obj : objects){
                        if((obj instanceof ClassUMLObject || obj instanceof AbstractClassUMLObject || obj instanceof InterfaceUMLObject)){
                            if(obj.getSootClass().hasSuperclass() && !obj.getSootClass().getSuperclass().getType().toString().contains("java.lang.Object")) {
                                Type superType = obj.getSootClass().getSuperclass().getType();
                                if(superType.toString().equals(clazz.getType().toString())){
                                    objects.add(new StereotypeUMLObject(obj, "Decorator" + color, color));
                                    objects.remove(obj);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        return objects;
    }

    @Override
    public List<IUMLObject> parse(String className) {
        return detect(inner.parse(className));
    }
}
