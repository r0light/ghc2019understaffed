package template;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Path;

public abstract class Writer {
    public void write(Solution solution, Path path) {
	try (PrintWriter out = new PrintWriter(path.normalize().toString())) {
	    out.println(solution.provideSolution());
	} catch (FileNotFoundException e) {
	    System.err.println(
		    String.format("Could not write a solution to file %s due to the following exception: %s", path, e));
	    System.err.println("The solution would have been as follows:");
	    System.err.println(solution.provideSolution());
	}
    }
}
