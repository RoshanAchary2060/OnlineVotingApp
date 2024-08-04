package evoting.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

import evoting.dao.CandidateDAO;
import evoting.dto.CandidateDTO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AddNewCandidateControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = null;
		try {
			DiskFileItemFactory df = new DiskFileItemFactory();
			ServletFileUpload sfu = new ServletFileUpload(df);
			ServletRequestContext src = new ServletRequestContext(request);
			List<FileItem> multiList = sfu.parseRequest(src);
			ArrayList<String> objValues = new ArrayList<>();
			InputStream inp = null;
			for (FileItem fit : multiList) {
				if (fit.isFormField()) {
					String fname = fit.getFieldName();
					String value = fit.getString();
					System.out.println(fname + ":" + value);
					objValues.add(value);
				} else {
					inp = fit.getInputStream();
					String key = fit.getFieldName();
					String fileName = fit.getName();
					System.out.println("Inside else");
					System.out.println(key + ":" + fileName);
				}
			}
			CandidateDTO candidate = new CandidateDTO(objValues.get(0), objValues.get(3), objValues.get(4),
					objValues.get(1), inp);
			boolean result = CandidateDAO.addCandidate(candidate);
			if(result) {
				rd = request.getRequestDispatcher("success.jsp");
			} else {
				rd = request.getRequestDispatcher("failure.jsp");
			}

		} catch (Exception ex) {
			System.out.println("Exception in AddNewCandidateController");
			ex.printStackTrace();
		} finally {
			if(rd!=null) {
				rd.forward(request, response);
			}
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

}
