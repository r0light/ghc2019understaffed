package problems.pizza;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Eval {

	public static int score(Slide s1, Slide s2) {
		List<String> tags1 = new ArrayList<String>(s1.tags);
		Collections.copy(tags1, s1.tags);

		List<String> tags2 = new ArrayList<String>(s2.tags);
		Collections.copy(tags2, s2.tags);

		List<String> intersect = new ArrayList<String>(s1.tags);
		Collections.copy(intersect, s1.tags);
		intersect.retainAll(tags2);

		tags1.removeAll(intersect);
		tags2.removeAll(intersect);

		return Math.min(Math.min(tags1.size(), tags2.size()), intersect.size());
	}

}
