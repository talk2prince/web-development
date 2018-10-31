package com.nc.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.nc.model.Employee;
import com.nc.model.LeaveRecord;
import com.nc.service.EmployeeService;
import com.nc.service.LeaveOperationService;


@WebServlet("*.html")
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private EmployeeService empService;
	private LeaveOperationService leaveService;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		empService = new EmployeeService();
		leaveService = new LeaveOperationService();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		
		String path = request.getRequestURL().toString();
		String query = request.getQueryString();
		String target = "/WEB-INF/home.jsp";
		
		
		if(path.endsWith("display.html")) {
			target = "/WEB-INF/display.jsp";
		}else if(path.endsWith("register.html")) {
			target = "/WEB-INF/register.jsp";
		}else if(path.endsWith("empsave.html")) {

			Employee newEmp = new Employee();
			newEmp.setName(request.getParameter("name"));
			newEmp.setUsername(request.getParameter("uname"));
			newEmp.setPassword(request.getParameter("password"));
			newEmp.setSalary(Long.parseLong(request.getParameter("salary")));
			newEmp.setGender(request.getParameter("gender"));
			
			String result = empService.addNewEmployee(newEmp);
			
			request.setAttribute("result", result);
			target = "/WEB-INF/register.jsp";
		} else if(path.endsWith("logout.html")) {
			HttpSession session = request.getSession();
			session.invalidate();
			target ="/WEB-INF/home.jsp";
				
		} else if(path.endsWith("newleave.html")) {
			String uname = request.getParameter("uname");
			int catid = Integer.parseInt(request.getParameter("leaveType"));
			LocalDate startDate = LocalDate.parse(request.getParameter("startDate"));
			LocalDate endDate = LocalDate.parse(request.getParameter("endDate"));
			
			LeaveRecord leaveRecord = new LeaveRecord();
			leaveRecord.setCategoryId(catid);
			leaveRecord.setStartDate(startDate);
			leaveRecord.setEndDate(endDate);
			
			if(leaveService.addNewLeave(leaveRecord,uname)) {
				request.setAttribute("msg", "Leave created successfully!");
				request.setAttribute("records", leaveService.getAllLeavesByUsername(uname));
			} else {
				request.setAttribute("msg", "Problem in leave creation! Try again!");
			}
			request.setAttribute("ltypes", leaveService.getAllLeaveTypes(uname));
			target = "/WEB-INF/display.jsp";
		} else if(path.endsWith("validate.html")) {
			Employee emp = new Employee();
			emp.setUsername(request.getParameter("uname"));
			emp.setPassword(request.getParameter("upass"));
			if(empService.checkEmployee(emp)) {
				HttpSession session = request.getSession(true);
				session.setAttribute("user", emp.getUsername());
				request.setAttribute("ltypes", leaveService.getAllLeaveTypes(emp.getUsername()));
				request.setAttribute("records", leaveService.getAllLeavesByUsername(emp.getUsername()));
				target = "/WEB-INF/display.jsp";
			}else {
				String result = "Login failed. Please try again!";
				request.setAttribute("result", result);
			}
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(target);
		rd.forward(request, response);
		
		
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
