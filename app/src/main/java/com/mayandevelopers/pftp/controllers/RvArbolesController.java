package com.mayandevelopers.pftp.controllers;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mayandevelopers.pftp.R;
import com.mayandevelopers.pftp.models.ArbolesModel;
import com.mayandevelopers.pftp.views.AgregarArbolActivity;

import java.util.ArrayList;

public class RvArbolesController extends RecyclerView.Adapter<RvArbolesController.ViewHolder> {

    private Context mContext;
    private ArrayList<ArbolesModel> mData;
    ArrayList<ArbolesModel> actividadItem;
    private int selectPosition;

    public RvArbolesController(Context mContext, ArrayList<ArbolesModel> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public RvArbolesController.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_mis_arboles ,viewGroup,false);

        return new RvArbolesController.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RvArbolesController.ViewHolder viewHolder, int position) {
        final ArbolesModel misarboles = mData.get(position);

        viewHolder.txtview_folio.setText(misarboles.getFolio());

        viewHolder.btn_visitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        viewHolder.btn_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AgregarArbolActivity.class);
                intent.putExtra("update","1");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });

        viewHolder.btn_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtview_folio;
        ImageButton btn_visitas, btn_editar, btn_eliminar;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtview_folio = itemView.findViewById(R.id.txtviewFolioCVarbol);
            btn_visitas = itemView.findViewById(R.id.btnVistasCVarbol);
            btn_editar = itemView.findViewById(R.id.btnEditarCVarbol);
            btn_eliminar = itemView.findViewById(R.id.btnEliminarCVarbol);


        }
    }
}
