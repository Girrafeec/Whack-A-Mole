package com.girrafeecstud.whacamole;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class RulesRecViewAdapter extends  RecyclerView.Adapter<RulesRecViewAdapter.ViewHolder> {

    private Context context;

    private ArrayList<RuleItem> ruleItemArrayList = new ArrayList<>();

    public RulesRecViewAdapter(ArrayList<RuleItem> ruleItemArrayList, Context context){
        this.context = context;
        this.ruleItemArrayList = ruleItemArrayList;
    }

    public void setRuleItemArrayList(ArrayList<RuleItem> ruleItemArrayList){
        this.ruleItemArrayList = ruleItemArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_rule_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.ruleImg.setImageResource(ruleItemArrayList.get(position).getRuleImageId());
        holder.ruleDesc.setText(ruleItemArrayList.get(position).getRuleDescription());
    }

    @Override
    public int getItemCount() {
        return ruleItemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView ruleImg;
        private TextView ruleDesc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ruleImg = itemView.findViewById(R.id.ruleImg);
            ruleDesc = itemView.findViewById(R.id.ruleTxt);
        }

    }

}
