package rex.app.com.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import rex.app.com.R;
import rex.app.com.model.User;
import rex.app.com.setup.AppSetup;

public class CadFuncionarios extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private User funcionario;
    private EditText etNome, etSobrenome, etEmail,etSenha;
    private Button btCadastrar;
    private Spinner spFunc;
    private String[] Func = new String[]{"Vendedor", "Administrador"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_funcionarios);

        mAuth = FirebaseAuth.getInstance();
        AppSetup.mAuth = mAuth;
        funcionario = new User();
        etNome = findViewById(R.id.NomeFunc_Cadastrar);
        etSobrenome = findViewById(R.id.SobrenomeFunc_Cadastrar);
        etEmail = findViewById(R.id.EmailFunc_Cadastrar);
        etSenha = findViewById(R.id.SenhaFunc_Cadastrar);
        spFunc = findViewById(R.id.SpinnerFunc_Cadastrar);
        btCadastrar = findViewById(R.id.btFunc_Cadastrar);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Func);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFunc.setAdapter(adapter);

        btCadastrar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
            if(!etEmail.getText().toString().isEmpty() && !etSenha.getText().toString().isEmpty())
            {
                mAuth.createUserWithEmailAndPassword(etEmail.getText().toString(), etSenha.getText().toString())
                        .addOnCompleteListener(CadFuncionarios.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("users/" + mAuth.getCurrentUser().getUid() + "/");
                                    String key = myRef.push().getKey();

                                    funcionario.setNome(etNome.getText().toString());
                                    funcionario.setSobrenome(etSobrenome.getText().toString());
                                    funcionario.setFirebaseUser(mAuth.getCurrentUser());
                                    funcionario.setFuncao(Func[spFunc.getSelectedItemPosition()]);

                                    myRef.setValue(funcionario).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(getApplicationContext(), "Funcionário Cadastrado", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(CadFuncionarios.this, ProdutosActivity.class));
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getApplicationContext(), "Erro: Funcionário não Cadastrado", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } else{
                                    Toast.makeText(getApplicationContext(), "Algo ocorreu de maneira inesperada", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }else {
                Toast.makeText(getApplicationContext(), "Preencha Todos os Campos", Toast.LENGTH_SHORT).show();
            }
            }
        });
    }
}