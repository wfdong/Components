package components.controllers.traceViewController;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;


public class Test {

	public static void main(String[] args) {
		//File file = new File("MED-4649_generic_rest_APNS_tmf1.trace");
			//con = new TraceViewRestController();
			//TraceView traceView = con.analysisTrace(file);
			String pattern_pdu_in = "^\\[[0-9]{8}] PDU_IN_.*";
			String pattern_pdu_out = "^\\[[0-9]{8}] PDU_OUT.*";
			String pattern_pdu_status = "^\\[[0-9]{8}] PDU_STATUS.*";
			//Pattern r = Pattern.compile(pattern);
			File directory = new File(".");
			try {
				System.out.println(directory.getCanonicalPath());
				System.out.println(directory.getAbsolutePath()); 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
			
			if(Pattern.matches(pattern_pdu_in,"[00000001] PDU_IN1_=LOGERR Preseed AUC")) {
				System.out.println(111);
			}else {
				System.out.println(000);
			}
			
			if(Pattern.matches(pattern_pdu_out,"[00000001] PDU_OUT=LOGERR Preseed AUC")) {
				System.out.println(111);
			}else {
				System.out.println(000);
			}
			
			if(Pattern.matches(pattern_pdu_status,"[00000001] PDU_STATUS=OK CODE=0002")) {
				System.out.println(111);
			}else {
				System.out.println(000);
			}
			
			
		
		
	}
}
