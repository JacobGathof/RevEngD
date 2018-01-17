package csse374.revengd.project.parserstrategies;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import csse374.revengd.project.umlobjects.IUMLObject;
import csse374.revengd.project.umlobjects.MethodUMLObject;
import csse374.revengd.project.umlobjects.ReturnUMLObject;
import csse374.revengd.project.umlobjects.SequenceMethodUMLObject;
import soot.Body;
import soot.Hierarchy;
import soot.MethodOrMethodContext;
import soot.PackManager;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.Unit;
import soot.Value;
import soot.baf.internal.BNewInst;
import soot.baf.internal.BSpecialInvokeInst;
import soot.baf.internal.BStaticInvokeInst;
import soot.jimple.AssignStmt;
import soot.jimple.InvokeExpr;
import soot.jimple.InvokeStmt;
import soot.jimple.NewExpr;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.ContextSensitiveCallGraph;
import soot.jimple.toolkits.callgraph.Edge;
import soot.jimple.toolkits.callgraph.Targets;
import soot.toolkits.graph.ExceptionalUnitGraph;
import soot.toolkits.graph.UnitGraph;

public class SequenceDiagramParserStrategy implements IParserStrategy {

	private String startMethodName;
	int depthToLook;
	
	public SequenceDiagramParserStrategy (String methodName, int aDepthToLook){
		startMethodName = methodName;
		depthToLook = aDepthToLook;
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
		List<IUMLObject> temp = examine(g, startMethod, clazz, depthToLook, v);
		return temp;
	}
	
	private List<IUMLObject> examine(CallGraph g, SootMethod rootMethod, SootClass callingClass, int remainingDepth, Scene v/*, Iterator<Unit> stmtIt*/){
		List<IUMLObject> examinationList = new ArrayList<>();
		Iterator<MethodOrMethodContext> children;
		if (rootMethod.hasActiveBody()){
			if (remainingDepth == 0){
				return new ArrayList<>();
			}
			children = new Targets(g.edgesOutOf(rootMethod));

			while(children.hasNext()){
				SootMethod aChild = (SootMethod) children.next();
				examinationList.add(new SequenceMethodUMLObject(callingClass, aChild));
				examinationList.addAll(examine(g, aChild, aChild.getDeclaringClass(), remainingDepth - 1,v));
				examinationList.add(new ReturnUMLObject(callingClass, aChild));
			}
		}
		else {
			Hierarchy hierarchy = v.getActiveHierarchy();
			  List<SootMethod> possibleMethods = hierarchy.resolveAbstractDispatch(rootMethod.getDeclaringClass(), rootMethod);
			  for(int i = 0; i < possibleMethods.size(); i++){
				  if (possibleMethods.get(i).hasActiveBody()){
					  examine(g, possibleMethods.get(i), callingClass, remainingDepth, v);
					  break;
				  }
			  }
		}
		return examinationList;
	}
}