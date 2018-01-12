package csse374.revengd.project.umlobjects;

import java.util.List;

import com.beust.jcommander.internal.Lists;

import soot.SootClass;
import soot.SootMethod;

public class ClassUMLObject implements IUMLObject{
	
	private SootClass clazz;
	
	public ClassUMLObject(SootClass c) {
		this.clazz = c;
	}
	
    @Override
    public String toUML() {
        return "class " + clazz.getName();
    }

	@Override
	public int getModifiers() {
		return clazz.getModifiers();
	}
	
	@Override
	public SootClass getSootClass() {
		return clazz;
	}

	@Override
	public List<String> getPackage() {
		return (List<String>) Lists.newArrayList(clazz.getName().split("\\.")[0]);
	}
    
}
