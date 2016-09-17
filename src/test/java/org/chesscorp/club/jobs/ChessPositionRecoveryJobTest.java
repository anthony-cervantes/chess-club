package org.chesscorp.club.jobs;

import org.assertj.core.api.Assertions;
import org.chesscorp.club.model.stats.ChessClubPosition;
import org.chesscorp.club.persistence.ChessPositionRepository;
import org.chesscorp.club.service.MessagingService;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collection;


public class ChessPositionRecoveryJobTest {

    @Test
    public void testRecovery() {
        Collection<ChessClubPosition> positions = new ArrayList<>();
        positions.add(new ChessClubPosition("xxxxx"));

        MessagingService messagingService = Mockito.mock(MessagingService.class);
        ChessPositionRepository positionRepository = Mockito.mock(ChessPositionRepository.class);
        Mockito.when(positionRepository.findAllByScore(Mockito.any(Integer.class))).thenReturn(positions.stream());

        ChessPositionRecoveryJob recoveryJob = new ChessPositionRecoveryJob(messagingService, positionRepository);
        recoveryJob.triggerRecovery();

        Assertions.assertThat(Mockito.mockingDetails(messagingService).getInvocations()).hasSize(1);
    }
}
