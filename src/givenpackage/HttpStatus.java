package givenpackage;

public enum HttpStatus {
	OK(200), BAD_REQUEST(400), INTERNAL_SERVER_ERROR(500), FORBIDDEN(403), NOT_FOUND(
			404);

	private final int code;

	private HttpStatus(int code2) {
		code = code2;
	}

	public int getCode() {
		return code;
	}

}
