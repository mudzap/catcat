package com.catcat.app.AudioFile

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.stereotype.Repository

import javax.swing.text.html.Option

@Repository
public interface AudioFileRepository extends JpaRepository<AudioFile, Long>{

    @Query("SELECT f FROM AudioFile f WHERE f.title = ?1 AND f.album = ?2")
    Collection<AudioFile> getByNameAlbum(String title, String album);

    @Query("SELECT f FROM AudioFile f WHERE f.title = ?1 AND f.album = ?2 AND f.trackNo = ?3")
    Optional<AudioFile> getByNameAlbumTrackNo(String title, String album, Integer trackNo);

}

