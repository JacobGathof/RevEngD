package csse374.revengd.project.parserstrategies;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import csse374.revengd.project.parserstrategies.resolutioncommands.CallGraphContextResolutionCommand;
import csse374.revengd.project.parserstrategies.resolutioncommands.FirstContextResolutionCommand;
import csse374.revengd.project.parserstrategies.resolutioncommands.HierarchyContextResolutionCommand;
import csse374.revengd.project.parserstrategies.resolutioncommands.ISDContextResolutionCommand;
import csse374.revengd.project.parserstrategies.resolutioncommands.NullContextResolutionCommand;
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
import soot.SootMethodRef;
import soot.Type;
import soot.Unit;
import soot.UnitBox;
import soot.Value;
import soot.ValueBox;
import soot.JastAddJ.AssignExpr;
import soot.baf.internal.BNewInst;
import soot.baf.internal.BSpecialInvokeInst;
import soot.baf.internal.BStaticInvokeInst;
import soot.jimple.AssignStmt;
import soot.jimple.InterfaceInvokeExpr;
import soot.jimple.InvokeExpr;
import soot.jimple.InvokeStmt;
import soot.jimple.NewExpr;
import soot.jimple.SpecialInvokeExpr;
import soot.jimple.VirtualInvokeExpr;
import soot.jimple.internal.AbstractInstanceInvokeExpr;
import soot.jimple.internal.AbstractInterfaceInvokeExpr;
import soot.jimple.internal.JInterfaceInvokeExpr;
import soot.jimple.internal.JSpecialInvokeExpr;
import soot.jimple.internal.JVirtualInvokeExpr;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.ContextSensitiveCallGraph;
import soot.jimple.toolkits.callgraph.Edge;
import soot.jimple.toolkits.callgraph.Targets;
import soot.toolkits.graph.ExceptionalUnitGraph;
import soot.toolkits.graph.UnitGraph;

public class SequenceDiagramParserStrategy implements IParserStrategy {

