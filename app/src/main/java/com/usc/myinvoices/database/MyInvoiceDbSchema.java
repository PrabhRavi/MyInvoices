package com.usc.myinvoices.database;


public class MyInvoiceDbSchema {

    public static final class InvoiceTable {
        public static final String NAME = "invoices";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String INVOICE_TYPE = "invoice_type";
            public static final String DATE = "date";
            public static final String TITLE = "title";
            public static final String COMMENT = "comment";
            public static final String LOCATION = "location";
            public static final String SHOP_NAME = "shop_name";
        }
    }

}
