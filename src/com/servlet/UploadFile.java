package com.servlet;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.entities.Files;
import com.util.HibernateUtils;

/**
 * Servlet implementation class UploadFile
 */
//@WebServlet("/UploadFile")

/* about==> @MultipartConfig() ==>annotation
 * If the size of the file being uploaded exceeds the configured maximum, an IllegalStateException exception will be thrown.
 * This happens when you attempt to get the Parts of the request by calling the
 * =======HttpServletRequest.getParts() or HttpServletRequest.getPart(). ====
 * So the easiest way is to just simply put this into a try-catch block .
 */


@MultipartConfig(maxFileSize=12000)
public class UploadFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadFile() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		//session.save(myObject);
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Part filePart=null;
		try{
			filePart=request.getPart("fileName");
		}
		catch(IllegalStateException e)
		{
			request.setAttribute("message", "Please choose file less than 12 KB  !!!!!!!");
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/upload.jsp");
			dispatcher.forward(request, response);
			
		}
		InputStream inputStream =null; 
		String fileName = null;
		String mimeType=null;
		if(filePart != null)
		{
			fileName = getFileName(filePart);
			inputStream=filePart.getInputStream();
			mimeType=filePart.getContentType();
			Files file=new Files();
			file.setFile_name(fileName);
			file.setMimeType(mimeType);
			
		/*	public static byte[] readFully(InputStream input) throws IOException
			{
			    byte[] buffer = new byte[8192];
			    int bytesRead;
			    ByteArrayOutputStream output = new ByteArrayOutputStream();
			    while ((bytesRead = input.read(buffer)) != -1)
			    {
			        output.write(buffer, 0, bytesRead);
			    }
			    return output.toByteArray();
			}*/		// above method is equivalent to 	IOUtils.toByteArray(inputStream);
			
			
			//Internally IOUtils creates a ByteArrayOutputStream and copies the bytes to the output, then calls toByteArray().
			//It handles large files by copying the bytes in blocks of 4KiB.
			
			byte[] bytes = IOUtils.toByteArray(inputStream);
			file.setContent(bytes);
			
			System.out.println(file.getFile_name());
			System.out.println(file.getContent());
		
			SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
			Session session = sessionFactory.openSession();
			 Transaction tr=session.beginTransaction();
		//session.beginTransaction();
			session.save(file);
			tr.commit();
		session.close();
		//HibernateUtils.closeSession();
			
		}
		
		
		
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/download.jsp");
		dispatcher.forward(request, response);
	}

	
	
	
	
	 private String getFileName(Part part) {
	        String contentDisp = part.getHeader("content-disposition");
	        System.out.println("content-disposition header= "+contentDisp);
	        String[] tokens = contentDisp.split(";");
	        for (String token : tokens) {
	            if (token.trim().startsWith("filename")) {
	                return token.substring(token.indexOf("=") + 2, token.length()-1);
	            }
	        }
	        return "";
	    }
}
