package com.brscherer.manga.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("MANGA_ENTRIES")
case class MangaEntry(
                       @Id id: java.lang.Long = null,
                       name: String,
                       chapter: Int
                     )
