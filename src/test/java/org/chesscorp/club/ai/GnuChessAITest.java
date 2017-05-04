package org.chesscorp.club.ai;

import org.chesscorp.club.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@Rollback
public class GnuChessAITest {

    @Autowired
    private GnuChessAI gnuChessAI;

    @Autowired
    private GenericTestHelper genericTestHelper;

    @Test
    public void testAI() {
        genericTestHelper.testAI(gnuChessAI);
    }
}
