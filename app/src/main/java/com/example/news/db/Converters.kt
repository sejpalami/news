package com.example.news.db

import androidx.room.TypeConverter
import com.example.news.models.Images
import com.example.news.models.TypeAttributes

class Converters {

    @TypeConverter
    fun fromImages(images: Images): String {
        return images.square_140
    }

    @TypeConverter
    fun toImages(images: String): Images {
        return Images(images)
    }

    @TypeConverter
    fun fromTypeAttributes(typeAttributes: TypeAttributes): String {
        return typeAttributes.url
    }

    @TypeConverter
    fun toTypeAttributes(typeAttributes: String): TypeAttributes {
        return TypeAttributes(typeAttributes, typeAttributes)
    }
}