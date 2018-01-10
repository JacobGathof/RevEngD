package csse374.revengd.project.parsers;

import java.util.*;

import csse374.revengd.project.parserstrategies.IParserStrategy;
import csse374.revengd.project.umlobjects.IUMLObject;
import csse374.revengd.soot.SceneBuilder;
import soot.*;
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

		options.set_whole_program(true);
		options.set_no_bodies_for_excluded(true);
		options.set_exclude(Arrays.asList("soot.*", "polygot.*"));
		options.set_allow_phantom_refs(true);

		v.setSootClassPath(v.defaultClassPath());
		v.extendSootClassPath(path);
	}

    @Override
    public List<IUMLObject> parse(String className, String[] args) {
    	SootClass clazz = v.loadClassAndSupport(className);
    	//v.getClasses();
		List<IUMLObject> umlObjects = parseHelper(clazz);

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
			if(!visited.contains(c) && !c.toString().contains("java")) {
				visited.add(c);
				umlObjects.addAll(parseHelper(c));
			}
		}

		return umlObjects;
	}
}
