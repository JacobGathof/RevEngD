package csse374.revengd.project.parserstrategies.resolutioncommands;

import java.util.ArrayList;
import java.util.List;

import csse374.revengd.project.umlobjects.IUMLObject;
import soot.Hierarchy;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.Edge;

public class FirstContextResolutionCommand implements ISDContextResolutionCommand {

	@Override
	public List<SootMethod> resolve(CallGraph g, SootMethod rootMethod, SootClass callingClass, Scene v, Edge e) {
		// TODO Auto-generated method stub
		Hierarchy hierarchy = v.getActiveHierarchy();
		  List<SootMethod> possibleMethods = hierarchy.resolveAbstractDispatch(rootMethod.getDeclaringClass(), rootMethod);
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
