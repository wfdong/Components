package components.beans.daobeans;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.*;

@XmlRootElement(name="mysql")
@XmlAccessorType (XmlAccessType.FIELD)
public class Mysqls {
	@XmlElement(name = "initialOperation")
    List<InitialOperation> initialOperations;
	
	@XmlElement(name = "operation")
    List<Operation> operations;
    
    public List<InitialOperation> getInitialOperations() {
		return initialOperations;
	}

	public List<Operation> getOperations() {
		return operations;
	}

	//@XmlElement(name="initialOperations")
    public void setInitialOperations(List<InitialOperation> initialOperations) {
    	this.initialOperations = initialOperations;
    }
    
    //@XmlElement(name="operations")
    public void setOperations(List<Operation> operations) {
    	this.operations = operations;
    }
    
    public void add(InitialOperation initialOperation) {
    	if(this.initialOperations==null) {
    		this.initialOperations = new ArrayList<InitialOperation>();
    	}
    	this.initialOperations.add(initialOperation);
    }
    
    public void add(Operation operation) {
    	if(this.operations==null) {
    		this.operations = new ArrayList<Operation>();
    	}
    	this.operations.add(operation);
    }
	
}
