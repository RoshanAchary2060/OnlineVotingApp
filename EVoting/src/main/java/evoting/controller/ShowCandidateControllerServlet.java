package evoting.controller;

import java.io.IOException;
import java.util.ArrayList;

import evoting.dao.CandidateDAO;
import evoting.dto.CandidateDetails;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ShowCandidateControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher rd = null;
		HttpSession sess = request.getSession();
		String userid = (String) sess.getAttribute("userid");
		if (userid == null) {
			sess.invalidate();
			response.sendRedirect("accessdenied.html");
			return;
		}
		String data = request.getParameter("data");
		try {
			if (data != null && data.equalsIgnoreCase("cid")) {
				ArrayList<String> candidateList = CandidateDAO.getCandidateId();
				request.setAttribute("candidateid", candidateList);
				request.setAttribute("result", "candidatelist");
			} else {
				CandidateDetails cd = CandidateDAO.getDetailsById(data);
				request.setAttribute("candidate", cd);
				request.setAttribute("result", "details");
			}
			rd = request.getRequestDispatcher("adminshowcandidate.jsp");
		} catch (Exception ex) {
			ex.printStackTrace();
			rd = request.getRequestDispatcher("showexception.jsp");
			request.setAttribute("Exception", ex);
		} finally {
			rd.forward(request, response);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

}
