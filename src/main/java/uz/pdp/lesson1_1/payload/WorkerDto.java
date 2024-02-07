package uz.pdp.lesson1_1.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkerDto {

    @NotNull(message = "Enter worker's name")
    private String name;

    @NotNull(message = "Enter phone number")
    private String phoneNumber;

    @NotNull(message = "Address Id")
    private Integer addressId;

    @NotNull(message = "Department Id")
    private Integer departmentId;
}
