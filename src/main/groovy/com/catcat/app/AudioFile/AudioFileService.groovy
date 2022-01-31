package com.catcat.app.AudioFile

import com.catcat.app.Album.Album
import com.catcat.app.Album.AlbumRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.InputStreamResource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

import javax.swing.text.html.Option

@Service
public class AudioFileService {

    @Autowired private AlbumRepository albumRepository;
    @Autowired private AudioFileContentStore audioFileContentStore;
    @Autowired private AudioFileRepository audioFileRepository;

    public ResponseEntity<?> addTrack(AudioFile audioFile) {
        // If already exists, raise exception
        // Assume identical tracks can exist on different albums, so no hashing comparison is required
        // Comparison function depends on: album name + song name
        Optional<AudioFile> f = audioFileRepository.findByNameAlbumTrackNo(
                audioFile.getTitle(),
                audioFile.getAlbum().getTitle(),
                audioFile.getTrackNo()
        );
        if (f.isPresent()) {
            return new ResponseEntity<Object>(HttpStatus.METHOD_NOT_ALLOWED);
        } else {
            Optional<Album> a = albumRepository.findByNameArtist(
                    audioFile.getAlbum().getTitle(),
                    audioFile.getAlbum().getArtist()
            );
            if (a.isPresent()) {
                audioFile.setAlbum(a.get());
            } else {
                albumRepository.save(audioFile.getAlbum());
            }
            audioFileRepository.save(audioFile);
        }

        return new ResponseEntity<Object>(HttpStatus.OK);
    }

    public ResponseEntity<?> addTracks(List<AudioFile> audioFiles) {
        for (AudioFile audioFile : audioFiles) {
            if (addTrack(audioFile).statusCode != HttpStatus.OK)
                return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Object>(HttpStatus.OK);
    }


    /*
     *  PUT
     *  Individual tracks can be updated by id or title + album + optionally track no.
     *  Track no. is necessary for instances where title is repeated, for example:
     *      Universal Indicator – Innovation In The Dynamics Of Acid
     *
     *  Updatable fields include:
     *      content
     *      title
     *      album
     *      track_no
     *
     *  Updating a track returns OK
     *  Attempting to update a non-existing track returns NOT FOUND
     *  Attempting to update several tracks returns METHOD NOT ALLOWED
     */
    public ResponseEntity<?> updateTrack(Long id,
                                         Optional<MultipartFile> file,
                                         Optional<String> title,
                                         Optional<String> album,
                                         Optional<Integer> trackNo) {
        Optional<AudioFile> f = audioFileRepository.findById(id);
        if(f.isPresent()) {
            if (title.isPresent()) {
                f.get().setTitle(title.get());
            }
            if (album.isPresent()) {
                Optional<Album> a = albumRepository.findByName(album.get());
                if (a.isPresent()) {
                    f.get().setAlbum(a.get());
                }
            }
            if (trackNo.isPresent()) {
                f.get().setTrackNo(trackNo.get());
            }
            if (file.isPresent()) {
                MultipartFile existing_file = file.get();
                f.get().setMimeType(existing_file.getContentType());
                audioFileContentStore.setContent(f.get(), existing_file.getInputStream());
            }
            audioFileRepository.save(f.get());
            return new ResponseEntity<Object>(HttpStatus.OK);
        }
        return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
    }
    /*
    public ResponseEntity<?> updateTrack(String title, Album album, Optional<Integer> trackNo,
                                         Optional<MultipartFile> audioFile,
                                         Optional<String> newTitle,
                                         Optional<Album> newAlbum,
                                         Optional<Integer> newTrackNo) {
        if (trackNo.isPresent()) {
            // Track no. provided
            Optional<AudioFile> f = audioFileRepository.findByNameAlbumTrackNo(title, album, trackNo.get());
            return updateTrackFields(f.get(), audioFile, newTitle, newAlbum, newTrackNo);
        } else {
            // No track no. provided (collection of entities can be returned)
            Set<AudioFile> f = audioFileRepository.findByNameAlbum(title, album);
            if (f.size() > 0) {

                // Refuses to update multiple elements in a single query to prevent user error
                if (f.size() < 2) {
                    // Updates first element of collection, i.e: the only element
                    return updateTrackFields(f.iterator().next(), audioFile, newTitle, newAlbum, newTrackNo);
                }
                return new ResponseEntity<Object>(HttpStatus.METHOD_NOT_ALLOWED);

            }
        }
        return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
    }
    */

    /* Helper class for updating several fields */
    private ResponseEntity<?> updateTrackFields(AudioFile f,
                                                Optional<MultipartFile> file,
                                                Optional<String> newTitle,
                                                Optional<Album> newAlbum,
                                                Optional<Integer> newTrackNo) {
        if (newTitle.isPresent()) {
            f.setTitle(newTitle.get());
        }
        if (newAlbum.isPresent()) {
            f.setAlbum(newAlbum.get());
        }
        if (newTrackNo.isPresent()) {
            f.setTrackNo(newTrackNo.get());
        }
        if (file.isPresent()) {
            MultipartFile existing_file = file.get();
            f.setMimeType(existing_file.getContentType());
            audioFileContentStore.setContent(f, existing_file.getInputStream());
        }
        audioFileRepository.save(f);
        return new ResponseEntity<Object>(HttpStatus.OK);
    }
    /* PUT END */


    /*
     *  GET
     *  Removing a track returns OK
     *  Attempting to remove a non-existing track returns NOT FOUND
     */
    public ResponseEntity<?> getTrack(Long id) {
        Optional<AudioFile> f = audioFileRepository.findById(id);
        if (f.isPresent()) {
            InputStreamResource inputStreamResource = new InputStreamResource(audioFileContentStore.getContent(f.get()));
            HttpHeaders headers = new HttpHeaders();
            headers.setContentLength(f.get().getContentLength());
            headers.set("Content-Type", f.get().getMimeType());
            return new ResponseEntity<Object>(inputStreamResource, headers, HttpStatus.OK);
        }
        return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> getAllTracks() {
        return new ResponseEntity<String>(audioFileRepository.getAll().toString(), HttpStatus.OK);
    }

    public ResponseEntity<?> getTrack(String title,
                           String album_title) {
        Optional<Album> album = albumRepository.findByName(album_title);
        if (album.isPresent()) {
            String s = audioFileRepository.findByNameAlbum(title, album.get().getTitle()).toString();
            return new ResponseEntity<String>(s, HttpStatus.OK);
        }
        return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
    }
    /* GET END */


    /*
     *  DELETE
     *  Individual tracks can be removed by id or title + album + optionally track no.
     *  Removing a track returns OK
     *  Attempting to remove a non-existing track returns NOT FOUND
     *  Attempting to remove several tracks returns METHOD NOT ALLOWED
     */

    public ResponseEntity<?> removeTrack(Long id) {
        Optional<AudioFile> f = audioFileRepository.findById(id);
        if (f.ifPresent()) {
            audioFileRepository.delete(f.get());
            return new ResponseEntity<Object>(HttpStatus.OK);
        }
        return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
    }

    /* DELETE END */

}
