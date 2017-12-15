package csse374.revengd.project.builder;

import java.util.List;

import csse374.revengd.project.umlobjects.IUMLObject;

public interface IBuilder {

    String build(List<IUMLObject> objects);
}
