package csse374.revengd.project.parserstrategies.resolutioncommands;

import java.util.ArrayList;
import java.util.List;

import soot.Hierarchy;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.jimple.toolkits.callgraph.CallGraph;

public class HierarchyContextResolutionCommand implements ISDContextResolutionCommand {

	@Override
	public List<SootMethod> resolve(CallGraph g, SootMethod topMethod, SootClass clazz, Scene v) {
		// TODO Auto-generated method stub
		Hierarchy hierarchy = v.getActiveHierarchy();
		  List<SootMethod> possibleMethods = hierarchy.resolveAbstractDispatch(topMethod.getDeclaringClass(), topMethod);
		  for(int i = 0; i < possibleMethods.size(); i++){
			  if (possibleMethods.get(i).hasActiveBody()){
				  List<SootMethod> toReturn = new ArrayList<SootMethod>();
				  toReturn.add(possibleMethods.get(i));
				  return toReturn;
				  //examine(g, possibleMethods.get(i), callingClass,  v, remainingDepth);
				  //break;
			  }
		  }
		  return new ArrayList<SootMethod>();
	}

}
