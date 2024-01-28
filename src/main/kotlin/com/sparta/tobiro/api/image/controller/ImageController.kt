package com.sparta.tobiro.api.image.controller

import com.sparta.tobiro.api.image.dto.UploadImageResponse
import com.sparta.tobiro.infra.aws.S3Service
import io.swagger.v3.oas.annotations.tags.Tag
import kotlinx.coroutines.runBlocking
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@Tag(name = "image", description = "이미지 관리 API")
@RequestMapping("/api/v1/images")
@RestController
class ImageController(
    private val s3Service: S3Service
) {
    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun uploadImage(@RequestParam("images") multipartFiles: MutableList<MultipartFile>): ResponseEntity<List<UploadImageResponse>> {
        return runBlocking {
            ResponseEntity
                .ok(s3Service.upload(multipartFiles, "image").map { UploadImageResponse(it) })
        }
    }
}