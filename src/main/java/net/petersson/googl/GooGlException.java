package net.petersson.googl;

public class GooGlException extends Exception {

	private static final long serialVersionUID = 1L;

	public GooGlException(StringBuffer response) {
		super(response == null ? "null" : response.toString());
	}

	public GooGlException(String response) {
		super(response);
	}
}
