package components.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import components.beans.daobeans.InitialOperation;
import components.beans.daobeans.Mysqls;
import components.beans.daobeans.Operation;

public abstract class AbstractController {

	public static HashMap<String, Operation> mysqls;
	public static List<InitialOperation> initialOperations;
	
	public AbstractController() throws JAXBException, FileNotFoundException{
		if(mysqls==null) {
			File file = new File("src\\main\\resources\\mysqlOperations.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(Mysqls.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			Mysqls mysqloperations = (Mysqls) unmarshaller.unmarshal(file);
			initialOperations = mysqloperations.getInitialOperations();
			List<Operation> operations = mysqloperations.getOperations();
			mysqls = new HashMap<String, Operation>();
			for(Operation operation:operations) {
				mysqls.put(operation.getId(), operation);
			}
			
		}
	}
}
