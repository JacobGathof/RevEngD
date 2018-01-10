package csse374.revengd.project.parserstrategies;

import csse374.revengd.project.umlobjects.AssociationUMLObject;
import csse374.revengd.project.umlobjects.IUMLObject;
import edu.rosehulman.jvm.sigevaluator.FieldEvaluator;
import edu.rosehulman.jvm.sigevaluator.GenericType;
import soot.*;
import soot.tagkit.Tag;

import java.util.ArrayList;
import java.util.List;

public class AssociationParserStrategy implements IParserStrategy{

    @Override
    public List<IUMLObject> parse(SootClass clazz, List<SootClass> dependencies, Scene v) {
        List<IUMLObject> umlObjects = new ArrayList<>();
        /*
        if(clazz.toString().contains("Object") || clazz.toString().contains("java")) {
            System.out.println("Skipping association analysis of " + clazz.toString() + " because of lack of access to source");
            return umlObjects;
        }
        */
        for(SootField field : clazz.getFields()){
            Type fieldType = field.getType();
            SootClass fieldClazz = v.loadClassAndSupport(fieldType.toString());
            umlObjects.add(new AssociationUMLObject(clazz, fieldClazz, plural(fieldClazz)));
            dependencies.add(dePluralizedClass(fieldType.toString(), v));

            try {
                Tag signatureTag = field.getTag("SignatureTag");
                if (signatureTag != null) {
                    String signature = signatureTag.toString();
                    FieldEvaluator fieldEvaluator = new FieldEvaluator(signature);
                    GenericType fieldGenericType = fieldEvaluator.getType();
                    for (String type : fieldGenericType.getAllElementTypes()) {
                        SootClass paramClazz = v.loadClassAndSupport(type);
                        umlObjects.add(new AssociationUMLObject(clazz, paramClazz, true));
                        dependencies.add(dePluralizedClass(type, v));
                    }
                }
            }
            catch(Exception e){
            }
        }
        return umlObjects;
    }

    public boolean plural(SootClass clazz){
        return clazz.toString().endsWith("[]");
    }


    public SootClass dePluralizedClass(String type, Scene v){
        System.out.println(type);
        if(type.endsWith("[]")) {
            return v.loadClassAndSupport(type.substring(0, type.length() - 2));
        }
        else{
            return v.loadClassAndSupport(type);
        }
    }
}
