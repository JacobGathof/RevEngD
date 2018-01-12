package csse374.revengd.project.umlobjects;

import java.util.List;

import com.beust.jcommander.internal.Lists;

import soot.SootClass;
import soot.SootMethod;

public class SuperclassUMLObject implements IUMLObject {

	private SootClass source;
	private SootClass target;
	
	public SuperclassUMLObject(SootClass source, SootClass target) {
		this.source = source;
		this.target = target;
	}
	
    @Override
    public String toUML(boolean full) {
        if(full) {
			return source.getName() + " <|-- " + target.getName();
		}
		else{
			return source.getShortName() + " <|-- " + target.getShortName();
		}
    }

	@Override
	public int getModifiers() {
		return source.getModifiers();
	}
    
	@Override
	public SootClass getSootClass() {
		return source;
	}
	
	@Override
	public List<String> getPackage() {
		return (List<String>) Lists.newArrayList(source.getName().split("\\.")[0], target.getName().split("\\.")[0]);
	}
}
