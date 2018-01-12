package csse374.revengd.project.parserstrategies;

import csse374.revengd.project.umlobjects.DependencyUMLObject;
import csse374.revengd.project.umlobjects.IUMLObject;
import soot.*;
import soot.baf.internal.BNewInst;
import soot.jimple.AssignStmt;
import soot.jimple.InvokeExpr;
import soot.jimple.InvokeStmt;
import soot.toolkits.graph.ExceptionalUnitGraph;

import java.util.ArrayList;
import java.util.List;

public class LocalVariableDependencyParserStrategy implements IParserStrategy{
    boolean packsRun = false;
    @Override
    public List<IUMLObject> parse(SootClass clazz, List<SootClass> dependencies, Scene v) {
        List<IUMLObject> umlObjects = new ArrayList<>();
        if(clazz.toString() == "void" || clazz.toString() == "word"){
            return umlObjects;
        }
        clazz.setApplicationClass();
        v.loadNecessaryClasses();
        List<SootMethod> methods = clazz.getMethods();
        v.setEntryPoints(methods);
        if(!packsRun) {
            PackManager.v().runPacks();
            packsRun = !packsRun;
        }

        for(SootMethod method : methods){
            if(method.toString().contains("<init>")){
                continue;
            }
            if(method.hasActiveBody()) {
                ExceptionalUnitGraph eg = new ExceptionalUnitGraph(method.getActiveBody());
                for(Unit u : eg){
                    ArrayList<String> localTypes = searchUnit(u);
                    for(String localType : localTypes){
                        SootClass localClazz = v.loadClassAndSupport(localType);
                        umlObjects.add(new DependencyUMLObject(clazz, localClazz, plural(localClazz)));
                        dependencies.add(dePluralizedClass(localType, v));
                    }
                }



            }
        }
        return umlObjects;
    }

    private boolean plural(SootClass clazz){
        return clazz.toString().endsWith("[]");
    }

    private SootClass dePluralizedClass(String type, Scene v){
        if(type.endsWith("[]")) {
            return dePluralizedClass(type.substring(0, type.length() - 2), v);
        }
        else{
            return v.loadClassAndSupport(type);
        }
    }

    private ArrayList<String> searchUnit(Unit u){
        ArrayList<String> dependecies = new ArrayList<>();
        if(u instanceof InvokeStmt){
            InvokeExpr IE = ((InvokeStmt)u).getInvokeExpr();
            for(Value v : IE.getArgs()){
                dependecies.add(v.getType().toString());
            }
        }
        else if(u instanceof AssignStmt){
            AssignStmt AS = (AssignStmt)u;
            dependecies.add(AS.getLeftOp().getType().toString());
            dependecies.add(AS.getRightOp().getType().toString());
        }

        else if(u instanceof BNewInst){
            BNewInst NS = (BNewInst)u;
            dependecies.add(NS.getOpType().toString());
        }

        return dependecies;
    }
}
