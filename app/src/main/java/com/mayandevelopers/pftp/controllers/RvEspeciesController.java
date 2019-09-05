package com.mayandevelopers.pftp.controllers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mayandevelopers.pftp.MainActivity;
import com.mayandevelopers.pftp.R;
import com.mayandevelopers.pftp.databaseHelper.DatabaseAccess;
import com.mayandevelopers.pftp.models.EspeciesModel;
import com.mayandevelopers.pftp.views.RanchosActivity;

import java.util.List;

public class RvEspeciesController extends RecyclerView.Adapter<RvEspeciesController.ViewHolder> {


    Context mContext;
    private List<EspeciesModel> mData;

    private static final String ESPECIE_SELECCIONADA = "ESPECIE_SELECCIONADA";

    public RvEspeciesController(Context mContext, List<EspeciesModel> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public RvEspeciesController.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_especies,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RvEspeciesController.ViewHolder viewHolder, final int position) {
        final EspeciesModel misespecies = mData.get(position);

        /*holder.precio_nuevo.setText(Lonuevo.getPrecio_nuevo());
        holder.vigencia_nuevo.setText(Lonuevo.getVigencia_nuevo());
        holder.nombre_nuevo.setText(Lonuevo.getNombre_nuevo());
        Glide.with(mContext).load(mData.get(position).getImg_nuevo()).error(R.drawable.default_picture_promo).into(holder.imag_nuevo);*/

        viewHolder.nombre_especie.setText(misespecies.getNombreEspecie());

        // intent //
        viewHolder.ver_arboles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent ver_arboles = new Intent(mContext, RanchosActivity.class);

                SharedPreferences sharedPref = mContext.getSharedPreferences(ESPECIE_SELECCIONADA,0);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("id_especie", misespecies.getIdEspecie());
                //editor.putString("nombre_empresa", misespecies.getNombreEspecie());
                editor.apply();

                ver_arboles.putExtra("id_miespecie",misespecies.getIdEspecie());
                ver_arboles.putExtra("nombre_miespecie", misespecies.getNombreEspecie());
                //Log.i("HOLA", String.valueOf(misespecies.getIdEspecie()));
                ver_arboles.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(ver_arboles);
            }
        });

        // editar una especie //
        viewHolder.editar_especie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater mInflater = LayoutInflater.from(mContext);
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(mContext);
                View mView = mInflater.inflate(R.layout.popup_especies_name,null);

                final EditText edtxt_nombre = (EditText) mView.findViewById(R.id.edtxtNombreMain);
                Button btn_cancelar = (Button) mView.findViewById(R.id.btnCancelarMain);
                Button btn_guardar = (Button) mView.findViewById(R.id.btnGuardarMain);

                edtxt_nombre.setText(misespecies.getNombreEspecie());


                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                btn_cancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
                    }
                });

                btn_guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String nombre_especie = edtxt_nombre.getText().toString();

                        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(mContext);

                        databaseAccess.open();

                        databaseAccess.editarEspecies(misespecies.getIdEspecie(), nombre_especie);

                        databaseAccess.close();

                        mData.get(position).setNombreEspecie(nombre_especie);
                        notifyItemChanged(position);
                        //mData.set(position,mData.get(position));


                        //mData.remove(position);
                        //notifyItemRemoved(position);
                        //notifyItemRangeChanged(position, mData.size());
                        dialog.dismiss();

                    }
                });
            }
        });

        // eliminar una especie //
        viewHolder.eliminar_especie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mostrar un alert dialog personalizado //
                LayoutInflater mInflater = LayoutInflater.from(mContext);
                androidx.appcompat.app.AlertDialog.Builder mBuilder = new androidx.appcompat.app.AlertDialog.Builder(mContext);
                View mView = mInflater.inflate(R.layout.popup_eliminar,null);

                Button btn_cancelar = (Button) mView.findViewById(R.id.btnCancelarDelete);
                Button btn_eliminar = (Button) mView.findViewById(R.id.btnEliminar);

                mBuilder.setView(mView);
                final androidx.appcompat.app.AlertDialog dialog = mBuilder.create();
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                btn_cancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
                    }
                });

                btn_eliminar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(mContext);

                        databaseAccess.open();

                        databaseAccess.eliminarEspecies(misespecies.getIdEspecie());

                        databaseAccess.close();

                        //mData.get(position).setNombreEspecie(nombre_especie);
                        //notifyItemChanged(position);
                        //mData.set(position,mData.get(position));

                        dialog.dismiss();
                        mData.remove(position);
                        notifyItemRemoved(position);
                        //notifyItemRangeChanged(position, mData.size());


                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder  {

        TextView nombre_especie;
        ImageButton ver_arboles;
        ImageButton editar_especie;
        ImageButton eliminar_especie;

        public ViewHolder(View view) {
            super(view);

            nombre_especie= (TextView) view.findViewById(R.id.txtNombreEspecie);
            ver_arboles= (ImageButton) view.findViewById(R.id.imgbtnVerArboles);
            editar_especie=(ImageButton) view.findViewById(R.id.imgbtnEditarArboles);
            eliminar_especie=(ImageButton) view.findViewById(R.id.imgbtnEliminarArboles);
           // relativeLayout=(RelativeLayout)view.findViewById(R.id.capa__lonuevo);

        }
    }
}
