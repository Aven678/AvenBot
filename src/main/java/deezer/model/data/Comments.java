package deezer.model.data;

import deezer.model.Comment;

import java.io.Serializable;
import java.util.StringJoiner;

public class Comments extends Data<Comment, Comments> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return new StringJoiner(", ", Comments.class.getSimpleName() + "{", "}")
                .add("data=" + this.data)
                .add("checksum=" + this.checksum)
                .add("total=" + this.total)
                .add("previousResults=" + this.previousResults)
                .add("nextResults=" + this.nextResults)
                .toString();
    }

}
