package problems.pizza;

import template.Solver;

public class PizzaSolver extends Solver {

	public PizzaSolution solve(PhotoProblem problem) {

		// TODO This is only a hard coded solution for a_example.in problem
		PizzaSolution solution = new PizzaSolution();
		// solution.numberOfSlices = 3;
		// solution.slices = Arrays.asList("0 0 2 1", "0 2 2 2", "0 3 2 4");

		System.out.println(problem.toString());

		return solution;
	}
}
