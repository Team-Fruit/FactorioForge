package net.teamfruit.factorioforge.mod;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Task;
import net.teamfruit.factorioforge.factorioapi.AbstractAPIRequest;

public class ModDownloader extends Task<Void> {

	public static final BooleanProperty PROVIDED = new SimpleBooleanProperty();
	private static UserData user;

	protected final String url;
	protected final File file;
	protected final File old;

	public ModDownloader(final String url, final File file, final File old) {
		this.url = url;
		this.file = file;
		this.old = old;
	}

	@Override
	protected Void call() throws Exception {
		final File parent = this.file.getParentFile();
		if (!parent.exists())
			parent.mkdirs();
		final HttpResponse res = AbstractAPIRequest.client.execute(new HttpGet(this.url+"?username="+user.getUsername()+"&token="+user.getToken()));
		if (res.getStatusLine().getStatusCode()!=HttpStatus.SC_OK) {
			cancel();
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
					if (isCancelled()) {
						this.file.delete();
						break;
					}
					fos.write(buf, 0, len);
					i++;
					updateProgress(i*8192, length);
				}
				if (this.old!=null&&this.old.exists())
					this.old.delete();
			}
		} else
			cancel();
		return null;
	}

	public static void setUser(final UserData user) {
		ModDownloader.user = user;
		PROVIDED.set(ModDownloader.user!=null&&ModDownloader.user.isValid());
	}

	public static UserData getUser() {
		return user;
	}

	public static boolean isUserDataProvided() {
		return PROVIDED.get();
	}

}
