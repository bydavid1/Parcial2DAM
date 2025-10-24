package com.example.parcial2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_DELAY_MS = 2000; // 2 segundos
    private static final String PREFS_NAME = "app_prefs";
    private static final String KEY_REMEMBER = "remember_me";
    // opcionales:
    private static final String KEY_SAVED_EMAIL = "saved_email";
    private static final String KEY_SAVED_PASSWORD = "saved_password";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Si prefieres un tema sin ActionBar, usa el tema en Manifest (recomendado)
        setContentView(R.layout.activity_splash);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // Ejemplo: verificar si guardaste credenciales y quieres ir directo a Home
                SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                boolean remember = prefs.getBoolean(KEY_REMEMBER, false);

                if (remember) {
                    // Si ya guardaste credenciales, podrías validar aquí si existen y dirigir a Home.
                    // String email = prefs.getString(KEY_SAVED_EMAIL, null);
                    // String pass = prefs.getString(KEY_SAVED_PASSWORD, null);
                    // verificar token/validar contra backend o Firebase si es necesario
                    Intent i = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(i);
                } else {
                    // Ir al login por defecto
                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(i);
                }

                finish();
            }
        }, SPLASH_DELAY_MS);
    }
}
