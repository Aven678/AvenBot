package deezer.model;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import util.URLTypeAdapter;

import java.io.Serializable;
import java.net.URL;
import java.util.Objects;
import java.util.StringJoiner;

public class Podcast implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String title;
    private String description;
    @SerializedName("available")
    private Boolean isAvailable;
    private Integer rating;
    @SerializedName("fans")
    private Integer numberOfFans;
    @JsonAdapter(URLTypeAdapter.class)
    private URL link;
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

    private Integer position;

    public Long getId() {
        return this.id;
    }

    public Podcast setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public Podcast setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public Podcast setDescription(String description) {
        this.description = description;
        return this;
    }

    public Boolean getIsAvailable() {
        return this.isAvailable;
    }

    public Podcast setAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
        return this;
    }

    public Integer getRating() {
        return this.rating;
    }

    public Podcast setRating(Integer rating) {
        this.rating = rating;
        return this;
    }

    public Integer getNumberOfFans() {
        return this.numberOfFans;
    }

    public Podcast setNumberOfFans(Integer numberOfFans) {
        this.numberOfFans = numberOfFans;
        return this;
    }

    public URL getLink() {
        return this.link;
    }

    public Podcast setLink(URL link) {
        this.link = link;
        return this;
    }

    public URL getPicture() {
        return this.picture;
    }

    public Podcast setPicture(URL picture) {
        this.picture = picture;
        return this;
    }

    public URL getSmallPicture() {
        return this.smallPicture;
    }

    public Podcast setSmallPicture(URL smallPicture) {
        this.smallPicture = smallPicture;
        return this;
    }

    public URL getMediumPicture() {
        return this.mediumPicture;
    }

    public Podcast setMediumPicture(URL mediumPicture) {
        this.mediumPicture = mediumPicture;
        return this;
    }

    public URL getBigPicture() {
        return this.bigPicture;
    }

    public Podcast setBigPicture(URL bigPicture) {
        this.bigPicture = bigPicture;
        return this;
    }

    public URL getXlPicture() {
        return this.xlPicture;
    }

    public Podcast setXlPicture(URL xlPicture) {
        this.xlPicture = xlPicture;
        return this;
    }

    public Integer getPosition() {
        return this.position;
    }

    public Podcast setPosition(Integer position) {
        this.position = position;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Podcast.class.getSimpleName() + "{", "}")
                .add("id=" + this.id)
                .add("title=" + this.title)
                .add("description=" + this.description)
                .add("isAvailable=" + this.isAvailable)
                .add("rating=" + this.rating)
                .add("numberOfFans=" + this.numberOfFans)
                .add("link=" + this.link)
                .add("picture=" + this.picture)
                .add("smallPicture=" + this.smallPicture)
                .add("mediumPicture=" + this.mediumPicture)
                .add("bigPicture=" + this.bigPicture)
                .add("xlPicture=" + this.xlPicture)
                .add("position=" + this.position)
                .toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (other == null || this.getClass() != other.getClass())
            return false;
        Podcast podcast = (Podcast) other;
        return  Objects.equals(this.id, podcast.id) &&
                Objects.equals(this.title, podcast.title) &&
                Objects.equals(this.description, podcast.description) &&
                Objects.equals(this.isAvailable, podcast.isAvailable) &&
                Objects.equals(this.rating, podcast.rating) &&
                Objects.equals(this.numberOfFans, podcast.numberOfFans) &&
                Objects.equals(this.link, podcast.link) &&
                Objects.equals(this.picture, podcast.picture) &&
                Objects.equals(this.smallPicture, podcast.smallPicture) &&
                Objects.equals(this.mediumPicture, podcast.mediumPicture) &&
                Objects.equals(this.bigPicture, podcast.bigPicture) &&
                Objects.equals(this.xlPicture, podcast.xlPicture) &&
                Objects.equals(this.position, podcast.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.title, this.description, this.isAvailable, this.rating, this.numberOfFans,
                            this.link, this.picture, this.smallPicture, this.mediumPicture, this.bigPicture,
                            this.xlPicture, this.position);
    }

}
