package com.crud.tasks.controller;

import com.crud.tasks.domain.CreateTrelloCard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.service.TrelloService;
import com.crud.tasks.trello.client.TrelloClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/trello")
@RequiredArgsConstructor
@CrossOrigin("*")
public class TrelloController {

    private final TrelloClient trelloClient;
    private final TrelloService trelloService;

    @GetMapping("getTrelloBoards")
    public List<TrelloBoardDto> getTrelloBoards() {

        return trelloService.fetchTrelloBoards();

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
    public CreateTrelloCard createTrelloCard(@RequestBody TrelloCardDto trelloCardDto) {
        return  trelloService.createTrelloCard(trelloCardDto);
    }
}
