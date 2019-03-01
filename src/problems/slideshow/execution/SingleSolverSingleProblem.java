package problems.slideshow.execution;

import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

import problems.slideshow.SlideshowParser;
import problems.slideshow.SlideshowProblem;
import problems.slideshow.SlideshowSolution;
import problems.slideshow.SlideshowWriter;
import problems.slideshow.solvers.SlideshowSolver;
import problems.slideshow.solvers.SlideshowSolver2;

public class SingleSolverSingleProblem {

	public static void main(String[] args) throws Exception {

		Path input = Conf.eInput;
		Path output = Conf.eOutput;
//		SlideshowSolver solver = new BeeSolver();
		SlideshowSolver solver = new SlideshowSolver2();

		System.out.println(solver.getClass().toString());
		long start = System.nanoTime();
		SlideshowProblem problem = new SlideshowParser().parse(input);
		SlideshowSolution solution = (SlideshowSolution) solver.solve(problem);
		new SlideshowWriter().write(solution, output);
		long elapsedTime = TimeUnit.SECONDS.convert(System.nanoTime() - start, TimeUnit.NANOSECONDS);
		System.out.println(input.getFileName().toString() + " == " + elapsedTime + "s ==> " + solution.score());
	}
}
