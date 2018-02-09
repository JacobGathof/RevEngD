package csse374.revengd.project.parserstrategies;

import java.util.ArrayList;
import java.util.List;

import csse374.revengd.project.parserstrategies.resolutioncommands.ISDContextResolutionCommand;
import csse374.revengd.project.umlobjects.IUMLObject;
import soot.PackManager;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.jimple.toolkits.callgraph.CallGraph;

public abstract class AbstractSequenceDiagramParserStrategy implements IParserStrategy {

	protected String startMethodName;
	protected int depthToLook;
	protected ISDContextResolutionCommand resolveCommand;
	
	public AbstractSequenceDiagramParserStrategy(String methodName, int aDepthToLook, ISDContextResolutionCommand command){
		startMethodName = methodName;
		depthToLook = aDepthToLook;
		resolveCommand = command;
	}
	@Override
	public List<IUMLObject> parse(SootClass clazz, List<SootClass> dependencies, Scene v) {
	    clazz.setApplicationClass();
	    v.setMainClass(clazz);
	    v.loadNecessaryClasses();
	    
	    PackManager.v().runPacks();
	    
	    CallGraph g = v.getCallGraph();

		SootMethod startMethod = clazz.getMethodByName(startMethodName);
		List<SootMethod> startMethods = new ArrayList<>();
		startMethods.add(startMethod);
		v.setEntryPoints(startMethods);

		System.out.println("about to examine");
		List<IUMLObject> temp = examine(g, startMethod, clazz, v, depthToLook);
		return temp;
	}
	protected abstract List<IUMLObject> examine(CallGraph g, SootMethod rootMethod, SootClass callingClass,  Scene v, int remainingDepth);

}
