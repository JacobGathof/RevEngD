package csse374.revengd.project;

import java.util.List;

public interface IParser {
    List<IUMLObject> parse(String path);
}
