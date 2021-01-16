package deezer.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

public class Options implements Serializable {

    private static final long serialVersionUID = 1L;

    @SerializedName("stream")
    private Boolean canStream;
    @SerializedName("streaming_duration")
    private Integer streamingDuration;
    @SerializedName("offline")
    private Boolean canUseOfflineMode;
    @SerializedName("hq")
    private Boolean canActivateHq;
    @SerializedName("ads_display")
    private Boolean displaysAds;
    @SerializedName("ads_audio")
    private Boolean activatesAudioAds;
    @SerializedName("too_many_devices")
    private Boolean tooManyDevices;
    @SerializedName("can_subscribe")
    private Boolean canSubscribe;
    @SerializedName("radio_skips")
    private Integer radioSkips;
    @SerializedName("lossless")
    private Boolean isLosslessAvailable;
    @SerializedName("preview")
    private Boolean canDisplayPreviews;
    @SerializedName("radio")
    private Boolean canStreamRadio;

    public Boolean getCanStream() {
        return this.canStream;
    }

    public Options setCanStream(Boolean canStream) {
        this.canStream = canStream;
        return this;
    }

    public Integer getStreamingDuration() {
        return this.streamingDuration;
    }

    public Options setStreamingDuration(Integer streamingDuration) {
        this.streamingDuration = streamingDuration;
        return this;
    }

    public Boolean getCanUseOfflineMode() {
        return this.canUseOfflineMode;
    }

    public Options setCanUseOfflineMode(Boolean canUseOfflineMode) {
        this.canUseOfflineMode = canUseOfflineMode;
        return this;
    }

    public Boolean getCanActivateHq() {
        return this.canActivateHq;
    }

    public Options setCanActivateHq(Boolean canActivateHq) {
        this.canActivateHq = canActivateHq;
        return this;
    }

    public Boolean getDisplaysAds() {
        return this.displaysAds;
    }

    public Options setDisplaysAds(Boolean displaysAds) {
        this.displaysAds = displaysAds;
        return this;
    }

    public Boolean getActivatesAudioAds() {
        return this.activatesAudioAds;
    }

    public Options setActivatesAudioAds(Boolean activatesAudioAds) {
        this.activatesAudioAds = activatesAudioAds;
        return this;
    }

    public Boolean getTooManyDevices() {
        return this.tooManyDevices;
    }

    public Options setTooManyDevices(Boolean tooManyDevices) {
        this.tooManyDevices = tooManyDevices;
        return this;
    }

    public Boolean getCanSubscribe() {
        return this.canSubscribe;
    }

    public Options setCanSubscribe(Boolean canSubscribe) {
        this.canSubscribe = canSubscribe;
        return this;
    }

    public Integer getRadioSkips() {
        return this.radioSkips;
    }

    public Options setRadioSkips(Integer radioSkips) {
        this.radioSkips = radioSkips;
        return this;
    }

    public Boolean getIsLosslessAvailable() {
        return this.isLosslessAvailable;
    }

    public Options setIsLosslessAvailable(Boolean isLosslessAvailable) {
        this.isLosslessAvailable = isLosslessAvailable;
        return this;
    }

    public Boolean getCanDisplayPreviews() {
        return this.canDisplayPreviews;
    }

    public Options setCanDisplayPreview(Boolean canDisplayPreviews) {
        this.canDisplayPreviews = canDisplayPreviews;
        return this;
    }

    public Boolean getCanStreamRadio() {
        return this.canStreamRadio;
    }

    public Options setCanStreamRadio(Boolean canStreamRadio) {
        this.canStreamRadio = canStreamRadio;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Options.class.getSimpleName() + "{", "}")
                .add("canStream=" + this.canStream)
                .add("streamingDuration=" + this.streamingDuration)
                .add("canUseOfflineMode=" + this.canUseOfflineMode)
                .add("canActivateHq=" + this.canActivateHq)
                .add("displaysAds=" + this.displaysAds)
                .add("activatesAudioAds=" + this.activatesAudioAds)
                .add("tooManyDevices=" + this.tooManyDevices)
                .add("canSubscribe=" + this.canSubscribe)
                .add("radioSkips=" + this.radioSkips)
                .add("isLosslessAvailable=" + this.isLosslessAvailable)
                .add("canDisplayPreviews=" + this.canDisplayPreviews)
                .add("canStreamRadio=" + this.canStreamRadio)
                .toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (other == null || this.getClass() != other.getClass())
            return false;
        Options options = (Options) other;
        return  Objects.equals(this.canStream, options.canStream) &&
                Objects.equals(this.streamingDuration, options.streamingDuration) &&
                Objects.equals(this.canUseOfflineMode, options.canUseOfflineMode) &&
                Objects.equals(this.canActivateHq, options.canActivateHq) &&
                Objects.equals(this.displaysAds, options.displaysAds) &&
                Objects.equals(this.activatesAudioAds, options.activatesAudioAds) &&
                Objects.equals(this.tooManyDevices, options.tooManyDevices) &&
                Objects.equals(this.canSubscribe, options.canSubscribe) &&
                Objects.equals(this.radioSkips, options.radioSkips) &&
                Objects.equals(this.isLosslessAvailable, options.isLosslessAvailable) &&
                Objects.equals(this.canDisplayPreviews, options.canDisplayPreviews) &&
                Objects.equals(this.canStreamRadio, options.canStreamRadio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.canStream, this.streamingDuration, this.canUseOfflineMode, this.canActivateHq,
                            this.displaysAds, this.activatesAudioAds, this.tooManyDevices, this.canSubscribe,
                            this.radioSkips, this.isLosslessAvailable, this.canDisplayPreviews, this.canStreamRadio);
    }

}
