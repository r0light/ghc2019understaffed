package problems.pizza;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import template.Writer;

public class PizzaMain {

	public static void main(String[] args) throws Exception {

		Map<String, String> inputOutputFiles = new HashMap<String, String>();
		inputOutputFiles.put("problems/pizza/a_example.in", "problems/pizza/a_example.out");
		/*-
		inputOutputFiles.put("problems/pizza/b_small.in", "problems/pizza/b_small.out");
		inputOutputFiles.put("problems/pizza/c_medium.in", "problems/pizza/c_medium.out");
		inputOutputFiles.put("problems/pizza/d_big.in", "problems/pizza/d_big.out");
		*/
		ExecutorService executor = Executors.newCachedThreadPool();

		try {
			for (String key : inputOutputFiles.keySet()) {
				final Path input = Paths.get(key);
				final Path output = Paths.get(inputOutputFiles.get(key));
				final PizzaProblem problem = new PizzaParser().parse(input);
				final PizzaSolver solver = new PizzaSolver();
				final Runnable runnable = new Runnable() {

					@Override
					public void run() {
						final PizzaSolution solution = solver.solve(problem);
						final Writer writer = new PizzaWriter();
						writer.write(solution, output);
						System.out.println(String.format("%s => %s", input, output));
					}
				};
				executor.submit(runnable);
			}
			while (!executor.isTerminated()) {
				try {
					executor.shutdown();
					executor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
				} catch (InterruptedException ignore) {
				}
			}
		} finally {
			executor.shutdownNow();
		}

	}

}
