package com.crud.tasks;

import com.crud.tasks.domain.Trello;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TasksApplicationTests {

	@Test
	void contextLoads() {
	}
@Test
public void shouldTrelloFetch() {
    //Given
    Trello trello1 = new Trello(1,1);
    Trello trello2 = new Trello(2,2);
    //When
    trello1.setBoard(3);
    trello1.setCard(3);

    //Then
    assertEquals(3, trello1.getBoard());
    assertEquals(2, trello2.getBoard());
    assertEquals(3,trello1.getCard());
    assertEquals(2,trello2.getCard());

}

}
