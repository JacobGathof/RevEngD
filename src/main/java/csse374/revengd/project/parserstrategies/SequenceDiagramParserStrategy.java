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
		//SootClass clazz = v.loadClassAndSupport(className);
	    clazz.setApplicationClass();
	    v.setMainClass(clazz);
	    v.loadNecessaryClasses();
	    
	    //List<SootMethod> points = new ArrayList<>();
	    //points.add(clazz.getMethodByName(startMethodName));
	    //v.setEntryPoints(points);
	    
	    PackManager.v().runPacks();
	    CallGraph g = v.getCallGraph();
	    //Iterator<MethodOrMethodContext> children = new Targets(g.edgesOutOf(points.get(0)));
	    
		SootMethod startMethod = clazz.getMethodByName(startMethodName);
		List<SootMethod> startMethods = new ArrayList<SootMethod>();
		startMethods.add(startMethod);
		v.setEntryPoints(startMethods);
		
		//ContextSensitiveCallGraph graph = v.getContextSensitiveCallGraph();
		
		Body body = startMethod.getActiveBody();//.retrieveActiveBody();
		UnitGraph unGraph = new ExceptionalUnitGraph(body);
		Iterator<Unit> stmtIt = unGraph.iterator();
		System.out.println("about to examine");
		List<IUMLObject> temp = examine(g, startMethod, clazz, depthToLook, v/*, stmtIt*/);
		return temp;
	}
	
	private List<IUMLObject> examine(CallGraph g, SootMethod rootMethod, SootClass callingClass, int remainingDepth, Scene v/*, Iterator<Unit> stmtIt*/){
		List<IUMLObject> examinationList = new ArrayList<IUMLObject>();
		//System.out.println("Inside examine");
		Iterator<MethodOrMethodContext> children = null;
		//System.out.println(method.getName());
		if (rootMethod.hasActiveBody()){
			//System.out.println("Method has active body");
			Body body = rootMethod.getActiveBody();//.retrieveActiveBody();
			UnitGraph unGraph = new ExceptionalUnitGraph(body);
			Iterator<Unit> stmtIt = unGraph.iterator();
			SootMethod method = null;
			
			if (remainingDepth == 0){
				return new ArrayList<IUMLObject>();
			}
			//System.out.println(unGraph.toString());
			//System.out.println("CallGraph: " + g.toString());

			//System.out.println(g.toString() + "\n\n");
			//for(Unit stmt: unGraph){
			while(stmtIt.hasNext()){
				Unit stmt = stmtIt.next();
				//System.out.println("Unit: " + stmt.toString());
				Iterator<Edge> tempasdf = g.edgesOutOf(stmt);
				
				if(stmt instanceof AssignStmt){
					//if(stmt instanceof AssignStmt) {
					Value rightOp = ((AssignStmt) stmt).getRightOp();
					if (rightOp instanceof InvokeExpr){
						//System.out.println("in assignment statement");
						InvokeExpr temp = (InvokeExpr) rightOp;
						method = temp.getMethod();
						children = new Targets (g.edgesOutOf(rootMethod));//g.edgesOutOf(method);
						//method = temp.getMethod();
						//edges = g.edgesOutOf(targetMethod);
					}
					if (stmt instanceof BSpecialInvokeInst)
					{
						System.out.println("BSpecialInvokeInst");
					
						//System.out.println("BSpecialInvokeInst");
						method = ((BSpecialInvokeInst) stmt).getMethod();
					
						//Iterator<Edge>
						children = new Targets(g.edgesOutOf(rootMethod));
					}
					if (stmt instanceof BStaticInvokeInst){
						System.out.println("BStaticInvokeInst");
						children = new Targets(g.edgesOutOf(((BStaticInvokeInst) stmt).getMethod()));
					}
					if(stmt instanceof InvokeStmt){
			            InvokeExpr IE = ((InvokeStmt)stmt).getInvokeExpr();
			            SootMethod aMethod = IE.getMethod();
			            children = new Targets(g.edgesOutOf(aMethod));
			        }
				}
				if (stmt instanceof BSpecialInvokeInst)
				{
					System.out.println("BSpecialInvokeInst");
				
					//System.out.println("BSpecialInvokeInst");
					method = ((BSpecialInvokeInst) stmt).getMethod();
				
					//Iterator<Edge>
					children = new Targets(g.edgesOutOf(rootMethod));
				}
				if (stmt instanceof BStaticInvokeInst){
					System.out.println("BStaticInvokeInst");
					children = new Targets(g.edgesOutOf(((BStaticInvokeInst) stmt).getMethod()));
				}
				if(stmt instanceof InvokeStmt){
		            InvokeExpr IE = ((InvokeStmt)stmt).getInvokeExpr();
		            SootMethod aMethod = IE.getMethod();
		            children = new Targets(g.edgesOutOf(aMethod));
		        }
		        /*else if(stmt instanceof AssignStmt){
		            AssignStmt AS = (AssignStmt)stmt;
		            
		        }

		        else if(stmt instanceof BNewInst){
		            BNewInst NS = (BNewInst)stmt;
		            NS.get
		            dependecies.add(NS.getOpType().toString());
		        }*/
			
					
				
				
				//if (method!= null){
					//Iterator<MethodOrMethodContext> children = new Targets (g.edgesOutOf(rootMethod));
					while(children!= null && children.hasNext()){
						SootMethod aChild = (SootMethod) children.next();
						
						//if (!aChild.hasActiveBody()){
							//void performCHAPointerAnalysis(Scene scene, SootMethod method) {
								  //System.out.println("Performing CHA analysis for " + method.getName() + "() ...");
								  /*Hierarchy hierarchy = v.getActiveHierarchy();
								  List<SootMethod> possibleMethods = hierarchy.resolveAbstractDispatch(method.getDeclaringClass(), method);
								  int i = 0;
								  for(i = 0; i < possibleMethods.size(); i++){
									  if (possibleMethods.get(i).hasActiveBody()){
										  aChild = possibleMethods.get(i);
										  break;
									  }
								  }*/
								  
								  
								  //examinationList.add(possibleMethods.iterator().next());
								 // this.prettyPrintMethods("CHA resolution for", method, possibleMethods);
	
						//}
						//System.out.println("root: " + rootMethod.getName());
						//System.out.println("aChild: " + aChild.getName());
						
						examinationList.add(new ReturnUMLObject(callingClass, aChild));
						
						examinationList.addAll(examine(g, aChild, aChild.getDeclaringClass(), remainingDepth - 1,v));
						
						examinationList.add(new SequenceMethodUMLObject(callingClass, aChild));
						
					}
				}
			}
		//}
		else {
			Hierarchy hierarchy = v.getActiveHierarchy();
			  List<SootMethod> possibleMethods = hierarchy.resolveAbstractDispatch(rootMethod.getDeclaringClass(), rootMethod);
			  int i = 0;
			  for(i = 0; i < possibleMethods.size(); i++){
				  if (possibleMethods.get(i).hasActiveBody()){
					  //aChild = possibleMethods.get(i);
					  examine(g, possibleMethods.get(i), callingClass, remainingDepth, v/*, Iterator<Unit> stmtIt*/);
					  break;
				  }
			  }
		}
		return examinationList;
	}
}
				
