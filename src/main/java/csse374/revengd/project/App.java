package csse374.revengd.project;

import csse374.revengd.project.builder.IBuilder;
import csse374.revengd.project.displayer.IDisplayer;
import csse374.revengd.project.parsers.IParser;
import csse374.revengd.project.parsers.MasterParser;
import csse374.revengd.project.parserstrategies.IParserStrategy;

import java.util.List;

public class App {
    public static void main(String[] args){
        Configuration config = new Configuration(args);
        List<IParserStrategy> strategies = config.getStrategies();
        IBuilder builder = config.getBuilder();
        IDisplayer displayer = config.getDisplayer();
        IParser parser = new MasterParser(config.getPath(), strategies);
        parser = config.applyFilters(parser);
        parser = config.applyDetectors(parser);
        Runner runner = new Runner(parser, builder, displayer);
        runner.run(config.getClasses());
    }
}
