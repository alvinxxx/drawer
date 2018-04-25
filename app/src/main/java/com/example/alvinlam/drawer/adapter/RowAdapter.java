package com.example.alvinlam.drawer.adapter;

/**
 * Created by Alvin Lam on 3/22/2017.
 */

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.alvinlam.drawer.R;
import com.example.alvinlam.drawer.data.StocklistContract;

import java.util.Locale;

public class RowAdapter extends RecyclerView.Adapter<RowAdapter.RowViewHolder> {

    private static final String TAG = RowAdapter.class.getSimpleName();
    private Context mContext;
    private Cursor mCursor;
    final private ListItemClickListener mOnClickListener;


    public interface ListItemClickListener{
        void onListItemClick(View v, int parameter);
    }

    public RowAdapter(Context context, Cursor cursor, ListItemClickListener listener ) {
        mContext = context;
        mCursor = cursor;
        mOnClickListener = listener;
    }

    @Override
    public RowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.row_list_data, parent, false);
        RowViewHolder viewHolder = new RowViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RowViewHolder holder, int position) {

        if (!mCursor.moveToPosition(position))
            return;
        long id = mCursor.getLong(mCursor.getColumnIndex(StocklistContract.StocklistEntry._ID));
        String name = mCursor.getString(mCursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_NAME));
        Double price = mCursor.getDouble(mCursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_PRICE));
        Double netChange = mCursor.getDouble(mCursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_NET_CHANGE));
        Double std250 = mCursor.getDouble(mCursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_STD250));

        holder.itemView.setTag(id);
        String code = String.valueOf(id);
        holder.keyTextView.setText(code);
        holder.valueTextView.setText(name);

    }


    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        // Always close the previous mCursor first
        if (mCursor != null) mCursor.close();
        // Update the local mCursor to be equal to  newCursor
        mCursor = newCursor;
        // Check if the newCursor is not null, and call this.notifyDataSetChanged() if so
        if (newCursor != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }


    class RowViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView keyTextView;
        TextView valueTextView;


        public RowViewHolder(View itemView) {
            super(itemView);
            keyTextView = (TextView) itemView.findViewById(R.id.row_key_text_view);
            valueTextView = (TextView) itemView.findViewById(R.id.row_value_text_view);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            //int click = getAdapterPosition();
            //mOnClickListener.onListItemClick(v, click);
        }
    }
}
