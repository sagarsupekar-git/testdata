package in.sp.backend;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/regForm")
public class Register extends HttpServlet
{
  @Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
{
	  PrintWriter out=resp.getWriter();
	  
	String name1=req.getParameter("FirstName");
	String name2=req.getParameter("LastName");
	String phone=req.getParameter("PhoneNumber");
	String email=req.getParameter("email1");
	String address=req.getParameter("Address");
	
	try
	{
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","Pass@123");
		
		PreparedStatement ps=con.prepareStatement("insert into data values(?,?,?,?,?)");
		ps.setString(1, name1);
		ps.setString(2, name2);
		ps.setString(3, phone);
		ps.setString(4, email);
		ps.setString(5, address);
		
		int count=ps.executeUpdate();
		if(count>0)
		{
			resp.setContentType("text/html");
			out.print("<h3 style='color:green'>User registered successfully</h3>");
			
			RequestDispatcher rd=req.getRequestDispatcher("/submit.jsp");
			rd.include(req, resp);
		}
		else
		{
			resp.setContentType("text/html");
			out.print("<h3 style='color:red'>User not registered due to some error</h3>");
			
			RequestDispatcher rd=req.getRequestDispatcher("/submit.jsp");
			rd.include(req, resp);
		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
		resp.setContentType("text/html");
		out.print("<h3 style='color:red'>Exception Occured: "+e.getMessage() +"</h3>");
		
		RequestDispatcher rd=req.getRequestDispatcher("/register.jsp");
		rd.include(req, resp);
	}
}
}

