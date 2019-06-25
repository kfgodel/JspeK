package info.kfgodel.jspek.junit;

import info.kfgodel.jspek.api.variable.Variable;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * This type verifies Variable behavior
 * Created by kfgodel on 12/07/14.
 */
public class VariableTest {

    private Variable<Object> var;

    @Test
    public void itShouldStoreNullIfNoInitialValue(){
        var = Variable.create();
        assertThat(var.get()).isNull();
    }

    @Test
    public void itShouldStoreTheInitialValueIfDefined(){
        var = Variable.of("Hola");
        assertThat(var.get()).isEqualTo("Hola");
    }

    @Test
    public void itShouldAllowValueChange(){
        var = Variable.create();
        var.set("Mundo");
        assertThat(var.get()).isEqualTo("Mundo");
    }

    @Test
    public void itShouldAllowValueCleaning(){
        var = Variable.of("Hola Mundo");
        var.clean();
        assertThat(var.get()).isNull();
    }

    @Test
    public void itShouldAllowAdditionAssignmentForIntegerValue(){
        var = Variable.of(1);
        assertThat(var.storeSumWith(1).get()).isEqualTo(2);
    }

    @Test
    public void itShouldAllowAdditionAssignmentForDoubleValue(){
        var = Variable.of(1.0);
        assertThat(var.storeSumWith(1.5).get()).isEqualTo(2.5);
    }

    @Test
    public void itShouldAllowAdditionAssignmentForStringValue(){
        var = Variable.of("Hola");
        assertThat(var.storeSumWith(" mundo").get()).isEqualTo("Hola mundo");
    }

    @Test
    public void itShouldThrowExceptionWhenAdditionAssignmentOverUnsupportedType(){
        var = Variable.create();
        try{
            var.storeSumWith(2);
            Assertions.failBecauseExceptionWasNotThrown(UnsupportedOperationException.class);
        }catch(UnsupportedOperationException e){
            assertThat(e)
                    .hasMessage("Sum is not supported for value [null] and operand [2]")
                    .hasNoCause();
        }
    }

    @Test
    public void itShouldAllowGenericTypeCasting(){
        var = Variable.of(1L);
        assertThat(var.castedTo(Long.class)).isEqualTo(Long.valueOf(1L));
    }

}
