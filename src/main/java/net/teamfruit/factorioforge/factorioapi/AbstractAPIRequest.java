package net.teamfruit.factorioforge.factorioapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import net.teamfruit.factorioforge.factorioapi.data.IResponse;
import net.teamfruit.factorioforge.factorioapi.data.impl.Response;

public abstract class AbstractAPIRequest implements APIRequest {

	protected final String endPoint;

	public AbstractAPIRequest(final String endPoint) {
		this.endPoint = endPoint;
	}

	@Override
	public String getEndPoint() {
		return this.endPoint;
	}

	@Override
	public String getURL() {
		return URL+getEndPoint();
	}

	@Override
	public IResponse getAPIResponse() throws IOException {
		final URL url = new URL(getURL());
		final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setConnectTimeout(1000*50);
		try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
			final IResponse res = parseAPIResponse(br.lines().collect(Collectors.joining()));
			if (res instanceof Response)
				((Response) res).setEndPoint(getEndPoint());
			return res;
		}
	}

	abstract protected IResponse parseAPIResponse(String raw);
}
