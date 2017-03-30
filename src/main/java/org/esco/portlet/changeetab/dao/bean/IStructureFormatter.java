package org.esco.portlet.changeetab.dao.bean;

import org.esco.portlet.changeetab.model.Structure;

/**
 * Created by jgribonvald on 28/07/16.
 */
public interface IStructureFormatter {

    /** Formatter used in visitor pattern
     * @param input UniteAdministrativeImmatriculee to format
     * @return The UniteAdministrativeImmatriculee object formatted
     */
    Structure format (Structure input);
}
