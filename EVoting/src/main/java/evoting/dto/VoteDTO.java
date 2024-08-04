package evoting.dto;

public class VoteDTO {
	private String candidateId;
	private String voterId;

	public String getCandidateId() {
		return candidateId;
	}

	public VoteDTO() {
		
	}

	public VoteDTO(String candidateId, String voterId) {
		super();
		this.candidateId = candidateId;
		this.voterId = voterId;
	}

	public void setCandidateId(String candidateId) {
		this.candidateId = candidateId;
	}

	public String getVoterId() {
		return voterId;
	}

	public void setVoterId(String voterId) {
		this.voterId = voterId;
	}

	@Override
	public String toString() {
		return "VoteDTO [candidateId=" + candidateId + ", voterId=" + voterId + "]";
	}
}
