package net.teamfruit.factorioforge.factorioapi.data.impl.modportal;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class Info {

	private List<String> authorS;
	private String contact;
	private List<String> dependenciesS;
	private String description;
	private String factorio_version;
	private String homepage;
	private String name;
	private String title;
	private String version;

	public List<String> getAuthor() {
		return this.authorS;
	}

	public String getContact() {
		return this.contact;
	}

	public List<String> getDependencies() {
		return this.dependenciesS;
	}

	public String getDescription() {
		return this.description;
	}

	public String getFactorioVersion() {
		return this.factorio_version;
	}

	public String getHomePage() {
		return this.homepage;
	}

	public String getName() {
		return this.name;
	}

	public String getTitle() {
		return this.title;
	}

	public String getVersion() {
		return this.version;
	}

	public void setAuthor(final List<String> authorS) {
		this.authorS = authorS;
	}

	public void setDependencies(final List<String> dependencies) {
		this.dependenciesS = dependencies;
	}

	@Override
	public String toString() {
		return "Info [authorS="+this.authorS+", contact="+this.contact+", dependenciesS="+this.dependenciesS+", description="+this.description+", factorio_version="+this.factorio_version+", homepage="+this.homepage+", name="+this.name+", title="+this.title+", version="+this.version+"]";
	}

	public static class DependenciesDeserilizer implements JsonDeserializer<Info> {
		private final Gson gson = new Gson();

		@Override
		public Info deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
			final Info info = this.gson.fromJson(json, Info.class);
			final JsonObject jsonObject = json.getAsJsonObject();
			if (jsonObject.has("dependencies"))
				info.setDependencies(parse(jsonObject.get("dependencies")));
			if (jsonObject.has("author"))
				info.setAuthor(parse(jsonObject.get("author")));
			return info;
		}

		private List<String> parse(final JsonElement elem) {
			final List<String> list = new ArrayList<>();
			if (elem!=null&&!elem.isJsonNull())
				if (elem.isJsonArray()) {
					if (elem.getAsJsonArray().size()==1)
						list.add(elem.getAsJsonArray().getAsString());
					else
						for (final Iterator<JsonElement> it = elem.getAsJsonArray().iterator(); it.hasNext();)
							list.add(it.next().getAsString());
				} else
					list.add(elem.getAsString());
			return list;
		}
	}
}
