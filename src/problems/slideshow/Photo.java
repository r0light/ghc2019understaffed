package problems.slideshow;

import java.util.ArrayList;
import java.util.List;

public class Photo {

	public int id;
	public Orientation orientation;
	public List<String> tags = new ArrayList<String>();

	public Photo() {
		super();
	}

	public Photo(int id, Orientation orientation, List<String> tags) {
		super();
		this.id = id;
		this.orientation = orientation;
		this.tags = tags;
	}

	@Override
	public String toString() {
		return String.format("%d", id);
	}

}
