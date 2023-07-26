package com.karsatech.karsanime.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.karsatech.karsanime.core.data.source.local.entity.MangaEntity
import com.karsatech.karsanime.core.data.source.local.entity.PeopleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PeopleDao {

    @Query("SELECT * FROM favorite_people ORDER BY id_favorite_people DESC")
    fun getAllFavoritePeople(): Flow<List<PeopleEntity>>

    @Query("SELECT * from favorite_people WHERE peopleId= :peopleId")
    fun getFavoritePeopleById(peopleId : String) : Flow<List<PeopleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoritePeople(people: PeopleEntity)

    @Query("DELETE from favorite_people WHERE peopleId= :peopleId")
    fun removeFavoritePeople(peopleId: String)

}