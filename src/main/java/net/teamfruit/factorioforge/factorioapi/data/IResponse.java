package net.teamfruit.factorioforge.factorioapi.data;

public interface IResponse {

	String getEndPoint();

	boolean isError();

	String getErrorMessage();
}
