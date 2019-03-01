package problems.slideshow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SlideshowHelper {

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

	public static <T> List<T> copyList(List<T> s1) {
		List<T> tags1 = new ArrayList<T>(s1);
		Collections.copy(tags1, s1);
		return tags1;
	}

	public static <T> List<T> intersection(List<T> s1, List<T> s2) {
		List<T> tags1 = new ArrayList<T>(s1);
		Collections.copy(tags1, s1);

		List<T> tags2 = new ArrayList<T>(s2);
		Collections.copy(tags2, s2);

		List<T> intersect = new ArrayList<T>(s1);
		Collections.copy(intersect, s1);
		intersect.retainAll(tags2);
		return intersect;
	}

	public static void scoreX(Slide s1, Slide s2) {
		List<String> tags1 = new ArrayList<String>(s1.tags);
		Collections.copy(tags1, s1.tags);

		List<String> tags2 = new ArrayList<String>(s2.tags);
		Collections.copy(tags2, s2.tags);

		List<String> intersect = new ArrayList<String>(s1.tags);
		Collections.copy(intersect, s1.tags);
		intersect.retainAll(tags2);

		tags1.removeAll(intersect);
		tags2.removeAll(intersect);

		System.out.println(tags1.size() + " -- " + intersect.size() + " -- " + tags2.size());
	}

	public static int scoreB(Slide s1, Slide s2) {
		List<String> tags1 = new ArrayList<String>(s1.tags);
		Collections.copy(tags1, s1.tags);

		List<String> tags2 = new ArrayList<String>(s2.tags);
		Collections.copy(tags2, s2.tags);

		List<String> intersect = new ArrayList<String>(s1.tags);
		Collections.copy(intersect, s1.tags);
		intersect.retainAll(tags2);

		tags1.removeAll(intersect);
		tags2.removeAll(intersect);

		int actual = Math.min(Math.min(tags1.size(), tags2.size()), intersect.size());
		int optimal = (tags1.size() + tags2.size()) / 2;
		int bValue = (100 * actual) / optimal;
//		System.out.println(				tags1.size() + " -- " + intersect.size() + " -- " + tags2.size() + " => " + bValue + " vs. " + actual);
		return bValue;

	}

	public static int score(List<Slide> slides) {
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
		return score;

	}

}
