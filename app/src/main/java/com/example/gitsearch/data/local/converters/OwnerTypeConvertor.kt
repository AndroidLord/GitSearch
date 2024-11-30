package com.example.gitsearch.data.local.converters

import androidx.room.TypeConverter
import com.example.gitsearch.data.local.entity.OwnerEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class OwnerTypeConvertor{

    @TypeConverter
    fun fromOwnerEntity(ownerEntity: OwnerEntity): String {
        return Gson().toJson(ownerEntity)
    }

    @TypeConverter
    fun toOwnerEntity(owner: String): OwnerEntity {
        val type = object : TypeToken<OwnerEntity>() {}.type
        return Gson().fromJson(owner, type)
    }

}