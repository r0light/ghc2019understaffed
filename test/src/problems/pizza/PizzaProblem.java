package problems.pizza;

import java.util.List;
import java.util.stream.Collectors;

import template.Problem;

public class PizzaProblem extends Problem {

    int rows;
    int columns;
    int minimumIngredients;
    int maxCells;
    List<String> pizza;

    @Override
    public String toString() {
	final String output = String.format("*Pizza Problem Instance*%n" + "rows: %d%n" + "columns: %d%n"
		+ "minimumIngredients: %d%n" + "maxCells: %d%n", rows, columns, minimumIngredients, maxCells);
	String pizza = this.pizza.stream().collect(Collectors.joining("\n"));
	return output + pizza;
    }
}
