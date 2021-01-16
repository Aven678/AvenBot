package deezer.model;

import deezer.model.data.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

public class Chart implements Serializable {

    private static final long serialVersionUID = 1L;

    private Tracks tracks;
    private Albums albums;
    private Artists artists;
    private Playlists playlists;
    private Podcasts podcasts;

    public Tracks getTracks() {
        return this.tracks;
    }

    public Chart setTracks(Tracks tracks) {
        this.tracks = tracks;
        return this;
    }

    public Albums getAlbums() {
        return this.albums;
    }

    public Chart setAlbums(Albums albums) {
        this.albums = albums;
        return this;
    }

    public Artists getArtists() {
        return this.artists;
    }

    public Chart setArtists(Artists artists) {
        this.artists = artists;
        return this;
    }

    public Playlists getPlaylists() {
        return this.playlists;
    }

    public Chart setPlaylists(Playlists playlists) {
        this.playlists = playlists;
        return this;
    }

    public Podcasts getPodcasts() {
        return this.podcasts;
    }

    public Chart setPodcasts(Podcasts podcasts) {
        this.podcasts = podcasts;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Chart.class.getSimpleName() + "{", "}")
                .add("tracks=" + this.tracks)
                .add("albums=" + this.albums)
                .add("artists=" + this.artists)
                .add("playlists=" + this.playlists)
                .add("podcasts=" + this.podcasts)
                .toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (other == null || this.getClass() != other.getClass())
            return false;
        Chart chart = (Chart) other;
        return  Objects.equals(this.tracks, chart.tracks) &&
                Objects.equals(this.albums, chart.albums) &&
                Objects.equals(this.artists, chart.artists) &&
                Objects.equals(this.playlists, chart.playlists) &&
                Objects.equals(this.podcasts, chart.podcasts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.tracks, this.albums, this.artists, this.playlists, this.podcasts);
    }

}
