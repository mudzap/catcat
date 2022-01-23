package com.catcat.app.AudioFile

import com.catcat.app.AlbumCover.AlbumCoverService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.InputStreamResource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile

import javax.swing.text.html.Option

@Service
public class AudioFileService {

    @Autowired private AudioFileContentStore audioFileContentStore;
    @Autowired private AudioFileRepository audioFileRepository;
    @Autowired private AlbumCoverService albumCoverService;

    public ResponseEntity<?> addTrack(AudioFile audiofile, MultipartFile file) {
        // If already exists, raise exception
        // Assume identical tracks can exist on different albums, so no hashing comparison is required
        // Comparison function depends on: album name + song name
        return new ResponseEntity<Object>(HttpStatus.NOT_IMPLEMENTED);
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
                                         Optional<MultipartFile> audioFile,
                                         Optional<String> newTitle,
                                         Optional<String> newAlbum,
                                         Optional<Integer> newTrackNo) {
        Optional<AudioFile> f = audioFileRepository.findById(id);
        return updateTrackFields(f, audioFile, newTitle, newAlbum, newTrackNo);
    }

    public ResponseEntity<?> updateTrack(String title, String album, Optional<Integer> trackNo,
                                         Optional<MultipartFile> audioFile,
                                         Optional<String> newTitle,
                                         Optional<String> newAlbum,
                                         Optional<Integer> newTrackNo) {
        if (trackNo.isPresent()) {
            /* Track no. provided */
            Optional<AudioFile> f = audioFileRepository.getByNameAlbumTrackNo(title, album, trackNo.get());
            return updateTrackFields(f, audioFile, newTitle, newAlbum, newTrackNo);
        } else {
            /* No track no. provided (collection of entities can be returned) */
            Optional<Collection<AudioFile>> f = audioFileRepository.getByNameAlbum(title, album);
            if (f.isPresent()) {

                /* Refuses to update multiple elements in a single query to prevent user error */
                if (f.get().size < 2) {
                    /* Updates first element of collection, i.e: the only element */
                    return updateTrackFields(f, audioFile, newTitle, newAlbum, newTrackNo);
                }
                return new ResponseEntity<Object>(HttpStatus.METHOD_NOT_ALLOWED);

            }
        }
        return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
    }

    /* Helper class for updating several fields */
    private ResponseEntity<?> updateTrackFields(Optional<AudioFile> f,
                              Optional<MultipartFile> audioFile,
                              Optional<String> newTitle,
                              Optional<String> newAlbum,
                              Optional<Integer> newTrackNo) {
        if (f.isPresent()) {
            if (audioFile.isPresent()) {
                f.setMimeType(audioFile.get().getContentType());
                audioFileContentStore.setContent(f, audioFile.get().getInputStream());
                audioFileRepository.save(f);
            }
            if (newTitle.isPresent()) {
                f.setTitle(newTitle.get());
            }
            if (newAlbum.isPresent()) {
                f.setAlbum(newAlbum.get());
            }
            if (newTrackNo.isPresent()) {
                f.setTrackNo(newTrackNo.get());
            }
            return new ResponseEntity<Object>(HttpStatus.OK);
        }
        return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
    }
    /* PUT END */


    /*
     *  GET
     *  Removing a track returns OK
     *  Attempting to remove a non-existing track returns NOT FOUND
     */
    public ResponseEntity<?> getTrack(String title, String album,
                                      List<String> genres, List<String> artists) {
        Optional<AudioFile> f = audioFileRepository.getByNameAlbum(title, album);
        if (f.isPresent()) {
            InputStreamResource inputStreamResource =
                    new InputStreamResource(audioFileContentStore.getContent(f.get()));
            HttpHeaders headers = new HttpHeaders();
            headers.setContentLength(f.get().getContentLength());
            headers.set("Content-Type", f.get().getMimeType());
            return new ResponseEntity<Object>(inputStreamResource, headers, HttpStatus.OK);
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
        Optional<AudioFile> f = audioFileRepository.getById(id);
        if (f.ifPresent()) {
            audioFileRepository.delete(f.get());
            return new ResponseEntity<Object>(HttpStatus.OK);
        }
        return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> removeTrack(String title, String album, Optional<Integer> trackNo) {
        if (trackNo.isPresent()) {
            /* Track no. provided */
            Optional<AudioFile> f = audioFileRepository.getByNameAlbumTrackNo(title, album, trackNo.get());
            audioFileRepository.delete(f.get());
            return new ResponseEntity<Object>(HttpStatus.OK);

        } else {
            /* No track no. provided (collection of entities can be returned) */
            Optional<Collection<AudioFile>> f = audioFileRepository.getByNameAlbum(title, album);
            if (f.isPresent()) {

                /* Refuses to delete multiple elements in a single query to prevent user error */
                if (f.get().size < 2) {
                    /* Removes first element of collection, i.e: the only element */
                    audioFileRepository.delete(f.get().iterator().next());
                    return new ResponseEntity<Object>(HttpStatus.OK);
                }
                return new ResponseEntity<Object>(HttpStatus.METHOD_NOT_ALLOWED);

            }
        }
        return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
    }
    /* DELETE END */

}
