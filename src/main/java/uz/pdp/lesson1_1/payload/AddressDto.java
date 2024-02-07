package uz.pdp.lesson1_1.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {

    @NotNull(message = "Street can't be empty")
    private String street;

    @NotNull(message = "Home number can't be empty")
    private String homeNumber;
}
