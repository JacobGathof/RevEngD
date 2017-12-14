package csse374.revengd.project;

import java.util.List;

public interface IParserFilter extends IParser{
    List<IUMLObject> process(List<IUMLObject> objects);
}
