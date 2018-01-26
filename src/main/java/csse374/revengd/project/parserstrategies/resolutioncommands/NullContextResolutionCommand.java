package csse374.revengd.project.parserstrategies.resolutioncommands;

import java.util.ArrayList;
import java.util.List;

import csse374.revengd.project.umlobjects.IUMLObject;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.Edge;

public class NullContextResolutionCommand implements ISDContextResolutionCommand {

	@Override
	public List<SootMethod> resolve(CallGraph g, SootMethod topMethod, SootClass clazz, Scene v, Edge e) {
		// TODO Auto-generated method stub
		return new ArrayList<SootMethod>();
	}

}
