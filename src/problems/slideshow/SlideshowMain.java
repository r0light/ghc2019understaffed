package problems.slideshow;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import problems.slideshow.solvers.SlideshowSolver;
import problems.slideshow.solvers.SlideshowSolver2;
import template.Writer;

public class SlideshowMain {

	public static void main(String[] args) throws Exception {

		final String folder = "problems/slideshow/";
		int overallScore = 0;

		Map<Path, Path> files = new HashMap<Path, Path>();
		files.put(Paths.get(folder + "a_example.txt"), Paths.get(folder + "a_example.out"));
		files.put(Paths.get(folder + "b_lovely_landscapes.txt"), Paths.get(folder + "b_lovely_landscapes.out"));
		files.put(Paths.get(folder + "c_memorable_moments.txt"), Paths.get(folder + "c_memorable_moments.out"));
		files.put(Paths.get(folder + "d_pet_pictures.txt"), Paths.get(folder + "d_pet_pictures.out"));
		files.put(Paths.get(folder + "e_shiny_selfies.txt"), Paths.get(folder + "e_shiny_selfies.out"));

		List<SlideshowSolver> solvers = new ArrayList<SlideshowSolver>();
		// solvers.add(new SlideshowSolver1());
		solvers.add(new SlideshowSolver2());
		// solvers.add(new SlideshowSolver3());

		List<Path> inputs = new ArrayList<Path>(files.keySet());
		inputs.sort(new Comparator<Path>() {

			@Override
			public int compare(Path o1, Path o2) {
				return o1.toAbsolutePath().toString().compareTo(o2.toAbsolutePath().toString());
			}
		});

		for (Path input : inputs) {
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
