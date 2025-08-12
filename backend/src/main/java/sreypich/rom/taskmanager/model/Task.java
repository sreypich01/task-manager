package sreypich.rom.taskmanager.model;

import lombok.Data;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Data
public class Task {
    private Long id;
    
    @NotBlank(message = "Title cannot be blank")
    @Size(min = 3, max = 100, message = "Title must be 3-100 characters")
    private String title;
    
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;
    
    @NotBlank(message = "Status cannot be blank")
    @Pattern(regexp = "^(PENDING|IN_PROGRESS|COMPLETED)$", 
             message = "Status must be PENDING, IN_PROGRESS or COMPLETED")
    private String status;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}