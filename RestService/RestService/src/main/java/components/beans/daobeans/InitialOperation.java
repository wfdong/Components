package components.beans.daobeans;

import javax.xml.bind.annotation.*;

@XmlRootElement(name="initialOperation")
@XmlAccessorType (XmlAccessType.FIELD)
public class InitialOperation {
	//@XmlAttribute(name="id")
    String id;
	
	//@XmlElement(name="type")
    String type;
	
	//@XmlElement(name="name")
    String name;
	
	//@XmlValue
    String initialContent;
	
	public InitialOperation() {
		
	}
    public InitialOperation(String id, String type, String name, String initialContent) {
    	this.id = id;
    	this.type = type;
    	this.name = name;
    	this.initialContent = initialContent;
    }
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return initialContent;
	}
	public void setContent(String initialContent) {
		this.initialContent = initialContent;
	}
	
	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("InitialOperation{");
		sb.append("id='").append(id).append('\'');
		sb.append(", type='").append(type).append('\'');
		sb.append(", name='").append(name).append('\'');
		sb.append(", initialContent='").append(initialContent).append('\'');
		sb.append('}');
        return sb.toString();
	}
}
