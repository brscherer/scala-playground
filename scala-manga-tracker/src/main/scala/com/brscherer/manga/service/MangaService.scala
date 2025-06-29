package com.brscherer.manga.service

import com.brscherer.manga.model.MangaEntry
import com.brscherer.manga.repository.MangaRepository
import org.springframework.stereotype.Service

import scala.jdk.CollectionConverters.*

@Service
class MangaService(private val repo: MangaRepository) {

  def addEntry(name: String, chapter: Int): MangaEntry = {
    val entry = MangaEntry(name = name, chapter = chapter)
    repo.save(entry)
  }

  def listEntries(): List[MangaEntry] = {
    repo.findAll().asScala.toList
  }
}
