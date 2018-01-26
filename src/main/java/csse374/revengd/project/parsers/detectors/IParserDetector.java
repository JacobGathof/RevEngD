package csse374.revengd.project.parsers.detectors;

import csse374.revengd.project.parsers.IParser;
import csse374.revengd.project.umlobjects.IUMLObject;

import java.util.List;

public interface IParserDetector extends IParser {
    List<IUMLObject> detect(List<IUMLObject> objects);
}
