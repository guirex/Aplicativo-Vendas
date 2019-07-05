package rex.app.com.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import rex.app.com.R;
import rex.app.com.model.Cliente;
import rex.app.com.setup.AppSetup;

import static rex.app.com.setup.AppSetup.clientes;

public class UpdProdutos extends AppCompatActivity {
    private EditText etCodigoProd, etNomeProd, etValorProd, etQuantidadeProd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upd_produtos);

        etCodigoProd = findViewById(R.id.CodBarrasProduto_Atualizar);
        etNomeProd = findViewById(R.id.NomeProduto_Atualizar);
        etValorProd = findViewById(R.id.ValorProduto_Atualizar);
        etQuantidadeProd = findViewById(R.id.QuantidadeProduto_Atualizar);



    }
}
