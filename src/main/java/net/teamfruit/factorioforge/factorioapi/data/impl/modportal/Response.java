package net.teamfruit.factorioforge.factorioapi.data.impl.modportal;

import net.teamfruit.factorioforge.factorioapi.IResponse;

public class Response implements IResponse {

	private String endPoint;
	private String detail;

	public Response setEndPoint(final String endPoint) {
		this.endPoint = endPoint;
		return this;
	}

	@Override
	public String getEndPoint() {
		return this.endPoint;
	}

	@Override
	public boolean isError() {
		return this.detail!=null;
	}

	@Override
	public String getErrorMessage() {
		return this.detail;
	}
}
