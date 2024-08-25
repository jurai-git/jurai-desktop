package validator;

import com.jurai.data.model.Advogado;
import com.jurai.data.validator.AdvogadoValidator;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AdvogadoValidatorTest {

    AdvogadoValidator validator;

    @BeforeEach
    void setup() {
        validator = new AdvogadoValidator();
    }

    @Test
    @DisplayName("Validator should check for valid e-mails")
    void testEmailValidation() {
        Advogado adv = new Advogado(
                1,
                "Nome",
                "email@email.c",
                "12345678",
                "token"
        );

        assertEquals(validator.validate(adv), validator.getInvalidEmailMessage());
    }

    @Test
    @DisplayName("Validator should check for entirely valid Advogados")
    void testValidAdvogado() {
        Advogado adv = new Advogado(
                1,
                "Nome",
                "email@email.com",
                "12345678",
                "token"
        );

        assertEquals(validator.validate(adv), "");
    }

}
