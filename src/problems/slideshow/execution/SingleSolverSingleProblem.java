package problems.slideshow.execution;

import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import problems.slideshow.SlideshowParser;
import problems.slideshow.SlideshowProblem;
import problems.slideshow.SlideshowSolution;
import problems.slideshow.SlideshowWriter;
import problems.slideshow.solvers.SlideshowSolver;
import problems.slideshow.solvers.SlideshowSolver2;

public class SingleSolverSingleProblem {

	public static void main(String[] args) throws Exception {

		int overallScore = 0;
		long overallTime = 0;

		SlideshowSolver solver = new SlideshowSolver2();
		System.out.println(solver.getClass().toString());

		Map<Path, Path> files = Conf.listFiles();

		for (Path input : Conf.getInputFiles()) {
			long start = System.nanoTime();
			Path output = files.get(input);
			SlideshowProblem problem = new SlideshowParser().parse(input);
			SlideshowSolution solution = (SlideshowSolution) solver.solve(problem);
			overallScore += solution.score();
			new SlideshowWriter().write(solution, output);
			long elapsedTime = TimeUnit.SECONDS.convert(System.nanoTime() - start, TimeUnit.NANOSECONDS);
			overallTime += elapsedTime;
			System.out.println(input.getFileName().toString() + " == " + elapsedTime + "s ==> " + solution.score());
		}
		System.out.println("Final score: " + overallScore);
		System.out.println("Overall time: " + overallTime + "s");
	}
}
