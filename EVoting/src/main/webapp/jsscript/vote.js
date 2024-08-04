function addvote() {
	// pick up the selected candidate id and call the addvotecontrollerservlet
	var id = $('input[type=radio][name=flat]:checked').attr('id');
	data = { candidateid: id };
	$.post("AddVoteControllerServlet", data, processresponse);
}
function processresponse(responseText) {
	// check the response, show the popup, redirect to votingresponse
	responseText = responseText.trim();
	if (responseText === "success") {
		swal("Success", "Voting done", "success").then((value) => {
			window.location = "votingresponse.jsp";
		});
	} else {
		swal("Failure", "Voting failed", "error").then((value) => {
			window.location = "votingresponse.jsp";
		});
	}
}