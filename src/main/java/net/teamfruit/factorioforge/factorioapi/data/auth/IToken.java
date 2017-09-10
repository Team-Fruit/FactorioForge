package net.teamfruit.factorioforge.factorioapi.data.auth;

import net.teamfruit.factorioforge.factorioapi.IResponse;

public interface IToken extends IResponse {

	int getErrorStatusCode();

	String getToken();
}
