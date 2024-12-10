package com.example.vickey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class S3Service {

    private final S3Client s3Client;
    private final String bucketName;

    public S3Service(
            @Value("${aws.s3.region}") String region,
            @Value("${aws.s3.access-key-id}") String accessKeyId,
            @Value("${aws.s3.secret-access-key}") String secretAccessKey,
            @Value("${aws.s3.bucket-name}") String bucketName
    ) {
        this.s3Client = S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKeyId, secretAccessKey)))
                .build();
        this.bucketName = bucketName;
    }

    // 썸네일 사진 파일 업로드
    public String uploadImg(MultipartFile file, String imgType) throws IOException {
        try {
            // 파일 이름 생성
            String fileName = imgType + "/" + UUID.randomUUID() + "-" + file.getOriginalFilename();

            // S3에 파일 업로드
            s3Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(fileName)
                            .contentType(file.getContentType())
                            .build(),
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize())
            );

            // 업로드된 파일의 URL 반환
            return "https://" + bucketName + ".s3.ap-northeast-2.amazonaws.com/" + fileName;

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload thumbnail to S3: " + e.getMessage(), e);
        }
    }

    //동영상 파일 업로드
    public Map<String, Object> uploadFile(MultipartFile file) throws IOException {

        String fileName = System.currentTimeMillis() + "-" + file.getOriginalFilename();
        Path tempFile = Files.createTempFile("upload-", fileName);

        // 파일을 임시 디렉토리에 저장
        file.transferTo(tempFile.toFile());

        try {
            // 1. 동영상 길이 추출
            long duration = getVideoDuration(tempFile.toString());

            // 2. S3에 파일 업로드
            s3Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(fileName)
                            .build(),
                    tempFile
            );

            // 3. 결과 반환
            Map<String, Object> result = new HashMap<>();
            result.put("url", "https://" + bucketName + ".s3.ap-northeast-2.amazonaws.com/" + fileName);
            result.put("duration", duration);

            return result;

        } finally {
            // 임시 파일 삭제
            Files.deleteIfExists(tempFile);
        }

    }


    // FFmpeg를 사용해 동영상 길이 추출
    private long getVideoDuration(String filePath) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "ffprobe",
                    "-i", filePath,                     // 입력 파일
                    "-v", "error",                      // 에러 메시지 최소화
                    "-show_entries", "format=duration", // 동영상 길이 추출
                    "-of", "default=noprint_wrappers=1:nokey=1" // 포맷 옵션
            );
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            // 프로세스 출력 읽기
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = reader.readLine();
            process.waitFor();

            // ffprobe 결과 출력
            if (line == null || line.isEmpty()) {
                throw new RuntimeException("ffprobe output is empty or null.");
            }
            System.out.println("ffprobe output: " + line);

            // 초 단위 반환
            return (long) Math.ceil(Double.parseDouble(line));

        } catch (NumberFormatException | InterruptedException e) {
            throw new RuntimeException("비디오 길이 파싱 실패: " + e.getMessage(), e);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to get video duration for file: " + filePath);
        }

    }

}
