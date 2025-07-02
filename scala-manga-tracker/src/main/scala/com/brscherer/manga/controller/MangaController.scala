package com.brscherer.manga.controller

import com.brscherer.manga.domain.model.MangaEntry
import com.brscherer.manga.service.MangaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

import scala.jdk.CollectionConverters.*

@RestController
@RequestMapping(Array("/manga"))
class MangaController @Autowired(private val service: MangaService) {

  case class MangaRequest(name: String, chapter: Int)

  @PostMapping
  def add(@RequestBody req: MangaRequest): MangaEntry =
    service.addEntry(req.name, req.chapter)

  @GetMapping
  def list(): java.util.List[MangaEntry] =
    service.listEntries().asJava
}
