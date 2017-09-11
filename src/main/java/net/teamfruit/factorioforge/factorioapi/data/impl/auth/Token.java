package net.teamfruit.factorioforge.factorioapi.data.impl.auth;

import java.lang.reflect.Type;
import java.util.List;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import net.teamfruit.factorioforge.factorioapi.FactorioAPI;
import net.teamfruit.factorioforge.factorioapi.data.auth.IToken;

public class Token implements IToken {

	private String token;
	private String message;
	private int status;

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
		return this.message;
	}

	@Override
	public int getErrorStatusCode() {
		return this.status;
	}

	@Override
	public boolean isError() {
		return this.message!=null;
	}

	public static class ResponseDeserilizer implements JsonDeserializer<Token> {
		private final Gson gson = new Gson();

		@Override
		public Token deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
			if (json.isJsonArray()) {
				final Token res = new Token();
				final List<String> list = FactorioAPI.gson.fromJson(json, new TypeToken<List<String>>() {
				}.getType());
				res.setToken(list.get(0));
				return res;
			} else
				return this.gson.fromJson(json, Token.class);
		}

	}

}
