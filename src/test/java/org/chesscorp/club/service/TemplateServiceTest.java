package org.chesscorp.club.service;

import org.assertj.core.api.Assertions;
import org.chesscorp.club.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thymeleaf.spring4.SpringTemplateEngine;

/**
 * Test message templates.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@Rollback
public class TemplateServiceTest {

    @Autowired
    private SpringTemplateEngine templateEngine;

    /**
     * Build a simple template.
     */
    @Test
    public void testAccountValidation() {
        TemplateServiceImpl templateService = new TemplateServiceImpl(templateEngine, "http://localhost/");

        String html = templateService.buildEmailAccountValidation("John Doe", "###Token###");
        Assertions.assertThat(html)
            .contains("John Doe")
            .contains(" href=\"http://localhost/#!/accountValidation/###Token###\"");
    }
}
