package com.gck.simplecontacts;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Pervacio on 03-06-2016.
 */
public class NumbersAdapter extends BaseAdapter {

    private static final String TAG = NumbersAdapter.class.getSimpleName();
    private Context context;
    private String[] numbers;

    public NumbersAdapter(Context context, String[] numbers) {
        this.context = context;
        this.numbers = numbers;
    }

    @Override
    public int getCount() {
        return numbers.length;
    }

    @Override
    public Object getItem(int position) {
        return numbers[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        ViewHolder viewHolder;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.numbers_list_item, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.setData(numbers[position]);

        return view;
    }

    private class ViewHolder {
        private TextView textView;
        private ImageView callIv;
        private ImageView messageIv;

        public ViewHolder(View view) {
            textView = (TextView) view.findViewById(R.id.number_tv);
            callIv = (ImageView) view.findViewById(R.id.call);
            messageIv = (ImageView) view.findViewById(R.id.message);

            callIv.setOnClickListener(onClickListener);
            messageIv.setOnClickListener(onClickListener);
        }

        public void setData(String number) {
            textView.setText(number);
            callIv.setTag(number);
            messageIv.setTag(number);
        }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String number = (String) v.getTag();
            switch (v.getId()) {
                case R.id.call:
                    Log.d(TAG, "Hi "+number);
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:"+number));
                    context.startActivity(intent);
                    break;
                case R.id.message:
                    Log.d(TAG, "Hello "+number);
                    break;

            }
        }
    };
}
