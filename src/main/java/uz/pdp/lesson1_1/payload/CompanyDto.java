package uz.pdp.lesson1_1.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDto {
    @NotNull(message = "Fill CorpName")
    private String corpName;

    @NotNull(message = "Director name can not be empty")
    private String directorName;

    @NotNull(message = "address id")
    private Integer addressId;
}
