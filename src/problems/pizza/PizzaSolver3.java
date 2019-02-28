package problems.pizza;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import template.Solver;

public class PizzaSolver3 extends Solver {

	public PizzaSolution solve(PhotoProblem problem) {

		// create List of slides
		List<Slide> slides = new ArrayList<>();

		List<Slide> candidates = this.createSlides(problem.photos);

		// candidates.stream().forEach(System.out::println);

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

			Slide neighbour = takeFromDistribution(distribution, currentTag);

			while (neighbour != null) {
				solutionSlides.add(neighbour);

				// find next neighbour
				for (String tag : neighbour.tags) {
					if (!tag.equals(currentTag)) {
						currentTag = tag;
						break;
					}
				}
				neighbour = takeFromDistribution(distribution, currentTag);
			}
			distribution.remove(currentTag);
//			System.out.println("size: " + distribution.size());
			currentTag = findTagWithPair(distribution);
		}

		// TODO This is only a hard coded solution for a_example.in problem
		PizzaSolution solution = new PizzaSolution();
		solution.slides = solutionSlides;
		solution.score();
		// solution.numberOfSlices = 3;
		// solution.slices = Arrays.asList("0 0 2 1", "0 2 2 2", "0 3 2 4");

//		System.out.println(problem.toString());

		return solution;
	}

	private String findTagWithPair(Map<String, List<Slide>> distribution) {
		for (String s : distribution.keySet()) {
			// System.out.println(s + " - " + distribution.get(s).size());
			if (distribution.get(s).size() > 1) {
				return s;
			}
		}
		return null;
	}

	private Slide takeFromDistribution(Map<String, List<Slide>> distribution, String startTag) {
		try {
			if (distribution.containsKey(startTag)) {
				Slide found = distribution.get(startTag).get(0);
				for (String tag : found.tags) {
					if (distribution.containsKey(tag)) {
						distribution.get(tag).remove(found);
					}
//				if (distribution.get(tag).size() == 0) {
//					distribution.remove(tag);
//				}
				}
				return found;
			} else {
				return null;
			}
		} catch (IndexOutOfBoundsException e) {
			return null;
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

		List<Slide> slides = new ArrayList<Slide>();

		// Collections.shuffle(verticals);
		verticals.sort(new Comparator<Photo>() {

			@Override
			public int compare(Photo o1, Photo o2) {
				return Integer.compare(o1.tags.size(), o2.tags.size());
			}
		});

		for (int i = 0; i < verticals.size() - 1; i += 2) {
			slides.add(new Slide(Arrays.asList(verticals.get(i), verticals.get(i + 1))));
		}

		return slides;
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
