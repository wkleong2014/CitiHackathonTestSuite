package citi.hackathon.entity;

public class CategoryRequestBody {
	private String category;
	
	public CategoryRequestBody() {}

	public CategoryRequestBody(String category) {
		this.category = category;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "CreateCategoryRequestBody [category=" + category + "]";
	}
	
}
