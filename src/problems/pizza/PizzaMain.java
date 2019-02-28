package problems.pizza;

import java.nio.file.Path;
import java.nio.file.Paths;

import template.Writer;

public class PizzaMain {

	public static void main(String[] args) throws Exception {
		System.out.println("Running Main ...");

		// final Path input = Paths.get("problems/pizza/a_example.txt");
		// final Path output = Paths.get("problems/pizza/a_example.out");

		// final Path input = Paths.get("problems/pizza/b_lovely_landscapes.txt");
		// final Path output = Paths.get("problems/pizza/b_lovely_landscapes.out");

		// final Path input = Paths.get("problems/pizza/c_memorable_moments.txt");
		// final Path output = Paths.get("problems/pizza/c_memorable_moments.out");

		// final Path input = Paths.get("problems/pizza/d_pet_pictures.txt");
		// final Path output = Paths.get("problems/pizza/d_pet_pictures.out");

		final Path input = Paths.get("problems/pizza/e_shiny_selfies.txt");
		final Path output = Paths.get("problems/pizza/e_shiny_selfies.out");

		final PhotoProblem problem = new PhotoParser().parse(input);
		final PizzaSolver2 solver = new PizzaSolver2();
		final PizzaSolution solution = solver.solve(problem);
		final Writer writer = new PizzaWriter();
		System.out.println("before writing . . . ");
		writer.write(solution, output);
		System.out.println(String.format("%s => %s", input, output));
		System.out.println(solution.score());

	}
}
