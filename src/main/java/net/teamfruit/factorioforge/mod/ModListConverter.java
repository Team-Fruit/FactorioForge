package net.teamfruit.factorioforge.mod;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Maps;
import com.google.gson.stream.JsonReader;

import net.teamfruit.factorioforge.factorioapi.FactorioAPI;
import net.teamfruit.factorioforge.factorioapi.data.impl.modportal.Info;

public class ModListConverter {

	public static ModListBean getModList(final InputStream is) throws IOException {
		try (final JsonReader r = new JsonReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
			return FactorioAPI.gson.fromJson(r, ModListBean.class);
		}
	}

	public static ModListBean getModList(final File file) throws IOException {
		try (JsonReader r = new JsonReader(new FileReader(file))) {
			return FactorioAPI.gson.fromJson(r, ModListBean.class);
		}
	}

	public static void setModList(final File file, final ModListBean bean) throws IOException {
		try (BufferedWriter r = new BufferedWriter(new FileWriter(file))) {
			FactorioAPI.gson.toJson(bean, ModListBean.class, r);
		}
	}

	public static Map<String, File> discoverModsDir(final File dir) {
		final File[] files = dir.listFiles((f, n) -> StringUtils.endsWithIgnoreCase(n, ".zip"));
		if (files==null)
			return Maps.newHashMap();
		return Stream.of(files)
				.collect(Collectors.toMap(f -> StringUtils.substringBeforeLast(f.getName(), "_"), f -> f));
	}

	public static Info getModInfo(final File mod) throws IOException {
		try (final ZipFile zip = new ZipFile(mod)) {
			final ZipEntry entry = zip.getEntry(zip.stream().filter((e) -> e.getName().endsWith("info.json"))
					.findAny().orElseThrow(() -> new RuntimeException(mod.getName()+" does not include info.json")).getName());
			if (entry!=null)
				try (JsonReader r = new JsonReader(new InputStreamReader(zip.getInputStream(entry), StandardCharsets.UTF_8))) {
					return FactorioAPI.gson.fromJson(r, Info.class);
				}
			else
				return null;
		}
	}
}
