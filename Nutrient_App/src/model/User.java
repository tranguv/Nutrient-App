package src.model;

import java.time.LocalDate;
import java.time.Period;

public class User {
	private int id;
	private String fName;
	private String lName;
	private String sex;
	private String dateOfBirth;
	private double height;
	private double weight;
	private String username;
	private String password;
	private String units;
	
	public User() {
	}

	//User newUser = new User(username, String.valueOf(password), "Dang", "Peos", "M", dob, weight, height, "metric");
	public User(String username, String password, String fName, String lName, String sex, String dateOfBirth, double weight, double height, String units) {
		super();
		this.dateOfBirth = dateOfBirth;
		this.fName = fName;
		this.lName = lName;
		this.sex = sex;
		this.height = height;
		this.weight = weight;
		this.username = username;
		this.password = password;
		this.units = units;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return fName;
	}

	public void getFirstName(String fName) {
		this.fName = fName;
	}

	public String getLastName() {
		return lName;
	}

	public void setLastName(String lName) {
		this.lName = lName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex){
		this.sex = sex;
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

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		if(units.equals("imperial") || units.equals("metric")){
			this.units = units;
		} else {
			this.units = "metric";
		}
	}

	public int getAge(String dateOfBirth) {
		LocalDate dob = LocalDate.parse(dateOfBirth);
		LocalDate todayDate = LocalDate.now();
		Period p = Period.between(dob, todayDate);
		return p.getYears();
	}

    public void setFirstName(String firstName) {
		this.fName = firstName;
    }
}
