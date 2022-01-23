package com.catcat.app.AlbumCover

import com.catcat.app.AudioFile.AudioFile
import com.catcat.app.AudioFile.AudioFileRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.InputStreamResource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class AlbumCoverService {

    @Autowired private AudioFileRepository audioFileRepository;
    @Autowired private AlbumCoverRepository albumCoverRepository;
    @Autowired private AlbumCoverContentStore albumCoverContentStore;

    public ResponseEntity<?> addImage(AudioFile audiofile, MultipartFile file) {
        // If already exists, raise exception
        // Assume identical tracks can exist on different albums, so no hashing comparison is required
        // Comparison function depends on: album name + song name
        return new ResponseEntity<Object>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<?> updateImage(Long id, MultipartFile audioFile) {
        Optional<AudioFile> f = audioFileRepository.findById(id);
        if(f.isPresent()) {
            f.get().setMimeType(audioFile.getContentType());
            audioFileContentStore.setContent(f.get(), audioFile.getInputStream());
            audioFileRepository.save(f.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
    }

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

    public ResponseEntity<?> removeTrack(Long id) {
        Optional<AudioFile> f = audioFileRepository.getById(id);
        if (f.ifPresent()) {
            audioFileRepository.deleteById(id);
            return new ResponseEntity<Object>(HttpStatus.OK);
        }
        return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
    }

}
