package exceptions;

public class StringNotValidException extends Exception {
	// Se estendo RuntimeException sto creando un'eccezione di tipo CHECKED
	public StringNotValidException(String str) {
		super("La stringa inserita: " + str + " non è valida");
	}
}
