package com.payroll.model;

public class ContractEmployee extends Employee
 {
 private double ratePerDay;
 public ContractEmployee(
		 ){
	 
 }
 public ContractEmployee(
		 String id,
		 String name,
		 String dept,
		 double rate){
	 super(id,name,dept,rate);
	 setRatePerDay(
			 rate);
	 }
 
 public double getRatePerDay(
		 ){
	 return ratePerDay;
	 } 
 public void setRatePerDay(
		 double v){
	 
	 if
	 (!Double.isFinite(v)||v<0)
		 throw new IllegalArgumentException(
				 "Rate must be non-negative.");
	 ratePerDay=v;
	 setBasePay(v);
	 }
 
 public String getEmployeeType (
		 ){
	 return "Contract";
	 }
 public double calculateGrossPay(
		 Attendance a){
	 if(
			 a==null)
		 throw new IllegalArgumentException
		 ("Attendance is required.");
	 return ratePerDay*a.getDaysWorked(
			 );
	 }
 public String toString(
		 ){
	 return super.toString(
			 )+" | Rate/day: "+ratePerDay;
	 }
}