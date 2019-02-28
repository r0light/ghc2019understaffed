package problems.pizza;

import java.util.List;
import java.util.stream.Collectors;

import template.Solution;

public class PizzaSolution extends Solution {

    int numberOfSlices;
    List<String> slices;

    @Override
    public String provideSolution() {
	final String output = numberOfSlices + "\n" + slices.stream().collect(Collectors.joining("\n"));
	return output;
    }

}
