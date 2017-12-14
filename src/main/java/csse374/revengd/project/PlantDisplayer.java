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
			Path filePath = Paths.get("./", "build", "plantuml", "diagram.png");
			System.out.println(filePath);
			Files.createDirectories(filePath.getParent());
				
			OutputStream outStream = new FileOutputStream(filePath.toFile());
			FileFormatOption option = new FileFormatOption(FileFormat.PNG, false);
			reader.outputImage(outStream, option);

			if(Desktop.isDesktopSupported()) {
				Desktop.getDesktop().open(filePath.toFile());
				//Files.deleteIfExists(filePath);
			}
			
		} catch (Exception e) {
			
		}

    }
    
}
