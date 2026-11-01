package com.concurrency;

import com.payroll.model.Payroll;
import com.service.PayrollService;
import java.util.concurrent.Callable;

public class PayrollTask 
implements Callable<Payroll> 
{	
 private final PayrollService service;
  private final String employeeId;
  
 public PayrollTask(
		 PayrollService service
		 ,String employeeId
		 ){
	 this.service=service;
	 this.employeeId=employeeId;
	 }
 public Payroll call(){
	 return service.calculatePayroll(employeeId);
	 }
}