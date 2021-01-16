package deezer.model.search;

import deezer.model.interfaces.Searchable;

import java.util.Objects;
import java.util.StringJoiner;

public abstract class Search<T extends Searchable> {

    String query;
    boolean strict;

    Search(String query) {
        this.query = query;
    }

    Search(String query, boolean strict) {
        this(query);
        this.strict = strict;
    }

    public String getQuery() {
        return this.query;
    }

    public Search setQuery(String query) {
        this.query = query;
        return this;
    }

    public boolean isStrict() {
        return this.strict;
    }

    public Search setStrict(boolean strict) {
        this.strict = strict;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Search.class.getSimpleName() + "{", "}")
                .add("query=" + this.query)
                .add("strict=" + this.strict)
                .toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (other == null || this.getClass() != other.getClass())
            return false;
        Search<?> search = (Search<?>) other;
        return  this.strict == search.strict &&
                Objects.equals(this.query, search.query);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.query, this.strict);
    }

}
