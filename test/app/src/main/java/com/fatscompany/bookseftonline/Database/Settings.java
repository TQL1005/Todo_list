package com.fatscompany.bookseftonline.Database;

public class Settings {
    //book
    protected static final String TABLE_NAME_01="book";
    protected static final String COLUMN_ID="id";
    protected static final String COLUMN_TITLE="title";
    protected static final String COLUMN_DECRIPTION="decription";
    protected static final String COLUMN_PRICE="price";
    protected static final String COLUMN_AUTHORS="authors";
    protected static final String COLUMN_PUBLICATION_YEAR="publication_year";
    protected static final String COLUMN_CONDITION="condition";
    protected static final String COLUMN_CATEGORY_ID="category_id";
    protected static final String COLUMN_PUBLISHER_ID="publisher_id";
    protected static final String COLUMN_IMAGE="image";

    //category
    protected static final String TABLE_NAME_02="category";
    protected static final String COLUMN_NAME="NAME";

    //inventory

    protected static final String TABLE_NAME_03="inventory";
    protected static final String COLUMN_STOCK="stock";
    protected static final String COLUMN_BOOK_ID="book_id";

    //order_detail
    protected static final String TABLE_NAME_04="order_detail";
    protected static final String COLUMN_SALEORDER_ID="sale_order_id";
    protected static final String COLUMN_AMOUNT="amount";

    //sale_order
    protected static final String TABLE_NAME_05="sale_order";
    protected static final String COLUMN_CREATED_DATE="created_date";
    protected static final String COLUMN_USER_ID="user_id";

    //user
    protected static final String TABLE_NAME_06="user";
    protected static final String COLUMN_USERNAME="username";
    protected static final String COLUMN_PASSWORD="password";
    protected static final String COLUMN_FIRSTNAME="first_name";
    protected static final String COLUMN_LASTNAME="last_name";
    protected static final String COLUMN_EMAIL="email";
    protected static final String COLUMN_PHONE="phone";
    protected static final String COLUMN_ACTIVE="active";
    protected static final String COLUMN_USEROLE="user_role";

    //publishers
    protected static final String TABLE_NAME_07="publishers";
}
