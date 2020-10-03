package assembleia.domain.enums;

public enum CpfStatus {

	ABLE_TO_VOTE("ABLE_TO_VOTE"),
	UNABLE_TO_VOTO("UNABLE_TO_VOTO");

	private String description;
	
	CpfStatus(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
}
