package assembleia.domain.dto;

import assembleia.domain.enums.VotoEnum;

public class VotoDTO {
	
	private int pautaId;
	private long cpf;
	private VotoEnum resposta;
	
	public int getPautaId() {
		return pautaId;
	}
	
	public long getCpf() {
		return cpf;
	}
	
	public VotoEnum getResposta() {
		return resposta;
	}

}
