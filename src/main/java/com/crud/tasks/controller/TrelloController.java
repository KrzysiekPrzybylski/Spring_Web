package com.crud.tasks.controller;

import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.facade.TrelloFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/trello")
@RequiredArgsConstructor
@CrossOrigin("*")
public class TrelloController {

    @Autowired
    private TrelloFacade trelloFacade;

    @GetMapping("getTrelloBoards")
    public List<TrelloBoardDto> getTrelloBoards() {

        return trelloFacade.fetchTrelloBoards();

//        trelloBoards.forEach(trelloBoardDto -> {
//            System.out.println(trelloBoardDto.getId() + " " + trelloBoardDto.getName());
//            System.out.println("This board contain lists: ");
//            trelloBoardDto.getLists().forEach(trelloList -> {
//                System.out.println(trelloList.getName() + " - " + trelloList.getId() + " - " + trelloList.isClosed());
//            } );
//        });
//
//        List<TrelloBoardDto> trelloBoardsWithKodilla = trelloBoards.stream()
//                .filter( t -> t.getId() != null)
//                .filter( t -> t.getName() != null)
//                .filter( t -> t.getName().contains("Kodilla"))
//                .collect(Collectors.toList());
    }
    @PostMapping("createTrelloCard")
    public CreatedTrelloCardDto createTrelloCard(@RequestBody TrelloCardDto trelloCardDto) {
        return  trelloFacade.createCard(trelloCardDto);
    }
}
