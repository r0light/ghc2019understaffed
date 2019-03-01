package problems.slideshow;

import java.util.ArrayList;
import java.util.List;

import template.Problem;

public class SlideshowProblem extends Problem {

	public int noPhotos;
	public List<Photo> photos = new ArrayList<Photo>();

	public SlideshowProblem() {
		super();
	}

	public SlideshowProblem(int noPhotos, List<Photo> photos) {
		super();
		this.noPhotos = noPhotos;
		this.photos = photos;
	}

	@Override
	public String toString() {
		return "PhotoProblem [noPhotos=" + noPhotos + ", photos=" + photos + "]";
	}

}
