package dark.login;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.UUID;

import javax.crypto.Cipher;


public class LoginDark extends AsyncTask<String, Void, String> {
    private WeakReference<Context> weakActivity;
    private String expire = "DTEXPIRE";

    native byte[] puk();

    private native String login(String str, String str2, HttpURLConnection internet, Context activity);

    private native URL NetworkConection();

    private native JSONObject JS0NDark(String s);

    LoginDark(Context activity){
        weakActivity = new WeakReference<>(activity);
    }

    @Override
    protected void onPreExecute() {
        Context activity = getActivity();
        if (activity == null) {
            return;
        }
    }

    @Override
    protected String doInBackground(String... strings) {
        if (!isInternetAvailable(getActivity())) {
            return "No internet connection";
        }
        try {
            return login(strings[0], strings[1], (HttpURLConnection) NetworkConection().openConnection(), getActivity());
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(String s) {
        Context activity = getActivity();
        if (activity == null) {
            return;
        }

        if(s == null || s.isEmpty()){
            Toast.makeText(activity,"Server Error", Toast.LENGTH_LONG).show();
            return;
        }

        if(s.equals("No internet connection")){
            Toast.makeText(activity,s, Toast.LENGTH_LONG).show();
            return;
        }

        try {
            JSONObject ack = new JSONObject(s);
            String decData = Utils.profileDecrypt(ack.get("Data").toString(), ack.get("Hash").toString());
            if (!verify(decData, ack.get("Sign").toString(), puk())) {
                Toast.makeText(activity, "Login Data is Wrong!", Toast.LENGTH_LONG).show();
                return;
            }
            JSONObject data = JS0NDark(decData);
            if(data.get("Status").toString().equals("Success")) {
                Prefs prefs = Prefs.with(getActivity());
                Toast.makeText(activity, "Login Successfully!", Toast.LENGTH_SHORT).show();
                if (Build.VERSION.SDK_INT >= 23 && !Settings.canDrawOverlays(getActivity())) {

                } else {
                    if (!isServiceRunning()) {
                        Intent intent = new Intent(getActivity(), FloaterDark.class);
                        intent.putExtra("EXPIRY", data.get("SubscriptionLeft").toString());
                        prefs.write(expire, data.get("SubscriptionLeft").toString());
                        getActivity().startService(intent);
                    } else {
                        Toast.makeText(getActivity(), "Service Already Running!", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(activity,data.get("MessageString").toString(), Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(activity,"Invalid Result", Toast.LENGTH_LONG).show();
        }
    }

    private static boolean isInternetAvailable(Context ctx) {
        ConnectivityManager connectivityManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private boolean isServiceRunning() {
        ActivityManager manager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        if (manager != null) {
            for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
                if (FloaterDark.class.getName().equals(service.service.getClassName())) {
                    return true;
                }
            }
        }
        return false;
    }

    private Context getActivity() {
        return weakActivity.get();
    }

    public static PublicKey getPublicKey(byte[] keyBytes) throws Exception {
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

    public static String encrypt(String plainText, byte[] keyBytes) throws Exception {
        Cipher encryptCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        encryptCipher.init(Cipher.ENCRYPT_MODE, getPublicKey(keyBytes));
        return Utils.toBase64(encryptCipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8)));
    }

    private boolean verify(String plainText, String signature, byte[] keyBytes) throws Exception {
        Signature publicSignature = Signature.getInstance("SHA256withRSA");
        publicSignature.initVerify(getPublicKey(keyBytes));
        publicSignature.update(plainText.getBytes(StandardCharsets.UTF_8));
        return publicSignature.verify(Utils.fromBase64(signature));
    }

}
