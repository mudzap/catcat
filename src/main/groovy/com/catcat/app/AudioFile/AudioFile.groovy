package com.catcat.app.AudioFile

import com.catcat.app.AlbumCover.AlbumCover
import lombok.Getter
import lombok.Setter
import org.springframework.content.commons.annotations.ContentId
import org.springframework.content.commons.annotations.ContentLength

import javax.persistence.CollectionTable
import javax.persistence.Column
import javax.persistence.ElementCollection
import javax.persistence.JoinColumn
import javax.persistence.OneToMany
import javax.persistence.Table;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Table(name = "tracks")
@Entity
@Getter
@Setter
public class AudioFile {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String title;
    private String album;
    private Integer trackNo;

    @ElementCollection
    @CollectionTable(name="genre_tracks", joinColumns = @JoinColumn(name="track_id"))
    @Column(name="genre")
    private Set<String> genres;

    @ElementCollection
    @CollectionTable(name="artist_tracks", joinColumns = @JoinColumn(name="track_id"))
    @Column(name="genre")
    private Set<String> artists;

    @OneToMany(mappedBy = "tracks")
    private AlbumCover cover;

    @ContentId private String contentId;
    @ContentLength private Long contentLength;
    private String mimeType = "audio/ogg" /* ogg, webm, wave */

    public AudioFile(Long id, String title, String album, Integer trackNo, List<String> genres, List<String> artists) {
        this.id = id;
        this.title = title;
        this.album = album;
        this.trackNo = trackNo;
        this.genres = genres;
        this.artists = artists;
    }

    public AudioFile(String title, String album, Integer trackNo, List<String> genres, List<String> artists) {
        this.title = title;
        this.album = album;
        this.trackNo = trackNo;
        this.genres = genres;
        this.artists = artists;
    }

    @Override
    public String toString() {
        return  "AudioFile{" +
                    "id=" + id +
                    "title=" + title +
                    "album=" + album +
                    "no=" + trackNo +
                    "genres=" + genres.toString() +
                    "artists=" + artists.toString() +
                    "cover=" + cover.toString() +
                "}";       
    }

}