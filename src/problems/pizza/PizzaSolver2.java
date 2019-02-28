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
			Slide s = new Slide(photos, null);
			return s;
		}).collect(Collectors.toList());

		PizzaSolution sol = new PizzaSolution();
		sol.slides = slides;

		Slide last = null;
		for (Slide s : sol.slides) {
			if (last == null) {
				last = s;
				continue;
			}
			System.out.println(Eval.score(last, s));
			last = s;
		}

		return sol;
	}
}
