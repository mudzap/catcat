package com.catcat.app.AudioFile

import com.catcat.app.Album.Album
import org.springframework.content.commons.annotations.ContentId
import org.springframework.content.commons.annotations.ContentLength
import org.springframework.content.commons.annotations.MimeType

import javax.persistence.CascadeType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Table(name = "track")
@Entity
public class AudioFile {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;

    private String title;
    private Integer trackNo;
    private String artist;

    @ContentId private String contentId;
    @ContentLength private Long contentLength;
    @MimeType private String mimeType = "audio/ogg" /* ogg, webm, wave */

    public AudioFile(Long id, String title, Album album, Integer trackNo, String artist) {
        this.id = id;
        this.title = title;
        this.album = album;
        this.trackNo = trackNo;
        this.artist = artist;
    }

    public AudioFile(String title, Album album, Integer trackNo, String artist) {
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

    Album getAlbum() {
        return album
    }

    void setAlbum(Album album) {
        /*
        if (sameAlbum(album)) {
            return;
        }
         */
        this.album = album;
        /*
        Album oldAlbum = this.album;
        if (oldAlbum!=null) {
            oldAlbum.setAudioFile(null);
        }
        if (this.album != null) {
            this.album.setAudioFile(this);
        }
         */
    }

    private boolean sameAlbum(Album newAlbum) {
        if (album == null) {
            return newAlbum == null;
        }
        return album.equals(newAlbum);
    }

    @Override
    public String toString() {
        return  "AudioFile{" +
                    "id=" + id +
                    ", title=" + title +
                    ", track_no=" + trackNo.toString() +
                    ", artist=" + artist +
                    ", album=" + album.toString() +
                "}";
    }

}