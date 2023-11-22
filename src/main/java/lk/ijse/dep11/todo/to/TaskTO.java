package lk.ijse.dep11.todo.to;

import jdk.jfr.DataAmount;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import javax.validation.groups.Default;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskTO implements Serializable {
    @Null(message = "Id should be empty")
    private Integer id;
    @NotBlank(message = "Description should not be empty")
    private String description;
    @Null(message = "Status should be empty", groups = Create.class) //CREATE
    @NotNull(message = "Status should not be empty", groups = Update.class) //UPDATE
    private Boolean status;

    public interface Update extends Default {}

    public interface Create extends Default {}

}