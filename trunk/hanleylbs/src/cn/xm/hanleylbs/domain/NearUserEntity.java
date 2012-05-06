package cn.xm.hanleylbs.domain;

public class NearUserEntity {
	
	    private String name;
	    private String age;
	    private String tel;
	    private String address;
	    private String gender;
	    
	    private int layoutID;

		public NearUserEntity(String name, String age, String tel,
				String address, String gender, int layoutID) {
			super();
			this.name = name;
			this.age = age;
			this.tel = tel;
			this.address = address;
			this.gender = gender;
			this.layoutID = layoutID;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getAge() {
			return age;
		}

		public void setAge(String age) {
			this.age = age;
		}

		public String getTel() {
			return tel;
		}

		public void setTel(String tel) {
			this.tel = tel;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getGender() {
			return gender;
		}

		public void setGender(String gender) {
			this.gender = gender;
		}

		public int getLayoutID() {
			return layoutID;
		}

		public void setLayoutID(int layoutID) {
			this.layoutID = layoutID;
		}
	    
		
	    
}
