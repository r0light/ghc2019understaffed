package problems.slideshow;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Slide {

	public int id;
	public List<Photo> photos;
	public List<String> tags = new ArrayList<String>();
	
	
	public Slide(List<Photo> photos) {
		super();
		this.photos = photos;
		// TODO : improve
		this.id = photos.get(0).id;
		this.tags = new ArrayList<>();
		for (Photo p : this.photos) {
			this.tags.addAll(p.tags);
		}
		
		this.tags = this.tags.stream().distinct().collect(Collectors.toList());
	}


	@Override
	public String toString() {
		return "Slide [id=" + id + ", photos=" + photos + ", tags=" + tags + "]";
	}
	
	

}
