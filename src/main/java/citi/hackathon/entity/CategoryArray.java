package citi.hackathon.entity;

import java.util.List;

public class CategoryArray {
	List<Category> categories;
	
	public CategoryArray() {}

	public CategoryArray(List<Category> categories) {
		this.categories = categories;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	@Override
	public String toString() {
		return "CategoryArray [categories=" + categories + "]";
	}
	
}
