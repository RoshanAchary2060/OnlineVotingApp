package evoting.controller;

import java.io.IOException;
import java.util.ArrayList;

import evoting.dao.CandidateDAO;
import evoting.dao.VoteDAO;
import evoting.dto.CandidateInfo;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class VotingControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = null;
		HttpSession session = request.getSession();
		String userid = (String) session.getAttribute("userid");
		if (userid == null) {
			session.invalidate();
			response.sendRedirect("accessdenied.html");
			return;
		}
		try {
			String cid = VoteDAO.getCandidateId(userid);
			if (cid == null) {
				ArrayList<CandidateInfo> candidateList = CandidateDAO.viewCandidate(userid);
				request.setAttribute("candidateList", candidateList);
				rd = request.getRequestDispatcher("showcandidate.jsp");
			} else {
				CandidateInfo candidate = VoteDAO.getVote(cid);
				request.setAttribute("candidate", candidate);
				rd = request.getRequestDispatcher("votedenied.jsp");
			}
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
