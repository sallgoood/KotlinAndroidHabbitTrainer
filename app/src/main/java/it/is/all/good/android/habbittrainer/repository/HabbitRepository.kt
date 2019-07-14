package it.`is`.all.good.android.habbittrainer.repository

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import it.`is`.all.good.android.habbittrainer.Habbit
import it.`is`.all.good.android.habbittrainer.repository.HabbitEntity.DESCR_COL
import it.`is`.all.good.android.habbittrainer.repository.HabbitEntity.IMAGE_COL
import it.`is`.all.good.android.habbittrainer.repository.HabbitEntity.TITLE_COL
import it.`is`.all.good.android.habbittrainer.repository.HabbitEntity.TABLE_NAME
import it.`is`.all.good.android.habbittrainer.repository.HabbitEntity._ID
import java.io.ByteArrayOutputStream
import java.util.*

class HabbitRepository(conext: Context) {

    private val TAG = HabbitRepository::class.java.simpleName

    private val database = HabbitTrainerDatabase(conext)

    fun save(habbit: Habbit): Long {
        val values = ContentValues()
        with(values) {
            put(TITLE_COL, habbit.title)
            put(DESCR_COL, habbit.description)
            put(IMAGE_COL, toByteArray(habbit.image))
        }

        val write = database.writableDatabase

        val id = write.transaction {
            insert(TABLE_NAME, null, values)
        }

        Log.d(TAG, "Stored new habbit to the DB $habbit")

        return id
    }

    fun findAllHabbits(): List<Habbit> {
        val columns = arrayOf(_ID, TITLE_COL, DESCR_COL, IMAGE_COL)

        val order = "$_ID ASC"

        val read = database.readableDatabase

        val cursor = read.doQuery(TABLE_NAME, columns, orderBy = order)

        return pareHabbits(cursor)
    }

    private fun pareHabbits(cursor: Cursor): MutableList<Habbit> {
        val habbits = mutableListOf<Habbit>()
        while (cursor.moveToNext()) {

            val title = cursor.getString(TITLE_COL)
            val description = cursor.getString(DESCR_COL)
            val image = cursor.getBitmap(IMAGE_COL)

            habbits.add(Habbit(title, description, image))
        }
        cursor.close()
        return habbits
    }

    private fun toByteArray(bitmap: Bitmap): ByteArray {
        val output = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, output)
        return output.toByteArray()
    }
}

private inline fun <T> SQLiteDatabase.transaction(function: SQLiteDatabase.() -> T): T {
    beginTransaction()
    return try {
        val result = function()
        setTransactionSuccessful()

        result
    } finally {
        endTransaction()
        close()
    }
}

private fun SQLiteDatabase.doQuery(
    table: String, columns: Array<String>, selection: String? = null,
    selectionArgs: Array<String>? = null, groupBy: String? = null,
    having: String? = null, orderBy: String? = null
): Cursor {

    return query(table, columns, selection, selectionArgs, groupBy, having, orderBy)
}

private fun Cursor.getString(columnName: String): String =
    this.getString(getColumnIndex(columnName))

private fun Cursor.getBitmap(columnName: String): Bitmap {
    val bytes = getBlob(getColumnIndex(columnName))
    return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
}