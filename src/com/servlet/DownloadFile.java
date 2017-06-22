package com.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.entities.Files;
import com.util.HibernateUtils;

/**
 * Servlet implementation class DownloadFile
 */
//@WebServlet("/download")
public class DownloadFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadFile() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int id=0;
		String s=request.getParameter("id");
	try{
		id= Integer.parseInt(s);
				
	}catch( Exception e)
	{
		id=-1;
		
	}
		if(id<0)
		{
			  // if id is in the form of character rather than number
			
			request.setAttribute("message1", "Please Enter Valid Id!!!!!!!");
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/download.jsp");
			dispatcher.forward(request, response);
			
		
			
		}
		
		
		SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
		Session hsession = sessionFactory.openSession();
		Transaction tr= hsession.beginTransaction();
			
		
		Files files= (Files) hsession.get(Files.class,id);
		
		tr.commit();
		hsession.close();
		 
		if(files == null)
		{
		// if id is not exist in database then hibernatesession.get() method return null
			
			request.setAttribute("message2", "There is file of this id please choose another !!!!!!!");
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/download.jsp");
			dispatcher.forward(request, response);
			
		}
		 byte [] filecontent=files.getContent();
         // set content properties and header attributes for the response
         response.setContentType(files.getMimeType());
         response.setContentLength(filecontent.length);
         String headerKey = "Content-Disposition";
         String headerValue = String.format("attachment; filename=\"%s\"", files.getFile_name());
         response.setHeader(headerKey, headerValue);

         // writes the file to the client
         OutputStream outStream = response.getOutputStream();
          
         
         outStream.write(filecontent);
           outStream.close();           
		
		
		
		
		
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
