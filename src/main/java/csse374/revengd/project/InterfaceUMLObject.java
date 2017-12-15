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

	@Override
	public int getModifiers() {
		return inter.getModifiers();
	}
	
	@Override
	public SootClass getSootClass() {
		return inter;
	}
}
