package problems.pizza;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Slide {

	public List<Photo> photos;
	public List<String> tags = new ArrayList<String>();
	
	
	public Slide(List<Photo> photos, List<String> tags) {
		super();
		this.photos = photos;
		this.tags = new ArrayList<>();
		for (Photo p : this.photos) {
			this.tags.addAll(p.tags);
		}
		
		this.tags = this.tags.stream().distinct().collect(Collectors.toList());
	}

}
