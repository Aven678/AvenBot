package fr.aven.bot.modules.music.lyrics;

import java.io.*;

/**
 * From QuickLyric - https://www.javatips.net/api/QuickLyric-master/QuickLyric/src/main/java/com/geecko/QuickLyric/model/Lyrics.java
 * Thanks Geecko too
 */

public class Lyrics implements Serializable {

    private String mTitle;
    private String mArtist;
    private String mOriginalTitle;
    private String mOriginalArtist;
    private String mSourceUrl;
    private String mCoverURL;
    private String mCopyright;
    private String mWriter;
    private String mText;
    private String mSource;
    private boolean mLRC = false;
    private final int mFlag;
    public static final int NO_RESULT = -2;
    public static final int NEGATIVE_RESULT = -1;
    public static final int POSITIVE_RESULT = 1;
    public static final int ERROR = -3;
    public static final int SEARCH_ITEM = 2;

    public Lyrics(int flag) {
        this.mFlag = flag;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getOriginalTrack() {
        if (mOriginalTitle != null)
            return mOriginalTitle;
        else
            return mTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.mOriginalTitle = originalTitle;
    }

    public String getArtist() {
        return mArtist;
    }

    public void setArtist(String artist) {
        this.mArtist = artist;
    }

    public String getOriginalArtist() {
        if (mOriginalArtist != null)
            return mOriginalArtist;
        else
            return mArtist;
    }

    public void setOriginalArtist(String originalArtist) {
        this.mOriginalArtist = originalArtist;
    }

    public String getURL() {
        return mSourceUrl;
    }

    public void setURL(String uRL) {
        this.mSourceUrl = uRL;
    }

    public String getCoverURL() {
        return mCoverURL;
    }

    public void setCoverURL(String coverURL) {
        this.mCoverURL = coverURL;
    }

    public String getCopyright() {
        return mCopyright;
    }

    public void setCopyright(String copyright) {
        this.mCopyright = copyright;
    }

    public String getWriter() {
        return this.mWriter;
    }

    public void setWriter(String writer) {
        this.mWriter = writer;
    }

    public String getText() {
        return mText;
    }

    public void setText(String lyrics) {
        this.mText = lyrics;
    }

    public String getSource() {
        return mSource;
    }

    public int getFlag() {
        return mFlag;
    }

    public void setSource(String mSource) {
        this.mSource = mSource;
    }

    public void setLRC(boolean LRC) {
        this.mLRC = LRC;
    }

    public boolean isLRC() {
        return this.mLRC;
    }

    public byte[] toBytes() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutput out = new ObjectOutputStream(bos);
            out.writeObject(this);
            out.close();
        } finally {
            bos.close();
        }
        return bos.toByteArray();
    }

    public static Lyrics fromBytes(byte[] data) throws IOException, ClassNotFoundException {
        if (data == null)
            return null;
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        return (Lyrics) is.readObject();
    }

    @Override
    public boolean equals(Object object) {
        boolean isLyrics = object instanceof Lyrics;
        if (isLyrics && (this.getURL() != null) && ((Lyrics) object).getURL() != null)
            return this.getURL().equals(((Lyrics) object).getURL());
        else if (isLyrics) {
            Lyrics other = (Lyrics) object;
            boolean result = this.getText().equals(other.getText());
            result &= this.getFlag() == other.getFlag();
            result &= this.getSource().equals(other.getSource());
            result &= this.getArtist().equals(other.getArtist());
            result &= this.getTitle().equals(other.getTitle());
            return result;
        }
        else
            return false;
    }

    @Override
    public int hashCode() {
        // Potential issue with the Birthday Paradox when we hash over 50k lyrics
        return this.getURL() != null ? this.getURL().hashCode() :
                (""+this.getOriginalArtist()+this.getOriginalTrack()+this.getSource()).hashCode();
    }
}






