package com.payroll.app;

import com.payroll.model.*;
import com.service.*;
import com.persistence.*;
import com.concurrency.*;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

public class App {
 private static final Scanner in =
		 new Scanner(System.in);
 public static void main(String[] args) 
     {
      EmployeeService es = new EmployeeService();
      PayrollService ps = new PayrollService(es);
      EmployeeJsonStore store = new EmployeeJsonStore 
      ("data/employees.json");
      ExecutorService pool = Executors.newFixedThreadPool(4);
  try 
  { System.out.println(store.load(es) ? 
		  "Saved data loaded." : "No saved data.");
  }
  catch 
  (Exception e) {
	  System.out.println
	  ("Load error: " + e.getMessage());
	  }
  boolean run = true;
  while(run) {
   System.out.println
   ("\n=== EMPLOYEE PAYROLL SYSTEM ===\n1"
   		+ " Add permanent employee\n2 "
   		+ "Add contract employee\n3 Record attendance\n4 "
   		+ "List employees\n5 Search employee\"n6 Calculate "
   		+ "all payrolls\n7 Concurrent payroll calculation\n8 Save data\n0 Save and exit");
   
   int c = integer
		   ("Choice: ");
   try {
    switch(c) {
     
    case 1 -> {
    	 String id=text("ID: "),
    			 n=text("Name: "),
    			 d=text("Department: ");
    	 double s=number("Monthly salary: "),
    			 a=number("Allowance: ");
    	 es.addEmployee(new PermanentEmployee(id,n,d,s,a));
    	 System.out.println("Added.");
    	 }
     
     
     case 2 -> {
    	 String id=text("ID: "),
         n=text("Name: "),
         d=text("Department: ");
         double r=number("Rate per day: ");
         es.addEmployee(new ContractEmployee(id,n,d,r));
         System.out.println("Added.");
         }
     
    case 3 -> {
    	 String id=text("Employee ID: ");
    	 int total=integer("Working days: "),
    	 worked=integer("Days worked: ");
    	 es.recordAttendance(new Attendance(id,total,worked));
    	 System.out.println("Attendance saved.");
    	 }
     
    case 4 -> { 
    	 es.getAllEmployees().forEach(System.out::println);
    	 System.out.println("Sorted by base pay:");
    	 es.sortBySalaryDescending().forEach(System.out::println);
    	 }
     
   case 5 -> {
    	 es.searchEmployees(text("Search ID/name/department: ")).forEach(System.out::println); 
    	 }
     
   case 6 -> 
     ps.calculateAllPayrolls().forEach
     (System.out::println);
     
  case 7 -> {
      List<Future<Payroll>> fs=new ArrayList<>();
      for
      (Employee e:es.getAllEmployees())
    	  if(es.getAttendance(e.getEmployeeId())!=null)
    		  fs.add(pool.submit(
    				  new PayrollTask(ps,e.getEmployeeId())));
      if
      (fs.isEmpty())
    	  System.out.println("No employees with attendance.");
      
      for
      (Future<Payroll> f:fs) 
    	  System.out.println(f.get());
     }
     
     case 8 ->
     save(store,es);
     
     case 0 ->
     { save(store,es);
     run=false; 
     }
     
     default -> 
     System.out.println("Invalid choice.");
    }
   } 
   catch (Exception e) {
	   System.out.println("Error: "+(e.getCause()!=null?e.getCause().getMessage():e.getMessage()));
	   }
  }
  pool.shutdown();
  in.close();
  System.out.println("Goodbye.");
 }
 static void save(EmployeeJsonStore s, EmployeeService es) {
	 try { s.save(es.getAllEmployees(),es.getAllAttendance());
	 System.out.println("Saved data/employees.json"); 
	 }
	 catch(IOException e) {
		 System.out.println("Save error: "+e.getMessage());
		 } 
	 }
 
 static String text(String p) {
	 System.out.print(p);
	 return in.nextLine().trim();
	 }
 
 static int integer(String p) {
	 while(true) try {
		 return Integer.parseInt(text(p));
		 } 
	 catch(NumberFormatException e) 
	 { System.out.println("Enter a whole number."); 
  } 
}
 static double number(String p) {
	 while(true) try {
		 double d=Double.parseDouble(text(p));
		 if(d>=0 && Double.isFinite(d)) return d; 
		 } 
	 catch(NumberFormatException e) {
		 
	 } 
  }
}