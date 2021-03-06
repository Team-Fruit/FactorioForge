package net.teamfruit.factorioforge.factorioapi;

import java.io.IOException;

public interface APIRequest<E extends IResponse> {

	String URL = "https://mods.factorio.com/api/";

	String getEndPoint();

	String getURL();

	E getAPIResponse() throws IOException;

}
