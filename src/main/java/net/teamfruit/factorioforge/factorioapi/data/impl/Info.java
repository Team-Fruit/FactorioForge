package net.teamfruit.factorioforge.factorioapi.data.impl;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import net.teamfruit.factorioforge.Log;
import net.teamfruit.factorioforge.factorioapi.FactorioAPI;
import net.teamfruit.factorioforge.factorioapi.data.IInfo;

public class Info implements IInfo {

	private String authorS;
	private String contact;
	private List<String> dependenciesS;
	private String description;
	private String factorio_version;
	private String homepage;
	private String name;
	private String title;
	private String version;

	@Override
	public String getAuthor() {
		return this.authorS;
	}

	@Override
	public String getContact() {
		return this.contact;
	}

	@Override
	public List<String> getDependencies() {
		return this.dependenciesS;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public String getFactorioVersion() {
		return this.factorio_version;
	}

	@Override
	public String getHomePage() {
		return this.homepage;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getTitle() {
		return this.title;
	}

	@Override
	public String getVersion() {
		return this.version;
	}

	public void setDependencies(List<String> dependencies) {
		this.dependenciesS = dependencies;
	}

	public static class DependenciesDeserilizer implements JsonDeserializer<Info> {
		private Gson gson = new Gson();

		@Override
		public Info deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			Info info = this.gson.fromJson(json, Info.class);
			JsonObject jsonObject = json.getAsJsonObject();
			if (jsonObject.has("dependencies")) {
				JsonElement elem = jsonObject.get("dependencies");
				if (elem!=null&&!elem.isJsonNull()) {
					String valuesString = null;
					if (elem.isJsonArray())
						if (elem.getAsJsonArray().size()==1)
							valuesString = elem.getAsJsonArray().getAsString();
					if (valuesString!=null)
						valuesString = elem.getAsString();
					Log.log.info(valuesString);
					if (StringUtils.startsWith(valuesString, "[")) {
						List<String> values = FactorioAPI.gson.fromJson(valuesString, new TypeToken<ArrayList<String>>() {
						}.getType());
						info.setDependencies(values);
					} else {
						info.setDependencies(new ArrayList<>());
						info.getDependencies().add(valuesString);
					}
				}
			}
			return info;
		}
	}
}
