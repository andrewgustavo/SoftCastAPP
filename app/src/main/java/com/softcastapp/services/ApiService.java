package com.softcastapp.services;

import com.softcastapp.models.UsuarioLogin;
import com.softcastapp.models.UsuarioRegister;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("api/Usuarios/Register")
    Call<Void> registerUsuario(@Body UsuarioRegister usuario);

    @POST("api/Usuarios/Login")
    Call<Void> login(@Body UsuarioLogin usuarioLogin);
}
