package com.tech.challenge.ecommerce.items.api.controller;

import com.tech.challenge.ecommerce.items.api.domain.dto.request.ItemRequestDTO;
import com.tech.challenge.ecommerce.items.api.domain.dto.response.ItemResponseDTO;
import com.tech.challenge.ecommerce.items.api.domain.mapper.ItemMapper;
import com.tech.challenge.ecommerce.items.api.exceptions.NotFoundException;
import com.tech.challenge.ecommerce.items.api.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.lang.String.format;

@RestController
@RequestMapping(value = "/v1/item")
@AllArgsConstructor
@Validated
public class ItemController {

    private final ItemService itemService;
    private final ItemMapper mapper;

    @PostMapping
    @ApiResponse(description = "Item Response", responseCode = "201")
    @Operation(summary = "Create Item", description = """
          # Registra novo Item
          ---
          
          """)
    public ResponseEntity<ItemResponseDTO> createItem(
            @RequestBody @Valid ItemRequestDTO requestDTO) {
        var response = mapper.of(itemService.save(mapper.of(requestDTO)));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @ApiResponse(description = "Item Response", responseCode = "200")
    @Operation(summary = "Get All Items", description = """
          # Busca todos os Itens
          ---
          
          """)
    public ResponseEntity<List<ItemResponseDTO>> getAll() {

        return ResponseEntity.status(HttpStatus.OK).body(mapper.of(itemService.findAll()));
    }

    @GetMapping("/{id}")
    @ApiResponse(description = "Item Response", responseCode = "200")
    @Operation(summary = "Get Item by Id", description = """
          # Busca Item por Id
          ---
          
          """)
    public ResponseEntity<ItemResponseDTO> findById(@PathVariable("id") @Valid Long id) {
        var optItem = itemService.findById(id);
        if (optItem.isPresent()) {
            var response = mapper.of(optItem.get());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        throw new NotFoundException(format("Item ID %d not found", id));
    }

    @PutMapping("/{id}")
    @ApiResponse(description = "Item Response", responseCode = "200")
    @Operation(summary = "Update Item Name by ID", description = """
          # Atualiza o Item pelo ID
          ---
          
          """)
    public ResponseEntity<ItemResponseDTO> updateItem(
            @PathVariable("id") Long id,
            @RequestBody @Valid ItemRequestDTO requestDTO) {

        var response = mapper.of(itemService.update(mapper.of(requestDTO), id));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(description = "Void", responseCode = "204")
    @Operation(summary = "Delete Item By ID", description = """
          # Apaga Item por Id
          ---
          
          """)
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        itemService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
