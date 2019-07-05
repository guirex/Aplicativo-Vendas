package rex.app.com.setup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import rex.app.com.model.Cliente;
import rex.app.com.model.ItemPedido;
import rex.app.com.model.Pedido;
import rex.app.com.model.Produto;
import rex.app.com.model.User;

public class AppSetup {

    public static List<Produto> produtos = new ArrayList<>();
    public static List<Cliente> clientes = new ArrayList<>();
    public static List<ItemPedido> carrinho = new ArrayList<>();
    public static Cliente cliente = null;
    public static Pedido pedido = null;
    public static FirebaseAuth mAuth = null;
    public static User usuario = null;
}
