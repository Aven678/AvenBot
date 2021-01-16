package deezer.model;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import util.URLTypeAdapter;

import java.io.Serializable;
import java.net.URL;
import java.util.Objects;
import java.util.StringJoiner;

public class Offer implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private Float amount;
    private String currency;
    @SerializedName("displayed_amount")
    private String displayedAmount;
    @JsonAdapter(URLTypeAdapter.class)
    @SerializedName("tc")
    private URL termsAndConditions;
    @JsonAdapter(URLTypeAdapter.class)
    @SerializedName("tc_html")
    private URL htmlTermsAndConditions;
    @JsonAdapter(URLTypeAdapter.class)
    @SerializedName("tc_txt")
    private URL textTermsAndConditions;
    @SerializedName("try_and_buy")
    private Integer tryAndBuy;

    public long getId() {
        return this.id;
    }

    public Offer setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Offer setName(String name) {
        this.name = name;
        return this;
    }

    public String getCurrency() {
        return this.currency;
    }

    public Offer setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public String getDisplayedAmount() {
        return this.displayedAmount;
    }

    public Offer setDisplayedAmount(String displayedAmount) {
        this.displayedAmount = displayedAmount;
        return this;
    }

    public URL getTermsAndConditions() {
        return this.termsAndConditions;
    }

    public Offer setTermsAndConditions(URL termsAndConditions) {
        this.termsAndConditions = termsAndConditions;
        return this;
    }

    public URL getHtmlTermsAndConditions() {
        return this.htmlTermsAndConditions;
    }

    public Offer setHtmlTermsAndConditions(URL htmlTermsAndConditions) {
        this.htmlTermsAndConditions = htmlTermsAndConditions;
        return this;
    }

    public URL getTextTermsAndConditions() {
        return this.textTermsAndConditions;
    }

    public Offer setTextTermsAndConditions(URL textTermsAndConditions) {
        this.textTermsAndConditions = textTermsAndConditions;
        return this;
    }

    public Integer getTryAndBuy() {
        return this.tryAndBuy;
    }

    public Offer setTryAndBuy(Integer tryAndBuy) {
        this.tryAndBuy = tryAndBuy;
        return this;
    }

    public Float getAmount() {
        return this.amount;
    }

    public Offer setAmount(Float amount) {
        this.amount = amount;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Offer.class.getSimpleName() + "{", "}")
                .add("id=" + this.id)
                .add("name=" + this.name)
                .add("amount=" + this.amount)
                .add("currency=" + this.currency)
                .add("displayedAmount=" + this.displayedAmount)
                .add("termsAndConditions=" + this.termsAndConditions)
                .add("htmlTermsAndConditions=" + this.htmlTermsAndConditions)
                .add("textTermsAndConditions=" + this.textTermsAndConditions)
                .add("tryAndBuy=" + this.tryAndBuy)
                .toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (other == null || this.getClass() != other.getClass())
            return false;
        Offer offer = (Offer) other;
        return  Objects.equals(this.id, offer.id) &&
                Objects.equals(this.name, offer.name) &&
                Objects.equals(this.amount, offer.amount) &&
                Objects.equals(this.currency, offer.currency) &&
                Objects.equals(this.displayedAmount, offer.displayedAmount) &&
                Objects.equals(this.termsAndConditions, offer.termsAndConditions) &&
                Objects.equals(this.htmlTermsAndConditions, offer.htmlTermsAndConditions) &&
                Objects.equals(this.textTermsAndConditions, offer.textTermsAndConditions) &&
                Objects.equals(this.tryAndBuy, offer.tryAndBuy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.amount, this.currency, this.displayedAmount,
                            this.termsAndConditions, this.htmlTermsAndConditions, this.textTermsAndConditions,
                            this.tryAndBuy);
    }

}
