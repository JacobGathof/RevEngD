package csse374.revengd.project;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import csse374.revengd.soot.MainMethodMatcher;
import csse374.revengd.soot.SceneBuilder;
import soot.*;
import soot.javaToJimple.IInitialResolver;
import soot.options.Options;
import soot.util.Chain;

public class MasterParser implements IParser{
	Scene v;

	public MasterParser(String path){
		this.v = Scene.v();

		Options options = Options.v();
		options.set_no_bodies_for_excluded(true);
		options.set_exclude(Arrays.asList("soot.*", "polygot.*"));
		options.set_allow_phantom_refs(true);

		v.setSootClassPath(v.defaultClassPath());
		v.extendSootClassPath(path);
	}

    @Override
    public List<IUMLObject> parse(String className, String[] args) {
    	List<IUMLObject> umlObjects;

    	SootClass clazz = v.loadClassAndSupport(className);
		umlObjects = parseHelper(clazz);

        return umlObjects;
    }

    private List<IUMLObject> parseHelper(SootClass clazz){
    	ArrayList<IUMLObject> umlObjects = new ArrayList<>();
    	ArrayList<SootClass> dependencies = new ArrayList<>();

    	if(clazz == null){
    		return umlObjects;
		}

		//TODO: Strategy Pattern
		if(clazz.hasSuperclass()) {
			dependencies.add(clazz.getSuperclass());
			umlObjects.add(new ClassUMLObject(clazz.getSuperclass()));
			umlObjects.add(new InheritanceRelationUMLObject(clazz, clazz.getSuperclass()));
		}

		clazz.getInterfaces().forEach(i ->{
			IUMLObject inter = new InterfaceUMLObject(i);
			IUMLObject obj = new InheritanceRelationUMLObject(clazz, i);
			umlObjects.add(obj);
			umlObjects.add(inter);
			dependencies.add(i);
		});

		clazz.getMethods().forEach(m->{
			IUMLObject obj = new MethodUMLObject(clazz, m);
			umlObjects.add(obj);
		});

		clazz.getFields().forEach(f->{
			IUMLObject obj = new InstanceVariableUMLObject(clazz, f);
			umlObjects.add(obj);
		});

		for(SootClass c : dependencies){
			umlObjects.addAll(parseHelper(c));
		}

		return umlObjects;
	}
    
    public Scene setupScene(String path) {
    	Scene scene = SceneBuilder.create()
				.addDirectory(path)
				.addExclusions(Arrays.asList("soot.*", "polygot.*"))
				.addExclusions(Arrays.asList("org.*", "com.*"))
				.build();	
    	return scene;
    }
}
