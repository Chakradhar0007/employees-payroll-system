package com.payroll.model;
public class Payroll {
 private String 
 employeeId,
 employeeName,
 employeeType;
 private double grossPay,
 deductions,
 netPay;
 public Payroll(){
 }
 public Payroll(String id,String name,String type,double gross,double deductions)
 {
	 employeeId=id;
	 employeeName=name;
	 employeeType=type;
	 grossPay=gross;
	 this.deductions=deductions;
	 netPay=gross-deductions;
	 }
 public String getEmployeeId(){
	 return employeeId;
	 } 
 public void setEmployeeId(
			 String v){
		 employeeId=v;
		 }
 public String getEmployeeName(){
	 return employeeName;
	 } 
 public void setEmployeeName(String v){
	 employeeName=v;
	 }
 public String getEmployeeType(){
	 return employeeType;
	 }
 public void setEmployeeType(String v){
	 employeeType=v;
	 }
 public double getGrossPay(){
	 return grossPay;
	 } 
 public void setGrossPay(double v){
	 grossPay=v;
	 }
 public double getDeductions(){
	 return deductions;
	 } 
 public void setDeductions(double v){
	 deductions=v;
	 }
 public double getNetPay(){
	 return netPay;
	 }
 public void setNetPay(double v){
	 netPay=v;
	 }
 public String toString(){
	 return employeeName+" ("+employeeId+")  "+employeeType+"  Gross: "+grossPay+"  Deductions: "+deductions+"  Net: "+netPay;
	 }
}