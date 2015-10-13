package com.zacharytamas.often.models

import io.realm.Realm
import io.realm.RealmMigration

/**
 * Created by zacharytamas on 10/10/15.
 */
class Migrations : RealmMigration {

    override fun execute(realm: Realm, version: Long): Long {
        return 0
    }

}
