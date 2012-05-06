package cn.xm.hanleylbs.domain;

public class ChatEntity {
	    private String name;
	    private String info;
	    private int layoutID;
	    
	    public ChatEntity(String name,String info, int layoutID){
	    	this.name=name;
	    	this.info=info;
	    	this.layoutID=layoutID;
	    }
		public String getName() {
			return name;
		}
		public String getInfo() {
			return info;
		}
		public void setInfo(String info) {
			this.info = info;
		}
		public int getLayoutID() {
			return layoutID;
		}
		public void setLayoutID(int layoutID) {
			this.layoutID = layoutID;
		}
}
