package cn.xm.hanleylbs.domain;

import org.json.JSONObject;

public class TaskInfo {

	private JSONObject jsonObj;
	
	private String opCode;
	
	public TaskInfo(){
		
	}

	public JSONObject getJsonObj() {
		return jsonObj;
	}

	public void setJsonObj(JSONObject jsonObj) {
		this.jsonObj = jsonObj;
	}

	public String getOpCode() {
		return opCode;
	}

	public void setOpCode(String opCode) {
		this.opCode = opCode;
	}
	
	
}
