package rex.app.com.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.Date;

import rex.app.com.R;
import rex.app.com.adapter.CarrinhoAdapter;
import rex.app.com.model.ItemPedido;
import rex.app.com.model.Pedido;
import rex.app.com.setup.AppSetup;

import static rex.app.com.setup.AppSetup.usuario;


public class CarrinhoActivity extends AppCompatActivity {

    private static final String TAG = "CarrinhoActivity";
    private ListView lv_carrinho;
    private double totalpedido;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       
        setContentView(R.layout.activity_carrinho);
        TextView tvClienteCarinho = findViewById(R.id.tvClienteCarrinho);
        tvClienteCarinho.setText(String.valueOf(AppSetup.cliente.getNome().concat(" " + AppSetup.cliente.getSobrenome())));
        lv_carrinho = findViewById(R.id.lv_carrinho);

        atualizaView();
        lv_carrinho.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                excluiItem(position);
                return true;
            }
        });

        lv_carrinho.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editaItem(position);
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_carrinho, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuitem_salvar:
                confirmaSalvar();
                break;
            case R.id.menuitem_cancelar:
                confirmaCancelar();
                break;
        }
        return true;
    }

    private void editaItem(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.title_confimar);
        builder.setMessage(R.string.mensagens_edita);
        builder.setPositiveButton(R.string.alertdialog_sim, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                atualizaEstoque(position);
                Intent intent = new Intent(CarrinhoActivity.this, ProdutoDetalheActivity.class);
                intent.putExtra("position", AppSetup.produtos.get(position).getIndex());
                startActivity(intent);
            }
        });
        builder.setNegativeButton(R.string.alertdialog_nao, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();
    }

    private void excluiItem(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.title_confimar);
        builder.setMessage(R.string.mensagem_exclui);
        builder.setPositiveButton(R.string.alertdialog_sim, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                atualizaEstoque(position);
            }
        });
        builder.setNegativeButton(R.string.alertdialog_nao, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();
    }

    private void confirmaCancelar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.title_confimar);
        builder.setMessage(R.string.message_confirma_cancelar);

        builder.setPositiveButton(R.string.alertdialog_sim, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("tamanho", String.valueOf(AppSetup.carrinho.size()));
                for (ItemPedido item : AppSetup.carrinho) {
                    DatabaseReference myRef = database.getReference("produtos/" + item.getProduto().getKey() + "/quantidade");
                    myRef.setValue(item.getQuantidade() + item.getProduto().getQuantidade());
                    Log.d("removido", item.toString());
                    Log.d("item", "item removido");
                }
                AppSetup.carrinho.clear();
                AppSetup.cliente = null;
                finish();
            }
        });
        builder.setNegativeButton(R.string.alertdialog_nao, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    private void confirmaSalvar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.title_confimar);
        builder.setMessage(R.string.message_confirma_salvar);
        builder.setPositiveButton(R.string.alertdialog_sim, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (AppSetup.carrinho == null) {
                    Toast.makeText(CarrinhoActivity.this, getString(R.string.carrinho_vazio), Toast.LENGTH_SHORT).show();
                } else {
                    Date dataHoraAtual = new Date();

                    DatabaseReference myRef = database.getReference("pedidos");
                    String key = myRef.push().getKey();

                    Pedido pedido = new Pedido();
                    pedido.setCliente(AppSetup.cliente);
                    pedido.setDataCriacao(dataHoraAtual);
                    pedido.setDataModificacao(dataHoraAtual);
                    pedido.setEstado("aberto");
                    pedido.setFormaDePagamento("avista");
                    pedido.setItens(AppSetup.carrinho);
                    pedido.setSituacao(true);
                    pedido.setTotalPedido(totalpedido);
                    pedido.setVendedor(usuario);

                    myRef.child(key).setValue(pedido);

                    AppSetup.clientes = null;
                    AppSetup.carrinho.clear();
                    AppSetup.pedido = null;
                    finish();
                }
            }
        });
        builder.setNegativeButton(R.string.alertdialog_nao, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();
    }

    public void atualizaEstoque(int position) {
        DatabaseReference myRef = database.getReference("produtos/" + AppSetup.carrinho.get(position).getProduto().getKey() + "/quantidade");
        myRef.setValue(AppSetup.carrinho.get(position).getQuantidade() + AppSetup.carrinho.get(position).getProduto().getQuantidade());

        Log.d("removido", AppSetup.carrinho.get(position).toString());
        AppSetup.carrinho.remove(position);
        Log.d("item", "item removido");

        atualizaView();

        Toast.makeText(CarrinhoActivity.this, "Produto removido com sucesso!", Toast.LENGTH_SHORT).show();
    }

    public void atualizaView() {
        TextView tvTotalPedidoCarrinho = findViewById(R.id.tvTotalPedidoCarrinho);
        lv_carrinho.setAdapter(new CarrinhoAdapter(CarrinhoActivity.this, AppSetup.carrinho));
        for (ItemPedido itemPedido : AppSetup.carrinho) {
            totalpedido = totalpedido + itemPedido.getTotalItem();
        }
        tvTotalPedidoCarrinho.setText(NumberFormat.getCurrencyInstance().format(totalpedido));
    }

}