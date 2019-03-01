package problems.slideshow.execution;

import java.nio.file.Path;
import java.util.Map;

import problems.slideshow.SlideshowParser;
import problems.slideshow.SlideshowProblem;
import problems.slideshow.SlideshowSolution;
import problems.slideshow.SlideshowWriter;
import problems.slideshow.solvers.SlideshowSolver;
import problems.slideshow.solvers.SlideshowSolver2;

public class SingleSolverSingleProblem {

	public static void main(String[] args) throws Exception {

		int overallScore = 0;

		SlideshowSolver solver = new SlideshowSolver2();
		System.out.println(solver.getClass().toString());

		Map<Path, Path> files = Conf.listFiles();

		for (Path input : Conf.getInputFiles()) {
			Path output = files.get(input);
			SlideshowProblem problem = new SlideshowParser().parse(input);
			SlideshowSolution solution = (SlideshowSolution) solver.solve(problem);
			System.out.println(input.getFileName().toString() + " => " + solution.score());
			overallScore += solution.score();
			new SlideshowWriter().write(solution, output);
		}
		System.out.println("Final score: " + overallScore);
	}
}
