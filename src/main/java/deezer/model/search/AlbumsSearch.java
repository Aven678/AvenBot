package deezer.model.search;

import deezer.model.Album;

import java.util.StringJoiner;

public class AlbumsSearch extends Search<Album> {

    public AlbumsSearch(String query) {
        super(query);
    }

    public AlbumsSearch(String query, boolean strict) {
        super(query, strict);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AlbumsSearch.class.getSimpleName() + "{", "}")
                .add("query=" + this.query)
                .add("strict=" + this.strict)
                .toString();
    }

}
