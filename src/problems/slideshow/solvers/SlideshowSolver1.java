package problems.slideshow.solvers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import problems.slideshow.Orientation;
import problems.slideshow.Photo;
import problems.slideshow.Slide;
import problems.slideshow.SlideshowHelper;
import problems.slideshow.SlideshowProblem;
import problems.slideshow.SlideshowSolution;
import template.Solver;

public class SlideshowSolver1 extends Solver implements SlideshowSolver {

	private final int inspectVnextElements = 200;
	private final int numberOfCandidates = 1000;

	public SlideshowSolution solve(SlideshowProblem problem) {

		List<Slide> candidates = this.createSlides(problem.photos);

		Map<String, List<Slide>> distribution = generateDistributionMap(candidates);

		List<Slide> solutionSlides = new ArrayList<Slide>();

//		System.out.println(distribution.size());
		// removing all the tags with a single slide
		List<String> singleTags = new ArrayList<>();
		for (String tag : distribution.keySet()) {
			if (distribution.get(tag).size() < 2) {
				singleTags.add(tag);
			}
		}
		for (String removeTag : singleTags) {
			distribution.remove(removeTag);
		}
//		System.out.println(distribution.size());

		// Search start tag
		// TODO: randomize
		String startTag = "";
		startTag = findTagWithPair(distribution);
		String currentTag = startTag;
		while (currentTag != null) {
			// Get first slide
			Slide startSlide = takeFromDistribution(distribution, currentTag);

			solutionSlides.add(startSlide);

			Slide neighbour = takeFromDistribution(distribution, currentTag, startSlide);

			while (neighbour != null) {
				solutionSlides.add(neighbour);

				// find next neighbour
				for (String tag : neighbour.tags) {
					if (!tag.equals(currentTag)) {
						currentTag = tag;
						break;
					}
				}
				neighbour = takeFromDistribution(distribution, currentTag, neighbour);
			}
			distribution.remove(currentTag);
			System.out.println("size: " + distribution.size());
			currentTag = findTagWithPair(distribution);
		}

		// TODO This is only a hard coded solution for a_example.in problem
		SlideshowSolution solution = new SlideshowSolution();
		solution.slides = solutionSlides;
		solution.score();

		return solution;
	}

	private Map<String, List<Slide>> generateDistributionMap(List<Slide> candidates) {

		Map<String, List<Slide>> distribution = new HashMap<>();

		for (Slide s : candidates) {
			for (String tag : s.tags) {
				if (distribution.containsKey(tag)) {
					distribution.get(tag).add(s);
				} else {
					List<Slide> ss = new ArrayList<Slide>();
					ss.add(s);
					distribution.put(tag, ss);
				}
			}
		}

		return distribution;
	}

	private String findTagWithPair(Map<String, List<Slide>> distribution) {

		String bestKey = null;
		int bestSize = 1;

		for (String s : distribution.keySet()) {
			// // System.out.println(s + " - " + distribution.get(s).size());
			if (distribution.get(s).size() > bestSize) {
				bestSize = distribution.get(s).size();
				bestKey = s;
			}
		}

		if (bestSize == 1) {
			return null;
		} else {
			return bestKey;
		}
	}

	private Slide takeFromDistribution(Map<String, List<Slide>> distribution, String startTag) {
		try {
			if (distribution.containsKey(startTag)) {
				Slide found = distribution.get(startTag).get(0);

				// remove found slide from all distribution entries
				removeSlideFromDistributions(distribution, found);

				return found;
			} else {
				return null;
			}
		} catch (IndexOutOfBoundsException e) {
			return null;
		}

	}

	/**
	 * Searches for a neighbor w.r.t. the score of the solution.
	 * 
	 * @param distribution
	 * @param currentTag
	 * @param startSlide
	 * @return
	 */
	private Slide takeFromDistribution(Map<String, List<Slide>> distribution, String currentTag, Slide startSlide) {

		try {
			if (distribution.containsKey(currentTag)) {

				// compute the highest score
				List<Slide> slides = distribution.get(currentTag);
				slides.sort(new Comparator<Slide>() {

					@Override
					public int compare(Slide o1, Slide o2) {
						return Integer.compare(o2.tags.size(), o1.tags.size());
					}
				});

				Slide best = slides.get(0);
				int bestScore = SlideshowHelper.score(best, startSlide);
				// System.out.println(slides.size());
				for (int i = 1; i < (slides.size() > numberOfCandidates ? numberOfCandidates : slides.size()); i++) {
					Slide candidate = slides.get(i);
					int candidateScore = SlideshowHelper.score(startSlide, candidate);
					if (candidateScore > bestScore) {
						bestScore = candidateScore;
						best = candidate;
					}
				}

				// remove found slide from all distribution entries
				removeSlideFromDistributions(distribution, best);

				return best;
			} else {
				return null;
			}
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	private void removeSlideFromDistributions(Map<String, List<Slide>> distribution, Slide found) {
		for (String tag : found.tags) {
			if (distribution.containsKey(tag)) {
				distribution.get(tag).remove(found);
			}
		}
	}

	private List<Slide> createSlides(List<Photo> photos) {

		List<Slide> slides = new ArrayList<Slide>();

		List<Photo> horizontals = this.getPhotos(photos, Orientation.H);
		List<Photo> verticals = this.getPhotos(photos, Orientation.V);

		for (Photo p : horizontals) {
			slides.add(new Slide(Arrays.asList(p)));
		}

		slides.addAll(this.getSlidesFromVerticals(verticals));

		return slides;
	}

	private Collection<? extends Slide> getSlidesFromVerticals(List<Photo> verticals) {

//		List<Slide> slides = new ArrayList<Slide>();
//
//		for (int i = 0; i < verticals.size() - 1; i += 2) {
//			slides.add(new Slide(Arrays.asList(verticals.get(i), verticals.get(i + 1))));
//		}
//
//		return slides;
		// turn all V photos into slides
		List<Photo> Vphotos = verticals;

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
				// if (intersect.size() / (photo.tags.size() * somePartner.tags.size()) <
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

		return Vslides;
	}

	private List<Photo> getPhotos(List<Photo> photos, Orientation o) {
		List<Photo> pp = new ArrayList<>();

		for (Photo p : photos) {
			if (p.orientation == o) {
				pp.add(p);
			}
		}
		return pp;
	}

}
