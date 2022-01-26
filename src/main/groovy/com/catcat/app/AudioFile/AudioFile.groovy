package com.catcat.app.AudioFile

import com.catcat.app.Album.Album
import com.catcat.app.AlbumCover.AlbumCover
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import org.springframework.content.commons.annotations.ContentId
import org.springframework.content.commons.annotations.ContentLength
import org.springframework.content.commons.annotations.MimeType

import javax.persistence.CollectionTable
import javax.persistence.Column
import javax.persistence.ElementCollection
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Table(name = "track")
@Entity
public class AudioFile {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    //@ManyToOne
    //@JoinColumn(name = "album_id", nullable = false)
    //private Album album;
    private String album;

    private String title;
    private Integer trackNo;
    private String artist;

    @ContentId private String contentId;
    @ContentLength private Long contentLength;
    @MimeType private String mimeType = "audio/ogg" /* ogg, webm, wave */

    public AudioFile(Long id, String title, String album, Integer trackNo, String artist) {
        this.id = id;
        this.title = title;
        this.album = album;
        this.trackNo = trackNo;
        this.artist = artist;
    }

    public AudioFile(String title, String album, Integer trackNo, String artist) {
        this.title = title;
        this.album = album;
        this.trackNo = trackNo;
        this.artist = artist;
    }

    public AudioFile() {
    }

    Long getId() {
        return id
    }

    void setId(Long id) {
        this.id = id
    }

    String getTitle() {
        return title
    }

    void setTitle(String title) {
        this.title = title
    }

    Integer getTrackNo() {
        return trackNo
    }

    void setTrackNo(Integer trackNo) {
        this.trackNo = trackNo
    }

    String getArtist() {
        return artist
    }

    void setArtist(String artist) {
        this.artist = artist
    }

    String getContentId() {
        return contentId
    }

    void setContentId(String contentId) {
        this.contentId = contentId
    }

    Long getContentLength() {
        return contentLength
    }

    void setContentLength(Long contentLength) {
        this.contentLength = contentLength
    }

    String getMimeType() {
        return mimeType
    }

    void setMimeType(String mimeType) {
        this.mimeType = mimeType
    }

    String getAlbum() {
        return album
    }

    void setAlbum(String album) {
        this.album = album
    }

    @Override
    public String toString() {
        return  "AudioFile{" +
                    "id=" + id +
                    ", title=" + title +
                    ", track_no=" + trackNo.toString() +
                    ", artist=" + artist +
                    //"album_id=" + album.getId().toString() +
                "}";
    }

}