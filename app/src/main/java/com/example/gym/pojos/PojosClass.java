package com.example.gym.pojos;

public class PojosClass {
    public static UsersDAOImp getUsuarioDAO () {
        return new UsersDAOImp();
    }
    public static GymDAOimp getGymDAO() { return new GymDAOimp(); }
    public static ActividadesDAOImp getActividadesDao() { return new ActividadesDAOImp(); }
    public static ReservaDAOImp getReservaDao() { return new ReservaDAOImp(); }
}