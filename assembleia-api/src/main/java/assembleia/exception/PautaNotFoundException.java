package assembleia.exception;

public class PautaNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3351531097150331807L;
	
	public PautaNotFoundException(int id) {
		super("Pauta "+ id +" não encontrada!");
	}

}
