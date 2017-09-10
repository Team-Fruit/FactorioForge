package net.teamfruit.factorioforge.factorioapi;

public interface IResponse {

	String getEndPoint();

	boolean isError();

	String getErrorMessage();
}
