package org.nem.core.model.namespace;

import org.nem.core.serialization.*;

import java.util.regex.Pattern;

/**
 * Represents a part of a namespace id
 */
public class NamespaceIdPart implements SerializableEntity {
	private final String id;
	private static final Pattern IsValidPattern = Pattern.compile("[^a-zA-Z0-9_-]");

	/**
	 * Creates a namespace id part from a string.
	 *
	 * @param id The id.
	 */
	public NamespaceIdPart(final String id) {
		this.id = id.toLowerCase();
		if (!this.isValid()) {
			throw new IllegalArgumentException(String.format("'%s' is not a valid namespace id part.", this.id));
		}
	}

	/**
	 * Deserializes a namespace id.
	 *
	 * @param deserializer The deserializer.
	 */
	public NamespaceIdPart(final Deserializer deserializer) {
		this(deserializer.readString("id"));
	}

	/**
	 * Gets a value indicating whether or not the namespace id part is valid.
	 *
	 * @return true if valid, false otherwise.
	 */
	public boolean isValid() {
		return !this.id.isEmpty() && !IsValidPattern.matcher(this.id).find();
	}

	/**
	 * Creates a (root) namespace id from this.
	 * TODO 20150618 J-B: why do we need this?
	 * TODO 20150619 BR -> J: ProvisionNamespaceTransaction.getResultingNamespaceId() needs to convert a part to a NamespaceId.
	 * > Alternatively i could use "new NamespaceId(this.newPart.toString())" in that place.
	 *
	 * @return The (root) namespace id.
	 */
	public NamespaceId toNamespaceId() {
		return new NamespaceId(this.toString());
	}

	@Override
	public String toString() {
		return this.id;
	}

	@Override
	public int hashCode() {
		return this.id.hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		if (!(obj instanceof NamespaceIdPart)) {
			return false;
		}

		final NamespaceIdPart rhs = (NamespaceIdPart)obj;
		return this.id.equals(rhs.id);
	}

	@Override
	public void serialize(final Serializer serializer) {
		serializer.writeString("id", this.toString());
	}
}
