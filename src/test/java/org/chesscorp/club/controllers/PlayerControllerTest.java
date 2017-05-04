package org.chesscorp.club.controllers;

import org.alcibiade.chess.model.ChessGameStatus;
import org.chesscorp.club.Application;
import org.chesscorp.club.model.game.ChessGame;
import org.chesscorp.club.model.people.ClubPlayer;
import org.chesscorp.club.model.people.ExternalPlayer;
import org.chesscorp.club.model.people.Player;
import org.chesscorp.club.model.people.RobotPlayer;
import org.chesscorp.club.persistence.ChessGameRepository;
import org.chesscorp.club.persistence.PlayerRepository;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.stream.IntStream;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@Rollback
public class PlayerControllerTest {
    @Autowired
    private PlayerController playerController;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private ChessGameRepository chessGameRepository;

    @Test
    @Transactional
    public void testSearchSuccess() throws Exception {
        playerRepository.save(new ExternalPlayer("Freddie Mercury", "freddie"));
        playerRepository.save(new ExternalPlayer("Brian May", "brian"));
        playerRepository.save(new ExternalPlayer("John Deacon", "john"));
        playerRepository.save(new ExternalPlayer("Roger Taylor", "roger"));

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(playerController).build();

        mockMvc.perform(
                post("/api/player/search").param("query", "Billy")
        ).andExpect(
                status().is2xxSuccessful()
        ).andExpect(
                jsonPath("$", hasSize(0))
        );

        mockMvc.perform(
                post("/api/player/search").param("query", "bri")
        ).andExpect(
                status().is2xxSuccessful()
        ).andExpect(
                jsonPath("$", hasSize(1))
        );

        mockMvc.perform(
                post("/api/player/search").param("query", "er")
        ).andExpect(
                status().is2xxSuccessful()
        ).andExpect(
                jsonPath("$", hasSize(2))
        );
    }

    @Test
    @Transactional
    public void testSearchLimit() throws Exception {
        IntStream.range(0, 100).forEach(i -> playerRepository.save(new ExternalPlayer("Player number " + i, "player" + i)));

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(playerController).build();

        mockMvc.perform(
                post("/api/player/search").param("query", "play")
        ).andExpect(
                status().is2xxSuccessful()
        ).andExpect(
                jsonPath("$", hasSize(100))
        );


        mockMvc.perform(
                post("/api/player/search").param("query", "play").param("limit", "20")
        ).andExpect(
                status().is2xxSuccessful()
        ).andExpect(
                jsonPath("$", hasSize(20))
        );
    }

    @Test
    @Transactional
    public void testSearchAI() throws Exception {
        playerRepository.save(new ExternalPlayer("Freddie Mercury", "freddie"));
        playerRepository.save(new ClubPlayer("Brian May"));
        playerRepository.save(new RobotPlayer("GnuChess", "gnuchess", "1"));

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(playerController).build();

        mockMvc.perform(
                get("/api/player/search-ai")
        ).andExpect(
                status().is2xxSuccessful()
        ).andExpect(
                jsonPath("$", hasSize(1))
        ).andExpect(
                jsonPath("$[0].displayName", Matchers.is("GnuChess"))
        );
    }

    @Test
    @Transactional
    public void testProfile() throws Exception {
        Player freddie = playerRepository.save(new ExternalPlayer("Freddie Mercury", "freddie"));
        Player brian = playerRepository.save(new ClubPlayer("Brian May"));
        playerRepository.save(new RobotPlayer("GnuChess", "gnuchess", "1"));

        chessGameRepository.save(new ChessGame(brian, freddie, new ArrayList<>(), ChessGameStatus.BLACKWON, OffsetDateTime.now()));
        chessGameRepository.save(new ChessGame(freddie, brian, new ArrayList<>(), ChessGameStatus.BLACKWON, OffsetDateTime.now()));

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(playerController).build();

        mockMvc.perform(
                get("/api/player/profile/" + brian.getId())
        ).andExpect(
                status().is2xxSuccessful()
        ).andExpect(
                jsonPath("$.player.displayName", Matchers.is("Brian May"))
        ).andExpect(
                jsonPath("$.pvpStatistics", hasSize(1))
        ).andExpect(
                jsonPath("$.eloHistory", hasSize(0))
        );
    }
}
