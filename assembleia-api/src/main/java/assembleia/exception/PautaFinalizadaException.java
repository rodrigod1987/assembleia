package assembleia.exception;

public class PautaFinalizadaException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8570124315027968259L;

	public PautaFinalizadaException() {
		super("Pauta finalizada, voto não pode ser realizado!");
	}
	
}
