package com.war.WardCardGame2;

import com.war.WardCardGame2.Models.CardsEntity;
import com.war.WardCardGame2.Models.PlayersEntity;
import com.war.WardCardGame2.Repositories.CardsRepository;
import com.war.WardCardGame2.Repositories.GameRepository;
import com.war.WardCardGame2.Repositories.PlayerRepository;
import com.war.WardCardGame2.Services.CardsService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
class WardCardGame2ApplicationTests {
    @Mock
    private GameRepository gameRepository;

    @Mock
    private CardsRepository cardsRepository;
    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private CardsService cardService;
    private PlayersEntity player1;
    private CardsEntity card1;

    private PlayersEntity player2;
    private CardsEntity card2;

//    @Test
//    public void testFindById() {
//        // Mocking the GameRepository
//        GameRepository gameRepository = Mockito.mock(GameRepository.class);
//
//        // Creating a sample game entity
//        GameEntity gameEntity = new GameEntity(1L); // Assuming 1L is a valid game ID
//
//        // Stubbing the findById method to return the sample game entity
//        Mockito.when(gameRepository.findById(1L)).thenReturn(Optional.of(gameEntity));
//
//        // Calling the findById method
//        Optional<GameEntity> result = gameRepository.findById(1L);
//
//        // Asserting that the result is present and equals the sample game entity
//        assertTrue(result.isPresent());
//        assertEquals(gameEntity, result.get());
//
//        System.out.println("GameEntity: " + result.get());
//
//        System.out.println("Completed testFindById");
//    }

//    @Test
//    public void testShuffleCards() {
//        MockitoAnnotations.initMocks(this);
//
//        Long testGameId = 1L;
//        GameEntity gameEntity = new GameEntity(1L);
//
//        List<CardsEntity> cardsList = Arrays.asList(
//                new CardsEntity(1L, "1", "Heart", 1, gameEntity, createPlayerCardsEntities()),
//                new CardsEntity(2L, "2", "Spades", 2, gameEntity, createPlayerCardsEntities()),
//                new CardsEntity(3L, "3", "Diamonds", 3, gameEntity ,createPlayerCardsEntities()),
//                new CardsEntity(4L, "4", "Heart", 4, gameEntity, createPlayerCardsEntities()),
//                new CardsEntity(5L, "5", "Diamonds", 5, gameEntity, createPlayerCardsEntities()),
//                new CardsEntity(6L, "6", "Heart", 6, gameEntity, createPlayerCardsEntities())
//        );
//
//        when(gameRepository.findById(testGameId)).thenReturn(Optional.of(gameEntity));
//        when(cardsRepository.findAll()).thenReturn(cardsList);
//
//        List<CardsEntity> shuffledCards = cardService.shuffleCards(testGameId);
//
//        assertEquals(6, shuffledCards.size());
//    }

//    private Set<PlayerCardsEntity> createPlayerCardsEntities() {
//
//        PlayerCardsEntity playerCardsEntity1 = new PlayerCardsEntity();
//        playerCardsEntity1.setPlayerCardsId(1L);
//        playerCardsEntity1.setPlayer(player1);
//        playerCardsEntity1.setCard(card1);
//
//
//        PlayerCardsEntity playerCardsEntity2 = new PlayerCardsEntity();
//        playerCardsEntity2.setPlayerCardsId(2L);
//        playerCardsEntity2.setPlayer(player2);
//        playerCardsEntity2.setCard(card2);
//
//
//        Set<PlayerCardsEntity> playerCardsEntities = new HashSet<>();
//        playerCardsEntities.add(playerCardsEntity1);
//        playerCardsEntities.add(playerCardsEntity2);
//
//        return playerCardsEntities;
//    }

//    @Test
//    void testDealCards() {
//
//        GameEntity game = new GameEntity(1L);
//        PlayersEntity player1 = new PlayersEntity(1L);
//        PlayersEntity player2 = new PlayersEntity(2L);
//
//        List<CardsEntity> shuffledCards = new ArrayList<>();
//        for (int i = 0; i < 52; i++) {
//            CardsEntity card = new CardsEntity((long) i);
//            shuffledCards.add(card);
//        }
//
//        when(gameRepository.findById(1L)).thenReturn(Optional.of(game));
//        when(playerRepository.findById(1L)).thenReturn(Optional.of(player1));
//        when(playerRepository.findById(2L)).thenReturn(Optional.of(player2));
//        when(cardService.shuffleCards(1L)).thenReturn(shuffledCards);
//
//        Map<PlayersEntity, List<CardsEntity>> dealtCards = cardService.dealCards(1L, 1L, 2L);
//
//        assertEquals(26, dealtCards.get(player1).size());
//        assertEquals(26, dealtCards.get(player2).size());
//
//        // Verify that player cards are saved correctly
//        for (CardsEntity card : dealtCards.get(player1)) {
//            PlayerCardsEntity playerCard = new PlayerCardsEntity();
//            playerCard.setPlayer(player1);
//            playerCard.setCard(card);
//            assertEquals(playerCard, playerCardsRepository.save(playerCard));
//        }
//        for (CardsEntity card : dealtCards.get(player2)) {
//            PlayerCardsEntity playerCard = new PlayerCardsEntity();
//            playerCard.setPlayer(player2);
//            playerCard.setCard(card);
//            assertEquals(playerCard, playerCardsRepository.save(playerCard));
//        }
//    }
}

