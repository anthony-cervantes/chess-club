package org.chesscorp.club.jobs;

import org.assertj.core.api.Assertions;
import org.chesscorp.club.Application;
import org.chesscorp.club.model.people.RobotPlayer;
import org.chesscorp.club.model.robot.RobotPreparationQuery;
import org.chesscorp.club.persistence.RobotCacheRepository;
import org.chesscorp.club.persistence.RobotRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@Rollback
public class RobotPreparationListenerTest {

    @Autowired
    private RobotPreparationListener robotPreparationListener;

    @Autowired
    private RobotRepository robotRepository;

    @Autowired
    private RobotCacheRepository robotCacheRepository;

    @Test
    @Transactional
    public void testPreparation() {
        RobotPlayer robotPlayer = robotRepository.save(new RobotPlayer("Simple AI", "randomAI", "", true));
        Assertions.assertThat(robotCacheRepository.findAll()).isEmpty();
        RobotPreparationQuery query = new RobotPreparationQuery(robotPlayer.getId(), new ArrayList<>(), 2);
        robotPreparationListener.prepareRobot(query);
        Assertions.assertThat(robotCacheRepository.findAll()).hasSize(2);
    }
}
