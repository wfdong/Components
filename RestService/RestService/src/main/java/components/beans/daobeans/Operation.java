package components.beans.daobeans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name="operation")
@XmlAccessorType (XmlAccessType.FIELD)
public class Operation {
	//@XmlAttribute(name="id")
	String id;
	
	//@XmlElement(name="type")
    String type;
	
	//@XmlElement(name="name")
    String name;
	
	//@XmlValue
    String content;
	
	public Operation() {
		
	}
    public Operation(String id, String type, String name, String content) {
    	this.id = id;
    	this.type = type;
    	this.name = name;
    	this.content = content;
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
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("InitialOperation{");
		sb.append("id='").append(id).append('\'');
		sb.append(", type='").append(type).append('\'');
		sb.append(", name='").append(name).append('\'');
		sb.append(", content='").append(content).append('\'');
		sb.append('}');
        return sb.toString();
	}
}
