package com.usc.myinvoices.lab;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.usc.myinvoices.cursors.InvoiceCursorWrapper;
import com.usc.myinvoices.database.MyInvoiceDbSchema;
import com.usc.myinvoices.database.MyInvoiceHelper;
import com.usc.myinvoices.models.MyInvoice;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InvoiceLab {

    private static InvoiceLab mMyInvoicesLab;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static InvoiceLab get(Context context){
        if(mMyInvoicesLab == null){
            mMyInvoicesLab = new InvoiceLab(context);
        }
        return mMyInvoicesLab;
    }

    private InvoiceLab(Context context){

        mContext = context.getApplicationContext();
        mDatabase = new MyInvoiceHelper(mContext).getWritableDatabase();

    }

    public MyInvoice addInvoice(MyInvoice t){
        ContentValues values = getContentValues(t);

        mDatabase.insert(MyInvoiceDbSchema.InvoiceTable.NAME,null,values);
        return t;
    }

    public List<MyInvoice> getInvoices(){
        List<MyInvoice> activities = new ArrayList<>();
        InvoiceCursorWrapper cursor = queryInvoice(null,null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                activities.add(cursor.getActivity());
                cursor.moveToNext();
            }
        } finally {

        }

        return activities;

    }

    public MyInvoice getInvoices(UUID id){

        InvoiceCursorWrapper cursor = queryInvoice(MyInvoiceDbSchema.InvoiceTable.Cols.UUID + " =?",new String[]{ id.toString() });

        try{
            if(cursor.getCount() == 0){
                return null;
            }
            cursor.moveToFirst();
            return cursor.getActivity();

        } finally {
            cursor.close();
        }

    }

    public void updateLog(MyInvoice myInvoice){

        String uuidString = myInvoice.getId().toString();
        ContentValues values = getContentValues(myInvoice);

        mDatabase.update(MyInvoiceDbSchema.InvoiceTable.NAME, values, MyInvoiceDbSchema.InvoiceTable.Cols.UUID + " =?", new String[] { uuidString });

    }

    public void deleteLog(MyInvoice myInvoice){
        String uuidString = myInvoice.getId().toString();
        mDatabase.delete(MyInvoiceDbSchema.InvoiceTable.NAME, MyInvoiceDbSchema.InvoiceTable.Cols.UUID + " =?", new String[] { uuidString });
    }

    private static ContentValues getContentValues(MyInvoice myInvoice){

        ContentValues values = new ContentValues();
        values.put(MyInvoiceDbSchema.InvoiceTable.Cols.UUID,myInvoice.getId().toString());
        values.put(MyInvoiceDbSchema.InvoiceTable.Cols.TITLE,myInvoice.getTitle());
        values.put(MyInvoiceDbSchema.InvoiceTable.Cols.COMMENT,myInvoice.getComment());
        values.put(MyInvoiceDbSchema.InvoiceTable.Cols.LOCATION,myInvoice.getLocation());
        values.put(MyInvoiceDbSchema.InvoiceTable.Cols.INVOICE_TYPE,myInvoice.getType());
        values.put(MyInvoiceDbSchema.InvoiceTable.Cols.DATE,myInvoice.getDate());
        values.put(MyInvoiceDbSchema.InvoiceTable.Cols.SHOP_NAME,myInvoice.getShopName());

        return values;
    }

    private InvoiceCursorWrapper queryInvoice(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(
                MyInvoiceDbSchema.InvoiceTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new InvoiceCursorWrapper(cursor);
    }

}
