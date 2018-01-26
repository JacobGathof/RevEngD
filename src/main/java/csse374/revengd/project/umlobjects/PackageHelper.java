package csse374.revengd.project.umlobjects;

import java.util.ArrayList;
import java.util.List;

public class PackageHelper {

	public static List<String> getPackageNames(String ...strings){
		List<String> str = new ArrayList<>();
		for(String s : strings) {
			int index = s.lastIndexOf('.');
			if(index < 0) continue;
			str.add(s.substring(0, index));
		}
		return str;
	}
}
