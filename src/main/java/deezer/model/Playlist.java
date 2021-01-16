package deezer.model;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import deezer.model.data.Tracks;
import deezer.model.interfaces.Searchable;
import util.URLTypeAdapter;

import java.io.Serializable;
import java.net.URL;
import java.util.Objects;
import java.util.StringJoiner;

public class Playlist implements Serializable, Searchable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String title;
    private String description;
    private Integer duration;
    @SerializedName("public")
    private Boolean isPublic;
    @SerializedName("is_loved_track")
    private Boolean isLovedTracksPlaylist;
    @SerializedName("collaborative")
    private Boolean isCollaborative;
    private Integer rating;
    @SerializedName("nb_track")
    private Integer numberOfTracks;
    @SerializedName("unseen_track_count")
    private Integer unseenTracksCount;
    @SerializedName("fans")
    private Integer numberOfFans;
    @JsonAdapter(URLTypeAdapter.class)
    private URL link;
    @JsonAdapter(URLTypeAdapter.class)
    private URL share;
    @JsonAdapter(URLTypeAdapter.class)
    private URL picture;
    @JsonAdapter(URLTypeAdapter.class)
    @SerializedName("picture_small")
    private URL smallPicture;
    @JsonAdapter(URLTypeAdapter.class)
    @SerializedName("picture_medium")
    private URL mediumPicture;
    @JsonAdapter(URLTypeAdapter.class)
    @SerializedName("picture_big")
    private URL bigPicture;
    @JsonAdapter(URLTypeAdapter.class)
    @SerializedName("picture_xl")
    private URL xlPicture;
    private String checksum;
    private User creator;
    private Tracks tracks;

    private Integer position;

    public Long getId() {
        return this.id;
    }

    public Playlist setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public Playlist setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public Playlist setDescription(String description) {
        this.description = description;
        return this;
    }

    public Integer getDuration() {
        return this.duration;
    }

    public Playlist setDuration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public Boolean isPublic() {
        return this.isPublic;
    }

    public Playlist setPublic(Boolean isPublic) {
        this.isPublic = isPublic;
        return this;
    }

    public Boolean isLovedTracksPlaylist() {
        return this.isLovedTracksPlaylist;
    }

    public Playlist setLovedTracksPlaylist(Boolean isLovedTracksPlaylist) {
        this.isLovedTracksPlaylist = isLovedTracksPlaylist;
        return this;
    }

    public Boolean isCollaborative() {
        return this.isCollaborative;
    }

    public Playlist setCollaborative(Boolean isCollaborative) {
        this.isCollaborative = isCollaborative;
        return this;
    }

    public Integer getRating() {
        return this.rating;
    }

    public Playlist setRating(Integer rating) {
        this.rating = rating;
        return this;
    }

    public Integer getNumberOfTracks() {
        return this.numberOfTracks;
    }

    public Playlist setNumberOfTracks(Integer numberOfTracks) {
        this.numberOfTracks = numberOfTracks;
        return this;
    }

    public Integer getUnseenTracksCount() {
        return this.unseenTracksCount;
    }

    public Playlist setUnseenTracksCount(Integer unseenTracksCount) {
        this.unseenTracksCount = unseenTracksCount;
        return this;
    }

    public Integer getNumberOfFans() {
        return this.numberOfFans;
    }

    public Playlist setNumberOfFans(Integer numberOfFans) {
        this.numberOfFans = numberOfFans;
        return this;
    }

    public URL getLink() {
        return this.link;
    }

    public Playlist setLink(URL link) {
        this.link = link;
        return this;
    }

    public URL getShare() {
        return this.share;
    }

    public Playlist setShare(URL share) {
        this.share = share;
        return this;
    }

    public URL getPicture() {
        return this.picture;
    }

    public Playlist setPicture(URL picture) {
        this.picture = picture;
        return this;
    }

    public URL getSmallPicture() {
        return this.smallPicture;
    }

    public Playlist setSmallPicture(URL smallPicture) {
        this.smallPicture = smallPicture;
        return this;
    }

    public URL getMediumPicture() {
        return this.mediumPicture;
    }

    public Playlist setMediumPicture(URL mediumPicture) {
        this.mediumPicture = mediumPicture;
        return this;
    }

    public URL getBigPicture() {
        return this.bigPicture;
    }

    public Playlist setBigPicture(URL bigPicture) {
        this.bigPicture = bigPicture;
        return this;
    }

    public URL getXlPicture() {
        return this.xlPicture;
    }

    public Playlist setXlPicture(URL xlPicture) {
        this.xlPicture = xlPicture;
        return this;
    }

    public String getChecksum() {
        return this.checksum;
    }

    public Playlist setChecksum(String checksum) {
        this.checksum = checksum;
        return this;
    }

    public User getCreator() {
        return this.creator;
    }

    public Playlist setCreator(User creator) {
        this.creator = creator;
        return this;
    }

    public Tracks getTracks() {
        return this.tracks;
    }

    public Playlist setTracks(Tracks tracks) {
        this.tracks = tracks;
        return this;
    }

    public Integer getPosition() {
        return this.position;
    }

    public Playlist setPosition(Integer position) {
        this.position = position;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Playlist.class.getSimpleName() + "{", "}")
                .add("id=" + this.id)
                .add("title=" + this.title)
                .add("description=" + this.description)
                .add("duration=" + this.duration)
                .add("isPublic=" + this.isPublic)
                .add("isLovedTracksPlaylist=" + this.isLovedTracksPlaylist)
                .add("isCollaborative=" + this.isCollaborative)
                .add("rating=" + this.rating)
                .add("numberOfTracks=" + this.numberOfTracks)
                .add("unseenTracksCount=" + this.unseenTracksCount)
                .add("numberOfFans=" + this.numberOfFans)
                .add("link=" + this.link)
                .add("share=" + this.share)
                .add("picture=" + this.picture)
                .add("smallPicture=" + this.smallPicture)
                .add("mediumPicture=" + this.mediumPicture)
                .add("bigPicture=" + this.bigPicture)
                .add("xlPicture=" + this.xlPicture)
                .add("checksum=" + this.checksum)
                .add("creator=" + this.creator)
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
        Playlist playlist = (Playlist) other;
        return  Objects.equals(this.id, playlist.id) &&
                Objects.equals(this.title, playlist.title) &&
                Objects.equals(this.description, playlist.description) &&
                Objects.equals(this.duration, playlist.duration) &&
                Objects.equals(this.isPublic, playlist.isPublic) &&
                Objects.equals(this.isLovedTracksPlaylist, playlist.isLovedTracksPlaylist) &&
                Objects.equals(this.isCollaborative, playlist.isCollaborative) &&
                Objects.equals(this.rating, playlist.rating) &&
                Objects.equals(this.numberOfTracks, playlist.numberOfTracks) &&
                Objects.equals(this.unseenTracksCount, playlist.unseenTracksCount) &&
                Objects.equals(this.numberOfFans, playlist.numberOfFans) &&
                Objects.equals(this.link, playlist.link) &&
                Objects.equals(this.share, playlist.share) &&
                Objects.equals(this.picture, playlist.picture) &&
                Objects.equals(this.smallPicture, playlist.smallPicture) &&
                Objects.equals(this.mediumPicture, playlist.mediumPicture) &&
                Objects.equals(this.bigPicture, playlist.bigPicture) &&
                Objects.equals(this.xlPicture, playlist.xlPicture) &&
                Objects.equals(this.checksum, playlist.checksum) &&
                Objects.equals(this.creator, playlist.creator) &&
                Objects.equals(this.tracks, playlist.tracks) &&
                Objects.equals(this.position, playlist.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.title, this.description, this.duration, this.isPublic,
                            this.isLovedTracksPlaylist, this.isCollaborative, this.rating, this.numberOfTracks,
                            this.unseenTracksCount, this.numberOfFans, this.link, this.share, this.picture,
                            this.smallPicture, this.mediumPicture, this.bigPicture, this.xlPicture, this.checksum,
                            this.creator, this.tracks, this.position);
    }

}
