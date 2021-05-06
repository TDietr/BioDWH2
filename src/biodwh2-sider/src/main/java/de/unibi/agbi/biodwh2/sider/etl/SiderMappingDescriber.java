package de.unibi.agbi.biodwh2.sider.etl;

import de.unibi.agbi.biodwh2.core.DataSource;
import de.unibi.agbi.biodwh2.core.etl.MappingDescriber;
import de.unibi.agbi.biodwh2.core.model.IdentifierType;
import de.unibi.agbi.biodwh2.core.model.graph.*;
import org.apache.commons.lang3.StringUtils;

public class SiderMappingDescriber extends MappingDescriber {
    public SiderMappingDescriber(final DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public NodeMappingDescription[] describe(final Graph graph, final Node node, final String localMappingLabel) {
        switch (localMappingLabel) {
            case "Drug": {
                NodeMappingDescription description = new NodeMappingDescription(NodeMappingDescription.NodeType.DRUG);
                String id = StringUtils.stripStart(node.getProperty("id"), "CID");
                description.addIdentifier(IdentifierType.PUB_CHEM_COMPOUND, "" + Long.parseLong(id));
                return new NodeMappingDescription[]{description};
            }
            case "Disease": {
                NodeMappingDescription description = new NodeMappingDescription(
                        NodeMappingDescription.NodeType.DISEASE);
                description.addIdentifier(IdentifierType.UMLS_CUI, node.<String>getProperty("id"));
                return new NodeMappingDescription[]{description};
            }
            case "SideEffect": {
                NodeMappingDescription description = new NodeMappingDescription(
                        NodeMappingDescription.NodeType.ADVERSE_EVENT);
                description.addIdentifier(IdentifierType.UMLS_CUI, node.<String>getProperty("id"));
                return new NodeMappingDescription[]{description};
            }
        }
        return null;
    }

    @Override
    protected String[] getNodeMappingLabels() {
        return new String[]{"Drug", "Disease", "SideEffect"};
    }

    @Override
    public PathMappingDescription describe(final Graph graph, final Node[] nodes, final Edge[] edges) {
        return null;
    }

    @Override
    protected String[][] getEdgeMappingPaths() {
        return new String[0][];
    }
}
