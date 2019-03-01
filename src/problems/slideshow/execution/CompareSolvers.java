package problems.slideshow.execution;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import problems.slideshow.SlideshowParser;
import problems.slideshow.SlideshowProblem;
import problems.slideshow.SlideshowSolution;
import problems.slideshow.SlideshowWriter;
import problems.slideshow.solvers.SlideshowSolver;
import problems.slideshow.solvers.SlideshowSolver2;
import template.Writer;

public class CompareSolvers {

	public static void main(String[] args) throws Exception {

		int overallScore = 0;

		Map<Path, Path> files = Conf.listFiles();

		List<SlideshowSolver> solvers = new ArrayList<SlideshowSolver>();
		// solvers.add(new SlideshowSolver1());
		solvers.add(new SlideshowSolver2());
		// solvers.add(new SlideshowSolver3());

		for (Path input : Conf.getInputFiles()) {
			Path output = files.get(input);
			System.out.println("****" + input.getFileName().toString() + "****");
			SlideshowSolver bestSolver = null;
			SlideshowSolution bestSolution = null;
			int bestScore = Integer.MIN_VALUE;
			for (SlideshowSolver solver : solvers) {
				SlideshowProblem problem = new SlideshowParser().parse(input);
				SlideshowSolution solution = (SlideshowSolution) solver.solve(problem);
				System.out.println(String.format("%s => %d", solver.getClass().toString(), solution.score()));
				if (solution.score() > bestScore) {
					bestSolution = solution;
					bestScore = solution.score();
					bestSolver = solver;
				}
			}
			System.out.println(
					String.format("Best solver: %s with score of %d", bestSolver.getClass(), bestSolution.score()));
			overallScore += bestSolution.score();
			Writer writer = new SlideshowWriter();
			writer.write(bestSolution, output);
			// System.out.println(String.format("Solved! Writing output to %s", output));
			// writer.write(solution, output);
			// System.out.println("overall score so far: " + overallScore);
		}
		System.out.println("****" + overallScore + "****");
	}
}
