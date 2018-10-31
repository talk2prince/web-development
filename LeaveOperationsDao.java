package com.nc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.nc.model.LeaveRecord;
import com.nc.model.LeaveType;

public class LeaveOperationsDao {

	public List<LeaveType> getAllLeaveTypes() {
		String sql = "select * from leaves_master";
		List<LeaveType> leaveTypes = null;
		try(Connection conn = ConnectionUtil.getConnection();
			 Statement st = conn.createStatement()) {
			ResultSet rs = st.executeQuery(sql);
			leaveTypes = new ArrayList<>();
			while(rs.next()) {
				LeaveType lt = new  LeaveType();
				lt.setLeaveId(rs.getInt("catid"));
				lt.setTypeName(rs.getString("cat_type"));
				lt.setMaximum(rs.getInt("max_allowed"));
				
				leaveTypes.add(lt);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return leaveTypes;
	}
	
	public boolean addNewLeave(LeaveRecord leaveRecord) {
		boolean result = false;
		String sql = "insert into leave_records values(?,?,?,?,?)";
		
		try(Connection conn = ConnectionUtil.getConnection();
				PreparedStatement pst = conn.prepareStatement(sql)) {
			pst.setInt(1, leaveRecord.getEmployeeId());
			pst.setInt(2, leaveRecord.getCategoryId());
			pst.setDate(3, Date.valueOf(leaveRecord.getStartDate()));
			pst.setDate(4, Date.valueOf(leaveRecord.getEndDate()));
			pst.setDate(5, Date.valueOf(LocalDate.now()));
			
			int count = pst.executeUpdate();
			if(count>0) {
				result = true;
			}
		} catch (SQLException e) {
			System.out.println("Problem in creating new leave:" + e.getMessage());
		}
		return result;
	}
	
	public List<LeaveRecord> getAllLeavesByEmpId(int empId) {
		String sql = "select * from leave_records where empid = ?";
		List<LeaveRecord> records = new ArrayList<>();
		try(Connection conne = ConnectionUtil.getConnection();
			PreparedStatement pst = conne.prepareStatement(sql)) {
			pst.setInt(1, empId);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				LeaveRecord record = new LeaveRecord();
				record.setEmployeeId(rs.getInt("empid"));
				record.setCategoryId(rs.getInt("catid"));
				record.setStartDate(rs.getDate("start_date").toLocalDate());
				record.setEndDate(rs.getDate("end_date").toLocalDate());
				records.add(record);
			}
		} catch (SQLException e) {
			System.out.println("Problem in fetching leave records!");
		}
		return records;
	}
	
	
}
