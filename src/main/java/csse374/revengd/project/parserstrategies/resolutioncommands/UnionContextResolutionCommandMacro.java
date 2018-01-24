package csse374.revengd.project.parserstrategies.resolutioncommands;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import java.util.Set;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.jimple.toolkits.callgraph.CallGraph;

public class UnionContextResolutionCommandMacro implements ISDContextResolutionCommand {

	List<ISDContextResolutionCommand> resolutionCommands;
	public UnionContextResolutionCommandMacro(List<ISDContextResolutionCommand> resolutionCommands){
		this.resolutionCommands = resolutionCommands;
	}
	@Override
	public List<SootMethod> resolve(CallGraph g, SootMethod topMethod, SootClass clazz, Scene v) {
		// TODO Auto-generated method stub
		Set<SootMethod> resolutions = new HashSet<SootMethod>();

		List<SootMethod> methods = new ArrayList<SootMethod>();

		for(ISDContextResolutionCommand e : resolutionCommands){
			methods = e.resolve(g, topMethod, clazz, v);
			for(SootMethod f : methods){
				resolutions.add(f);
			}
		}
		List<SootMethod> toReturn = new ArrayList<SootMethod>();
		toReturn.addAll(resolutions);
		
		return toReturn;
	}

}
