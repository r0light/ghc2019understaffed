package problems.pizza;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import template.Solver;

public class PizzaSolver2 extends Solver {

	public PizzaSolution solve(PhotoProblem problem) {

		List<Photo> Hphotos = problem.photos.stream().filter(x -> x.orientation == Orientation.H)
				.collect(Collectors.toList());

		List<Slide> Hslides = Hphotos.stream().map(x -> {
			List<Photo> photos = new ArrayList<Photo>();
			photos.add(x);
			Slide s = new Slide(photos);
			return s;
		}).collect(Collectors.toList());

		List<Photo> Vphotos = problem.photos.stream().filter(x -> x.orientation == Orientation.V)
				.collect(Collectors.toList());
		
		// TODO: EY idea merge photos with small intersect.

		assert Vphotos.size() % 2 == 0;
		List<Slide> Vslides = new ArrayList<Slide>();
		for (int i = 0; i < Vphotos.size(); i += 2) {
			Photo a = Vphotos.get(i);
			Photo b = Vphotos.get(i + 1);
			Slide slide = new Slide(Arrays.asList(a, b));
			Vslides.add(slide);
		}

		PizzaSolution sol = new PizzaSolution();
		List<Slide> slides = new ArrayList<Slide>(Vslides);
		Collections.copy(slides, Vslides);
		slides.addAll(Hslides);
		sol.slides = slides;

		int score = sol.score();
		int maxScore = score;
		System.out.println(score);
		while (score <= 100000) {
			Collections.shuffle(slides);
			score = sol.score();
			if (score > maxScore) {
				maxScore = score;
			}
			System.out.println("SCORE: " + score + "--max so far: " + maxScore);
		}
		System.out.println("DONE");

		return sol;
	}
}
