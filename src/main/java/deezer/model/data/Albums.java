package deezer.model.data;

import deezer.model.Album;

import java.io.Serializable;
import java.util.StringJoiner;

public class Albums extends Data<Album, Albums> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return new StringJoiner(", ", Albums.class.getSimpleName() + "{", "}")
                .add("data=" + this.data)
                .add("checksum=" + this.checksum)
                .add("total=" + this.total)
                .add("previousResults=" + this.previousResults)
                .add("nextResults=" + this.nextResults)
                .toString();
    }

}
