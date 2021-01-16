package deezer.model;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import deezer.model.interfaces.Searchable;
import util.URLTypeAdapter;

import java.io.Serializable;
import java.net.URL;
import java.util.Objects;
import java.util.StringJoiner;

public class Radio implements Serializable, Searchable {

    private static final long serialVersionUID = 1L;

    private Long radio;
    private String title;
    private String description;
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
    @JsonAdapter(URLTypeAdapter.class)
    private URL tracklist;

    public Long getRadio() {
        return this.radio;
    }

    public Radio setRadio(Long radio) {
        this.radio = radio;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public Radio setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public Radio setDescription(String description) {
        this.description = description;
        return this;
    }

    public URL getShare() {
        return this.share;
    }

    public Radio setShare(URL share) {
        this.share = share;
        return this;
    }

    public URL getPicture() {
        return this.picture;
    }

    public Radio setPicture(URL picture) {
        this.picture = picture;
        return this;
    }

    public URL getSmallPicture() {
        return this.smallPicture;
    }

    public Radio setSmallPicture(URL smallPicture) {
        this.smallPicture = smallPicture;
        return this;
    }

    public URL getMediumPicture() {
        return this.mediumPicture;
    }

    public Radio setMediumPicture(URL mediumPicture) {
        this.mediumPicture = mediumPicture;
        return this;
    }

    public URL getBigPicture() {
        return this.bigPicture;
    }

    public Radio setBigPicture(URL bigPicture) {
        this.bigPicture = bigPicture;
        return this;
    }

    public URL getXlPicture() {
        return this.xlPicture;
    }

    public Radio setXlPicture(URL xlPicture) {
        this.xlPicture = xlPicture;
        return this;
    }

    public URL getTracklist() {
        return this.tracklist;
    }

    public Radio setTracklist(URL tracklist) {
        this.tracklist = tracklist;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Radio.class.getSimpleName() + "{", "}")
                .add("radio=" + this.radio)
                .add("title=" + this.title)
                .add("description=" + this.description)
                .add("share=" + this.share)
                .add("picture=" + this.picture)
                .add("smallPicture=" + this.smallPicture)
                .add("mediumPicture=" + this.mediumPicture)
                .add("bigPicture=" + this.bigPicture)
                .add("xlPicture=" + this.xlPicture)
                .add("tracklist=" + this.tracklist)
                .toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (other == null || this.getClass() != other.getClass())
            return false;
        Radio radio = (Radio) other;
        return  Objects.equals(this.radio, radio.radio) &&
                Objects.equals(this.title, radio.title) &&
                Objects.equals(this.description, radio.description) &&
                Objects.equals(this.share, radio.share) &&
                Objects.equals(this.picture, radio.picture) &&
                Objects.equals(this.smallPicture, radio.smallPicture) &&
                Objects.equals(this.mediumPicture, radio.mediumPicture) &&
                Objects.equals(this.bigPicture, radio.bigPicture) &&
                Objects.equals(this.xlPicture, radio.xlPicture) &&
                Objects.equals(this.tracklist, radio.tracklist);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.radio, this.title, this.description, this.share, this.picture, this.smallPicture,
                            this.mediumPicture, this.bigPicture, this.xlPicture, this.tracklist);
    }

}
