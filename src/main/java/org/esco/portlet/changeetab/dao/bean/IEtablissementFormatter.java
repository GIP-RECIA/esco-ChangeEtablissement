package org.esco.portlet.changeetab.dao.bean;

import org.esco.portlet.changeetab.model.Etablissement;

/**
 * Created by jgribonvald on 28/07/16.
 */
public interface IEtablissementFormatter {

    /** Formatter used in visitor pattern **/
    Etablissement format (Etablissement input);
}
