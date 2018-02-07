package csse374.revengd.project.umlobjects;

import soot.SootClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StereotypeUMLObject implements IUMLObjectDecorator{
    IUMLObject inner;
    String stereotype;
    HashMap<String,String> colors = new HashMap<>();

    public StereotypeUMLObject(IUMLObject inner, String stereotype, String color){
        this.inner = inner;
        this.stereotype = stereotype;
        colors.put(stereotype, color);
    }

    @Override
    public String toUML(boolean full) {
        StringBuilder header = new StringBuilder();
        header.append("skinparam class {\n");
        for(String stereotype : colors.keySet()) {
            header.append("\tBorderColor<<" + stereotype + ">> " + colors.get(stereotype) + "\n");
        }
        header.append("}\n");
        String innerUML = inner.toUML(full);
        return header.toString() + innerUML + " <<" + stereotype + ">>";
    }

    @Override
    public int getModifiers() {
        return inner.getModifiers();
    }

    @Override
    public SootClass getSootClass() {
        return inner.getSootClass();
    }

    @Override
    public List<String> getPackage() {
        return inner.getPackage();
    }

    @Override
    public IUMLObject getInner() {
        return inner;
    }
}
