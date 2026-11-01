package com.payroll.model;
import java.util.Objects;
public abstract class Employee implements 
Comparable<Employee> {
private String 
         employeeId,
         name,
         department;
 private double basePay;
 public Employee() {}
 public Employee(
		 String id,
		 String name,
		 String department,
		 double basePay)
 {
	 this.employeeId=id;
	 this.name=name; 
	 this.department=department;
	 setBasePay(basePay);
	 }
 public abstract String getEmployeeType(); 
 public abstract double calculateGrossPay(
		 Attendance attendance);
 public String getEmployeeId(){
	 return employeeId;
	 }
 public void setEmployeeId(String v){
	 employeeId=v;
	 }
 public String getName(){
	 return name;
	 } 
 public void setName(String v){
	 name=v;
	 }
 public String getDepartment(){
	 return department;
	 }
 public void setDepartment(String v)
     {
	 department=v;
	 }
 public double getBasePay(){
	 return basePay;
	 } 
 public void setBasePay(double v)
	 {
		 if(!Double.isFinite(v)||v<0)
			 throw new IllegalArgumentException("Pay must be non-"
			 		+ "negative.")
			 ;basePay=v;
			 }
 public int compareTo(Employee e)
 {
	 return String.CASE_INSENSITIVE_ORDER.compare(
			 name,e.name);
	 }
 public String toString(){
	 return employeeId+"  "+name+"  "+department+"  "+getEmployeeType()+"  Base pay: "+basePay;
	 }
 public boolean equals(Object o){
	 return o instanceof Employee e && Objects.equals(
			 employeeId,e.employeeId);
	 }
 public int hashCode(){
	 return Objects.hash(employeeId);
	 
 }
}