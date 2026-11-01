package com.payroll.model;

public class PermanentEmployee extends Employee
{
 private double allowance;
 public PermanentEmployee(
		 ){
	 
 }
 public PermanentEmployee(
		 String id,
		 String name,
		 String dept,
		 double salary,
		 double allowance
		 ){
	 super(id,name,dept,salary);
	 setAllowance(allowance);
	 }
 
 public double getAllowance(
		 ){
	 return allowance;
	 }
 public void setAllowance(
		 double v){
	 if
	 (!Double.isFinite(v)||v<0)
		 throw new IllegalArgumentException(
				 "Allowance must be non-negative.");
	 allowance=v;
	 }
 public String getEmployeeType(
		 ){
	 return "Permanent";
	 }
 
 public double calculateGrossPay(Attendance a){return getBasePay()+allowance;}
 public String toString(){return super.toString()+" | Allowance: "+allowance;}
}