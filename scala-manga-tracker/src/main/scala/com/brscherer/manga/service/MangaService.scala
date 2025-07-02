package com.brscherer.manga.service

import com.brscherer.manga.dao.MangaDAO
import com.brscherer.manga.domain.model.MangaEntry
import org.springframework.stereotype.Service

@Service
class MangaService(private val mangaDAO: MangaDAO) {

  def addEntry(name: String, chapter: Int): MangaEntry = {
    val entry = MangaEntry(name = name, chapter = chapter)
    mangaDAO.save(entry)
  }

  def listEntries(): List[MangaEntry] = {
    mangaDAO.findAll()
  }
}
