package net.teamfruit.factorioforge.factorioapi;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.net.URLCodec;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;

import com.google.gson.stream.JsonReader;

import net.teamfruit.factorioforge.Log;
import net.teamfruit.factorioforge.factorioapi.data.auth.IToken;
import net.teamfruit.factorioforge.factorioapi.data.impl.auth.Token;
import net.teamfruit.factorioforge.factorioapi.data.impl.modportal.Response;

public class WebAuthAPI extends AbstractAPIRequest<IToken> {
	private static final URLCodec CODEC = new URLCodec("UTF-8");

	protected String username;
	protected String password;

	public WebAuthAPI(final String username, final String password) {
		super("api-login");
		this.username = username;
		this.password = password;
	}

	@Override
	public String getURL() {
		try {
			return "https://auth.factorio.com/api-login?username="+CODEC.encode(this.username)+"&password="+CODEC.encode(this.password);
		} catch (final EncoderException e) {
			Log.log.error(e);
			return "https://auth.factorio.com/api-login?username="+this.username+"&password="+this.password;
		}
	}

	@Override
	public IToken getAPIResponse() throws IOException {
		final HttpPost post = new HttpPost(getURL());
		final HttpResponse res = client.execute(post);
		try (JsonReader jr = new JsonReader(new InputStreamReader(res.getEntity().getContent(), StandardCharsets.UTF_8))) {
			final IToken apiresponse = parseAPIResponse(jr);
			if (apiresponse instanceof Response)
				((Response) apiresponse).setEndPoint(getEndPoint());
			return apiresponse;
		}
	}

	@Override
	protected IToken parseAPIResponse(final JsonReader jr) throws IOException {
		return FactorioAPI.gson.fromJson(jr, Token.class);
	}

}
