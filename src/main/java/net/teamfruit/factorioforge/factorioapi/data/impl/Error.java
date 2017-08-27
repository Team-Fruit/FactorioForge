package net.teamfruit.factorioforge.factorioapi.data.impl;

import net.teamfruit.factorioforge.factorioapi.data.IError;

public class Error implements IError {

	private String detail;

	@Override
	public boolean isError() {
		return this.detail!=null;
	}

	@Override
	public String getErrorMessage() {
		return this.detail;
	}
}
