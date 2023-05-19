package uz.online.study_sets.sevice;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.online.study_sets.common.exception.*;
import uz.online.study_sets.dto.attachDto.AttachDownloadDTO;
import uz.online.study_sets.dto.attachDto.AttachResponseDTO;
import uz.online.study_sets.entity.AttachEntity;
import uz.online.study_sets.repository.AttachRepository;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.info.MultimediaInfo;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


@Service
@RequiredArgsConstructor
public class AttachService {

    @Value("${attach.upload.folder}")
    private String attachUploadFolder;

    @Value("${attach.download.url}")
    private String attachDownloadUrl;

    private final AttachRepository repository;

    public AttachResponseDTO saveToSystem(MultipartFile file) {
        try {

            System.out.println(file.getContentType());

            String pathFolder = getYmDString(); // 2022/04/23
            File folder = new File(attachUploadFolder + pathFolder); // attaches/2022/04/23

            if (!folder.exists()) folder.mkdirs();


            String fileName = UUID.randomUUID().toString(); // dasdasd-dasdasda-asdasda-asdasd
            String extension = getExtension(file.getOriginalFilename()); //zari.jpg

            // attaches/2022/04/23/dasdasd-dasdasda-asdasda-asdasd.jpg
            byte[] bytes = file.getBytes();
            Path path = Paths.get(attachUploadFolder + pathFolder + "/" + fileName + "." + extension);
            File f = Files.write(path, bytes).toFile();


            AttachEntity entity = new AttachEntity();
            entity.setOriginName(file.getOriginalFilename());
            entity.setName(fileName);
            entity.setType(extension);
            entity.setPath(pathFolder);
            entity.setSize(file.getSize());
            entity.setContentType(file.getContentType());

            if (extension.equalsIgnoreCase("mp4")

                    || extension.equalsIgnoreCase("wmv")
                    || extension.equalsIgnoreCase("avi")
                    || extension.equalsIgnoreCase("avchd")
                    || extension.equalsIgnoreCase("flv")
                    || extension.equalsIgnoreCase("mkv")) {

                MultimediaObject instance = new MultimediaObject(f);
                MultimediaInfo result = instance.getInfo();
                entity.setDuration((double) (result.getDuration() / 1000));
            }

            AttachEntity save = repository.save(entity);


            AttachResponseDTO dto = new AttachResponseDTO();
            dto.setId(save.getId());
            dto.setName(fileName);
            dto.setOriginalName(file.getOriginalFilename());
            dto.setDuration(entity.getDuration());
            dto.setPath(path.toString());
            dto.setExtension(extension);
            dto.setSize(file.getSize());
            dto.setCreatedData(entity.getCreatedDate());
            dto.setUrl(attachDownloadUrl + fileName + "." + extension);
            return dto;

        } catch (IOException e) {
            throw new FileUploadException("File could not upload");
        } catch (EncoderException e) {
            throw new RuntimeException(e);
        }

    }

    private AttachEntity getAttach(Long id) {
       // String id = fileName.split("\\.")[0];
        Optional<AttachEntity> optional = repository.findById(id);
        if (optional.isEmpty()) {
            throw new FileNotFoundException("File Not Found");
        }
        return optional.get();
    }

    public byte[] open(Long id) {
        try {
            AttachEntity entity = getAttach(id);

            Path file = Paths.get(attachUploadFolder + entity.getPath() + "/" +entity.getName());
            return Files.readAllBytes(file);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public AttachDownloadDTO download(Long id) {
        try {
            AttachEntity entity = getAttach(id);

            File file = new File(attachUploadFolder + entity.getPath() + "/" + entity.getName());

            File dir = file.getParentFile();
            File rFile = new File(dir, entity.getId() + "." + entity.getType());

            Resource resource = new UrlResource(rFile.toURI());

            if (resource.exists() || resource.isReadable()) {
                return new AttachDownloadDTO(resource, entity.getContentType());
            } else {
                throw new CouldNotRead("Could not read");
            }
        } catch (MalformedURLException e) {
            throw new SomethingWentWrong("Something went wrong");
        }
    }

    public Page<AttachResponseDTO> getWithPage(Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<AttachEntity> pageObj = repository.findAll(pageable);

        List<AttachEntity> entityList = pageObj.getContent();
        List<AttachResponseDTO> dtoList = new ArrayList<>();


        for (AttachEntity entity : entityList) {

            AttachResponseDTO dto = new AttachResponseDTO();
            dto.setId(entity.getId());
            dto.setPath(entity.getPath());
            dto.setExtension(entity.getType());
            dto.setUrl(attachDownloadUrl + "/" + entity.getId() + "." + entity.getType());
            dto.setOriginalName(entity.getOriginName());
            dto.setSize(entity.getSize());
            dto.setCreatedData(entity.getCreatedDate());
            dtoList.add(dto);
        }

        return new PageImpl<>(dtoList, pageable, pageObj.getTotalElements());
    }

    public String getYmDString() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DATE);

        return year + "/" + month + "/" + day; // 2022/04/23
    }

    public String getExtension(String fileName) {
        // mp3/jpg/npg/mp4.....
        if (fileName == null) {
            throw new OriginalFileNameNullException("File name null");
        }
        int lastIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastIndex + 1);
    }

    public String deleteById(Long id) {
        try {
            AttachEntity entity = getAttach(id);
            Path file = Paths.get(attachUploadFolder + entity.getPath() + "/" + entity.getName());

            Files.delete(file);
            repository.deleteById(entity.getId());

            return "deleted";
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }

    }
}

