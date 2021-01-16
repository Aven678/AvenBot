package deezer.model;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import util.URLTypeAdapter;

import java.io.Serializable;
import java.net.URL;
import java.util.Objects;
import java.util.StringJoiner;

public class Editorial implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
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

    public Long getId() {
        return this.id;
    }

    public Editorial setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Editorial setName(String name) {
        this.name = name;
        return this;
    }

    public URL getPicture() {
        return this.picture;
    }

    public Editorial setPicture(URL picture) {
        this.picture = picture;
        return this;
    }

    public URL getSmallPicture() {
        return this.smallPicture;
    }

    public Editorial setSmallPicture(URL smallPicture) {
        this.smallPicture = smallPicture;
        return this;
    }

    public URL getMediumPicture() {
        return this.mediumPicture;
    }

    public Editorial setMediumPicture(URL mediumPicture) {
        this.mediumPicture = mediumPicture;
        return this;
    }

    public URL getBigPicture() {
        return this.bigPicture;
    }

    public Editorial setBigPicture(URL bigPicture) {
        this.bigPicture = bigPicture;
        return this;
    }

    public URL getXlPicture() {
        return this.xlPicture;
    }

    public Editorial setXlPicture(URL xlPicture) {
        this.xlPicture = xlPicture;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Editorial.class.getSimpleName() + "{", "}")
                .add("id=" + this.id)
                .add("name=" + this.name)
                .add("picture=" + this.picture)
                .add("smallPicture=" + this.smallPicture)
                .add("mediumPicture=" + this.mediumPicture)
                .add("bigPicture=" + this.bigPicture)
                .add("xlPicture=" + this.xlPicture)
                .toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (other == null || this.getClass() != other.getClass())
            return false;
        Editorial editorial = (Editorial) other;
        return  Objects.equals(this.id, editorial.id) &&
                Objects.equals(this.name, editorial.name) &&
                Objects.equals(this.picture, editorial.picture) &&
                Objects.equals(this.smallPicture, editorial.smallPicture) &&
                Objects.equals(this.mediumPicture, editorial.mediumPicture) &&
                Objects.equals(this.bigPicture, editorial.bigPicture) &&
                Objects.equals(this.xlPicture, editorial.xlPicture);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.picture, this.smallPicture, this.mediumPicture, this.bigPicture,
                            this.xlPicture);
    }

}
