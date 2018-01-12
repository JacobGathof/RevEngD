package csse374.revengd.project.parserstrategies;

import csse374.revengd.project.umlobjects.DependencyUMLObject;
import csse374.revengd.project.umlobjects.IUMLObject;
import edu.rosehulman.jvm.sigevaluator.MethodEvaluator;
import soot.*;

import edu.rosehulman.jvm.sigevaluator.FieldEvaluator;
import edu.rosehulman.jvm.sigevaluator.GenericType;
import soot.tagkit.Tag;

import java.util.ArrayList;
import java.util.List;

public class MethodSignatureDependencyParserStrategy implements IParserStrategy {
    @Override
    public List<IUMLObject> parse(SootClass clazz, List<SootClass> dependencies, Scene v) {
        List<IUMLObject> umlObjects = new ArrayList<>();
        clazz.setApplicationClass();
        List<SootMethod> methods = clazz.getMethods();
        /*
        if(clazz.toString().contains("java")) {
            System.out.println("Skipping dependency analysis of " + clazz.toString() + " because of lack of access to source");
            return umlObjects;
        }
        */
        for(SootMethod method : methods){
            if(method.toString().contains("<init>")){
                continue;
            }

            Type returnType = method.getReturnType();
            SootClass returnClazz = v.loadClassAndSupport(returnType.toString());
            umlObjects.add(new DependencyUMLObject(clazz, returnClazz, plural(returnClazz)));
            dependencies.add(dePluralizedClass(returnType.toString(), v));

            Tag signatureTag = method.getTag("SignatureTag");
            if(signatureTag != null) {
                // Use SignatureEvaluator API for parsing the field signature
                String signature = signatureTag.toString();
                MethodEvaluator methodEvaluator = new MethodEvaluator(signature);

                try {
                    GenericType returnGenericType = methodEvaluator.getReturnType();
                    for (String type : returnGenericType.getAllElementTypes()) {
                        SootClass genericClazz = v.loadClassAndSupport(type);
                        umlObjects.add(new DependencyUMLObject(clazz, genericClazz, true));
                        dependencies.add(dePluralizedClass(type, v));
                    }
                }
                catch(Exception e){
                }
                try {
                    for (GenericType paramGenericType : methodEvaluator.getParameterTypes()) {
                        for (String type : paramGenericType.getAllElementTypes()) {
                            SootClass genericClazz = v.loadClassAndSupport(type);
                            umlObjects.add(new DependencyUMLObject(clazz, genericClazz, true));
                            dependencies.add(dePluralizedClass(type, v));
                        }
                    }
                }
                catch(Exception e){
                }
            }

            for(Type paramType : method.getParameterTypes()){
                SootClass paramClazz = v.loadClassAndSupport(paramType.toString());
                umlObjects.add(new DependencyUMLObject(clazz, paramClazz, plural(paramClazz)));
                dependencies.add(dePluralizedClass(paramType.toString(), v));
            }
        }
        return umlObjects;
    }

    public boolean plural(SootClass clazz){
        return clazz.toString().endsWith("[]");
    }

    public SootClass dePluralizedClass(String type, Scene v){
        if(type.endsWith("[]")) {
            return dePluralizedClass(type.substring(0, type.length() - 2), v);
        }
        else{
            return v.loadClassAndSupport(type);
        }
    }
}
