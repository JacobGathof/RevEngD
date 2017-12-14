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
			Path filePath = Paths.get(System.getProperty("user.dir"), "build", "plantuml", "diagram.png");
			Files.createDirectories(filePath.getParent());
				
			OutputStream outStream = new FileOutputStream(filePath.toFile());
			FileFormatOption option = new FileFormatOption(FileFormat.PNG, false);
			reader.outputImage(outStream, option);
			
			Desktop.getDesktop().open(filePath.toFile());
			
		} catch (Exception e) {
			
		}

    }
    
}
