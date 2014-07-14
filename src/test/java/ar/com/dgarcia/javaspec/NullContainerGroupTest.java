package ar.com.dgarcia.javaspec;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import ar.com.dgarcia.javaspec.impl.model.impl.NullContainerGroup;

/**
 * This type berifies the expected behavior for the null container group
 * Created by kfgodel on 13/07/14.
 */
public class NullContainerGroupTest {


    private NullContainerGroup nullContainer;

    @Before
    public void createNullContainer(){
        nullContainer = NullContainerGroup.create();
    }

    @Test
    public void itShouldBeEnabled(){
        assertThat(nullContainer.isMarkedAsDisabled()).isFalse();
    }

    @Test
    public void itShouldContainNoBeforeOrAfter(){
        assertThat(nullContainer.getBeforeBlocks()).isEmpty();
        assertThat(nullContainer.getAfterBlocks()).isEmpty();
    }
}
