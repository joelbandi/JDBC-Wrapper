//import java.sql.Date;

public class Star extends Person {
	private String dob;
	private String photoURL;
	
	public Star(int id, String first_name, String last_name, String dob, String photoURL) {
		super(id, first_name, last_name);
		this.dob = dob;
		this.photoURL = photoURL;
	}

	public String getDob() {
		return dob;
	}

	public String getPhotoURL() {
		return photoURL;
	}
	
	
		
	
	

}