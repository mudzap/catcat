package com.catcat.app.AudioFile

import org.springframework.content.commons.repository.ContentStore
import org.springframework.content.rest.StoreRestResource

@StoreRestResource
public interface AudioFileContentStore extends ContentStore<AudioFile, String> {
}