package csse374.revengd.project.parserstrategies;

import csse374.revengd.project.umlobjects.DependencyUMLObject;
import csse374.revengd.project.umlobjects.IUMLObject;
import edu.rosehulman.jvm.sigevaluator.GenericType;
import edu.rosehulman.jvm.sigevaluator.MethodEvaluator;
import soot.*;
import soot.tagkit.Tag;

import java.util.ArrayList;
import java.util.List;

public class LocalVariableDependencyParserStrategy implements IParserStrategy{
    @Override
    public List<IUMLObject> parse(SootClass clazz, List<SootClass> dependencies, Scene v) {
        List<IUMLObject> umlObjects = new ArrayList<>();
        clazz.setApplicationClass();
        List<SootMethod> methods = clazz.getMethods();

        if(clazz.toString().contains("Object")) {
            System.out.println("Skipping dependency analysis of " + clazz.toString() + " because of lack of access to source");
            return umlObjects;
        }

        for(SootMethod method : methods){
            if(method.toString().contains("<init>")){
                continue;
            }

            Body body = method.retrieveActiveBody();
            for (Local local : body.getLocals()) {
                Type localType = local.getType();
                SootClass localClazz = v.loadClassAndSupport(localType.toString());
                umlObjects.add(new DependencyUMLObject(clazz, localClazz, false));
                dependencies.add(localClazz);
            }
        }
        return umlObjects;
    }
}
