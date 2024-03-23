package com.tech.challenge.ecommerce.bag.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Response payload")
public class ResponseDTO<T> {

    @Schema(description = "Response data")
    private T response;

    @Schema(description = "Error details")
    private List<ErrorDTO> errors;

    public ResponseDTO(T response) {
        this.response = response;
    }

    @JsonIgnore
    public boolean isResponseNullOrEmpty() {
        if (response == null) {
            return true;
        }
        if (response instanceof List) {
            return ((List<?>) response).isEmpty();
        }
        return false;
    }

    public ResponseDTO(ErrorDTO errorDTO) {
        if (errors == null) {
            errors = new ArrayList<>();
        }
        errors.add(errorDTO);
    }

    public ResponseDTO(List<ErrorDTO> errorsDTO) {
        if (errors == null) {
            errors = new ArrayList<>();
        }
        errors.addAll(errorsDTO);
    }
}