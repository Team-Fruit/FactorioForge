package net.teamfruit.factorioforge.factorioapi;

import net.teamfruit.factorioforge.factorioapi.data.IResponse;

public interface APICallback<E extends IResponse> {

	void onSuccess(E res);

	void onErrorResponse(IResponse res);

	void onError(Throwable t);

}
