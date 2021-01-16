package deezer.client;

import java.util.Objects;
import java.util.StringJoiner;

public class DeezerClientException extends RuntimeException {

    public static final int QUOTA = 4;
    public static final int ITEMS_LIMIT_EXCEEDED = 100;
    public static final int PERMISSION = 200;
    public static final int TOKEN_INVALID = 300;
    public static final int PARAMETER = 500;
    public static final int PARAMETER_MISSING = 501;
    public static final int QUERY_INVALID = 600;
    public static final int SERVICE_BUSY = 700;
    public static final int DATA_NOT_FOUND = 800;

    private String requestedUrl;
    private Error error;

    public DeezerClientException(String requestedUrl) {
        super();
        this.requestedUrl = requestedUrl;
    }

    public DeezerClientException(String requestedUrl, Error error) {
        this(requestedUrl);
        this.error = error;
    }

    public DeezerClientException(String requestedUrl, Throwable throwable) {
        super(throwable);
        this.requestedUrl = requestedUrl;
    }

    public String getRequestedUrl() {
        return this.requestedUrl;
    }

    public String getErrorType() {
        return this.error == null ? null : this.error.type;
    }

    public String getErrorMessage() {
        return this.error == null ? null : this.error.message;
    }

    public Integer getErrorCode() {
        return this.error == null ? null : this.error.code;
    }

    @Override
    public String getMessage() {
        if (this.requestedUrl == null)
            return super.getMessage();
        else if (this.error == null)
            return String.format("An exception occurred while trying to carry a request to URL: %s", this.requestedUrl);
        return String.format("An API error occurred while trying to carry a request to URL: %s. " +
                        "Received API error: %s (type: %s, code: %d)",
                this.requestedUrl, this.error.message, this.error.type, this.error.code);
    }

    public static class Error {

        String type;
        String message;
        Integer code;

        public String getType() {
            return this.type;
        }

        public Error setType(String type) {
            this.type = type;
            return this;
        }

        public String getMessage() {
            return this.message;
        }

        public Error setMessage(String message) {
            this.message = message;
            return this;
        }

        public Integer getCode() {
            return this.code;
        }

        public Error setCode(Integer code) {
            this.code = code;
            return this;
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", Error.class.getSimpleName() + "{", "}")
                    .add("type=" + this.type)
                    .add("message=" + this.message)
                    .add("code=" + this.code)
                    .toString();
        }

        @Override
        public boolean equals(Object other) {
            if (this == other)
                return true;
            if (other == null || this.getClass() != other.getClass())
                return false;
            Error error = (Error) other;
            return  Objects.equals(this.type, error.type) &&
                    Objects.equals(this.message, error.message) &&
                    Objects.equals(this.code, error.code);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.type, this.message, this.code);
        }

    }

}
