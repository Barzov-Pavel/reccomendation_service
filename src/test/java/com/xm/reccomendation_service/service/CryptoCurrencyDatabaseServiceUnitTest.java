package com.xm.reccomendation_service.service;

import com.xm.reccomendation_service.dto.CryptoCurrencyDto;
import com.xm.reccomendation_service.repository.CryptoCurrencyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static com.xm.reccomendation_service.CryptoCurrencyTestUtils.getCryptoCurrencyDtos;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CryptoCurrencyDatabaseServiceUnitTest {

    @Mock
    CryptoCurrencyRepository repository;

    @InjectMocks
    CryptoCurrencyDatabaseService service;

    @Test
    void should_lunch_save_all_method_from_repository() {
        // Given
        List<CryptoCurrencyDto> dtos = getCryptoCurrencyDtos();
        when(repository.saveAll(anyList())).thenReturn(Collections.emptyList());
        // When
        service.saveAll(dtos);
        // Then
        verify(repository, times(1)).saveAll(anyList());
        verifyNoMoreInteractions(repository);
    }

    @Test
    void should_lunch_delete_all_method_from_repository() {
        // Given
        doNothing().when(repository).deleteAll();
        // When
        service.deleteAll();
        // Then
        verify(repository, times(1)).deleteAll();
        verifyNoMoreInteractions(repository);
    }
}
