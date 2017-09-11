package net.teamfruit.factorioforge.mod;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;

import javafx.concurrent.Task;
import net.teamfruit.factorioforge.Log;
import net.teamfruit.factorioforge.factorioapi.AbstractAPIRequest;

public class ModDownloader extends Task<Void> {

	private static String username;
	private static String token;

	protected final String url;
	protected final File file;

	public ModDownloader(final String url, final File file) {
		this.url = url;
		this.file = file;
	}

	@Override
	protected Void call() throws Exception {
		final HttpResponse res = AbstractAPIRequest.client.execute(new HttpGet(this.url+"?username="+username+"&token="+token));
		if (res.getStatusLine().getStatusCode()!=HttpStatus.SC_OK) {
			fail(res.getStatusLine().getStatusCode()+" "+res.getStatusLine().getReasonPhrase());
			return null;
		}
		final long length = Long.parseLong(res.getFirstHeader("Content-Length").getValue());
		final HttpEntity entity = res.getEntity();
		if (entity!=null) {
			final InputStream is = entity.getContent();
			try (FileOutputStream fos = new FileOutputStream(this.file)) {
				int i = 0;
				final byte[] buf = new byte[8192];
				int len;
				while ((len = is.read(buf))!=-1) {
					Log.log.info(i);
					fos.write(buf, 0, len);
					i++;
					updateProgress(i*8192, length);
				}
			}
		} else
			fail();
		return null;
	}

	public static void setUsername(final String username) {
		ModDownloader.username = username;
	}

	public static void setToken(final String token) {
		ModDownloader.token = token;
	}

	public static boolean isUserDataProvided() {
		return ModDownloader.username!=null&&ModDownloader.token!=null;
	}

}
