package net.teamfruit.factorioforge.factorioapi.data.impl.auth;

import java.lang.reflect.Type;
import java.util.List;

import com.google.common.reflect.TypeToken;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import net.teamfruit.factorioforge.factorioapi.FactorioAPI;
import net.teamfruit.factorioforge.factorioapi.data.auth.IToken;

public class Token implements IToken {

	private String token;
	private String errorMessage;
	private int statusCode;

	@Override
	public String getEndPoint() {
		return "api-login";
	}

	public void setToken(final String token) {
		this.token = token;
	}

	@Override
	public String getToken() {
		return this.token;
	}

	@Override
	public String getErrorMessage() {
		return this.errorMessage;
	}

	@Override
	public int getErrorStatusCode() {
		return this.statusCode;
	}

	@Override
	public boolean isError() {
		return this.errorMessage!=null;
	}

	public static class ResponseDeserilizer implements JsonDeserializer<Token> {

		@Override
		public Token deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
			final JsonObject obj = json.getAsJsonObject();
			if (obj.isJsonArray()) {
				final Token res = new Token();
				final List<String> list = FactorioAPI.gson.fromJson(json, new TypeToken<List<String>>() {
				}.getType());
				res.setToken(list.get(0));
				return res;
			} else
				return FactorioAPI.gson.fromJson(json, Token.class);
		}

	}

}
