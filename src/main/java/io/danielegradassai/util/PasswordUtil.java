package io.danielegradassai.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class PasswordUtil {

    public static String crypt(String password) {
        String newPass = "";
        try {
            MessageDigest messDig = MessageDigest.getInstance("MD5");
            messDig.update(password.getBytes());
            byte[] cryptedBytes = messDig.digest();
            BigInteger convert = new BigInteger(1, cryptedBytes);
            newPass = convert.toString(16);
        } catch(NoSuchAlgorithmException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Errore del server...");
        }
        return newPass;
    }

    public static String generate(){
        StringBuilder sb = new StringBuilder(10);
        sb.append(new Random().nextInt('0','9'));
        sb.append('@');
        for (int i = 0; i < 8; i++) {
            sb.append(new Random().nextInt('a','z'));
        }
        return sb.toString();
    }
}
