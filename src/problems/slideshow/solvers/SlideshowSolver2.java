package problems.slideshow.solvers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import problems.slideshow.Orientation;
import problems.slideshow.Photo;
import problems.slideshow.Slide;
import problems.slideshow.SlideshowHelper;
import problems.slideshow.SlideshowProblem;
import problems.slideshow.SlideshowSolution;
import template.Solver;

public class SlideshowSolver2 extends Solver implements SlideshowSolver {

	public SlideshowSolution solve(SlideshowProblem problem) {

		final int inspectVnextElements = 50; // 400
		final int inspectnextSlides = 10; // 2000

		// turn all H photos into slides
		List<Slide> Hslides = problem.photos.parallelStream().filter(x -> x.orientation == Orientation.H).map(x -> {
			List<Photo> photos = new ArrayList<Photo>();
			photos.add(x);
			Slide s = new Slide(photos);
			return s;
		}).collect(Collectors.toList());

		// turn all V photos into slides
		List<Photo> Vphotos = problem.photos.parallelStream().filter(x -> x.orientation == Orientation.V)
				.collect(Collectors.toList());
		assert Vphotos.size() % 2 == 0;
		// sort Vphotos by the number of tags
		Vphotos.sort(new Comparator<Photo>() {
			@Override
			public int compare(Photo o1, Photo o2) {
				return Integer.compare(o2.tags.size(), o1.tags.size());
			}
		});

		// two V photos are a good match if they
		List<Slide> Vslides = new ArrayList<Slide>();
		while (!Vphotos.isEmpty()) {
			Photo photo = Vphotos.remove(0);
			int currentIntersect = Integer.MAX_VALUE;
			Photo bestPartner = Vphotos.get(0);
			for (int i = 0; i < inspectVnextElements && i < Vphotos.size(); i++) {
				Photo somePartner = Vphotos.get(i);
				List<String> intersect = new ArrayList<String>(photo.tags);
				intersect.retainAll(somePartner.tags);
				// if (intersect.size() / (photo.tags.size() + somePartner.tags.size()) <
				// currentIntersect) {
				if (intersect.size() < currentIntersect) {
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

		slides.sort(new Comparator<Slide>() {
			@Override
			public int compare(Slide arg0, Slide arg1) {
				return Integer.compare(arg0.tags.size(), arg1.tags.size());
			}
		});

		List<Slide> finalSlides = new ArrayList<Slide>();
		Slide slide = slides.remove(0);
		finalSlides.add(slide);
		while (!slides.isEmpty()) {
			Slide bestSlide = slides.get(0);
			int bestScore = SlideshowHelper.scoreX(slide, bestSlide);
			for (int i = 0; i < inspectnextSlides && i < slides.size(); i++) {
				Slide someSlide = slides.get(i);
				if (SlideshowHelper.scoreX(slide, someSlide) > bestScore) {
					bestSlide = someSlide;
					bestScore = SlideshowHelper.scoreX(slide, someSlide);
				}
			}
			slides.remove(bestSlide);
			finalSlides.add(bestSlide);
			slide = bestSlide;
		}

		sol.slides = finalSlides;

		return sol;
	}
}
