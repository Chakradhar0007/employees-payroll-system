package com.service;

import com.payroll.model.*;
import java.util.*;

public class PayrollService {
	
 private final EmployeeService employees;
 private final double deductionRate;
 
 public PayrollService(
		 EmployeeService e){
	 this(e,0.10);
	 }
 
 public PayrollService(
		 EmployeeService e,
		 double rate){
	 if
	 (rate<0||rate>1)
		 throw new
		 IllegalArgumentException
		 ("Deduction rate must be 0..1.");
	 employees=e;deductionRate=rate;
	 }
 
 public Payroll calculatePayroll(
		 String id){
	 Employee e=
			 employees.findEmployee(id);
	 if(e==null)
		 throw new 
		 IllegalArgumentException
		 ("Employee not found.");
	 Attendance a=employees.getAttendance(id);
	 if(a==null)throw new 
	 IllegalArgumentException
	 ("Record attendance first.");
	 double gross=e.calculateGrossPay(a);
	 return new Payroll(id,e.getName(),
			 e.getEmployeeType(),
			 gross,
			 gross*deductionRate);
	 }
 public List<Payroll> calculateAllPayrolls(){
	 List<Payroll> out=new ArrayList<>();
	 for(Employee e:employees.getAllEmployees())
		 if(employees.getAttendance(e.getEmployeeId())!=null)
			 out.add(calculatePayroll(e.getEmployeeId()));
	 return out;
	 }
 
}