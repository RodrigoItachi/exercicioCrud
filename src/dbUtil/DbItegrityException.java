package dbUtil;

public class DbItegrityException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DbItegrityException(String msg) {
		super(msg);
	}
}