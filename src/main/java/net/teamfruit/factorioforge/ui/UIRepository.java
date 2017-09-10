package net.teamfruit.factorioforge.ui;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Window;

public enum UIRepository {
	SCENE {
		@Override
		Object getKey(final Object current) {
			if (current instanceof Node)
				return ((Node) current).getScene();
			else if (current instanceof Scene)
				return current;
			throw new IllegalArgumentException("not scene or node");
		}
	},
	STAGE {
		@Override
		Object getKey(final Object current) {
			if (current instanceof Node)
				return ((Node) current).getScene().getWindow();
			else if (current instanceof Scene)
				return ((Scene) current).getWindow();
			else if (current instanceof Window)
				return current;
			throw new IllegalArgumentException("not window or scene or node");
		}
	},
	;

	private final Table<Object, UIRepositoryProperty<?>, Object> map = HashBasedTable.create();

	abstract Object getKey(Object current);

	private <T> T get(final Object current, final UIRepositoryProperty<T> key) {
		@SuppressWarnings("unchecked")
		final T value = (T) this.map.get(getKey(current), key);
		return value;
	}

	private <T> void set(final Object current, final UIRepositoryProperty<T> key, final T value) {
		final Object currentKey = getKey(current);
		if (value!=null)
			this.map.put(currentKey, key, value);
		else
			this.map.remove(currentKey, key);
	}

	public static <T> UIUniversalRepositoryProperty<T> key() {
		return new UIUniversalRepositoryProperty<T>();
	}

	public static <T> UISpecificRepositoryProperty<T> key(final UIRepository repository) {
		return new UISpecificRepositoryProperty<T>(repository);
	}

	public static abstract class UIRepositoryProperty<E> {
		private UIRepositoryProperty() {
		}
	}

	public static class UIUniversalRepositoryProperty<E> extends UIRepositoryProperty<E> {
		private UIUniversalRepositoryProperty() {
		}

		public E get(final Object current, final UIRepository repository) {
			return repository.get(current, this);
		}

		public void set(final Object current, final UIRepository repository, final E value) {
			repository.set(current, this, value);
		}
	}

	public static class UISpecificRepositoryProperty<E> extends UIRepositoryProperty<E> {
		private final UIRepository repository;

		private UISpecificRepositoryProperty(final UIRepository repository) {
			this.repository = repository;
		}

		public E get(final Object current) {
			return this.repository.get(current, this);
		}

		public void set(final Object current, final E value) {
			this.repository.set(current, this, value);
		}
	}
}
