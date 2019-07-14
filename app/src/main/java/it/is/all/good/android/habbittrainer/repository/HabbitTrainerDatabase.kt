package it.`is`.all.good.android.habbittrainer.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class HabbitTrainerDatabase(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private val TAG = HabbitTrainerDatabase::class.java.simpleName

    private val SQL_CREATE_HABBIT_TABLE = "CREATE TABLE ${HabbitEntity.TABLE_NAME} (" +
            "${HabbitEntity._ID} INTEGER PRIMARY KEY, " +
            "${HabbitEntity.TITLE_COL} TEXT, " +
            "${HabbitEntity.DESCR_COL} TEXT, " +
            "${HabbitEntity.IMAGE_COL} BLOB " +
            ")"

    private val SQL_DELETE_ENTRITES = "DROP TABLE IF EXISTS ${HabbitEntity.TABLE_NAME}"

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(SQL_CREATE_HABBIT_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL(SQL_DELETE_ENTRITES)
        onCreate(db)
    }
}