package uz.online.study_sets.sevice;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.online.study_sets.common.exception.RecordNotFountException;
import uz.online.study_sets.entity.AttachEntity;
import uz.online.study_sets.repository.AttachRepository;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttachService {
    private final AttachRepository repository;
    private static final String url = "F:\\AA PROJECT\\study_sets\\src\\main\\resources\\templates\\imag2023";

    public Long save(MultipartHttpServletRequest multipartFile) {
        Iterator<String> fileNames = multipartFile.getFileNames();
        MultipartFile file = multipartFile.getFile(fileNames.next());
        if (file != null) {
            String originalFilename = file.getOriginalFilename();

            AttachEntity attach = new AttachEntity();
            attach.setSize(file.getSize());
            attach.setOriginName(originalFilename);
            attach.setContentType(file.getContentType());

            String[] split = originalFilename.split("\\.");
            String filename = UUID.randomUUID() + "." + split[split.length - 1];

            attach.setName(filename);
            AttachEntity save = repository.save(attach);


            try {
                Path path = Paths.get(url + "/" + filename);
                Files.copy(file.getInputStream(), path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return save.getId();
        }
        return 0L;
    }

    public void getAttachById(Long id, HttpServletResponse response) {

        Optional<AttachEntity> attach = repository.findById(id);
        if (attach.isPresent()) {
            AttachEntity attachEntity = attach.get();

            response.setHeader("Content-Disposition", "attachment; filename=\"" + attachEntity.getOriginName() + "\"");
            response.setContentType(attachEntity.getContentType());

            try {
                FileInputStream fileInputStream = new FileInputStream(url + "/" + attachEntity.getName());

                FileCopyUtils.copy(fileInputStream, response.getOutputStream());

            } catch (Exception e) {
                throw new RuntimeException(e);
            }


        }

    }

    @Transactional
    public Boolean deleteById(Long id) {
        try {
            AttachEntity entity = repository.findById(id).orElseThrow(() ->
                    new RecordNotFountException(id + " no such file found"));
            Path file = Paths.get(url + "/" + entity.getName());

            Files.delete(file);
            repository.deleteById(entity.getId());

            return true;
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }

    }
}
