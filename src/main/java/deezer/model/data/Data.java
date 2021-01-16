package deezer.model.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.net.URL;
import java.util.*;
import java.util.function.Consumer;

public abstract class Data<E, T extends Data<E, T>> implements Serializable, Iterable<E> {

    private static final long serialVersionUID = 1L;

    List<E> data;
    String checksum;
    Integer total;
    @SerializedName("prev")
    URL previousResults;
    @SerializedName("next")
    URL nextResults;

    public List<E> getData() {
        return this.data;
    }

    public Data<E, T> setData(List<E> data) {
        this.data = data;
        return this;
    }

    public String getChecksum() {
        return this.checksum;
    }

    public T setChecksum(String checksum) {
        this.checksum = checksum;
        return (T) this;
    }

    public Integer getTotal() {
        return this.total;
    }

    public T setTotal(Integer total) {
        this.total = total;
        return (T) this;
    }

    public URL getPreviousResults() {
        return this.previousResults;
    }

    public T setPreviousResults(URL previousResults) {
        this.previousResults = previousResults;
        return (T) this;
    }

    public URL getNextResults() {
        return this.nextResults;
    }

    public T setNextResults(URL nextResults) {
        this.nextResults = nextResults;
        return (T) this;
    }

    public boolean isEmpty() {
        return this.data == null || this.data.isEmpty();
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        if (this.data != null)
            this.data.forEach(action);
    }

    @Override
    public Iterator<E> iterator() {
        return this.data == null ? Collections.emptyIterator() : this.data.iterator();
    }

    @Override
    public Spliterator<E> spliterator() {
        return this.data == null ? Spliterators.emptySpliterator() : this.data.spliterator();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Data.class.getSimpleName() + "{", "}")
                .add("data=" + this.data)
                .add("checksum=" + this.checksum)
                .add("total=" + this.total)
                .add("previousResults=" + this.previousResults)
                .add("nextResults=" + this.nextResults)
                .toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (other == null || this.getClass() != other.getClass())
            return false;
        Data<?, ?> data = (Data<?, ?>) other;
        return  Objects.equals(this.data, data.data) &&
                Objects.equals(this.checksum, data.checksum) &&
                Objects.equals(this.total, data.total) &&
                Objects.equals(this.previousResults, data.previousResults) &&
                Objects.equals(this.nextResults, data.nextResults);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.data, this.checksum, this.total, this.previousResults, this.nextResults);
    }

}
