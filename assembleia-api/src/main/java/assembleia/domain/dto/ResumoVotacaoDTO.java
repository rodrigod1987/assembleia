package assembleia.domain.dto;

public class ResumoVotacaoDTO {

	private long sim;
	private long nao;
	
	public ResumoVotacaoDTO(long sim, long nao) {
		this.sim = sim;
		this.nao = nao;
	}

	public long getSim() {
		return sim;
	}

	public long getNao() {
		return nao;
	}

}
