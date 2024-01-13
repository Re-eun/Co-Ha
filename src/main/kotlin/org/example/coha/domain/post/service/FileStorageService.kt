package org.example.coha.domain.post.service

import org.springframework.core.io.Resource
import org.springframework.web.multipart.MultipartFile

//파일 저장 및 로드 서비스를 정의하는 인터페이스인 FileStorageService
interface FileStorageService {

    // MultipartFile을 받아 파일을 저장하고, 저장된 파일의 경로를 반환하는 메소드
    fun storeFile(file: MultipartFile): String
    // 주어진 파일 이름을 기반으로 파일을 로드하고 Resource 형태로 반환하는 메소드
    fun loadFile(filename: String): Resource

    fun storesupaFile(file: MultipartFile): String
}