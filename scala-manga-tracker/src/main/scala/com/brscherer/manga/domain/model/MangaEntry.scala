package com.brscherer.manga.domain.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.{Column, Table}

import java.util.UUID
import scala.annotation.meta.field

@Table("MANGA_ENTRIES")
case class MangaEntry(
                       @(Id @field) 
                       id: UUID = null,
                     
                       @(Column @field)
                       name: String = null,
                     
                       @(Column @field)
                       chapter: Int = 0,
                     )
