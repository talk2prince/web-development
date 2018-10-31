package com.nc.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.List;

import com.nc.dao.EmployeeDao;
import com.nc.dao.LeaveOperationsDao;
import com.nc.model.Employee;
import com.nc.model.LeaveRecord;
import com.nc.model.LeaveType;

public class LeaveOperationService {

	private LeaveOperationsDao leaveOpDao;
	private EmployeeDao empDao;
	
	public LeaveOperationService() {
		leaveOpDao = new LeaveOperationsDao();
		empDao = new EmployeeDao();
	}
	
	public List<LeaveType> getAllLeaveTypes(String username) {
		List<LeaveType> leaveTypes = new ArrayList<>();
		Employee employee = empDao.getEmployeeByUserID(username); 
		for(LeaveType lt:leaveOpDao.getAllLeaveTypes()) {
			if(employee.getGender().equals("M") && lt.getLeaveId()==3) {
				continue;
			}else if(employee.getGender().equals("F") && lt.getLeaveId()==4) {
				continue;
			}else {
				leaveTypes.add(lt);
			}
		}
		return leaveTypes;
	}
	
	public boolean addNewLeave(LeaveRecord leaveRecord,String username) {
		Employee employee = empDao.getEmployeeByUserID(username);
		leaveRecord.setEmployeeId(employee.getEmployeeId());
		return leaveOpDao.addNewLeave(leaveRecord);
	}
	
	public List<LeaveRecord> getAllLeavesByUsername(String username) {
		Employee employee = empDao.getEmployeeByUserID(username);
		List<LeaveRecord> records = leaveOpDao.getAllLeavesByEmpId(employee.getEmployeeId());
		for(LeaveRecord record:records) {
			record.setNoOfDays(1+(int)record.getStartDate().until(record.getEndDate(),ChronoUnit.DAYS));
		}
		return records;
	}
	
	
}
