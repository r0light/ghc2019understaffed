package problems.pizza;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import template.Parser;
import template.ParserException;

public class PizzaParser extends Parser {

	final static String headerDelimeter = " ";
	Path path;
	PizzaProblem problem;

	public PizzaProblem parse(Path path) throws ParserException {
		this.path = path;
		problem = new PizzaProblem();

		List<String> lines;
		try {
			lines = Files.readAllLines(path, StandardCharsets.US_ASCII);
			parseHeader(lines.get(0));
			lines.remove(0);
			problem.pizza = lines;
			return problem;
		} catch (IOException e) {
			throw new ParserException(e);
		}
	}

	private void parseHeader(String line) throws ParserException {
		String[] strParts = line.split(headerDelimeter);
		if (strParts.length != 4) {
			throw new ParserException("Header row must contain 4 ints");
		}

		try {
			/*- 0: rows, 1: columns, 2: min ingredients per slice, 3: max cells per slice */
			int[] parts = Arrays.stream(strParts).mapToInt(Integer::parseInt).toArray();
			problem.rows = parts[0];
			problem.columns = parts[1];
			problem.minimumIngredients = parts[2];
			problem.maxCells = parts[3];

		} catch (NumberFormatException e) {
			throw new ParserException("Header row must only contain ints", e);
		}
	}

}
