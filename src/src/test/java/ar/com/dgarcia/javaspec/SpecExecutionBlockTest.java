package ar.com.dgarcia.javaspec;

import ar.com.dgarcia.javaspec.impl.model.impl.SpecExecutionBlock;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * This type verifies the behavior of spec execution block
 * Created by kfgodel on 13/07/14.
 */
public class SpecExecutionBlockTest {


    @Test
    public void itShouldExecuteTestCode(){
        Runnable mockedTestCode = mock(Runnable.class);
        SpecExecutionBlock specBlock = SpecExecutionBlock.create(Collections.emptyList(), mockedTestCode, Collections.emptyList());
        specBlock.run();
        verify(mockedTestCode).run();
    }

    @Test
    public void itShouldExecuteBeforeBlockBeforeTest() {
        List<String> executionOrder = new ArrayList<>();
        Runnable beforeBlock = ()-> executionOrder.add("before");
        Runnable testBlock =()->executionOrder.add("test");

        SpecExecutionBlock specBlock = SpecExecutionBlock.create(Lists.newArrayList(beforeBlock), testBlock, Collections.emptyList());
        specBlock.run();

        assertThat(executionOrder).isEqualTo(Lists.newArrayList("before", "test"));
    }

    @Test
    public void itShouldExecuteAfterBlockAfterTest(){
        List<String> executionOrder = new ArrayList<>();
        Runnable testBlock =()->executionOrder.add("test");
        Runnable afterBlock = ()-> executionOrder.add("after");

        SpecExecutionBlock specBlock = SpecExecutionBlock.create(Collections.emptyList(), testBlock, Lists.newArrayList(afterBlock));
        specBlock.run();

        assertThat(executionOrder).isEqualTo(Lists.newArrayList("test", "after"));
    }

    @Test
    public void itShouldExecuteBeforeBlocksInOrder(){
        List<String> executionOrder = new ArrayList<>();
        Runnable firstBlock = ()-> executionOrder.add("first");
        Runnable secondBlock =()->executionOrder.add("second");

        SpecExecutionBlock specBlock = SpecExecutionBlock.create(Lists.newArrayList(firstBlock, secondBlock), mock(Runnable.class), Collections.emptyList());
        specBlock.run();

        assertThat(executionOrder).isEqualTo(Lists.newArrayList("first", "second"));
    }

    @Test
    public void itShouldExecuteAfterBlocksInOrder(){
        List<String> executionOrder = new ArrayList<>();
        Runnable firstBlock = ()-> executionOrder.add("first");
        Runnable secondBlock =()->executionOrder.add("second");

        SpecExecutionBlock specBlock = SpecExecutionBlock.create(Collections.emptyList(), mock(Runnable.class), Lists.newArrayList(firstBlock, secondBlock));
        specBlock.run();

        assertThat(executionOrder).isEqualTo(Lists.newArrayList("first", "second"));

    }

}
