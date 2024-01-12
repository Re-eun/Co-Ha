package org.example.coha.domain.post.service

import org.springframework.core.io.Resource
import org.springframework.web.multipart.MultipartFile

interface FileStorageService {

    fun storeFile(file: MultipartFile): String
    fun loadFile(filename: String): Resource
}