package net.teamfruit.factorioforge.factorioapi;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;

import com.google.gson.stream.JsonReader;

import net.teamfruit.factorioforge.factorioapi.data.auth.IToken;
import net.teamfruit.factorioforge.factorioapi.data.impl.auth.Token;
import net.teamfruit.factorioforge.factorioapi.data.impl.modportal.Response;

public class WebAuthAPI extends AbstractAPIRequest<IToken> {

	protected String username;
	protected String password;

	public WebAuthAPI(final String username, final String password) {
		super("api-login");
		this.username = username;
		this.password = password;
	}

	@Override
	public String getURL() {
		return "https://auth.factorio.com/api-login?username="+this.username+"&password="+this.password;
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
