package com.catcat.app.AlbumCover

import com.catcat.app.AudioFile.AudioFile;
import lombok.Getter;
import lombok.Setter;
import org.springframework.content.commons.annotations.ContentId;
import org.springframework.content.commons.annotations.ContentLength;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table;

@Table(name = "album_covers")
@Entity
@Getter
@Setter
public class AlbumCover {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "track_id")
    private Set<AudioFile> audioFiles;

    @ContentId private String contentId;
    @ContentLength private Long contentLength;
    private String mimeType = "image/png";

    public AlbumCover(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return  "Cover{" +
                "id=" + id +
                "}";
    }

}
