package uz.online.study_sets.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.online.study_sets.dto.attachDto.AttachDownloadDTO;
import uz.online.study_sets.dto.attachDto.AttachResponseDTO;
import uz.online.study_sets.sevice.AttachService;

@RestController
@RequestMapping("/attach")
@RequiredArgsConstructor
public class AttachController {

    private final AttachService service;

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam MultipartFile file){
        AttachResponseDTO attach = service.saveToSystem(file);
        return ResponseEntity.ok().body(attach);
    }


    @GetMapping(value = "/public/open/{id}", produces = MediaType.ALL_VALUE)
    public byte[] open(@PathVariable("id") Long id) {
        return service.open(id);
    }


    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> download(@PathVariable("id") Long id) {
        AttachDownloadDTO result = service.download(id);

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(result.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + result.getResource().getFilename() + "\"").body(result.getResource());
    }



    @GetMapping("/get")
    public ResponseEntity<?> getWithPage(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        Page<AttachResponseDTO> result = service.getWithPage(page, size);
        return ResponseEntity.ok(result);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        String result = service.deleteById(id);
        return ResponseEntity.ok(result);
    }

}
