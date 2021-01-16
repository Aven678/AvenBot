package deezer.model.search;

import deezer.model.Playlist;

import java.util.StringJoiner;

public class PlaylistsSearch extends Search<Playlist> {

    public PlaylistsSearch(String query) {
        super(query);
    }

    public PlaylistsSearch(String query, boolean strict) {
        super(query, strict);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PlaylistsSearch.class.getSimpleName() + "{", "}")
                .add("query=" + this.query)
                .add("strict=" + this.strict)
                .toString();
    }

}
