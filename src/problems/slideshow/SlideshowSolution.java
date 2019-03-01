package problems.slideshow;

import java.util.List;
import java.util.stream.Collectors;

import template.Solution;

public class SlideshowSolution extends Solution {

	public List<Slide> slides;

	public SlideshowSolution(List<Slide> slides) {
		this.slides = slides;
	}

	public SlideshowSolution() {
	}

	@Override
	public String provideSolution() {
		String output = slides.size() + "";
		output += "\n";
		for (Slide s : slides) {
			output += s.photos.stream().map(x -> "" + x.id).collect(Collectors.joining(" "));
			output += "\n";
		}
		return output;
	}

	public int score() {
		return SlideshowHelper.score(slides);
	}
}
