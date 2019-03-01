package problems.slideshow.execution;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Conf {

	public static String folder = "problems/slideshow/";

	public static Path aInput = Paths.get("problems/slideshow/a_example.txt");
	public static Path aOutput = Paths.get("problems/slideshow/a_example.out");

	public static Path bInput = Paths.get("problems/slideshow/b_lovely_landscapes.txt");
	public static Path bOutput = Paths.get("problems/slideshow/b_lovely_landscapes.out");

	public static Path cInput = Paths.get("problems/slideshow/c_memorable_moments.txt");
	public static Path cOutput = Paths.get("problems/slideshow/c_memorable_moments.out");

	public static Path dInput = Paths.get("problems/slideshow/d_pet_pictures.txt");
	public static Path dOutput = Paths.get("problems/slideshow/d_pet_pictures.out");

	public static Path eInput = Paths.get("problems/slideshow/e_shiny_selfies.txt");
	public static Path eOutput = Paths.get("problems/slideshow/e_shiny_selfies.out");

	public static Map<Path, Path> listFiles() {
		Map<Path, Path> files = new HashMap<Path, Path>();
		files.put(Paths.get(folder + "a_example.txt"), Paths.get(folder + "a_example.out"));
//		files.put(Paths.get(folder + "b_lovely_landscapes.txt"), Paths.get(folder + "b_lovely_landscapes.out"));
		files.put(Paths.get(folder + "c_memorable_moments.txt"), Paths.get(folder + "c_memorable_moments.out"));
		files.put(Paths.get(folder + "d_pet_pictures.txt"), Paths.get(folder + "d_pet_pictures.out"));
		files.put(Paths.get(folder + "e_shiny_selfies.txt"), Paths.get(folder + "e_shiny_selfies.out"));
		return files;
	}

	public static List<Path> getInputFiles() {
		List<Path> inputs = new ArrayList<Path>(listFiles().keySet());
		inputs.sort(new Comparator<Path>() {

			@Override
			public int compare(Path o1, Path o2) {
				return o1.toAbsolutePath().toString().compareTo(o2.toAbsolutePath().toString());
			}
		});
		return inputs;
	}

}
