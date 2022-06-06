package com.example.gym.data;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ComFunctions {
    public static int actYear = Calendar.getInstance().get(Calendar.YEAR);
    public static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    public static SimpleDateFormat sdfMin = new SimpleDateFormat("HH:mm");
    //Constantes de Actividad
    public static final String ID_ACTIVIDAD = "actividad";
    public static final String USER_NAME = "actividad";
    public static final String ID_RESERVA = "idReserva";

    //-----------------------------------------------------------
    //Constantes de GYM
    public static final String GYM = "gym";

    //------------------------------------------------------------
    //Constantes de Registro
    public static final String RESERVA = "Reserva";

    //Constatntes acitvidad
    public static final String ACTIVIDADES = "Actividades";

    /**
     * Comprueba si la longitud de la String esta entre los valores introducidos
     *
     * @param etValidator , editText de donde se registra la información
     * @param i, minimo de caracteres que debe de tener la String
     * @param i2, maximo de caracteres que debe de tener la String
     * @return boleano dependiendo de si esta comprendido entre los valores (True) o no (False)
     */
    public static boolean is_not_correct(EditText etValidator, int i, int i2) {
        if (etValidator.getText().toString() == null) return true;
        if (etValidator.getText().toString().length() < i || etValidator.getText().toString().length() > i2)
            return true;
        return false;
    }

    /**
     * Comprueba si la String que contiene el edit text tiene la longitud exacta
     *
     * @param etValidator , editText de donde se registra la información
     * @param i , numero de caracteres exactos que tiene que tener la String
     * @return boleano dependiendo de si el numero de caracteres es exacto (True) o no (False)
     */
    public static boolean is_not_correct(EditText etValidator, int i) {
        if (etValidator.getText().toString() == null) return true;
        if (etValidator.getText().toString().length() != i) return true;
        return false;
    }

    /**
     * Comprueba si el año introducido, esta comprendido entre el actual - 100
     *
     * @param year año que se queire comprobar
     * @param etYear EditText de dodnde se seca el valor de año
     * @return boleano que devuelve True si el valor esta entre el año actual y año actual -100 y Falso si no
     */
    public static boolean is_not_correct_year(int year, EditText etYear) {
        if (is_not_correct(etYear, 4)) return true;
        if (year < actYear - 100 || year > actYear) return true;
        return false;
    }

    /**
     * Pasa la hora a de String a Date y comprueba que este bien, ed decir que la primera no sea mayor a la segunda
     *
     * @param horaA ,Primera hora que debe de ser menos que la segunda
     * @param horaC ,Segundo hora que debe de ser mayor a la primera
     * @return Devuelve False en caso de que la horaA sea menor a horaC y True si es al reves
     */
    public static boolean isCorrectHour(String horaA, String horaC) {
        Date hDateA = strToDate(horaA);
        Date hDateC = strToDate(horaC);

        if (hDateA == null || hDateC == null) return false;
        if (hDateA.equals(hDateC)) return false;
        if (hDateA.after(hDateC)) return false;
        return true;
    }

    /**
     * Transforma una String con un formato en concreto (HH:mm) en
     * un objeto de clase Date con la hora.
     *
     * @exception ParseException cuanod la hora no tiene el formato concreto salta esta excepcion
     * @param hora ,Hora con un formato concreto que se transformara a date
     * @return la String transformada en Date si es correcta y en caso contrasio null si no es correcto el formato
     */
    public static Date strToDate(String hora) {
        try {
            return sdfMin.parse(hora);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * Transforma una string con un formato concreto (dd/mm/yyyy) en
     * un objeto Date.
     * @exception ParseException Cuando el formato no es el indicado anteriormente salta la siguiente error
     * @param dateStr
     * @return  la String transformada en Date si es correcta y en caso contrasio null si no es correcto el formato
     */
    public static Date strToDateDay(String dateStr) {
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date getActualDate() {
        return new Date();
    }
}
