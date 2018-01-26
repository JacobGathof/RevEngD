package csse374.revengd.project.parsers;

import java.util.*;

import csse374.revengd.project.Configuration;
import csse374.revengd.project.parserstrategies.IParserStrategy;
import csse374.revengd.project.umlobjects.IUMLObject;
import csse374.revengd.soot.SceneBuilder;
import soot.*;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.ContextSensitiveCallGraph;
import soot.jimple.toolkits.callgraph.Targets;
import soot.options.Options;

public class MasterParser implements IParser{
	Scene v;
	List<IParserStrategy> strategies;
	Set<SootClass> visited;

	public MasterParser(String path, List<IParserStrategy> strategies){
		this.strategies = strategies;
		this.visited = new HashSet<>();

		this.v = Scene.v();

		Options options = Options.v();
		PhaseOptions po = PhaseOptions.v();

		options.set_verbose(false);
		options.set_keep_line_number(true);
		options.set_src_prec(Options.src_prec_class);
		options.set_prepend_classpath(true);

		po.setPhaseOption("bb", "off");
		po.setPhaseOption("tag.ln", "on");
		po.setPhaseOption("jj.a", "on");
		po.setPhaseOption("jj.ule", "on");
		po.setPhaseOption("cg.spark", "on");

		options.set_whole_program(true);
		options.set_no_bodies_for_excluded(true);
		options.set_exclude(Arrays.asList("soot.*", "polygot.*"));
		options.set_allow_phantom_refs(true);

		v.setSootClassPath(v.defaultClassPath());
		if(path != null) {
			v.extendSootClassPath(path);
		}


	}

    @Override
    public List<IUMLObject> parse(String className) {
    	List<IUMLObject> umlObjects;

    	SootClass clazz = v.loadClassAndSupport(className);
    	//clazz.setApplicationClass();
    	//v.setMainClass(clazz);
    	//v.loadNecessaryClasses();
		/*
    	List<SootMethod> points = new ArrayList<>();
    	points.add(clazz.getMethodByName("main"));
		v.setEntryPoints(points);
		PackManager.v().runPacks();
		CallGraph g = v.getCallGraph();
		Iterator<MethodOrMethodContext> children = new Targets(g.edgesOutOf(points.get(0)));
		*/
		umlObjects = parseHelper(clazz);

        return umlObjects;
    }

    private List<IUMLObject> parseHelper(SootClass clazz){
    	ArrayList<IUMLObject> umlObjects = new ArrayList<>();
    	ArrayList<SootClass> dependencies = new ArrayList<>();

    	if(clazz == null){
    		return umlObjects;
		}

		for(IParserStrategy strategy : strategies){
    		umlObjects.addAll(strategy.parse(clazz, dependencies, v));
		}

		for(SootClass c : dependencies){
			if(!visited.contains(c)) {
				visited.add(c);
				umlObjects.addAll(parseHelper(c));
			}
		}

		return umlObjects;
	}
}
