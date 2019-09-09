package com.mayandevelopers.pftp.controllers;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mayandevelopers.pftp.R;
import com.mayandevelopers.pftp.models.RanchosModel;
import com.mayandevelopers.pftp.views.AgregarArbolActivity;
import com.mayandevelopers.pftp.views.ArbolesActivity;

import java.util.List;

public class RvRanchosController extends RecyclerView.Adapter<RvRanchosController.ViewHolder> {


    private Context mContext;
    private List<RanchosModel> mData;

    private static final String RANCHO_SELECCIONADO = "RANCHO_SELECCIONADO";

    public RvRanchosController(Context mContext, List<RanchosModel> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public RvRanchosController.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_ranchos,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RvRanchosController.ViewHolder viewHolder, int position) {
        final RanchosModel misranchos = mData.get(position);

        viewHolder.txt_nombre_rancho.setText(misranchos.getNombreRancho());

        viewHolder.btn_detalles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent btn_detalles = new Intent(mContext, ArbolesActivity.class);

                SharedPreferences sharedPref = mContext.getSharedPreferences(RANCHO_SELECCIONADO,0);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("id_rancho", misranchos.getIdRancho());
                editor.putString("nombre_rancho", misranchos.getNombreRancho());
                //editor.putString("nombre_empresa", misespecies.getNombreEspecie());
                editor.apply();

                btn_detalles.putExtra("id_rancho",misranchos.getIdRancho());
                btn_detalles.putExtra("nombre_rancho", misranchos.getNombreRancho());
                btn_detalles.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(btn_detalles);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder  {

        TextView txt_nombre_rancho;
        Button btn_detalles;

        public ViewHolder(View view) {
            super(view);

            txt_nombre_rancho= (TextView) view.findViewById(R.id.txtNombreRancho);
            btn_detalles= (Button) view.findViewById(R.id.btnDetallesRanchos);
           // relativeLayout=(RelativeLayout)view.findViewById(R.id.capa__lonuevo);
        }
    }
}
