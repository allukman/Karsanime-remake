package com.karsatech.karsanime.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.karsatech.karsanime.core.data.source.local.entity.CharacterEntity
import com.karsatech.karsanime.core.data.source.local.entity.PeopleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {

    @Query("SELECT * FROM favorite_character ORDER BY id_favorite_character DESC")
    fun getAllFavoriteCharacter(): Flow<List<CharacterEntity>>

    @Query("SELECT * from favorite_character WHERE characterId= :characterId")
    fun getFavoriteCharacterById(characterId : String) : Flow<List<CharacterEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteCharacter(character: CharacterEntity)

    @Query("DELETE from favorite_character WHERE characterId= :characterId")
    fun removeFavoriteCharacter(characterId: String)

}