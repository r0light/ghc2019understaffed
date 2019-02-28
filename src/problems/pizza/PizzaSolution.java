package problems.pizza;

import java.util.List;
import java.util.stream.Collectors;

import template.Solution;

public class PizzaSolution extends Solution {

	List<Slide> slides;

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

}
