package com.example.gym.data;

import android.util.Base64;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Encript {
    //Contantes
    public static final String AES = "AES";
    private static final String PWD_ENCRIPT = "12";
    public static final String SHA_256 = "SHA-256";

    /**
     * Encripta una String
     * @param datos ,Datos que se tienen que encriptar
     * @return String encriptada
     * @throws Exception Cuando hay un error a la hora de encriptar la String
     */
    public static String encriptar (String datos) throws Exception{

        SecretKeySpec secretKeySpec = generateKey(PWD_ENCRIPT);
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] datosEncriptadosBytes = cipher.doFinal(datos.getBytes());

        return Base64.encodeToString(datosEncriptadosBytes, Base64.DEFAULT);
    }

    /**
     * Genera una Key, mediante la contraseña que le pasamos
     * @param password, contraseña utilizada para crear la Key
     * @return SecreKey
     * @throws Exception Error al generar la Key
     */

    public static SecretKeySpec generateKey (String password) throws Exception{

        MessageDigest sha = MessageDigest.getInstance(SHA_256);
        byte[] key = password.getBytes(StandardCharsets.UTF_8);
        key = sha.digest(key);

        return new SecretKeySpec(key, AES);
    }

    /**
     * Actualmente en desuso
     * Desencripta la String en este caso encriptada
     * @param datos ,datos encriptados que tenemos que desencriptar
     * @return La String desencriptada
     * @throws Exception Error al desencirptar los datos
     */

    public static String desencriptar (String datos) throws Exception{

        SecretKeySpec secretKeySpec = generateKey(PWD_ENCRIPT);
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] datosDesencriptados = Base64.decode(datos, Base64.DEFAULT);
        byte[] datosDesencriptadosByte = cipher.doFinal(datosDesencriptados);

        return new String(datosDesencriptadosByte);
    }
}
