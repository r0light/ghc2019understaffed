package problems.slideshow;

import java.util.List;
import java.util.stream.Collectors;

import template.Solution;

public class SlideshowSolution extends Solution {

	public List<Slide> slides;

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
		int score = 0;
		Slide last = null;
		for (Slide s : slides) {
			if (last == null) {
				last = s;
				continue;
			}
			int newScore = SlideshowHelper.score(last, s);
			score += newScore;
			last = s;
		}
		// System.out.println("SCORE: " + score);
		return score;
	}
}
