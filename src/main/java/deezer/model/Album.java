package deezer.model;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import deezer.model.data.Genres;
import deezer.model.data.Tracks;
import deezer.model.interfaces.Commentable;
import deezer.model.interfaces.Searchable;
import util.URLTypeAdapter;

import java.io.Serializable;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class Album implements Serializable, Searchable, Commentable {

    private static final long serialVersionUID = 1L;

    public static final class ExplicitContentLevels {

        public static final int NOT_EXPLICIT = 0;
        public static final int EXPLICIT = 1;
        public static final int UNKNOWN = 2;
        public static final int EDITED = 3;
        public static final int PARTIALLY_EXPLICIT = 4;
        public static final int PARTIALLY_UNKNOWN = 5;
        public static final int NO_ADVICE_AVAILABLE = 6;
        public static final int PARTIALLY_NO_ADVICE_AVAILABLE = 7;

        private ExplicitContentLevels() {
            throw new UnsupportedOperationException();
        }

    }

    private Long id;
    private String title;
    private String upc;
    @JsonAdapter(URLTypeAdapter.class)
    private URL link;
    @JsonAdapter(URLTypeAdapter.class)
    private URL share;
    @JsonAdapter(URLTypeAdapter.class)
    private URL cover;
    @JsonAdapter(URLTypeAdapter.class)
    @SerializedName("cover_small")
    private URL smallCover;
    @JsonAdapter(URLTypeAdapter.class)
    @SerializedName("cover_medium")
    private URL mediumCover;
    @JsonAdapter(URLTypeAdapter.class)
    @SerializedName("cover_big")
    private URL bigCover;
    @JsonAdapter(URLTypeAdapter.class)
    @SerializedName("cover_xl")
    private URL xlCover;
    @SerializedName("genre_id")
    private Long genreId;
    private Genres genres;
    private String label;
    @SerializedName("nb_tracks")
    private Integer numberOfTracks;
    private Integer duration;
    @SerializedName("fans")
    private Integer numberOfFans;
    private Integer rating;
    @SerializedName("release_date")
    private Date releaseDate;
    @SerializedName("record_type")
    private String recordType;
    @SerializedName("available")
    private Boolean isAvailable;
    private Album alternative;
    @JsonAdapter(URLTypeAdapter.class)
    private URL tracklist;
    @SerializedName("explicit_lyrics")
    private Boolean hasExplicitLyrics;
    @SerializedName("explicit_content_lyrics")
    private Integer explicitContentLyrics;
    @SerializedName("explicit_content_cover")
    private Integer explicitContentCover;
    private List<Artist> contributors;
    private Artist artist;
    private Tracks tracks;

    private Integer position;

    public Long getId() {
        return this.id;
    }

    public Album setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public Album setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getUpc() {
        return this.upc;
    }

    public Album setUpc(String upc) {
        this.upc = upc;
        return this;
    }

    public URL getLink() {
        return this.link;
    }

    public Album setLink(URL link) {
        this.link = link;
        return this;
    }

    public URL getShare() {
        return this.share;
    }

    public Album setShare(URL share) {
        this.share = share;
        return this;
    }

    public URL getCover() {
        return this.cover;
    }

    public Album setCover(URL cover) {
        this.cover = cover;
        return this;
    }

    public URL getSmallCover() {
        return this.smallCover;
    }

    public Album setSmallCover(URL smallCover) {
        this.smallCover = smallCover;
        return this;
    }

    public URL getMediumCover() {
        return this.mediumCover;
    }

    public Album setMediumCover(URL mediumCover) {
        this.mediumCover = mediumCover;
        return this;
    }

    public URL getBigCover() {
        return this.bigCover;
    }

    public Album setBigCover(URL bigCover) {
        this.bigCover = bigCover;
        return this;
    }

    public URL getXlCover() {
        return this.xlCover;
    }

    public Album setXlCover(URL xlCover) {
        this.xlCover = xlCover;
        return this;
    }

    public Long getGenreId() {
        return this.genreId;
    }

    public Album setGenreId(Long genreId) {
        this.genreId = genreId;
        return this;
    }

    public Genres getGenres() {
        return this.genres;
    }

    public Album setGenres(Genres genres) {
        this.genres = genres;
        return this;
    }

    public String getLabel() {
        return this.label;
    }

    public Album setLabel(String label) {
        this.label = label;
        return this;
    }

    public Integer getNumberOfTracks() {
        return this.numberOfTracks;
    }

    public Album setNumberOfTracks(Integer numberOfTracks) {
        this.numberOfTracks = numberOfTracks;
        return this;
    }

    public Integer getDuration() {
        return this.duration;
    }

    public Album setDuration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public Integer getNumberOfFans() {
        return this.numberOfFans;
    }

    public Album setNumberOfFans(Integer numberOfFans) {
        this.numberOfFans = numberOfFans;
        return this;
    }

    public Integer getRating() {
        return this.rating;
    }

    public Album setRating(Integer rating) {
        this.rating = rating;
        return this;
    }

    public Date getReleaseDate() {
        return this.releaseDate;
    }

    public Album setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    public String getRecordType() {
        return this.recordType;
    }

    public Album setRecordType(String recordType) {
        this.recordType = recordType;
        return this;
    }

    public Boolean isAvailable() {
        return this.isAvailable;
    }

    public Album setAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
        return this;
    }

    public Album getAlternative() {
        return this.alternative;
    }

    public Album setAlternative(Album alternative) {
        this.alternative = alternative;
        return this;
    }

    public URL getTracklist() {
        return this.tracklist;
    }

    public Album setTracklist(URL tracklist) {
        this.tracklist = tracklist;
        return this;
    }

    public Boolean getHasExplicitLyrics() {
        return this.hasExplicitLyrics;
    }

    public Album setHasExplicitLyrics(Boolean hasExplicitLyrics) {
        this.hasExplicitLyrics = hasExplicitLyrics;
        return this;
    }

    public Integer getExplicitContentLyrics() {
        return this.explicitContentLyrics;
    }

    public Album setExplicitContentLyrics(Integer explicitContentLyrics) {
        this.explicitContentLyrics = explicitContentLyrics;
        return this;
    }

    public Integer getExplicitContentCover() {
        return this.explicitContentCover;
    }

    public Album setExplicitContentCover(Integer explicitContentCover) {
        this.explicitContentCover = explicitContentCover;
        return this;
    }

    public List<Artist> getContributors() {
        return this.contributors;
    }

    public Album setContributors(List<Artist> contributors) {
        this.contributors = contributors;
        return this;
    }

    public Artist getArtist() {
        return this.artist;
    }

    public Album setArtist(Artist artist) {
        this.artist = artist;
        return this;
    }

    public Tracks getTracks() {
        return this.tracks;
    }

    public Album setTracks(Tracks tracks) {
        this.tracks = tracks;
        return this;
    }

    public Integer getPosition() {
        return this.position;
    }

    public Album setPosition(Integer position) {
        this.position = position;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Album.class.getSimpleName() + "{", "}")
                .add("id=" + this.id)
                .add("title=" + this.title)
                .add("upc=" + this.upc)
                .add("link=" + this.link)
                .add("share=" + this.share)
                .add("cover=" + this.cover)
                .add("smallCover=" + this.smallCover)
                .add("mediumCover=" + this.mediumCover)
                .add("bigCover=" + this.bigCover)
                .add("xlCover=" + this.xlCover)
                .add("genreId=" + this.genreId)
                .add("genres=" + this.genres)
                .add("label=" + this.label)
                .add("numberOfTracks=" + this.numberOfTracks)
                .add("duration=" + this.duration)
                .add("numberOfFans=" + this.numberOfFans)
                .add("rating=" + this.rating)
                .add("releaseDate=" + this.releaseDate)
                .add("recordType=" + this.recordType)
                .add("isAvailable=" + this.isAvailable)
                .add("alternative=" + this.alternative)
                .add("tracklist=" + this.tracklist)
                .add("hasExplicitLyrics=" + this.hasExplicitLyrics)
                .add("explicitContentLyrics=" + this.explicitContentLyrics)
                .add("explicitContentCover=" + this.explicitContentCover)
                .add("contributors=" + this.contributors)
                .add("artist=" + this.artist)
                .add("tracks=" + this.tracks)
                .add("position=" + this.position)
                .toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (other == null || this.getClass() != other.getClass())
            return false;
        Album album = (Album) other;
        return  Objects.equals(this.id, album.id) &&
                Objects.equals(this.title, album.title) &&
                Objects.equals(this.upc, album.upc) &&
                Objects.equals(this.link, album.link) &&
                Objects.equals(this.share, album.share) &&
                Objects.equals(this.cover, album.cover) &&
                Objects.equals(this.smallCover, album.smallCover) &&
                Objects.equals(this.mediumCover, album.mediumCover) &&
                Objects.equals(this.bigCover, album.bigCover) &&
                Objects.equals(this.xlCover, album.xlCover) &&
                Objects.equals(this.genreId, album.genreId) &&
                Objects.equals(this.genres, album.genres) &&
                Objects.equals(this.label, album.label) &&
                Objects.equals(this.numberOfTracks, album.numberOfTracks) &&
                Objects.equals(this.duration, album.duration) &&
                Objects.equals(this.numberOfFans, album.numberOfFans) &&
                Objects.equals(this.rating, album.rating) &&
                Objects.equals(this.releaseDate, album.releaseDate) &&
                Objects.equals(this.recordType, album.recordType) &&
                Objects.equals(this.isAvailable, album.isAvailable) &&
                Objects.equals(this.alternative, album.alternative) &&
                Objects.equals(this.tracklist, album.tracklist) &&
                Objects.equals(this.hasExplicitLyrics, album.hasExplicitLyrics) &&
                Objects.equals(this.explicitContentLyrics, album.explicitContentLyrics) &&
                Objects.equals(this.explicitContentCover, album.explicitContentCover) &&
                Objects.equals(this.contributors, album.contributors) &&
                Objects.equals(this.artist, album.artist) &&
                Objects.equals(this.tracks, album.tracks) &&
                Objects.equals(this.position, album.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.title, this.upc, this.link, this.share, this.cover, this.smallCover,
                            this.mediumCover, this.bigCover, this.xlCover, this.genreId, this.genres, this.label,
                            this.numberOfTracks, this.duration, this.numberOfFans, this.rating, this.releaseDate,
                            this.recordType, this.isAvailable, this.alternative, this.tracklist, this.hasExplicitLyrics,
                            this.explicitContentLyrics, this.explicitContentCover, this.contributors, this.artist,
                            this.tracks, this.position);
    }

}
