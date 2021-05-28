package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.config.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class TrelloMapperTestSuite {

    @Autowired
    private  TrelloMapper trelloMapper;

    @Test
    public void mapToBoardsDtoTest() {
        //Given
        TrelloList trelloList1 = new TrelloList("1","Test list 1", true);
        TrelloList trelloList2 = new TrelloList("2", "Test list 2",true);
        TrelloList trelloList3 = new TrelloList("3","Test list 3", false);

        List<TrelloList> list1 = new ArrayList<>();
        list1.add(trelloList1);
        list1.add(trelloList2);

        List<TrelloList> list2 = new ArrayList<>();
        list2.add(trelloList3);

        TrelloBoard trelloBoard1 = new TrelloBoard("Test board 1","Boards id 1",  list1);
        TrelloBoard trelloBoard2 = new TrelloBoard("Test board 2", "Boards id 2", list2);

        List<TrelloBoard> trelloBoardList = new ArrayList<>();
        trelloBoardList.add(trelloBoard1);
        trelloBoardList.add(trelloBoard2);

        //When
        List<TrelloBoardDto> trelloBoardDtoList = trelloMapper.mapToBoardsDto(trelloBoardList);

        //Then
        assertEquals("1", trelloBoardDtoList.get(0).getLists().get(0).getId());
        assertEquals("Test list 1", trelloBoardDtoList.get(0).getLists().get(0).getName());
        assertTrue(trelloBoardDtoList.get(0).getLists().get(0).isClosed());
        assertEquals("2", trelloBoardDtoList.get(0).getLists().get(1).getId());
        assertEquals("Test list 2", trelloBoardDtoList.get(0).getLists().get(1).getName());
        assertTrue(trelloBoardDtoList.get(0).getLists().get(1).isClosed());
        assertEquals("3", trelloBoardDtoList.get(1).getLists().get(0).getId());
        assertEquals("Test list 3", trelloBoardDtoList.get(1).getLists().get(0).getName());
        assertFalse(trelloBoardDtoList.get(1).getLists().get(0).isClosed());
        assertEquals(2, trelloBoardDtoList.size());

    }
    @Test
    public  void mapToListDtoTest() {
        //Given
        TrelloList trelloList1 = new TrelloList("1","Test list 1", true);
        TrelloList trelloList2 = new TrelloList("2", "Test list 2",true);
        TrelloList trelloList3 = new TrelloList("3","Test list 3", false);

        List<TrelloList> list = new ArrayList<>();
        list.add(trelloList1);
        list.add(trelloList2);
        list.add(trelloList3);

        //When
        List<TrelloListDto> trelloListDto = trelloMapper.mapToListDto(list);

        //Then
        assertEquals("1", trelloListDto.get(0).getId());
        assertEquals("Test list 1", trelloListDto.get(0).getName());
        assertTrue(trelloListDto.get(0).isClosed());
        assertTrue(trelloListDto.get(1).isClosed());
    }

    @Test
    public  void mapToBoardsTest() {

        //Given
        TrelloListDto trelloList1 = new TrelloListDto("1","Test list 1", true);
        TrelloListDto trelloList2 = new TrelloListDto("2", "Test list 2",true);
        TrelloListDto trelloList3 = new TrelloListDto("3","Test list 3", false);

        List<TrelloListDto> list1 = Arrays.asList(trelloList1, trelloList2);
        List<TrelloListDto> list2 = Arrays.asList(trelloList3);

        TrelloBoardDto trelloBoard1 = new TrelloBoardDto("Board id 1", "Test Board 1", list1);
        TrelloBoardDto trelloBoard2 = new TrelloBoardDto(" Board id 2", "Test Board 2", list2);

        List<TrelloBoardDto> trelloBoardDtoList = Arrays.asList(trelloBoard1, trelloBoard2);

        //When
        List<TrelloBoard> trelloBoardList = trelloMapper.mapToBoards(trelloBoardDtoList);

        //Then
        assertEquals("1", trelloBoardList.get(0).getLists().get(0).getId());
        assertEquals("Test list 1", trelloBoardList.get(0).getLists().get(0).getName());
        assertTrue(trelloBoardList.get(0).getLists().get(0).isClosed());
        assertEquals("2", trelloBoardList.get(0).getLists().get(1).getId());
        assertEquals("Test list 2", trelloBoardList.get(0).getLists().get(1).getName());
        assertTrue(trelloBoardList.get(0).getLists().get(1).isClosed());
        assertEquals("3", trelloBoardList.get(1).getLists().get(0).getId());
        assertEquals("Test list 3", trelloBoardList.get(1).getLists().get(0).getName());
        assertFalse(trelloBoardList.get(1).getLists().get(0).isClosed());
        assertEquals(2, trelloBoardList.size());
    }

    @Test
    public void mapToListTest() {
        //Given
        TrelloListDto trelloList1 = new TrelloListDto("1", "Test List 1", false);
        TrelloListDto trelloList2 = new TrelloListDto("2", "Test List 2", true);
        List<TrelloListDto> list = Arrays.asList(trelloList1, trelloList2);

        //When
        List<TrelloList> TrelloList = trelloMapper.mapToList(list);

        //Then
        assertEquals("1", TrelloList.get(0).getId());
        assertEquals("Test List 1", TrelloList.get(0).getName());
        assertFalse(TrelloList.get(0).isClosed());
        assertEquals("2", TrelloList.get(1).getId());
        assertEquals("Test List 2", TrelloList.get(1).getName());
        assertTrue(TrelloList.get(1).isClosed());
    }
    @Test
    public void mapToCardDtoTest() {
        //Given
        TrelloCard trelloCard1 = new TrelloCard("CardId1", "Test Card 1", "1", "1");
        TrelloCard trelloCard2 = new TrelloCard("CardId2", "Test Card 2", "2", "1");

        //When
        TrelloCardDto trelloCardDto1 = trelloMapper.mapToCardDto(trelloCard1);
        TrelloCardDto trelloCardDto2 = trelloMapper.mapToCardDto(trelloCard2);

        //Then
        assertEquals("CardId1", trelloCardDto1.getName());
        assertEquals("Test Card 1", trelloCardDto1.getDescription());
        assertEquals("1", trelloCardDto1.getPos());
        assertEquals("1", trelloCardDto1.getListId());
        assertEquals("CardId2", trelloCardDto2.getName());
        assertEquals("Test Card 2", trelloCardDto2.getDescription());
        assertEquals("2", trelloCardDto2.getPos());
        assertEquals("1", trelloCardDto2.getListId());
    }
    @Test
    public void mapToCardTest() {
        //Given
        TrelloCardDto trelloCardDto1 = new TrelloCardDto("CardId1", "Test Card 1", "1", "1");
        TrelloCardDto trelloCardDto2 = new TrelloCardDto("CardId2", "Test Card 2", "2", "1");

        //When
        TrelloCard trelloCard1 = trelloMapper.mapToCard(trelloCardDto1);
        TrelloCard trelloCard2 = trelloMapper.mapToCard(trelloCardDto2);

        //Then
        assertEquals("CardId1", trelloCard1.getName());
        assertEquals("Test Card 1", trelloCard1.getDescription());
        assertEquals("1", trelloCard1.getPos());
        assertEquals("1", trelloCard1.getListId());
        assertEquals("CardId2", trelloCard2.getName());
        assertEquals("Test Card 2", trelloCard2.getDescription());
        assertEquals("2", trelloCard2.getPos());
        assertEquals("1", trelloCard2.getListId());
    }






}