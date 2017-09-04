package net.teamfruit.factorioforge.factorioapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import net.teamfruit.factorioforge.factorioapi.data.IResponse;
import net.teamfruit.factorioforge.factorioapi.data.impl.Response;

public abstract class AbstractAPIRequest<E extends IResponse> implements APIRequest<E> {

	public static HttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(RequestConfig.custom().setConnectTimeout(10*1000).setSocketTimeout(10*1000).build()).build();

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
	public E getAPIResponse() throws IOException {
		final HttpGet get = new HttpGet(getURL());
		final HttpResponse res = client.execute(get);
		try (BufferedReader br = new BufferedReader(new InputStreamReader(res.getEntity().getContent(), StandardCharsets.UTF_8))) {
			final E apiresponse = parseAPIResponse(br.lines().collect(Collectors.joining()));
			if (apiresponse instanceof Response)
				((Response) apiresponse).setEndPoint(getEndPoint());
			return apiresponse;
		}
	}

	abstract protected E parseAPIResponse(String raw);
}
