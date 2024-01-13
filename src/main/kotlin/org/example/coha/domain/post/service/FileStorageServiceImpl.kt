package org.example.coha.domain.post.service
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.*


@Service
class FileStorageServiceImpl(
        @Value("\${file.upload-dir}")
        private val uploadDir: String
) : FileStorageService {

    init {
        val uploadPath = Paths.get(uploadDir)
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath)
        }
    }

    override fun storesupaFile(file: MultipartFile): String {
        return storeFile(file)
    }

    override fun storeFile(file: MultipartFile): String {
        val fileName = generateFileName(file.originalFilename!!)
        val targetLocation = Paths.get(uploadDir, fileName)
        Files.copy(file.inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING)
        return fileName
    }

    override fun loadFile(filename: String): Resource {
        val filePath = Paths.get(uploadDir).resolve(filename).normalize()
        val resource: Resource = UrlResource(filePath.toUri())

        if (resource.exists()) {
            return resource
        } else {
            throw RuntimeException("File not found: $filename")
        }
    }

    private fun generateFileName(originalFileName: String): String {
        val extension = originalFileName?.substringAfterLast(".")
        return "${UUID.randomUUID()}.$extension"
    }
}