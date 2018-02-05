package csse374.revengd.project.umlobjects;

import soot.SootClass;

import java.util.List;

public class AbstractClassUMLObject implements IUMLObject{
    SootClass abstr;

    public AbstractClassUMLObject(SootClass i){
        abstr = i;
    }

    @Override
    public String toUML(boolean full) {
        if(full) {
            return "abstract " + abstr.getName();
        }
        else{
            return "abstract " + abstr.getShortName();
        }
    }

    @Override
    public int getModifiers() {
        return abstr.getModifiers();
    }

    @Override
    public SootClass getSootClass() {
        return abstr;
    }

    @Override
    public List<String> getPackage() {
        return PackageHelper.getPackageNames(abstr.getName());
    }
}
