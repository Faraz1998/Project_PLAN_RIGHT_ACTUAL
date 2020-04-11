package com.example.project.Login_Register_files;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.Home;
import com.example.project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    EditText Email, Password;
    Button Login;
    TextView register, forgotpass;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Email = findViewById(R.id.ETEmail);
        Password = findViewById(R.id.PWord);
        Login = findViewById(R.id.BtnLogin);
        register = findViewById(R.id.tvRegister);
        fAuth = FirebaseAuth.getInstance();
        forgotpass = findViewById(R.id.forgot);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();//if user closes app without logging out, this code allows them to continue with their session still logged in.
        if(user!=null){
            startActivity(new Intent(getApplicationContext(), Home.class));
            this.finish();
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityRegister();
            }
        });
        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityforgotpass();
            }
        });
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = Email.getText().toString().trim();
                String password = Password.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Email.setError("You must enter an Email Address.");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Password.setError("You must enter a Password.");
                    return;
                }
                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this, "Login Successful.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),Home.class));
                        }
                        else{
                            Toast.makeText(Login.this, "Error " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
    public void openActivityRegister(){
        Intent intent = new Intent(Login.this, com.example.project.Login_Register_files.register.class);
        startActivity(intent);
    }
    public void openActivityforgotpass() {
        Intent intent = new Intent(Login.this, com.example.project.Login_Register_files.forgotpass.class);
        startActivity(intent);
    }
}
