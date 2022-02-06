package com.example.myapplication.viewpagerr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.R;

public class EdgeTestPageItem implements EdgeOverflowPageItem {

    private View rootView;
    private TextView textView;
    private String item;

    public EdgeTestPageItem(Context context, String item) {
        rootView = LayoutInflater.from(context).inflate(R.layout.item_page_test1, null);
        this.item = item;
        init();
    }

    private void init() {
        textView = rootView.findViewById(R.id.text);
        textView.setText(item);
    }

    @Override
    public View getView() {
        return rootView;
    }

    @Override
    public void showView() {

    }
}
