package rex.app.com.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import rex.app.com.R;
import rex.app.com.model.Cliente;

public class ClientesAdapter extends ArrayAdapter<Cliente> {

    private Context context;
    private List<Cliente> clientes;

    public ClientesAdapter(@NonNull Context context, @NonNull List<Cliente> clientes) {
        super(context, 0, clientes);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Cliente cliente = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_clientes_adapter, parent, false);
        }

        TextView tvNome = convertView.findViewById(R.id.tvNomeClienteAdapter);
        TextView tvSobrenome = convertView.findViewById(R.id.tvSobrenomeClienteAdapter);
        TextView tvCPF = convertView.findViewById(R.id.tvCPFClienteAdapter);
        ImageView imvFoto = convertView.findViewById(R.id.imvFotoClienteAdapter);

        tvNome.setText(cliente.getNome());
        tvSobrenome.setText(cliente.getSobrenome());
        tvCPF.setText(cliente.getCPF());
        if(cliente.getUrl_foto() != null){
            
        }else{
           imvFoto.setImageResource(R.drawable.img_cliente_icon_524x524);
        }


        return convertView;
    }
}

