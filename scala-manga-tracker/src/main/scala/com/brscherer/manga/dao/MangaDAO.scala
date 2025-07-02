package com.brscherer.manga.dao

import com.brscherer.manga.domain.model.MangaEntry
import com.brscherer.manga.repository.MangaRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import scala.jdk.CollectionConverters.*

@Component
class MangaDAO @Autowired() (
                            private val mangaRepository: MangaRepository
                            ){
  def findAll(): List[MangaEntry] = {
    mangaRepository.findAll().asScala.toList
  }
  
  def findByName(name: String): List[MangaEntry] = {
    mangaRepository.findByName(name).asScala.toList
  }
  
  def save(mangaEntry: MangaEntry): MangaEntry = {
    mangaRepository.save(mangaEntry)
  }
}
