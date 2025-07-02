package com.brscherer.manga.repository

import com.brscherer.manga.domain.model.MangaEntry
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
trait MangaRepository extends CrudRepository[MangaEntry, String] {
  @Query("SELECT * FROM MANGA_ENTRIES WHERE name = :name")
  def findByName(name: String): java.util.List[MangaEntry]
}
