package com.xfn.sreit.value;

import java.io.Serializable; 

public class FileValue implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id = 0; 
	private String oldPath = "";
	private String newPath = ""; 
	private String name = "";
	private boolean isBeCheck = false;
	
	public boolean isBeCheck() {
		return isBeCheck;
	}
	public void setBeCheck(boolean isBeCheck) {
		this.isBeCheck = isBeCheck;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOldPath() {
		return oldPath;
	}
	public void setOldPath(String oldPath) {
		this.oldPath = oldPath;
	}
	public String getNewPath() {
		return newPath;
	}
	public void setNewPath(String newPath) {
		this.newPath = newPath;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}  
	
}
