package com.tech.challenge.ecommerce.bag.api.controller;

import com.tech.challenge.ecommerce.bag.api.domain.dto.request.PurchaseBagRequestDTO;
import com.tech.challenge.ecommerce.bag.api.domain.dto.response.ItemResponseDTO;
import com.tech.challenge.ecommerce.bag.api.domain.dto.response.PurchaseBagResponseDTO;
import com.tech.challenge.ecommerce.bag.api.domain.mapper.ItemMapper;
import com.tech.challenge.ecommerce.bag.api.domain.mapper.PurchaseBagMapper;
import com.tech.challenge.ecommerce.bag.api.domain.model.PurchaseBag;
import com.tech.challenge.ecommerce.bag.api.domain.model.UserData;
import com.tech.challenge.ecommerce.bag.api.exceptions.NotFoundException;
import com.tech.challenge.ecommerce.bag.api.service.ItemService;
import com.tech.challenge.ecommerce.bag.api.service.PurchaseBagService;
import com.tech.challenge.ecommerce.bag.api.service.UserDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

@RestController
@RequestMapping(value = "/v1/bag")
@AllArgsConstructor
@Validated
public class PurchaseBagController {

    private final PurchaseBagService service;
    private final PurchaseBagMapper mapper;
    private final ItemMapper itemMapper;
    private final ItemService itemService;
    private final UserDataService userDataService;

    @PostMapping
    @ApiResponse(description = "PurchaseBag Response", responseCode = "201")
    @Operation(summary = "Add Item to Current Purchase Bag or Create a new Purchase Bag", description = """
            # Registra novo item na sacola de compras, ou cria uma nova
            ---
                      
            """)
    public ResponseEntity<Void> addItemToBag(@PathParam("email") @Valid String email,
                                                               @RequestBody @Valid PurchaseBagRequestDTO requestDTO) {

        requestDTO.setIdUserData(findUserByEmail(email).getId());
        service.save(mapper.of(requestDTO));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{idItem}")
    @ApiResponse(description = "Void", responseCode = "204")
    @Operation(summary = "Delete Purchase Bag By ID", description = """
            # Apaga item da Sacola por Id PurchaseBag e Id Item
            ---
                      
            """)
    public ResponseEntity<Void> deletePurchaseBagFromBagById(@PathParam("email") @Valid String email,
                                                             @PathVariable("idItem") Long idItem) {
        service.deleteByIdItemAndIdUser(idItem, findUserByEmail(email).getId());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping
    @ApiResponse(description = "Purchase Bag Response", responseCode = "200")
    @Operation(summary = "Get Purchase Bag by Id", description = """
            # Busca Sacola de Compras por Id
            ---
                      
            """)
    public ResponseEntity<PurchaseBagResponseDTO> findById(@PathParam("email") @Valid String email) {

        var bag = service.findNotPayedByIdUser(findUserByEmail(email).getId());
        PurchaseBagResponseDTO response = new PurchaseBagResponseDTO();
        List<ItemResponseDTO> itemResponses = new ArrayList<>();
        for (PurchaseBag item : bag) {
            var itemResponse = itemMapper.of(itemService.validateAndFindById(item.getIdItem()));
            itemResponse.setQuantity(item.getQuantity());
            itemResponses.add(itemResponse);
        }
        response.setItems(itemResponses);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    private UserData findUserByEmail(String email) {
        var user = userDataService.findByEmail(email);
        if (user.isEmpty()) {
            throw new NotFoundException(format("User %s not found", email));
        }
        return user.get();
    }
}
