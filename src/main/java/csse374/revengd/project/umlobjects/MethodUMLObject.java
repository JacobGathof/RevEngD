package csse374.revengd.project.umlobjects;

import java.util.List;

import com.beust.jcommander.internal.Lists;

import soot.SootClass;
import soot.SootMethod;

public class MethodUMLObject implements IUMLObject{
	
	private SootClass source;
	private SootMethod target;
	
	public MethodUMLObject(SootClass source, SootMethod target) {
		this.source = source;
		this.target = target;
	}
	
    @Override
    public String toUML(boolean full) {
        if(full) {
			return source.getName() + " : " + target.getDeclaration();
		}
		else{
			return source.getShortName() + " : " + target.getDeclaration();
		}
    }

	@Override
	public int getModifiers() {
		
		return target.getModifiers();
	}
    
	@Override
	public SootClass getSootClass() {
		return source;
	}
	
	@Override
	public List<String> getPackage() {
		return (List<String>) Lists.newArrayList(source.getName().split("\\.")[0]);
	}
}
