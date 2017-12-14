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
import soot.util.Chain;

public class SourceParser implements IParser{
	int depth;

	public SourceParser(int depth){
		this.depth = depth;
	}

    @Override
    public List<IUMLObject> parse(String path) {
    	List<IUMLObject> umlObjects;

		SootClass clazz = Scene.v().loadClassAndSupport(path);

		umlObjects = parseHelper(clazz, 0, depth);

        return umlObjects;
    }

    private List<IUMLObject> parseHelper(SootClass clazz, int curDepth, int goalDepth){
    	ArrayList<IUMLObject> umlObjects = new ArrayList<>();
    	ArrayList<SootClass> dependencies = new ArrayList<>();

    	if(clazz == null || (curDepth == goalDepth + 1 && goalDepth != -1)){
    		return umlObjects;
		}

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
			umlObjects.addAll(parseHelper(c, curDepth + 1, goalDepth));
		}

		return umlObjects;
	}
    
    public Scene setupScene(String path) {
    	Scene scene = SceneBuilder.create()
				.addDirectory(path)												
				.setEntryClass("path")
				.addExclusions(Arrays.asList("soot.*", "polygot.*"))
				.addExclusions(Arrays.asList("org.*", "com.*"))	
				.build();	
    	return scene;
    }
}
