package problems.pizza;

import java.util.ArrayList;
import java.util.List;

public class Photo {

	public int id;
	public Orientation orientation;
	public List<String> tags = new ArrayList<String>();

	public Photo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Photo(int id, Orientation orientation, List<String> tags) {
		super();
		this.id = id;
		this.orientation = orientation;
		this.tags = tags;
	}

	@Override
	public String toString() {
		return "Photo [id=" + id + ", orientation=" + orientation + ", tags=" + tags + "]";
	}

}
