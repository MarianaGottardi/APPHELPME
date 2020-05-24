package com.mariana.gottardi.telaloginsqlite.Help.cuidador;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.mariana.gottardi.telaloginsqlite.R;

import java.util.List;

public class CuidadorAdapter extends BaseAdapter {
    private final Activity act;
    private final List<String> cuidadores;
    private final ListView listViewCuidadores;

    public CuidadorAdapter(List<String> cuidadores, Activity act, ListView listViewCuidadores) {
        this.cuidadores = cuidadores;
        this.act = act;
        this.listViewCuidadores = listViewCuidadores;
    }

    public int getCount() {
        return this.cuidadores.size();
    }

    public String getItem(int position) {
        return (String) this.cuidadores.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final BaseDAO dao = new BaseDAO(this.act);
        View view = this.act.getLayoutInflater().inflate(R.layout.cuidador_adapter, parent, false);
        final String cuidador = (String) this.cuidadores.get(position);
        Button btnExclui = (Button) view.findViewById(R.id.btnExcluiCuidador);
        Button btnEdita = (Button) view.findViewById(R.id.btnEditaCuidador);
        ((TextView) view.findViewById(R.id.nomeCuidadorAdapter)).setText(cuidador);
        btnExclui.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                dao.excluiCuidador(cuidador);
                CuidadorAdapter.this.listViewCuidadores.setAdapter(new CuidadorAdapter(dao.listaCuidador(), CuidadorAdapter.this.act, CuidadorAdapter.this.listViewCuidadores));
            }
        });

        btnEdita.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(CuidadorAdapter.this.act, CuidadorActivity.class);
                intent.putExtra("nomeCuidador", cuidador);
                CuidadorAdapter.this.act.startActivity(intent);
            }
        });
        return view;
    }
}
