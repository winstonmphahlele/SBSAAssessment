package za.ac.standardbank.card.dto;


import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class ProductDto {

    private Long id;
    private String name;
    private Double price;

}
