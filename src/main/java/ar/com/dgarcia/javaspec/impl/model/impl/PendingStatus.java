package ar.com.dgarcia.javaspec.impl.model.impl;

import ar.com.dgarcia.javaspec.impl.model.SpecGroup;

/**
 * This type represents possible values for pending status of a group spec or test
 * Created by kfgodel on 12/07/14.
 */
public enum PendingStatus {
    /**
     * Group or test can be executed normally
     */
    NORMAL,
    /**
     * Group or test is pending. Must be listed, but not executed
     */
    PENDING{
        @Override
        public boolean isPendingConsidering(SpecGroup containerGroup) {
            return true;
        }
    };

    /**
     * Indicates if this value is considered pending given the container group
     * @param containerGroup The group containing the test with this value
     * @return
     */
    public boolean isPendingConsidering(SpecGroup containerGroup) {
        return false;
    }
}
