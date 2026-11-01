package com.persistence;

import com.fasterxml.jackson.databind.*;
import com.payroll.model.*;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class EmployeeJsonStore {
 private final ObjectMapper mapper=new 
		 ObjectMapper().enable(
				 SerializationFeature.INDENT_OUTPUT);
 
 private final Path file;
 
 public EmployeeJsonStore(
		 String name){
	 file=Paths.get(
			 name).toAbsolutePath(
					 );
	 }
 public static class EmployeeRecord
   {
     public String 
     type,
     employeeId,
     name,
     department;
  public double basePay,allowance,ratePerDay;
  public EmployeeRecord(
		  ){
	  
  }
 }
 public static class StoreData {
  public List<EmployeeRecord> employees=new
		  ArrayList<>(); 
  public List<Attendance> attendance=
		  new ArrayList<>();
  public StoreData(){
	  
  }
 }
 public interface EmployeeServiceTarget {
	 void replaceAll(
			 List<Employee> employees,
			 List<Attendance> attendance);
	 }
 public void save(
		 List<Employee> employees,
		 List<Attendance> attendance)
				 throws IOException
 {
	 
  StoreData data=new 
		  StoreData(
		  );
  for
  (Employee e:employees){
	  EmployeeRecord r = new EmployeeRecord();
	  r.type=e.getEmployeeType();
	  r.employeeId=e.getEmployeeId();
	  r.name=e.getName();
	  r.department=e.getDepartment();
	  r.basePay=e.getBasePay();
   if
   (e instanceof PermanentEmployee p)r.allowance=
   p.getAllowance();
   if(e instanceof ContractEmployee c)r.ratePerDay=
		   c.getRatePerDay();data.employees.add(r);
		   }
  
  data.attendance=attendance;
  Path parent=file.getParent(
		  );
  if(parent!=
		  null)Files.createDirectories(parent);
  Path temp=
		  file.resolveSibling(
				  file.getFileName()+".tmp");
  mapper.writeValue(
		  temp.toFile(),
		  data);
  try{
	  Files.move(
			  temp,file,
			  StandardCopyOption.REPLACE_EXISTING,
			  StandardCopyOption.ATOMIC_MOVE);
	  }
  catch(
		  IOException ex){
	  Files.move(
			  temp,file,
			  StandardCopyOption.REPLACE_EXISTING);
	  }
  
 }
 public boolean load(
		 EmployeeServiceTarget target)
				 throws IOException {
  if
  (!Files.exists(file))
	  return false;
  StoreData data=
		  mapper.readValue(
				  file.toFile(),
				  StoreData.class);
  List<Employee> es=
		  new ArrayList<>(
				  );
  if
  (data.employees!=null)
	  for(EmployeeRecord r:data.employees){
   if
   ("Permanent".equalsIgnoreCase(r.type))
	   es.add
	   (new PermanentEmployee
			   (r.employeeId,
			    r.name,
			    r.department,
			    r.basePay,
			    r.allowance)
			   );
   else if
   ("Contract".equalsIgnoreCase(r.type))
	   es.add(new ContractEmployee(
			   r.employeeId,
			   r.name,
			   r.department,
			   r.ratePerDay)
			   );
   else throw new 
   IOException("Unknown employee type: "+r.type
		   );
  }
  target.replaceAll(
		  es,data.attendance==
		  null?new ArrayList<>()
				  :data.attendance);
  return true;
 }
}