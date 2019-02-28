package problems.pizza;

import java.util.ArrayList;
import java.util.List;

import template.Problem;

public class PhotoProblem extends Problem {

	int noPhotos;
	List<Photo> photos = new ArrayList<Photo>();

	public PhotoProblem() {
		super();
	}

	public PhotoProblem(int noPhotos, List<Photo> photos) {
		super();
		this.noPhotos = noPhotos;
		this.photos = photos;
	}

	@Override
	public String toString() {
		return "PhotoProblem [noPhotos=" + noPhotos + ", photos=" + photos + "]";
	}

}
