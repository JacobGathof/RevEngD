package csse374.revengd.project.umlobjects;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.beust.jcommander.internal.Lists;

import soot.SootClass;

public class InheritanceRelationUMLObject implements IUMLObject {
    SootClass child;
    SootClass parent;

    public InheritanceRelationUMLObject(SootClass c, SootClass p){
        child = c;
        parent = p;
    }


    @Override
    public String toUML(boolean full) {
		if(full) {
			return parent.getName() + " <|-- " + child.getName();
		}
		else{
			return parent.getShortName() + " <|-- " + child.getShortName();
		}
    }


	@Override
	public int getModifiers() {
		return child.getModifiers();
	}


	@Override
	public SootClass getSootClass() {
		return child;
	}
	
	@Override
	public List<String> getPackage() {
		return (List<String>) Lists.newArrayList(child.getName().split("\\.")[0], parent.getName().split("\\.")[0]);
	}
	
	
}