	private String startMethodName;
	private int depthToLook;
	//private ISDContextResolutionCommand resolveCommand = new CallGraphContextResolutionCommand();
	private ISDContextResolutionCommand resolveCommand = new HierarchyContextResolutionCommand();
	//private ISDContextResolutionCommand resolveCommand = new FirstContextResolutionCommand();
	
	
	public SequenceDiagramParserStrategy (String methodName, int aDepthToLook, ISDContextResolutionCommand command){
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
	
	private List<IUMLObject> examine(CallGraph g, SootMethod rootMethod, SootClass callingClass,  Scene v, int remainingDepth){
		List<IUMLObject> examinationList = new ArrayList<>();
		Iterator<MethodOrMethodContext> children;
		Iterator<Edge> testing;
		//if (rootMethod.hasActiveBody()){
			if (remainingDepth == 0){
				return new ArrayList<>();
			}
			
			children = new Targets(g.edgesOutOf(rootMethod));
			testing = g.edgesOutOf(rootMethod);
			
			List<Edge> IWantToSeeThis = new ArrayList<Edge>();
			/*while(testing.hasNext()){
				IWantToSeeThis.add(testing.next());
			}
			String breakp = "Breakpoint here";
			*/
			while(testing.hasNext()){
				Edge e = testing.next();
				//Unit asdf = e.srcUnit();
				/*List<ValueBox> wut = asdf.getDefBoxes();
				List<UnitBox> wut1 = asdf.getUnitBoxes();
				List<ValueBox> wut2 = asdf.getUseBoxes();
				List<ValueBox> wut3 = asdf.getUseAndDefBoxes();
				MethodOrMethodContext testSrc = e.getSrc();
				MethodOrMethodContext testInt = e.src();
				MethodOrMethodContext test2 = e.tgt();*/
				if (e != null && e.srcUnit() != null){
					Unit unit = e.srcUnit();
					Unit srcUnit = e.srcStmt();
					MethodOrMethodContext comeon = e.getSrc();
					MethodOrMethodContext plez = e.getTgt();
					
					List<ValueBox> vBoxes = unit.getUseBoxes();
					//List<ValueBox> vBoxes = e.srcUnit().getUseBoxes();
					for(int i = 0; i < vBoxes.size(); i++){
						SootMethod method = null;
						
						
						ValueBox a = vBoxes.get(i);
						Value value = a.getValue();
						
						//if(z instanceof JAbstractInvokeExpr)
						
						if(value instanceof InvokeExpr){
							InvokeExpr x = (InvokeExpr) value;
							method = x.getMethod();
							
						}
						/*if(z instanceof AbstractInterfaceInvokeExpr || z instanceof AbstractInstanceInvokeExpr){
						//if(z instanceof JInterfaceInvokeExpr || z instanceof JSpecialInvokeExpr || z instanceof JVirtualInvokeExpr){
							//JInterfaceInvokeExpr x = (JInterfaceInvokeExpr) z;
							InvokeExpr x = (InvokeExpr) z;
							method = x.getMethod();
							List<SootMethod> resolved = resolveCommand.resolve(g, method, method.getDeclaringClass(), v);
							if (!resolved.isEmpty()){
								method = resolved.get(0);
							}
							//method = 
							//method = x.getMethod();
							
							//SootMethodRef plasdf = x.getMethodRef();
							//String afds = "";
						}*/
						if(value instanceof AssignStmt){
							Value x = ((AssignStmt) value).getRightOp();
							if (x instanceof AssignStmt){
								InvokeExpr invkExpr = (InvokeExpr)x;
								method = invkExpr.getMethod();
							}
						}
						
						if (method != null && !method.hasActiveBody()){
							//CallGraphContextResolutionCommand cgRes = new CallGraphContextResolutionCommand();
							
							List<SootMethod> resolved = resolveCommand.resolve(g, method, method.getDeclaringClass(), v, e);
							//List<SootMethod> resolved = cgRes.resolve(g, method, method.getDeclaringClass(), v, e);
							//List<SootMethod> resolved = resolveCommand.resolve(g, rootMethod, rootMethod.getDeclaringClass(), v);
							e.getSrc();
							if (!resolved.isEmpty()){
								method = resolved.get(0);
							}
							
							
						}
						if (method != null){// && method.hasActiveBody()){
						examinationList.add(new SequenceMethodUMLObject(callingClass, method));
						examinationList.addAll(examine(g, method, method.getDeclaringClass(), v, remainingDepth - 1));
						examinationList.add(new ReturnUMLObject(callingClass, method));
						} else if (method != null){
							List<SootMethod> resolved = resolveCommand.resolve(g, method, method.getDeclaringClass(), v, e);
							if (!resolved.isEmpty()){
							//	method = resolved.get(0);
							}
							//resolveCommand.resolve(g, method, method.getDeclaringClass(), v);
						}
	
						
	//					if(stmt instanceof AssignStmt) {
	//						// If a method returns a value, then we should look for AssignStmt whose right hand side is InvokeExpr
	//						Value rightOp = ((AssignStmt) stmt).getRightOp();
	//						if(rightOp instanceof InvokeExpr) {
	//							InvokeExpr invkExpr = (InvokeExpr)rightOp;
	//							SootMethod method = invkExpr.getMethod();				
	//						}
	//					}
						
						//LinkedRValue aasdfaw = (LinkedRValue) a;
						//Type grr = a.getValue().getType();
						//a.getClass();
						//String adsdfasdf = "";
						
					}
				}
//				MethodOrMethodContext testTgt = e.getTgt();
//				String DebugStopper = "lol";
//				examinationList.add(new SequenceMethodUMLObject(callingClass, method));
//				examinationList.addAll(examine(g, method, method.getDeclaringClass(), v, remainingDepth - 1));
//				examinationList.add(new ReturnUMLObject(callingClass, method));
			}

			/*while(children.hasNext()){
				SootMethod aChild = (SootMethod) children.next();
				examinationList.add(new SequenceMethodUMLObject(callingClass, aChild));
				examinationList.addAll(examine(g, aChild, aChild.getDeclaringClass(), v, remainingDepth - 1));
				examinationList.add(new ReturnUMLObject(callingClass, aChild));
			}*/
		//}
		/*else {
			List<SootMethod> concMethods = resolveCommand.resolve(g, rootMethod, callingClass, v);
			for(int i = 0; i < concMethods.size(); i++){
				this.examine(g, concMethods.get(i), callingClass, v, remainingDepth);
			}*/
			/*Hierarchy hierarchy = v.getActiveHierarchy();
			  List<SootMethod> possibleMethods = hierarchy.resolveAbstractDispatch(rootMethod.getDeclaringClass(), rootMethod);
			  for(int i = 0; i < possibleMethods.size(); i++){
				  if (possibleMethods.get(i).hasActiveBody()){
					  examine(g, possibleMethods.get(i), callingClass,  v, remainingDepth);
					  break;
				  }
			  }*/
		//}
		return examinationList;
	}
}