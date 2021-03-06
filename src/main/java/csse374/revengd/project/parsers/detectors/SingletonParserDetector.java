package csse374.revengd.project.parsers.detectors;

import csse374.revengd.project.parsers.IParser;
import csse374.revengd.project.parsers.detectors.IParserDetector;
import csse374.revengd.project.umlobjects.ClassUMLObject;
import csse374.revengd.project.umlobjects.IUMLObject;
import csse374.revengd.project.umlobjects.StereotypeUMLObject;
import soot.SootClass;
import soot.SootMethod;

import java.util.List;

public class SingletonParserDetector implements IParserDetector {
    IParser parser;

    public SingletonParserDetector(IParser parser){
        this.parser = parser;
    }

    @Override
    public List<IUMLObject> detect(List<IUMLObject> objects) {
        for(int i = 0; i < objects.size(); i++){
            IUMLObject object = objects.get(i);
            if(object instanceof ClassUMLObject){
                boolean privCon = false;
                boolean getIn = false;
                SootClass clazz = object.getSootClass();
                List<SootMethod> methods = clazz.getMethods();
                for(SootMethod method : methods){
                    if(method.isPrivate() && method.getName().contains("<init>")){
                        privCon = true;
                    }
                    if(method.getReturnType().toString().equals(clazz.getType().toString())){
                        getIn = true;
                    }
                }
                if(privCon && getIn){
                    IUMLObject newObj = new StereotypeUMLObject(object, "Singleton", "blue");
                    objects.remove(object);
                    i--;
                    objects.add(newObj);
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
