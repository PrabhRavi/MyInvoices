package com.usc.myinvoices.cursors;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.usc.myinvoices.database.MyInvoiceDbSchema;
import com.usc.myinvoices.models.MyInvoice;

import java.util.UUID;


public class InvoiceCursorWrapper extends CursorWrapper {

    public InvoiceCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public MyInvoice getActivity(){

        String date = getString(getColumnIndex(MyInvoiceDbSchema.InvoiceTable.Cols.DATE));
        String type = getString(getColumnIndex(MyInvoiceDbSchema.InvoiceTable.Cols.INVOICE_TYPE));
        String title = getString(getColumnIndex(MyInvoiceDbSchema.InvoiceTable.Cols.TITLE));
        String comment = getString(getColumnIndex(MyInvoiceDbSchema.InvoiceTable.Cols.COMMENT));
        String uuidString  = getString(getColumnIndex(MyInvoiceDbSchema.InvoiceTable.Cols.UUID));
        String location = getString(getColumnIndex(MyInvoiceDbSchema.InvoiceTable.Cols.LOCATION));
        String shop_name = getString(getColumnIndex(MyInvoiceDbSchema.InvoiceTable.Cols.SHOP_NAME));

        MyInvoice invoice = new MyInvoice(UUID.fromString(uuidString));
        invoice.setType(type);
        invoice.setTitle(title);
        invoice.setComment(comment);
        invoice.setLocation(location);
        invoice.setShopName(shop_name);
        invoice.setDate(date);
        return invoice;

    }

}
