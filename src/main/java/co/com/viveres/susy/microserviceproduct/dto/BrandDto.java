package co.com.viveres.susy.microserviceproduct.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "Brand")
public class BrandDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotNull
	@ApiModelProperty(notes = "Brand Id", example = "1")
	private Long id;
	
	@ApiModelProperty(notes = "Brand name", example = "Diana")
	private String name;

}
