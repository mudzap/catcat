package com.catcat.app.AudioFile

import com.catcat.app.Album.Album
import com.catcat.app.Album.AlbumService
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
    /*
    @PostMapping
    public ResponseEntity<?> addTrack(@RequestBody AudioFile audioFile) {
        return audioFileService.addTrack(audioFile);
    }
    */
    @PostMapping    public ResponseEntity<?> addTrack(@RequestBody List<AudioFile> audioFiles) {
        return audioFileService.addTracks(audioFiles);
    }
    /* POST END */


    /*
     *  PUT MAPPINGS
     */
    @PutMapping(path = "files/{id}")
    public ResponseEntity<?> updateTrack(@PathVariable("id") Long id,
                                         @RequestParam("file") Optional<MultipartFile> file,
                                         @RequestParam("title") Optional<String> title,
                                         @RequestParam("album_title") Optional<String> albumTitle,
                                         @RequestParam("track_no") Optional<String> trackNo) {
        return audioFileService.updateTrack(id, file, title, albumTitle, trackNo);
    }
    /* PUT END */


    /*
     *  DELETE MAPPINGS
     */
    @DeleteMapping(path = "{id}")
    public ResponseEntity<?> removeTrack(@PathVariable("id") Long id) {
        return audioFileService.removeTrack(id);
    }
    /* DELETE END */


    /*
     *  GET MAPPINGS
     */
    @GetMapping(path = "files/{id}")
    public ResponseEntity<?> getTrack(@PathVariable("id") Long id) {
        return audioFileService.getTrack(id);
    }

    @GetMapping
    public ResponseEntity<?> getTrack(
            @RequestParam("title") String title,
            @RequestParam("album") String album_title) {
        return audioFileService.getTrack(title, album_title);
    }

    @GetMapping(path = "all")
    public ResponseEntity<?> getAllTracks() {
        return audioFileService.getAllTracks();
    }
    /* GET END */

}