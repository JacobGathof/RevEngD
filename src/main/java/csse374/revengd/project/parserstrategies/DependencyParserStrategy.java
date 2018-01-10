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

public class DependencyParserStrategy implements IParserStrategy {
    @Override
    public List<IUMLObject> parse(SootClass clazz, List<SootClass> dependencies, Scene v) {
        List<IUMLObject> umlObjects = new ArrayList<>();
        List<SootMethod> methods = clazz.getMethods();

        if(clazz.toString().contains("java") || clazz.toString().contains("soot")){
            System.out.println("Skipping dependency analysis of " + clazz.toString() + " because of lack of access to source");
            return umlObjects;
        }

        for(SootField field : clazz.getFields()){
            Type fieldType = field.getType();
            SootClass fieldClazz = v.loadClassAndSupport(fieldType.toString());
            umlObjects.add(new DependencyUMLObject(clazz, fieldClazz));
            dependencies.add(fieldClazz);

            Tag signatureTag = field.getTag("SignatureTag");
            if(signatureTag != null) {
                String signature = signatureTag.toString();
                FieldEvaluator fieldEvaluator = new FieldEvaluator(signature);
                GenericType fieldGenericType = fieldEvaluator.getType();
                for(String type: fieldGenericType.getAllElementTypes()) {
                    SootClass genericClazz = v.loadClassAndSupport(type);
                    umlObjects.add(new DependencyUMLObject(clazz, genericClazz));
                    dependencies.add(genericClazz);
                }
            }
        }

        for(SootMethod method : methods){
            if(method.toString().contains("<init>")){
                continue;
            }

            clazz.setApplicationClass();
            if(method.hasActiveBody()) {
                Body body = method.retrieveActiveBody();
                for (Local local : body.getLocals()) {
                    Type localType = local.getType();
                    SootClass localClazz = v.loadClassAndSupport(localType.toString());
                    umlObjects.add(new DependencyUMLObject(clazz, localClazz));
                    dependencies.add(localClazz);
                }
            }


            Type returnType = method.getReturnType();
            SootClass returnClazz = v.loadClassAndSupport(returnType.toString());
            umlObjects.add(new DependencyUMLObject(clazz, returnClazz));
            dependencies.add(returnClazz);

            Tag signatureTag = method.getTag("SignatureTag");
            if(signatureTag != null) {
                // Use SignatureEvaluator API for parsing the field signature
                String signature = signatureTag.toString();
                MethodEvaluator methodEvaluator = new MethodEvaluator(signature);

                GenericType returnGenericType = methodEvaluator.getReturnType();
                for(String type: returnGenericType.getAllElementTypes()) {
                    SootClass genericClazz = v.loadClassAndSupport(type);
                    umlObjects.add(new DependencyUMLObject(clazz, genericClazz));
                    dependencies.add(genericClazz);
                }

                for(GenericType paramGenericType : methodEvaluator.getParameterTypes()){
                    for(String type: paramGenericType.getAllElementTypes()) {
                        SootClass genericClazz = v.loadClassAndSupport(type);
                        umlObjects.add(new DependencyUMLObject(clazz, genericClazz));
                        dependencies.add(genericClazz);
                    }
                }
            }

            for(Type paramType : method.getParameterTypes()){
                SootClass paramClazz = v.loadClassAndSupport(paramType.toString());
                umlObjects.add(new DependencyUMLObject(clazz, paramClazz));
                dependencies.add(paramClazz);
            }
        }
        System.out.println(clazz.toString());
        for(IUMLObject obj : umlObjects){
            System.out.println(obj.toUML());
        }
        return umlObjects;
    }
}
