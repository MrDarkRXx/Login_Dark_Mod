package dark.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DarkLogin extends Activity {
    private final String USER = "USER";
    private final String PASS = "PASS";
    private Prefs prefs;

    static {
        System.loadLibrary("DarkLogin");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getResID("main_login","layout"));
        prefs = Prefs.with(this);
        final EditText mail = findViewById(getID("d_name"));
        final EditText pass = findViewById(getID("d_ps"));
        mail.setText(prefs.read(USER, ""));
        pass.setText(prefs.read(PASS, ""));
        Button init = findViewById(getID("log_btn"));
        init.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mail.getText().toString().isEmpty() && !pass.getText().toString().isEmpty()) {
                    prefs.write(USER, mail.getText().toString());
                    prefs.write(PASS, pass.getText().toString());
                    new LoginDark(DarkLogin.this).execute(mail.getText().toString(), pass.getText().toString());
                }
                if(mail.getText().toString().isEmpty() && pass.getText().toString().isEmpty()) {
                    mail.setError("Please enter username");
                    pass.setError("Please enter password");
                }
                if(mail.getText().toString().isEmpty()){
                    mail.setError("Please enter username");
                }
                if(pass.getText().toString().isEmpty()){
                    pass.setError("Please enter password");
                }
            }
        });
    }


    public int getResID(String name, String type){
        return getResources().getIdentifier(name, type, getPackageName());
    }
    public int getID(String name){
        return getResID(name, "id");
    }
}