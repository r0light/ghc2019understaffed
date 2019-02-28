package problems.pizza;

import java.nio.file.Path;
import java.nio.file.Paths;

import template.Writer;

public class PizzaMain {

	public static void main(String[] args) throws Exception {
		System.out.println("Running Main ...");

		final Path input = Paths.get("problems/pizza/b_lovely_landscapes.txt");
		final Path output = Paths.get("problems/pizza/b_lovely_landscapes.out");

		final PhotoProblem problem = new PhotoParser().parse(input);
		final PizzaSolver solver = new PizzaSolver();
		final PizzaSolution solution = solver.solve(problem);
		final Writer writer = new PizzaWriter();
		System.out.println("before writing . . . ");
		writer.write(solution, output);
		System.out.println(String.format("%s => %s", input, output));

	}
}
