package rex.app.com.activity;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import rex.app.com.R;
import rex.app.com.model.Cliente;
import rex.app.com.model.Pedido;
import rex.app.com.setup.AppSetup;

public class CadClientes extends AppCompatActivity {
    private FirebaseDatabase db;
    private Cliente cliente;
    private EditText etNome, etSobrenome, etCPF;
    private Button btCadastrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_clientes);

        db = FirebaseDatabase.getInstance();

        cliente = new Cliente();

        etNome = findViewById(R.id.NomeCliente_Cadastrar);
        etSobrenome = findViewById(R.id.SobrenomeCliente_Cadastrar);
        etCPF = findViewById(R.id.CPFCLiente_Cadastrar);
        btCadastrar = findViewById(R.id.btCliente_Cadastrar);
        btCadastrar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                if(!etNome.getText().toString().isEmpty() && !etSobrenome.getText().toString().isEmpty() && !etCPF.getText().toString().isEmpty()){
                    cliente.setNome(etNome.getText().toString());
                    cliente.setSobrenome(etSobrenome.getText().toString());
                    cliente.setCPF(etCPF.getText().toString());

                    DatabaseReference myRef = db.getReference("clientes");
                    String key = myRef.push().getKey();
                    myRef.child(key).setValue(cliente);
                    Toast.makeText(getApplicationContext(),"Cliente Cadastrado", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CadClientes.this, ProdutosActivity.class));
                }else{
                    Toast.makeText(getApplicationContext(),"Preencha Todos os Campos", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }



}
