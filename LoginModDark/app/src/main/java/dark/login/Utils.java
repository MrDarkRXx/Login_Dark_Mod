package dark.login;

import android.util.Base64;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class Utils {

    private static String bytesToHex(byte[] bytes) {
        char[] hexArray = "0123456789abcdef".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static String readStream(InputStream in) {
        StringBuilder response = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException ignored) {
        }
        return response.toString();
    }

    public static String SHA256(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.reset();
            md.update(data.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(md.digest()).toUpperCase();
        } catch (NoSuchAlgorithmException ex) {
            return null;
        }
    }

    public static String profileDecrypt(String data, String sign) {
        char[] key = sign.toCharArray();
        char[] out = fromBase64String(data).toCharArray();
        for(int i = 0; i < out.length;i++){
            out[i] = (char)(key[i % key.length] ^ out[i]);
        }
        return new String(out);
    }

    public static String toBase64(byte[] s) {
        return Base64.encodeToString(s, Base64.NO_WRAP);
    }

    public static byte[] fromBase64(String s) {
        return Base64.decode(s, Base64.NO_WRAP);
    }

   public static native String fromBase64String(String s);
}
