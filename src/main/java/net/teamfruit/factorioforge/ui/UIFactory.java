package net.teamfruit.factorioforge.ui;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.ReflectionUtil;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class UIFactory {
	public static FXMLLoader loadUI(final String name, final Class<?> env) throws IOException {
		final FXMLLoader loader = new FXMLLoader(env.getResource(StringUtils.removeEnd(name, ".fxml")+".fxml"));
		loader.setRoot(new AnchorPane());
		// The following line is supposed to help Scene Builder, although it doesn't seem to be needed for me.
		loader.setClassLoader(env.getClassLoader());
		loader.load();
		return loader;
	}

	public static FXMLLoader loadUI(final String name) throws IOException {
		return loadUI(name, ReflectionUtil.getCallerClass(UIFactory.class));
	}
}
