package za.co.wethinkcode.Textworld;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.world.TextWorld;

import static org.junit.jupiter.api.Assertions.assertSame;

public class TextWorldTest {

    private TextWorld textWorld;
    @BeforeEach
    public void setUp() {
        textWorld = TextWorld.getInstance(); // Get singleton instance
    }

    @Test
    public void testSingletonInstance() {
        TextWorld anotherInstance = TextWorld.getInstance();
        assertSame(textWorld, anotherInstance, "TextWorld should return the same instance each time");
    }

}
