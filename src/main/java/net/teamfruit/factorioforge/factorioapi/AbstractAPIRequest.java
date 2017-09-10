package net.teamfruit.factorioforge.factorioapi;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import com.google.gson.stream.JsonReader;

import net.teamfruit.factorioforge.factorioapi.data.impl.modportal.Response;

public abstract class AbstractAPIRequest<E extends IResponse> implements APIRequest<E> {

	public static HttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(RequestConfig.custom().setConnectTimeout(100*1000).setSocketTimeout(100*1000).setConnectionRequestTimeout(100*1000).build()).build();

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
		try (JsonReader jr = new JsonReader(new InputStreamReader(res.getEntity().getContent(), StandardCharsets.UTF_8))) {
			final E apiresponse = parseAPIResponse(jr);
			if (apiresponse instanceof Response)
				((Response) apiresponse).setEndPoint(getEndPoint());
			return apiresponse;
		}
	}

	abstract protected E parseAPIResponse(JsonReader jr) throws IOException;
}
