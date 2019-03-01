package problems.slideshow.solvers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import problems.slideshow.Orientation;
import problems.slideshow.Slide;
import problems.slideshow.SlideshowHelper;
import problems.slideshow.SlideshowProblem;
import problems.slideshow.SlideshowSolution;
import template.Solver;

public class BeeSolver extends Solver implements SlideshowSolver {

	final int LOOKAHEAD = 1;

	public SlideshowSolution solve(SlideshowProblem problem) {

		System.out.println("Number of V photos: "+problem.photos.parallelStream().filter(s -> s.orientation == Orientation.V).count());

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
			}

			finalSlides.add(bestCandidate);
			slides.remove(bestCandidate);
			slide = bestCandidate;
		}

		System.out.println("SIZE: " + finalSlides.size());
		return new SlideshowSolution(finalSlides);

	}
}
