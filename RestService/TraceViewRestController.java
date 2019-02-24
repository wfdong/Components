package components.controllers.traceViewController;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import components.Response;
import components.RestService.daoService.MysqlCRUDService;
import components.beans.daobeans.InitialOperation;
import components.beans.traceviewbeans.TraceView;
import components.controllers.AbstractController;

@RestController
public class TraceViewRestController extends AbstractController{
	
	@Autowired
    private MysqlCRUDService mysqlCRUDService;

	public TraceViewRestController() throws JAXBException, FileNotFoundException {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@PostConstruct
	public void init() {
		if(this.initialOperations!=null&&!this.initialOperations.isEmpty()) {
			String initSqls="";
			for(InitialOperation initOper:this.initialOperations) {
				initSqls=initSqls+initOper.getContent();
			}
			if(initSqls.length()>1) {
				try {
				    mysqlCRUDService.excuteSql(initSqls);
				}catch(DataAccessException e) {
					System.out.println(e.getStackTrace());
				}
			}
		}
	}
	
	@RequestMapping({"/getArrowView"})
	public Response getArrowView(@RequestParam(value="filename", defaultValue="RETRACE0000") String filename) {
		File file = new File(filename);
		TraceView traceView = analysisTrace(file);
		return traceView;
	}
	
	public static void main(String[] args) {
		File file = new File("MED-4649_generic_rest_APNS_tmf1.trace");
		TraceViewRestController con;
		try {
			con = new TraceViewRestController();
			TraceView traceView = con.analysisTrace(file);
			System.out.println(traceView.getNodeDataArray().get(0));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	private TraceView analysisTrace(File file) {
		TraceView traceView = new TraceView();
		String pattern_pdu_in = "^\\[[0-9]{8}] PDU_IN_=.*";
		String pattern_pdu_in_logerr = "^\\[[0-9]{8}] PDU_IN_=LOGERR.*";
		String pattern_pdu_out = "^\\[[0-9]{8}] PDU_OUT=.*";
		String pattern_pdu_out_logerr = "^\\[[0-9]{8}] PDU_OUT=LOGERR.*";
		String pattern_pdu_status = "^\\[[0-9]{8}] PDU_STATUS=OK.*";
		
		String pattern_device_to = "^\\[[0-9]{8}] TO___.*";
		String pattern_device_from = "^\\[[0-9]{8}] FROM_.*";
		
		String pattern_device_to_locker = "^\\[[0-9]{8}] TO___LOCKER.*";
		String pattern_device_from_locker = "^\\[[0-9]{8}] FROM_LOCKER.*";
		
		String pattern_mainlines = "^\\[[0-9]{8}] .*";
		
		ArrayList<HashMap<String, String>> nodeDataArray = new ArrayList<HashMap<String, String>>();
		ArrayList<HashMap<String, String>> linkDataArray = new ArrayList<HashMap<String, String>>();
		/*HashMap<String, String> pdu_top_left = new HashMap<String, String>();
		HashMap<String, String> pdu_top_right = new HashMap<String, String>();
		pdu_top_left.put("id", "0");
		pdu_top_left.put("loc", "0 0");
		pdu_top_left.put("text", "frontend");
		
		pdu_top_right.put("id", "1");
		pdu_top_right.put("loc", "900 0");
		pdu_top_right.put("text", "Mediator");
		nodeDataArray.add(pdu_top_left);
		nodeDataArray.add(pdu_top_right);*/
		int cur_y = 0;
		try {
			String cur_pdu_in_id = "";//[00000001]
			String cur_pdu_out = "";
			Scanner scanner = new Scanner(file);
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if(Pattern.matches(pattern_pdu_in, line)&&!Pattern.matches(pattern_pdu_in_logerr, line)){
					//[00000001]...lines
					String[] curLine = line.split("=");
					cur_pdu_in_id = curLine[0].split(" ")[0];
						HashMap<String, String> leftNode = new HashMap<String, String>();
						HashMap<String, String> rightNode = new HashMap<String, String>();
						HashMap<String, String> request = new HashMap<String, String>();
						leftNode.put("id", curLine[0]);
						cur_y = cur_y + 80;
						String loc_left = "0 " + cur_y;
						String loc_right = "900 " + cur_y;
						leftNode.put("loc", loc_left);
						leftNode.put("text", "frontend");
						
						rightNode.put("id", curLine[0]+"@");
						rightNode.put("loc", loc_right);
						rightNode.put("text", "Mediator");
						nodeDataArray.add(leftNode);
						nodeDataArray.add(rightNode);
						
						String color = "#7CFC00";
						request.put("from", curLine[0]);
						request.put("to", curLine[0]+"@");
						request.put("text", "PDU_IN(request)");
						request.put("color", color);
						request.put("curviness", "20");
						if(curLine.length>=2) {
							String details = "";
							if(curLine[1].length()>80) {
								for(int i=80;i<curLine[1].length();i=i+80) {
									if(i<=curLine[1].length()-1) {
										details = details +"\n"+ curLine[1].substring(i-80, i);
									}else {
										details = details +"\n"+ curLine[1].substring(i-80, curLine[1].length()-1);
									}
								}
							}else {
								details = curLine[1];
							}
							request.put("details", details);
						}
						linkDataArray.add(request);
				}else if(Pattern.matches(pattern_pdu_out, line)&&!Pattern.matches(pattern_pdu_out_logerr, line)){
					String[] curLine = line.split("=");
					if(cur_pdu_in_id.equals(curLine[0].split(" ")[0])) {
						if(scanner.hasNextLine()) {
							String status_line = scanner.nextLine();
							HashMap<String, String> response = new HashMap<String, String>();
							String color = "#7CFC00";
							if(!Pattern.matches(pattern_pdu_status, status_line)) {
								color = "#FF0000";
							}
							String toNode = curLine[0].split(" ")[0]+" PDU_IN_";
							response.put("from", toNode+"@");
							response.put("to", toNode);
							response.put("text", "PDU_OUT(response)");
							response.put("color", color);
							response.put("curviness", "20");
							if(curLine.length>=2) {
								String details = "";
								String unformatedDetails = curLine[1];
								details = formatStringDetails(details, unformatedDetails);
								response.put("details", details);
							}
							linkDataArray.add(response);
						}/*else {
							traceView.setStatus("FAILED");
							traceView.setErroredId(cur_pdu_in_id);
							return traceView;
						}*/
					}/*else {
						traceView.setStatus("FAILED");
						traceView.setErroredId(cur_pdu_in_id);
						return traceView;
					}*/
					
				}else {
					// deal the detailed interactives with different devices
					//HashMap
					if(nodeDataArray.size()>1) {
						String nodeDetails = "";
						//browse current interactive lines,stored in stack
						Stack<String> stack = new Stack<String>();
						String curID = line.split(" ")[0];
						while(cur_pdu_in_id.equals(curID)) {
							boolean had_steped_flag = false;
							if(Pattern.matches(pattern_device_to, line)&&!Pattern.matches(pattern_device_to_locker, line)) {
								String deviceStr = line.split(":")[1];
								if(deviceStr!=null&&deviceStr.contains("=")) {
									String deviceName = deviceStr.split("=")[0].trim();
									String requestDetails = deviceStr.split("=")[1];
									String requestFormatedDetails = "";
									requestFormatedDetails = formatStringDetails(requestFormatedDetails, requestDetails);
									if(scanner.hasNextLine()) {
										line = scanner.nextLine();
										while(!Pattern.matches(pattern_mainlines, line)) {
											String formatedLine = "";
											formatedLine = formatStringDetails(formatedLine, line);
											requestFormatedDetails = requestFormatedDetails + "\n" + formatedLine;
											if(scanner.hasNextLine()) {
												line = scanner.nextLine();
												if(Pattern.matches(pattern_mainlines, line)) {
													curID = line.split(" ")[0];
													had_steped_flag = true;
													break;
												}
											}
										}
									}
									String status = "SUCCESS";
									stack.push(deviceName+"@@"+requestFormatedDetails+"@@"+status);
								}
							}else if(Pattern.matches(pattern_device_from, line)&&!Pattern.matches(pattern_device_from_locker, line)) {
								String deviceStr = line.split(":")[1];
								if(deviceStr!=null&&deviceStr.contains("=")) {
									String deviceName = deviceStr.split("=")[0].trim();
									String resposeDetails = deviceStr.split("=")[1];
									String responseFormatedDetails = "";
									responseFormatedDetails = formatStringDetails(responseFormatedDetails, resposeDetails);
									if(scanner.hasNextLine()) {
										line = scanner.nextLine();
										while(!Pattern.matches(pattern_mainlines, line)) {
											String formatedLine = "";
											formatedLine = formatStringDetails(formatedLine, line);
											responseFormatedDetails = responseFormatedDetails + "\n" + formatedLine;
											if(scanner.hasNextLine()) {
												line = scanner.nextLine();
												if(Pattern.matches(pattern_mainlines, line)) {
													curID = line.split(" ")[0];
													had_steped_flag = true;
													break;
												}
											}
										}
									}
									String status = "SUCCESS";
									if(responseFormatedDetails.contains("No matching rows")||responseFormatedDetails.contains("NOT ACCEPTED")||
									    responseFormatedDetails.contains("FAULT CODE")||(responseFormatedDetails.contains("HTTP/1.1 ")&&!responseFormatedDetails.contains("HTTP/1.1 200 OK"))||
									    responseFormatedDetails.contains("result: FAILURE")) {
										status = "FAILED";
									}
									String formatedDetails = deviceName+"@@"+responseFormatedDetails+"@@"+status;
									String cur_top = stack.peek();
									String cur_top_deviceName = cur_top.split("@@")[0];
									if(deviceName.equals(cur_top_deviceName)) {
										String request = stack.pop();
										nodeDetails = nodeDetails + "||" + request + "%%" + formatedDetails;
									}
								}
							}
							
							if(!had_steped_flag) {
								if(scanner.hasNextLine()) {
									line = scanner.nextLine();
									curID = line.split(" ")[0];
								}
							}
							
						}
						
						
						nodeDataArray.get(nodeDataArray.size()-1).put("details", nodeDetails);
					}
					
				}
			}
			scanner.close();
			if(nodeDataArray.size()>=4) {
				HashMap<String, String> leftLine = new HashMap<String, String>();
				HashMap<String, String> rightLine = new HashMap<String, String>();
				leftLine.put("from", nodeDataArray.get(0).get("id"));
				leftLine.put("from", nodeDataArray.get(nodeDataArray.size()-2).get("id"));
				leftLine.put("curviness","0");
				rightLine.put("from", nodeDataArray.get(1).get("id"));
				rightLine.put("from", nodeDataArray.get(nodeDataArray.size()-1).get("id"));
				rightLine.put("curviness","0");
				linkDataArray.add(leftLine);
				linkDataArray.add(rightLine);
			}
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		traceView.setNodeDataArray(nodeDataArray);
		traceView.setLinkDataArray(linkDataArray);
		return traceView;
	}

	private String formatStringDetails(String details, String unformatedDetails) {
		if(unformatedDetails.length()>80) {
			for(int i=80;i<unformatedDetails.length();i=i+80) {
				if(i<=unformatedDetails.length()-1) {
					details = details +"\n"+ unformatedDetails.substring(i-80, i);
				}else {
					details = details +"\n"+ unformatedDetails.substring(i-80, unformatedDetails.length()-1);
				}
			}
		}else {
			details = unformatedDetails;
		}
		return details;
	}
	
	

}
