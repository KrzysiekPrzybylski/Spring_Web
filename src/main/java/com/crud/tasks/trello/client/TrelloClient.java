package com.crud.tasks.trello.client;

import com.crud.tasks.domain.CreateTrelloCardDto;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.config.TrelloConfig;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class TrelloClient {

    private final RestTemplate restTemplate;
    private final TrelloConfig trelloConfig;


    private static final Logger LOGGER = LoggerFactory.getLogger(TrelloClient.class);


    public List<TrelloBoardDto> getTrelloBoards() {
        URI url = UriComponentsBuilder.fromHttpUrl(trelloConfig.getTrelloApiEndpoint() + "/members/" + trelloConfig.getTrelloUserName() + "/boards")
                .queryParam("key", trelloConfig.getTrelloAppKey())
                .queryParam("token", trelloConfig.getTrelloToken())
                .queryParam("fields", "name,id")
                .queryParam("lists","all")
                .build()
                .encode()
                .toUri();

        try {
//
//                TrelloBoardDto[] boardsResponse = restTemplate.getForObject(url, TrelloBoardDto[].class);
//                return Arrays.asList(ofNullable(boardsResponse).orElse(new TrelloBoardDto[0]));
            TrelloBoardDto[] boardsResponse = restTemplate.getForObject(url, TrelloBoardDto[].class);
            return Optional.ofNullable(boardsResponse)
                    .map(Arrays::asList)
                    .orElse(Collections.emptyList())
                    .stream()
                    .filter(p -> Objects.nonNull(p.getId()) && Objects.nonNull(p.getName()))
                    //.filter(p -> p.getName().contains("Kodilla"))
                    .collect(Collectors.toList());
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return Collections.emptyList();
        }

    }

//    public List<TrelloBoardDto> getTrelloBoards() {
//
//
//        TrelloBoardDto[] boardsResponse = restTemplate.getForObject(buildUrlAddress(), TrelloBoardDto[].class);
//
//        return Optional.ofNullable(boardsResponse)
//                .map(Arrays::asList)
//                .orElse(Collections.emptyList())
//                .stream()
//                .filter(p -> Objects.nonNull(p.getId()) && Objects.nonNull(p.getName()))
//                .filter(p -> p.getName().contains("Kodilla"))
//                .collect(Collectors.toList());
//    }
    public CreateTrelloCardDto createNewCard(TrelloCardDto trelloCardDto) {
        URI url = UriComponentsBuilder.fromHttpUrl(trelloConfig.getTrelloApiEndpoint() + "/cards")
                .queryParam("key", trelloConfig.getTrelloAppKey())
                .queryParam("token", trelloConfig.getTrelloToken())
                .queryParam("name", trelloCardDto.getName())
                .queryParam("desc", trelloCardDto.getDescription())
                .queryParam("pos", trelloCardDto.getPos())
                .queryParam("idList", trelloCardDto.getListId())
                .build().encode().toUri();

        return restTemplate.postForObject(url, null, CreateTrelloCardDto.class);
    }
//    private URI buildUrlAddress(){
//        return UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/members/krzysztofprzybylski13/boards")
//                .queryParam("key", trelloAppKey)
//                .queryParam("token", trelloToken)
//                .queryParam("fields", "name,id")
//                .queryParam("name", trelloUserName)
//                .queryParam("lists", "all")
//                .build()
//                .encode()
//                .toUri();
//    }

}