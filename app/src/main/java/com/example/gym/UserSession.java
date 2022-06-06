package com.example.gym;

import com.example.gym.Clases.Usuario;

public class UserSession {
    private static Usuario usuario;

    /**
     *
     * @return Usuario que ha iniciado sesión
     */
    public static Usuario getUsuario() {
        return usuario;
    }

    /**
     * Añade el usuario a la sesión actual
     * @param actualSession, usuario que ha iniciado sesión
     */
    public static void setUsuario(Usuario actualSession) {
        usuario = actualSession;
    }
}
