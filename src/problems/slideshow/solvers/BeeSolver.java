package problems.slideshow.solvers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.stream.Collectors;

import problems.slideshow.Orientation;
import problems.slideshow.Slide;
import problems.slideshow.SlideshowHelper;
import problems.slideshow.SlideshowProblem;
import problems.slideshow.SlideshowSolution;
import template.Solver;

public class BeeSolver extends Solver implements SlideshowSolver {

	final int LOOKAHEAD = 200;

	public SlideshowSolution solve(SlideshowProblem problem) {

		assert problem.photos.parallelStream().filter(s -> s.orientation == Orientation.V).count() == 0;

		// turn all H photos into slides
		List<Slide> slides = problem.photos.parallelStream().filter(x -> x.orientation == Orientation.H)
				.map(slide -> new Slide(Arrays.asList(slide))).collect(Collectors.toList());

		// many tags -> few tags
		slides.sort(new Comparator<Slide>() {
			@Override
			public int compare(Slide arg0, Slide arg1) {
				return Integer.compare(arg1.tags.size(), arg0.tags.size());
			}
		});

		// build Map from tags to Slides
		Map<String, List<Slide>> tag2slides = new HashMap<String, List<Slide>>();
		for (Slide slide : slides) {
			for (String tag : slide.tags) {
				tag2slides.putIfAbsent(tag, new ArrayList<Slide>());
				tag2slides.get(tag).add(slide);
			}
		}

		List<Slide> finalSlides = new ArrayList<Slide>();
		Slide slide = slides.remove(0);
		finalSlides.add(slide);
		while (!slides.isEmpty()) {
			System.out.println(slides.size());
			List<Slide> candidates = new ArrayList<Slide>();
			slide.tags.forEach(t -> candidates.addAll(tag2slides.get(t)));
			candidates.retainAll(slides);
			// the candidates comprise the slides having at least one intersecting element
			candidates.sort(new Comparator<Slide>() {
				@Override
				public int compare(Slide arg0, Slide arg1) {
					return Integer.compare(arg1.tags.size(), arg0.tags.size());
				}
			});

			Slide bestCandidate = null;
			int bestScore = Integer.MIN_VALUE;
			for (int i = 0; i < LOOKAHEAD && i < candidates.size(); i++) {
				Slide candidate = candidates.get(i);
				int candidateScore = SlideshowHelper.score(slide, candidate);
				if (candidateScore > bestScore) {
					bestScore = candidateScore;
					bestCandidate = candidate;
				}
			}
			if (bestCandidate == null || slide == bestCandidate) {
				bestCandidate = slides.get(0);
				// finalSlides.add(bestCandidate);
				// continue;
			}

			finalSlides.add(bestCandidate);
			slides.remove(bestCandidate);
//			System.out.println("removing " + bestCandidate);
//				bestCandidate.tags.forEach(tag -> tag2slides.get(tag).remove(bestCandidate));
			slide = bestCandidate;
		}

		/*
		 * int counter = 0; for (String tag : tag2slides.keySet()) {
		 * System.out.println(counter + " of " + tag2slides.keySet().size()); counter +=
		 * 1; List<Slide> slidesWithTag = tag2slides.get(tag); if (slidesWithTag.size()
		 * <= 1) { // later on I need to find a way to properly combine lists of Slides
		 * continue; } finalSlides.addAll(SlideshowHelper.copyList(slidesWithTag)); //
		 * tag2slides.values().parallelStream().forEach(x -> //
		 * x.removeAll(slidesWithTag)); }
		 */
		// remove duplicates (remove them from the end first, leave the first occurence)
		Collections.reverse(finalSlides);
		finalSlides = finalSlides.stream().distinct().collect(Collectors.toList());
		Collections.reverse(finalSlides);
		System.out.println("SIZE: " + finalSlides.size());
		return new SlideshowSolution(finalSlides);

	}
}
