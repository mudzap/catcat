package com.catcat.app.AudioFile

import com.catcat.app.Album.Album
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.stereotype.Repository

import javax.swing.text.html.Option

@Repository
public interface AudioFileRepository extends JpaRepository<AudioFile, Long>{

    @Query("SELECT f FROM AudioFile f WHERE f.title = ?1 AND f.album = ?2")
    Set<AudioFile> findByNameAlbum(String title, String album);

    @Query("""SELECT f FROM AudioFile f
              INNER JOIN f.album a
              WHERE f.title = ?1 AND a.title = ?2 AND f.trackNo = ?3""")
    Optional<AudioFile> findByNameAlbumTrackNo(String title, String album, Integer trackNo);

    @Query("SELECT f FROM AudioFile f")
    Set<AudioFile> getAll();

}

