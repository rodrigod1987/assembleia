package assembleia.exception;

public class AssociadoNaoEncontradoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2486386155138168886L;

	public AssociadoNaoEncontradoException(long cpf) {
		super("Cpf "+ cpf +" não encontrado.");		
	}
	
	
}
