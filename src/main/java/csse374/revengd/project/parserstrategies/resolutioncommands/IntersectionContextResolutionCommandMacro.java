package csse374.revengd.project.parserstrategies.resolutioncommands;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.Edge;

public class IntersectionContextResolutionCommandMacro implements ISDContextResolutionCommand {

	List<ISDContextResolutionCommand> resolutionCommands;
	public IntersectionContextResolutionCommandMacro(List<ISDContextResolutionCommand> resolutionCommands){
		this.resolutionCommands = resolutionCommands;
	}
	@Override
	public List<SootMethod> resolve(CallGraph g, SootMethod topMethod, SootClass clazz, Scene v, Edge e) {
		List<Set<SootMethod>> resolutions = new ArrayList<Set<SootMethod>>();

		List<SootMethod> methods = new ArrayList<SootMethod>();

		//for(ISDContextResolutionCommand e : resolutionCommands){
		for(int i =0; i < resolutionCommands.size(); i++){
			methods = resolutionCommands.get(i).resolve(g, topMethod, clazz, v, e);
			Set<SootMethod> indivSet = new LinkedHashSet<SootMethod>();
			indivSet.addAll(methods);
			resolutions.add(indivSet);
			/*for(SootMethod f : methods){
				indivSet.add(f);
			}*/
		}
		Set<SootMethod> fSet = new LinkedHashSet<SootMethod>();
		if(resolutions.size() > 0){
			fSet = resolutions.get(0);
		}
		for(Set<SootMethod> s : resolutions){
			for(SootMethod m : fSet){
				if(!s.contains(m)){
					fSet.remove(m);
				}
			}
		}
		List<SootMethod> toReturn = new ArrayList<SootMethod>();
		toReturn.addAll(fSet);
		
		return toReturn;
	}

}
