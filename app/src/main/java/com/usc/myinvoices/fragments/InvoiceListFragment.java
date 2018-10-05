package com.usc.myinvoices.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.usc.myinvoices.R;
import com.usc.myinvoices.adapters.MyInvoicesAdapter;
import com.usc.myinvoices.controllers.HelpActivity;
import com.usc.myinvoices.controllers.InvoiceActivity;
import com.usc.myinvoices.lab.InvoiceLab;
import com.usc.myinvoices.models.MyInvoice;

import java.util.List;

public class InvoiceListFragment extends Fragment {

    private MyInvoicesAdapter mAdapter;
    private RecyclerView mLogRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_fragment_invoices_list,container,false);

        Button mNewButton = (Button) view.findViewById(R.id.btn_new);
        Button mHelpButton = (Button) view.findViewById(R.id.btn_help);
        mLogRecyclerView = (RecyclerView) view.findViewById(R.id.invoiceList);

        mLogRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MyInvoice a = new MyInvoice();
                InvoiceLab.get(getActivity()).addInvoice(a);
                Intent intent = InvoiceActivity.newIntent(getActivity(), a.getId());
                startActivity(intent);

            }
        });

        mHelpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = HelpActivity.newIntent(getActivity());
                startActivity(intent);

            }
        });

        updateList();

        return view;

    }

    private void updateList() {
        InvoiceLab Loglab = InvoiceLab.get(getActivity());
        List<MyInvoice> logs = Loglab.getInvoices();

        if(mAdapter == null){
            mAdapter = new MyInvoicesAdapter(getActivity(),logs);
            mAdapter.notifyDataSetChanged();
            mLogRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setLogs(logs);
            mAdapter.notifyDataSetChanged();
            mLogRecyclerView.setAdapter(mAdapter);
        }

    }

    @Override
    public void onResume(){
        super.onResume();
        updateList();
    }

}
