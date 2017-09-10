package net.teamfruit.factorioforge.factorioapi.data.modportal;

public interface IResponse {

	String getEndPoint();

	boolean isError();

	String getErrorMessage();
}
