package assembleia.domain.dto;

public class PautaDTO {
	
	private int id;
	private int seconds;
	
	public PautaDTO() {}
	
	public PautaDTO(int id, Integer seconds) {
		this.id = id;
		this.seconds = seconds;
	}

	public int getId() {
		return id;
	}
	
	public int getSeconds() {
		return seconds;
	}

}
