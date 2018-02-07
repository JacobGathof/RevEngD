package csse374.revengd.project.parsers.detectors;

import csse374.revengd.project.parsers.IParser;
import csse374.revengd.project.umlobjects.*;
import soot.SootClass;
import soot.SootField;
import soot.SootMethod;
import soot.Type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PoorlyWrittenDecoratorDetector implements IParserDetector{

    IParser parser;

    public PoorlyWrittenDecoratorDetector(IParser parser){
        this.parser = parser;
    }

    @Override
    public List<IUMLObject> detect(List<IUMLObject> objects) {
        for(int a = 0; a < objects.size(); a++){
            IUMLObject object = objects.get(a);
            if(object instanceof AbstractClassUMLObject || object instanceof InterfaceUMLObject){
                boolean superField = false;
                boolean superArgument = false;
                SootClass clazz = object.getSootClass();
                if(clazz.hasSuperclass()) {
                    Type type = clazz.getSuperclass().getType();
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
                    if (superField && superArgument) {
                        boolean missingMethods = false;
                        for (SootMethod method : superMethods) {
                            missingMethods = true;
                            objects.add(new MethodColorerUMLObject(new MethodUMLObject(object.getSootClass(), method), "red"));
                        }
                        if(missingMethods) {
                            objects.add(new StereotypeUMLObject(object, "Bad Decorator", "black"));
                            objects.remove(object);
                            for (IUMLObject obj : objects) {
                                if ((obj instanceof ClassUMLObject || obj instanceof AbstractClassUMLObject || obj instanceof InterfaceUMLObject)
                                        && obj.getSootClass().getType().equals(type)) {
                                    objects.add(new StereotypeUMLObject(obj, "Component", "black"));
                                    objects.remove(obj);
                                    break;
                                }
                            }
                            for (IUMLObject obj : objects) {
                                if ((obj instanceof InheritanceRelationUMLObject || obj instanceof SuperclassUMLObject)
                                        && obj.getSootClass().getType().equals(clazz.getType())) {
                                    objects.add(new ArrowCommentUMLObject(obj, "<<Decorates>>"));
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
        return detect(parser.parse(className));
    }
}
