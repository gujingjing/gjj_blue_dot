package com.example.myapplication.viewpagerr;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.List;

public class EdgeListTestPageItem implements EdgeOverflowPageItem {

    private View rootView;
    private Context context;
    private List<String> items;
    private RecyclerView recyclerView;

    public EdgeListTestPageItem(Context context, List<String> items) {
        rootView = LayoutInflater.from(context).inflate(R.layout.item_page_list_test1, null);
        this.items = items;
        this.context = context;
        init();
    }

    private void init() {
        recyclerView = rootView.findViewById(R.id.recyclerView);
        // 定义一个线性布局管理器
        GridLayoutManager manager = new GridLayoutManager(context, 4);
        // 设置布局管理器
        recyclerView.setLayoutManager(manager);
        // 设置adapter
        DemoAdapter adapter = new DemoAdapter(context, items);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public View getView() {
        return rootView;
    }

    @Override
    public void showView() {

    }

    public class DemoAdapter extends RecyclerView.Adapter {

        private Context mContext;
        private List<String> mEntityList;

        public DemoAdapter(Context context, List<String> entityList) {
            this.mContext = context;
            this.mEntityList = entityList;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_demo, parent, false);
            return new DemoViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            String entity = mEntityList.get(position);
            ((DemoViewHolder) holder).mText.setText(entity);
        }

        @Override
        public int getItemCount() {
            return mEntityList.size();
        }

        private class DemoViewHolder extends RecyclerView.ViewHolder {

            private TextView mText;

            public DemoViewHolder(View itemView) {
                super(itemView);
                mText = (TextView) itemView.findViewById(R.id.item_text);
            }
        }
    }
}
