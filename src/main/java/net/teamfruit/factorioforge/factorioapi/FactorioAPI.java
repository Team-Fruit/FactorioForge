package net.teamfruit.factorioforge.factorioapi;

import java.io.IOException;
import java.util.concurrent.Callable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.teamfruit.factorioforge.factorioapi.data.impl.modportal.Info;
import net.teamfruit.factorioforge.factorioapi.data.modportal.IModList;
import net.teamfruit.factorioforge.factorioapi.data.modportal.IResponse;
import net.teamfruit.factorioforge.factorioapi.data.modportal.IResult;

public class FactorioAPI {

	public static Gson gson = new GsonBuilder().registerTypeAdapter(Info.class, new Info.DependenciesDeserilizer()).create();

	public static ModListAPI newModListAPI() {
		return new ModListAPI();
	}

	public static ModListAPI newModListAPI(final int page, final int pageSize) {
		return new ModListAPI(page, pageSize);
	}

	public static ModSearchAPI newModSearchAPI(final String modName) {
		return new ModSearchAPI(modName);
	}

	public static IModList getModList() throws IOException {
		return newModListAPI().getAPIResponse();
	}

	public static IModList getModList(final int page, final int pageSize) throws IOException {
		return newModListAPI(page, pageSize).getAPIResponse();
	}

	public static IResult getMod(final String modName) throws IOException {
		return newModSearchAPI(modName).getAPIResponse();
	}

	public static Callable<IModList> getModListWithCallable() {
		return () -> getModList();
	}

	public static Callable<IModList> getModListWithCallable(final int page, final int pageSize) {
		return () -> getModList(page, pageSize);
	}

	public static Callable<IResult> getModWithCallable(final String modName) {
		return () -> getMod(modName);
	}

	public static void getModListWithNewThread(final APICallback<IModList> callback) {
		new Thread(runnable(newModListAPI(), callback)).start();
	}

	public static void getModListWithNewThread(final int page, final int pageSize, final APICallback<IModList> callback) {
		new Thread(runnable(newModListAPI(page, pageSize), callback)).start();
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
