package com.fittoo.utills;

import com.fittoo.member.model.LoginType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.time.LocalDate.now;

@Component
public class FileStore {
    public String getFullPath(String fileName, String loginType) {
        String fileDir = "C:/inflearn/image";
        String dirs = String.format("%s\\%s\\%d\\%02d\\%02d\\",
                fileDir,  loginType, now().getYear(), now().getMonthValue(), now().getDayOfMonth());
        File file = new File(dirs); // 저장 경로 생성
        if (!file.isDirectory()) {
            file.mkdirs();
        }

        return dirs + fileName;
    }

    public String[] storeFile(MultipartFile multipartFile, String loginType) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }

        // 실제 파일명 / 경로 없이 저장
        String originalFilename = multipartFile.getOriginalFilename();

        // 서버에 저장하는 파일명(UUID + 확장자) / 경로 없이 저장
        String storeFileName = createStoreFileName(originalFilename);

        // 경로 + 파일명으로 파일을 실제 경로에 전송

//        storeFileName = getFullPath(storeFileName, loginType);

        multipartFile.transferTo(new File(getFullPath(storeFileName, loginType)));

        return new String[]{originalFilename, storeFileName};
    }

    private String createStoreFileName(String originalFilename) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");

        if (originalFilename == null) {
            return null;
        }

        // 확장자 추출
        String ext = extractExt(originalFilename);

        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int extPos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(extPos + 1);
    }

}
