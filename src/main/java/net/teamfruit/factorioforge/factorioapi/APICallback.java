package net.teamfruit.factorioforge.factorioapi;

import net.teamfruit.factorioforge.factorioapi.data.IResponse;

public interface APICallback<T> {

	void onSuccess(T res);

	void onErrorResponse(IResponse res);

	void onError(Throwable t);

}
