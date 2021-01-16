package deezer.model.data;

import deezer.model.Podcast;

import java.io.Serializable;
import java.util.Collection;
import java.util.StringJoiner;

public class Podcasts extends Data<Podcast, Podcasts> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return new StringJoiner(", ", Podcasts.class.getSimpleName() + "{", "}")
                .add("data=" + this.data)
                .add("checksum=" + this.checksum)
                .add("total=" + this.total)
                .add("previousResults=" + this.previousResults)
                .add("nextResults=" + this.nextResults)
                .toString();
    }

}
