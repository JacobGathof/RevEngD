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
    public String toUML(boolean full) {
        if(full) {
			return "class " + clazz.getName();
		}
		else{
        	return "class " + clazz.getShortName();
		}
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
		return PackageHelper.getPackageNames(clazz.getName());
	}
    
}
