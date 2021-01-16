package deezer.model.data;

import deezer.model.Genre;

import java.io.Serializable;
import java.util.StringJoiner;

public class Genres extends Data<Genre, Genres> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return new StringJoiner(", ", Genres.class.getSimpleName() + "{", "}")
                .add("data=" + this.data)
                .add("checksum=" + this.checksum)
                .add("total=" + this.total)
                .add("previousResults=" + this.previousResults)
                .add("nextResults=" + this.nextResults)
                .toString();
    }

}
