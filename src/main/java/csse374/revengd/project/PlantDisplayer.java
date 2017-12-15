package csse374.revengd.project;

import java.awt.Desktop;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import net.sourceforge.plantuml.*;

public class PlantDisplayer implements IDisplayer{
    @Override
    public void display(String UML) {

    	SourceStringReader reader = new SourceStringReader(UML);
		try {
			System.out.println(UML);
			Path filePath = Paths.get("./", "build", "plantuml", "diagram.svg");
			System.out.println(filePath);
			Files.createDirectories(filePath.getParent());
				
			OutputStream outStream = new FileOutputStream(filePath.toFile());
			FileFormatOption option = new FileFormatOption(FileFormat.SVG, false);
			reader.outputImage(outStream, option);

			if(Desktop.isDesktopSupported()) {
				Desktop.getDesktop().open(filePath.toFile());
			}
			
		} catch (Exception e) {
			
		}

    }
    
}
