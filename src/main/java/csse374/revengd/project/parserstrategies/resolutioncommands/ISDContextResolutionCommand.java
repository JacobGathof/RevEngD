package csse374.revengd.project.parserstrategies.resolutioncommands;

import java.util.List;
import csse374.revengd.project.umlobjects.IUMLObject;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.Edge;

public interface ISDContextResolutionCommand {
  List<SootMethod> resolve(CallGraph g, SootMethod topMethod, SootClass clazz, Scene v, Edge e);
}
