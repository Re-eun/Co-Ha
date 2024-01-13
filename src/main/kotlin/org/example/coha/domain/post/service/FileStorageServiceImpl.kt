package org.example.coha.domain.post.service

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.*
import kotlin.time.Duration.Companion.seconds


@Service
class FileStorageServiceImpl(
        @Value("\${file.upload-dir}") //파일 업로드 디렉토리 경로를 외부 설정 파일(application.yml)에서 읽어와서 uploadDir 변수에 주입함
        private val uploadDir: String
) : FileStorageService {

    // 생성자에서 초기화 수행
    init {
        //업로드 디렉토리가 없으면 생성
        //업로드 디렉토리의 경로를 나타내는 객체를 생성
        val uploadPath = Paths.get(uploadDir)
        //업로드 디렉토리가 존재하지 않는다면
        if (!Files.exists(uploadPath)) {
            // 업로드 디렉토리를 생성함
            Files.createDirectories(uploadPath)
        }
    }


    override fun storesupaFile(file: MultipartFile): String {

        val supabaseClient = createSupabaseClient(
                supabaseUrl = "https://dkwzbmeugxsfvouqvpve.supabase.co",
                supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImRrd3pibWV1Z3hzZnZvdXF2cHZlIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MDQ2ODkzNTQsImV4cCI6MjAyMDI2NTM1NH0.kcSlZQJfRAoS5ZY81nYeiaT5DiBxvdnh-egZ2j3_n7g"

        ) {
            install(Storage) {
                transferTimeout = 90.seconds
            }
        }
        val bucket = supabaseClient.storage.from("coha")
        val filePath = "${UUID.randomUUID()}.png"
        runBlocking {
            bucket.upload(filePath, file.bytes, upsert = false)
        }

        val uploadedUrl =
                supabaseClient.storage.from("coha").publicUrl(filePath)

        return uploadedUrl

    }


    //MultipartFile을 받아서 파일을 저장하고, 저장된 파일의 이름을 반환하는 메소드
    override fun storeFile(file: MultipartFile): String {
        //파일이름을 생성하고 저장경로를 설정
        val fileName = generateFileName(file.originalFilename!!)
        //저장될 파일의 전체 경로를 나타내는 객체 생성
        val targetLocation = Paths.get(uploadDir, fileName)

        //파일을 지정된 경로로 복사
        //file.inputStream은 업로드된 파일의 내용을 읽어오는 InputStream을 제공
        //StandardCopyOption.REPLACE_EXISITING은 동일한 이름의 파일이 이미 존재하면 덮어쓰기 옵션
        Files.copy(file.inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING)
        //저장된 파일의 이름을 반환
        return fileName
    }

    //주어진 파일 이름을 기반으로 파일을 로드하고 Resource 형태로 반환하는 메소드
    override fun loadFile(filename: String): Resource {
        //파일의 전체 경로를 나타내는 객체로 생성
        val filePath = Paths.get(uploadDir).resolve(filename).normalize()
        // UrlResource를 사용하여 파일의 경로를 URI로 변환한 후 Resource 형태로 생성
        val resource: Resource = UrlResource(filePath.toUri())

        //로드한 파일이 존재하면 해당 Resource를 반환, 없으면 예외처리
        if (resource.exists()) {
            return resource
        } else {
            //파일이 존재하지 않는 경우 RuntimeException 예외를 던짐
            throw RuntimeException("File not found : $filename")
        }
    }

    //원본 파일 이름에서 확장자를 추출하고 UUID 을 이용해서 새로운 파일 이름을 생성
    private fun generateFileName(originalFileName: String): String {
        // 원본 파일 이름에서 마지막 점(.)이후의 문자열을 추출하여 확장자로 설정
        val extension = originalFileName?.substringAfterLast(".")
        //UUID를 이용하여 고유한 식별자를 생성하고, 그 뒤에 추출한 확장자를 붙여서 새로운 파일 이름을 생성
        return "${UUID.randomUUID()}.$extension"
    }


}