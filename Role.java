package main;

public class Role extends Login{
	int UserId =0;
	String Email,Username,Password,Gender,Nationality;
	int Age =0;
	private String type;
	
	public Role(int userId, String email, String username, String password, String gender, String nationality,
	int age) {
		super();
		UserId = userId;
		Email = email;
		Username = username;
		Password = password;
		Gender = gender;
		Nationality = nationality;
		Age = age;
	}

	public int getUserId() {
		return UserId;
	}

	public void setUserId(int userId) {
		UserId = userId;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getGender() {
		return Gender;
	}

	public void setGender(String gender) {
		Gender = gender;
	}

	public String getNationality() {
		return Nationality;
	}

	public void setNationality(String nationality) {
		Nationality = nationality;
	}

	public int getAge() {
		return Age;
	}

	public void setAge(int age) {
		Age = age;
	}
	
	 public Role(String type) {
         this.type = type;
     }

     public String getType() {
         return type;
     }



}
