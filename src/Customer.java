public class Customer extends Person{
		private String cc;
		private String add;
		private String email,pwd;
		
		
		public Customer(int id, String first_name, String last_name, String cc, String add, String email, String pwd) {
			super(id, first_name, last_name);
			this.cc = cc;
			this.add = add;
			this.email = email;
			this.pwd = pwd;
		}


		public String getCc() {
			return cc;
		}


		public String getAdd() {
			return add;
		}


		public String getEmail() {
			return email;
		}


		public String getPwd() {
			return pwd;
		}
		
		

}