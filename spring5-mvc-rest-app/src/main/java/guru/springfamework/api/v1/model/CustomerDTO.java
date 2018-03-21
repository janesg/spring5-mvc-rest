package guru.springfamework.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

    @ApiModelProperty(value = "Customer's given name", required = true)
    private String firstName;

    private String lastName;

    @JsonProperty("customer_url")
    private String customerUrl;
}
