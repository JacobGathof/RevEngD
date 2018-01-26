package csse374.revengd.project.parserstrategies.resolutioncommands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import csse374.revengd.project.umlobjects.IUMLObject;
import soot.MethodOrMethodContext;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.Unit;
import soot.ValueBox;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.Edge;
import soot.jimple.toolkits.callgraph.Targets;

public class CallGraphContextResolutionCommand implements ISDContextResolutionCommand{

	public List<SootMethod> resolve(CallGraph g, SootMethod topMethod, SootClass clazz, Scene v, Edge e) {
		List<SootMethod> possibleMethods = new ArrayList<>();
		Iterator<MethodOrMethodContext> children;
		Iterator<Edge> testing;
		children = new Targets(g.edgesOutOf(topMethod));
		//testing = g.edgesOutOf(topMethod);
		List<SootMethod> meths = new ArrayList<SootMethod>();
		meths.add((SootMethod)e.getTgt());
		return meths;
		//Iterator<Edge> testingUnit = g.edgesOutOf(u);
		//Unit unit = e.srcUnit();
		//List<ValueBox> vBoxes = unit.getUseBoxes();
		/*while(testingUnit.hasNext()){
			SootMethod aChild = (SootMethod) children.next();
			possibleMethods.add(aChild);
		}*/
		
		/*while(children.hasNext()){
			SootMethod aChild = (SootMethod) children.next();
			possibleMethods.add(aChild);
		}*/
		/*while(testing.hasNext()){
			SootMethod aChild = (SootMethod) testing.next().getTgt();
			possibleMethods.add(aChild);
		}*/
		//return possibleMethods;
	}

}
