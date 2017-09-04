package net.teamfruit.factorioforge.factorioapi;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import net.teamfruit.factorioforge.factorioapi.data.IResponse;
import net.teamfruit.factorioforge.factorioapi.data.impl.Response;

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
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new BufferedInputStream(res.getEntity().getContent(), 640000), StandardCharsets.UTF_8), 640000)) {
			//TODO
			//			Log.log.info(new Date());
			final StringBuilder builder = new StringBuilder();
			String line;
			while ((line = br.readLine())!=null)
				builder.append(line);
			final E apiresponse = parseAPIResponse(builder.toString());
			//			final E apiresponse = parseAPIResponse(br.lines().collect(Collectors.joining()));
			//			Log.log.info(new Date());
			if (apiresponse instanceof Response)
				((Response) apiresponse).setEndPoint(getEndPoint());
			return apiresponse;
		}
	}

	abstract protected E parseAPIResponse(String raw);
}
