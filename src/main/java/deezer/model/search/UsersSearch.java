package deezer.model.search;

import deezer.model.interfaces.Searchable;

import java.util.StringJoiner;

public class UsersSearch extends Search<Searchable> {

    public UsersSearch(String query) {
        super(query);
    }

    public UsersSearch(String query, boolean strict) {
        super(query, strict);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UsersSearch.class.getSimpleName() + "{", "}")
                .add("query=" + this.query)
                .add("strict=" + this.strict)
                .toString();
    }

}
