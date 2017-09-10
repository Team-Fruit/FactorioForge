package net.teamfruit.factorioforge.factorioapi;

public class APICallbackAdapter<E extends IResponse> implements APICallback<E> {

	@Override
	public void onSuccess(final E res) {
	}

	@Override
	public void onErrorResponse(final IResponse res) {
		onError(new FactorioAPIException(res.getErrorMessage()));
	}

	@Override
	public void onError(final Throwable t) {
	}

}
