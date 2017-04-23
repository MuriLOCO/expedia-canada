package com.jose.exercice.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * This Class is responsible to store the previous outputs.
 * @author jose
 *
 */

@Entity
public class OutputBox {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String output;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}
	
	
}
