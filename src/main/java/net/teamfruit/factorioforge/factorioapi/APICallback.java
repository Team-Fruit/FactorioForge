package net.teamfruit.factorioforge.factorioapi;

public interface APICallback<E extends IResponse> {

	void onSuccess(E res);

	void onErrorResponse(IResponse res);

	void onError(Throwable t);

}
