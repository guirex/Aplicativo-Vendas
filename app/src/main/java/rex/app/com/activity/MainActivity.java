package rex.app.com.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import rex.app.com.R;

import rex.app.com.model.User;
import rex.app.com.setup.AppSetup;

public class MainActivity extends AppCompatActivity {
    private static String TAG = "loginActivity";
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        AppSetup.mAuth = mAuth;

        findViewById(R.id.bt_logar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=((EditText)findViewById(R.id.email)).getText().toString();
                String senha=((EditText)findViewById(R.id.senha)).getText().toString();
                if(!email.isEmpty() && !senha.isEmpty()){
                    entrar(email,senha);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Preencha todos os campos!", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    private void entrar(String email, String senha){
        mAuth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(this,
                new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success");
                    buscarUserNobanco(mAuth.getCurrentUser());
                }
                else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure ",  task.getException());
                    if(task.getException().getMessage().contains("password")){
                        Toast.makeText(MainActivity.this, "Senha não cadastrada.", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MainActivity.this, "Email não cadastrado.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void buscarUserNobanco(final FirebaseUser firebaseUser) {

        FirebaseDatabase.getInstance().
                getReference().child("users/").
                child(firebaseUser.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            AppSetup.usuario = dataSnapshot.getValue(User.class);

            AppSetup.usuario.setFirebaseUser(firebaseUser);
                startActivity(new Intent(MainActivity.this, ProdutosActivity.class));
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, ("Não foi possível realizar o login"), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cadastrar(String email, String senha){
        mAuth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign up success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            AppSetup.usuario.setFirebaseUser(mAuth.getCurrentUser());
                            startActivity(new Intent(MainActivity.this, ProdutosActivity.class));
                        } else {
                            // If sign up fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            if(task.getException().getMessage().contains("email")){
                                Toast.makeText(MainActivity.this, "Email já cadastradou.", Snackbar.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(MainActivity.this, "Sign up falhou.", Snackbar.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }
}