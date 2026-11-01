package com.payroll.model;

public class Attendance {

	private String employeeId;
    private int workingDays, daysWorked;
 
    public Attendance(){
    	
    }
 public Attendance(
		 String id, int total, int worked
		 ){
	 employeeId=id;
	 setWorkingDays(total); setDaysWorked(worked);
	 }
 
 public String getEmployeeId(
		 ){
	 return employeeId;
	 }
 public void setEmployeeId(
		 String v
		 ){
	 employeeId=v;
	 }
 public int getWorkingDays(
		 ){
	 return workingDays;
	 }
 public void setWorkingDays(
		 int v){
	 if(v<=0)throw new IllegalArgumentException(
			 "Working days must be > 0.");
	 workingDays=v;if(
			 daysWorked>v)daysWorked=v;
	 }
 public int getDaysWorked(
		 ){
	 return daysWorked;
	 }
 public void setDaysWorked(
		 int v){
	 if(v<0||(
			 workingDays>0&&v>workingDays))
		 throw new IllegalArgumentException
		 ("Days worked must be within working days.");
	 daysWorked=v;
	 }
 
 public String toString(
		 ){
	 return employeeId+": "+daysWorked+"/"+workingDays+" days";
	 }
} 
