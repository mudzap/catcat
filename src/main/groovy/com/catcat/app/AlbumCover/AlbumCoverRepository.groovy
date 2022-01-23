package com.catcat.app.AlbumCover

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
public interface AlbumCoverRepository extends JpaRepository<AlbumCover, Long>{
}
