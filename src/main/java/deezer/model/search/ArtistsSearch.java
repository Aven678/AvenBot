package deezer.model.search;

import deezer.model.Artist;

import java.util.StringJoiner;

public class ArtistsSearch extends Search<Artist> {

    public ArtistsSearch(String query) {
        super(query);
    }

    public ArtistsSearch(String query, boolean strict) {
        super(query, strict);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ArtistsSearch.class.getSimpleName() + "{", "}")
                .add("query=" + this.query)
                .add("strict=" + this.strict)
                .toString();
    }

}
