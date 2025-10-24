package com.example.parcial2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Chip chipRemember;
    private Button btnLogin;
    private TextView tvRegister;

    private static final String PREFS_NAME = "app_prefs";
    private static final String KEY_EMAIL = "saved_email";
    private static final String KEY_PASSWORD = "saved_password";
    private static final String KEY_REMEMBER = "remember_me";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        chipRemember = findViewById(R.id.chipRemember);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // Cargar valores guardados si existían
        boolean remember = prefs.getBoolean(KEY_REMEMBER, false);
        if (remember) {
            etEmail.setText(prefs.getString(KEY_EMAIL, ""));
            etPassword.setText(prefs.getString(KEY_PASSWORD, ""));
            chipRemember.setChecked(true);
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (!isValidEmail(email)) {
                    etEmail.setError("Correo inválido");
                    return;
                }

                if (!isValidPassword(password)) {
                    etPassword.setError("Mínimo 8 caracteres, letras y números");
                    return;
                }

                // Si pasa validaciones, continuar (más adelante validaremos contra Firebase)
                Toast.makeText(LoginActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();

                if (chipRemember.isChecked()) {
                    prefs.edit()
                            .putBoolean(KEY_REMEMBER, true)
                            .putString(KEY_EMAIL, email)
                            .putString(KEY_PASSWORD, password)
                            .apply();
                } else {
                    prefs.edit().clear().apply();
                }

                // Ir al HomeActivity (luego lo crearemos)
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Ir al registro
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
    }

    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        // al menos 8 caracteres, con letras y números
        return password.length() >= 8 && password.matches("^(?=.*[0-9])(?=.*[A-Za-z]).{8,}$");
    }
}