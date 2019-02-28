package problems.pizza;

import static org.junit.Assert.assertEquals;

import java.nio.file.Paths;

import org.junit.Test;

import template.ParserException;

public class PizzaParserTest {

    @Test
    public void a_example() throws ParserException {

	// GIVEN
	final String filename = "problems/pizza/a_example.in";
	final int rows = 3;
	final int columns = 5;
	final int minimumIngredients = 1;
	final int maxCells = 6;

	// WHEN
	final PizzaProblem problem = new PizzaParser().parse(Paths.get(filename));

	// THEN
	assertEquals(rows, problem.rows);
	assertEquals(columns, problem.columns);
	assertEquals(minimumIngredients, problem.minimumIngredients);
	assertEquals(maxCells, problem.maxCells);
	assertEquals(rows, problem.pizza.size());
    }

    @Test
    public void b_example() throws ParserException {

	// GIVEN
	final String filename = "problems/pizza/b_small.in";
	final int rows = 6;
	final int columns = 7;
	final int minimumIngredients = 1;
	final int maxCells = 5;

	// WHEN
	final PizzaProblem problem = new PizzaParser().parse(Paths.get(filename));

	// THEN
	assertEquals(rows, problem.rows);
	assertEquals(columns, problem.columns);
	assertEquals(minimumIngredients, problem.minimumIngredients);
	assertEquals(maxCells, problem.maxCells);
	assertEquals(rows, problem.pizza.size());
    }

    @Test
    public void c_example() throws ParserException {

	// GIVEN
	final String filename = "problems/pizza/c_medium.in";
	final int rows = 200;
	final int columns = 250;
	final int minimumIngredients = 4;
	final int maxCells = 12;

	// WHEN
	final PizzaProblem problem = new PizzaParser().parse(Paths.get(filename));

	// THEN
	assertEquals(rows, problem.rows);
	assertEquals(columns, problem.columns);
	assertEquals(minimumIngredients, problem.minimumIngredients);
	assertEquals(maxCells, problem.maxCells);
	assertEquals(rows, problem.pizza.size());
    }

    @Test
    public void d_example() throws ParserException {

	// GIVEN
	final String filename = "problems/pizza/d_big.in";
	final int rows = 1000;
	final int columns = 1000;
	final int minimumIngredients = 6;
	final int maxCells = 14;

	// WHEN
	final PizzaProblem problem = new PizzaParser().parse(Paths.get(filename));

	// THEN
	assertEquals(rows, problem.rows);
	assertEquals(columns, problem.columns);
	assertEquals(minimumIngredients, problem.minimumIngredients);
	assertEquals(maxCells, problem.maxCells);
	assertEquals(rows, problem.pizza.size());
    }

}
