package net.teamfruit.factorioforge.factorioapi;

public class FactorioAPIException extends Exception {

	public FactorioAPIException() {
		super();
	}

	public FactorioAPIException(final String message) {
		super(message);
	}

	public FactorioAPIException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public FactorioAPIException(final Throwable cause) {
		super(cause);
	}

	protected FactorioAPIException(final String message, final Throwable cause,
			final boolean enableSuppression,
			final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
