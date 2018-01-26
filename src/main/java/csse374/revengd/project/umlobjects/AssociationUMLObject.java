package csse374.revengd.project.umlobjects;

import java.util.List;

import com.beust.jcommander.internal.Lists;

import soot.SootClass;

public class AssociationUMLObject implements IUMLObject {
    private SootClass source;
    private SootClass reference;
    private boolean manyToOne;

    public AssociationUMLObject(SootClass source, SootClass reference, boolean manyToOne){
        this.source = source;
        this.reference = reference;
        this.manyToOne = manyToOne;
    }

    @Override
    public String toUML(boolean full) {
        if(full){
            if (manyToOne) {
                return source.toString() + " --* \"1..*\" " + cleanName(reference.toString());
            }
            return source.toString() + " --* " + cleanName(reference.toString());
        }
        else{
            if (manyToOne) {
                return source.getShortName() + " --* \"1..*\" " + cleanName(reference.getShortName());
            }
            return source.getShortName() + " --* " + cleanName(reference.getShortName());
        }
    }

    private String cleanName(String className){
        if(className.endsWith("[]")){
            return cleanName(className.substring(0, className.length() - 2));
        }
        return className;
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
    	return PackageHelper.getPackageNames(source.getName(), reference.getName());
	}
}
