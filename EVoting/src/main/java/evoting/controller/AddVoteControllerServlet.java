package evoting.controller;

import java.io.IOException;

import evoting.dao.VoteDAO;
import evoting.dto.CandidateInfo;
import evoting.dto.VoteDTO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AddVoteControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = null;
		HttpSession session = request.getSession();
		String userid = (String) request.getAttribute("userid");
		if (userid == null) {
			session.invalidate();
			response.sendRedirect("accessdenied.html");
			return;
		}
		try {
			String candidateId = (String) request.getParameter("candidateid");
			VoteDTO vote = new VoteDTO();
			boolean result = VoteDAO.addVote(vote);
			CandidateInfo candidate = VoteDAO.getVote(candidateId);
			if (result) {
				session.setAttribute("candidate", candidate);
			}
			request.setAttribute("result", result);
			rd = request.getRequestDispatcher("verifyvote.jsp");
		} catch (Exception ex) {
			ex.printStackTrace();
			request.setAttribute("Exception", ex);
			rd = request.getRequestDispatcher("showexception.jsp");
		} finally {
			rd.forward(request, response);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.processRequest(request, response);
	}

}
