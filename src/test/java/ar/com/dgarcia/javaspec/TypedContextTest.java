package ar.com.dgarcia.javaspec;

import ar.com.dgarcia.javaspec.api.Variable;
import ar.com.dgarcia.javaspec.api.contexts.TestContext;
import ar.com.dgarcia.javaspec.impl.context.MappedContext;
import ar.com.dgarcia.javaspec.impl.context.typed.TypedContextFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This type verifies the behavior of type contexts using proxies
 * Created by kfgodel on 21/07/14.
 */
public class TypedContextTest {

    public static interface TypedTestContext extends TestContext {

        void letFoo(Supplier<Integer> fooDefinition);
        Integer getFoo();

        void bar(Supplier<Integer> barDefinition);
        Integer bar();
    }

    private TypedTestContext context;
    private Variable<TestContext> sharedVariable;

    @Before
    public void createTypedContext(){
        sharedVariable = Variable.of(MappedContext.create());
        context = TypedContextFactory.createInstanceOf(TypedTestContext.class, sharedVariable);
    }


    @Test
    public void itShouldAllowUntypedVariableDefinition(){
        context.let("var1", ()-> 1);

        assertThat(context.<Integer>get("var1")).isEqualTo(1);
        assertThat(sharedVariable.get().<Integer>get("var1")).isEqualTo(1);
    }

    @Test
    public void itShouldAllowTypedVariableWithLetPrefix(){
        context.letFoo(()-> 2);

        assertThat(context.getFoo()).isEqualTo(2);
        assertThat(sharedVariable.get().<Integer>get("foo")).isEqualTo(2);
    }

    @Test
    public void itShouldAllowTypedVariableWithoutLetPrefix(){
        context.bar(() -> 3);

        assertThat(context.bar()).isEqualTo(3);
        assertThat(sharedVariable.get().<Integer>get("bar")).isEqualTo(3);
    }

    @Test
    public void itShouldAllowMixedVariableDefinition(){
        context.letFoo(() -> 3);

        assertThat(context.<Integer>get("foo")).isEqualTo(3);
    }

    @Test
    public void itShouldAllowVariableDefinitionInDifferentContexts() {
        MappedContext otherContext = MappedContext.create();

        context.letFoo(()-> 1);
        sharedVariable.set(otherContext);
        context.letFoo(()-> 2);


        assertThat(context.getFoo()).isEqualTo(2);
    }
}
