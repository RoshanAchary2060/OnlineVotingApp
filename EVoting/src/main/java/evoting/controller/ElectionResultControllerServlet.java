package evoting.controller;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import evoting.dao.CandidateDAO;
import evoting.dao.VoteDAO;
import evoting.dto.CandidateDetails;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ElectionResultControllerServlet extends HttpServlet {
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
		try {
			Map<String, Integer> result = VoteDAO.getResult();
			Set<Entry<String, Integer>> s = result.entrySet();
			Iterator<Entry<String, Integer>> it = s.iterator();
			LinkedHashMap<CandidateDetails, Integer> resultDetails = new LinkedHashMap<>();
			while (it.hasNext()) {
				Map.Entry<String, Integer> e = (Map.Entry<String, Integer>) it.next();
				CandidateDetails cd = CandidateDAO.getDetailsById(e.getKey());
				resultDetails.put(cd, e.getValue());
			}
			request.setAttribute("votecount", VoteDAO.getVoteCount());
			request.setAttribute("result", resultDetails);
			rd = request.getRequestDispatcher("electionresult.jsp");
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
