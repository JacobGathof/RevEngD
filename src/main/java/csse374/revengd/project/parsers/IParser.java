package csse374.revengd.project.parsers;

import java.util.List;

import csse374.revengd.project.Configuration;
import csse374.revengd.project.umlobjects.IUMLObject;

public interface IParser {
    List<IUMLObject> parse(String className);
}
