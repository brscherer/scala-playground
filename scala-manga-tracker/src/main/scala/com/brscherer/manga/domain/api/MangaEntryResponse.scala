package com.brscherer.manga.domain.api

import java.util.UUID

case class MangaEntryResponse (
  id: UUID,
  name: String,
  chapter: Int,
)
