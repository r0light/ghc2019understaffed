package problems.slideshow.execution;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import problems.slideshow.SlideshowParser;
import problems.slideshow.SlideshowProblem;
import problems.slideshow.SlideshowSolution;
import problems.slideshow.SlideshowWriter;
import problems.slideshow.solvers.SlideshowSolver;
import problems.slideshow.solvers.SlideshowSolver2;
import template.ParserException;

public class SingleSolverAllProblems {

	public static void main(String[] args) throws Exception {

		AtomicLong overallTime = new AtomicLong();
		AtomicInteger overallScore = new AtomicInteger();

		SlideshowSolver solver = new SlideshowSolver2();
		System.out.println(solver.getClass().toString());

		Map<Path, Path> files = Conf.listFiles();

		List<Runnable> runnables = new ArrayList<Runnable>();
		long start = System.nanoTime();
		for (Path input : Conf.getInputFiles()) {
			Runnable r = new Runnable() {
				@Override
				public void run() {
					Path output = files.get(input);
					SlideshowProblem problem = null;
					try {
						problem = new SlideshowParser().parse(input);
					} catch (ParserException e) {
						System.err.println("FUCK");
					}
					SlideshowSolution solution = (SlideshowSolution) solver.solve(problem);
					new SlideshowWriter().write(solution, output);
					long elapsedTime = TimeUnit.SECONDS.convert(System.nanoTime() - start, TimeUnit.NANOSECONDS);
					System.out.println(
							input.getFileName().toString() + " == " + elapsedTime + "s ==> " + solution.score());
					overallTime.getAndAdd(elapsedTime);
					overallScore.getAndAdd(solution.score());

				}
			};
			runnables.add(r);
		}
		ExecutorService executor = Executors.newCachedThreadPool();
		for (Runnable r : runnables) {
			executor.submit(r);
		}

		while (!executor.isTerminated()) {
			try {
				executor.shutdown();
				executor.awaitTermination(100, TimeUnit.MILLISECONDS);
			} catch (InterruptedException ignore) {
			}
		}

		System.out.println("Overall time: " + overallTime + "s");
		System.out.println("overall score: " + overallScore);
	}
}
