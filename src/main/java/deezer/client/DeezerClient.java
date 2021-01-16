package deezer.client;

import deezer.model.*;
import deezer.model.data.*;
import deezer.model.search.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class DeezerClient {

    public static final String API_OBJECT_ALBUM = "album";
    public static final String API_OBJECT_ARTIST = "artist";
    public static final String API_OBJECT_CHART = "chart";
    public static final String API_OBJECT_COMMENT = "comment";
    public static final String API_OBJECT_EDITORIAL = "editorial";
    public static final String API_OBJECT_GENRE = "genre";
    public static final String API_OBJECT_INFOS = "infos";
    public static final String API_OBJECT_OPTIONS = "options";
    public static final String API_OBJECT_PLAYLIST = "playlist";
    public static final String API_OBJECT_RADIO = "radio";
    public static final String API_OBJECT_SEARCH = "search";
    public static final String API_OBJECT_TRACK = "track";
    public static final String API_OBJECT_USER = "user";

    private DeezerRequestsCarrier requestsCarrier;

    public DeezerClient() {
        this.requestsCarrier = new DeezerRequestsCarrier();
    }

    /*
        Album API object methods
     */

    public Album getAlbum(final long albumId) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_ALBUM, albumId, Album.class);
    }

    public Comments getAlbumComments(final long albumId) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_ALBUM, albumId, "comments", Comments.class);
    }

    public Comments getAlbumComments(final long albumId, final int index) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_ALBUM, albumId, "comments", index, Comments.class);
    }

    public Comments getAlbumComments(final long albumId, final int index, final int limit) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_ALBUM, albumId, "comments", index, limit, Comments.class);
    }

    public Users getAlbumFans(final long albumId) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_ALBUM, albumId, "fans", Users.class);
    }

    public Users getAlbumFans(final long albumId, final int index) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_ALBUM, albumId, "fans", index, Users.class);
    }

    public Users getAlbumFans(final long albumId, final int index, final int limit) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_ALBUM, albumId, "fans", index, limit, Users.class);
    }

    public Tracks getAlbumTracks(final long albumId) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_ALBUM, albumId, "tracks", Tracks.class);
    }

    public Tracks getAlbumTracks(final long albumId, final int index) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_ALBUM, albumId, "tracks", index, Tracks.class);
    }

    public Tracks getAlbumTracks(final long albumId, final int index, final int limit) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_ALBUM, albumId, "tracks", index, limit, Tracks.class);
    }

    /*
        Artist API object methods
     */

    public Artist getArtist(final long artistId) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_ARTIST, artistId, Artist.class);
    }

    public Tracks getArtistTopTracks(final long artistId) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_ARTIST, artistId, "top", Tracks.class);
    }

    public Tracks getArtistTopTracks(final long artistId, final int index) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_ARTIST, artistId, "top", index, Tracks.class);
    }

    public Tracks getArtistTopTracks(final long artistId, final int index, final int limit) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_ARTIST, artistId, "top", index, limit, Tracks.class);
    }

    public Albums getArtistAlbums(final long artistId) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_ARTIST, artistId, "albums", Albums.class);
    }

    public Albums getArtistAlbums(final long artistId, final int index) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_ARTIST, artistId, "albums", index, Albums.class);
    }

    public Albums getArtistAlbums(final long artistId, final int index, final int limit) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_ARTIST, artistId, "albums", index, limit, Albums.class);
    }

    public Comments getArtistComments(final long artistId) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_ARTIST, artistId, "comments", Comments.class);
    }

    public Comments getArtistComments(final long artistId, final int index) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_ARTIST, artistId, "comments", index, Comments.class);
    }

    public Comments getArtistComments(final long artistId, final int index, final int limit) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_ARTIST, artistId, "comments", index, limit, Comments.class);
    }

    public Users getArtistFans(final long artistId) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_ARTIST, artistId, "fans", Users.class);
    }

    public Users getArtistFans(final long artistId, final int index) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_ARTIST, artistId, "fans", index, Users.class);
    }

    public Users getArtistFans(final long artistId, final int index, final int limit) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_ARTIST, artistId, "fans", index, limit, Users.class);
    }

    public Artists getRelatedArtists(final long artistId) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_ARTIST, artistId, "related", Artists.class);
    }

    public Artists getRelatedArtists(final long artistId, final int index) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_ARTIST, artistId, "related", index, Artists.class);
    }

    public Artists getRelatedArtists(final long artistId, final int index, final int limit) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_ARTIST, artistId, "related", index, limit, Artists.class);
    }

    public Tracks getArtistRadio(final long artistId) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_ARTIST, artistId, "radio", Tracks.class);
    }

    public Tracks getArtistRadio(final long artistId, final int index) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_ARTIST, artistId, "radio", index, Tracks.class);
    }

    public Tracks getArtistRadio(final long artistId, final int index, final int limit) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_ARTIST, artistId, "radio", index, limit, Tracks.class);
    }

    public Playlists getArtistPlaylists(final long artistId) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_ARTIST, artistId, "playlists", Playlists.class);
    }

    public Playlists getArtistPlaylists(final long artistId, final int index) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_ARTIST, artistId, "playlists", index, Playlists.class);
    }

    public Playlists getArtistPlaylists(final long artistId, final int index, final int limit) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_ARTIST, artistId, "playlists", index, limit, Playlists.class);
    }

    /*
        Chart API object methods
     */

    public Chart getChart() {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_CHART, Chart.class);
    }

    public Chart getChart(final int index) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_CHART, index, Chart.class);
    }

    public Chart getChart(final int index, final int limit) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_CHART, index, limit, Chart.class);
    }

    public Chart getChart(final long chartId) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_CHART, chartId, Chart.class);
    }

    public Chart getChart(final long chartId, final int index) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_CHART, chartId, index, Chart.class);
    }

    public Chart getChart(final long chartId, final int index, final int limit) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_CHART, chartId, index, limit, Chart.class);
    }

    public Tracks getTopTracks() {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_CHART + "/0", "top", Tracks.class);
    }

    public Tracks getTopTracks(final int index) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_CHART + "/0", "top", index, Tracks.class);
    }

    public Tracks getTopTracks(final int index, final int limit) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_CHART + "/0", "top", index, limit, Tracks.class);
    }

    public Tracks getTopTracks(final long chartId) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_CHART + "/" + chartId, "top", Tracks.class);
    }

    public Tracks getTopTracks(final long chartId, final int index) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_CHART + "/" + chartId, "top", index, Tracks.class);
    }

    public Tracks getTopTracks(final long chartId, final int index, final int limit) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_CHART + "/" + chartId, "top", index, limit, Tracks.class);
    }

    public Albums getTopAlbums() {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_CHART + "/0", "albums", Albums.class);
    }

    public Albums getTopAlbums(final int index) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_CHART + "/0", "albums", index, Albums.class);
    }

    public Albums getTopAlbums(final int index, final int limit) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_CHART + "/0", "albums", index, limit, Albums.class);
    }

    public Albums getTopAlbums(final long chartId) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_CHART + "/" + chartId, "albums", Albums.class);
    }

    public Albums getTopAlbums(final long chartId, final int index) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_CHART + "/" + chartId, "albums", index, Albums.class);
    }

    public Albums getTopAlbums(final long chartId, final int index, final int limit) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_CHART + "/" + chartId, "albums", index, limit, Albums.class);
    }

    public Artists getTopArtists() {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_CHART + "/0", "artists", Artists.class);
    }

    public Artists getTopArtists(final int index) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_CHART + "/0", "artists", index, Artists.class);
    }

    public Artists getTopArtists(final int index, final int limit) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_CHART + "/0", "artists", index, limit, Artists.class);
    }

    public Artists getTopArtists(final long chartId) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_CHART + "/" + chartId, "artists", Artists.class);
    }

    public Artists getTopArtists(final long chartId, final int index) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_CHART + "/" + chartId, "artists", index, Artists.class);
    }

    public Artists getTopArtists(final long chartId, final int index, final int limit) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_CHART + "/" + chartId, "artists", index, limit, Artists.class);
    }

    public Playlists getTopPlaylists() {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_CHART + "/0", "playlists", Playlists.class);
    }

    public Playlists getTopPlaylists(final int index) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_CHART + "/0", "playlists", index, Playlists.class);
    }

    public Playlists getTopPlaylists(final int index, final int limit) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_CHART + "/0", "playlists", index, limit, Playlists.class);
    }

    public Playlists getTopPlaylists(final long chartId) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_CHART + "/" + chartId, "playlists", Playlists.class);
    }

    public Playlists getTopPlaylists(final long chartId, final int index) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_CHART + "/" + chartId, "playlists", index, Playlists.class);
    }

    public Playlists getTopPlaylists(final long chartId, final int index, final int limit) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_CHART + "/" + chartId, "playlists", index, limit, Playlists.class);
    }

    public Podcasts getTopPodcasts() {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_CHART + "/0", "podcasts", Podcasts.class);
    }

    public Podcasts getTopPodcasts(final int index) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_CHART + "/0", "podcasts", index, Podcasts.class);
    }

    public Podcasts getTopPodcasts(final int index, final int limit) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_CHART + "/0", "podcasts", index, limit, Podcasts.class);
    }

    public Podcasts getTopPodcasts(final long chartId) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_CHART + "/" + chartId, "podcasts", Podcasts.class);
    }

    public Podcasts getTopPodcasts(final long chartId, final int index) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_CHART + "/" + chartId, "podcasts", index, Podcasts.class);
    }

    public Podcasts getTopPodcasts(final long chartId, final int index, final int limit) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_CHART + "/" + chartId, "podcasts", index, limit, Podcasts.class);
    }

    /*
        Comment API object methods
     */

    public Comment getComment(final long commentId) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_COMMENT, commentId, Comment.class);
    }

    /*
        Editorial API object methods
     */

    public Editorial getEditorial(final long editorialId) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_EDITORIAL, editorialId, Editorial.class);
    }

    public Editorials getEditorials() {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_EDITORIAL, Editorials.class);
    }

    public Editorials getEditorials(final int index) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_EDITORIAL, index, Editorials.class);
    }

    public Editorials getEditorials(final int index, final int limit) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_EDITORIAL, index, limit, Editorials.class);
    }

    /*
        Genre API object methods
     */

    public Genre getGenre(final long genreId) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_GENRE, genreId, Genre.class);
    }

    public Artists getGenreArtists(final long genreId) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_GENRE, genreId, "artists", Artists.class);
    }

    public Radios getGenreRadios(final long genreId) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_GENRE, genreId, "radios", Radios.class);
    }

    /*
        Infos API object methods
     */

    public Infos getInfos() {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_INFOS, Infos.class);
    }

    /*
        Options API object methods
     */

    public Options getOptions() {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_OPTIONS, Options.class);
    }

    /*
        Playlist API object methods
     */

    public Playlist getPlaylist(final long playlistId) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_PLAYLIST, playlistId, Playlist.class);
    }

    public Comments getPlaylistComments(final long playlistId) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_PLAYLIST, playlistId, "comments", Comments.class);
    }

    public Comments getPlaylistComments(final long playlistId, final int index) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_PLAYLIST, playlistId, "comments", index, Comments.class);
    }

    public Comments getPlaylistComments(final long playlistId, final int index, final int limit) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_PLAYLIST, playlistId, "comments", index, limit, Comments.class);
    }

    public Users getPlaylistFans(final long playlistId) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_PLAYLIST, playlistId, "fans", Users.class);
    }

    public Users getPlaylistFans(final long playlistId, final int index) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_PLAYLIST, playlistId, "fans", index, Users.class);
    }

    public Users getPlaylistFans(final long playlistId, final int index, final int limit) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_PLAYLIST, playlistId, "fans", index, limit, Users.class);
    }

    public Tracks getPlaylistTracks(final long playlistId) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_PLAYLIST, playlistId, "tracks", Tracks.class);
    }

    public Tracks getPlaylistTracks(final long playlistId, final int index) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_PLAYLIST, playlistId, "tracks", index, Tracks.class);
    }

    public Tracks getPlaylistTracks(final long playlistId, final int index, final int limit) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_PLAYLIST, playlistId, "tracks", index, limit, Tracks.class);
    }

    public Tracks getPlaylistRadio(final long playlistId) { // Seems to always return "false"
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_PLAYLIST, playlistId, "radio", Tracks.class);
    }

    public Tracks getPlaylistRadio(final long playlistId, final int index) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_PLAYLIST, playlistId, "radio", index, Tracks.class);
    }

    public Tracks getPlaylistRadio(final long playlistId, final int index, final int limit) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_PLAYLIST, playlistId, "radio", index, limit, Tracks.class);
    }

    /*
        Radio API object methods
     */

    public Radio getRadio(final long radioId) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_RADIO, radioId, Radio.class);
    }

    public Radios getRadios() {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_RADIO, Radios.class);
    }

    public Radios getGenresRadios() {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_RADIO, "genres", Radios.class);
    }

    public Radios getTopRadios() {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_RADIO, "top", Radios.class);
    }

    public Radios getTopRadios(final int index) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_RADIO, "top", index, Radios.class);
    }

    public Radios getTopRadios(final int index, final int limit) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_RADIO, "top", index, limit, Radios.class);
    }

    public Tracks getRadioTracks(final long radioId) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_RADIO, radioId, "tracks", Tracks.class);
    }

    public Tracks getRadioTracks(final long radioId, final int index) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_RADIO, radioId, "tracks", index, Tracks.class);
    }

    public Tracks getRadioTracks(final long radioId, final int index, final int limit) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_RADIO, radioId, "tracks", index, limit, Tracks.class);
    }

    public Radios getRadioLists() {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_RADIO, "lists", Radios.class);
    }

    public Radios getRadioLists(final int index) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_RADIO, "lists", index, Radios.class);
    }

    public Radios getRadioLists(final int index, final int limit) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_RADIO, "lists", index, limit, Radios.class);
    }

    /*
        Search API object methods
     */

    public Albums getAlbumsSearchResults(final AlbumsSearch albumsSearch) {
        final String searchQuery = this.buildSearchQuery(albumsSearch);
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_SEARCH, "album", searchQuery, Albums.class);
    }

    public Albums getAlbumsSearchResults(final AlbumsSearch albumsSearch, final int index) {
        final String searchQuery = this.buildSearchQuery(albumsSearch);
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_SEARCH, "album", searchQuery, index, Albums.class);
    }

    public Albums getAlbumsSearchResults(final AlbumsSearch albumsSearch, final int index, final int limit) {
        final String searchQuery = this.buildSearchQuery(albumsSearch);
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_SEARCH, "album", searchQuery, index, limit, Albums.class);
    }

    public Artists getArtistsSearchResults(final ArtistsSearch searchArtists) {
        final String searchQuery = this.buildSearchQuery(searchArtists);
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_SEARCH, "artist", searchQuery, Artists.class);
    }

    public Artists getArtistsSearchResults(final ArtistsSearch searchArtists, final int index) {
        final String searchQuery = this.buildSearchQuery(searchArtists);
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_SEARCH, "artist", searchQuery, index, Artists.class);
    }

    public Artists getArtistsSearchResults(final ArtistsSearch searchArtists, final int index, final int limit) {
        final String searchQuery = this.buildSearchQuery(searchArtists);
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_SEARCH, "artist", searchQuery, index, limit, Artists.class);
    }

    public Playlists getPlaylistsSearchResults(final PlaylistsSearch playlistsSearch) {
        final String searchQuery = this.buildSearchQuery(playlistsSearch);
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_SEARCH, "playlist", searchQuery, Playlists.class);
    }

    public Playlists getPlaylistsSearchResults(final PlaylistsSearch playlistsSearch, final int index) {
        final String searchQuery = this.buildSearchQuery(playlistsSearch);
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_SEARCH, "playlist", searchQuery, index, Playlists.class);
    }

    public Playlists getPlaylistsSearchResults(final PlaylistsSearch playlistsSearch, final int index, final int limit) {
        final String searchQuery = this.buildSearchQuery(playlistsSearch);
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_SEARCH, "playlist", searchQuery, index, limit, Playlists.class);
    }

    public Radios getRadiosSearchResults(final PlaylistsSearch radiosSearch) {
        final String searchQuery = this.buildSearchQuery(radiosSearch);
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_SEARCH, "radio", searchQuery, Radios.class);
    }

    public Radios getRadiosSearchResults(final PlaylistsSearch radiosSearch, final int index) {
        final String searchQuery = this.buildSearchQuery(radiosSearch);
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_SEARCH, "radio", searchQuery, index, Radios.class);
    }

    public Radios getRadiosSearchResults(final PlaylistsSearch radiosSearch, final int index, final int limit) {
        final String searchQuery = this.buildSearchQuery(radiosSearch);
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_SEARCH, "radio", searchQuery, index, limit, Radios.class);
    }

    public Tracks getTracksSearchResults(final TracksSearch searchTracks) {
        final String searchQuery = this.buildSearchQuery(searchTracks);
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_SEARCH, "track", searchQuery, Tracks.class);
    }

    public Tracks getTracksSearchResults(final TracksSearch searchTracks, final int index) {
        final String searchQuery = this.buildSearchQuery(searchTracks);
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_SEARCH, "track", searchQuery, index, Tracks.class);
    }

    public Tracks getTracksSearchResults(final TracksSearch searchTracks, final int index, final int limit) {
        final String searchQuery = this.buildSearchQuery(searchTracks);
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_SEARCH, "track", searchQuery, index, limit, Tracks.class);
    }

    public Users getUsersSearchResults(final UsersSearch searchUsers) {
        final String searchQuery = this.buildSearchQuery(searchUsers);
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_SEARCH, "user", searchQuery, Users.class);
    }

    public Users getUsersSearchResults(final UsersSearch searchUsers, final int index) {
        final String searchQuery = this.buildSearchQuery(searchUsers);
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_SEARCH, "user", searchQuery, index, Users.class);
    }

    public Users getUsersSearchResults(final UsersSearch searchUsers, final int index, final int limit) {
        final String searchQuery = this.buildSearchQuery(searchUsers);
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_SEARCH, "user", searchQuery, index, limit, Users.class);
    }

    private String buildSearchQuery(final Search search) {
        StringBuilder queryBuilder = new StringBuilder();
        try {
            if (search.getQuery() != null)
                queryBuilder.append(URLEncoder.encode(search.getQuery(), StandardCharsets.UTF_8.toString()));
            if (search instanceof TracksSearch) {                           // This is really dirty and the entire
                final TracksSearch searchTracks = (TracksSearch) search;    // search section should get redesigned
                if (searchTracks.getArtistQuery() != null)
                    queryBuilder.append(String.format("+artist:\"%s\"",
                            URLEncoder.encode(searchTracks.getArtistQuery(), StandardCharsets.UTF_8.toString())));
                if (searchTracks.getAlbumQuery() != null)
                    queryBuilder.append(String.format("+album:\"%s\"",
                            URLEncoder.encode(searchTracks.getAlbumQuery(), StandardCharsets.UTF_8.toString())));
                if (searchTracks.getTrackQuery() != null)
                    queryBuilder.append(String.format("+track:\"%s\"",
                            URLEncoder.encode(searchTracks.getTrackQuery(), StandardCharsets.UTF_8.toString())));
                if (searchTracks.getLabelQuery() != null)
                    queryBuilder.append(String.format("+label:\"%s\"",
                            URLEncoder.encode(searchTracks.getLabelQuery(), StandardCharsets.UTF_8.toString())));
                if (searchTracks.getMinDuration() != null)
                    queryBuilder.append(String.format("+dur_min:%d", searchTracks.getMinDuration()));
                if (searchTracks.getMaxDuration() != null)
                    queryBuilder.append(String.format("+dur_max:%d", searchTracks.getMaxDuration()));
                if (searchTracks.getMinBpm() != null)
                    queryBuilder.append(String.format("+bpm_min:%d", searchTracks.getMinBpm()));
                if (searchTracks.getMaxBpm() != null)
                    queryBuilder.append(String.format("+max_bpm:%d", searchTracks.getMaxBpm()));
                if (searchTracks.getOrder() != null)
                    queryBuilder.append(String.format("&order=%s", searchTracks.getOrder()));
            }
            if (search.isStrict())
                queryBuilder.append("&strict=on");
            return queryBuilder.toString();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /*
        Track API object methods
     */

    public Track getTrack(final long trackId) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_TRACK, trackId, Track.class);
    }

    // @todo implement folders, permissions, options, personal_songs, recommendations methods
    /*
        User API object methods
     */

    public User getUser(final long userId) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_USER, userId, User.class);
    }

    public Albums getUserAlbums(final long userId) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_USER, userId, "albums", Albums.class);
    }

    public Albums getUserAlbums(final long userId, final int index) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_USER, userId, "albums", index, Albums.class);
    }

    public Albums getUserAlbums(final long userId, final int index, final int limit) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_USER, userId, "albums", index, limit, Albums.class);
    }

    public Artists getUserArtists(final long userId) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_USER, userId, "artists", Artists.class);
    }

    public Artists getUserArtists(final long userId, final int index) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_USER, userId, "artists", index, Artists.class);
    }

    public Artists getUserArtists(final long userId, final int index, final int limit) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_USER, userId, "artists", index, limit, Artists.class);
    }

    public Albums getUserChartAlbums(final long userId) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_USER, userId, "charts/albums", Albums.class);
    }

    public Albums getUserChartAlbums(final long userId, final int index) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_USER, userId, "charts/albums", index, Albums.class);
    }

    public Albums getUserChartAlbums(final long userId, final int index, final int limit) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_USER, userId, "charts/albums", index, limit, Albums.class);
    }

    public Playlists getUserChartPlaylists(final long userId) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_USER, userId, "charts/playlists", Playlists.class);
    }

    public Playlists getUserChartPlaylists(final long userId, final int index) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_USER, userId, "charts/playlists", index, Playlists.class);
    }

    public Playlists getUserChartPlaylists(final long userId, final int index, final int limit) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_USER, userId, "charts/playlists", index, limit, Playlists.class);
    }

    public Tracks getUserChartTracks(final long userId) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_USER, userId, "charts/tracks", Tracks.class);
    }

    public Tracks getUserChartTracks(final long userId, final int index) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_USER, userId, "charts/tracks", index, Tracks.class);
    }

    public Tracks getUserChartTracks(final long userId, final int index, final int limit) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_USER, userId, "charts/tracks", index, limit, Tracks.class);
    }

    public Tracks getUserFlow(final long userId) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_USER, userId, "flow", Tracks.class);
    }

    public Users getUserFollowings(final long userId) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_USER, userId, "followings", Users.class);
    }

    public Users getUserFollowings(final long userId, final int index) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_USER, userId, "followings", index, Users.class);
    }

    public Users getUserFollowings(final long userId, final int index, final int limit) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_USER, userId, "followings", index, limit, Users.class);
    }

    public Users getUserFollowers(final long userId) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_USER, userId, "followers", Users.class);
    }

    public Users getUserFollowers(final long userId, final int index) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_USER, userId, "followers", index, Users.class);
    }

    public Users getUserFollowers(final long userId, final int index, final int limit) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_USER, userId, "followers", index, limit, Users.class);
    }

    public Tracks getUserHistory(final long userId) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_USER, userId, "history", Tracks.class);
    }

    public Playlists getUserPlaylists(final long userId) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_USER, userId, "playlists", Playlists.class);
    }

    public Playlists getUserPlaylists(final long userId, final int index) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_USER, userId, "playlists", index, Playlists.class);
    }

    public Playlists getUserPlaylists(final long userId, final int index, final int limit) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_USER, userId, "playlists", index, limit, Playlists.class);
    }

    public Radios getUserRadios(final long userId) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_USER, userId, "radios", Radios.class);
    }

    public Radios getUserRadios(final long userId, final int index) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_USER, userId, "radios", index, Radios.class);
    }

    public Radios getUserRadios(final long userId, final int index, final int limit) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_USER, userId, "radios", index, limit, Radios.class);
    }

    public Tracks getUserFavoriteTracks(final long userId) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_USER, userId, "tracks", Tracks.class);
    }

    public Tracks getUserFavoriteTracks(final long userId, final int index) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_USER, userId, "tracks", index, Tracks.class);
    }

    public Tracks getUserFavoriteTracks(final long userId, final int index, final int limit) {
        return this.requestsCarrier.get
                (DeezerClient.API_OBJECT_USER, userId, "tracks", index, limit, Tracks.class);
    }

    public <T> T getData(final String url, Class<T> targetClass) {
        return this.requestsCarrier.getData
                (url, targetClass);
    }

}
