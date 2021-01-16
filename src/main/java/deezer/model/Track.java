package deezer.model;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import deezer.model.interfaces.Searchable;
import util.URLTypeAdapter;

import java.io.Serializable;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class Track implements Serializable, Searchable {

    private static final long serialVersionUID = 1L;

    public static final class ExplicitContentLevels {

        public static final int NOT_EXPLICIT = 0;
        public static final int EXPLICIT = 1;
        public static final int UNKNOWN = 2;
        public static final int EDITED = 3;
        public static final int NO_ADVICE_AVAILABLE = 6;

        private ExplicitContentLevels() {
            throw new UnsupportedOperationException();
        }

    }

    private Long id;
    @SerializedName("readable")
    private Boolean isReadable;
    private String title;
    @SerializedName("title_short")
    private String shortTitle;
    @SerializedName("title_version")
    private String titleVersion;
    @SerializedName("unseen")
    private Boolean isUnseen;
    private String isrc;
    @JsonAdapter(URLTypeAdapter.class)
    private URL link;
    @JsonAdapter(URLTypeAdapter.class)
    private URL share;
    private Integer duration;
    @SerializedName("track_position")
    private Integer trackPosition;
    @SerializedName("disk_number")
    private Integer diskNumber;
    private Integer rank;
    private Date releaseDate;
    @SerializedName("explicit_lyrics")
    private Boolean hasExplicitLyrics;
    @SerializedName("explicit_content_lyrics")
    private Integer explicitContentLyrics;
    @SerializedName("explicit_content_cover")
    private Integer explicitContentCover;
    @JsonAdapter(URLTypeAdapter.class)
    private URL preview;
    private Float bpm;
    private Float gain;
    @SerializedName("available_countries")
    private List<String> availableCounties;
    private Track alternative;
    private List<Artist> contributors;
    private Artist artist;
    private Album album;

    private Integer position;

    public Long getId() {
        return this. id;
    }

    public Track setId(Long id) {
        this.id = id;
        return this;
    }

    public Boolean isReadable() {
        return this.isReadable;
    }

    public Track setReadable(Boolean isReadable) {
        this.isReadable = isReadable;
        return this;
    }

    public String getTitle() {
        return this. title;
    }

    public Track setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getShortTitle() {
        return this. shortTitle;
    }

    public Track setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
        return this;
    }

    public String getTitleVersion() {
        return this. titleVersion;
    }

    public Track setTitleVersion(String titleVersion) {
        this.titleVersion = titleVersion;
        return this;
    }

    public Boolean isUnseen() {
        return this.isUnseen;
    }

    public Track setUnseen(Boolean isUnseen) {
        this.isUnseen = isUnseen;
        return this;
    }

    public String getIsrc() {
        return this. isrc;
    }

    public Track setIsrc(String isrc) {
        this.isrc = isrc;
        return this;
    }

    public URL getLink() {
        return this. link;
    }

    public Track setLink(URL link) {
        this.link = link;
        return this;
    }

    public URL getShare() {
        return this. share;
    }

    public Track setShare(URL share) {
        this.share = share;
        return this;
    }

    public Integer getDuration() {
        return this. duration;
    }

    public Track setDuration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public Integer getTrackPosition() {
        return this. trackPosition;
    }

    public Track setTrackPosition(Integer trackPosition) {
        this.trackPosition = trackPosition;
        return this;
    }

    public Integer getDiskNumber() {
        return this. diskNumber;
    }

    public Track setDiskNumber(Integer diskNumber) {
        this.diskNumber = diskNumber;
        return this;
    }

    public Integer getRank() {
        return this. rank;
    }

    public Track setRank(Integer rank) {
        this.rank = rank;
        return this;
    }

    public Date getReleaseDate() {
        return this. releaseDate;
    }

    public Track setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    public Boolean getHasExplicitLyrics() {
        return this. hasExplicitLyrics;
    }

    public Track setHasExplicitLyrics(Boolean hasExplicitLyrics) {
        this.hasExplicitLyrics = hasExplicitLyrics;
        return this;
    }

    public Integer getExplicitContentLyrics() {
        return this. explicitContentLyrics;
    }

    public Track setExplicitContentLyrics(Integer explicitContentLyrics) {
        this.explicitContentLyrics = explicitContentLyrics;
        return this;
    }

    public Integer getExplicitContentCover() {
        return this. explicitContentCover;
    }

    public Track setExplicitContentCover(Integer explicitContentCover) {
        this.explicitContentCover = explicitContentCover;
        return this;
    }

    public URL getPreview() {
        return this. preview;
    }

    public Track setPreview(URL preview) {
        this.preview = preview;
        return this;
    }

    public Float getBpm() {
        return this. bpm;
    }

    public Track setBpm(Float bpm) {
        this.bpm = bpm;
        return this;
    }

    public Float getGain() {
        return this. gain;
    }

    public Track setGain(Float gain) {
        this.gain = gain;
        return this;
    }

    public List<String> getAvailableCounties() {
        return this. availableCounties;
    }

    public Track setAvailableCounties(List<String> availableCounties) {
        this.availableCounties = availableCounties;
        return this;
    }

    public Track getAlternative() {
        return this. alternative;
    }

    public Track setAlternative(Track alternative) {
        this.alternative = alternative;
        return this;
    }

    public List<Artist> getContributors() {
        return this. contributors;
    }

    public Track setContributors(List<Artist> contributors) {
        this.contributors = contributors;
        return this;
    }

    public Artist getArtist() {
        return this. artist;
    }

    public Track setArtist(Artist artist) {
        this.artist = artist;
        return this;
    }

    public Album getAlbum() {
        return this. album;
    }

    public Track setAlbum(Album album) {
        this.album = album;
        return this;
    }

    public Integer getPosition() {
        return this.position;
    }

    public Track setPosition(Integer position) {
        this.position = position;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Track.class.getSimpleName() + "{", "}")
                .add("id=" + this.id)
                .add("isReadable=" + this.isReadable)
                .add("title=" + this.title)
                .add("shortTitle=" + this.shortTitle)
                .add("titleVersion=" + this.titleVersion)
                .add("isUnseen=" + this.isUnseen)
                .add("isrc=" + this.isrc)
                .add("link=" + this.link)
                .add("share=" + this.share)
                .add("duration=" + this.duration)
                .add("trackPosition=" + this.trackPosition)
                .add("diskNumber=" + this.diskNumber)
                .add("rank=" + this.rank)
                .add("releaseDate=" + this.releaseDate)
                .add("hasExplicitLyrics=" + this.hasExplicitLyrics)
                .add("explicitContentLyrics=" + this.explicitContentLyrics)
                .add("explicitContentCover=" + this.explicitContentCover)
                .add("preview=" + this.preview)
                .add("bpm=" + this.bpm)
                .add("gain=" + this.gain)
                .add("availableCounties=" + this.availableCounties)
                .add("alternative=" + this.alternative)
                .add("contributors=" + this.contributors)
                .add("artist=" + this.artist)
                .add("album=" + this.album)
                .add("position=" + this.position)
                .toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (other == null || this.getClass() != other.getClass())
            return false;
        Track track = (Track) other;
        return  Objects.equals(this.id, track.id) &&
                Objects.equals(this.isReadable, track.isReadable) &&
                Objects.equals(this.title, track.title) &&
                Objects.equals(this.shortTitle, track.shortTitle) &&
                Objects.equals(this.titleVersion, track.titleVersion) &&
                Objects.equals(this.isUnseen, track.isUnseen) &&
                Objects.equals(this.isrc, track.isrc) &&
                Objects.equals(this.link, track.link) &&
                Objects.equals(this.share, track.share) &&
                Objects.equals(this.duration, track.duration) &&
                Objects.equals(this.trackPosition, track.trackPosition) &&
                Objects.equals(this.diskNumber, track.diskNumber) &&
                Objects.equals(this.rank, track.rank) &&
                Objects.equals(this.releaseDate, track.releaseDate) &&
                Objects.equals(this.hasExplicitLyrics, track.hasExplicitLyrics) &&
                Objects.equals(this.explicitContentLyrics, track.explicitContentLyrics) &&
                Objects.equals(this.explicitContentCover, track.explicitContentCover) &&
                Objects.equals(this.preview, track.preview) &&
                Objects.equals(this.bpm, track.bpm) &&
                Objects.equals(this.gain, track.gain) &&
                Objects.equals(this.availableCounties, track.availableCounties) &&
                Objects.equals(this.alternative, track.alternative) &&
                Objects.equals(this.contributors, track.contributors) &&
                Objects.equals(this.artist, track.artist) &&
                Objects.equals(this.album, track.album) &&
                Objects.equals(this.position, track.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.isReadable, this.title, this.shortTitle, this.titleVersion, this.isUnseen,
                            this.isrc, this.link, this.share, this.duration, this.trackPosition, this.diskNumber,
                            this.rank, this.releaseDate, this.hasExplicitLyrics, this.explicitContentLyrics,
                            this.explicitContentCover, this.preview, this.bpm, this.gain, this.availableCounties,
                            this.alternative, this.contributors, this.artist, this.album, this.position);
    }

}
