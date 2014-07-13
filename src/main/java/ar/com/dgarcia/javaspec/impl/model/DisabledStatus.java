package ar.com.dgarcia.javaspec.impl.model;

/**
 * This type represents possible values for disable status of a group spec or test
 * Created by kfgodel on 12/07/14.
 */
public enum DisabledStatus {
    /**
     * The group or test can be run normally
     */
    ENABLED,
    /**
     * The group or test mut be omitted
     */
    DISABLED{

        @Override
        public boolean isDisabledConsidering(SpecGroup containerGroup) {
            return true;
        }
    };

    /**
     * Verifies if element can be considered disabled given this instance value and the element container
     * @param containerGroup The group that contains the element
     * @return true if element is disabled or container hierarchy is disabled, false otherwise
     */
    public boolean isDisabledConsidering(SpecGroup containerGroup) {
        //If we are enabled, then depends on container (until we reach NullContainerGroup)
        return containerGroup.isMarkedAsDisabled();
    }

}
