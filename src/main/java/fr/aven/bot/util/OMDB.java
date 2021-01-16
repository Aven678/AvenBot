package fr.aven.bot.util;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.apache.hc.core5.util.TextUtils;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class OMDB {
    private static final String apiUrl = "http://www.omdbapi.com";

    private static API apiClient;

    public OMDB() {
        // http://square.github.io/retrofit/
        // http://www.omdbapi.com/
        apiClient = getClient();
    }

    public static API getClient() {
        if (apiClient == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC);//BODY);
            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(apiUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient)
                    .build();
            apiClient = retrofit.create(API.class);
        }
        return apiClient;
    }

    public OMDBMovie findMovie(String text) throws IOException {
        Response<OMDBMovie> res = apiClient.findMovie(text).execute();
        if (!res.isSuccessful())
            throw new IOException(res.errorBody().string());
        return res.body();
    }

    public OMDBMovie getMovie(String imdb_id) throws IOException {
        Response<OMDBMovie> res = apiClient.getMovie(imdb_id).execute();
        if (!res.isSuccessful())
            throw new IOException(res.errorBody().string());
        return res.body();
    }

    private interface API {
        @GET("/?apikey=f1752697&r=json&tomatoes=true")
        Call<OMDBMovie> findMovie(@Query("t") String text);

        @GET("/?apikey=f1752697&type=movie&r=json&plot=full&tomatoes=true")
        Call<OMDBMovie> getMovie(@Query("i") String imdb_id);
    }

    public static class MovieData {
        public String imdbID;
        public String Title;
        public String Year;
        public String Rated;
        public String Released;
        public String Runtime;
        public String Genre;
        public String Director;
        public String Writer;
        public String Actors;
        public String Plot;
        public String Language;
        public String Country;
        public String Awards;
        public String Poster;
        public String Metascore;
        public String imdbRating;
        public String imdbVotes;
        public String tomatoMeter;
        public String tomatoImage;
        public String tomatoRating;
        public String tomatoReviews;
        public String tomatoFresh;
        public String tomatoRotten;
        public String tomatoConsensus;
        public String tomatoUserMeter;
        public String tomatoUserRating;
        public String tomatoUserReviews;
        public String tomatoURL;
        public String DVD;
        public String BoxOffice;
        public String Production;
        public String Website;
    }

    public static class OMDBMovies {
        public MovieData[] Search;
        public String Response; // True | False
        public String Error;

        public boolean success() {
            return Response != null && Response.equals("True");
        }

        public String error() {
            return success() ? null : TextUtils.isEmpty(Error) ? "Unknown error" : Error;
        }
    }

    public static class OMDBMovie extends MovieData {
        public String Response; // True | False
        public String Error;
        public String Type;

        public boolean success() {
            return Response != null && Response.equals("True") && Type != null && (Type.equals("movie") || Type.equals("series"));
        }

        public String error() {
            return success() ? null : TextUtils.isEmpty(Error) ? "Unknown error" : Error;
        }
    }
}






