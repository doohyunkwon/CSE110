package edu.ucsd.cse110.server.db;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class DataElement implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue
	public Integer getId() { return id; }
	public void setId(Integer id) { this.id = id; }
	private Integer id;
	
	@Column(name="dataName")
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	private String name;
	
	public int getQuantity() {return quantity;}
	public void setQuantity(int quantity) {this.quantity = quantity;}
	private int quantity;
	
	public DataElement() {	}
	
	public DataElement(String name, int quantity) {
		this.name = name;
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "DataElement [id=" + id + ", name=" + name + ", quantity="
				+ quantity + "]";
	}	
}
