package deezer.model.search;

import deezer.model.interfaces.Searchable;

import java.util.StringJoiner;

public class RadiosSearch extends Search<Searchable> {

    public RadiosSearch(String query) {
        super(query);
    }

    public RadiosSearch(String query, boolean strict) {
        super(query, strict);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", RadiosSearch.class.getSimpleName() + "{", "}")
                .add("query=" + this.query)
                .add("strict=" + this.strict)
                .toString();
    }

}
