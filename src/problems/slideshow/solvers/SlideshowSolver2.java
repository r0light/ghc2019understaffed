package problems.slideshow.solvers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import problems.slideshow.Orientation;
import problems.slideshow.Photo;
import problems.slideshow.Slide;
import problems.slideshow.SlideshowProblem;
import problems.slideshow.SlideshowSolution;
import template.Solver;

public class SlideshowSolver2 extends Solver implements SlideshowSolver {

	public SlideshowSolution solve(SlideshowProblem problem) {

		final int inspectVnextElements = 25;

		// turn all H photos into slides
		List<Slide> Hslides = problem.photos.stream().filter(x -> x.orientation == Orientation.H).map(x -> {
			List<Photo> photos = new ArrayList<Photo>();
			photos.add(x);
			Slide s = new Slide(photos);
			return s;
		}).collect(Collectors.toList());

		// turn all V photos into slides
		List<Photo> Vphotos = problem.photos.stream().filter(x -> x.orientation == Orientation.V)
				.collect(Collectors.toList());
		assert Vphotos.size() % 2 == 0;

		// two V photos are a good match if they 
		List<Slide> Vslides = new ArrayList<Slide>();
		while (!Vphotos.isEmpty()) {
			Photo photo = Vphotos.remove(0);
			int currentIntersect = Integer.MAX_VALUE;
			Photo bestPartner = Vphotos.get(0);
			for (int i = 0; i < inspectVnextElements && i < Vphotos.size(); i++) {
				Photo somePartner = Vphotos.get(new Random().nextInt(Vphotos.size()));
				List<String> intersect = new ArrayList<String>(photo.tags);
				intersect.retainAll(somePartner.tags);
				if (intersect.size() / (photo.tags.size() * somePartner.tags.size()) < currentIntersect) {
					bestPartner = somePartner;
					currentIntersect = intersect.size();
				}
			}
			Slide slide = new Slide(Arrays.asList(photo, bestPartner));
			Vphotos.remove(bestPartner);
			Vslides.add(slide);
		}

		// properly merge Vslides and Hslides

		SlideshowSolution sol = new SlideshowSolution();
		List<Slide> slides = new ArrayList<Slide>(Vslides);
		Collections.copy(slides, Vslides);
		slides.addAll(Hslides);
		sol.slides = slides;

		return sol;
	}
}
