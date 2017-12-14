package csse374.revengd.project;

import soot.SootClass;

public class InterfaceUMLObject implements IUMLObject{
    SootClass inter;

    public InterfaceUMLObject(SootClass i){
        inter = i;
    }

    @Override
    public String toUML() {
        return "interface " + inter.getName();
    }
}
