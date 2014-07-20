package ar.com.dgarcia.javaspec;

import ar.com.dgarcia.javaspec.api.TestContext;
import ar.com.dgarcia.javaspec.api.Variable;
import ar.com.dgarcia.javaspec.impl.model.TestContextDefinition;
import ar.com.dgarcia.javaspec.impl.model.impl.SpecExecutionBlock;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * This type verifies the behavior of spec execution block
 * Created by kfgodel on 13/07/14.
 */
public class SpecExecutionBlockTest {


    private List<Runnable> noBefores = Collections.emptyList();
    private List<Runnable> noAfters = Collections.emptyList();
    private TestContextDefinition mockedParentContext;
    private Variable<TestContext> sharedContext;
    private Runnable mockedTestCode;

    @Before
    public void createMocks(){
        mockedParentContext = mock(TestContextDefinition.class);
        sharedContext = Variable.of(mockedParentContext);
        mockedTestCode = mock(Runnable.class);
    }

    @Test
    public void itShouldExecuteTestCode(){
        SpecExecutionBlock specBlock = SpecExecutionBlock.create(noBefores, mockedTestCode, noAfters, mockedParentContext, sharedContext);

        specBlock.run();

        verify(mockedTestCode).run();
    }

    @Test
    public void itShouldExecuteBeforeBlockBeforeTest() {
        List<String> executionOrder = new ArrayList<>();
        Runnable beforeBlock = ()-> executionOrder.add("before");
        Runnable testBlock =()->executionOrder.add("test");

        SpecExecutionBlock specBlock = SpecExecutionBlock.create(Lists.newArrayList(beforeBlock), testBlock, noAfters, mockedParentContext, sharedContext);
        specBlock.run();

        assertThat(executionOrder).isEqualTo(Lists.newArrayList("before", "test"));
    }

    @Test
    public void itShouldExecuteAfterBlockAfterTest(){
        List<String> executionOrder = new ArrayList<>();
        Runnable testBlock =()->executionOrder.add("test");
        Runnable afterBlock = ()-> executionOrder.add("after");

        SpecExecutionBlock specBlock = SpecExecutionBlock.create(noBefores, testBlock, Lists.newArrayList(afterBlock), mockedParentContext, sharedContext);
        specBlock.run();

        assertThat(executionOrder).isEqualTo(Lists.newArrayList("test", "after"));
    }

    @Test
    public void itShouldExecuteBeforeBlocksInOrder(){
        List<String> executionOrder = new ArrayList<>();
        Runnable firstBlock = ()-> executionOrder.add("first");
        Runnable secondBlock =()->executionOrder.add("second");

        SpecExecutionBlock specBlock = SpecExecutionBlock.create(Lists.newArrayList(firstBlock, secondBlock), mock(Runnable.class), noAfters, mockedParentContext, sharedContext);
        specBlock.run();

        assertThat(executionOrder).isEqualTo(Lists.newArrayList("first", "second"));
    }

    @Test
    public void itShouldExecuteAfterBlocksInOrder(){
        List<String> executionOrder = new ArrayList<>();
        Runnable firstBlock = ()-> executionOrder.add("first");
        Runnable secondBlock =()->executionOrder.add("second");

        SpecExecutionBlock specBlock = SpecExecutionBlock.create(noBefores, mock(Runnable.class), Lists.newArrayList(firstBlock, secondBlock), mockedParentContext, sharedContext);
        specBlock.run();

        assertThat(executionOrder).isEqualTo(Lists.newArrayList("first", "second"));
    }


    @Test
    public void itShouldCreateItsOwnTestContext(){
        Runnable testCode = ()->  sharedContext.get().let("foo", ()-> 1);
        SpecExecutionBlock specBlock = SpecExecutionBlock.create(noBefores, testCode, noAfters, mockedParentContext, sharedContext);

        specBlock.run();

        verify(mockedParentContext, never()).let(anyString(), anyObject());
    }

}
