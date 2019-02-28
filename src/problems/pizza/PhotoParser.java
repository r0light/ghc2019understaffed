package problems.pizza;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import template.Parser;
import template.ParserException;

public class PhotoParser extends Parser {

	final static String headerDelimeter = " ";
	Path path;
	PhotoProblem problem;

	public PhotoProblem parse(Path path) throws ParserException {
		this.path = path;
		problem = new PhotoProblem();

		List<String> lines;
		try {
			lines = Files.readAllLines(path, StandardCharsets.US_ASCII);
			parseHeader(lines.get(0));
			lines.remove(0);

			List<Photo> photos = new ArrayList<Photo>();
			for (int i = 0; i < lines.size(); i++) {
				String line = lines.get(i);
				String[] parts = line.split(headerDelimeter);

				Photo photo = new Photo();
				photo.id = i;

				if (parts[0] == "H") {
					photo.orientation = Orientation.H;
				} else {
					photo.orientation = Orientation.V;
				}

				int no = Integer.parseInt(parts[1]);

				for (i = 2; i < no + 2; i++) {
					photo.tags.add(parts[i]);
				}

				photos.add(photo);

			}

			problem.photos = photos;
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
			problem.noPhotos = parts[0];

		} catch (NumberFormatException e) {
			throw new ParserException("Header row must only contain ints", e);
		}
	}

}
