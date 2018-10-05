package com.usc.myinvoices.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MyInvoiceHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "MyInvoices.db";

    public MyInvoiceHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + MyInvoiceDbSchema.InvoiceTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                MyInvoiceDbSchema.InvoiceTable.Cols.UUID + ", "+
                MyInvoiceDbSchema.InvoiceTable.Cols.INVOICE_TYPE + ", "+
                MyInvoiceDbSchema.InvoiceTable.Cols.DATE + ", "+
                MyInvoiceDbSchema.InvoiceTable.Cols.TITLE + ", "+
                MyInvoiceDbSchema.InvoiceTable.Cols.COMMENT + ", "+
                MyInvoiceDbSchema.InvoiceTable.Cols.LOCATION + ", "+
                MyInvoiceDbSchema.InvoiceTable.Cols.SHOP_NAME +
            ")"
        );


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
