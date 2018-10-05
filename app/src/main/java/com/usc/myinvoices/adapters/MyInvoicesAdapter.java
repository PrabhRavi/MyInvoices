package com.usc.myinvoices.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.usc.myinvoices.R;
import com.usc.myinvoices.controllers.InvoiceActivity;
import com.usc.myinvoices.fragments.InvoiceViewFragment;
import com.usc.myinvoices.models.MyInvoice;

import java.util.List;


public class MyInvoicesAdapter extends RecyclerView.Adapter<MyInvoicesAdapter.LogHolder> {

    private Context mContext;
    private List<MyInvoice> mMyInvoices;

    public MyInvoicesAdapter(Context context, List<MyInvoice> logs){
        this.mContext = context;
        this.mMyInvoices = logs;
    }

    @Override
    public LogHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.my_invoice_item,parent,false);
        return new LogHolder(view);

    }

    @Override
    public void onBindViewHolder(LogHolder holder, int position) {

        final MyInvoice myInvoice = mMyInvoices.get(position);

        holder.bindLog(myInvoice);

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                AppCompatActivity activity = (AppCompatActivity) view.getContext();

                InvoiceViewFragment myFragment = new InvoiceViewFragment();

                Bundle bundle = new Bundle();
                bundle.putString(InvoiceActivity.EXTRA_LOG_ID, myInvoice.getId().toString());
                myFragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();
            }
        });



    }

    @Override
    public int getItemCount() {
        return mMyInvoices.size();
    }

    public class LogHolder extends RecyclerView.ViewHolder  {

        private MyInvoice mInvoice;

        private TextView txtTitle;
        private TextView txtDate;
        private TextView txtDesc;

        public LogHolder(final View itemView){
            super(itemView);
            txtDate = (TextView) itemView.findViewById(R.id.date);
            txtDesc = (TextView) itemView.findViewById(R.id.desc);
            txtTitle = (TextView) itemView.findViewById(R.id.title);
        }

        public void bindLog(MyInvoice myInvoice){
            mInvoice = myInvoice;
            txtDate.setText("Date: "+mInvoice.getDate());
            txtTitle.setText("Title: "+mInvoice.getTitle());
            txtDesc.setText("Shop Name: "+mInvoice.getShopName());
        }

    }

    public void setLogs(List<MyInvoice> myInvoices){
        mMyInvoices = myInvoices;
    }

}
