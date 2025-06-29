package com.brscherer.manga.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("manga_entries")
case class MangaEntry(
                       @Id id: java.lang.Long = null,
                       name: String,
                       chapter: Int
                     )
