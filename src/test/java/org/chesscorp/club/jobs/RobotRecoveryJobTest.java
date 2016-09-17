package org.chesscorp.club.jobs;

import org.alcibiade.chess.model.ChessGameStatus;
import org.assertj.core.api.Assertions;
import org.chesscorp.club.model.game.ChessGame;
import org.chesscorp.club.model.people.ClubPlayer;
import org.chesscorp.club.model.people.RobotPlayer;
import org.chesscorp.club.persistence.ChessGameRepository;
import org.chesscorp.club.service.MessagingService;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collection;


public class RobotRecoveryJobTest {

    @Test
    public void testRecovery() {
        ClubPlayer bob = new ClubPlayer("Bob");
        ChessGame game1 = Mockito.mock(ChessGame.class);
        Mockito.when(game1.getNextPlayer()).thenReturn(bob);

        RobotPlayer rob = new RobotPlayer("Rob", "Ai", "");
        ChessGame game2 = Mockito.mock(ChessGame.class);
        Mockito.when(game2.getNextPlayer()).thenReturn(rob);

        Collection<ChessGame> games = new ArrayList<>();
        games.add(game1);
        games.add(game2);

        MessagingService messagingService = Mockito.mock(MessagingService.class);
        ChessGameRepository chessGameRepository = Mockito.mock(ChessGameRepository.class);
        Mockito.when(chessGameRepository.findAllByStatus(Mockito.any(ChessGameStatus.class))).thenReturn(games.stream());

        RobotRecoveryJob recoveryJob = new RobotRecoveryJob(messagingService, chessGameRepository);
        recoveryJob.triggerRobotGameEvents();

        Assertions.assertThat(Mockito.mockingDetails(messagingService).getInvocations()).hasSize(1);
    }
}
