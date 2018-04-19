package com.example.alvinlam.drawer.adapter;

/**
 * Created by Alvin Lam on 3/22/2017.
 */

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class CardlistAdapter extends RecyclerView.Adapter<CardlistAdapter.CardViewHolder> {

    private static final String TAG = CardlistAdapter.class.getSimpleName();
    private Context mContext;
    private Cursor mCursor;
    final private ListItemClickListener mOnClickListener;

    String letter;
    ColorGenerator generator = ColorGenerator.MATERIAL;

    public interface ListItemClickListener{
        void onListItemClick(View v, int parameter);
    }

    public CardlistAdapter(Context context, Cursor cursor, ListItemClickListener listener ) {
        mContext = context;
        mCursor = cursor;
        mOnClickListener = listener;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.main_content_list_data, parent, false);
        CardViewHolder viewHolder = new CardViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {

        if (!mCursor.moveToPosition(position))
            return;
        long id = mCursor.getLong(mCursor.getColumnIndex(StocklistContract.StocklistEntry._ID));
        String name = mCursor.getString(mCursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_NAME));
        Double price = mCursor.getDouble(mCursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_PRICE));
        Double netChange = mCursor.getDouble(mCursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_NET_CHANGE));
        Double preClose = mCursor.getDouble(mCursor.getColumnIndex(StocklistContract.StocklistEntry.COLUMN_PRE_CLOSE));

        try {
            netChange = (price-preClose)/preClose;
            //System.out.println(netChange+"%");

        } catch (Exception e) {
            Log.d(TAG, "onCreateView: "+"nullpointer");
        }
        holder.itemView.setTag(id);
        holder.nameTextView.setText(name);
        holder.priceTextView.setText(String.format(Locale.getDefault(), "%.2f", price));
        holder.netChangeTextView.setText(String.format(Locale.getDefault(), "%.2f", netChange)+"%");

        //        Get the first letter of list item
        letter = String.valueOf(name.charAt(0));
        int color = generator.getColor(letter);

        //        Create a new TextDrawable for our image's background
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(letter, color);

        holder.letter.setImageDrawable(drawable);
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


    class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView nameTextView;
        TextView priceTextView;
        TextView netChangeTextView;

        ImageView letter;

        public CardViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.name_text_view);
            priceTextView = (TextView) itemView.findViewById(R.id.price_text_view);
            netChangeTextView = (TextView) itemView.findViewById(R.id.net_change_text_view);
            letter = (ImageView) itemView.findViewById(R.id.imageViewLetter);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            int click = getAdapterPosition();
            mOnClickListener.onListItemClick(v, click);
        }
    }
}
