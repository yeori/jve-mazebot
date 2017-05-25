package loading.pathfinder;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import andybot.model.path.IPathFinder;
import andybot.view.MazeFileReader;

public class LoadingPathFinder {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
		String [] pathes = System.getProperty("java.class.path").split(File.pathSeparator);
		String s = String.join("\n", pathes);
		System.out.println(s);
		
		String clsName = "southbot.SouthMovement";
		Class<?> cls = Class.forName(clsName);
		System.out.println(IPathFinder.class.isAssignableFrom(cls) );
		
		MazeFileReader reader = new MazeFileReader();
		List<File> classFiles = reader.findFile(pathes, f -> {
			if ( !f.getName().endsWith(".class") ) {
				return false;
			}
			return true;
		});

		File f = new File(".");
		
	}

}
