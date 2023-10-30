package src.model.models;

public class User {
	
	private String name;
	private String dateOfBirth;
	private double height;
	private double weight;
	private String username;
	private String password;
	
	public User() {}
	
	
	public User(String name, String dateOfBirth, double height, double weight, String username, String password) {
		super();
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.height = height;
		this.weight = weight;
		this.username = username;
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
