package components.beans.traceviewbeans;

import java.util.ArrayList;
import java.util.HashMap;

import components.Response;

public class TraceView implements Response{
	ArrayList<HashMap<String, String>> nodeDataArray;
	ArrayList<HashMap<String, String>> linkDataArray;
	
	String status = "SUCCESS";
	String erroredId = "";
	
	public TraceView(ArrayList<HashMap<String, String>> nodeDataArray, ArrayList<HashMap<String, String>> linkDataArray) {
		this.nodeDataArray = nodeDataArray;
		this.linkDataArray = linkDataArray;
	}
	
	public ArrayList<HashMap<String, String>> getNodeDataArray() {
		return nodeDataArray;
	}

	public void setNodeDataArray(ArrayList<HashMap<String, String>> nodeDataArray) {
		this.nodeDataArray = nodeDataArray;
	}

	public ArrayList<HashMap<String, String>> getLinkDataArray() {
		return linkDataArray;
	}

	public void setLinkDataArray(ArrayList<HashMap<String, String>> linkDataArray) {
		this.linkDataArray = linkDataArray;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getErroredId() {
		return erroredId;
	}

	public void setErroredId(String erroredId) {
		this.erroredId = erroredId;
	}

	public TraceView() {
		
	}
	
}
