package csse374.revengd.project.umlobjects;

import java.util.List;

import com.beust.jcommander.internal.Lists;
import soot.SootClass;
import soot.SootMethod;

public class SequenceMethodUMLObject implements IUMLObject {

	private SootClass source;
	private SootMethod target;
	
	public SequenceMethodUMLObject(SootClass source, SootMethod target) {
		this.source = source;
		this.target = target;
	}
	
    @Override
    public String toUML(boolean full) {
    	StringBuilder builder = new StringBuilder();
    	for(int i = 0; i < target.getParameterCount(); i++){
    		builder.append(target.getParameterType(i));
    		if (i < target.getParameterCount() - 1){
    			builder.append(", ");
    		}
    	}
    	//String temp = source.getJavaStyleName() + " -> " + target.getDeclaringClass().getJavaStyleName();
    	//return source.getJavaStyleName() + " -> " + target.getDeclaringClass().getJavaStyleName();
        return source.getName().replace('$', '_') + " -> " + target.getDeclaringClass().getName().replace('$', '_') + " : " + target.getReturnType().toString() + " " + target.getName() + "( " + builder.toString() + ")";
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

		return (List<String>) Lists.newArrayList(source.getName().split("\\.")[0], target.getDeclaringClass().getName().split("\\.")[0]);
	}


}
