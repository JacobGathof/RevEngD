package csse374.revengd.project.parserstrategies.resolutioncommands;

import java.util.ArrayList;
import java.util.List;

import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.jimple.toolkits.callgraph.CallGraph;

public class ChainContextResolutionCommandMacro implements ISDContextResolutionCommand {

	List<ISDContextResolutionCommand> resolutionCommands;
	public ChainContextResolutionCommandMacro(List<ISDContextResolutionCommand> resolutionCommands){
		this.resolutionCommands = resolutionCommands;
	}
	@Override
	public List<SootMethod> resolve(CallGraph g, SootMethod topMethod, SootClass clazz, Scene v) {
		for(ISDContextResolutionCommand rc : resolutionCommands){
			List<SootMethod> methods = rc.resolve(g, topMethod, clazz, v);
			if(!methods.isEmpty()){
				return methods;
			}
		}
		return new ArrayList<SootMethod>();
	}

}
