package problems.slideshow.solvers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import problems.slideshow.Orientation;
import problems.slideshow.Photo;
import problems.slideshow.Slide;
import problems.slideshow.SlideshowHelper;
import problems.slideshow.SlideshowProblem;
import problems.slideshow.SlideshowSolution;
import template.Solver;

public class EveeSolver extends Solver implements SlideshowSolver {

	final int SLIDE_LOOKAHEAD = 500;
	final int V_LOOKAHEAD = 500;

	public SlideshowSolution solve(SlideshowProblem problem) {

		// turn all V photos into slides
		List<Slide> Vslides = new ArrayList<Slide>();
		generateVslides(Vslides, problem);

		// turn all H photos into slides
		List<Slide> Hslides = problem.photos.parallelStream().filter(x -> x.orientation == Orientation.H)
				.map(slide -> new Slide(Arrays.asList(slide))).collect(Collectors.toList());

		List<Slide> slides = new ArrayList<Slide>();
		slides.addAll(Hslides);
		slides.addAll(Vslides);

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
		Slide slide = slides.remove(slides.size() - 1);
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
			for (int i = 0; i < SLIDE_LOOKAHEAD && i < candidates.size(); i++) {
				Slide candidate = candidates.get(i);
				int candidateScore = SlideshowHelper.score(slide, candidate);
				if (candidateScore > bestScore) {
					bestScore = candidateScore;
					bestCandidate = candidate;
				}
			}
			if (bestCandidate == null || slide == bestCandidate) {
				// take the worst slide (low tag size) first if you cannot find a proper one
				bestCandidate = slides.get(slides.size() - 1);
			}

			finalSlides.add(bestCandidate);
			slides.remove(bestCandidate);
			slide = bestCandidate;
		}

		System.out.println("SIZE: " + finalSlides.size());
		return new SlideshowSolution(finalSlides);

	}

	private void generateVslides(List<Slide> vslides, SlideshowProblem problem) {
		// turn all V photos into slides
		List<Photo> Vphotos = problem.photos.parallelStream().filter(x -> x.orientation == Orientation.V)
				.collect(Collectors.toList());
		// sort Vphotos by the number of tags
		Vphotos.sort(new Comparator<Photo>() {
			@Override
			public int compare(Photo o1, Photo o2) {
				return Integer.compare(o2.tags.size(), o1.tags.size());
			}
		});

		while (!Vphotos.isEmpty()) {
			Photo photo = Vphotos.remove(Vphotos.size() - 1);
			int currentScore = Integer.MAX_VALUE;
			Photo bestPartner = Vphotos.get(0);
			for (int i = 0; i < V_LOOKAHEAD && i < Vphotos.size(); i++) {
				Photo somePartner = Vphotos.get(i);
				List<String> intersect = new ArrayList<String>(photo.tags);
				intersect.retainAll(somePartner.tags);
				int someScore = intersect.size() + Math.abs(photo.tags.size() - somePartner.tags.size());
				if (someScore < currentScore) {
					bestPartner = somePartner;
					currentScore = intersect.size();
				}
			}
			Slide slide = new Slide(Arrays.asList(photo, bestPartner));
			Vphotos.remove(bestPartner);
			vslides.add(slide);
		}

	}
}
