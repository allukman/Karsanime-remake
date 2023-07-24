package com.karsatech.karsanime.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.karsatech.karsanime.core.data.source.local.entity.MangaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MangaDao {

    @Query("SELECT * FROM favorite_manga")
    fun getAllFavoriteManga(): Flow<List<MangaEntity>>

    @Query("SELECT * from favorite_manga WHERE mangaId= :mangaId")
    fun getFavoriteById(mangaId : String) : Flow<List<MangaEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteManga(manga: MangaEntity)

    @Query("DELETE from favorite_manga WHERE mangaId= :mangaId")
    fun removeFavoriteManga(mangaId: String)

}