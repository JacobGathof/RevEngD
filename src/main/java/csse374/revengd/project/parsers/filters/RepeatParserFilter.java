package csse374.revengd.project.parsers.filters;

import csse374.revengd.project.parsers.IParser;
import csse374.revengd.project.parsers.filters.IParserFilter;
import csse374.revengd.project.umlobjects.IUMLObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class RepeatParserFilter implements IParserFilter {
    IParser inner;

    public RepeatParserFilter(IParser inner){
        this.inner = inner;
    }

    @Override
    public List<IUMLObject> process(List<IUMLObject> objects) {
        HashSet<String> stringSet = new HashSet<String>();
        HashMap<String, IUMLObject> objMap = new HashMap<>();
        for(IUMLObject obj : objects) {
            stringSet.add(obj.toUML(true));
            objMap.put(obj.toUML(true), obj);
        }
        HashSet<String> newObjSet = (HashSet<String>)stringSet.clone();
        for(String obj : stringSet){
            if(obj.contains(" --* \"1..*\" ")){
                String[] comps = obj.split(" --\\* \"1\\.\\.\\*\" ");
                if(newObjSet.contains(comps[0] + " --> \"1..*\" " + comps[1])){
                    newObjSet.remove(comps[0] + " --> \"1..*\" "+ comps[1]);
                }
            }
            else if(obj.contains(" --* ")){
                String[] comps = obj.split(" --\\* ");
                if(newObjSet.contains(comps[0] + " --> " + comps[1])){
                    newObjSet.remove(comps[0] + " --> " + comps[1]);
                }
            }
        }
        List<IUMLObject> newObjs = new ArrayList<>();
        for(String obj : newObjSet){
            newObjs.add(objMap.get(obj));
        }
        return newObjs;
    }

    @Override
    public List<IUMLObject> parse(String className) {
        return process(inner.parse(className));
    }
}
