package com.catcat.app.AudioFile

import org.springframework.content.commons.repository.ContentStore
import org.springframework.stereotype.Repository

@Repository
public interface AudioFileContentStore extends ContentStore<AudioFile, String> {
}