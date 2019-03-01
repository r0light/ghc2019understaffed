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
		assert Vphotos.size() % 2 == 0;
		// sort by number of tags (high to small9
		/*
		 * Vphotos.sort(new Comparator<Photo>() {
		 * 
		 * @Override public int compare(Photo o1, Photo o2) { return
		 * Integer.compare(o1.tags.size(), o2.tags.size()); } });
		 */

		// TODO: EY idea merge photos with small intersect.
		// System.out.println("traversing V photos");
		List<Photo> vPhotoCopy = new ArrayList<Photo>(Vphotos);
		List<Slide> Vslides = new ArrayList<Slide>();
		while (!vPhotoCopy.isEmpty()) {
			Photo p1 = vPhotoCopy.get(0);
			// System.out.println("looking at photo " + p1.id);
			vPhotoCopy.remove(0);
			int currentIntersect = Integer.MAX_VALUE;
			Photo p2 = vPhotoCopy.get(new Random().nextInt(vPhotoCopy.size()));
			for (int i = 0; i < 50; i++) {
				Photo p3 = vPhotoCopy.get(new Random().nextInt(vPhotoCopy.size()));
				List<String> intersect = new ArrayList<String>(p1.tags);
				intersect.retainAll(p3.tags);
				if (intersect.size() / (p1.tags.size() + p3.tags.size()) < currentIntersect) {
					p2 = p3;
					currentIntersect = intersect.size();
				}
			}
			Slide s = new Slide(Arrays.asList(p1, p2));
			vPhotoCopy.remove(p2);
			Vslides.add(s);
		}

		/*
		 * List<Slide> Vslides = new ArrayList<Slide>(); for (int i = 0; i <
		 * Vphotos.size(); i += 2) { Photo a = Vphotos.get(i); Photo b = Vphotos.get(i +
		 * 1); Slide slide = new Slide(Arrays.asList(a, b)); Vslides.add(slide); }
		 */

		SlideshowSolution sol = new SlideshowSolution();
		List<Slide> slides = new ArrayList<Slide>(Vslides);
		Collections.copy(slides, Vslides);
		slides.addAll(Hslides);
		sol.slides = slides;
		Collections.shuffle(slides);
		return sol;
	}
}
