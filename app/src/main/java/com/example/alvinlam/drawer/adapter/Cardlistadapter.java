package com.example.alvinlam.drawer.adapter;

/**
 * Created by Alvin Lam on 3/22/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alvinlam.drawer.R;

public class Cardlistadapter extends RecyclerView.Adapter<Cardlistadapter.NumberViewHolder> {

    private static final String TAG = Cardlistadapter.class.getSimpleName();
    final private ListItemClickListener mOnClickListener;
    private int mNumberItems;

    public interface ListItemClickListener{
        void onListItemClick(int parameter);
    }

    public Cardlistadapter(int numberOfItems, ListItemClickListener listener) {
        mNumberItems = numberOfItems;
        mOnClickListener = listener;
    }

    @Override
    public NumberViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.card_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        NumberViewHolder viewHolder = new NumberViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NumberViewHolder holder, int position) {
        Log.d(TAG, "#" + position);
        holder.bind(position);
    }


    @Override
    public int getItemCount() {
        return mNumberItems;
    }

    class NumberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView listItemNumberView;

        public NumberViewHolder(View itemView) {
            super(itemView);
            listItemNumberView = (TextView) itemView.findViewById(R.id.tv_item_number);
            itemView.setOnClickListener(this);
        }

        void bind(int listIndex) {
            listItemNumberView.setText(String.valueOf(listIndex));
        }

        @Override
        public void onClick(View v) {
            int click = getAdapterPosition();
            mOnClickListener.onListItemClick(click);
        }
    }
}
