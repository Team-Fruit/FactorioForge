package net.teamfruit.factorioforge.factorioapi;

import java.io.IOException;
import java.util.concurrent.Callable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.teamfruit.factorioforge.factorioapi.data.IModList;
import net.teamfruit.factorioforge.factorioapi.data.IResponse;
import net.teamfruit.factorioforge.factorioapi.data.IResult;

public class FactorioAPI {

	public static Gson gson = new GsonBuilder().create();

	public static ModListAPI newModListAPI() {
		return new ModListAPI();
	}

	public static ModListAPI newModListAPI(final int page) {
		return new ModListAPI(page);
	}

	public static ModSearchAPI newModSearchAPI(final String modName) {
		return new ModSearchAPI(modName);
	}

	public static IModList getModList() throws IOException {
		return newModListAPI().getAPIResponse();
	}

	public static IModList getModList(final int page) throws IOException {
		return newModListAPI(page).getAPIResponse();
	}

	public static IResult getMod(final String modName) throws IOException {
		return newModSearchAPI(modName).getAPIResponse();
	}

	public static Callable<IModList> getModListWithCallable() {
		return () -> getModList();
	}

	public static Callable<IModList> getModListWithCallable(final int page) {
		return () -> getModList(page);
	}

	public static Callable<IResult> getModWithCallable(final String modName) {
		return () -> getMod(modName);
	}

	public static void getModListWithNewThread(final APICallback<IModList> callback) {
		new Thread(runnable(newModListAPI(), callback)).start();
	}

	public static void getModListWithNewThread(final int page, final APICallback<IModList> callback) {
		new Thread(runnable(newModListAPI(page), callback)).start();
	}

	public static void getModWithNewThread(final String modName, final APICallback<IResult> callback) {
		new Thread(runnable(newModSearchAPI(modName), callback)).start();
	}

	private static Runnable runnable(final APIRequest req, final APICallback callback) {
		return () -> {
			try {
				final IResponse res = req.getAPIResponse();
				if (res.isError())
					callback.onErrorResponse(res);
				else
					callback.onSuccess(res);
			} catch (final Throwable t) {
				callback.onError(t);
			}
		};
	}
}
