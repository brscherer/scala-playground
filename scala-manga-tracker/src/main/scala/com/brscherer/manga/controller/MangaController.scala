package com.brscherer.manga.controller

import com.brscherer.manga.model.MangaEntry
import com.brscherer.manga.service.MangaService
import org.springframework.web.bind.annotation.*
import scala.jdk.CollectionConverters._

@RestController
@RequestMapping(Array("/manga"))
class MangaController(private val service: MangaService) {

  case class MangaRequest(name: String, chapter: Int)

  @PostMapping
  def add(@RequestBody req: MangaRequest): MangaEntry =
    service.addEntry(req.name, req.chapter)

  @GetMapping
  def list(): java.util.List[MangaEntry] =
    service.listEntries().asJava
}
