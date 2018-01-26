package csse374.revengd.project.parsers.detectors;

import csse374.revengd.project.parsers.IParser;
import csse374.revengd.project.umlobjects.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class CompositionOverInheritanceParserDetector implements IParserDetector{
    IParser inner;

    public CompositionOverInheritanceParserDetector(IParser inner){
        this.inner = inner;
    }

    @Override
    public List<IUMLObject> detect(List<IUMLObject> list) {
        HashMap<String, IUMLObject> objects = new HashMap<>();
        HashMap<String, String> relations = new HashMap<>();
        for(int i = 0; i < list.size(); i++){
            IUMLObject object = list.get(i);
            if(object instanceof SuperclassUMLObject){
                String representation = object.toUML(true);
                String[] parts = representation.split(" ");
                relations.put(parts[0], parts[2]);
                objects.put(parts[0], object);
            }
        }
        Iterator<String> keyIter = relations.keySet().iterator();
        while(keyIter.hasNext()){
            String child = keyIter.next();
            String parent = relations.get(child);
            if(relations.containsKey(parent) && !parent.equals("java.lang.Object")){
                IUMLObject obj1 = objects.get(child);
                IUMLObject obj2 = objects.get(parent);
                IUMLObject class1 = null;
                for(IUMLObject object : list){
                    if(object instanceof ClassUMLObject){
                        if(object.toUML(true).contains(parent)){
                            class1 = object;
                        }
                    }
                }
                if(class1 != null){
                    ColoredUMLObject co1 = new ColoredUMLObject(obj1, "orange");
                    list.remove(obj1);
                    list.add(co1);
                    ColoredUMLObject co2 = new ColoredUMLObject(obj2, "orange");
                    list.remove(obj2);
                    list.add(co2);
                    ColoredUMLObject cc1 = new ColoredUMLObject(class1, "orange");
                    list.remove(class1);
                    list.add(cc1);
                }
            }
        }
        return list;
    }

    @Override
    public List<IUMLObject> parse(String className) {
        return detect(inner.parse(className));
    }
}
