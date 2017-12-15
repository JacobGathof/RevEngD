package csse374.revengd.project.parsers;

import java.util.List;

import csse374.revengd.project.umlobjects.IUMLObject;

public interface IParserFilter extends IParser{
    List<IUMLObject> process(List<IUMLObject> objects);
}
