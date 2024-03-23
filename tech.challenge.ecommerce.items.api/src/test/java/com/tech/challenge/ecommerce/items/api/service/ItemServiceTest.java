package com.tech.challenge.ecommerce.items.api.service;

import com.tech.challenge.ecommerce.items.api.exceptions.NotFoundException;
import com.tech.challenge.ecommerce.items.api.fixture.ItemFixture;
import com.tech.challenge.ecommerce.items.api.repository.ItemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @InjectMocks
    private ItemService service;

    @Mock
    private ItemRepository repository;

    @Nested
    @DisplayName("1. Find Items")
    class FindItems {

        @Test
        @DisplayName("1.1 Should find All Items")
        void shouldFindAll() {
            var expectedResponse = ItemFixture.buildItems();
            when(repository.findAll()).thenReturn(expectedResponse);

            var Items = service.findAll();

            verify(repository, times(1)).findAll();
            assertEquals(expectedResponse, Items);
        }

        @Test
        @DisplayName("1.2 Should find Item by Id")
        void shouldFindById() {
            var expectedResponse = Optional.of(ItemFixture.buildItem());
            when(repository.findById(anyLong())).thenReturn(expectedResponse);

            var Item = service.findById(1L);

            verify(repository, times(1)).findById(anyLong());
            assertEquals(expectedResponse, Item);
        }

        @Test
        @DisplayName("1.3 Should not find Item for given Id")
        void shouldNotFindById() {
            when(repository.findById(anyLong())).thenReturn(Optional.empty());

            var Item = service.findById(1L);

            verify(repository, times(1)).findById(anyLong());
            assertEquals(Optional.empty(), Item);
        }
    }

    @Nested
    @DisplayName("2. Save Items")
    class SaveItems {

        @Test
        @DisplayName("2.1 Should save All Items")
        void shouldSaveItem() {
            when(repository.save(any())).thenReturn(ItemFixture.buildItem());

            assertDoesNotThrow(() -> service.save(ItemFixture.buildItem()));
        }

    }

    @Nested
    @DisplayName("3. Update Items")
    class UpdateItems {

        @Test
        @DisplayName("3.1 Should Update Item")
        void shouldUpdateItem() {
            var updateResponse = ItemFixture.buildItem();
            updateResponse.setName("Test3");

            when(repository.findById(anyLong())).thenReturn(Optional.of(ItemFixture.buildItem()));
            when(repository.save(any())).thenReturn(updateResponse);

            var Items = service.update(updateResponse, 1L);

            verify(repository, times(1)).save(any());
            verify(repository, times(1)).findById(anyLong());
            assertEquals(updateResponse, Items);
        }

        @Test
        @DisplayName("3.2 Should Throw When Trying to Update a Non-Existent Item")
        void shouldUThrowNotFoundExceptionWhenUpdateItem() {
            var updateResponse = ItemFixture.buildItem();
            updateResponse.setName("Test3");

            when(repository.findById(any())).thenReturn(Optional.empty());

            assertThrows(NotFoundException.class, () -> service.update(updateResponse, 1L));
        }

    }

    @Nested
    @DisplayName("4. Delete Items")
    class DeleteItems {

        @Test
        @DisplayName("4.1 Should delete Item")
        void shouldDeleteItem() {
            when(repository.findById(anyLong())).thenReturn(Optional.of(ItemFixture.buildItem()));
            doNothing().when(repository).delete(ItemFixture.buildItem());
            assertDoesNotThrow(() -> service.delete(1L));
        }

        @Test
        @DisplayName("4.2 Should Throw when trying to delete Item")
        void shouldThrowNotFoundExceptionWhenDeleteItem() {
            when(service.findById(anyLong())).thenReturn(Optional.empty());
            assertThrows(NotFoundException.class, () -> service.delete(1L));
        }


    }
}