package com.catcat.app.AlbumCover

import com.catcat.app.Album.Album
import com.catcat.app.AudioFile.AudioFile;
import lombok.Getter;
import lombok.Setter;
import org.springframework.content.commons.annotations.ContentId;
import org.springframework.content.commons.annotations.ContentLength
import org.springframework.content.commons.annotations.MimeType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToOne
import javax.persistence.Table;

@Table(name = "album_cover")
@Entity
@Getter
@Setter
public class AlbumCover {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ContentId private String contentId;
    @ContentLength private Long contentLength;
    @MimeType private String mimeType = "image/png";

    @OneToOne
    @JoinColumn(name = "cover_id", referencedColumnName = "id")
    private Album album;

    /*
    public AlbumCover(Long id, Album album) {
        this.id = id;
        this.album = album;
    }

    public AlbumCover(Album album) {
        this.album = album;
    }
     */

    @Override
    public String toString() {
        return  "Cover{" +
                "id=" + id +
                "album_id=" + album.getId().toString() +
                "}";
    }

}
