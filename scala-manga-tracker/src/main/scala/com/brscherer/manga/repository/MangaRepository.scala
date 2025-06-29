package com.brscherer.manga.repository

import com.brscherer.manga.model.MangaEntry
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
trait MangaRepository extends CrudRepository[MangaEntry, java.lang.Long]
