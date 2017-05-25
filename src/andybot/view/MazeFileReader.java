package andybot.view;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class MazeFileReader {

	public List<File> findMazeFiles(String [] pathes ) {
		List<File> files = new ArrayList<>();
		
		for ( int i = 0 ; i < pathes.length; i++) {
			File f = new File(pathes[i]);
			if ( f.isFile()) {
				continue;
			}
			
			findMazeFileinDir(files,  f );
		}
		
		return files;
		
	}
	
	public List<File> findFile(String [] pathes, FileFilter fn) {
		List<File> files = new ArrayList<>();
		
		for ( int i = 0 ; i < pathes.length; i++) {
			File f = new File(pathes[i]);
			if ( f.isFile()) {
				continue;
			}
			
			findFileinDir(files,  f, fn );
		}
		
		return files;
		
	}
	private void findMazeFileinDir(List<File> holder, File dir) {
		Queue<File> queue = new ArrayDeque<>(100);
		queue.add(dir);
		while ( ! queue.isEmpty()) {
			File f= queue.remove();
			if ( f.isDirectory()) {
				File [] children = f.listFiles();
				for (int i = 0; i < children.length; i++) {
					queue.add(children[i]);
				}
			} else {
				if( f.getName().toLowerCase().endsWith(".mz")){
					holder.add(f);
				}
			}
		}
	}
	private void findFileinDir(List<File> holder, File dir, FileFilter fn) {
		Queue<File> queue = new ArrayDeque<>(100);
		queue.add(dir);
		while ( ! queue.isEmpty()) {
			File f= queue.remove();
			if ( f.isDirectory() ) {
				File [] children = f.listFiles();
				for (int i = 0; i < children.length; i++) {
					queue.add(children[i]);
				}
			} else {
				if( fn.accept(f)  ){
					holder.add(f);
				}
			}
		}
	}


}
