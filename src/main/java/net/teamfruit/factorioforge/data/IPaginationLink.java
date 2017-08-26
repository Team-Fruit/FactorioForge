package net.teamfruit.factorioforge.data;

import java.util.Optional;

public interface IPaginationLink {

	/**
	 * URL to the first page of the results, or null if you're already on the first page.
	 * @return Optional<String>
	 */
	Optional<String> getFirst();

	/**
	 * URL to the previous page of the results, or null if you're already on the first page.
	 * @return Optional<String>
	 */
	Optional<String> getPrev();

	/**
	 * URL to the next page of the results, or null if you're already on the last page.
	 * @return Optional<String>
	 */
	Optional<String> getNext();

	/**
	 * URL to the last page of the results, or null if you're already on the last page.
	 * @return Optional<String>
	 */
	Optional<String> getLast();
}
