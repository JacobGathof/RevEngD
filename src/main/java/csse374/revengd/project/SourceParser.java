package csse374.revengd.project;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import csse374.revengd.soot.MainMethodMatcher;
import csse374.revengd.soot.SceneBuilder;
import soot.Hierarchy;
import soot.Scene;
import soot.SootClass;

public class SourceParser implements IParser{
    @Override
    public List<IUMLObject> parse(String path) {
    	List<IUMLObject> umlObjects = new ArrayList<IUMLObject>();
		
    	Scene scene = setupScene(path);
		
		System.out.println("==============================================================");
		System.out.println("Application classes loaded by SOOT:");
		
		scene.getApplicationClasses().forEach(clazz -> {
			
			clazz.getInterfaces().forEach(i ->{
				IUMLObject obj = new ClassUMLObject(clazz, i);
				umlObjects.add(obj);
			});
			
			
			clazz.getMethods().forEach(m->{
				IUMLObject obj = new MethodUMLObject(clazz, m);
				umlObjects.add(obj);
			});
			
			clazz.getFields().forEach(f->{
				IUMLObject obj = new InstanceVariableUMLObject(clazz, f);
				umlObjects.add(obj);
			});
			
		});
		
		//SootClass appClass = scene.getSootClass("csse374.revengd.examples.fixtures.CalculatorApp");
  
		//Hierarchy typeHierarchy = scene.getActiveHierarchy();
		//SootClass iCalculator = scene.getSootClass("csse374.revengd.examples.fixtures.ICalculator");
		//Collection<SootClass> implementors = typeHierarchy.getImplementersOf(iCalculator);
    	
        return umlObjects;
    }
    
    public Scene setupScene(String path) {
    	Scene scene = SceneBuilder.create()
				.addDirectory(path)												
				.setEntryClass("csse374.revengd.project.App")	
				.addEntryPointMatcher(new MainMethodMatcher("csse374.revengd.project.App"))	
				.addExclusions(Arrays.asList("java.*", "javax.*", "sun.*"))
				.addExclusions(Arrays.asList("soot.*", "polygot.*"))
				.addExclusions(Arrays.asList("org.*", "com.*"))	
				.build();	
    	return scene;
    }
}
