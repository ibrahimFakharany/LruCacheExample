package app.fakharany.com.lrucacheexample

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


class Entries(@PrimaryKey
              val key: String,
              val value: String)
    : RealmObject()