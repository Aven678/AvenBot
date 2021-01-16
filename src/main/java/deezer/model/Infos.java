package deezer.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class Infos implements Serializable {

    private static final long serialVersionUID = 1L;

    @SerializedName("country_iso")
    private String countryIso;
    private String country;
    @SerializedName("open")
    private Boolean isOpen;
    private List<Offer> offers;

    public String getCountryIso() {
        return this.countryIso;
    }

    public Infos setCountryIso(String countryIso) {
        this.countryIso = countryIso;
        return this;
    }

    public String getCountry() {
        return this.country;
    }

    public Infos setCountry(String country) {
        this.country = country;
        return this;
    }

    public Boolean isOpen() {
        return this.isOpen;
    }

    public Infos setOpen(Boolean open) {
        isOpen = open;
        return this;
    }

    public List<Offer> getOffers() {
        return this.offers;
    }

    public Infos setOffers(List<Offer> offers) {
        this.offers = offers;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Infos.class.getSimpleName() + "{", "}")
                .add("countryIso=" + this.countryIso)
                .add("country=" + this.country)
                .add("isOpen=" + this.isOpen)
                .add("offers=" + this.offers)
                .toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (other == null || this.getClass() != other.getClass())
            return false;
        Infos infos = (Infos) other;
        return  Objects.equals(this.countryIso, infos.countryIso) &&
                Objects.equals(this.country, infos.country) &&
                Objects.equals(this.isOpen, infos.isOpen) &&
                Objects.equals(this.offers, infos.offers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.countryIso, this.country, this.isOpen, this.offers);
    }

}
