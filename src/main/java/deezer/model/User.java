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

public class User implements Serializable, Searchable {

    private static final long serialVersionUID = 1L;

    public static final class ExplicitContentLevels {

        public static final String DISPLAY = "explicit_display";
        public static final String NO_RECOMMENDATION = "explicit_no_recommendation";
        public static final String HIDE = "explicit_hide";

        private ExplicitContentLevels() {
            throw new UnsupportedOperationException();
        }

    }

    private Long id;
    private String name;
    @SerializedName("lastname")
    private String lastName;
    @SerializedName("firstname")
    private String firstName;
    private String email;
    private Integer status;
    private Date birthday;
    @SerializedName("inscription_date")
    private Date inscriptionDate;
    private Character gender;
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
    private String country;
    @SerializedName("lang")
    private String language;
    @SerializedName("is_kid")
    private Boolean isKid;
    @SerializedName("explicit_content_level")
    private String explicitContentLevel;
    @SerializedName("explicit_content_levels_available")
    private List<String> availableExplicitContentLevels;
    @JsonAdapter(URLTypeAdapter.class)
    private URL tracklist;

    public Long getId() {
        return this.id;
    }

    public User setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getLastName() {
        return this.lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getEmail() {
        return this.email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public Integer getStatus() {
        return this.status;
    }

    public User setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Date getBirthday() {
        return this.birthday;
    }

    public User setBirthday(Date birthday) {
        this.birthday = birthday;
        return this;
    }

    public Date getInscriptionDate() {
        return this.inscriptionDate;
    }

    public User setInscriptionDate(Date inscriptionDate) {
        this.inscriptionDate = inscriptionDate;
        return this;
    }

    public Character getGender() {
        return this.gender;
    }

    public User setGender(Character gender) {
        this.gender = gender;
        return this;
    }

    public URL getLink() {
        return this.link;
    }

    public User setLink(URL link) {
        this.link = link;
        return this;
    }

    public URL getPicture() {
        return this.picture;
    }

    public User setPicture(URL picture) {
        this.picture = picture;
        return this;
    }

    public URL getSmallPicture() {
        return this.smallPicture;
    }

    public User setSmallPicture(URL smallPicture) {
        this.smallPicture = smallPicture;
        return this;
    }

    public URL getMediumPicture() {
        return this.mediumPicture;
    }

    public User setMediumPicture(URL mediumPicture) {
        this.mediumPicture = mediumPicture;
        return this;
    }

    public URL getBigPicture() {
        return this.bigPicture;
    }

    public User setBigPicture(URL bigPicture) {
        this.bigPicture = bigPicture;
        return this;
    }

    public URL getXlPicture() {
        return this.xlPicture;
    }

    public User setXlPicture(URL xlPicture) {
        this.xlPicture = xlPicture;
        return this;
    }

    public String getCountry() {
        return this.country;
    }

    public User setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getLanguage() {
        return this.language;
    }

    public User setLanguage(String language) {
        this.language = language;
        return this;
    }

    public Boolean isKid() {
        return this.isKid;
    }

    public User setKid(Boolean isKid) {
        this.isKid = isKid;
        return this;
    }

    public String getExplicitContentLevel() {
        return this.explicitContentLevel;
    }

    public User setExplicitContentLevel(String explicitContentLevel) {
        this.explicitContentLevel = explicitContentLevel;
        return this;
    }

    public List<String> getAvailableExplicitContentLevels() {
        return this.availableExplicitContentLevels;
    }

    public User setAvailableExplicitContentLevels(List<String> availableExplicitContentLevels) {
        this.availableExplicitContentLevels = availableExplicitContentLevels;
        return this;
    }

    public URL getTracklist() {
        return this.tracklist;
    }

    public User setTracklist(URL tracklist) {
        this.tracklist = tracklist;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "{", "}")
                .add("id=" + this.id)
                .add("name=" + this.name)
                .add("lastName=" + this.lastName)
                .add("firstName=" + this.firstName)
                .add("email=" + this.email)
                .add("status=" + this.status)
                .add("birthday=" + this.birthday)
                .add("inscriptionDate=" + this.inscriptionDate)
                .add("gender=" + this.gender)
                .add("link=" + this.link)
                .add("picture=" + this.picture)
                .add("smallPicture=" + this.smallPicture)
                .add("mediumPicture=" + this.mediumPicture)
                .add("bigPicture=" + this.bigPicture)
                .add("xlPicture=" + this.xlPicture)
                .add("country=" + this.country)
                .add("language=" + this.language)
                .add("isKid=" + this.isKid)
                .add("explicitContentLevel=" + this.explicitContentLevel)
                .add("availableExplicitContentLevels=" + this.availableExplicitContentLevels)
                .add("tracklist=" + this.tracklist)
                .toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (other == null || this.getClass() != other.getClass())
            return false;
        User user = (User) other;
        return  Objects.equals(this.id, user.id) &&
                Objects.equals(this.name, user.name) &&
                Objects.equals(this.lastName, user.lastName) &&
                Objects.equals(this.firstName, user.firstName) &&
                Objects.equals(this.email, user.email) &&
                Objects.equals(this.status, user.status) &&
                Objects.equals(this.birthday, user.birthday) &&
                Objects.equals(this.inscriptionDate, user.inscriptionDate) &&
                Objects.equals(this.gender, user.gender) &&
                Objects.equals(this.link, user.link) &&
                Objects.equals(this.picture, user.picture) &&
                Objects.equals(this.smallPicture, user.smallPicture) &&
                Objects.equals(this.mediumPicture, user.mediumPicture) &&
                Objects.equals(this.bigPicture, user.bigPicture) &&
                Objects.equals(this.xlPicture, user.xlPicture) &&
                Objects.equals(this.country, user.country) &&
                Objects.equals(this.language, user.language) &&
                Objects.equals(this.isKid, user.isKid) &&
                Objects.equals(this.explicitContentLevel, user.explicitContentLevel) &&
                Objects.equals(this.availableExplicitContentLevels, user.availableExplicitContentLevels) &&
                Objects.equals(this.tracklist, user.tracklist);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.lastName, this.firstName, this.email, this.status,
                            this.birthday, this.inscriptionDate, this.gender, this.link, this.picture,
                            this.smallPicture, this.mediumPicture, this.bigPicture, this.xlPicture, this.country,
                            this.language, this.isKid, this.explicitContentLevel, this.availableExplicitContentLevels,
                            this.tracklist);
    }

}
