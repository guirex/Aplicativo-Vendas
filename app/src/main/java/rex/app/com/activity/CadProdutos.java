package rex.app.com.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;

import rex.app.com.R;
import rex.app.com.model.Produto;

public class CadProdutos extends AppCompatActivity {
    private FirebaseDatabase db;
    private EditText etNome, etValor, etQuantidade, etCodigo;
    private Produto produto;
    private Button btSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_produtos);

        db = FirebaseDatabase.getInstance();

        produto = new Produto();

        etCodigo = findViewById(R.id.CodBarrasProduto_Cadastrar);
        etNome = findViewById(R.id.NomeProduto_Cadastrar);
        etQuantidade = findViewById(R.id.QuantidadeProduto_Cadastrar);
        etValor = findViewById(R.id.ValorProduto_Cadastrar);
        btSalvar = findViewById(R.id.btProduto_Cadastrar);

        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etNome.getText().toString().isEmpty() && !etCodigo.getText().toString().isEmpty()
                        && !etQuantidade.getText().toString().isEmpty() && !etValor.getText().toString().isEmpty()) {


                    produto.setNome(etNome.getText().toString());
                    produto.setCodigoDeBarras(Long.parseLong(etCodigo.getText().toString()));
                    produto.setValor(Double.parseDouble(etValor.getText().toString()));
                    produto.setQuantidade(Integer.parseInt(etQuantidade.getText().toString()));


                    DatabaseReference myRef = db.getReference("produtos");
                    String key = myRef.push().getKey();
                    myRef.child(key).setValue(produto);
                    Toast.makeText(getApplicationContext(), "Produto Cadastrado", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CadProdutos.this, ProdutosActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Preencha Todos os Campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
