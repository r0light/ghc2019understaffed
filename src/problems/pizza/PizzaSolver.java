package problems.pizza;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import template.Solver;

public class PizzaSolver extends Solver {

	public PizzaSolution solve(PhotoProblem problem) {
		
		// create List of slides
		List<Slide> slides = new ArrayList<>();
		
		List<Slide> candidates = this.createSlides(problem.photos);
		
		candidates.stream().forEach(System.out::println);
		
		Map<String, List<Slide>> distribution = new HashMap<>();
		
		for ( Slide s : candidates ) {
			for ( String tag : s.tags ) {
				if (distribution.containsKey(tag)) {
					distribution.get(tag).add(s);
				} else {
					List<Slide> ss = new ArrayList<Slide>();
					slides.add(s);
					distribution.put(tag, ss);
				}
			}
		}
		
		System.out.println(distribution);

		// TODO This is only a hard coded solution for a_example.in problem
		PizzaSolution solution = new PizzaSolution();
		// solution.numberOfSlices = 3;
		// solution.slices = Arrays.asList("0 0 2 1", "0 2 2 2", "0 3 2 4");

		System.out.println(problem.toString());

		return solution;
	}

	private List<Slide> createSlides(List<Photo> photos) {

		List<Slide> slides = new ArrayList<Slide>();
		
		List<Photo> horizontals = this.getPhotos(photos, Orientation.H);
		List<Photo> verticals = this.getPhotos(photos, Orientation.V);
		
		for ( Photo p : horizontals ) {
			slides.add(new Slide(Arrays.asList(p)));
		}
		
		slides.addAll(this.getSlidesFromVerticals(verticals));
		
		return slides;
	}

	private Collection<? extends Slide> getSlidesFromVerticals(List<Photo> verticals) {

		List<Slide> slides = new ArrayList<Slide>();
		
		for ( int i = 0 ; i < verticals.size() - 1  ; i+=2) {
			slides.add(new Slide(Arrays.asList(verticals.get(i), verticals.get(i+1))));
		}
		
		return slides;
	}

	private List<Photo> getPhotos(List<Photo> photos, Orientation o) {
		List<Photo> pp = new ArrayList<>();
		
		for (Photo p : photos ) {
			if ( p.orientation == o) {
				pp.add(p);
			}
		}
		return pp;
	}
	
}
