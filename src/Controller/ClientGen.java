package Controller;

import java.util.Random;

public class ClientGen {
	private String code;
	private String name;
	private String telf;
	private String email;
	private int status;	
	Random ran;	
	private int value;
	
	
	public ClientGen(){		
		// Constructor
		value = 1;
		ran = new Random();
	}
	
	public void setNewValue(int val){
		this.value = val;
	}
	
	public int getValue(){
		return value;
	}
	
	public String genRanCode(){
		// Code generator
		code = String.valueOf(ran.nextInt(Integer.MAX_VALUE));
		return code;
	}
	
	public String genRanName(){
		// Name generator
		if(value<10)
			name = "Client0"+value;
		else 
			name = "Client"+value;
		value++;
		return name;
	}
	
	public String genRanTelf(){
		// Telephone generator
		String range = String.valueOf(ran.nextInt(Integer.MAX_VALUE));
		if(range.length()<10){
			for(int i=range.length();i<10;i++)
				range+=0;
		}else if(range.length()>10)
			range = range.substring(0,10);
		telf = "+52"+range;
		return telf;
	}
	
	public String genRanEmail(){
		// email generator
		email = name + "@telynet.com";
		return email;
	}
	
	public int genRanStatus(){
		// Status visited generator
		status = ran.nextInt(2)%2; 
		return status;
	}

}
