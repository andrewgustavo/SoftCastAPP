package com.softcastapp.services;

import com.softcastapp.models.Playlist;
import com.softcastapp.models.UsuarioLogin;
import com.softcastapp.models.UsuarioRegister;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Path;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.DELETE;

public interface ApiService {

    @POST("api/Usuarios/Register")
    Call<Void> registerUsuario(@Body UsuarioRegister usuario);

    @POST("api/Usuarios/Login")
    Call<Void> login(@Body UsuarioLogin usuarioLogin);

    @GET("api/Usuarios/email/{email}")
    Call<UsuarioLogin> getUsuarioByEmail(@Path(value = "email", encoded = true) String email);

    @GET("api/PlaylistsRepository/usuario/{usuarioId}")
    Call<List<Playlist>> getPlaylistsByUserId(@Path("usuarioId") int usuarioId);

    @POST("api/PlaylistsRepository")
    Call<Playlist> createPlaylist(@Body Playlist newPlaylist);

    @PUT("api/PlaylistsRepository/{id}")
    Call<Playlist> editPlaylist(@Path("id") int playlistId, @Body Playlist playlist);

    @DELETE ("api/PlaylistsRepository/{id}")
    Call<Playlist> deletePlaylist (@Path("id") int id);

}
