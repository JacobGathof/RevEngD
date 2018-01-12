package csse374.revengd.project.umlobjects;

import java.util.List;

import com.beust.jcommander.internal.Lists;

import soot.SootClass;

public class InterfaceUMLObject implements IUMLObject{
    SootClass inter;

    public InterfaceUMLObject(SootClass i){
        inter = i;
    }

    @Override
    public String toUML(boolean full) {
        if(full) {
			return "interface " + inter.getName();
		}
		else{
			return "interface " + inter.getShortName();
		}
    }

	@Override
	public int getModifiers() {
		return inter.getModifiers();
	}
	
	@Override
	public SootClass getSootClass() {
		return inter;
	}
	
	@Override
	public List<String> getPackage() {
		return (List<String>) Lists.newArrayList(inter.getName().split("\\.")[0]);
	}
}
