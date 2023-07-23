package com.karsatech.karsanime.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.karsatech.karsanime.core.data.source.local.entity.AnimeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeDao {

    @Query("SELECT * FROM favorite_anime")
    fun getAllFavoriteAnime(): Flow<List<AnimeEntity>>

    @Query("SELECT * from favorite_anime WHERE animeId= :animeId")
    fun getFavoriteById(animeId : String) : Flow<List<AnimeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteAnime(anime: AnimeEntity)

    @Query("DELETE from favorite_anime WHERE animeId= :animeId")
    fun removeFavoriteAnime(animeId: String)

}