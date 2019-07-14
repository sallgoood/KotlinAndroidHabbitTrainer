package it.`is`.all.good.android.habbittrainer.repository

import android.provider.BaseColumns

val DATABASE_NAME = "habbittrainer.db"
val DATABASE_VERSION = 10

object HabbitEntity : BaseColumns {
    val TABLE_NAME = "habbit"
    val _ID = "id"
    val TITLE_COL = "title"
    val DESCR_COL = "description"
    val IMAGE_COL = "image"
}