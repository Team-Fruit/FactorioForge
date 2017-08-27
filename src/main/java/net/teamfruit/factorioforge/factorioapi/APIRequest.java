package net.teamfruit.factorioforge.factorioapi;

import java.io.IOException;

import net.teamfruit.factorioforge.factorioapi.data.IResponse;

public interface APIRequest {

	String URL = "https://mods.factorio.com/api/";

	String getEndPoint();

	String getURL();

	IResponse getAPIResponse() throws IOException;

}
