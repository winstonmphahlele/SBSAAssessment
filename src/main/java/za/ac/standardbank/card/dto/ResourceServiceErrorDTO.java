package za.ac.standardbank.card.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class ResourceServiceErrorDTO {
	private int errorCode;
	private String message = "";
}