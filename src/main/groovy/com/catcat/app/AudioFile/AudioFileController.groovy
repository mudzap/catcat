package com.catcat.app.AudioFile

import com.catcat.app.Album.Album
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.InputStreamResource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping(path = "audio")
public class AudioFileController {

    @Autowired private AudioFileService audioFileService;

    /*
     *  POST MAPPINGS
     */
    @PostMapping
    public ResponseEntity<?> addTrack(@RequestBody AudioFile audioFile) {
        return audioFileService.addTrack(audioFile);
    }
    /* POST END */


    /*
     *  PUT MAPPINGS
     */
    @PutMapping(path = "id")
    public ResponseEntity<?> updateTrack(@PathVariable("id") Long id,
                                         @RequestParam("new_file") Optional<MultipartFile> newFile,
                                         @RequestParam("new_title") Optional<String> newTitle,
                                         @RequestParam("new_album") Optional<String> newAlbum,
                                         @RequestParam("new_track_no") Optional<Integer> newTrackNo) {
        println(id);
        return audioFileService.updateTrack(id, newFile, newTitle, newAlbum, newTrackNo);
    }
    @PutMapping
    public ResponseEntity<?> updateTrack(
            @RequestParam("title") String title,
            @RequestParam("track_no") Optional<Integer> trackNo,
            @RequestParam("album") String album,
            @RequestParam("new_file") Optional<MultipartFile> newFile,
            @RequestParam("new_title") Optional<String> newTitle,
            @RequestParam("new_track_no") Optional<Integer> newTrackNo,
            @RequestParam("new_album") Optional<String> newAlbum) {
        return audioFileService.updateTrack(title, album, trackNo, newFile, newTitle, newAlbum, newTrackNo);
    }
    /* PUT END */


    /*
     *  DELETE MAPPINGS
     */
    @DeleteMapping(path = "id")
    public ResponseEntity<?> removeTrack(@PathVariable("id") Long id) {
        return audioFileService.removeTrack(id);
    }
    @DeleteMapping
    public ResponseEntity<?> removeTrack(
            @RequestParam("title") String title,
            @RequestParam("album") String album,
            @RequestParam("track_no") Optional<Integer> trackNo) {
        return audioFileService.removeTrack(title, album, trackNo);
    }
    /* DELETE END */


    /*
     *  GET MAPPINGS
     */
    @GetMapping(path = "id")
    public ResponseEntity<?> getTrack(@PathVariable("id") Long id) {
        return audioFileService.getTrack(id);
    }

    @GetMapping
    public ResponseEntity<?> getTrack(
            @RequestParam("title") Optional<String> title,
            @RequestParam("track_no") Optional<Integer> trackNo,
            @RequestParam("album") Optional<String> album,
            @RequestParam("artist") Optional<String> artist,
            @RequestParam("genre") Optional<String> genre,
            @RequestParam("style") Optional<String> style,
            @RequestParam("label") Optional<String> label) {
        //return audioFileService.getTrack(title, album, trackNo);
        return new ResponseEntity<Object>(HttpStatus.NOT_IMPLEMENTED);
    }
    /* GET END */

}