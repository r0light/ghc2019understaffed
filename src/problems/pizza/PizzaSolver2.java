package problems.pizza;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import template.Solver;

public class PizzaSolver2 extends Solver {

	public PizzaSolution solve(PhotoProblem problem) {
		List<Slide> slides = problem.photos.stream().map(x -> {
			List<Photo> photos = new ArrayList<Photo>();
			photos.add(x);
			Slide s = new Slide(photos);
			return s;
		}).collect(Collectors.toList());

		PizzaSolution sol = new PizzaSolution();
		sol.slides = slides;

		int score = 0;
		Slide last = null;
		for (Slide s : sol.slides) {
			if (last == null) {
				last = s;
				continue;
			}
			int newScore = Eval.score(last, s);
			score += newScore;
			last = s;
		}
		System.out.println("SCORE: " + score);

		return sol;
	}
}
