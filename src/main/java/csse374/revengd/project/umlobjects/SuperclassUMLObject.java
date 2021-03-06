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
			return target.getName() + " <|-- " + source.getName();
		}
		else{
			return target.getShortName() + " <|-- " + source.getShortName();
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
		return PackageHelper.getPackageNames(source.getName(), target.getName());
	}
}
