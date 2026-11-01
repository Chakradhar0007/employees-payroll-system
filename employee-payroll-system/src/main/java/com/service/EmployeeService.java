package com.service;

import com.payroll.model.*;
import com.persistence.EmployeeJsonStore.EmployeeServiceTarget;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class EmployeeService 

implements EmployeeServiceTarget {
 
	private final Map<String,Employee> employees=
		 new ConcurrentHashMap<>();
 
	private final Map<String,Attendance> attendance=
			new ConcurrentHashMap<>();
 
	public synchronized void addEmployee(Employee e){
		if
		(e==null||blank(e.getEmployeeId())||
		blank(e.getName())||
		blank(e.getDepartment()))
			throw new
			IllegalArgumentException
			("Employee ID,"
					+ " name and department are required.");
		if
			(employees.putIfAbsent
					(e.getEmployeeId(),
							e)!=
							null)
			throw new
			IllegalArgumentException
			("Employee ID already exists.");
		}
 public synchronized void recordAttendance(
		 Attendance a){
	 if(a==null||!employees.containsKey
			 (a.getEmployeeId()))
		 throw new IllegalArgumentException
		 ("Add employee first.");
	 attendance.put(a.getEmployeeId(),a);
	 }
 public Employee findEmployee(String id){
	 return employees.get(id);
	 }
 public Attendance getAttendance(String id){
	 return attendance.get(id);
	 }
 public List<Employee> getAllEmployees(){
	 List<Employee> l=
			 new ArrayList<>(employees.values());
	 l.sort(Comparator.naturalOrder());return l;
	 }
 public List<Attendance> getAllAttendance(){
	 return new ArrayList<>(attendance.values());
	 }
 public List<Employee> searchEmployees(String q){
	 String k=q==null?"":q.toLowerCase().trim();
	 List<Employee> l=new ArrayList<>();
	 for(Employee e:employees.values())
		 if(e.getEmployeeId().toLowerCase().contains(k)||
				 e.getName().toLowerCase().contains(k)||
				 e.getDepartment().toLowerCase().contains(k))l.add(e);
	 l.sort(Comparator.naturalOrder());
		 return l;
		 }
 public List<Employee> sortBySalaryDescending(){
	 List<Employee> l=getAllEmployees();
	 l.sort(Comparator.comparingDouble(
			 Employee::getBasePay).reversed());
	 return l;
	 }
 public synchronized void replaceAll
 (List<Employee> es,List<Attendance> as){
	 Map<String,Employee> ne=
			 new ConcurrentHashMap<>();
	 Map<String,Attendance> na=
			 new ConcurrentHashMap<>();
	 for(Employee e:es)
		 if(e==null||
		 blank(e.getEmployeeId())||
		 ne.putIfAbsent(e.getEmployeeId(),e)!=
		 null)
			 throw new 
			 IllegalArgumentException
			 ("Invalid/duplicate employee data.");
	 for(Attendance a:as){
		 if(a==null||!ne.containsKey(a.getEmployeeId()))
			 throw new IllegalArgumentException
			 ("Attendance employee missing.");
		 na.put(a.getEmployeeId(),a);
		 }
	 employees.clear();
	 employees.putAll(ne);
	 attendance.clear();
	 attendance.putAll(na);
	 }
 private boolean blank(String s){
	 return s==null
			 ||s.trim().isEmpty();
	 }
}