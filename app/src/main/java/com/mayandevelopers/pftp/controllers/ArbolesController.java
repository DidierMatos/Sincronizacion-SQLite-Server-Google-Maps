package com.mayandevelopers.pftp.controllers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mayandevelopers.pftp.R;
import com.mayandevelopers.pftp.models.ArbolesModel;

import java.lang.reflect.Modifier;
import java.util.ArrayList;

public class ArbolesController extends RecyclerView.Adapter<ArbolesController.ViewHolder> {

    private Context mContext;
    private ArrayList<ArbolesModel> mData;
    ArrayList<ArbolesModel> actividadItem;
    private int selectPosition;

    public ArbolesController(Context mContext, ArrayList<ArbolesModel> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ArbolesController.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_mis_arboles ,viewGroup,false);

        return new ArbolesController.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArbolesController.ViewHolder viewHolder, int i) {



    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtview_titulo, txtview_folio;
        Button btn_visitas, btn_editar, btn_eliminar;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);


        }
    }
}
