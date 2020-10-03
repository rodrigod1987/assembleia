package assembleia.exception;

public class CpfInvalidoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6460098474361220404L;

	public CpfInvalidoException(long cpf) {
		super("Cpf "+ cpf + " é inválido.");
	}
	
}