/*
				
				
				
				//Iterator<Edge> edgeIt = g.edgesOutOf(unit);
				//Iterator<MethodOrMethodContext> tar = new Targets (edgeIt);
				//System.out.println("Inside edgeIt: " + edgeIt.toString());

				//System.out.println("got edge iterator");
				while(edgeIt.hasNext()){
				//while(tar.hasNext()){
					System.out.println("Inside edgeIt");
					MethodOrMethodContext methodOrC = edgeIt.next().getTgt();
					SootMethod target = methodOrC.method();
					System.out.println(target.method().toString());
					if(target != null){
						//examine(g, target, remainingDepth-1);
						
						examinationList.add(new ReturnUMLObject(callingClass, target));
						
						examinationList.addAll(examine(g, target, target.getDeclaringClass(), remainingDepth - 1));
						
						examinationList.add(new SequenceMethodUMLObject(callingClass, target));
					}
				}
			}


		
		
		
		stmtIt = unGraph.iterator();
		while(stmtIt.hasNext()){
			Unit stmt = stmtIt.next();
			
			SootMethod wut;
			Iterator<Edge> edges = null;
		
			if(stmt instanceof AssignStmt){
				//if(stmt instanceof AssignStmt) {
					Value rightOp = ((AssignStmt) stmt).getRightOp();
					if (rightOp instanceof InvokeExpr){
						InvokeExpr temp = (InvokeExpr) rightOp;
						SootMethod targetMethod = temp.getMethod();
						method = temp.getMethod();
						edges = g.edgesOutOf(targetMethod);
					}
					if (rightOp instanceof NewExpr){
						NewExpr temp = (NewExpr) rightOp;
						//SootMethod targetMethod = temp.
					}
				}
			if(stmt instanceof InvokeStmt){
				//System.out.println("BStaticInvokeInst");
				if(stmt instanceof AssignStmt) {
					// If a method returns a value, then we should look for AssignStmt whose right hand side is InvokeExpr
					//Value rightOp = 
					edges = (graph.edgesOutOf(((BStaticInvokeInst) stmt).getMethod()));
					if(rightOp instanceof InvokeExpr) {
						InvokeExpr invkExpr = (InvokeExpr)rightOp;
						SootMethod targetMethod = invkExpr.getMethod();
						edges = (graph.edgesOutOf(targetMethod));
					}
				
			}
			}
		
			
			
			if (stmt instanceof BSpecialInvokeInst)
			{
				
			
				//System.out.println("BSpecialInvokeInst");
				method = ((BSpecialInvokeInst) stmt).getMethod();
			
				//Iterator<Edge>
				edges = (g.edgesOutOf(wut));
		
				//Iterator<Edge> edges = (graph.edgesOutOf(stmt));
				while(edges != null && edges.hasNext()){
					Edge edge = edges.next();
					MethodOrMethodContext methodOrCntxt = edge.getTgt();
					SootMethod targetMethod = methodOrCntxt.method();
					
					examinationList.add(new ReturnUMLObject(callingClass, targetMethod));
					
					examinationList.addAll(examine(graph, targetMethod, targetMethod.getDeclaringClass(), remainingDepth - 1, stmtIt));
					
					examinationList.add(new SequenceMethodUMLObject(callingClass, targetMethod));
				}
			}
			while(edges != null && edges.hasNext()){
				Edge edge = edges.next();
				MethodOrMethodContext methodOrCntxt = edge.getTgt();
				SootMethod targetMethod = methodOrCntxt.method();
				
				examinationList.add(new ReturnUMLObject(callingClass, targetMethod));
				
				examinationList.addAll(examine(g, targetMethod, targetMethod.getDeclaringClass(), remainingDepth - 1, stmtIt));
				
				examinationList.add(new SequenceMethodUMLObject(callingClass, targetMethod));
			}
		}
		//graph.ed
		unGraph.forEach(stmt -> {
			if(stmt instanceof AssignStmt) {
				String temp = "wut";
			}
			if(stmt instanceof InvokeExpr) {
				InvokeExpr invkExpr = (InvokeExpr)stmt;
				SootMethod myMethod = invkExpr.getMethod();
				examinationList.add(new ReturnUMLObject(callingClass, myMethod));
				
				examinationList.addAll(examine(graph, myMethod, myMethod.getDeclaringClass(), remainingDepth - 1));

				examinationList.add(new SequenceMethodUMLObject(callingClass, myMethod));

			}

				
		});
		
		
		//examinationList.add(new SequenceMethodUMLObject(callingClazz, method));
		Iterator<MethodOrMethodContext> children = new Targets (g.edgesOutOf(method));
		while(children!= null && children.hasNext()){
			SootMethod aChild = (SootMethod) children.next();
			
			if (!aChild.isConcrete()){
				//void performCHAPointerAnalysis(Scene scene, SootMethod method) {
					  //System.out.println("Performing CHA analysis for " + method.getName() + "() ...");
					  Hierarchy hierarchy = v.getActiveHierarchy();
					  Collection possibleMethods = hierarchy.resolveAbstractDispatch(method.getDeclaringClass(), method);
					  examinationList.add(possibleMethods.iterator().next())
					 // this.prettyPrintMethods("CHA resolution for", method, possibleMethods);

			}
			examinationList.add(new ReturnUMLObject(callingClass, aChild));
			
			examinationList.addAll(examine(g, aChild, aChild.getDeclaringClass(), remainingDepth - 1));
			
			examinationList.add(new SequenceMethodUMLObject(callingClass, aChild));
			
		}
		return examinationList;
	}


}
*/