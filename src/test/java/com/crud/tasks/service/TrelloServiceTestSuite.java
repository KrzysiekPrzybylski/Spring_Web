package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.controller.TrelloController;
import com.crud.tasks.domain.CreateTrelloCardDto;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.domain.TrelloListDto;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrelloServiceTestSuite {

    @InjectMocks
    private TrelloService trelloService;

    @Mock
    private TrelloClient trelloClient;


    @Test
    public void shouldFetchTrelloBoards() {
        // Given
        List<TrelloListDto> lists = new ArrayList<>();
        lists.add(new TrelloListDto("Test","2",  false));
        List<TrelloBoardDto> boards = new ArrayList<>();
        boards.add(new TrelloBoardDto("Test","12",lists));

        when(trelloClient.getTrelloBoards()).thenReturn(boards);
        // When
        List<TrelloBoardDto> fetchedTrelloBoards = trelloService.fetchTrelloBoards();
        // Then
        assertEquals(1, fetchedTrelloBoards.size());
        assertEquals("12", fetchedTrelloBoards.get(0).getId());
        assertEquals("Test", fetchedTrelloBoards.get(0).getName());
        assertEquals(1, fetchedTrelloBoards.get(0).getLists().size());
        assertEquals("2", fetchedTrelloBoards.get(0).getLists().get(0).getId());
        assertEquals("Test", fetchedTrelloBoards.get(0).getLists().get(0).getName());
        assertEquals(false, fetchedTrelloBoards.get(0).getLists().get(0).isClosed());
    }

    @Test
    public void shouldCreateTrelloCardTest() {
        // Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("First card", "Test card", "Top", "1");
        CreateTrelloCardDto cardDtoStub = new CreateTrelloCardDto("Test Id", "Testing", "Test URL");

        when(trelloClient.createNewCard(trelloCardDto)).thenReturn(cardDtoStub);
        // When
        CreateTrelloCardDto createdTrelloCardDto = trelloService.createTrelloCard(trelloCardDto);
        // Then
        assertEquals("Test Id", createdTrelloCardDto.getId());
        assertEquals("Testing", createdTrelloCardDto.getName());
        assertEquals("Test URL", createdTrelloCardDto.getShortUrl());
    }

}