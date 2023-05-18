package uz.online.study_sets.dto.attachDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.core.io.Resource;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttachDownloadDTO {
    private Resource resource;
    private String contentType;

}
