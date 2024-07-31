package exceptions;

public class NumberLessThanZeroException extends RuntimeException {
	// Se estendo RuntimeException sto creando un'eccezione di tipo UNCHECKED
	public NumberLessThanZeroException(int num) {
		super("Il numero inserito: " + num + " è inferiore a zero!");
	}


}
