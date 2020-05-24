package com.mariana.gottardi.telaloginsqlite.Help.endereco;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class AddressAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<Address> addresses;

    public AddressAdapter(Context context, List<Address> addresses) {
        inflater = LayoutInflater.from(context);
        this.addresses = addresses;
    }

    @Override
    public int getCount() {
        return addresses.size();
    }

    @Override
    public Object getItem(int i) {
        return addresses.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        if (view == null) {
            holder = new ViewHolder();
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.setData(addresses.get(i));

        return view;
    }


    private static class ViewHolder {
        TextView tvZipCode;
        TextView tvStreet;
        TextView tvNeighbor;

        private void setData(Address address) {
            tvZipCode.setText("CEP: " + address.getCep());
            tvStreet.setText("Rua: " + address.getLogradouro());
            tvNeighbor.setText("Bairro: " + address.getBairro());
        }
    }

}
