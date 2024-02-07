package uz.pdp.lesson1_1.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DepartmentDto {

    @NotNull(message = "Enter Company name")
    private String name;

    @NotNull(message = "Enter Company id!")
    private Integer companyId;
}
