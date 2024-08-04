package evoting.dto;

import java.util.Objects;

public class CandidateDetails {
	private String candidateId;
	private String userId;
	private String candidateName;
	private String city;
	private String symbol;
	private String party;

	@Override
	public String toString() {
		return "CandidateDetails [candidateId=" + candidateId + ", userId=" + userId + ", candidateName="
				+ candidateName + ", symbol=" + symbol + ", party=" + party + ", city=" + city + "]";
	}

	public String getCandidateId() {
		return candidateId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(candidateId, candidateName, city, party, symbol, userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CandidateDetails other = (CandidateDetails) obj;
		return Objects.equals(candidateId, other.candidateId) && Objects.equals(candidateName, other.candidateName)
				&& Objects.equals(city, other.city) && Objects.equals(party, other.party)
				&& Objects.equals(symbol, other.symbol) && Objects.equals(userId, other.userId);
	}

	public void setCandidateId(String candidateId) {
		this.candidateId = candidateId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getParty() {
		return party;
	}

	public void setParty(String party) {
		this.party = party;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

}
